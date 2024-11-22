
import javax.swing.*;
import java.awt.*;

public class AddDepartmentDialog extends JDialog {
    JTextField nameField;
    JTextField managerField;
    private boolean confirmed;

    public AddDepartmentDialog(Frame owner) {
        super(owner, "Add/Edit Department", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Department Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Manager:"));
        managerField = new JTextField();
        add(managerField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
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

    public String getDepartmentName() {
        return nameField.getText();
    }

    public String getManager() {
        return managerField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}