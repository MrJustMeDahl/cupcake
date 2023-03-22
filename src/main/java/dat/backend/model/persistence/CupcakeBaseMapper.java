package dat.backend.model.persistence;

import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CupcakeBaseMapper {


    public static List<CupcakeBase> getAllBases(ConnectionPool connectionPool) {
    String sql = "SELECT * FROM cupcakebase";
    List<CupcakeBase> cupcakeBase = new ArrayList<>();

    try (Connection connection = connectionPool.getConnection()){
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int baseId = rs.getInt("cupcakebaseId");
                String flavor = rs.getString("flavor");
                float price = rs.getFloat("price");

                CupcakeBase newCupcakeBase = new CupcakeBase(flavor, price, baseId);
                cupcakeBase.add(newCupcakeBase);
            }
        }

    }catch (SQLException e){
        e.printStackTrace();
    }

        return cupcakeBase;

    }

    public static void addFlavor(String flavor, float price, ConnectionPool connectionPool) throws DatabaseException{
        Logger.getLogger("web").log(Level.INFO,"");
        CupcakeBase cupcakeBase;
        String sql = "INSERT INTO cupcakebase (flavor, price) VALUES (?,?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS)) {
                ps.setString(1, flavor);
                ps.setFloat(2, price);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert Baseflavor into database");
        }

    }

    public static void deleteBase(int cupcakebase_id, ConnectionPool connectionPool) {
    String sql = "DELETE FROM cupcakebase WHERE cupcakebaseId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)){;
                ps.setInt(1,cupcakebase_id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editBase(int cupcakebaseId, String flavor, float price, ConnectionPool connectionPool) {
        String sql = "UPDATE item SET flavor = ? AND price = ? WHERE cupcakebaseId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1,flavor);
                ps.setFloat(2,price);
                ps.setInt(3,cupcakebaseId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
