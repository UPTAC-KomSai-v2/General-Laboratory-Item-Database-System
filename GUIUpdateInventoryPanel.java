import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
<<<<<<< Updated upstream
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
=======
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
>>>>>>> Stashed changes
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

public class GUIUpdateInventoryPanel extends JPanel {
    private Branding branding;
<<<<<<< Updated upstream
    private JPanel mainContentPanel;
    private JButton selectedCategoryButton = null;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JPanel inventoryPanel; // Panel to hold the inventory table
    
    public GUIUpdateInventoryPanel(Branding branding, JButton backButton) {
=======
    private JPanel mainContentPanel, categoryPanel;
    private JButton selectedCategoryButton = null, removeItemButton, addItemButton;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JPanel inventoryPanel; // Panel to hold the inventory table
    private Controller ctrl;
    private int selectedCategoryIndexDatabase, selectedCategoryIndexArray;
    private JButton backButton;
    private GUIBorrowItemPanel borrowItemPanel;

    // Categories of laboratory equipment (we'll load this from the controller)
    private String[] categories;

    /**
     * Constructor - creates the panel structure but doesn't load data
     * @param ctrl Controller instance
     * @param branding Branding instance
     * @param backButton Back button reference
     */
    public GUIUpdateInventoryPanel(Controller ctrl, Branding branding, JButton backButton, GUIBorrowItemPanel borrowItemPanel) {
        this.ctrl = ctrl;
>>>>>>> Stashed changes
        this.branding = branding;
        this.backButton = backButton;
        this.borrowItemPanel = borrowItemPanel;
        
        // Set up panel properties
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(900, 500));
        
        // Create the panel structure without loading data
        createPanelStructure();
    }

    // Categories of laboratory equipment
    private String[] categories = {
        "Glassware and Plasticware",
        "Measuring and Analytical Instruments",
        "Lab Tools and Accessories",
        "Consumables and Miscellaneous",
        "Storage Containers", 
        "Biological Materials",
        "Electrical Equipment",
        "Safety Equipment"
    };
    
    // Sample inventory data for each category
    private String[][][] categoryInventoryData = {
        // Glassware and Plasticware
        {
            {"LAB001", "Beaker (100ml)", "Unit", "35"},
            {"LAB002", "Erlenmeyer Flask", "Unit", "18"},
            {"LAB009", "Graduated Cylinder", "Unit", "22"},
            {"LAB013", "Test Tube", "Pack", "45"},
            {"LAB017", "Pipette", "Unit", "12"},
            {"LAB024", "Glass Funnel", "Unit", "10"}
        },
        // Measuring and Analytical Instruments
        {
            {"LAB003", "Digital Scale", "Unit", "7"},
            {"LAB014", "Microscope", "Unit", "5"},
            {"LAB021", "Thermometers", "Unit", "18"},
            {"LAB028", "pH Meter", "Unit", "4"},
            {"LAB033", "Lab Timer", "Unit", "14"}
        },
        // Lab Tools and Accessories
        {
            {"LAB005", "Bunsen Burner", "Unit", "8"},
            {"LAB018", "Forceps", "Unit", "20"},
            {"LAB019", "Scalpel Blades", "Box", "10"},
            {"LAB028", "Stirring Rods", "Pack", "15"},
            {"LAB035", "Burette Stand", "Unit", "6"}
        },
        // Consumables and Miscellaneous
        {
            {"LAB006", "Laboratory Gloves", "Box", "45"},
            {"LAB011", "pH Test Strips", "Box", "40"},
            {"LAB012", "Distilled Water", "Liter", "50"},
            {"LAB022", "Alcohol Swabs", "Box", "35"},
            {"LAB026", "Filter Paper", "Pack", "25"},
            {"LAB031", "Ethanol Solution", "Liter", "12"},
            {"LAB036", "Rubber Stoppers", "Pack", "20"}
        },
        // Storage Containers
        {
            {"LAB004", "Specimen Containers", "Pack", "30"},
            {"LAB017", "Centrifuge Tubes", "Pack", "25"},
            {"LAB027", "Reagent Bottles", "Unit", "20"},
            {"LAB032", "Autoclave Bags", "Pack", "18"}
        },
        // Biological Materials
        {
            {"LAB001", "Microscope Slides", "Box", "15"},
            {"LAB003", "Petri Dishes", "Pack", "30"},
            {"LAB023", "Glass Slides", "Box", "22"},
            {"LAB024", "Cover Slips", "Box", "22"}
        },
        // Electrical Equipment
        {
            {"LAB014", "Digital Scale", "Unit", "7"},
            {"LAB020", "Multimeter", "Unit", "5"},
            {"LAB029", "Power Supply", "Unit", "3"},
            {"LAB034", "Hot Plate", "Unit", "8"}
        },
        // Safety Equipment
        {
            {"LAB007", "Safety Goggles", "Unit", "20"},
            {"LAB013", "Lab Coats", "Unit", "15"},
            {"LAB025", "Lab Notebook", "Unit", "30"},
            {"LAB030", "Safety Face Shield", "Unit", "8"}
        }
    };
    
