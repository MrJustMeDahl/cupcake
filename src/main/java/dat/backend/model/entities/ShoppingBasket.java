package dat.backend.model.entities;

import java.sql.Timestamp;
import java.util.List;

public class ShoppingBasket extends Order{

    public ShoppingBasket(int orderID, List<Cupcake> cupcakes, float totalPrice, boolean ordered, boolean paid, Timestamp timestamp){
        super(orderID, cupcakes, totalPrice, ordered, paid, timestamp);
    }

    public boolean addCupcake(Cupcake cupcake){
        super.getCupcakes().add(cupcake);
        return true;
    }

    public boolean removeCupcake(Cupcake cupcake){
        if(super.getCupcakes().contains(cupcake)){
            super.getCupcakes().remove(cupcake);
            return true;
        }
        return false;
    }
}
