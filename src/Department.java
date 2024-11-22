// import java.awt.Window;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Department extends BaseEntity implements Serializable {
    private String manager;
    private List<Category> categories;

    public Department(String id, String name, String manager) {
        super(id, name);
        this.manager = manager;
        this.categories = new ArrayList<>();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}