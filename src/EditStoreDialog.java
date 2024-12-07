import javax.swing.*;
import java.awt.*;

public class EditStoreDialog extends JDialog {
    private JTextField nameField;
    private JTextField addressField;
    private boolean confirmed;

    public EditStoreDialog(Frame owner, String name, String address) {
        super(owner, "Edit Store", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Store Name:"));
        nameField = new JTextField(name);
        add(nameField);

        add(new JLabel("Address:"));
        addressField = new JTextField(address);
        add(addressField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()) {
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

    public String getStoreName() {
        return nameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}