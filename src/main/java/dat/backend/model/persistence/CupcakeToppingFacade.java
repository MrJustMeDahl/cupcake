
package dat.backend.model.persistence;

import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;


    public class CupcakeToppingFacade {


        public static List<CupcakeTopping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException{
            return CupcakeToppingMapper.getAllToppings(connectionPool);
        }

        public static CupcakeTopping getOneToppings(int toppingId, ConnectionPool connectionPool) throws DatabaseException{
            return CupcakeToppingMapper.getOneTopping(toppingId, connectionPool);
        }

        public static void addTopping(String flavor, float price, ConnectionPool connectionPool)throws DatabaseException {
            CupcakeToppingMapper.addTopping(flavor, price, connectionPool);
        }

        public static void editTopping (int cupcaketoppingId, String name, float price, ConnectionPool connectionPool) throws DatabaseException{
            CupcakeToppingMapper.editTopping(cupcaketoppingId, name, price, connectionPool);
        }

        public static void deleteTopping (int cupcaketoppingId, ConnectionPool connectionPool) throws DatabaseException{
            CupcakeToppingMapper.deleteTopping(cupcaketoppingId, connectionPool);
        }

    }
