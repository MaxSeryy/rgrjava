import java.io.Serializable;

public class Product extends BaseEntity implements Serializable {
    private double price;

    public Product(String id, String name, double price) {
        super(id, name);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}