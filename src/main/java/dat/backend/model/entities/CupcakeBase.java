package dat.backend.model.entities;

public class CupcakeBase extends CupcakePart{

    private int baseID;

    public CupcakeBase(String flavor, float price, int baseID){
        super(flavor, price);
        this.baseID = baseID;
    }

    public int getBaseID() {
        return baseID;
    }


}
