package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderMapper;

import java.util.List;

public class OrderFacade {

    private ConnectionPool connectionPool;

    public OrderFacade() {
        connectionPool = ConnectionPool.getInstance();
    }

    public Order createOrder(int userId, List<Integer> productIds) throws DatabaseException {
        return OrderMapper.createOrder(userId, productIds, connectionPool);
    }

    public List<Order> getOrdersByUserId(int userId) throws DatabaseException {
        return OrderMapper.getOrdersByUserId(userId, connectionPool);
    }

}
