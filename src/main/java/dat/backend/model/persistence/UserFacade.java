package dat.backend.model.persistence;

import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

public class UserFacade
{
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.login(email, password, connectionPool);
    }

    public static User createUser(String name, String username, String password, float balance, String role, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.createUser(name, username, password, connectionPool);
    }

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException{
        return UserMapper.getAllUsers(connectionPool);
    }

    public static void updateBalance(float amount, int userID, ConnectionPool connectionPool) throws DatabaseException{
        UserMapper.updateBalance(amount, userID, connectionPool);
    }
}
