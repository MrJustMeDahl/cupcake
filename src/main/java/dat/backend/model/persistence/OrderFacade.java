package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;


import java.util.List;

public class OrderFacade {

    public static Order createOrder(int userId, List<Integer> productIds, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(userId, productIds, connectionPool);
    }

    public static List<Order> getOrdersByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getOrdersByUserId(userId, connectionPool);
    }

}
