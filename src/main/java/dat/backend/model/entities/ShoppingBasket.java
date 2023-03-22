package dat.backend.model.entities;

import java.sql.Timestamp;
import java.util.List;

public class ShoppingBasket extends Order{

    public ShoppingBasket(int orderID, List<Cupcake> cupcakes, boolean ordered, boolean paid, Timestamp timestamp){
        super(orderID, cupcakes, ordered, paid, timestamp);
    }

    public boolean addCupcake(Cupcake cupcake){
        super.getCupcakes().add(cupcake);
        super.setTotalPrice();
        return true;
    }

    public boolean removeCupcake(Cupcake cupcake){
        if(super.getCupcakes().contains(cupcake)){
            super.getCupcakes().remove(cupcake);
            super.setTotalPrice();
            return true;
        }
        return false;
    }
}
