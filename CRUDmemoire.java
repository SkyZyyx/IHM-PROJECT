import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 class pageTest {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Memoire> memoires;
    private JPanel panel1,main,navPanel,searchPanel;

     public pageTest(String iconPath) {
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
                searchMemoires(searchText);
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

        memoires = new ArrayList<>(); // Assume you have a list of Memoire objects

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Year");
        tableModel.addColumn("Summary");
        tableModel.addColumn("Encadreur");


        try {
            Connection connection = null;
            connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String query = "SELECT * FROM memoires";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String summary = rs.getString("summary");
                String encadreur = rs.getString("encadreur");
                memoires.add(new Memoire(id, title, author, year, summary, encadreur));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add some sample data
        for (Memoire memoire : memoires) {
            tableModel.addRow(new Object[]{memoire.getid(), memoire.gettitle(), memoire.getauthor(), memoire.getyear(), memoire.getsummary(), memoire.getEncadreur()});
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
            Memoire selectedMemoire = memoires.get(selectedRow);
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
                        PreparedStatement pstmt = connection1.prepareStatement("DELETE FROM memoires WHERE id = ?");

                        // Assuming you have a primary key column named 'id' in your database table
                        Memoire selectedMemoire = memoires.get(selectedRow);
                        pstmt.setInt(1, selectedMemoire.getid());

                        memoires.remove(selectedRow);
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

   

    private class Memoire {
        private int id;
        private String title;
        private String author;
        private String encadreur;
        private int year;
        private String summary;

        public Memoire(int id, String title, String author, int year, String summary, String encadreur) {

            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
            this.summary = summary;
            this.encadreur = encadreur;
        }
        
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public void setAuthor(String author) {
            this.author = author;
        }
        
        public void setYear(int year) {
            this.year = year;
        }
        
        public void setSummary(String summary) {
            this.summary = summary;
        }
        
        public void setEncadreur(String encadreur) {
            this.encadreur = encadreur;
        }
         

        public int getid() {
            return id;
        }

        public String gettitle() {
            return title;
        }

        public String getauthor() {
            return author;
        }

        public int getyear() {
            return year;
        }

        public String getsummary() {
            return summary;
        }

        public String getEncadreur() {
            return encadreur;
        }

        @Override
        public String toString() {
            return "Memoire{" +
                    ", encadreur='" + encadreur + '\'' +
                    ", annee=" + year + '\'' +
                    ", id=" + id + '\'' +
                    '}';
        }
        public void updateInDatabase(String updatedTitle, String updatedAuthor, int updatedYear,
        String updatedSummary, String updatedEncadreur) {
        try {
        // Create a connection to your database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/memoiredb", "root", "2003");

        // Create an SQL UPDATE query
        String sql = "UPDATE memoires SET title = ?, author = ?, year = ?, summary = ?, encadreur = ? WHERE id = ?";

        // Create a PreparedStatement
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // Set the parameters for the SQL UPDATE query
        pstmt.setString(1, updatedTitle);
        pstmt.setString(2, updatedAuthor);
        pstmt.setInt(3, updatedYear);
        pstmt.setString(4, updatedSummary);
        pstmt.setString(5, updatedEncadreur);
        pstmt.setInt(6, this.id);
        

        // Execute the SQL UPDATE query
        pstmt.executeUpdate();

        // Close the database connection
        conn.close();
        } catch (SQLException e) {
        System.out.println("Error updating the database: " + e.getMessage());
        }
        }
    }

    private void searchMemoires(String searchText) {
        // Clear the table before adding search results
        clearTable();

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM memoires WHERE id = ? OR title LIKE ? OR encadreur LIKE ?";
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

            rs = pstmt.executeQuery();

            // Add the results to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                String summary = rs.getString("summary");
                String encadreur = rs.getString("encadreur");
                memoires.add(new Memoire(id, title, author, year, summary, encadreur));
                tableModel.addRow(new Object[]{id, title, author, year, summary, encadreur});
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM memoires");

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int year = rs.getInt("year");
            String summary = rs.getString("summary");
            String encadreur = rs.getString("encadreur");
            memoires.add(new Memoire(id, title, author, year, summary, encadreur));
            tableModel.addRow(new Object[]{id, title, author, year, summary, encadreur});
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
        memoires.clear();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
    }
    private void updateMemoireDetails(Memoire memoire) {
        // Create a dialog for updating memoire details
        JDialog updateDialog = new JDialog(frame, "Modifier la mémoire", true);
        updateDialog.setLayout(new BorderLayout());
    
        // Create text fields for each detail
        JTextField titleField = new JTextField(memoire.gettitle());
        JTextField authorField = new JTextField(memoire.getauthor());
        JTextField yearField = new JTextField(String.valueOf(memoire.getyear()));
        JTextField summaryField = new JTextField(memoire.getsummary());
        JTextField encadreurField = new JTextField(memoire.getEncadreur());
    
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
        int updatedYear = Integer.parseInt(yearField.getText());
        String updatedSummary = summaryField.getText();
        String updatedEncadreur = encadreurField.getText();

        // Update the memoire details
        
        memoire.setTitle(updatedTitle);
        memoire.setAuthor(updatedAuthor);
        memoire.setYear(updatedYear);
        memoire.setSummary(updatedSummary);
        memoire.setEncadreur(updatedEncadreur);

        // Update the table and database
        memoire.updateInDatabase(updatedTitle, updatedAuthor, updatedYear, updatedSummary, updatedEncadreur);
        resetTable();

        // Close the dialog
        updateDialog.dispose();
    }
});
        JLabel title;
        JComboBox<String> encadreurCombo; 
        String[] professors = {"Sélectionnez l'encadreur"};
        JPanel supPanel = new JPanel();
        title = new JLabel("<html><span style='color: #0056b3; font-size:22px;'>Memoire</span>-Form</html>");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(20, 40);
        title.setForeground(Color.BLACK);
        supPanel.add(title);

    
        // Add components to the dialog
        JPanel dialogPanel = new JPanel(new GridLayout(6, 2));
        dialogPanel.add(new JLabel("Titre : "));
        dialogPanel.add(titleField);
        dialogPanel.add(new JLabel("Auteur : "));
        dialogPanel.add(authorField);
        dialogPanel.add(new JLabel("Année : "));
        dialogPanel.add(yearField);
        dialogPanel.add(new JLabel("Résumé : "));
        dialogPanel.add(summaryField);
        dialogPanel.add(new JLabel("Encadreur : "));
        dialogPanel.add(encadreurField);

        
        dialogPanel.add(updateButton);
        dialogPanel.add(CancelB);
        supPanel.add(dialogPanel);
    

        updateDialog.add(supPanel, BorderLayout.CENTER);
    
        // Set dialog properties
        updateDialog.setSize(400, 300);
        updateDialog.setLocationRelativeTo(frame);
        updateDialog.setVisible(true);
         frame.setBounds(300, 90, 900, 600);
    }
    
   
}
public class CRUDmemoire{
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new pageTest("./images/icons8-university-50.png");
        });
    }
}