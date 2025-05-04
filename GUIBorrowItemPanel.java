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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GUIBorrowItemPanel extends JPanel{
    private CardLayout cardLayout;
    private Branding branding;
    private JPanel screen1, screen2, screen3;
    private JButton selectedCategoryButton = null;
    private JPanel scrn2BorrowedItemsContentPanel;
    private JPanel equipmentPanel;  // Declare equipmentPanel as an instance variable
    private JButton addToBasketButton, continueButton; // Track the Add To Basket button
    
    // Data structure to keep track of items in the basket
    private List<BasketItem> basketItems = new ArrayList<>();
    // Map to quickly check if an item is in the basket
    private Map<String, BasketItem> basketItemsMap = new HashMap<>();
    
    private Map<String, JPanel> itemPanelsMap = new HashMap<>();

    private Set<JPanel> selectedItemCards = new HashSet<>();
    
    // Class to represent an item in the basket
    private static class BasketItem {
        String category;
        String itemName;
        int itemQuantity;
        
        public BasketItem(String category, String itemName) {
            this.category = category;
            this.itemName = itemName;
            this.itemQuantity = 1;
        }
        
        @Override
        public String toString() {
            return "Category: " + category + ", Item: " + itemName + ", Quantity: " + itemQuantity;
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
    
    private void initializeScreen2() {
        screen2 = new JPanel();
        screen2.setLayout(new GridBagLayout());
        screen2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        screen2.setBackground(branding.maroon);
    
        scrn2BorrowedItemsContentPanel = new JPanel();
        scrn2BorrowedItemsContentPanel.setLayout(new BoxLayout(scrn2BorrowedItemsContentPanel, BoxLayout.Y_AXIS)); // Set to vertical stack
        scrn2BorrowedItemsContentPanel.setBackground(branding.maroon);
        scrn2BorrowedItemsContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    
    
        JPanel scrn2BorrowerInfoPanel = new JPanel();
        scrn2BorrowerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 0));
        scrn2BorrowerInfoPanel.setLayout(new GridBagLayout());
        scrn2BorrowerInfoPanel.setOpaque(false);
    
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
        screen3.setBackground(branding.maroon);
    
        JPanel scrn3BorrowerInformationFormsPanel = new JPanel();
        scrn3BorrowerInformationFormsPanel.setLayout(new GridBagLayout());
        scrn3BorrowerInformationFormsPanel.setBackground(branding.maroon);
        scrn3BorrowerInformationFormsPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    
        JPanel formsHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel formsHeaderLabel = new JLabel("Borrower(s) Information");
        formsHeaderLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
        formsHeaderLabel.setForeground(branding.white);
        formsHeaderPanel.add(formsHeaderLabel);
        formsHeaderPanel.setOpaque(false);
    
        JPanel formsBorrowerInfoInputPanel = new JPanel();
        formsBorrowerInfoInputPanel.setLayout(new GridBagLayout());
        formsBorrowerInfoInputPanel.setPreferredSize(new Dimension(700, 150));
        formsBorrowerInfoInputPanel.setBorder(BorderFactory.createLineBorder(branding.white, 1));
        formsBorrowerInfoInputPanel.setOpaque(false);
    
        JLabel formStudentNumberLabel = new JLabel("Student Number");
        JLabel formFullNameLabel = new JLabel("Full Name");
        JLabel formEmailAddressLabel = new JLabel("Email Address");
        JLabel formContactNumberLabel = new JLabel("Contact Number");
        JLabel formDegreeProgramLabel = new JLabel("Degree Program");
    
        formStudentNumberLabel.setForeground(branding.white);
        formFullNameLabel.setForeground(branding.white);
        formEmailAddressLabel.setForeground(branding.white);
        formContactNumberLabel.setForeground(branding.white);
        formDegreeProgramLabel.setForeground(branding.white);
        
        JPanel borrowerInfoInputHeaderPanel = new JPanel(new GridBagLayout());
        borrowerInfoInputHeaderPanel.setPreferredSize(new Dimension(800, 20));
        borrowerInfoInputHeaderPanel.setOpaque(false);

        GridBagConstraints borrowerInfoInputHeaderPanelGBC = new GridBagConstraints();
        borrowerInfoInputHeaderPanelGBC.fill = GridBagConstraints.NONE;
        borrowerInfoInputHeaderPanelGBC.anchor = GridBagConstraints.WEST;
        borrowerInfoInputHeaderPanelGBC.gridx = 0;
        borrowerInfoInputHeaderPanelGBC.weightx = 0.01;
        borrowerInfoInputHeaderPanel.add(formStudentNumberLabel, borrowerInfoInputHeaderPanelGBC);
        borrowerInfoInputHeaderPanelGBC.gridx++;
        borrowerInfoInputHeaderPanelGBC.weightx = 0.05;
        borrowerInfoInputHeaderPanel.add(formFullNameLabel, borrowerInfoInputHeaderPanelGBC);
        borrowerInfoInputHeaderPanelGBC.gridx++;
        borrowerInfoInputHeaderPanelGBC.weightx = 0.04;
        borrowerInfoInputHeaderPanel.add(formEmailAddressLabel, borrowerInfoInputHeaderPanelGBC);
        borrowerInfoInputHeaderPanelGBC.gridx++;
        borrowerInfoInputHeaderPanelGBC.weightx = 0.02;
        borrowerInfoInputHeaderPanel.add(formContactNumberLabel, borrowerInfoInputHeaderPanelGBC);
        borrowerInfoInputHeaderPanelGBC.gridx++;
        borrowerInfoInputHeaderPanelGBC.weightx = 0.04;
        borrowerInfoInputHeaderPanel.add(formDegreeProgramLabel, borrowerInfoInputHeaderPanelGBC);
    
        // Create a panel to hold all borrower info panels
        JPanel borrowerInfoInputPanel = new JPanel();
        borrowerInfoInputPanel.setLayout(new BoxLayout(borrowerInfoInputPanel, BoxLayout.Y_AXIS));
        borrowerInfoInputPanel.setBackground(branding.maroon);
    
        // Lists to store all borrower components for later data retrieval
        List<JPanel> borrowerPanels = new ArrayList<>();
        List<JTextField> studentNumberFields = new ArrayList<>();
        List<JTextField> fullNameFields = new ArrayList<>();
        List<JTextField> emailAddressFields = new ArrayList<>();
        List<JTextField> contactNumberFields = new ArrayList<>();
        List<JComboBox<String>> degreeProgramComboBoxes = new ArrayList<>();
        
        String[] courseOptionsData = {"Select an Option", "BIO 123", "CMSC 127", "MATH 55"};
        JComboBox<String> courseOptions = new JComboBox<>(courseOptionsData);

        String[] sectionOptionsData = {"Select an Option", "A", "NM", "MF"};
        JComboBox<String> sectionOptions = new JComboBox<>(sectionOptionsData);

        String[] instructorOptionsData = {"Select an Option", "Prof A.", "Prof B.", "Prof C."};
        JComboBox<String> instructorOptions = new JComboBox<>(instructorOptionsData);

        // Create first borrower panel and add it
        JPanel firstBorrowerPanel = createBorrowerInfoPanel(
            studentNumberFields, fullNameFields, emailAddressFields, 
            contactNumberFields, degreeProgramComboBoxes
        );
        borrowerPanels.add(firstBorrowerPanel);
        borrowerInfoInputPanel.add(firstBorrowerPanel);
    
        JScrollPane borrowerInfoInputScrollPanel = new JScrollPane(borrowerInfoInputPanel);
        borrowerInfoInputScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        borrowerInfoInputScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        borrowerInfoInputScrollPanel.setBackground(branding.maroon);
        borrowerInfoInputScrollPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, branding.maroon));
        branding.reskinScrollBar(borrowerInfoInputScrollPanel, branding.maroon);
        
        GridBagConstraints formsBorrowerInfoInputPanelGBC = new GridBagConstraints();
        formsBorrowerInfoInputPanelGBC.fill = GridBagConstraints.BOTH;
        formsBorrowerInfoInputPanelGBC.weightx = 1;
        formsBorrowerInfoInputPanelGBC.weighty = 0.001;
        formsBorrowerInfoInputPanelGBC.gridy = 0;
        formsBorrowerInfoInputPanel.add(borrowerInfoInputHeaderPanel, formsBorrowerInfoInputPanelGBC);
        formsBorrowerInfoInputPanelGBC.weighty = 0.8;
        formsBorrowerInfoInputPanelGBC.gridy++;
        formsBorrowerInfoInputPanel.add(borrowerInfoInputScrollPanel, formsBorrowerInfoInputPanelGBC);
    
        JPanel formsAddRemoveBorrowerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        formsAddRemoveBorrowerPanel.setOpaque(false);

        JButton formsAddBorrowerBtn = new JButton("Add More Borrower");
        formsAddBorrowerBtn.setPreferredSize(new Dimension(165, 25));
        formsAddBorrowerBtn.setBackground(branding.lightergray);
        formsAddBorrowerBtn.setForeground(branding.maroon);

        JButton formsRemoveBorrowerBtn = new JButton("Remove Borrower");
        formsRemoveBorrowerBtn.setPreferredSize(new Dimension(165, 25));
        formsRemoveBorrowerBtn.setBackground(branding.lightergray);
        formsRemoveBorrowerBtn.setForeground(branding.maroon);

        formsRemoveBorrowerBtn.setEnabled(false);
        
        // Add action listener to the Add More Borrower button
        formsAddBorrowerBtn.addActionListener(e -> {
            JPanel newBorrowerPanel = createBorrowerInfoPanel(
                studentNumberFields, fullNameFields, emailAddressFields, 
                contactNumberFields, degreeProgramComboBoxes
            );
            borrowerPanels.add(newBorrowerPanel);
            borrowerInfoInputPanel.add(newBorrowerPanel);
            borrowerInfoInputPanel.revalidate();
            borrowerInfoInputPanel.repaint();

            formsRemoveBorrowerBtn.setEnabled(true);
            if (borrowerPanels.size() >= 7) {
                formsAddBorrowerBtn.setEnabled(false);
            }
            
            // Scroll to the bottom to show the new panel
            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalScrollBar = borrowerInfoInputScrollPanel.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
        });

        formsRemoveBorrowerBtn.addActionListener(e -> {
            if (borrowerPanels.size() > 1) {
                // Get the index of the last borrower
                int lastIndex = borrowerPanels.size() - 1;
                
                // Remove the last panel from the visual container
                borrowerInfoInputPanel.remove(borrowerPanels.get(lastIndex));
                
                // Remove from our tracking lists
                borrowerPanels.remove(lastIndex);
                studentNumberFields.remove(lastIndex);
                fullNameFields.remove(lastIndex);
                emailAddressFields.remove(lastIndex);
                contactNumberFields.remove(lastIndex);
                degreeProgramComboBoxes.remove(lastIndex);
                
                // If we now have only one borrower, disable the remove button
                if (borrowerPanels.size() <= 1) {
                    formsRemoveBorrowerBtn.setEnabled(false);
                }
                
                // Re-enable the add button since we're below the maximum
                formsAddBorrowerBtn.setEnabled(true);
                
                // Update the UI
                borrowerInfoInputPanel.revalidate();
                borrowerInfoInputPanel.repaint();

                formsAddBorrowerBtn.requestFocusInWindow();
                SwingUtilities.invokeLater(() -> {
                    courseOptions.updateUI();
                    sectionOptions.updateUI();
                    instructorOptions.updateUI();
                });
            }
        });
        
        formsAddRemoveBorrowerPanel.add(formsAddBorrowerBtn);
        formsAddRemoveBorrowerPanel.add(formsRemoveBorrowerBtn);
        
        JPanel formsCourseInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel formsCourseInfoLabel = new JLabel("Course Information");
        formsCourseInfoLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
        formsCourseInfoLabel.setForeground(branding.white);
        formsCourseInfoPanel.add(formsCourseInfoLabel);
        formsCourseInfoPanel.setOpaque(false);
    
        JPanel formsCourseInfoInputPanel = new JPanel();
        formsCourseInfoInputPanel.setPreferredSize(new Dimension(700, 60));
        formsCourseInfoInputPanel.setBorder(BorderFactory.createLineBorder(branding.white, 1));
        formsCourseInfoInputPanel.setBackground(branding.maroon);
        formsCourseInfoInputPanel.setLayout(new GridBagLayout());
        formsCourseInfoInputPanel.setOpaque(false);

        JPanel coursInfCoursePanel = new JPanel();
        JPanel coursInfSectionPanel = new JPanel();
        JPanel coursInfInstructorPanel = new JPanel();

        coursInfCoursePanel.setLayout(new GridBagLayout());
        coursInfSectionPanel.setLayout(new GridBagLayout());
        coursInfInstructorPanel.setLayout(new GridBagLayout());

        coursInfCoursePanel.setOpaque(false);
        coursInfSectionPanel.setOpaque(false);
        coursInfInstructorPanel.setOpaque(false);

        JLabel coursInfCourseLabel  = new JLabel("Course");
        JLabel coursInfSectionLabel = new JLabel("Section");
        JLabel coursInfInstructorLabel = new JLabel("Instructor");
        coursInfCourseLabel.setForeground(branding.white);
        coursInfSectionLabel.setForeground(branding.white);
        coursInfInstructorLabel.setForeground(branding.white);

        

        GridBagConstraints coursPanelGBC = new GridBagConstraints();
        coursPanelGBC.anchor = GridBagConstraints.WEST;
        coursPanelGBC.fill = GridBagConstraints.HORIZONTAL;
        coursPanelGBC.ipadx = 100;
        coursPanelGBC.gridy = 0;
        coursInfCoursePanel.add(coursInfCourseLabel, coursPanelGBC);
        coursPanelGBC.gridy = 1;
        coursInfCoursePanel.add(courseOptions, coursPanelGBC);

        coursPanelGBC.gridy = 0;
        coursInfSectionPanel.add(coursInfSectionLabel, coursPanelGBC);
        coursPanelGBC.gridy = 1;
        coursInfSectionPanel.add(sectionOptions, coursPanelGBC);

        coursPanelGBC.gridy = 0;
        coursInfInstructorPanel.add(coursInfInstructorLabel, coursPanelGBC);
        coursPanelGBC.gridy = 1;
        coursInfInstructorPanel.add(instructorOptions, coursPanelGBC);

        GridBagConstraints formsCourseInfoInputPanelGBC = new GridBagConstraints();
        formsCourseInfoInputPanelGBC.anchor = GridBagConstraints.WEST;
        formsCourseInfoInputPanelGBC.insets = new Insets(10,10,10,0);
        formsCourseInfoInputPanelGBC.weightx = 0.05;
        formsCourseInfoInputPanelGBC.gridx = 0;
        formsCourseInfoInputPanel.add(coursInfCoursePanel, formsCourseInfoInputPanelGBC);
        formsCourseInfoInputPanelGBC.weightx = 0.05;
        formsCourseInfoInputPanelGBC.gridx++;
        formsCourseInfoInputPanel.add(coursInfSectionPanel, formsCourseInfoInputPanelGBC);
        formsCourseInfoInputPanelGBC.weightx = 0.2;
        formsCourseInfoInputPanelGBC.gridx++;
        formsCourseInfoInputPanel.add(coursInfInstructorPanel, formsCourseInfoInputPanelGBC);



        GridBagConstraints scrn3BorrowerInformationFormsPanelGBC = new GridBagConstraints();
        scrn3BorrowerInformationFormsPanelGBC.fill = GridBagConstraints.HORIZONTAL;
        scrn3BorrowerInformationFormsPanelGBC.anchor = GridBagConstraints.WEST;
        scrn3BorrowerInformationFormsPanelGBC.weightx = 1;
        scrn3BorrowerInformationFormsPanelGBC.weighty = 1;
        scrn3BorrowerInformationFormsPanelGBC.gridy = 0;
        scrn3BorrowerInformationFormsPanel.add(formsHeaderPanel, scrn3BorrowerInformationFormsPanelGBC);
        scrn3BorrowerInformationFormsPanelGBC.weighty = 1;
        scrn3BorrowerInformationFormsPanelGBC.gridy++;
        scrn3BorrowerInformationFormsPanel.add(formsBorrowerInfoInputPanel, scrn3BorrowerInformationFormsPanelGBC);
        scrn3BorrowerInformationFormsPanelGBC.weighty = 1;
        scrn3BorrowerInformationFormsPanelGBC.gridy++;
        scrn3BorrowerInformationFormsPanel.add(formsAddRemoveBorrowerPanel, scrn3BorrowerInformationFormsPanelGBC);
        scrn3BorrowerInformationFormsPanelGBC.weighty = 1;
        scrn3BorrowerInformationFormsPanelGBC.gridy++;
        scrn3BorrowerInformationFormsPanel.add(formsCourseInfoPanel, scrn3BorrowerInformationFormsPanelGBC);
        scrn3BorrowerInformationFormsPanelGBC.weighty = 1;
        scrn3BorrowerInformationFormsPanelGBC.gridy++;
        scrn3BorrowerInformationFormsPanel.add(formsCourseInfoInputPanel, scrn3BorrowerInformationFormsPanelGBC);
    
        JScrollPane scrn3ScrollContentPanel = new JScrollPane(scrn3BorrowerInformationFormsPanel);
        scrn3ScrollContentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrn3ScrollContentPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrn3ScrollContentPanel.setBackground(branding.maroon);
        scrn3ScrollContentPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, branding.maroon));
        branding.reskinScrollBar(scrn3ScrollContentPanel, branding.maroon);
    
        JPanel scrn3MenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        scrn3MenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 33));
        scrn3MenuPanel.setBackground(branding.lightgray);
    
        JButton screen3BackBtn = new JButton("Go Back");
        JButton screen3BorrowBtn = new JButton("Borrow Now");
    
        screen3BackBtn.setPreferredSize(new Dimension(150, 30));
        screen3BackBtn.setBackground(branding.maroon);
        screen3BackBtn.setForeground(branding.white);
        screen3BackBtn.addActionListener(e -> {
            cardLayout.previous(GUIBorrowItemPanel.this);
        });
    
        screen3BorrowBtn.setPreferredSize(new Dimension(150, 30));
        screen3BorrowBtn.setBackground(branding.maroon);
        screen3BorrowBtn.setForeground(branding.white);
        screen3BorrowBtn.addActionListener(e -> {
            System.out.println("======== BORROWER INFORMATION ========");
            
            // Print all borrower information
            for (int i = 0; i < studentNumberFields.size(); i++) {
                System.out.println("Borrower #" + (i + 1) + ":");
                System.out.println("Student Number: " + studentNumberFields.get(i).getText());
                System.out.println("Full Name: " + fullNameFields.get(i).getText());
                System.out.println("Email Address: " + emailAddressFields.get(i).getText());
                System.out.println("Contact Number: " + contactNumberFields.get(i).getText());
                System.out.println("Degree Program: " + degreeProgramComboBoxes.get(i).getSelectedItem());
                System.out.println("-------------------------------------");
            }

            System.out.println("======== COURSE INFORMATION ========");
            System.out.println("Course: " + courseOptions.getSelectedItem());
            System.out.println("Section: " + sectionOptions.getSelectedItem());
            System.out.println("Instructor: " + instructorOptions.getSelectedItem());
            System.out.println("-------------------------------------");
            
            // Continue with borrowing process
            // cardLayout.next(GUIBorrowItemPanel.this);
            System.out.println("CONTINUE WITH BORROWING");
        });
    
        scrn3MenuPanel.add(screen3BackBtn);
        scrn3MenuPanel.add(screen3BorrowBtn);
    
        GridBagConstraints screen3GBC = new GridBagConstraints();
        screen3GBC.fill = GridBagConstraints.BOTH;
        screen3GBC.weightx = 1;
        screen3GBC.gridy = 0;
        screen3GBC.weighty = 0.8;
        screen3.add(scrn3ScrollContentPanel, screen3GBC);
        screen3GBC.weighty = 0.1;
        screen3GBC.gridy++;
        screen3.add(scrn3MenuPanel, screen3GBC);
    
    }

