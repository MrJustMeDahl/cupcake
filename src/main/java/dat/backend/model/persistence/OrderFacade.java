package dat.backend.model.persistence;

import dat.backend.model.entities.Cupcake;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;


import java.util.List;

public class OrderFacade {

    public static Order createOrder(int userId, Cupcake cupcake, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(cupcake, userId, connectionPool);
    }

    public static Order getOrderByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.getOrderByOrderId(orderId, connectionPool);
    }

    public static List<Order> getOrdersByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getOrdersByUserId(userId, connectionPool);
    }

    public static void deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.deleteOrder(orderID, connectionPool);
    }

    public static void updateOrderPaid(int orderID, boolean paid, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateOrderPaid(orderID, paid, connectionPool);
    }
    public static void updateOrderOrdered(int orderID, boolean ordered, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateOrderOrdered(orderID, ordered, connectionPool);
    }
    public  static void insertCupcakeForOrder(int orderId, Cupcake cupcake, ConnectionPool connectionpool) throws DatabaseException{
        OrderMapper.insertCupcakeForOrder(orderId, cupcake, connectionpool);
    }

    public static void updateTotalBalanceForOrder(int activeOrderId, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateTotalBalanceForOrder(activeOrderId, connectionPool);
    }

    public static void removeCupcakeFromOrder(int cupcakeID, ConnectionPool connectionPool)throws DatabaseException {
        OrderMapper.removeCupcakeFromOrder(cupcakeID, connectionPool);
    }
}
