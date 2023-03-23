package dat.backend.model.persistence;

import dat.backend.model.entities.Cupcake;
import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class OrderMapper {

    static Order createOrder(Cupcake cupcake, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sqlOrder = "INSERT INTO cupcake.order (price, user_id) VALUES (?, ?)";
        int orderId;
        List<Cupcake> cupcakes = new ArrayList<>();
        cupcakes.add(cupcake);
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setFloat(1, cupcake.getFullPrice());
                ps.setInt(2, userId);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new DatabaseException("Failed to create order in the database");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order. Something went wrong with the database");
        }
        String sqlCupcake = "INSERT INTO cupcake.cupcake (orderId, cupcakebaseId, cupcaketoppingId) VALUES (?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sqlCupcake)) {
                ps.setInt(1, orderId);
                ps.setInt(2, cupcake.getBase().getBaseID());
                ps.setInt(3, cupcake.getTopping().getToppingID());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order. Something went wrong with the database");
        }
        return new Order(orderId, userId, cupcakes, false, false);
    }

    static Order getOrderByOrderId(int orderId, ConnectionPool connectionPool){
        String sqlOrder = "SELECT * FROM cupcake.order WHERE orderId = ?";
        Order order;
        int id = 0;
        List<Cupcake> cupcakes = new ArrayList<>();
        int userId = 0;
        boolean isPaid = false;
        boolean isOrdered = false;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sqlOrder)){
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    id = rs.getInt("orderId");
                    userId = rs.getInt("userId");
                    isPaid = rs.getBoolean("isPaid");
                    isOrdered = rs.getBoolean("isOrdered");
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        try(Connection connection = connectionPool.getConnection()){
            cupcakes = getCupcakesForOrder(id, connection);
        } catch (SQLException e){

        }
        order = new Order(id, userId, cupcakes, isPaid, isOrdered);
        return order;
    }

    static List<Order> getOrdersByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sqlOrder = "SELECT * FROM cupcake.order WHERE userId = ?";
        int id = 0;
        List<Cupcake> cupcakes = new ArrayList<>();
        boolean isPaid = false;
        boolean isOrdered = false;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sqlOrder)){
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    id = rs.getInt("orderId");
                    isPaid = rs.getBoolean("isPaid");
                    isOrdered = rs.getBoolean("isOrdered");
                    cupcakes = getCupcakesForOrder(id, connection);
                    orders.add(new Order(id, userId, cupcakes, isOrdered, isPaid));
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static List<Cupcake> getCupcakesForOrder(int orderId, Connection connection){
        String sqlCupcakebase = "SELECT * FROM cupcake.cupcakeview WHERE orderId = ?";
        List<Cupcake> cupcakes = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sqlCupcakebase)){
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CupcakeBase cupcakeBase = new CupcakeBase(rs.getString("baseflavor"), rs.getFloat("basePrice"),rs.getInt("baseId"));
                CupcakeTopping cupcakeTop = new CupcakeTopping(rs.getString("toppingflavor"), rs.getFloat("toppingprice"), rs.getInt("toppingId"));
                cupcakes.add(new Cupcake(cupcakeTop, cupcakeBase));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return cupcakes;
    }
}

