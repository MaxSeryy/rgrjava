import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class Main {

    private JFrame frame;
    private static DefaultTreeModel treeModel;
    private static JTree tree;
    private static ComputerStore store;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Main window = new Main();
            window.frame.setVisible(true);
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Computer Store Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setMinimumSize(new Dimension(600, 450)); // Set minimum size for the frame
        frame.setLayout(new BorderLayout());

        // Create sample data
        store = SampleData.createSampleStore();

        // Create the tree nodes
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

        // Create the tree model and tree
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        JScrollPane treeScrollPane = new JScrollPane(tree);

        // Expand all nodes
        expandAllNodes(tree, 0, tree.getRowCount());

        // Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton calculateButton = new JButton("Calculate");
        JButton infoButton = new JButton("<html>Developer<br>Info</html>");
        JButton techButton = new JButton("Technical");

        // Set fixed height for buttons
        Dimension buttonSize = new Dimension(100, 50); // Width 100, Height 50
        addButton.setMaximumSize(buttonSize);
        editButton.setMaximumSize(buttonSize);
        removeButton.setMaximumSize(buttonSize);
        saveButton.setMaximumSize(buttonSize);
        loadButton.setMaximumSize(buttonSize);
        calculateButton.setMaximumSize(buttonSize);
        infoButton.setMaximumSize(buttonSize);
        techButton.setMaximumSize(buttonSize);

        // Center text in buttons
        addButton.setHorizontalAlignment(SwingConstants.CENTER);
        addButton.setVerticalAlignment(SwingConstants.CENTER);
        editButton.setHorizontalAlignment(SwingConstants.CENTER);
        editButton.setVerticalAlignment(SwingConstants.CENTER);
        removeButton.setHorizontalAlignment(SwingConstants.CENTER);
        removeButton.setVerticalAlignment(SwingConstants.CENTER);
        saveButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveButton.setVerticalAlignment(SwingConstants.CENTER);
        loadButton.setHorizontalAlignment(SwingConstants.CENTER);
        loadButton.setVerticalAlignment(SwingConstants.CENTER);
        calculateButton.setHorizontalAlignment(SwingConstants.CENTER);
        calculateButton.setVerticalAlignment(SwingConstants.CENTER);
        infoButton.setHorizontalAlignment(SwingConstants.CENTER);
        infoButton.setVerticalAlignment(SwingConstants.CENTER);
        techButton.setHorizontalAlignment(SwingConstants.CENTER);
        techButton.setVerticalAlignment(SwingConstants.CENTER);

        TreeActions treeActions = new TreeActions(frame, tree, treeModel, store);

        addButton.addActionListener(e -> treeActions.addNode());

        editButton.addActionListener(e -> treeActions.editNode());

        removeButton.addActionListener(e -> treeActions.removeNode());

        saveButton.addActionListener(e -> treeActions.saveToFile());

        loadButton.addActionListener(e -> treeActions.loadFromFile());

        calculateButton.addActionListener(e -> {
            JPopupMenu calculateMenu = new JPopupMenu();
            JMenuItem totalSumItem = new JMenuItem("Total Sum");
            JMenuItem selectedBranchSumItem = new JMenuItem("Selected Branch Sum");

            totalSumItem.addActionListener(ev -> treeActions.calculateTotalSum());
            totalSumItem.setLocation(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
            selectedBranchSumItem.addActionListener(ev -> treeActions.calculateSelectedBranchSum());
            selectedBranchSumItem.setLocation(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);

            calculateMenu.add(totalSumItem);
            calculateMenu.add(selectedBranchSumItem);
            calculateMenu.show(calculateButton, calculateButton.getWidth() / 2, calculateButton.getHeight() / 2);
        });

        infoButton.addActionListener(e -> {
            InfoFrame infoFrame = new InfoFrame();
            infoFrame.setLocation(frame.getX() + frame.getWidth(), frame.getY());
            infoFrame.setVisible(true);
        });

        // Create the technical menu
        JPopupMenu techMenu = new JPopupMenu();
        JMenuItem tsMenuItem = new JMenuItem("Technical Specification");
        JMenuItem classDiagramMenuItem = new JMenuItem("Class Diagram");

        tsMenuItem.addActionListener(e -> treeActions.showTechnicalSpecification());
        classDiagramMenuItem.addActionListener(e -> treeActions.showClassDiagram());

        techMenu.add(tsMenuItem);
        techMenu.add(classDiagramMenuItem);

        techButton.addActionListener(e -> techMenu.show(techButton, techButton.getWidth() / 2, techButton.getHeight() / 2));

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(removeButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(calculateButton);
        controlPanel.add(infoButton);
        controlPanel.add(techButton);

        frame.add(treeScrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.EAST);
    }

    private static void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
}