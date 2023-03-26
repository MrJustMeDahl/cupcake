package dat.backend.model.entities;

public abstract class CupcakePart {

    private String flavor;
    private float price;

    public CupcakePart(String flavor, float price){
        this.flavor = flavor;
        this.price = price;
    }

    public String getFlavor() {
        return flavor;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return flavor;
    }
}
