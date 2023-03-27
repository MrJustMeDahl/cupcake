package dat.backend.model.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket extends Order{

    ArrayList<Cupcake> itemsInBasket = new ArrayList<>();

    public ArrayList<Cupcake> getItemsInBasket() {
        return itemsInBasket;
    }

    public void addItemToBasket (Cupcake cupcake){
        itemsInBasket.add(cupcake);
    }

    public ShoppingBasket(int orderID, int userID, List<Cupcake> cupcakes, boolean ordered, boolean paid){
        super(orderID, userID, cupcakes, ordered, paid);
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
