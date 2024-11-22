import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

public class InfoFrame {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InfoFrame window = new InfoFrame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public InfoFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 260, 230);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Developer Info");
        frame.getContentPane().setLayout(new BorderLayout());
    
        JTextArea txtrName = new JTextArea();
        txtrName.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtrName.setEditable(false);
        txtrName.setText("Name: Max Siryk\r\nGroup: KI-233\r\nEmail: maxsiryk@stu.cn.ua");
    
        JScrollPane scrollPane = new JScrollPane(txtrName);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BorderLayout());
    
        JLabel photoLabel = new JLabel();
        ImageIcon photo = new ImageIcon(getClass().getResource("foto.jpg"));
        Image img = photo.getImage();
    
        int newWidth = 250;
        int newHeight = 250;
    
        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(scaledImg));
        photoLabel.setHorizontalAlignment(JLabel.CENTER);
        photoPanel.add(photoLabel, BorderLayout.CENTER);
    
        frame.getContentPane().add(photoPanel, BorderLayout.SOUTH);
        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setLocation(int x, int y) {
        frame.setLocation(x, y);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
