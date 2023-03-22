package dat.backend.model.entities;

public class Cupcake {

    private CupcakeTopping topping;
    private CupcakeBase base;
    private float fullPrice;

    Cupcake(CupcakeTopping topping, CupcakeBase base){
        this.topping = topping;
        this.base = base;
        this.fullPrice = calculateFullPrice();
    }

    public CupcakeTopping getTopping() {
        return topping;
    }

    public CupcakeBase getBase() {
        return base;
    }

    public float getFullPrice() {
        return fullPrice;
    }

    private float calculateFullPrice(){
        return topping.getPrice() + base.getPrice();
    }
}