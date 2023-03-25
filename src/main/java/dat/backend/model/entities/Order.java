package dat.backend.model.entities;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int orderID;
    private int userID;
    private List<Cupcake> cupcakes;
    private float totalPrice;
    private boolean ordered;
    private boolean paid;

    public Order(int orderID, int userID, List<Cupcake> cupcakes, boolean ordered, boolean paid){
        this.orderID = orderID;
        this.userID = userID;
        this.cupcakes = cupcakes;
        this.totalPrice = setTotalPrice();
        this.ordered = ordered;
        this.paid = paid;
    }

    protected float setTotalPrice(){
        float totalPrice = 0;
        for(Cupcake c: cupcakes){
            totalPrice += c.getFullPrice();
        }
        this.totalPrice = totalPrice;
        return totalPrice;
    }

    public int getUserID(){
        return userID;
    }

    public int getOrderID() {
        return orderID;
    }

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getUserID() {
        return userID;
    }

    public boolean getOrdered() {
        return ordered;
    }

    public void setOrdered(boolean newOrdered){
        this.ordered = newOrdered;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean newPaid){
        this.paid = newPaid;
    }
}