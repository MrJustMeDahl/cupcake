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
        String sql = "INSERT INTO cupcake.order (price, user_id) VALUES (?, ?)";
        int orderId;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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


        return ;
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
        String sqlCupcakebase = "SELECT * FROM cupcake.cupcakeview WHERE orderId = ?";
        try(Connection connection = connectionPool.getConnection()){
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
        } catch(SQLException e){
            e.printStackTrace();
        }
        order = new Order(id, userId, cupcakes, isPaid, isOrdered);
        return order;
    }

    static List<Order> getOrdersByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT orders.id AS order_id, order_items.product_id AS product_id FROM orders INNER JOIN order_items ON orders.id = order_items.order_id WHERE orders.user_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int productId = rs.getInt("product_id");
                    Order order = orders.stream().filter(o -> o.getId() == orderId).findFirst().orElse(null);

                    if (order == null) {
                        order = new Order(orderId, userId, new ArrayList<>());
                        orders.add(order);
                    }
                    order.getProductIds().add(productId);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error retrieving orders. Something went wrong with the database");
        }

        return orders;
    }
}

