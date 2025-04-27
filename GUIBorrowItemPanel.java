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
        "Measuring and Weighing",
        "Observation and Analysis",
        "Heating and Cooling",
        "Safety and Protective", 
        "Electrical and Electronic"
    };
    
    // Equipment items to display in a 4x3 grid
    private String[][] equipmentItems = {
        {"Beaker", "Erlenmeyer Flask", "Graduated Cylinder", "Test Tube"},
        {"Petri Dish", "Pipette", "Funnel", "Erlenmeyer Flask"},
        {"Funnel", "Beaker", "Petri Dish", "Test Tube"},
        {"Multimeter", "Power Supplies", "Face Shield", "Electrode"}
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
        JPanel equipmentPanel = createEquipmentPanel(backButton);
        contentPanel.add(equipmentPanel, BorderLayout.CENTER);
        
        mainContentPanel.add(contentPanel, BorderLayout.CENTER);
        
    }
    
    private JPanel createCategoryPanel() {
        // The panel that contains the category buttons (BoxLayout for vertical stack)
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBackground(branding.maroon);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        for (String category : categories) {
            JButton categoryButton = new JButton(category);
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
    
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (selectedCategoryButton != null) {
                        selectedCategoryButton.setBackground(branding.lightergray);
                    }
                    categoryButton.setBackground(branding.gray);
                    selectedCategoryButton = categoryButton;
                }
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
    
    private JPanel createEquipmentPanel(JButton backButton) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(branding.lightgray);
    
        // Layout settings
        int rows = 3;
        int cols = 4;
        int cardWidth = 140, cardHeight = 140;
        int hgap = 20, vgap = 20;
    
        JPanel gridPanel = new JPanel(new GridLayout(0, cols, hgap, vgap)); // 0 rows = auto, 4 columns fixed
        gridPanel.setBackground(branding.lightgray);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Calculate preferred size to force 4 columns
        int panelWidth = (cardWidth + hgap) * cols;
        int panelHeight = (cardHeight + vgap + 10) * rows;
        gridPanel.setPreferredSize(new Dimension(panelWidth, panelHeight)); // ensures fixed width and triggers vertical scroll
    
        for (int row = 0; row < equipmentItems.length; row++) {
            for (int col = 0; col < equipmentItems[row].length; col++) {
                String itemName = equipmentItems[row][col];
    
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
        }
    
        // Scroll pane with vertical-only scrolling
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        branding.reskinScrollBar(scrollPane, branding.gray);
    
        // Add components
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = createButtonPanel(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        return mainPanel;
    }
    
    
    
    private JPanel createButtonPanel(JButton backButton) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(branding.lightgray);
        
        // Use the provided back button
        backButton.setText("Go Back");
        styleActionButton(backButton);
        
        JButton addToBasketButton = new JButton("Add To Basket");
        JButton continueButton = new JButton("Continue");
        
        styleActionButton(addToBasketButton);
        styleActionButton(continueButton);
        
        buttonPanel.add(backButton);
        buttonPanel.add(addToBasketButton);
        buttonPanel.add(continueButton);
        
        return buttonPanel;
    }
    
    private void styleActionButton(JButton button) {
        button.setPreferredSize(new Dimension(170, 40));
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