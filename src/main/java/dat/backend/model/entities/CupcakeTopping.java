package dat.backend.model.entities;

public class CupcakeTopping extends CupcakePart {

    private int toppingID;

    public CupcakeTopping(String flavor, float price, int toppingID){
        super(flavor, price);
        this.toppingID = toppingID;
    }
}
