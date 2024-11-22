import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category extends BaseEntity implements Serializable {
    private List<Product> products;
    private String responsiblePerson;

    public Category(String id, String name, String responsiblePerson) {
        super(id, name);
        this.products = new ArrayList<>();
        this.responsiblePerson = responsiblePerson;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
}