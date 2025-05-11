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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.TableCellRenderer;

public class GUIUpdateInventoryPanel extends JPanel {
    private Branding branding;
    private JPanel mainContentPanel, categoryPanelContents;
    private JButton selectedCategoryButton = null, removeItemButton, addItemButton;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JPanel inventoryPanel; // Panel to hold the inventory table
    private Controller ctrl;
    private int currentCategoryID = -1;
    private Map<Integer, JPanel> categoryGridPanels = new HashMap<>();
    
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
        categoryPanelContents = new JPanel();
        categoryPanelContents.setLayout(new BoxLayout(categoryPanelContents, BoxLayout.Y_AXIS));
        categoryPanelContents.setBackground(branding.maroon);
        categoryPanelContents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categoryPanelContents.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel categoryPanel = new JPanel(new GridBagLayout());
        categoryPanel.setBackground(branding.maroon);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        categoryPanel.add(categoryPanelContents, gbc);
        categoryPanel.setPreferredSize(new Dimension(220, 0)); // Force fixed width like before
        return categoryPanel;
    }

    public void LoadCategoryPanel(List<String[]> categoryList) {
        categoryPanelContents.removeAll();
        int panelCount = 1;
        for (String[] category : categoryList) {
            System.out.printf("Loading Panel (%d/%d)\n",  panelCount++, categoryList.size());
            int categoryId = Integer.parseInt(category[0]);
            String categoryName = category[1];

            JButton categoryButton = new JButton(categoryName);
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

            categoryButton.putClientProperty("categoryId", categoryId);
            categoryButton.putClientProperty("categoryName", categoryName);
            
            // Hover effects
            categoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    categoryButton.setBackground(branding.lightgray);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (getSelectedCategoryButton() != categoryButton) {
                        categoryButton.setBackground(branding.lightergray);
                    }
                }
            });

            // Action listener
            categoryButton.addActionListener(e -> {
                JButton currentSelectedButton = getSelectedCategoryButton();
                if (currentSelectedButton != null) {
                    currentSelectedButton.setBackground(branding.lightergray);
                }

                categoryButton.setBackground(branding.gray);
                setSelectedCategoryButton(categoryButton);

                int selectedCategoryId = (int) categoryButton.getClientProperty("categoryId");
                String selectedCategoryName = (String) categoryButton.getClientProperty("categoryName");
                setCurrentCategoryID(selectedCategoryId);

                // Update the inventory panel with the selected category
                updateInventoryPanel(selectedCategoryId, selectedCategoryName);
            });

            JPanel tablePanel = createInventoryTablePanel(categoryId);
            categoryGridPanels.put(categoryId, tablePanel);

            categoryPanelContents.add(categoryButton);
            categoryPanelContents.add(Box.createVerticalStrut(10));
        }

        categoryPanelContents.revalidate();
        categoryPanelContents.repaint();
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

    // Method to update inventory panel based on selected category
    private void updateInventoryPanel(int categoryId, String categoryName) {
        if (inventoryPanel == null) return;
    
        JPanel tableContainerPanel = (JPanel) inventoryPanel.getClientProperty("tableContainerPanel");
        if (tableContainerPanel == null) return;
    
        tableContainerPanel.removeAll();
    
        // Get cached panel
        JPanel cachedPanel = categoryGridPanels.get(categoryId);
        if (cachedPanel != null) {
            tableContainerPanel.add(cachedPanel, BorderLayout.CENTER);
        }
    
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(branding.lightgray);
        JLabel headerLabel = new JLabel(categoryName + " Inventory");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(branding.maroon);
        headerPanel.add(headerLabel);
        tableContainerPanel.add(headerPanel, BorderLayout.NORTH);
    
        tableContainerPanel.revalidate();
        tableContainerPanel.repaint();
    
        refreshAddItemButton();
    }
    
    private JPanel createInventoryTablePanel(int categoryId) {
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(branding.lightgray);
        Border lineBorder = BorderFactory.createLineBorder(branding.maroon, 5);
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        CompoundBorder compoundBorder = new CompoundBorder(emptyBorder, lineBorder);
        tablePanel.setBorder(compoundBorder);
    
        String[][] data = ctrl.getItemsPerCategory(categoryId);
        // Add checkbox column as the first column
        String[] columnNames = {"Select", "Item ID", "Item Name", "Unit", "Quantity"};
    
        // Create a table model that properly handles the checkbox column
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only checkbox column is editable
            }
        };
        
        // Add data to the table model with checkboxes
        if (data != null) {
            for (String[] row : data) {
                Object[] rowData = new Object[5];
                rowData[0] = Boolean.FALSE; // Checkbox (unchecked by default)
                rowData[1] = row[0]; // Item ID
                rowData[2] = row[1]; // Item Name
                rowData[3] = row[2]; // Unit
                rowData[4] = row[3]; // Quantity
                tableModel.addRow(rowData);
            }
        }
    
        inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(50);
        inventoryTable.setForeground(branding.maroon);
        inventoryTable.setBackground(branding.lightgray);
        
        // Set checkbox column width
        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        inventoryTable.getColumnModel().getColumn(0).setMaxWidth(50);
        
        // Make sure table properly renders Boolean values as checkboxes
        TableCellRenderer checkboxRenderer = inventoryTable.getDefaultRenderer(Boolean.class);
        inventoryTable.getColumnModel().getColumn(0).setCellRenderer(checkboxRenderer);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
    
        gbc.gridy = 0;
        gbc.ipady = 20;
        tablePanel.add(inventoryTable.getTableHeader(), gbc);
    
        gbc.gridy = 1;
        gbc.ipady = 0;
        gbc.weighty = 1;
        tablePanel.add(inventoryTable, gbc);
    
        return tablePanel;
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
    
    private JPanel createButtonPanel(JButton backButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(branding.lightgray);
        
        // Use the provided back button
        backButton.setText("Go Back");
        styleActionButton(backButton);
        
        // Update the remove item button text to reflect multiple selection capability
        removeItemButton = new JButton("Remove Item(s)");
        addItemButton = new JButton("Add Item");
        
        styleActionButton(removeItemButton);
        styleActionButton(addItemButton);
        
        // Add action listener for Remove Item button
        removeItemButton.addActionListener(e -> removeSelectedItems());
        
        buttonPanel.add(backButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(addItemButton);
        
        return buttonPanel;
    }

    public void refreshAddItemButton() {
        // Get the number of items from the controller
        int itemCount = ctrl.getNoOfItems();
        
        // Remove existing action listeners
        for (ActionListener listener : addItemButton.getActionListeners()) {
            addItemButton.removeActionListener(listener);
        }
        
        if (itemCount != 0) {
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

            // Use the controller to import items
            ctrl.importItemsFromCSV(filepath, data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Removes multiple selected items from the inventory table after confirming with the user
     */
    private void removeSelectedItems() {
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
        
        // Get all selected items
        List<Integer> selectedRows = new ArrayList<>();
        List<String> selectedItemNames = new ArrayList<>();
        List<Integer> selectedItemIds = new ArrayList<>();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                selectedRows.add(i);
                selectedItemNames.add((String) tableModel.getValueAt(i, 2));
                selectedItemIds.add(Integer.parseInt((String) tableModel.getValueAt(i, 1)));
            }
        }
        
        // Check if any items are selected
        if (selectedRows.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please select at least one item to remove.",
                "No Items Selected",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Confirmation dialog
        String message = selectedRows.size() == 1 
            ? "Are you sure you want to remove '" + selectedItemNames.get(0) + "' from the inventory database?"
            : "Are you sure you want to remove these " + selectedRows.size() + " items from the inventory database?";
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            message,
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            // Remove items in reverse order to avoid index shifting issues
            for (int i = selectedRows.size() - 1; i >= 0; i--) {
                int row = selectedRows.get(i);
                int itemId = selectedItemIds.get(i);
                
                // Use controller to remove the item
                ctrl.removeItemFromDatabase(itemId);
                
                // Update the table model
                tableModel.removeRow(row);
            }
            
            String successMessage = selectedRows.size() == 1 
                ? "Item '" + selectedItemNames.get(0) + "' has been removed from inventory."
                : selectedRows.size() + " items have been removed from inventory.";
            
            JOptionPane.showMessageDialog(
                this,
                successMessage,
                "Items Removed",
                JOptionPane.INFORMATION_MESSAGE
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
        
        // Add a new row with empty fields for the user to fill in
        // First column is checkbox (false), then other columns
        tableModel.addRow(new Object[]{Boolean.FALSE, "", "", "", "0"});
        
        // Select the newly added row
        int newRowIndex = tableModel.getRowCount() - 1;
        inventoryTable.setRowSelectionInterval(newRowIndex, newRowIndex);
        
        // Scroll to the new row
        inventoryTable.scrollRectToVisible(inventoryTable.getCellRect(newRowIndex, 0, true));
        
        // Request user input for columns 2–4 (index 1–3)
        String input[] = new String[3];
        for (int col = 2; col <= 4; col++) {
            boolean validInput = false;
            while (!validInput) {
                input[col-2] = JOptionPane.showInputDialog(
                    this,
                    "Enter value for " + inventoryTable.getColumnName(col) + ":"
                );
                if (input[col-2] == null || input[col-2].trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "You must enter a value for " + inventoryTable.getColumnName(col) + ".",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    tableModel.setValueAt(input[col-2].trim(), newRowIndex, col);
                    validInput = true;
                }
            }
        }

        try {
            int qty = Integer.parseInt(input[2]);
            int categoryId = getCurrentCategoryID();

            if(qty > 0) {
                // Use controller to add the item
                ctrl.addItemToDatabase(input[0], input[1], qty, categoryId);
                
                // Update the panel
                ctrl.refreshCachedData(); // Ensure latest DB state is reflected
                updateInventoryPanel(categoryId, getCategoryNameById(categoryId));
                
                JOptionPane.showMessageDialog(
                    this,
                    "New item added. You may now edit other details if needed.",
                    "New Item",
                    JOptionPane.INFORMATION_MESSAGE
                );
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
    
    // Helper method to get the category name by ID
    private String getCategoryNameById(int categoryId) {
        if (selectedCategoryButton != null) {
            return (String) selectedCategoryButton.getClientProperty("categoryName");
        }
        
        // Fallback to getting it from the controller
        return ctrl.getCategoryName(categoryId);
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
    
    public JButton getSelectedCategoryButton() {
        return selectedCategoryButton;
    }

    public void setSelectedCategoryButton(JButton selectedCategoryButton) {
        this.selectedCategoryButton = selectedCategoryButton;
    }

    public int getCurrentCategoryID() {
        return currentCategoryID;
    }

    public void setCurrentCategoryID(int currentCategoryID) {
        this.currentCategoryID = currentCategoryID;
    }
}