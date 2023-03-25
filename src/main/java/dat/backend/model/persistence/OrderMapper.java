package dat.backend.model.persistence;

import dat.backend.model.entities.*;
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
            throw new DatabaseException(ex, "Error creating order. Cupcake was not added to order");
        }
        return new Order(orderId, userId, cupcakes, false, false);
    }

    static Order getOrderByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException{
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
                throw new DatabaseException("Failed to retrieve order with id: " + orderId);
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to retrieve order with id: " + orderId);
        }
        try(Connection connection = connectionPool.getConnection()){
            cupcakes = getCupcakesForOrder(id, connection);
        } catch (SQLException e){
            throw new DatabaseException("Failed to retrieve cupcakes on order with id: " + orderId);
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
                    if(isOrdered) {
                        orders.add(new Order(id, userId, cupcakes, isOrdered, isPaid));
                    } else {
                        orders.add(new ShoppingBasket(id, userId, cupcakes, isOrdered, isPaid));
                    }
                }
            } catch(SQLException e){
                throw new DatabaseException("Failed to retrieve orders for user with id: " + userId);
            }
        } catch(SQLException e) {
            throw new DatabaseException("Failed to retrieve orders for user with id: " + userId);
        }
        return orders;
    }

    private static List<Cupcake> getCupcakesForOrder(int orderId, Connection connection) throws DatabaseException{
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
            throw new DatabaseException("Failed to get cupcakes on order with order id: " + orderId);
        }
        return cupcakes;
    }

    public static void deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        String sqlOrder = "DELETE FROM cupcake.order WHERE orderId = ?";
        String sqlCupcake = "DELETE FROM cupcake.cupcake WHERE orderId = ?";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sqlOrder)){
                ps.setInt(1, orderID);
                try(PreparedStatement ps1 = connection.prepareStatement(sqlCupcake)){
                    ps1.setInt(1, orderID);
                    ps1.execute();
                }
                ps.execute();
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to delete order in database.");
        }
    }

    public static void updateOrderPaid(int orderID, boolean paid, ConnectionPool connectionPool) throws DatabaseException{
        String sql = "UPDATE cupcake.order SET order.isPaid = ? WHERE orderId = ?";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                if(paid){
                    ps.setBoolean(1, true);
                } else {
                    ps.setBoolean(1, false);
                }
                ps.setInt(2, orderID);
                ps.execute();
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to update paid in database");
        }
    }
}

