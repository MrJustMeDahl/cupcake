package dat.backend.model.entities;

public class CupcakeBase {
    private int cupcakeBaseId;
    private String flavorBase;
    private float priceBase;

    public CupcakeBase(int cupcakeBaseId, String flavorBase, float priceBase) {
        this.cupcakeBaseId = cupcakeBaseId;
        this.flavorBase = flavorBase;
        this.priceBase = priceBase;
    }

    public int getCupcakebaseId() {
        return cupcakeBaseId;
    }

    public String getFlavorbase() {
        return flavorBase;
    }

    public float getPricebase() {
        return priceBase;
    }
}