    /**
     * Creates the basic panel structure without loading data
     */
    private void createPanelStructure() {
        // Create main content panel with border layout
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createLineBorder(branding.maroon, 2));
        
        // Create content panel with border layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(branding.maroon, 1));
        
<<<<<<< Updated upstream
        // Create and add category panel to the left side
        JPanel categoryPanel = createCategoryPanel();
        contentPanel.add(categoryPanel, BorderLayout.WEST);
        
        // Create inventory display panel
        inventoryPanel = createItemInventoryPanel(backButton);
        contentPanel.add(inventoryPanel, BorderLayout.CENTER);
        
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Show the initial "No Category is Selected" message
        showNoCategorySelectedMessage();
    }
    
    private JPanel createCategoryPanel() {
        // The panel that contains the category buttons (BoxLayout for vertical stack)
        JPanel categoryPanel = new JPanel();
=======
        // Create category panel structure (without adding category buttons yet)
        categoryPanel = new JPanel();
>>>>>>> Stashed changes
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBackground(branding.maroon);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
<<<<<<< Updated upstream
    
=======
        
        // Wrapper panel to center the category panel vertically
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(branding.maroon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centeredPanel.add(categoryPanel, gbc);
        centeredPanel.setPreferredSize(new Dimension(220, 0)); // Force fixed width
        
        // Create the inventory display panel structure
        inventoryPanel = createItemInventoryPanel();
        
        // Add panels to the content panel
        contentPanel.add(centeredPanel, BorderLayout.WEST);
        contentPanel.add(inventoryPanel, BorderLayout.CENTER);
        
        // Add content panel to main panel
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main content panel to this panel
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    /**
     * Loads data and initializes interactive elements after login
     */
    public void loadContents() {
        // Get categories from the controller
        loadCategories();
        
        // Initialize category buttons
        loadCategoryButtons();
        
        // Create button panel with action listeners
        JPanel buttonPanel = createButtonPanel();
        inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Configure initial state - show "No Category Selected" message
        showNoCategorySelectedMessage();
        
        // Set up add/remove item buttons based on database state
        refreshAddItemButton();
        
        // Refresh the panel
        revalidate();
        repaint();
    }
    
    /**
     * Loads categories from the controller
     */
    private void loadCategories() {
        List<String[]> categoryList = ctrl.getCategoryList();
        categories = new String[categoryList.size()];
        
        for (int i = 0; i < categoryList.size(); i++) {
            categories[i] = categoryList.get(i)[1]; // Category name is at index 1
        }
    }
    
    /**
     * Creates and adds category buttons to the category panel
     */
    private void loadCategoryButtons() {
        // Clear any existing buttons
        categoryPanel.removeAll();
        
>>>>>>> Stashed changes
        for (int i = 0; i < categories.length; i++) {
            final int categoryIndex = i;
            JButton categoryButton = new JButton(categories[i]);
            categoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            categoryButton.setPreferredSize(new Dimension(200, 40));
            categoryButton.setMaximumSize(new Dimension(200, 40));
            categoryButton.setMinimumSize(new Dimension(200, 40));
            categoryButton.setBackground(branding.lightergray);
            categoryButton.setForeground(branding.maroon);
            categoryButton.setFont(new Font("Arial", Font.PLAIN, 14));
            categoryButton.setFocusPainted(false);
            categoryButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            categoryButton.setMargin(new Insets(0, 0, 0, 0));
    
            // Hover and selection effect
            categoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    categoryButton.setBackground(branding.lightgray);
                }
    
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (selectedCategoryButton != categoryButton) {
                        categoryButton.setBackground(branding.lightergray);
                    }
                }
            });
            
            // Action listener to update inventory panel when a category is selected
            categoryButton.addActionListener(e -> {
                if (selectedCategoryButton != null) {
                    selectedCategoryButton.setBackground(branding.lightergray);
                }
                categoryButton.setBackground(branding.gray);
                selectedCategoryButton = categoryButton;
                
                // Update inventory display with items from the selected category
<<<<<<< Updated upstream
                updateInventoryPanel(categoryIndex);
=======
                selectedCategoryIndexDatabase = ctrl.getCategoryID(categories[categoryIndex]);
                selectedCategoryIndexArray = categoryIndex;
                System.out.println("Selected Category ID from Database: " + selectedCategoryIndexDatabase);
                System.out.println("Selected Category ID from Array: " + selectedCategoryIndexArray);
                updateInventoryPanel(selectedCategoryIndexDatabase, categoryIndex);
>>>>>>> Stashed changes
            });
    
            categoryPanel.add(categoryButton);
            categoryPanel.add(Box.createVerticalStrut(10)); // Small space between buttons
        }
