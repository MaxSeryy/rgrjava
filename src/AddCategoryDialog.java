import javax.swing.*;
import java.awt.*;

public class AddCategoryDialog extends JDialog {
    JTextField nameField;
    JTextField responsiblePersonField;
    private boolean confirmed;

    public AddCategoryDialog(Frame owner) {
        super(owner, "Add/Edit Category", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Category Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Responsible Person:"));
        responsiblePersonField = new JTextField();
        add(responsiblePersonField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || responsiblePersonField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                confirmed = true;
                setVisible(false);
            }
        });
        add(confirmButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));
        add(cancelButton);

        pack();

        int x = owner.getX() + owner.getWidth();
        int y = owner.getY();
        setLocation(x, y);
    }

    public String getCategoryName() {
        return nameField.getText();
    }

    public String getResponsiblePerson() {
        return responsiblePersonField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}