
package dat.backend.model.persistence;

import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;


    public class CupcakeToppingFacade {


        public static List<CupcakeTopping> getAllToppings(ConnectionPool connectionPool){
            return CupcakeToppingMapper.getAllToppings(connectionPool);
        }

        public static void addToppingflavor(String flavor, float price, ConnectionPool connectionPool)throws DatabaseException {
            CupcakeToppingMapper.addToppingFlavor(flavor, price, connectionPool);
        }

        public static void editTopping (int cupcaketoppingId, String name, float price, ConnectionPool connectionPool){
            CupcakeToppingMapper.editTopping(cupcaketoppingId, name, price, connectionPool);
        }

        public static void deleteTopping (int cupcaketoppingId, ConnectionPool connectionPool){
            CupcakeToppingMapper.deleteTopping(cupcaketoppingId, connectionPool);
        }

    }
