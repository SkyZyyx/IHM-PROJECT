import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pageLogin extends JFrame implements ActionListener {
    private JPanel panel3, panel1, panel2,mainPanel;
    private JTextField tuserName;
    private JPasswordField tpassWord;
    private JLabel lUserName, lPassWord, imageLabel,title;
    private JButton login, cancel;
    private int borderRadius = 25;
    private MatteBorder customBorder = new MatteBorder(
            2, 2, 2, 2, // Border thickness
            new Color(0, 86, 179)); // Color: #3498db

    private CompoundBorder compoundBorder = new CompoundBorder(
            customBorder,
            new EmptyBorder(borderRadius, borderRadius, borderRadius, borderRadius)
    );

    public pageLogin(String iconPath) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Login");
        setBounds(300, 90, 900, 600);
        mainPanel= new JPanel();
        panel3 = new JPanel(new FlowLayout());  
        setContentPane(mainPanel);

        // Panel 1
        panel1 = new JPanel(null);  // Use null layout for precise component placement
        panel1.setBackground(Color.WHITE);

        title = new JLabel("<html><span style='color: #0056b3; font-size:22px;'>Log</span>-in</html>");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 40);
        title.setLocation(10, 10);
        title.setForeground(Color.BLACK);
        panel1.add(title);

        lUserName = new JLabel("Username:");
        lUserName.setFont(new Font("Arial", Font.PLAIN, 20));
        lUserName.setSize(100, 30);
        lUserName.setForeground(Color.BLACK);
        lUserName.setLocation(20, 80);
        panel1.add(lUserName);

        tuserName = new JTextField();
        tuserName.setFont(new Font("Arial", Font.PLAIN, 20));
        tuserName.setSize(220, 30);
        tuserName.setLocation(120, 80);
        panel1.add(tuserName);

        lPassWord = new JLabel("Password:");  // Fix label text
        lPassWord.setFont(new Font("Arial", Font.PLAIN, 20));
        lPassWord.setSize(100, 30);
        lPassWord.setForeground(Color.BLACK);
        lPassWord.setLocation(20, 130);  // Adjust label location
        panel1.add(lPassWord);

        tpassWord = new JPasswordField();
        tpassWord.setFont(new Font("Arial", Font.PLAIN, 20));
        tpassWord.setSize(220, 30);
        tpassWord.setLocation(120, 130);  // Adjust text field location
        panel1.add(tpassWord);

        login = new JButton("Login");  // Fix button text
        login.setFont(new Font("Arial", Font.PLAIN, 15));
        login.setSize(150, 30);
        login.setLocation(20, 180);
        login.setBackground(new Color(0, 86, 179));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        panel1.add(login);
        //Action performed
      login.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
        String username = tuserName.getText();
        char[] password = tpassWord.getPassword();
        String passwordStr = new String(password);
        if (username.equals("admin") && passwordStr.equals("admin")) {
            Homepage home = new Homepage(iconPath);
            home.setVisible(true);
            dispose();
        } else if (username.equals("user") && passwordStr.equals("user")){
            userPage home = new userPage(iconPath);
            home.setVisible(true);
            dispose();
        }else {
            JOptionPane.showMessageDialog(null, "Wrong Username And password retry!");
        }
    }
});


        cancel = new JButton("Cancel");  // Fix button text
        cancel.setFont(new Font("Arial", Font.PLAIN, 15));
        cancel.setSize(150, 30);
        cancel.setLocation(200, 180);  // Adjust button location
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        cancel.setFocusPainted(false);
        panel1.add(cancel);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt){
                dispose();
            }
        });







        panel1.setBorder(compoundBorder);
        panel1.setPreferredSize(new Dimension(500, 270));

        panel3.add(panel1);

        // Panel 2
        panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon("./images/8531854.jpg");
        Image image = imageIcon.getImage();
        int labelWidth = 200;
        int labelHeight = 200;
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setSize(labelWidth, labelHeight);
        panel2.setBorder(compoundBorder);

        panel2.add(imageLabel);
        panel3.add(panel2);
        panel3.setLocation(400,200);
        panel3.setBackground(new Color(0, 86, 179));
        int topMargin = 80;
        mainPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, topMargin, topMargin, topMargin));
        mainPanel.add(panel3);
        mainPanel.setBackground(Color.WHITE);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

public class Login{
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new pageLogin("./images/icons8-university-50.png");
        });
    }
}