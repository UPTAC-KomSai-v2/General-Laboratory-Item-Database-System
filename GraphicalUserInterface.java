
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;


public class GraphicalUserInterface implements ActionListener{
    // GUI Components
    private JButton ctntBorrowItemBtn, ctntBorrowerListBtn, ctntUpdateInventoryBtn, ctntTransactionHistoryBtn;
    private JButton rbbnLogoutBtn, rbbnUserBtn, rbbnAboutBtn;
    private JButton bitmBackBtn;
    private JButton blstBackBtn;
    private JButton upinBackBtn;
    private JButton tranBackBtn;
    private JButton lgnLoginBtn;
    private JLabel lgnStatusLabel;
    private JPanel loginPanel, mainPanel, mainRibbonPanel, mainContentPanel, ctntMenuPanel, ctntBorrowItemPanel, ctntBorrowerListPanel, ctntUpdateInventoryPanel, ctntTransactionHistoryPanel;
    private JPasswordField lgnInputPasswordField;
    private JTextField lgnInputUsernameField;
    private JFrame mainFrame;
    private Queries queries;
    private Branding branding;

    public GraphicalUserInterface(){
        queries = new Queries();    // Logic/backend interactions
        branding = new Branding();  // Color, font, and image assets
        
        intializeMainFrame();       // Set up the frame (window) structure
        initializeLoginPanel();     // Load the login interface
        initializeMainPanel();      // Load the main UI but do not yet display it
    }

    public void intializeMainFrame(){
        // Create the main window
        mainFrame = new JFrame("UPTC General Laboratory Database");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setLocationRelativeTo(null);                            // Center the window
        mainFrame.setLayout(new BorderLayout());                            // Use BorderLayout as layout manager
        mainFrame.setMinimumSize(new Dimension(1000, 700));    // Prevent resizing too small
    }

