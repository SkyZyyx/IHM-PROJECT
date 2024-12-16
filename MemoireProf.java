import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;

class pageAjouterProf extends JFrame implements ActionListener {
    
    private JLabel title,nom,prenom,specialiteLabel,imageLabel,res;
    private JPanel panel1,main,navPanel,searchPanel;
    private JTextField tnom,tprenom,tspecialiteLabel,resadd;
  
    private JComboBox<String> encadreurCombo;
    private JButton sub,reset;

    
    public pageAjouterProf(String iconPath) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Memoire Form");
        setBounds(300, 90, 900, 600);

        
        panel1 = new JPanel();
        main = new JPanel();
        main.setLayout(new BorderLayout());
        panel1.setLayout(null);
        setContentPane(main);
       // Create a panel for the navigation bar
        navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS)); // X_AXIS for horizontal alignment
        navPanel.setBackground(new Color(42, 43, 46));

        // Logo and title on the right side
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setPreferredSize(new Dimension(50, 50)); // Adjust the size as needed
        navPanel.add(Box.createHorizontalGlue()); // Align to the right
        navPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><span style='color: #0056b3; font-size:22px;'>UMBB</span><span style='color: white;'>-School</span></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(200, 30)); // Adjust the size as needed
        navPanel.add(titleLabel);

        // Add space between title and search components
        navPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        // Search bar and button on the right side
        searchPanel = new JPanel();
        searchPanel.setBackground(new Color(42, 43, 46));

        JButton homebutton;
        homebutton = new JButton("Home");
        homebutton.setFont(new Font("Arial", Font.PLAIN, 15));
        homebutton.setSize(150, 30);
        homebutton.setBackground(Color.WHITE);
        homebutton.setForeground(Color.BLACK);
        homebutton.setFocusPainted(false); // Remove the border around the text
        homebutton.addActionListener(this);
        searchPanel.add(homebutton);
        homebutton.addActionListener(new ActionListener() {
         @Override
        public void actionPerformed(ActionEvent evt){
       
            Homepage memoireFrame = new Homepage(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
        } 
        });

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 15));
        searchButton.setSize(150, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 86, 179));
        searchPanel.add(searchButton);

        // Adding top margin to searchPanel
        int topMargin = 15;
        searchPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, 0, 0, 0));

        // Now you can add searchPanel to your navPanel
        navPanel.add(searchPanel);


        // Set the preferred size to increase the height of the navigation bar
        navPanel.setPreferredSize(new Dimension(800, 70));

        // Add the navigation panel to the main panel
        main.add(navPanel, BorderLayout.NORTH);

        // Add the navigation panel to the main panel
        main.add(navPanel, BorderLayout.NORTH);
        title = new JLabel("Encadreur-form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(450, 40);
        title.setForeground(Color.BLACK);
        panel1.add(title);

        nom = new JLabel("Nom:");
        nom.setFont(new Font("Arial", Font.PLAIN, 20));
        nom.setSize(100, 30);
        nom.setLocation(470, 100);
        nom.setForeground(Color.BLACK);
        panel1.add(nom);

        tnom = new JTextField();
        tnom.setFont(new Font("Arial", Font.PLAIN, 20));
        tnom.setSize(250, 30);
        tnom.setLocation(570, 100);
        panel1.add(tnom);

        prenom = new JLabel("Prenom:");
        prenom.setFont(new Font("Arial", Font.PLAIN, 20));
        prenom.setSize(100, 30);
        prenom.setLocation(470, 150);
        prenom.setForeground(Color.BLACK);
        panel1.add(prenom);

        tprenom = new JTextField();
        tprenom.setFont(new Font("Arial", Font.PLAIN, 20));
        tprenom.setSize(250, 30);
        tprenom.setLocation(570, 150);
        panel1.add(tprenom);

        specialiteLabel = new JLabel("Specialite:");
        specialiteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        specialiteLabel.setSize(100, 30);
        specialiteLabel.setForeground(Color.BLACK);
        specialiteLabel.setLocation(470, 200);
        
        panel1.add(specialiteLabel);

        tspecialiteLabel = new JTextField();
        tspecialiteLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        tspecialiteLabel.setSize(250, 30);
        tspecialiteLabel.setLocation(570, 200);
        panel1.add(tspecialiteLabel);
       

        sub = new JButton("Add Prof");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(150, 30);
        sub.setLocation(500, 400);
        sub.setBackground(new Color(0, 86, 179));
        sub.setForeground(Color.WHITE);
        sub.setFocusPainted(false); // Remove the border around the text
        
             sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String nomproff=tnom.getText();
                String prenomproff=tprenom.getText();
                String specialiteproff=tspecialiteLabel.getText();
                try {
                Connection connection = null;
                // Obtenez une connexion à la base de données en utilisant la classe DatabaseUtil
                connection = DatabaseConnection.getConnection();

                Statement stmt = connection.createStatement();

                if ("".equals(nomproff)) {
                    JOptionPane.showMessageDialog(null, "This Username is Already Taken, Choose Another One");
                }
                String query = "INSERT INTO enseignant(nom, prenom, specialite) VALUES ('" + nomproff + "','" + prenomproff + "','" + specialiteproff + "')";
                
                stmt.executeUpdate(query);
                System.out.println("Okay this is working !");

            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());
            } 
        }
    });
                
        panel1.add(sub);


        reset = new JButton("Clear Form");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(150, 30);
        reset.setLocation(700, 400);
        reset.setBackground(Color.WHITE);
        reset.setForeground(Color.BLACK);
        reset.setFocusPainted(false); // Remove the border around the text
        reset.addActionListener(this);
        panel1.add(reset);


       // Replace JTextArea with JLabel for displaying an image
        ImageIcon imageIcon = new ImageIcon("./images/20944362.jpg");

        // Get the image from the ImageIcon
        Image image = imageIcon.getImage();
        
        // Scale the image to fit perfectly within the JLabel while maintaining aspect ratio
        int labelWidth = 500;
        int labelHeight = 400;
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setSize(labelWidth, labelHeight);
        imageLabel.setLocation(0, 50);
        panel1.add(imageLabel);
    

        panel1.setBackground(Color.WHITE);

        main.add(panel1, BorderLayout.CENTER);
        setVisible(true);
    }

    // Rest of your code...

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data = "Title : " + tnom.getText() + "\n" +
                    "Author : " + tprenom.getText() + "\n" +
                    "Year : " + tspecialiteLabel.getText() + "\n" 
            ;

            resadd.setText(data);
            res.setText("Memoire Added Successfully..");
        } else if (e.getSource() == reset) {
            tnom.setText("");
            tprenom.setText("");
            tspecialiteLabel.setText("");
            res.setText("");
            resadd.setText("");
        }
    }
}

public class MemoireProf {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new pageAjouterProf("./images/icons8-university-50.png");
        });
    }
}