// Helper method to create new borrower info panels
    private JPanel createBorrowerInfoPanel(
        List<JTextField> studentNumberFields,
        List<JTextField> fullNameFields,
        List<JTextField> emailAddressFields,
        List<JTextField> contactNumberFields,
        List<JComboBox<String>> degreeProgramComboBoxes) {

        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(800, 25));
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
    
        JTextField studentNumberTextField = new JTextField(11);
        JTextField fullNameTextField = new JTextField(16);
        JTextField emailAddressTextField = new JTextField(14);
        JTextField contactNumberTextField = new JTextField(12);

        String[] degreeProgram = {"Select an Option", "BS Biology", "BS Computer Science", "BS Applied Mathematics"};
        JComboBox<String> degreeProgramOptions = new JComboBox<>(degreeProgram);
        

        // Add all components to respective lists for later data retrieval
        studentNumberFields.add(studentNumberTextField);
        fullNameFields.add(fullNameTextField);
        emailAddressFields.add(emailAddressTextField);
        contactNumberFields.add(contactNumberTextField);
        degreeProgramComboBoxes.add(degreeProgramOptions);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 4);
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.weightx = 0.011;
        panel.add(studentNumberTextField, gbc);
        gbc.weightx = 0.014;
        gbc.gridx++;
        panel.add(fullNameTextField, gbc);
        gbc.weightx = 0.015;
        gbc.gridx++;
        panel.add(emailAddressTextField, gbc);
        gbc.weightx = 0.0125;
        gbc.gridx++;
        panel.add(contactNumberTextField, gbc);
        gbc.weightx = 0.0001;
        gbc.gridx++;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(degreeProgramOptions, gbc);
        return panel;
    }

    private void updateBasketDisplayPanel() {
        // Clear the panel before rebuilding it
        scrn2BorrowedItemsContentPanel.removeAll();
        
        // Set the layout manager to BoxLayout for consistent spacing
        scrn2BorrowedItemsContentPanel.setLayout(new BoxLayout(scrn2BorrowedItemsContentPanel, BoxLayout.Y_AXIS));
        
        // Empty the item panels map as we're rebuilding it
        itemPanelsMap.clear();
        
        // Add each item in the basket to the panel
        for (BasketItem item : basketItems) {
            JLabel itemLabel = new JLabel(item.itemName);
            JLabel quantityLabel = new JLabel(String.valueOf(item.itemQuantity));
            
            // Set styling
            itemLabel.setForeground(branding.maroon);
            quantityLabel.setForeground(branding.maroon);
            quantityLabel.setFont(new Font("Arial", Font.BOLD, 16));
            
            // Create buttons
            JButton addBtn = new JButton("+");
            addBtn.setPreferredSize(new Dimension(50, 35));
            addBtn.setBackground(branding.maroon);
            addBtn.setForeground(branding.white);
            
            JButton subtractBtn = new JButton("-");
            subtractBtn.setPreferredSize(new Dimension(50, 35));
            subtractBtn.setBackground(branding.maroon);
            subtractBtn.setForeground(branding.white);
            
            // Create panels
            JPanel itemPanel = new JPanel();
            JPanel quantityPanel = new JPanel();
            
            itemPanel.setLayout(new BorderLayout());
            quantityPanel.setLayout(new GridBagLayout());
            
            itemPanel.setPreferredSize(new Dimension(10, 70));
            quantityPanel.setPreferredSize(new Dimension(10, 70));
            
            itemPanel.setOpaque(false);
            quantityPanel.setOpaque(false);
            
            itemPanel.add(itemLabel, BorderLayout.WEST);
            
            // Add components to quantity panel
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 5, 0, 5);
            
            gbc.gridx = 0;
            quantityPanel.add(addBtn, gbc);
            gbc.gridx = 1;
            quantityPanel.add(quantityLabel, gbc);
            gbc.gridx = 2;
            quantityPanel.add(subtractBtn, gbc);
            
            // Add button listeners
            final String itemName = item.itemName; // Capture the item name for the lambda expressions
            
            // Add button listener
            addBtn.addActionListener(e -> {
                BasketItem basketItem = basketItemsMap.get(itemName);
                if (basketItem != null) {
                    basketItem.itemQuantity++;
                    updateBasketDisplayPanel(); // Rebuild the entire panel
                }
            });
            
            // Subtract button listener
            subtractBtn.addActionListener(e -> {
                BasketItem basketItem = basketItemsMap.get(itemName);
                if (basketItem != null) {
                    if (basketItem.itemQuantity > 1) {
                        basketItem.itemQuantity--;
                    } else {
                        // Remove the item completely
                        basketItems.remove(basketItem);
                        basketItemsMap.remove(itemName);
                        
                        // Update the item's background color in the equipment panel
                        refreshEquipmentItemBackground(itemName);
                    }
                    updateBasketDisplayPanel(); // Rebuild the entire panel
                }
            });
            
            // Create and add the item row panel
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
            tupleInfoPanel.add(itemPanel, tupleInfoPanelGBC);
            
            tupleInfoPanelGBC.fill = GridBagConstraints.NONE;
            tupleInfoPanelGBC.anchor = GridBagConstraints.EAST;
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(quantityPanel, tupleInfoPanelGBC);
            
            // Store the panel in the map
            itemPanelsMap.put(itemName, tupleInfoPanel);
            
            // Add the panel to the content panel
            scrn2BorrowedItemsContentPanel.add(tupleInfoPanel);
            
            // Add a small gap between items (only if this isn't the last item)
            if (basketItems.indexOf(item) < basketItems.size() - 1) {
                scrn2BorrowedItemsContentPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        // Update the UI
        scrn2BorrowedItemsContentPanel.revalidate();
        scrn2BorrowedItemsContentPanel.repaint();
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
                    boolean isInBasket = basketItemsMap.containsKey(itemName);
                    if (isInBasket) return; // Do not allow selection if it's already in basket
            
                    if (selectedItemCards.contains(itemCard)) {
                        // Deselect item
                        itemCard.setBackground(Color.WHITE);
                        selectedItemCards.remove(itemCard);
                    } else {
                        // Select item
                        itemCard.setBackground(Color.GRAY);
                        selectedItemCards.add(itemCard);
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
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 30));
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
            if (!selectedItemCards.isEmpty()) {
                for (JPanel card : new HashSet<>(selectedItemCards)) {
                    String itemName = (String) card.getClientProperty("itemName");
                    String category = (String) card.getClientProperty("category");
            
                    if (!basketItemsMap.containsKey(itemName)) {
                        BasketItem newItem = new BasketItem(category, itemName);
                        basketItems.add(newItem);
                        basketItemsMap.put(itemName, newItem);
            
                        card.setBackground(Color.YELLOW);
                    }
                    selectedItemCards.remove(card); // Deselect after adding
                }
            
                // Print basket
                System.out.println("\n--- Current Basket Contents ---");
                for (int i = 0; i < basketItems.size(); i++) {
                    System.out.println((i + 1) + ". " + basketItems.get(i));
                }
                System.out.println("-----------------------------\n");
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
    
    private void refreshEquipmentItemBackground(String itemName) {
        // Get the grid panel from the equipment panel
        if (equipmentPanel == null) return;
        JPanel gridPanel = (JPanel) equipmentPanel.getClientProperty("gridPanel");
        if (gridPanel == null) return;
        
        // Loop through all components in the grid panel
        for (Component component : gridPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemCard = (JPanel) component;
                String cardItemName = (String) itemCard.getClientProperty("itemName");
                
                // If this is the card for the removed item, reset its background
                if (itemName.equals(cardItemName)) {
                    itemCard.setBackground(Color.WHITE);
                    break; // Found the item, no need to continue searching
                }
            }
        }
        
        // Ensure the UI is updated
        gridPanel.revalidate();
        gridPanel.repaint();
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

}