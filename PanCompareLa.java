import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    // private JLabel lgnStatusLabel;
    private GUILoginPanel loginPanel;
    private JPanel mainPanel, mainRibbonPanel, mainContentPanel, ctntMenuPanel, ctntBorrowItemPanel, ctntBorrowerListPanel, ctntUpdateInventoryPanel, ctntTransactionHistoryPanel;
    // private JPasswordField lgnInputPasswordField;
    // private JTextField lgnInputUsernameField;
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

        // Login button
        lgnLoginBtn = new JButton("Login");
        lgnLoginBtn.setBackground(branding.maroon);
        lgnLoginBtn.setForeground(branding.white);
        lgnLoginBtn.setFocusable(false);
        lgnLoginBtn.setPreferredSize(new Dimension(250, 35));
        lgnLoginBtn.addActionListener(this);
        
        // Add login container to login panel and show it in the main frame
        loginPanel = new GUILoginPanel(branding, lgnLoginBtn);
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
            queries.login(loginPanel.lgnInputUsernameField, loginPanel.lgnInputPasswordField, loginPanel, mainFrame, mainPanel, loginPanel.lgnStatusLabel);
            loginPanel.lgnInputUsernameField.setText("");
            loginPanel.lgnInputPasswordField.setText("");
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
                loginPanel.lgnStatusLabel.setText("Please enter login credentials.");
                loginPanel.lgnStatusLabel.setForeground(branding.maroon);

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