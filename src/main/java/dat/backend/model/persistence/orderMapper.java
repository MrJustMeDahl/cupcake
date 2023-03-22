package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class OrderMapper {

    static Order createOrder(int userId, List<Integer> productIds, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id) VALUES (?)";
        int orderId;

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();

                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new DatabaseException("Failed to create order in the database");
                }
            }

            for (Integer productId : productIds) {
                String orderItemSql = "INSERT INTO order_items (order_id, product_id) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(orderItemSql)) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, productId);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order. Something went wrong with the database");
        }

        return new Order(orderId, userId, productIds);
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
