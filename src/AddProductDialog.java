
import javax.swing.*;
import java.awt.*;

public class AddProductDialog extends JDialog {
    JTextField nameField;
    JTextField priceField;
    private boolean confirmed;

    public AddProductDialog(Frame owner) {
        super(owner, "Add/Edit Product", true);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Product Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

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

    public String getProductName() {
        return nameField.getText();
    }

    public double getPrice() {
        try {
            return Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}