    public void initializeLoginPanel(){
        // Main login panel with maroon background
        loginPanel = new JPanel();
        loginPanel.setBackground(branding.maroon);
        loginPanel.setLayout(new GridBagLayout());
        
        // Title section with logo and text
        JPanel lgnTitlePanel = new JPanel();
        lgnTitlePanel.setBackground(branding.white);
        lgnTitlePanel.setLayout(new GridBagLayout());
        lgnTitlePanel.setOpaque(false); // Transparent panel for layering

        // UP logo
        JLabel lgnUPLogo = new JLabel();
        lgnUPLogo.setIcon(new ImageIcon(branding.lgnUPLogoResized));
        
        // Title labels
        JLabel lgnTitleLabel1 = new JLabel("UP TACLOBAN COLLEGE");
        JLabel lgnTitleLabel2 = new JLabel("General Laboratory Database");
        
        lgnTitleLabel1.setFont(branding.sizedFontPalatinoBig);
        lgnTitleLabel2.setFont(branding.sizedFontPalatinoSmall);
        lgnTitleLabel1.setForeground(branding.white);
        lgnTitleLabel2.setForeground(branding.white);

        // Position the elements inside title panel
        GridBagConstraints lgnTitlePanelGBC = new GridBagConstraints();
        lgnTitlePanelGBC.insets = new Insets(0, 0, 30, 0);
        lgnTitlePanelGBC.gridy = 0;
        lgnTitlePanel.add(lgnUPLogo, lgnTitlePanelGBC);
        lgnTitlePanelGBC.insets = new Insets(0, 0, 15, 0);
        lgnTitlePanelGBC.gridy = 1;
        lgnTitlePanel.add(lgnTitleLabel1, lgnTitlePanelGBC);
        lgnTitlePanelGBC.gridy = 2;
        lgnTitlePanel.add(lgnTitleLabel2, lgnTitlePanelGBC);
        
        // Input section for username and password
        JPanel lgnInputPanel = new JPanel();
        lgnInputPanel.setBackground(branding.lightgray);
        lgnInputPanel.setLayout(new GridBagLayout());
        lgnInputPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // "Login" title inside input panel
        JLabel lgnLoginLabel = new JLabel("Login", SwingConstants.CENTER);
        lgnLoginLabel.setFont(new Font("Roboto", Font.PLAIN, 25));
        lgnLoginLabel.setForeground(branding.maroon);

        // Username and password labels
        GridLayout gridLayoutForLabels = new GridLayout(1,2);
        JPanel inputUserPanel = new JPanel(gridLayoutForLabels);
        JPanel inputPasswordPanel = new JPanel(gridLayoutForLabels);
        JLabel lgnIputUsernameLabel = new JLabel("Enter Username: ");
        JLabel lgnIputPasswordLabel = new JLabel("Enter Password: ");
        inputUserPanel.add(lgnIputUsernameLabel);
        inputPasswordPanel.add(lgnIputPasswordLabel);
        lgnIputUsernameLabel.setForeground(branding.maroon);
        lgnIputPasswordLabel.setForeground(branding.maroon);

        // Input fields
        lgnInputUsernameField = new JTextField(10);
        lgnInputPasswordField = new JPasswordField(10);
        lgnInputUsernameField.setPreferredSize(new Dimension(200, 30));
        lgnInputPasswordField.setPreferredSize(new Dimension(200, 30));

        // Login button
        lgnLoginBtn = new JButton("Login");
        lgnLoginBtn.setBackground(branding.maroon);
        lgnLoginBtn.setForeground(branding.white);
        lgnLoginBtn.setFocusable(false);
        lgnLoginBtn.setPreferredSize(new Dimension(250, 35));
        lgnLoginBtn.addActionListener(this);

         // Status label for feedback (e.g. "Login Confirmed" or "Login Denied")
        lgnStatusLabel = new JLabel("Please enter login credentials.", SwingConstants.CENTER); //.setText("Login Confirmed, Login Denied")
        lgnStatusLabel.setForeground(branding.maroon);
        
        // Position the components inside lgnInputPanel
        GridBagConstraints lgnInputPanelGBC = new GridBagConstraints();
        lgnInputPanelGBC.fill = GridBagConstraints.HORIZONTAL;
        lgnInputPanelGBC.weightx = 0.01;
        lgnInputPanelGBC.insets = new Insets(0, 0, 20, 0);
        lgnInputPanelGBC.gridy = 0;
        lgnInputPanel.add(lgnLoginLabel, lgnInputPanelGBC);
        lgnInputPanelGBC.insets = new Insets(0, 30, 10, 30);
        lgnInputPanelGBC.gridy = 1;
        lgnInputPanel.add(inputUserPanel, lgnInputPanelGBC);
        lgnInputPanelGBC.gridy = 2;
        lgnInputPanel.add(lgnInputUsernameField, lgnInputPanelGBC);
        lgnInputPanelGBC.gridy = 3;
        lgnInputPanel.add(inputPasswordPanel, lgnInputPanelGBC);
        lgnInputPanelGBC.gridy = 4;
        lgnInputPanel.add(lgnInputPasswordField, lgnInputPanelGBC);
        lgnInputPanelGBC.insets = new Insets(30, 30, 0, 30);
        lgnInputPanelGBC.gridy = 5;
        lgnInputPanel.add(lgnStatusLabel, lgnInputPanelGBC);
        lgnInputPanelGBC.insets = new Insets(70, 30, 0, 30);
        lgnInputPanelGBC.gridy = 6;
        lgnInputPanel.add(lgnLoginBtn, lgnInputPanelGBC);

        // Container for title and input panel
        JPanel lgnContainer = new JPanel();
        lgnContainer.setPreferredSize(new Dimension(900, 400));
        lgnContainer.setLayout(new GridBagLayout());
        lgnContainer.setOpaque(false);

        // Position title and input panels inside container
        GridBagConstraints lgnContainerGBC = new GridBagConstraints();
        lgnContainerGBC.fill = GridBagConstraints.BOTH;
        lgnContainerGBC.weightx = 1;
        lgnContainerGBC.weighty = 1;
        lgnContainerGBC.gridx = 0;
        lgnContainer.add(lgnTitlePanel, lgnContainerGBC);
        lgnContainerGBC.weightx = 0.2;
        lgnContainerGBC.gridx = 1;
        lgnContainer.add(lgnInputPanel, lgnContainerGBC);

        // Add login container to login panel and show it in the main frame
        loginPanel.add(lgnContainer);
        mainFrame.add(loginPanel);
        mainFrame.setVisible(true);
    }

