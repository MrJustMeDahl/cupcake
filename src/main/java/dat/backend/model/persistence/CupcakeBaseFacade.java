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

    public static void editBase (int cupcakebase_id, String name, float price, ConnectionPool connectionPool){
        CupcakeBaseMapper.editBase(cupcakebase_id, name, price, connectionPool);
    }

    public static void deleteBase (int cupcakebase_id, ConnectionPool connectionPool){
        CupcakeBaseMapper.deleteBase(cupcakebase_id, connectionPool);
    }

}
