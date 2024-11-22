import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerStore  implements Serializable{
    private String name;
    private String address;
    private List<Department> departments;

    public ComputerStore(String name, String address) {
        this.name = name;
        this.address = address;
        this.departments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }
}