package dat.backend.model.persistence;

import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;


public class CupcakeBaseFacade {


    public static List<CupcakeBase> getAllBases(ConnectionPool connectionPool){
        return CupcakeBaseMapper.getAllBases(connectionPool);
    }

    public static void addBaseflavor(String flavor, float price, ConnectionPool connectionPool)throws DatabaseException {
        CupcakeBaseMapper.addFlavor(flavor, price, connectionPool);
    }

    public static void editBase (int cupcakebaseId, String name, float price, ConnectionPool connectionPool){
        CupcakeBaseMapper.editBase(cupcakebaseId, name, price, connectionPool);
    }

    public static void deleteBase (int cupcakebaseId, ConnectionPool connectionPool){
        CupcakeBaseMapper.deleteBase(cupcakebaseId, connectionPool);
    }

}