<<<<<<< Updated upstream
    
        // Wrapper panel to center the category panel vertically
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(branding.maroon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centeredPanel.add(categoryPanel, gbc);
    
        centeredPanel.setPreferredSize(new Dimension(220, 0)); // Force fixed width like before
        return centeredPanel;
=======
        
        // Refresh the panel
        categoryPanel.revalidate();
        categoryPanel.repaint();
>>>>>>> Stashed changes
    }
    
    private JPanel createItemInventoryPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(branding.lightgray);
    
        // Create a panel to hold the table (will be updated based on category selection)
        JPanel tableContainerPanel = new JPanel(new BorderLayout());
        tableContainerPanel.setBackground(branding.lightgray);
        
        // Scroll pane with vertical-only scrolling
        JScrollPane scrollPane = new JScrollPane(tableContainerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        branding.reskinScrollBar(scrollPane, branding.gray);
    
        // Add components
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Store the tableContainerPanel as a property of the main panel for later access
        mainPanel.putClientProperty("tableContainerPanel", tableContainerPanel);
    
        return mainPanel;
    }
    
<<<<<<< Updated upstream
=======
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(branding.lightgray);
        
        // Configure the back button
        backButton.setText("Go Back");
        styleActionButton(backButton);
        
        // Create and style action buttons
        removeItemButton = new JButton("Remove Item");
        addItemButton = new JButton("Add Item");
        
        styleActionButton(removeItemButton);
        styleActionButton(addItemButton);
        
        // Add action listener for Remove Item button
        removeItemButton.addActionListener(e -> removeSelectedItem());
        
        buttonPanel.add(backButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(addItemButton);
        
        return buttonPanel;
    }
    
>>>>>>> Stashed changes
    // Method to show "No Category is Selected" message
    private void showNoCategorySelectedMessage() {
        if (inventoryPanel == null) return;
        
        // Get the table container panel
        JPanel tableContainerPanel = (JPanel) inventoryPanel.getClientProperty("tableContainerPanel");
        if (tableContainerPanel == null) return;
        
        // Clear the panel
        tableContainerPanel.removeAll();
        
        // Create a message panel
        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setBackground(branding.lightgray);
        
        JLabel messageLabel = new JLabel("No Category is Selected");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(branding.maroon);
        
        messagePanel.add(messageLabel);
        
        // Add the message panel
        tableContainerPanel.add(messagePanel, BorderLayout.CENTER);
        
        // Refresh the panel
        tableContainerPanel.revalidate();
        tableContainerPanel.repaint();
    }
    
    // Method to update inventory panel based on selected category
