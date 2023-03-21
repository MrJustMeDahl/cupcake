package dat.backend.model.entities;

import java.sql.Timestamp;
import java.util.List;

public class Order {

    private int orderID;
    private List<Cupcake> cupcakes;
    private float totalPrice;
    private boolean ordered;
    private boolean paid;
    private Timestamp timestamp;

    public Order(int orderID, List<Cupcake> cupcakes, float totalPrice, boolean ordered, boolean paid, Timestamp timestamp){
        this.orderID = orderID;
        this.cupcakes = cupcakes;
        this.totalPrice = totalPrice;
        this.ordered = ordered;
        this.paid = paid;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
