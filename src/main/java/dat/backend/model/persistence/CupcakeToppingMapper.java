package dat.backend.model.persistence;

import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CupcakeToppingMapper {


    public static List<CupcakeTopping> getAllToppings(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM cupcaketopping";
        List<CupcakeTopping> cupcakeTopping = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    int toppingId = rs.getInt("cupcaketoppingId");
                    String flavor = rs.getString("flavor");
                    float price = rs.getFloat("price");

                    CupcakeTopping newCupcakeTopping = new CupcakeTopping(flavor, price, toppingId);
                    cupcakeTopping.add(newCupcakeTopping);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return cupcakeTopping;

    }

    public static void addToppingFlavor(String flavor, float price, ConnectionPool connectionPool) throws DatabaseException{
        Logger.getLogger("web").log(Level.INFO,"");
        CupcakeTopping cupcakeTopping;
        String sql = "INSERT INTO cupcaketopping (flavor, price) VALUES (?,?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS)) {
                ps.setString(1, flavor);
                ps.setFloat(2, price);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Topping flavor into database");
        }

    }

    public static void deleteTopping(int cupcaketoppingId, ConnectionPool connectionPool) {
        String sql = "DELETE FROM cupcaketopping WHERE cupcaketoppingId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)){;
                ps.setInt(1,cupcaketoppingId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editTopping(int cupcaketoppingId, String flavor, float price, ConnectionPool connectionPool) {
        String sql = "UPDATE item SET flavor = ? AND price = ? WHERE cupcakebase_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,flavor);
                ps.setFloat(2,price);
                ps.setInt(3,cupcaketoppingId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

