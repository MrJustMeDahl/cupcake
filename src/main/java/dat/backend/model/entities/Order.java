package dat.backend.model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private int userId;
    private List<Integer> productIds;

    public Order(int id, int userId, List<Integer> productIds) {
        this.id = id;
        this.userId = userId;
        this.productIds = productIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public void addProductId(int productId) {
        if (productIds == null) {
            productIds = new ArrayList<>();
        }
        productIds.add(productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && Objects.equals(productIds, order.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productIds);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", productIds=" + productIds +
                '}';
    }
}
