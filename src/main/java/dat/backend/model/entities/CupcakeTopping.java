package dat.backend.model.entities;

public class CupcakeTopping {

    private int cupcaketoppingId;
    private String flavortopping;
    private float pricetopping;

    public CupcakeTopping(int cupcaketoppingId, String flavortopping, float pricetopping) {
        this.cupcaketoppingId = cupcaketoppingId;
        this.flavortopping = flavortopping;
        this.pricetopping = pricetopping;
    }

    public int getCupcaketoppingId() {
        return cupcaketoppingId;
    }

    public String getFlavortopping() {
        return flavortopping;
    }

    public float getPricetopping() {
        return pricetopping;
    }
}