<<<<<<< Updated upstream
    private void updateInventoryPanel(int categoryIndex) {
        if (inventoryPanel == null) return;
        
        // Get the table container panel
        JPanel tableContainerPanel = (JPanel) inventoryPanel.getClientProperty("tableContainerPanel");
        if (tableContainerPanel == null) return;
        
        // Clear the panel
        tableContainerPanel.removeAll();
        
        // Create table panel with a border
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(branding.lightgray);
        Border lineBorder = BorderFactory.createLineBorder(branding.maroon, 5);
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        CompoundBorder compoundBorder = new CompoundBorder(emptyBorder, lineBorder);
        tablePanel.setBorder(compoundBorder);

        // Get data for selected category
        String[][] data = categoryInventoryData[categoryIndex];

        // Column names
        String[] columnNames = {"Item ID", "Item Name", "Unit", "Quantity"};

        // Create table model that allows cell editing
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make cells editable
                return true;
            }
        };

        // Create JTable with the table model
        inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(50);
        inventoryTable.setForeground(branding.maroon);
        inventoryTable.setBackground(branding.lightgray);

        GridBagConstraints tablePanelGBC = new GridBagConstraints();
        tablePanelGBC.fill = GridBagConstraints.BOTH;
        tablePanelGBC.weightx = 1;
        tablePanelGBC.ipady = 20;
        tablePanelGBC.gridy = 0;
        tablePanel.add(inventoryTable.getTableHeader(), tablePanelGBC); 
        tablePanelGBC.weighty = 1;
        tablePanelGBC.ipady = 0;
        tablePanelGBC.gridy = 1;
        tablePanel.add(inventoryTable, tablePanelGBC);  
        
        // Add the table panel to the container
        tableContainerPanel.add(tablePanel, BorderLayout.CENTER);
        
        // Add a header label showing which category is selected
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(branding.lightgray);
        JLabel headerLabel = new JLabel(categories[categoryIndex] + " Inventory");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(branding.maroon);
        headerPanel.add(headerLabel);
        tableContainerPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Refresh the panel
        tableContainerPanel.revalidate();
        tableContainerPanel.repaint();
    }
    
    private JPanel createButtonPanel(JButton backButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 30));
        buttonPanel.setBackground(branding.lightgray);
=======
    private void updateInventoryPanel(int categoryIndexDatabase, int categoryIndex) {
        if (inventoryPanel == null) return;
>>>>>>> Stashed changes
        
        // Get the table container panel
        JPanel tableContainerPanel = (JPanel) inventoryPanel.getClientProperty("tableContainerPanel");
        if (tableContainerPanel == null) return;
        
<<<<<<< Updated upstream
        JButton removeItemButton = new JButton("Remove Item");
        JButton addItemButton = new JButton("Add Item");
=======
        // Clear the panel
        tableContainerPanel.removeAll();
>>>>>>> Stashed changes
        
        // Create table panel with a border
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(branding.lightgray);
        Border lineBorder = BorderFactory.createLineBorder(branding.maroon, 5);
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        CompoundBorder compoundBorder = new CompoundBorder(emptyBorder, lineBorder);
        tablePanel.setBorder(compoundBorder);

        // Get data for selected category using controller
        String[][] data = ctrl.getItemsPerCategory(categoryIndexDatabase);

        // Column names
        String[] columnNames = {"Item Name", "Unit", "Quantity"};

        // Transform data to exclude item_id (index 0)
        String[][] trimmedData = new String[data.length][3];
        for (int i = 0; i < data.length; i++) {
            trimmedData[i][0] = data[i][1]; // Item Name
            trimmedData[i][1] = data[i][2]; // Unit
            trimmedData[i][2] = data[i][3]; // Quantity
        }

        // Create table model with trimmed data
        tableModel = new DefaultTableModel(trimmedData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // or false depending on your requirement
            }
        };

        // Create JTable with the table model
        inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(50);
        inventoryTable.setForeground(branding.maroon);
        inventoryTable.setBackground(branding.lightgray);

        GridBagConstraints tablePanelGBC = new GridBagConstraints();
        tablePanelGBC.fill = GridBagConstraints.BOTH;
        tablePanelGBC.weightx = 1;
        tablePanelGBC.ipady = 20;
        tablePanelGBC.gridy = 0;
        tablePanel.add(inventoryTable.getTableHeader(), tablePanelGBC); 
        tablePanelGBC.weighty = 1;
        tablePanelGBC.ipady = 0;
        tablePanelGBC.gridy = 1;
        tablePanel.add(inventoryTable, tablePanelGBC);  
        
<<<<<<< Updated upstream
        // Add action listener for Remove Item button
        removeItemButton.addActionListener(e -> removeSelectedItem());
        
        // Add action listener for Add Item button
        addItemButton.addActionListener(e -> addNewItem());
=======
        // Add the table panel to the container
        tableContainerPanel.add(tablePanel, BorderLayout.CENTER);
