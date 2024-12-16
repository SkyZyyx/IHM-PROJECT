import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 class pageCRUD {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Enseignant> enseignant;
    private JPanel panel1,main,navPanel,searchPanel;

     public pageCRUD(String iconPath) {
        frame = new JFrame("Gestion des Mémoires");
       // frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1 = new JPanel();
        main = new JPanel();
        main.setLayout(new BorderLayout());
        panel1.setLayout(null);
        frame.setContentPane(main);
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

        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 15));
        searchButton.setSize(150, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 86, 179));
        searchPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the search text
                String searchText = searchField.getText();

                // Execute the search based on the entered text
                searchProf(searchText);
            }
        });
         JButton homebutton;
        homebutton = new JButton("Home");
        homebutton.setFont(new Font("Arial", Font.PLAIN, 15));
        homebutton.setSize(150, 30);
        homebutton.setBackground(Color.WHITE);
        homebutton.setForeground(Color.BLACK);
        homebutton.setFocusPainted(false); // Remove the border around the text
        searchPanel.add(homebutton);
        homebutton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt){
           Homepage memoireFrame = new Homepage(iconPath);
        // Fermez la fenêtre actuelle
        frame.dispose();
            
            
    }
}

    
        );


        // Adding top margin to searchPanel
        int topMargin = 15;
        searchPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, 0, 0, 0));

        // Now you can add searchPanel to your navPanel
        navPanel.add(searchPanel);


        // Set the preferred size to increase the height of the navigation bar
        navPanel.setPreferredSize(new Dimension(800, 70));

        // Add the navigation panel to the main panel
        main.add(navPanel, BorderLayout.NORTH);

        enseignant = new ArrayList<>(); // Assume you have a list of Memoire objects

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("nom");
        tableModel.addColumn("prenom");
        tableModel.addColumn("specialite");


        try {
            Connection connection = null;
            connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String query = "SELECT * FROM enseignant";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                enseignant.add(new Enseignant(id, nom, prenom, specialite));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add some sample data
        for (Enseignant enseignant : enseignant) {
            tableModel.addRow(new Object[]{enseignant.getid(), enseignant.getName(), enseignant.getPrenom(), enseignant.getSpecialite()});
        }

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton modifyButton = new JButton("Modifier");
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setBackground(new Color(0, 86, 179));
        modifyButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Show a dialog to update details for the selected memoire
            Enseignant selectedMemoire = enseignant.get(selectedRow);
            updateMemoireDetails(selectedMemoire);
        } else {
            JOptionPane.showMessageDialog(frame, "Sélectionnez une mémoire à mettre à jour.");
        }
    }
});





        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(179, 0, 0));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        Connection connection1 = DatabaseConnection.getConnection();
                        PreparedStatement pstmt = connection1.prepareStatement("DELETE FROM enseignant WHERE id = ?");

                        // Assuming you have a primary key column named 'id' in your database table
                        Enseignant selectedMemoire = enseignant.get(selectedRow);
                        pstmt.setInt(1, selectedMemoire.getid());

                        enseignant.remove(selectedRow);
                        tableModel.removeRow(selectedRow);
                        int rowsAffected = pstmt.executeUpdate();

                        if (rowsAffected > 0) {
                            // Row deleted successfully
                            System.out.println("Row deleted successfully");
                        } else {
                            // No row deleted (perhaps the row with the specified ID doesn't exist)
                            System.out.println("No row deleted");
                        }

                        
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Sélectionnez une mémoire à supprimer.");
                }
            }
        });
       
        JButton resetButton = new JButton("Reset Search");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 15));
        resetButton.setSize(150, 30);
        resetButton.setLocation(200, 400);
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(Color.BLACK);
        resetButton.setFocusPainted(false); // Remove the border around the text
        
        resetButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Call the method to reset the table
        resetTable();
        // Clear the search field
        searchField.setText("");
    }
});

        frame.setBackground(Color.WHITE);
        JPanel mainPanel = new JPanel(new FlowLayout());
        // Replace JTextArea with JLabel for displaying an image
        ImageIcon imageIcon = new ImageIcon("./images/search.png");

        // Get the image from the ImageIcon
        Image image = imageIcon.getImage();
        
        // Scale the image to fit perfectly within the JLabel while maintaining aspect ratio
        int labelWidth = 300;
        int labelHeight = 400;
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setSize(labelWidth, labelHeight);
        imageLabel.setLocation(500, 100);
        
       
        mainPanel.add(imageLabel);
        mainPanel.add(scrollPane);
        main.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

   

    private class Enseignant {
        private int id;
        private String nom;
        private String prenom;
        private String specialite;
    

        public Enseignant(int id, String nom, String prenom,String specialite) {

            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.specialite = specialite;
        }
        
        
        public void setNom(String nom) {
            this.nom = nom;
        }
        
        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }
        
        public void setSpecialite(String specialite) {
            this.specialite = specialite;
        }
         

        public int getid() {
            return id;
        }

        public String getName() {
            return nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public String getSpecialite() {
            return specialite;
        }

        @Override
        public String toString() {
            return "Enseignant{" +
                    ", nom='" + nom + '\'' +
                    ", prenom=" + prenom + '\'' +
                    ", id=" + id + '\'' +
                    '}';
        }
        public void updateInDatabase(String updatedNom, String updatedPrenom,String updatedSpecialite) {
        try {
        // Create a connection to your database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/memoiredb", "root", "2003");

        // Create an SQL UPDATE query
        String sql = "UPDATE enseignant SET nom = ?, prenom = ?, specialite = ? WHERE id = ?";

        // Create a PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the parameters for the SQL UPDATE query
        pstmt.setString(1, updatedNom);
        pstmt.setString(2, updatedPrenom);
        pstmt.setString(3, updatedSpecialite);
        pstmt.setInt(4, this.id);
        

        // Execute the SQL UPDATE query
        pstmt.executeUpdate();

        // Close the database connection
        
        } catch (SQLException e) {
        System.out.println("Error updating the database: " + e.getMessage());
        }
        }
    }

    private void searchProf(String searchText) {
        // Clear the table before adding search results
        clearTable();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM enseignant WHERE id = ? OR nom LIKE ? OR prenom LIKE ? OR specialite LIKE ?";
            pstmt = connection.prepareStatement(query);

            // Convert searchText to an integer if it's a number (id)
            try {
                int id = Integer.parseInt(searchText);
                pstmt.setInt(1, id);
            } catch (NumberFormatException ex) {
                pstmt.setInt(1, -1); // A value that won't match any id
            }

            pstmt.setString(2, "%" + searchText + "%"); // Partial title search
            pstmt.setString(3, "%" + searchText + "%"); // Partial encadreur search
            pstmt.setString(4, "%" + searchText + "%"); // Partial encadreur search

            rs = pstmt.executeQuery();

            // Add the results to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String specialite = rs.getString("specialite");
                enseignant.add(new Enseignant(id, nom, prenom, specialite));
                tableModel.addRow(new Object[]{id, nom, prenom, specialite});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    // Method to load the initial data and update the table
private void loadData() {
    // Clear the table
    clearTable();

    // Load the initial data
    try {
        Connection connection = DatabaseConnection.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM enseignant");

        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String specialite = rs.getString("specialite");
            enseignant.add(new Enseignant(id, nom, prenom, specialite));
            tableModel.addRow(new Object[]{id, nom, prenom, specialite});
        }

        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private void resetTable() {
    clearTable(); // Clear the current table data

    // Reload the initial data and update the table
    loadData(); // Replace this with the actual method to load initial data
}

    // Add a method to clear the current table
    private void clearTable() {
        enseignant.clear();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }
    private void updateMemoireDetails(pageCRUD.Enseignant selectedMemoire) {
        // Create a dialog for updating memoire details
        JDialog updateDialog = new JDialog(frame, "Modifier la mémoire", true);
        updateDialog.setLayout(new BorderLayout());
    
        // Create text fields for each detail
        JTextField titleField = new JTextField(selectedMemoire.getName());
        JTextField authorField = new JTextField(selectedMemoire.getPrenom());
        JTextField summaryField = new JTextField(selectedMemoire.getSpecialite());
    
        // Create a button to perform the update
        JButton updateButton = new JButton("Mettre à jour");
        updateButton.setFont(new Font("Arial", Font.PLAIN, 15));
        updateButton.setBackground(new Color(0, 86, 179));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        JButton CancelB = new JButton("Cancel");
        CancelB.setFont(new Font("Arial", Font.PLAIN, 15));
        CancelB.setBackground(Color.WHITE);
        CancelB.setForeground(Color.BLACK);
        CancelB.setFocusPainted(false);
updateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the updated details from the text fields
        String updatedTitle = titleField.getText();
        String updatedAuthor = authorField.getText();
        String updatedSummary = summaryField.getText();

        // Update the memoire details
        
        selectedMemoire.setNom(updatedTitle);
        selectedMemoire.setPrenom(updatedAuthor);
        selectedMemoire.setSpecialite(updatedSummary);

        // Update the table and database
        selectedMemoire.updateInDatabase(updatedTitle, updatedAuthor, updatedSummary);
        resetTable();

        // Close the dialog
        updateDialog.dispose();
    }
});
        JLabel title;
        JComboBox<String> encadreurCombo; 
        String[] professors = {"Sélectionnez l'encadreur"};
        JPanel supPanel = new JPanel();
        title = new JLabel("<html><span style='color: #0056b3; font-size:22px;'>Modifier</span>-prof</html>");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(20, 40);
        title.setForeground(Color.BLACK);
        supPanel.add(title);

    
        // Add components to the dialog
        JPanel dialogPanel = new JPanel(new GridLayout(6, 2));
        dialogPanel.add(new JLabel("Nom : "));
        dialogPanel.add(titleField);
        dialogPanel.add(new JLabel("Prenom : "));
        dialogPanel.add(authorField);
        dialogPanel.add(new JLabel("Specialite : "));
        dialogPanel.add(summaryField);

        
        dialogPanel.add(updateButton);
        dialogPanel.add(CancelB);
        supPanel.add(dialogPanel);
    

        updateDialog.add(supPanel, BorderLayout.CENTER);
    
        // Set dialog properties
        updateDialog.setSize(400, 300);
        updateDialog.setLocationRelativeTo(frame);
        updateDialog.setVisible(true);
    }
    
   
}
public class CRUDprof{
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new pageCRUD("./images/icons8-university-50.png");
        });
    }
}