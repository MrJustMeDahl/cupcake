package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
                    int userId = rs.getInt("userId");
                    String role = rs.getString("role");
                    String name = rs.getString("name");
                    float balance = Float.parseFloat(rs.getString("balance"));
                    user = new User(userId, name, email, password, balance, role, OrderFacade.getOrdersByUserId(userId, connectionPool));
                } else {
                    throw new DatabaseException("Wrong email or password");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    public static User createUser(String name, String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {

        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "INSERT INTO user (name, email, password) values (?,?,?)";


        try (Connection connection = connectionPool.getConnection())
        {

            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, name);
                ps.setNString(2, email);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0)
                {
                    user = login(email, password, connectionPool);
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

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException{
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM cupcake.user";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int userId = rs.getInt("userId");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    float balance = rs.getFloat("balance");
                    String role = rs.getString("role");
                    allUsers.add(new User(userId, name, email, password, balance, role, OrderFacade.getOrdersByUserId(userId, connectionPool)));
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to load users from database");
        }
        return allUsers;
    }

    public static void updateBalance(float amount, int userID, ConnectionPool connectionPool) throws DatabaseException{
        String sqlUpdate = "UPDATE cupcake.user SET balance = ? WHERE userId = ?";
        User user = getUserByID(userID, connectionPool);
        float newBalance = user.getBalance();
        newBalance += amount;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sqlUpdate)){
                ps.setFloat(1, newBalance);
                ps.setInt(2, userID);
                ps.execute();
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to update balance in user table, in database");
        }
    }

    private static User getUserByID(int userID, ConnectionPool connectionPool) throws DatabaseException{
        String sql = "SELECT * FROM cupcake.user WHERE userId = ?";
        User user = null;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int userId = rs.getInt("userId");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    float balance = rs.getFloat("balance");
                    String role = rs.getString("role");
                    user = new User(userId, name, email, password, balance, role, OrderFacade.getOrdersByUserId(userId, connectionPool));
                }
            }
        }catch(SQLException e){
            throw new DatabaseException("No user found with userID: " + userID);
        }
        return user;
    }
}