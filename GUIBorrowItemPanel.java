import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIBorrowItemPanel extends JPanel{
    private CardLayout cardLayout;
    private Branding branding;
    private JPanel screen1, screen2, screen3;
    private JButton selectedCategoryButton = null;
    private JPanel scrn2BorrowedItemsContentPanel;
    private JPanel equipmentPanel;  // Declare equipmentPanel as an instance variable
    private JPanel selectedItemCard = null; // Track the currently selected equipment item
    private JButton addToBasketButton, continueButton; // Track the Add To Basket button
    
    // Data structure to keep track of items in the basket
    private List<BasketItem> basketItems = new ArrayList<>();
    // Map to quickly check if an item is in the basket
    private Map<String, BasketItem> basketItemsMap = new HashMap<>();
    
    // Class to represent an item in the basket
    private static class BasketItem {
        String category;
        String itemName;
        
        public BasketItem(String category, String itemName) {
            this.category = category;
            this.itemName = itemName;
        }
        
        @Override
        public String toString() {
            return "Category: " + category + ", Item: " + itemName;
        }
    }
    
    public GUIBorrowItemPanel(Branding branding, JButton backButton) {
        this.branding = branding;
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(900, 500));
        
        // Create main content panel
        initializeScreen1(backButton);
        initializeScreen2();
        initializeScreen3();
        add(screen1, "Panel 1");
        add(screen2, "Panel 2");
        add(screen3, "Panel 3");
        
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
    
    private void initializeScreen1(JButton backButton) {
        // Main panel with border layout
        screen1 = new JPanel(new BorderLayout());
        screen1.setBorder(BorderFactory.createLineBorder(branding.maroon, 2));
        
        // Create content panel with border layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(branding.maroon, 1));
        
        // Create and add category panel to the left side
        JPanel categoryPanel = createCategoryPanel();
        contentPanel.add(categoryPanel, BorderLayout.WEST);
        
        // Create equipment display panel
        equipmentPanel = createEquipmentPanel(backButton);
        contentPanel.add(equipmentPanel, BorderLayout.CENTER);
        
        screen1.add(contentPanel, BorderLayout.CENTER);
        
        // Show the initial "No Category is Selected" message
        showNoCategorySelectedMessage();
    }
    
    private void initializeScreen2(){
        screen2 = new JPanel();
        screen2.setLayout(new GridBagLayout());
        screen2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        screen2.setBackground(branding.maroon);

        scrn2BorrowedItemsContentPanel = new JPanel();
        scrn2BorrowedItemsContentPanel.setLayout(new BoxLayout(scrn2BorrowedItemsContentPanel, BoxLayout.Y_AXIS));
        scrn2BorrowedItemsContentPanel.setBackground(branding.maroon);
        scrn2BorrowedItemsContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        
        

        String[] borrowerInfo = {"Example, John Pork", "69696969", "12:01 PM", "13 Feb 2023"};

        JPanel scrn2BorrowerInfoPanel = new JPanel();
        scrn2BorrowerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 0));
        scrn2BorrowerInfoPanel.setLayout(new GridBagLayout());
        scrn2BorrowerInfoPanel.setOpaque(false);
        int i = 0;
        JLabel borrowerLabel = new JLabel("Borrower: ");
        JLabel borrowerNameLabel = new JLabel(borrowerInfo[i++]);
        JLabel studentIdLabel = new JLabel(borrowerInfo[i++]);
        JLabel timeLabel = new JLabel(borrowerInfo[i++]);
        JLabel dateLabel = new JLabel(borrowerInfo[i++]);

        borrowerLabel.setForeground(branding.white);
        borrowerNameLabel.setForeground(branding.white);
        studentIdLabel.setForeground(branding.white);
        timeLabel.setForeground(branding.white);
        dateLabel.setForeground(branding.white);

        JPanel borrowerPanel = new JPanel();
        JPanel borrowerNamePanel = new JPanel();
        JPanel studentIdPanel = new JPanel();
        JPanel timePanel = new JPanel();
        JPanel datePanel = new JPanel();

        borrowerPanel.setLayout(new BorderLayout());
        borrowerNamePanel.setLayout(new BorderLayout());
        studentIdPanel.setLayout(new BorderLayout());
        timePanel.setLayout(new BorderLayout());
        datePanel.setLayout(new BorderLayout());

        borrowerPanel.setOpaque(false);
        borrowerNamePanel.setOpaque(false);
        studentIdPanel.setOpaque(false);
        timePanel.setOpaque(false);
        datePanel.setOpaque(false);

        borrowerPanel.add(borrowerLabel, BorderLayout.WEST);
        borrowerNamePanel.add(borrowerNameLabel, BorderLayout.WEST);
        studentIdPanel.add(studentIdLabel, BorderLayout.WEST);
        timePanel.add(timeLabel, BorderLayout.WEST);
        datePanel.add(dateLabel, BorderLayout.WEST);

        GridBagConstraints scrn2BorrowerInfoPanelGBC = new GridBagConstraints();
        scrn2BorrowerInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
        scrn2BorrowerInfoPanelGBC.gridx = 0;
        scrn2BorrowerInfoPanelGBC.ipadx = 20;
        scrn2BorrowerInfoPanelGBC.weightx = 0.05;
        scrn2BorrowerInfoPanel.add(borrowerPanel, scrn2BorrowerInfoPanelGBC);
        scrn2BorrowerInfoPanelGBC.gridx++;
        scrn2BorrowerInfoPanelGBC.ipadx = 20;
        scrn2BorrowerInfoPanelGBC.weightx = 0.1;
        scrn2BorrowerInfoPanel.add(borrowerNamePanel, scrn2BorrowerInfoPanelGBC);
        scrn2BorrowerInfoPanelGBC.gridx++;
        scrn2BorrowerInfoPanelGBC.ipadx = 20;
        scrn2BorrowerInfoPanelGBC.weightx = 0.05;
        scrn2BorrowerInfoPanel.add(studentIdPanel, scrn2BorrowerInfoPanelGBC);
        scrn2BorrowerInfoPanelGBC.gridx++;
        scrn2BorrowerInfoPanelGBC.ipadx = 20;
        scrn2BorrowerInfoPanelGBC.weightx = 0.05;
        scrn2BorrowerInfoPanel.add(timePanel, scrn2BorrowerInfoPanelGBC);
        scrn2BorrowerInfoPanelGBC.gridx++;
        scrn2BorrowerInfoPanelGBC.ipadx = 20;
        scrn2BorrowerInfoPanelGBC.weightx = 0.5;
        scrn2BorrowerInfoPanel.add(datePanel, scrn2BorrowerInfoPanelGBC);

        JScrollPane scrn2ScrollContentPanel = new JScrollPane(scrn2BorrowedItemsContentPanel);
        scrn2ScrollContentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrn2ScrollContentPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrn2ScrollContentPanel.setBackground(branding.maroon);
        scrn2ScrollContentPanel.setBorder(null);
        branding.reskinScrollBar(scrn2ScrollContentPanel, branding.maroon);

        JPanel scrn2MenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        scrn2MenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 33));
        scrn2MenuPanel.setBackground(branding.lightgray);
        scrn2MenuPanel.setOpaque(true);

        JButton screen2BackBtn = new JButton("Go Back");
        JButton screen2ContinueBtn = new JButton("Continue");


        screen2BackBtn.setPreferredSize(new Dimension(150, 30));
        screen2BackBtn.setBackground(branding.maroon);
        screen2BackBtn.setForeground(branding.white);
        screen2BackBtn.addActionListener(e -> {
            cardLayout.previous(GUIBorrowItemPanel.this);
        });

        screen2ContinueBtn.setPreferredSize(new Dimension(150, 30));
        screen2ContinueBtn.setBackground(branding.maroon);
        screen2ContinueBtn.setForeground(branding.white);
        screen2ContinueBtn.addActionListener(e -> {
            cardLayout.next(GUIBorrowItemPanel.this);
        });



        scrn2MenuPanel.add(screen2BackBtn);
        scrn2MenuPanel.add(screen2ContinueBtn);

        GridBagConstraints screen2GBC = new GridBagConstraints();
        screen2GBC.fill = GridBagConstraints.BOTH;
        screen2GBC.weightx = 1;
        screen2GBC.weighty = 0.01;
        screen2GBC.gridy = 0;
        screen2.add(scrn2BorrowerInfoPanel, screen2GBC);
        screen2GBC.weighty = 0.9;
        screen2GBC.gridy++;
        screen2.add(scrn2ScrollContentPanel, screen2GBC);
        screen2GBC.weighty = 0.01;
        screen2GBC.gridy++;
        screen2.add(scrn2MenuPanel, screen2GBC);

    }

    private void initializeScreen3(){
        screen3 = new JPanel();
        screen3.setLayout(new GridBagLayout());
        screen3.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        screen3.setBackground(branding.lightgray);

        JButton screen3BackBtn = new JButton("Go Back");
        screen3BackBtn.addActionListener(e ->{
            cardLayout.previous(GUIBorrowItemPanel.this);
        });

        screen3.add(screen3BackBtn);
    }

    private void updateBasketDisplayPanel(){
        String[][] entries = {
            {"2x", "Beaker", "100 ml"},
            {"1x", "Petri Dish", ""}
        };
        
        for (String[] tuple: entries){
            int i = 0;
            
            JLabel quantityLabel = new JLabel(tuple[i++]);
            JLabel itemLabel = new JLabel(tuple[i++]);
            JLabel unitLabel = new JLabel(tuple[i++]);

            quantityLabel.setForeground(branding.maroon);
            itemLabel.setForeground(branding.maroon);
            unitLabel.setForeground(branding.maroon);

            JButton returnItemBtn = new JButton("Return");
            returnItemBtn.setPreferredSize(new Dimension(100, 35));
            returnItemBtn.setBackground(branding.maroon);
            returnItemBtn.setForeground(branding.white);
            
            
            JPanel quantityPanel = new JPanel();
            JPanel itemPanel = new JPanel();
            JPanel unitPanel = new JPanel();
            JPanel returnPanel = new JPanel();

            quantityPanel.setLayout(new BorderLayout());
            itemPanel.setLayout(new BorderLayout());
            unitPanel.setLayout(new BorderLayout());
            returnPanel.setLayout(new GridBagLayout());

            quantityPanel.setPreferredSize(new Dimension(10, 70));
            itemPanel.setPreferredSize(new Dimension(10, 70));
            unitPanel.setPreferredSize(new Dimension(10, 70));
            returnPanel.setPreferredSize(new Dimension(10, 70));
            
            quantityPanel.setOpaque(false);
            itemPanel.setOpaque(false);
            unitPanel.setOpaque(false);
            returnPanel.setOpaque(false);

            quantityPanel.add(quantityLabel, BorderLayout.WEST);
            itemPanel.add(itemLabel, BorderLayout.WEST);
            unitPanel.add(unitLabel, BorderLayout.WEST);
            returnPanel.add(returnItemBtn);

            JPanel tupleInfoPanel = new JPanel();
            tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
            tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 20));
            tupleInfoPanel.setLayout(new GridBagLayout());
            tupleInfoPanel.setBackground(branding.lightgray);
            
            GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
            tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            tupleInfoPanelGBC.gridx = 0;
            tupleInfoPanelGBC.ipadx = 20;
            tupleInfoPanelGBC.weightx = 0.05;
            tupleInfoPanel.add(quantityPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 20;
            tupleInfoPanelGBC.weightx = 0.1;
            tupleInfoPanel.add(itemPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 20;
            tupleInfoPanelGBC.weightx = 0.1;
            tupleInfoPanel.add(unitPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.fill = GridBagConstraints.NONE;
            tupleInfoPanelGBC.anchor = GridBagConstraints.EAST; // optional
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(returnPanel, tupleInfoPanelGBC);

            scrn2BorrowedItemsContentPanel.add(tupleInfoPanel);
            scrn2BorrowedItemsContentPanel.add(Box.createVerticalStrut(10)); //add gaps between touples
        }
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
        
        // Clear the grid panel and reset selected item
        gridPanel.removeAll();
        selectedItemCard = null;
        
        // Reset to GridLayout for displaying equipment items
        gridPanel.setLayout(new GridLayout(0, 4, 20, 20));
        
        // Get the current category name
        String currentCategory = categories[categoryIndex];
        
        // Add the equipment items for the selected category
        String[] categoryItems = equipmentItems[categoryIndex];
        int cardWidth = 140, cardHeight = 140;
        
        for (String itemName : categoryItems) {
            JPanel itemCard = new JPanel();
            itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));
            
            // Check if this item is in the basket and set background color accordingly
            BasketItem basketItem = basketItemsMap.get(itemName);
            if (basketItem != null) {
                itemCard.setBackground(Color.YELLOW);
            } else {
                itemCard.setBackground(Color.WHITE);
            }
            
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
            
            // Store the item name and category as client properties for reference
            itemCard.putClientProperty("itemName", itemName);
            itemCard.putClientProperty("category", currentCategory);
            
            // Add click listener to select the item
            itemCard.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Check if this item is already in basket
                    boolean isInBasket = basketItemsMap.containsKey(itemName);
                    
                    // Reset the previously selected item if any
                    if (selectedItemCard != null && selectedItemCard != itemCard) {
                        // Only reset to white if it's not in basket
                        String prevItemName = (String) selectedItemCard.getClientProperty("itemName");
                        if (!basketItemsMap.containsKey(prevItemName)) {
                            selectedItemCard.setBackground(Color.WHITE);
                        }
                    }
                    
                    // Select the current item if it's not already in basket
                    if (!isInBasket) {
                        itemCard.setBackground(Color.GRAY);
                        selectedItemCard = itemCard;
                    }
                }
                
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // Only highlight if not already selected or in basket
                    boolean isInBasket = basketItemsMap.containsKey(itemName);
                    if (!itemCard.getBackground().equals(Color.GRAY) && !isInBasket) {
                        itemCard.setBackground(new Color(245, 245, 245)); // Light hover effect
                    }
                    setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    // Reset hover effect if not selected or in basket
                    boolean isInBasket = basketItemsMap.containsKey(itemName);
                    if (!itemCard.getBackground().equals(Color.GRAY) && !isInBasket) {
                        itemCard.setBackground(Color.WHITE);
                    }
                    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            });

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
        addToBasketButton = new JButton("Add To Basket");
        continueButton = new JButton("Continue");
        
        styleActionButton(backButton);
        styleActionButton(addToBasketButton);
        styleActionButton(continueButton);
        
        // Add action listener to the Add To Basket button
        addToBasketButton.addActionListener(e -> {
            if (selectedItemCard != null) {
                String itemName = (String) selectedItemCard.getClientProperty("itemName");
                String category = (String) selectedItemCard.getClientProperty("category");
                
                // Check if this item is already in the basket
                if (!basketItemsMap.containsKey(itemName)) {
                    // Add to basket
                    BasketItem newItem = new BasketItem(category, itemName);
                    basketItems.add(newItem);
                    basketItemsMap.put(itemName, newItem);
                    
                    // Change the background color to yellow
                    selectedItemCard.setBackground(Color.YELLOW);
                    
                    // Print the basket contents for debugging
                    System.out.println("\n--- Current Basket Contents ---");
                    for (int i = 0; i < basketItems.size(); i++) {
                        System.out.println((i + 1) + ". " + basketItems.get(i));
                    }
                    System.out.println("-----------------------------\n");
                }
            }
        });

        continueButton.addActionListener(e -> {
            updateBasketDisplayPanel();
            cardLayout.next(GUIBorrowItemPanel.this);
        });
        
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