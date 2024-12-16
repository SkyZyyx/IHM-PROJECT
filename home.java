import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Homepage extends JFrame implements ActionListener {
    
    private JLabel label1,label2,label3,imageLabel;
    private JPanel panel1,main,navPanel,searchPanel;
    private JButton posez,ajouter;
    

    
    public Homepage(String iconPath) {
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
       
            Homepage memoireFrame = new Homepage(iconPath);
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
        searchButton.addActionListener(new ActionListener() {
        @Override
         public void actionPerformed(ActionEvent evt){
       
            pageTest memoireFrame = new pageTest(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
    }});

        // Adding top margin to searchPanel
        int topMargin = 15;
        searchPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, 0, 0, 0));

        // Now you can add searchPanel to your navPanel
        navPanel.add(searchPanel);


        // Set the preferred size to increase the height of the navigation bar
        navPanel.setPreferredSize(new Dimension(800, 70));

        // Add the navigation panel to the main panel
        main.add(navPanel, BorderLayout.NORTH);


        label1 = new JLabel("Cher étudiants,");
        label1.setFont(new Font("Arial", Font.PLAIN, 30));
        label1.setSize(300, 30);
        label1.setLocation(20, 40);
        label1.setForeground(Color.BLACK);
        panel1.add(label1);

        label2 = new JLabel("Découvrez notre nouveau formulaire de recours!");
        label2.setFont(new Font("Arial", Font.PLAIN, 20));
        label2.setSize(600, 30);
        label2.setLocation(20, 100);
        label2.setForeground(Color.BLACK);
        panel1.add(label2);

        label3 = new JLabel("Exprimez-vous et posez vos questions dès maintenant.");
        label3.setFont(new Font("Arial", Font.PLAIN, 20));
        label3.setSize(600, 30);
        label3.setLocation(20, 150);
        label3.setForeground(Color.BLACK);
        panel1.add(label3);



        posez = new JButton("Add Memoire");
        posez.setFont(new Font("Arial", Font.PLAIN, 15));
        posez.setSize(150, 30);
        posez.setLocation(20, 300);
        posez.setBackground(new Color(0, 86, 179));
        posez.setForeground(Color.WHITE);
        posez.setFocusPainted(false); // Remove the border around the text
        panel1.add(posez);
        posez.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
       
            MyFrame memoireFrame = new MyFrame(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
        } 
        
    }
);

        JButton Gerer = new JButton("Gerer les prof");
        Gerer.setFont(new Font("Arial", Font.PLAIN, 15));
        Gerer.setSize(330, 30);
        Gerer.setLocation(20, 380);
        Gerer.setBackground(new Color(0, 86, 100));
        Gerer.setForeground(Color.WHITE);
        Gerer.setFocusPainted(false); // Remove the border around the text
        panel1.add(Gerer);
        Gerer.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
       
            pageCRUD memoireFrame = new pageCRUD(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
        } 
        
    }
);

        ajouter = new JButton("Ajouter prof");
        ajouter.setFont(new Font("Arial", Font.PLAIN, 15));
        ajouter.setSize(150, 30);
        ajouter.setLocation(200, 300);
        ajouter.setBackground(Color.WHITE);
        ajouter.setForeground(Color.BLACK);
        ajouter.setFocusPainted(false); // Remove the border around the text
       
          ajouter.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
       
            pageAjouterProf pageProf = new pageAjouterProf(iconPath);
        // Fermez la fenêtre actuelle
        dispose();
            
        } 
        
    }
);
        panel1.add(ajouter);


       // Replace JTextArea with JLabel for displaying an image
        ImageIcon imageIcon = new ImageIcon("./images/homeimage.jpg");

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
    }
}

public class home {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new Homepage("./images/icons8-university-50.png");
        });
    }

    
}