    public void initializeMainPanel(){
        // Main panel holds the mainRibbonPanel and mainContentPanel sections
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);
        mainPanel.setLayout(new GridBagLayout());

        // Initialize both the top ribbon and the main content area (Gin separate kola idk para d ada malipong hehe)
        initializeMainRibbonPanel();
        initializeMainContentPanel();

        // Position ribbon at the top and content below
        GridBagConstraints mainPanelGBC = new GridBagConstraints();
        mainPanelGBC.fill = 1;
        mainPanelGBC.weightx = 1;
        mainPanelGBC.gridx = 0;
        mainPanelGBC.gridy = 0;
        mainPanelGBC.weighty = 0.01;
        mainPanel.add(mainRibbonPanel, mainPanelGBC);
        mainPanelGBC.gridx = 0;
        mainPanelGBC.gridy = 1;
        mainPanelGBC.weighty = 1.5;
        mainPanel.add(mainContentPanel, mainPanelGBC);

    }

    public void initializeMainRibbonPanel(){
        // Top banner panel (ribbon)
        // Main Ribbon Panel
        mainRibbonPanel = new JPanel();
        mainRibbonPanel.setBackground(branding.maroon);
        mainRibbonPanel.setLayout(new GridLayout(1,1));

        // Title section of the ribbon
        JPanel rbbnTitlePanel = new JPanel();
        rbbnTitlePanel.setLayout(new BoxLayout(rbbnTitlePanel, BoxLayout.Y_AXIS));
        rbbnTitlePanel.setOpaque(false);
        JLabel rbbnTitleLabel1 = new JLabel("UP TACLOBAN COLLEGE");
        JLabel rbbnTitleLabel2 = new JLabel("General Laboratory Database");
        rbbnTitleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rbbnTitleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rbbnTitleLabel1.setFont(branding.sizedFontPalatinoBig);
        rbbnTitleLabel2.setFont(branding.sizedFontPalatinoSmall);
        rbbnTitleLabel1.setForeground(branding.white);
        rbbnTitleLabel2.setForeground(branding.white);
        rbbnTitlePanel.add(rbbnTitleLabel1);
        rbbnTitlePanel.add(rbbnTitleLabel2);

        // Menu section of the ribbon
        // Button group (User, About, Logout) aligned to the right
        JPanel rbbnMenuPanel = new JPanel();
        rbbnMenuPanel.setBackground(branding.lightgray);
        rbbnMenuPanel.setOpaque(false);
        rbbnMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 35, 8));
        rbbnLogoutBtn = new JButton("Logout");
        rbbnUserBtn = new JButton("User");
        rbbnAboutBtn = new JButton("About");

        // Button styling
        rbbnLogoutBtn.setContentAreaFilled(true);
        rbbnLogoutBtn.setBackground(branding.maroon);
        rbbnLogoutBtn.setBorderPainted(false);
        rbbnLogoutBtn.setFocusable(false);
        rbbnLogoutBtn.setForeground(branding.white);
        rbbnUserBtn.setContentAreaFilled(true);
        rbbnUserBtn.setBackground(branding.maroon);
        rbbnUserBtn.setBorderPainted(false);
        rbbnUserBtn.setFocusable(false);
        rbbnUserBtn.setForeground(branding.white);
        rbbnAboutBtn.setContentAreaFilled(true);
        rbbnAboutBtn.setBackground(branding.maroon);
        rbbnAboutBtn.setBorderPainted(false);
        rbbnAboutBtn.setFocusable(false);
        rbbnAboutBtn.setForeground(branding.white);

        // Add button actions
        rbbnLogoutBtn.addActionListener(this);
        rbbnUserBtn.addActionListener(this);
        rbbnAboutBtn.addActionListener(this);

        // Add buttons to the menu panel
        rbbnMenuPanel.add(rbbnUserBtn);
        rbbnMenuPanel.add(rbbnAboutBtn);
        rbbnMenuPanel.add(rbbnLogoutBtn);

        // // UP Logo on the left
        JLabel rbbnLogoLabel = new JLabel();
        rbbnLogoLabel.setIcon(new ImageIcon(branding.rbbnUPLogoResized));

        // Container 1: title and buttons
        JPanel rbbnPanelContainer1 = new JPanel(new BorderLayout());
        rbbnPanelContainer1.setOpaque(false);
        rbbnPanelContainer1.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        rbbnPanelContainer1.add(rbbnTitlePanel, BorderLayout.NORTH); 
        rbbnPanelContainer1.add(rbbnMenuPanel, BorderLayout.SOUTH);

        // Container 2: logo
        JPanel rbbnPanelContainer2 = new JPanel(new BorderLayout());
        rbbnPanelContainer2.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
        rbbnPanelContainer2.add(rbbnLogoLabel, BorderLayout.WEST);
        rbbnPanelContainer2.setOpaque(false);

        // Overlay both containers for left-right layout
        JPanel rbbnOverlayPanel = new JPanel();
        rbbnOverlayPanel.setLayout(new OverlayLayout(rbbnOverlayPanel));
        rbbnOverlayPanel.setOpaque(false);
        rbbnOverlayPanel.add(rbbnPanelContainer1);
        rbbnOverlayPanel.add(rbbnPanelContainer2);
        
        // Add everything to the ribbon
        mainRibbonPanel.add(rbbnOverlayPanel);
    }

    public void initializeMainContentPanel(){
        // Main content panel holds either the menu or one of the sub-panels
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(branding.white);
        mainContentPanel.setLayout(new GridBagLayout());

        // Menu panel shown initially (main dashboard)
        ctntMenuPanel = new JPanel(); 
        ctntMenuPanel.setPreferredSize(new Dimension(600, 400)); // 900x500 Big, 600x400 Small
        ctntMenuPanel.setBorder(BorderFactory.createMatteBorder(40, 5, 5, 5, branding.maroon));
        ctntMenuPanel.setBackground(branding.lightgray);
        ctntMenuPanel.setLayout(new GridBagLayout());

        // Buttons for each content section
        ctntBorrowItemBtn = new JButton("Borrow Item");
        ctntBorrowerListBtn = new JButton("Borrower List");
        ctntUpdateInventoryBtn = new JButton("Update Inventory");
        ctntTransactionHistoryBtn = new JButton("Transaction History");
        
        // Button styles
        ctntBorrowItemBtn.setForeground(branding.white);
        ctntBorrowerListBtn.setForeground(branding.white);
        ctntUpdateInventoryBtn.setForeground(branding.white);
        ctntTransactionHistoryBtn.setForeground(branding.white);

        ctntBorrowItemBtn.setBackground(branding.maroon);
        ctntBorrowerListBtn.setBackground(branding.maroon);
        ctntUpdateInventoryBtn.setBackground(branding.maroon);
        ctntTransactionHistoryBtn.setBackground(branding.maroon);

        ctntBorrowItemBtn.setFocusable(false);
        ctntBorrowerListBtn.setFocusable(false);
        ctntUpdateInventoryBtn.setFocusable(false);
        ctntTransactionHistoryBtn.setFocusable(false);

        ctntBorrowItemBtn.setPreferredSize(new Dimension(270, 35));
        ctntBorrowerListBtn.setPreferredSize(new Dimension(270, 35));
        ctntUpdateInventoryBtn.setPreferredSize(new Dimension(270, 35));
        ctntTransactionHistoryBtn.setPreferredSize(new Dimension(270, 35));

        // Button listeners
        ctntBorrowItemBtn.addActionListener(this);
        ctntBorrowerListBtn.addActionListener(this);
        ctntUpdateInventoryBtn.addActionListener(this);
        ctntTransactionHistoryBtn.addActionListener(this);


        // Place the buttons vertically in ctntMenuPanel
        GridBagConstraints contentPanelGBC = new GridBagConstraints();
        contentPanelGBC.insets = new Insets(0, 0, 25, 0);
        contentPanelGBC.gridx = 0;
        contentPanelGBC.gridy = 0;
        ctntMenuPanel.add(ctntBorrowItemBtn, contentPanelGBC);
        contentPanelGBC.gridx = 0;
        contentPanelGBC.gridy = 1;
        ctntMenuPanel.add(ctntBorrowerListBtn, contentPanelGBC);
        contentPanelGBC.gridx = 0;
        contentPanelGBC.gridy = 2;
        ctntMenuPanel.add(ctntUpdateInventoryBtn, contentPanelGBC);
        contentPanelGBC.gridx = 0;
        contentPanelGBC.gridy = 3;
        ctntMenuPanel.add(ctntTransactionHistoryBtn, contentPanelGBC);

        // Add the menu to the content panel
        mainContentPanel.add(ctntMenuPanel);

        // Back buttons for sub-panels
        bitmBackBtn = new JButton("Go Back");
        blstBackBtn = new JButton("Go Back");
        upinBackBtn = new JButton("Go Back");
        tranBackBtn = new JButton("Go Back");
        bitmBackBtn.addActionListener(this);
        blstBackBtn.addActionListener(this);
        upinBackBtn.addActionListener(this);
        tranBackBtn.addActionListener(this);

        // Initialize sub-panels (they will always be added inside mainContentPanel)
        ctntBorrowItemPanel = new GUIBorrowItemPanel(branding, bitmBackBtn);
        ctntBorrowerListPanel = new GUIBorrowItemPanel(branding, blstBackBtn);
        ctntUpdateInventoryPanel = new GUIBorrowItemPanel(branding, upinBackBtn);
        ctntTransactionHistoryPanel = new GUIBorrowItemPanel(branding, tranBackBtn);
    }


    // Button Logic
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lgnLoginBtn) {
            queries.login(lgnInputUsernameField, lgnInputPasswordField, loginPanel, mainFrame, mainPanel, lgnStatusLabel);
            lgnInputUsernameField.setText("");
            lgnInputPasswordField.setText("");
        } else if (e.getSource() == ctntBorrowItemBtn) {
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntBorrowItemPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
            queries.borrowItems(new Scanner(System.in));
        } else if (e.getSource() == ctntBorrowerListBtn) {
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntBorrowerListPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == ctntUpdateInventoryBtn) {
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntUpdateInventoryPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == ctntTransactionHistoryBtn) {
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntTransactionHistoryPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == rbbnLogoutBtn){
            int result = JOptionPane.showConfirmDialog(
            mainFrame,
            new JLabel("Are you sure you want to logout?", SwingConstants.CENTER),
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                lgnStatusLabel.setText("Please enter login credentials.");
                lgnStatusLabel.setForeground(branding.maroon);
                mainFrame.add(loginPanel);
                mainFrame.remove(mainPanel);
                mainContentPanel.removeAll();
                mainContentPanel.add(ctntMenuPanel);
                mainContentPanel.revalidate();
                mainContentPanel.repaint();
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        } else if (e.getSource() == rbbnUserBtn){
            
        } else if (e.getSource() == rbbnAboutBtn){
            
        } else if (e.getSource() == bitmBackBtn){
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntMenuPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == blstBackBtn){
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntMenuPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == upinBackBtn){
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntMenuPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else if (e.getSource() == tranBackBtn){
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntMenuPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } 
    }

}
