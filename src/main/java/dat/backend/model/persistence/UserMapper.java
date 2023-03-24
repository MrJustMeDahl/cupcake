package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper {
    static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");


        User user = null;

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    String name = rs.getString("name");
                    int ID = rs.getInt("userId");
                    float balance = Float.parseFloat(rs.getString("balance"));
                    user = new User(name, email, password, balance, role);
                    user.setUserId(ID);
                    System.out.println("USERID ER NU: " + user.getUserId());

                    user.setUserOrders(OrderFacade.getOrdersByUserId(ID,connectionPool));
                } else {
                    throw new DatabaseException("Wrong email or password");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    public static User createUser(String name, String email, String password,  ConnectionPool connectionPool) throws DatabaseException
    {

        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "INSERT INTO user (name, email, password, balance, role) values (?,?,?,?,?)";
        System.out.println("VI ER EFTER STATEMENT 333333333");

        try (Connection connection = connectionPool.getConnection())
        {

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                System.out.println("prepared statement1: " + ps);
                ps.setString(1, name);
                ps.setNString(2, email);
                ps.setString(3, password);
                ps.setFloat(4,0);
                ps.setString(5, "user");
                System.out.println("prepared statement1: " + ps);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    user = new User(name, email, password);
                } else
                {
                    throw new DatabaseException("The user with email = " + email + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Error: " + ex.getMessage());
            throw new DatabaseException(ex, "Could not insert email into database");
        }
        return user;
    }
}