>>>>>>> Stashed changes
        
        // Add a header label showing which category is selected
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(branding.lightgray);
        JLabel headerLabel = new JLabel(categories[categoryIndex] + " Inventory");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(branding.maroon);
        headerPanel.add(headerLabel);
        tableContainerPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Refresh the panel
        tableContainerPanel.revalidate();
        tableContainerPanel.repaint();
    }
<<<<<<< Updated upstream
=======

    public void refreshAddItemButton() {
        if (ctrl.getNoOfItems() != 0) {
            addItemButton.setText("Add Item");
            
            // Clear previous action listeners
            for (ActionListener listener : addItemButton.getActionListeners()) {
                addItemButton.removeActionListener(listener);
            }
            
            addItemButton.addActionListener(e -> addNewItem());
            removeItemButton.setEnabled(true);
        } else {
            removeItemButton.setEnabled(false);
            addItemButton.setText("Import From File");
            
            // Clear previous action listeners
            for (ActionListener listener : addItemButton.getActionListeners()) {
                addItemButton.removeActionListener(listener);
            }
            
            addItemButton.addActionListener(e -> {
                boolean success = importItemsFromCSV();
                if (success) {
                    removeItemButton.setEnabled(true);
                    resetAddItemButtonToAddMode();
                    JOptionPane.showMessageDialog(
                    this,
                    "Import successful.",
                    "Successful Import",
                    JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                    this,
                    "Import unsuccessful.",
                    "Unsuccessful Import",
                    JOptionPane.WARNING_MESSAGE
                    );
                }
            });
        }
    }

    private void resetAddItemButtonToAddMode() {
        for (ActionListener listener : addItemButton.getActionListeners()) {
            addItemButton.removeActionListener(listener);
        }
        addItemButton.setText("Add Item");
        addItemButton.addActionListener(e -> addNewItem());
    }

    private boolean importItemsFromCSV() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog(this);

            if(result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                String filepath = selectedFile.getAbsolutePath();
                System.out.println("Selected file: " + filepath);
                
                // Use the controller to handle the import process
                // We'll parse the file here since controller needs the data array
                String[][] data = parseCSVFile(selectedFile);
                
                if (data != null && data.length > 0) {
                    ctrl.importItemsFromCSV(filepath, data);
                    
                    // If a category is selected, refresh its display
                    if (selectedCategoryButton != null) {
                        updateInventoryPanel(selectedCategoryIndexDatabase, selectedCategoryIndexArray);
                    }
                    
                    return true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Opening file terminated.", "Terminated", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                this,
                "Error importing file: " + e.getMessage(),
                "Import Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        return false;
    }
    
    /**
     * Helper method to parse CSV file
     */
    private String[][] parseCSVFile(File file) {
        try {
            java.io.BufferedReader br1 = new java.io.BufferedReader(new java.io.FileReader(file));
            String line = "";
            int numItems = 0;
            while ((line = br1.readLine()) != null) {
                numItems++;
            }
            br1.close();

            String[][] data = new String[numItems][5];
            int rowsIndex = 0;
            java.io.BufferedReader br2 = new java.io.BufferedReader(new java.io.FileReader(file));
            while ((line = br2.readLine()) != null) {
                String[] values = line.split(",");
                data[rowsIndex][0] = values[1]; // category_id
                data[rowsIndex][1] = values[2]; // item_name
                data[rowsIndex][2] = values[3]; // unit
                data[rowsIndex][3] = values[4]; // quantity
                data[rowsIndex][4] = values[5]; // ???
                rowsIndex++;
            }
            br2.close();
            
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
>>>>>>> Stashed changes
    
    /**
     * Removes the selected item from the inventory table after confirming with the user
     */
    private void removeSelectedItem() {
<<<<<<< Updated upstream
        // Check if a category is selected
=======
>>>>>>> Stashed changes
        if (selectedCategoryButton == null) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a category first.",
                "No Category Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
<<<<<<< Updated upstream
        
        // Check if an item is selected
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 1);
            String itemId = (String) tableModel.getValueAt(selectedRow, 0);
            
            // Confirmation dialog
=======

        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 0); // Item Name
            String unit = (String) tableModel.getValueAt(selectedRow, 1);     // Unit

            String itemNameWithUnit = (unit == null || unit.trim().isEmpty())
                ? itemName
                : itemName + " - " + unit;

            int itemId = ctrl.getItemIDWithUnit(itemNameWithUnit);
            if (itemId == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Item could not be found in the database.",
                    "Invalid Item",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

>>>>>>> Stashed changes
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove '" + itemName + "' from the inventory database?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
<<<<<<< Updated upstream
            
            if (choice == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(
                    this,
                    "Item '" + itemName + "' (ID: " + itemId + ") has been removed from inventory.",
                    "Item Removed",
                    JOptionPane.INFORMATION_MESSAGE
                );
=======

            if (choice == JOptionPane.YES_OPTION) {
                ctrl.removeItemFromDatabase(itemId);
                updateInventoryPanel(selectedCategoryIndexDatabase, selectedCategoryIndexArray);
                JOptionPane.showMessageDialog(
                    this,
                    "Item '" + itemName + "' has been removed from inventory.",
                    "Item Removed",
                    JOptionPane.INFORMATION_MESSAGE
                );
                borrowItemPanel.loadCategoryPanel(ctrl.getCategoryList());
>>>>>>> Stashed changes
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Please select an item to remove.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    
    /**
     * Adds a new empty row to the inventory table that the user can edit
     */
    private void addNewItem() {
        // Check if a category is selected
        if (selectedCategoryButton == null) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a category first.",
                "No Category Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
<<<<<<< Updated upstream
        // Generate a new item ID based on the highest existing ID
        String newId = generateNewItemId();
        
        // Add a new row with the generated ID and empty fields for the user to fill in
        tableModel.addRow(new Object[]{newId, "", "", "0"});
        
        // Select the newly added row
        int newRowIndex = tableModel.getRowCount() - 1;
        inventoryTable.setRowSelectionInterval(newRowIndex, newRowIndex);
        
        // Scroll to the new row
        inventoryTable.scrollRectToVisible(inventoryTable.getCellRect(newRowIndex, 0, true));
        
        // Optional: Start editing the item name cell
        inventoryTable.editCellAt(newRowIndex, 1);
        inventoryTable.getEditorComponent().requestFocus();
        
        JOptionPane.showMessageDialog(
            this,
            "New item added. Please fill in the details.",
            "New Item",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Generates a new item ID based on the highest existing ID
     * @return A new item ID string
     */
    private String generateNewItemId() {
        int highestNum = 0;
        String prefix = "LAB";
        
        // Find the highest item ID number
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String id = (String) tableModel.getValueAt(i, 0);
            if (id != null && id.startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(id.substring(prefix.length()));
                    if (num > highestNum) {
                        highestNum = num;
                    }
                } catch (NumberFormatException e) {
                    // Skip non-numeric IDs
=======
        // Request user input for item details
        String[] input = new String[3];
        String[] labels = {"Item Name", "Unit", "Quantity"};
        
        // Collect input for each field
        for (int i = 0; i < 3; i++) {
            boolean validInput = false;
            while (!validInput) {
                input[i] = JOptionPane.showInputDialog(
                    this,
                    "Enter " + labels[i] + ":",
                    "Add New Item",
                    JOptionPane.QUESTION_MESSAGE
                );
                
                // User cancelled
                if (input[i] == null) {
                    return;
                }
                
                // Validate input
                if (input[i].trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "You must enter a value for " + labels[i] + ".",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    validInput = true;
>>>>>>> Stashed changes
                }
            }
        }
        
<<<<<<< Updated upstream
        // Generate new ID with zero-padding to ensure consistent format
        return String.format("%s%03d", prefix, highestNum + 1);
=======
        // Validate quantity
        try {
            int qty = Integer.parseInt(input[2]);
            
            if (qty > 0) {
                // Use controller to add the item
                ctrl.addItemToDatabase(input[0], input[1], qty, selectedCategoryIndexDatabase);
                
                // Refresh the display
                updateInventoryPanel(selectedCategoryIndexDatabase, selectedCategoryIndexArray);
                
                JOptionPane.showMessageDialog(
                    this,
                    "New item added successfully.",
                    "Item Added",
                    JOptionPane.INFORMATION_MESSAGE
                );
                borrowItemPanel.loadCategoryPanel(ctrl.getCategoryList());
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than zero.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Quantity must be a valid number.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
            );
        }
>>>>>>> Stashed changes
    }
    
    private void styleActionButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 30));
        button.setBackground(branding.maroon);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    // Custom Box class to replace javax.swing.Box
    private static class Box {
        public static Component createVerticalStrut(int height) {
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(0, height));
            return panel;
        }
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
