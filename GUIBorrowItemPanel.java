import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIBorrowItemPanel extends JPanel {
    private Branding branding;
    private JPanel mainContentPanel;
    private JButton selectedCategoryButton = null;
    private JPanel equipmentPanel;  // Declare equipmentPanel as an instance variable
    
    public GUIBorrowItemPanel(Branding branding, JButton backButton) {
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
        "Glassware and Plasticware",
        "Measuring and Analytical Instruments",
        "Lab Tools and Accessories",
        "Consumables and Miscellaneous",
        "Storage Containers", 
        "Biological Materials",
        "Electrical Equipment",
        "Safety Equipment"
    };
    
    // Equipment items
    private String[][] equipmentItems = {
        {"Beaker", "Erlenmeyer Flask", "Graduated Cylinder", "Test Tube", "Burette", "Pipette", "Funnel", "Test Tube Rack", 
         "Glass Stirring Rod", "Volumetric Flask", "Conical Flask", "Burette Stand", "Wash Bottle", "Dropping Bottle", "Desiccator", "Reagent Bottle"},
        {"Petri Dish", "Pipette", "Funnel", "Erlenmeyer Flask", "Bunsen Burner", "Thermometer", "Hydrometer", "Refractometer", 
         "Measuring Cup", "Beaker", "Volumetric Pipette", "Conical Flask", "Alcohol Lamp", "Glass Rod", "Chemical Balance", "Titrator"},
        {"Funnel", "Beaker", "Petri Dish", "Test Tube", "Pipette", "Volumetric Flask", "Safety Goggles", "Tongs", 
         "Bunsen Burner", "Forceps", "Test Tube Holder", "Iron Stand", "Clamp", "Lab Coat", "Face Shield", "Microscope"},
        {"Multimeter", "Power Supplies", "Face Shield", "Electrode", "Soldering Iron", "Oscilloscope", "Power Cord", "Extension Cord", 
         "Welding Machine", "Oscilloscope Probe", "Power Meter", "DC Motor", "Induction Motor", "Electric Fan", "Multimeter Probe", "Inverter"},
        {"Storage Box", "Plastic Bin", "Glass Bottle", "Storage Jar", "File Box", "Plastic Container", "Storage Drawer", "Tupperware", 
         "Trash Bin", "Cardboard Box", "Plastic Bucket", "Metal Container", "Metal Box", "Plastic Shelf", "Storage Cart", "Tool Box"},
        {"Petri Dish", "Microscope Slide", "Sterile Culture Medium", "Pipette", "Beaker", "Test Tube", "Dissection Kit", "Agar Plate", 
         "Culture Tube", "Test Tube Rack", "Inoculating Loop", "Sterile Pipette", "Gloves", "Safety Goggles", "Specimen Tray", "Incubator"},
        {"Multimeter", "Oscilloscope", "Power Supply", "Soldering Iron", "Test Leads", "Electrode", "Power Cord", "DC Motor", 
         "Induction Motor", "Electrical Wire", "Connector", "Electric Switch", "Breadboard", "Resistor", "Capacitor", "Battery Pack"},
        {"Face Shield", "Lab Coat", "Gloves", "Safety Goggles", "Fire Extinguisher", "First Aid Kit", "Eye Wash Station", "Fire Blanket", 
         "Safety Boots", "Gas Mask", "Lab Apron", "Safety Signs", "Ear Protection", "Fire Hose", "Spill Kit", "Fume Hood"}
    };
    
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
        
        // Create equipment display panel
        equipmentPanel = createEquipmentPanel(backButton);
        contentPanel.add(equipmentPanel, BorderLayout.CENTER);
        
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Show the initial "No Category is Selected" message
        showNoCategorySelectedMessage();
    }
    
    private JPanel createCategoryPanel() {
        // The panel that contains the category buttons (BoxLayout for vertical stack)
        JPanel categoryPanel = new JPanel();
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
            
            // Action listener to update equipment panel when a category is selected
            categoryButton.addActionListener(e -> {
                if (selectedCategoryButton != null) {
                    selectedCategoryButton.setBackground(branding.lightergray);
                }
                categoryButton.setBackground(branding.gray);
                selectedCategoryButton = categoryButton;
                
                // Update equipment display with items from the selected category
                updateEquipmentPanel(categoryIndex);
            });
    
            categoryPanel.add(categoryButton);
            categoryPanel.add(Box.createVerticalStrut(10)); // Small space between buttons
            
            // No default selection - all buttons start unselected
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
    
    private JPanel createEquipmentPanel(JButton backButton) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(branding.lightgray);
    
        // Layout with button panel at the bottom
        JPanel itemsContainerPanel = new JPanel(new BorderLayout());
        itemsContainerPanel.setBackground(branding.lightgray);
        
        // Create scrollable grid panel
        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 20, 20)); // 0 rows = auto, 4 columns fixed
        gridPanel.setBackground(branding.lightgray);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Scroll pane with vertical-only scrolling
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        branding.reskinScrollBar(scrollPane, branding.gray);
        
        itemsContainerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(itemsContainerPanel, BorderLayout.CENTER);
        
        // Add button panel at the bottom
        JPanel buttonPanel = createButtonPanel(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Store the gridPanel as a property of the main panel for later access
        mainPanel.putClientProperty("gridPanel", gridPanel);
    
        return mainPanel;
    }
    
    // Method to show "No Category is Selected" message
    private void showNoCategorySelectedMessage() {
        if (equipmentPanel == null) return;
        
        // Get the grid panel from the equipment panel
        JPanel gridPanel = (JPanel) equipmentPanel.getClientProperty("gridPanel");
        if (gridPanel == null) return;
        
        // Clear the grid panel
        gridPanel.removeAll();
        
        // Create a message panel
        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setBackground(branding.lightgray);
        
        JLabel messageLabel = new JLabel("No Category is Selected");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(branding.maroon);
        
        messagePanel.add(messageLabel);
        
        // Add the message panel to the grid panel
        gridPanel.setLayout(new BorderLayout());
        gridPanel.add(messagePanel, BorderLayout.CENTER);
        
        // Refresh the panel
        gridPanel.revalidate();
        gridPanel.repaint();
    }
    
    // Method to update equipment panel based on selected category
    private void updateEquipmentPanel(int categoryIndex) {
        if (equipmentPanel == null) return;
        
        // Get the grid panel from the equipment panel
        JPanel gridPanel = (JPanel) equipmentPanel.getClientProperty("gridPanel");
        if (gridPanel == null) return;
        
        // Clear the grid panel
        gridPanel.removeAll();
        
        // Reset to GridLayout for displaying equipment items
        gridPanel.setLayout(new GridLayout(0, 4, 20, 20));
        
        // Add the equipment items for the selected category
        String[] categoryItems = equipmentItems[categoryIndex];
        int cardWidth = 140, cardHeight = 140;
        
        for (String itemName : categoryItems) {
            JPanel itemCard = new JPanel();
            itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));
            itemCard.setBackground(Color.WHITE);
            itemCard.setBorder(BorderFactory.createLineBorder(branding.maroon, 1));
            itemCard.setPreferredSize(new Dimension(cardWidth, cardHeight));

            // Placeholder space (simulates an image)
            JPanel imagePlaceholder = new JPanel();
            imagePlaceholder.setPreferredSize(new Dimension(80, 60));
            imagePlaceholder.setMaximumSize(new Dimension(80, 60));
            imagePlaceholder.setBackground(new Color(240, 240, 240)); // light gray
            imagePlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel(itemName, JLabel.CENTER);
            nameLabel.setForeground(branding.maroon);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            itemCard.add(Box.createVerticalStrut(10));
            itemCard.add(imagePlaceholder);
            itemCard.add(Box.createVerticalStrut(5));
            itemCard.add(nameLabel);
            itemCard.add(javax.swing.Box.createVerticalGlue());

            gridPanel.add(itemCard);
        }
        
        // Refresh the panel to show the updated items
        gridPanel.revalidate();
        gridPanel.repaint();
    }
    
    private JPanel createButtonPanel(JButton backButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(branding.lightgray);
        
        // Ensure the back button is visible and has appropriate size
        backButton.setText("Go Back");
        JButton addToBasketButton = new JButton("Add To Basket");
        JButton continueButton = new JButton("Continue");
        
        styleActionButton(backButton);
        styleActionButton(addToBasketButton);
        styleActionButton(continueButton);
        
        // Ensure all buttons are added properly to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(addToBasketButton);
        buttonPanel.add(continueButton);
        
        return buttonPanel;
    }
    
    private void styleActionButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(branding.maroon);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 15));
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