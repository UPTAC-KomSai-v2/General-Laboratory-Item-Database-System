import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class GUIUpdateInventoryPanel extends JPanel {
    private Branding branding;
    private JPanel mainContentPanel, categoryPanel;
    private JButton selectedCategoryButton = null, removeItemButton, addItemButton;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JPanel inventoryPanel; // Panel to hold the inventory table
    private Controller ctrl;
    private Queries queries = new Queries();
    private int selectedCategoryIndexDatabase, selectedCategoryIndexArray;

    public GUIUpdateInventoryPanel(Controller ctrl, Branding branding, JButton backButton) {
        this.ctrl = ctrl;
        this.branding = branding;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(900, 500));
        
        // Create main content panel
        initializeMainContentPanel(backButton);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    // Categories of laboratory equipment
    private String[] categories = {
        "Biological Materials",
        "Consumables and Miscellaneous",
        "Electrical Equipment",
        "Glassware/Plasticware",
        "Lab Tools and Accessories",
        "Measuring and Analytical Instruments",
        "Safety Equipment",
        "Storage Containers"
    };
    
    // // Sample inventory data for each category
    // private String[][][] categoryInventoryData = {
    //     // Glassware and Plasticware
    //     {
    //         {"LAB001", "Beaker (100ml)", "Unit", "35"},
    //         {"LAB002", "Erlenmeyer Flask", "Unit", "18"},
    //         {"LAB009", "Graduated Cylinder", "Unit", "22"},
    //         {"LAB013", "Test Tube", "Pack", "45"},
    //         {"LAB017", "Pipette", "Unit", "12"},
    //         {"LAB024", "Glass Funnel", "Unit", "10"}
    //     },
    //     // Measuring and Analytical Instruments
    //     {
    //         {"LAB003", "Digital Scale", "Unit", "7"},
    //         {"LAB014", "Microscope", "Unit", "5"},
    //         {"LAB021", "Thermometers", "Unit", "18"},
    //         {"LAB028", "pH Meter", "Unit", "4"},
    //         {"LAB033", "Lab Timer", "Unit", "14"}
    //     },
    //     // Lab Tools and Accessories
    //     {
    //         {"LAB005", "Bunsen Burner", "Unit", "8"},
    //         {"LAB018", "Forceps", "Unit", "20"},
    //         {"LAB019", "Scalpel Blades", "Box", "10"},
    //         {"LAB028", "Stirring Rods", "Pack", "15"},
    //         {"LAB035", "Burette Stand", "Unit", "6"}
    //     },
    //     // Consumables and Miscellaneous
    //     {
    //         {"LAB006", "Laboratory Gloves", "Box", "45"},
    //         {"LAB011", "pH Test Strips", "Box", "40"},
    //         {"LAB012", "Distilled Water", "Liter", "50"},
    //         {"LAB022", "Alcohol Swabs", "Box", "35"},
    //         {"LAB026", "Filter Paper", "Pack", "25"},
    //         {"LAB031", "Ethanol Solution", "Liter", "12"},
    //         {"LAB036", "Rubber Stoppers", "Pack", "20"}
    //     },
    //     // Storage Containers
    //     {
    //         {"LAB004", "Specimen Containers", "Pack", "30"},
    //         {"LAB017", "Centrifuge Tubes", "Pack", "25"},
    //         {"LAB027", "Reagent Bottles", "Unit", "20"},
    //         {"LAB032", "Autoclave Bags", "Pack", "18"}
    //     },
    //     // Biological Materials
    //     {
    //         {"LAB001", "Microscope Slides", "Box", "15"},
    //         {"LAB003", "Petri Dishes", "Pack", "30"},
    //         {"LAB023", "Glass Slides", "Box", "22"},
    //         {"LAB024", "Cover Slips", "Box", "22"}
    //     },
    //     // Electrical Equipment
    //     {
    //         {"LAB014", "Digital Scale", "Unit", "7"},
    //         {"LAB020", "Multimeter", "Unit", "5"},
    //         {"LAB029", "Power Supply", "Unit", "3"},
    //         {"LAB034", "Hot Plate", "Unit", "8"}
    //     },
    //     // Safety Equipment
    //     {
    //         {"LAB007", "Safety Goggles", "Unit", "20"},
    //         {"LAB013", "Lab Coats", "Unit", "15"},
    //         {"LAB025", "Lab Notebook", "Unit", "30"},
    //         {"LAB030", "Safety Face Shield", "Unit", "8"}
    //     }
    // };
    
    private void initializeMainContentPanel(JButton backButton) {
        // Main panel with border layout
        mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBorder(BorderFactory.createLineBorder(branding.maroon, 2));
        
        // Create content panel with border layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(branding.maroon, 1));
        
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
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBackground(branding.maroon);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
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
                selectedCategoryIndexDatabase = queries.getCategoryID(categories[categoryIndex]);
                selectedCategoryIndexArray = categoryIndex;
                System.out.println("Selected Category ID from Database: " + selectedCategoryIndexDatabase);
                System.out.println("Selected Category ID from Array: " + selectedCategoryIndexArray);
                updateInventoryPanel(queries.getCategoryID(categories[categoryIndex]), categoryIndex);
            });
    
            categoryPanel.add(categoryButton);
            categoryPanel.add(Box.createVerticalStrut(10)); // Small space between buttons
        }
    
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
    }
    
    private JPanel createItemInventoryPanel(JButton backButton) {
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
        JPanel buttonPanel = createButtonPanel(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Store the tableContainerPanel as a property of the main panel for later access
        mainPanel.putClientProperty("tableContainerPanel", tableContainerPanel);
    
        return mainPanel;
    }
    
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
    private void updateInventoryPanel(int categoryIndexDatabase, int categoryIndex) {
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
        String[][] data = queries.getItemsPerCategory(categoryIndexDatabase);

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
        buttonPanel.setBackground(branding.lightgray);
        
        // Use the provided back button
        backButton.setText("Go Back");
        styleActionButton(backButton);
        
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

    public void refreshAddItemButton() {
        if (queries.getNoOfItems() != 0) {
            addItemButton.setText("Add Item");
            addItemButton.addActionListener(e -> addNewItem());
            removeItemButton.setEnabled(true);
        } else {
            removeItemButton.setEnabled(false);
            addItemButton.setText("Import From File");
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
            String filepath = null;
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files (*.csv)", "csv");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog(chooser);

            if(result == JFileChooser.APPROVE_OPTION){
                File selectedFile = chooser.getSelectedFile();
                filepath = selectedFile.getAbsolutePath();
                
                System.out.println(filepath);
            } else {
                JOptionPane.showMessageDialog(null, "Opening file terminated.", "Terminated", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            BufferedReader br1 = new BufferedReader(new FileReader(filepath));
            String line = "";
            int numItems = 0;
            while ((line = br1.readLine()) != null) {
                numItems++;
            }

            String[][] data = new String[numItems][5];
            int rowsIndex = 0;
            BufferedReader br2 = new BufferedReader(new FileReader(filepath));
            while ((line = br2.readLine()) != null) {
                String[] values = line.split(",");
                data[rowsIndex][0] = values[1];
                data[rowsIndex][1] = values[2];
                data[rowsIndex][2] = values[3];
                data[rowsIndex][3] = values[4];
                data[rowsIndex][4] = values[5];
                rowsIndex++;
            }

            br1.close();
            br2.close();

            queries.importToItems(filepath, data);
            return true;
        } catch (IOException e) {}

        return true;
    }
    
    /**
     * Removes the selected item from the inventory table after confirming with the user
     */
    private void removeSelectedItem() {
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
        
        // Check if an item is selected
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 1);
            String itemId = (String) tableModel.getValueAt(selectedRow, 0);
            
            // Confirmation dialog
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove '" + itemName + "' from the inventory database?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(
                    this,
                    "Item '" + itemName + "' (ID: " + itemId + ") has been removed from inventory.",
                    "Item Removed",
                    JOptionPane.INFORMATION_MESSAGE
                );
                queries.removeItemFromDatabase(Integer.parseInt(itemId));
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
        
        // Generate a new item ID based on the highest existing ID
        // String newId = generateNewItemId();
        
        // Add a new row with the generated ID and empty fields for the user to fill in
        tableModel.addRow(new Object[]{"", "", "", "0"});
        
        // Select the newly added row
        int newRowIndex = tableModel.getRowCount() - 1;
        inventoryTable.setRowSelectionInterval(newRowIndex, newRowIndex);
        
        // Scroll to the new row
        inventoryTable.scrollRectToVisible(inventoryTable.getCellRect(newRowIndex, 0, true));
        
        // // Optional: Start editing the item name cell
        // inventoryTable.editCellAt(newRowIndex, 1);
        // inventoryTable.getEditorComponent().requestFocus();
        
        // JOptionPane.showMessageDialog(
        //     this,
        //     "New item added. Please fill in the details.",
        //     "New Item",
        //     JOptionPane.INFORMATION_MESSAGE
        // );

        // Request user input for columns 1–3 (index 0–2)
        String input[] = new String[3];
        for (int col = 1; col <= 3; col++) {
            boolean validInput = false;
            while (!validInput) {
                input[col-1] = JOptionPane.showInputDialog(
                    this,
                    "Enter value for " + inventoryTable.getColumnName(col) + ":"
                );
                if (input[col-1] == null || input[col-1].trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "You must enter a value for " + inventoryTable.getColumnName(col) + ".",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    tableModel.setValueAt(input[col-1].trim(), newRowIndex, col);
                    validInput = true;
                }
            }
        }

        try {
            int qty = Integer.parseInt(input[2]);

            if(qty > 0) {
                JOptionPane.showMessageDialog(
                this,
                "New item added. You may now edit other details if needed.",
                "New Item",
                JOptionPane.INFORMATION_MESSAGE
                );

                queries.addItemToDatabase(input[0], input[1], qty, selectedCategoryIndexDatabase);
                updateInventoryPanel(selectedCategoryIndexDatabase, selectedCategoryIndexArray);
            } else {
                JOptionPane.showMessageDialog(
                this,
                "Quantity input is invalid.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
                );
            }
            
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(
            this,
            "Quantity input is invalid.",
            "Invalid Input",
            JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    /**
     * Generates a new item ID based on the highest existing ID
     * @return A new item ID string
     */
    // private String generateNewItemId() {
    //     int highestNum = 0;
    //     String prefix = "LAB";
        
    //     // Find the highest item ID number
    //     for (int i = 0; i < tableModel.getRowCount(); i++) {
    //         String id = (String) tableModel.getValueAt(i, 0);
    //         if (id != null && id.startsWith(prefix)) {
    //             try {
    //                 int num = Integer.parseInt(id.substring(prefix.length()));
    //                 if (num > highestNum) {
    //                     highestNum = num;
    //                 }
    //             } catch (NumberFormatException e) {
    //                 // Skip non-numeric IDs
    //             }
    //         }
    //     }
        
    //     // Generate new ID with zero-padding to ensure consistent format
    //     return String.format("%s%03d", prefix, highestNum + 1);
    // }
    
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
}