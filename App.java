import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;

class MyFrame extends JFrame implements ActionListener {
    
    private JLabel title,memoireTitle,author,yearLabel,summary,encadreurLabel,imageLabel,res;
    private JPanel panel1,main,navPanel,searchPanel;
    private JTextField tmemoireTitle,tauthor,tyear,tsummary,resadd;
    private JComboBox<String> encadreurCombo; 
    private JButton sub,reset;

    private String[] professors = {"Sélectionnez l'encadreur"};
    

    
    public MyFrame(String iconPath) {
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
        homebutton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
       
            userPage memoireFrame = new userPage(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
        } 
        
    }
);


        searchPanel.add(homebutton);

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


        title = new JLabel("Memoire-form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(20, 40);
        title.setForeground(Color.BLACK);
        panel1.add(title);

        memoireTitle = new JLabel("Title:");
        memoireTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        memoireTitle.setSize(100, 30);
        memoireTitle.setLocation(20, 100);
        memoireTitle.setForeground(Color.BLACK);
        panel1.add(memoireTitle);

        tmemoireTitle = new JTextField();
        tmemoireTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        tmemoireTitle.setSize(270, 30);
        tmemoireTitle.setLocation(120, 100);
        panel1.add(tmemoireTitle);

        author = new JLabel("Author:");
        author.setFont(new Font("Arial", Font.PLAIN, 20));
        author.setSize(100, 30);
        author.setLocation(20, 150);
        author.setForeground(Color.BLACK);
        panel1.add(author);

        tauthor = new JTextField();
        tauthor.setFont(new Font("Arial", Font.PLAIN, 20));
        tauthor.setSize(270, 30);
        tauthor.setLocation(120, 150);
        panel1.add(tauthor);

        yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        yearLabel.setSize(100, 30);
        yearLabel.setForeground(Color.BLACK);
        yearLabel.setLocation(20, 200);
        panel1.add(yearLabel);

        tyear = new JTextField();
        tyear.setFont(new Font("Arial", Font.PLAIN, 20));
        tyear.setSize(270, 30);
        tyear.setLocation(120, 200);
        panel1.add(tyear);
        
        summary = new JLabel("Summary:");
        summary.setFont(new Font("Arial", Font.PLAIN, 20));
        summary.setSize(100, 30);
        summary.setForeground(Color.BLACK);
        summary.setLocation(20, 250);
        panel1.add(summary);

        tsummary = new JTextField();
        tsummary.setFont(new Font("Arial", Font.PLAIN, 20));
        tsummary.setSize(270, 30);
        tsummary.setLocation(120, 250);
        tsummary.setForeground(Color.BLACK);
        panel1.add(tsummary);

        encadreurLabel = new JLabel("Encadreur:");
        encadreurLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        encadreurLabel.setSize(100, 30);
        encadreurLabel.setForeground(Color.BLACK);
        encadreurLabel.setLocation(20, 300);
        panel1.add(encadreurLabel);

        encadreurCombo = new JComboBox<>(professors);
        encadreurCombo.setFont(new Font("Arial", Font.PLAIN, 15));
        encadreurCombo.setSize(270, 30);
        encadreurCombo.setLocation(120, 300);
        panel1.add(encadreurCombo);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/memoiredb","root","2003");
            Statement stmt=con.createStatement();
            ResultSet rs;
            String query ="SELECT nom,prenom FROM enseignant";
            rs=stmt.executeQuery(query);

            while(rs.next()){
                String name=rs.getString("nom");
                String prenom=rs.getString("prenom");
                encadreurCombo.addItem(name+" "+prenom);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }


        sub = new JButton("Add Memoire");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(150, 30);
        sub.setLocation(20, 400);
        sub.setBackground(new Color(0, 86, 179));
        sub.setForeground(Color.WHITE);
        sub.setFocusPainted(false); // Remove the border around the text
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String titles=tmemoireTitle.getText();
                String author=tauthor.getText();
                String year = tyear.getText();
                System.out.println(year);
                String summary=tsummary.getText();
                String encadreur=(String) encadreurCombo.getSelectedItem();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    String query;
                    Connection con=DatabaseConnection.getConnection();
                    Statement stmt=con.createStatement();

                    if ("".equals(tmemoireTitle.getText())) {
                         JOptionPane.showMessageDialog(null, "This Username is Already Taken, Choose Another One");
                    }
                    query="INSERT INTO memoires(title,author,year,summary,encadreur) VALUES ('"+titles+"','"+author+"','"+year+"','"+summary+"','"+encadreur+"')";
                    stmt.executeUpdate(query);
                    System.out.println("Okay this is working !");
                        con.close();
                } catch (Exception e) {
                    System.out.println("Error"+e.getMessage());
                }
            }
        });
        panel1.add(sub);


        reset = new JButton("Clear Form");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(150, 30);
        reset.setLocation(200, 400);
        reset.setBackground(Color.WHITE);
        reset.setForeground(Color.BLACK);
        reset.setFocusPainted(false); // Remove the border around the text
        reset.addActionListener(this);
        panel1.add(reset);


       // Replace JTextArea with JLabel for displaying an image
        ImageIcon imageIcon = new ImageIcon("./images/illustration.jpg");

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
        imageLabel.setLocation(400, 50);
        panel1.add(imageLabel);

        panel1.setBackground(Color.WHITE);

        main.add(panel1, BorderLayout.CENTER);
        setVisible(true);
    }

    // Rest of your code...

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data = "Title : " + tmemoireTitle.getText() + "\n" +
                    "Author : " + tauthor.getText() + "\n" +
                    "Year : " + tyear.getText() + "\n" +
                    "Summary : " + tsummary.getText() + "\n";

            resadd.setText(data);
            res.setText("Memoire Added Successfully..");
        } else if (e.getSource() == reset) {
            tmemoireTitle.setText("");
            tauthor.setText("");
            tyear.setText("");
            tsummary.setText("");
            encadreurCombo.setSelectedIndex(0);
            res.setText("");
            resadd.setText("");
        }
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new MyFrame("./images/icons8-university-50.png");
        });
    }
}