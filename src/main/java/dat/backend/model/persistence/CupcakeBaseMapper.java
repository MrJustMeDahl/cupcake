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


    public static List<CupcakeBase> getAllBases(ConnectionPool connectionPool) throws DatabaseException{
    String sql = "SELECT * FROM cupcakebase";
    List<CupcakeBase> cupcakeBase = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int baseId = rs.getInt("cupcakebaseId");
                    String flavor = rs.getString("flavor");
                    float price = rs.getFloat("price");

                    CupcakeBase newCupcakeBase = new CupcakeBase(flavor, price, baseId);
                    cupcakeBase.add(newCupcakeBase);
                }
            }

    }catch (SQLException e){
        throw new DatabaseException("Failed to retrieve list of bases");
    }

        return cupcakeBase;

    }

    public static CupcakeBase getOneBase(int baseId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM cupcakebase WHERE cupcakebaseId = ?";

        String flavor;
        float price;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, baseId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    flavor = rs.getString("flavor");
                    price = rs.getFloat("price");
                    return new CupcakeBase(flavor, price, baseId);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "fail to get base from database" );
        }
        return null;

    }

    public static void addFlavor(String flavor, float price, ConnectionPool connectionPool) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
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

    public static void deleteBase(int cupcakebase_id, ConnectionPool connectionPool) throws DatabaseException {
    String sql = "DELETE FROM cupcakebase WHERE cupcakebaseId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, cupcakebase_id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete base in database");
        }
    }

    public static void editBase(int cupcakebaseId, String flavor, float price, ConnectionPool connectionPool) throws DatabaseException{
        String sql = "UPDATE item SET flavor = ? AND price = ? WHERE cupcakebaseId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, flavor);
                ps.setFloat(2, price);
                ps.setInt(3, cupcakebaseId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not edit base in database");
        }
    }
}
