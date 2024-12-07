import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


import java.awt.*;
import java.io.*;

public class TreeActions {
    private JFrame frame;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private ComputerStore store;

    public TreeActions(JFrame frame, JTree tree, DefaultTreeModel treeModel, ComputerStore store) {
        this.frame = frame;
        this.tree = tree;
        this.treeModel = treeModel;
        this.store = store;
    }

    public void addNode() {
        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if (selectedNode.getUserObject() instanceof String) {
                String nodeName = (String) selectedNode.getUserObject();
                if (nodeName.equals(store.getName())) {
                    addDepartment(selectedNode);
                } else {
                    addCategoryOrProduct(selectedNode, nodeName);
                }
            }
        }
    }

    private void addDepartment(DefaultMutableTreeNode selectedNode) {
        AddDepartmentDialog dialog = new AddDepartmentDialog(frame);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Department newDepartment = new Department(String.valueOf(store.getDepartments().size() + 1), dialog.getDepartmentName(), dialog.getManager());
            store.addDepartment(newDepartment);
            addNodeToTree(selectedNode, newDepartment.getName());
        }
    }

    private void addCategoryOrProduct(DefaultMutableTreeNode selectedNode, String nodeName) {
        for (Department department : store.getDepartments()) {
            if (nodeName.equals(department.getName())) {
                addCategory(selectedNode, department);
                return;
            }
            for (Category category : department.getCategories()) {
                if (nodeName.equals(category.getName())) {
                    addProduct(selectedNode, category);
                    return;
                }
            }
        }
    }

    private void addCategory(DefaultMutableTreeNode selectedNode, Department department) {
        AddCategoryDialog dialog = new AddCategoryDialog(frame);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Category newCategory = new Category(String.valueOf(department.getCategories().size() + 1), dialog.getCategoryName(), dialog.getResponsiblePerson());
            department.addCategory(newCategory);
            addNodeToTree(selectedNode, newCategory.getName());
        }
    }

    private void addProduct(DefaultMutableTreeNode selectedNode, Category category) {
        AddProductDialog dialog = new AddProductDialog(frame);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Product newProduct = new Product(String.valueOf(category.getProducts().size() + 1), dialog.getProductName(), dialog.getPrice());
            category.addProduct(newProduct);
            addNodeToTree(selectedNode, newProduct.getName() + " - $" + newProduct.getPrice());
        }
    }

    private void addNodeToTree(DefaultMutableTreeNode parentNode, String nodeName) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
        parentNode.add(newNode);
        treeModel.reload(parentNode);
    }

    public void editNode() {
        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if (selectedNode.getUserObject() instanceof String) {
                String nodeName = (String) selectedNode.getUserObject();
                if (selectedNode.isRoot()) {
                    editRoot(selectedNode, nodeName);
                } else {
                    editDepartmentOrCategoryOrProduct(selectedNode, nodeName);
                }
            }
        }
    }
    
    private void editRoot(DefaultMutableTreeNode selectedNode, String nodeName) {
        EditStoreDialog dialog = new EditStoreDialog(frame, store.getName(), store.getAddress());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            store.setName(dialog.getStoreName());
            store.setAddress(dialog.getAddress());
            updateNodeInTree(selectedNode, dialog.getStoreName());
        }
    }

    private void editDepartmentOrCategoryOrProduct(DefaultMutableTreeNode selectedNode, String nodeName) {
        for (Department department : store.getDepartments()) {
            if (nodeName.equals(department.getName())) {
                editDepartment(selectedNode, department);
                return;
            }
            for (Category category : department.getCategories()) {
                if (nodeName.equals(category.getName())) {
                    editCategory(selectedNode, category);
                    return;
                }
                for (Product product : category.getProducts()) {
                    if (nodeName.startsWith(product.getName())) {
                        editProduct(selectedNode, product);
                        return;
                    }
                }
            }
        }
    }

    private void editDepartment(DefaultMutableTreeNode selectedNode, Department department) {
        AddDepartmentDialog dialog = new AddDepartmentDialog(frame);
        dialog.nameField.setText(department.getName());
        dialog.managerField.setText(department.getManager());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            department.setName(dialog.getDepartmentName());
            department.setManager(dialog.getManager());
            updateNodeInTree(selectedNode, department.getName());
        }
    }

    private void editCategory(DefaultMutableTreeNode selectedNode, Category category) {
        AddCategoryDialog dialog = new AddCategoryDialog(frame);
        dialog.nameField.setText(category.getName());
        dialog.responsiblePersonField.setText(category.getResponsiblePerson());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            category.setName(dialog.getCategoryName());
            category.setResponsiblePerson(dialog.getResponsiblePerson());
            updateNodeInTree(selectedNode, category.getName());
        }
    }

    private void editProduct(DefaultMutableTreeNode selectedNode, Product product) {
        AddProductDialog dialog = new AddProductDialog(frame);
        dialog.nameField.setText(product.getName());
        dialog.priceField.setText(String.valueOf(product.getPrice()));
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            product.setName(dialog.getProductName());
            product.setPrice(dialog.getPrice());
            updateNodeInTree(selectedNode, product.getName() + " - $" + product.getPrice());
        }
    }

    private void updateNodeInTree(DefaultMutableTreeNode node, String nodeName) {
        node.setUserObject(nodeName);
        treeModel.reload(node);
    }

    public void removeNode() {
        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            if (selectedNode.getParent() != null) {
                removeNodeFromStore(selectedNode);
                treeModel.removeNodeFromParent(selectedNode);
            }
        }
    }

    private void removeNodeFromStore(DefaultMutableTreeNode node) {
        if (node.getUserObject() instanceof String) {
            String nodeName = (String) node.getUserObject();
            if (nodeName.equals(store.getName())) {
                return;
            }
            for (Department department : store.getDepartments()) {
                if (nodeName.equals(department.getName())) {
                    store.getDepartments().remove(department);
                    return;
                }
                for (Category category : department.getCategories()) {
                    if (nodeName.equals(category.getName())) {
                        department.getCategories().remove(category);
                        return;
                    }
                    for (Product product : category.getProducts()) {
                        if (nodeName.startsWith(product.getName())) {
                            category.getProducts().remove(product);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Save files", "save"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Set to project directory
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".save")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".save");
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                oos.writeObject(store);
                JOptionPane.showMessageDialog(frame, "File saved successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving file: " + e.getMessage());
            }
        }
    }

    public void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Save files", "save"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"))); // Set to project directory
        int userSelection = fileChooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToOpen))) {
                ComputerStore loadedStore = (ComputerStore) ois.readObject();
                clearTree();
                store = loadedStore;
                reloadTree();
                JOptionPane.showMessageDialog(frame, "File loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Error loading file: " + e.getMessage());
            }
        }
    }

    private void clearTree() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if (root != null) {
            root.removeAllChildren();
            treeModel.reload();
        }
    }

    private void reloadTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(store.getName());
        for (Department department : store.getDepartments()) {
            DefaultMutableTreeNode departmentNode = new DefaultMutableTreeNode(department.getName());
            for (Category category : department.getCategories()) {
                DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(category.getName());
                for (Product product : category.getProducts()) {
                    DefaultMutableTreeNode productNode = new DefaultMutableTreeNode(product.getName() + " - $" + product.getPrice());
                    categoryNode.add(productNode);
                }
                departmentNode.add(categoryNode);
            }
            root.add(departmentNode);
        }
        treeModel.setRoot(root);
        expandAllNodes(tree, 0, tree.getRowCount());
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public void calculateTotalSum() {
        double totalSum = store.getDepartments().stream()
                .flatMap(department -> department.getCategories().stream())
                .flatMap(category -> category.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
        JOptionPane.showMessageDialog(frame, "Total sum of all products: $" + totalSum);
    }

    public void calculateSelectedBranchSum() {
        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            double branchSum = calculateBranchSum(selectedNode);
            JOptionPane.showMessageDialog(frame, "Total sum of selected branch: $" + branchSum);
        }
    }

    private double calculateBranchSum(DefaultMutableTreeNode node) {
        double sum = 0;
        if (node.getUserObject() instanceof String) {
            String nodeName = (String) node.getUserObject();
            for (Department department : store.getDepartments()) {
                if (nodeName.equals(department.getName())) {
                    return calculateDepartmentSum(department);
                }
                for (Category category : department.getCategories()) {
                    if (nodeName.equals(category.getName())) {
                        return calculateCategorySum(category);
                    }
                    for (Product product : category.getProducts()) {
                        if (nodeName.equals(product.getName() + " - $" + product.getPrice())) {
                            return product.getPrice();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            sum += calculateBranchSum((DefaultMutableTreeNode) node.getChildAt(i));
        }
        return sum;
    }

    private double calculateDepartmentSum(Department department) {
        return department.getCategories().stream()
                .mapToDouble(this::calculateCategorySum)
                .sum();
    }

    private double calculateCategorySum(Category category) {
        return category.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void findMostExpensiveProduct() {
        Product mostExpensiveProduct = null;
        Category mostExpensiveCategory = null;
        Department mostExpensiveDepartment = null;

        for (Department department : store.getDepartments()) {
            for (Category category : department.getCategories()) {
                for (Product product : category.getProducts()) {
                    if (mostExpensiveProduct == null || product.getPrice() > mostExpensiveProduct.getPrice()) {
                        mostExpensiveProduct = product;
                        mostExpensiveCategory = category;
                        mostExpensiveDepartment = department;
                    }
                }
            }
        }

        if (mostExpensiveProduct != null) {
            String message = String.format("Department: %s\nCategory: %s\nProduct: %s - $%.2f",
                    mostExpensiveDepartment.getName(),
                    mostExpensiveCategory.getName(),
                    mostExpensiveProduct.getName(),
                    mostExpensiveProduct.getPrice());
            JOptionPane.showMessageDialog(frame, message, "Most Expensive Product", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "No products found.", "Most Expensive Product", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showTechnicalSpecification() {
        String ts = "Технічне завдання\n\n" +
                "1. Мета проекту\n" +
                "Розробити програму для управління комп'ютерним магазином, яка дозволяє додавати, редагувати та \n" + 
                "видаляти відділи, категорії та продукти, а також зберігати та завантажувати дані з файлу.\n\n" +
                "2. Функціональні вимоги\n" +
                "2.1. Додавання, редагування та видалення відділів, категорій та продуктів.\n" +
                "2.2. Збереження даних магазину у файл та завантаження з файлу.\n" +
                "2.3. Обчислення загальної суми всіх продуктів та суми вибраної гілки.\n" +
                "2.4. Виведення інформації про розробника.\n" +
                "2.5. Виведення технічного завдання.\n" +
                "2.6. Виведення діаграми класів моделі предметної області.\n\n" +
                "3. Нефункціональні вимоги\n" +
                "3.1. Інтерфейс користувача повинен бути реалізований за допомогою Swing.\n" +
                "3.2. Дані повинні зберігатися у форматі серіалізованих об'єктів Java.\n\n" +
                "4. Структура даних\n" +
                "4.1. ComputerStore: назва магазину, адреса, список відділів.\n" +
                "4.2. Department: назва відділу, відповідальний менеджер, список категорій.\n" +
                "4.3. Category: назва категорії, відповідальна особа, список продуктів.\n" +
                "4.4. Product: ID товару, назва, ціна.\n\n" +
                "5. Розробник\n" +
                "Максим Сірик, група KI-233, email: maxsiryk@stu.cn.ua";

    JFrame tsFrame = new JFrame("Technical Specification");
    tsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JTextArea textArea = new JTextArea(ts);
    textArea.setEditable(false);
    tsFrame.getContentPane().add(new JScrollPane(textArea));

    tsFrame.setSize(600, 500);

    tsFrame.setLocation(frame.getX() + frame.getWidth(), frame.getY());

    tsFrame.setVisible(true);
        
    }

    public void showClassDiagram() {
        ImageIcon diagramIcon = new ImageIcon(getClass().getResource("/d.png"));
        JLabel diagramLabel = new JLabel(diagramIcon);
    
        JScrollPane scrollPane = new JScrollPane(diagramLabel);
        scrollPane.setPreferredSize(new Dimension(800, 600));
    
        JFrame diagramFrame = new JFrame("Class Diagram");
        diagramFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        diagramFrame.getContentPane().add(scrollPane);
    
        diagramFrame.setSize(800, 600);
        diagramFrame.setLocation(frame.getX() + frame.getWidth(), frame.getY());
        diagramFrame.setVisible(true);
    
        scrollPane.addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                int notches = e.getWheelRotation();
                double scale = 1.0;
                if (notches < 0) {
                    scale = 1.1;
                } else {
                    scale = 0.9;
                }
                int width = (int) (diagramLabel.getIcon().getIconWidth() * scale);
                int height = (int) (diagramLabel.getIcon().getIconHeight() * scale);
                Image scaledImage = diagramIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                diagramLabel.setIcon(new ImageIcon(scaledImage));
            }
        });
    }
}