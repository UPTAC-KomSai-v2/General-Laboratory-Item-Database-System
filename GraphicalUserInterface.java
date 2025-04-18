
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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
import javax.swing.UIManager;


public class GraphicalUserInterface implements ActionListener{
    private JButton ctntBorrowItemBtn, ctntBorrowerListBtn, ctntUpdateInventoryBtn, ctntTransactionHistoryBtn;
    private JButton rbbnLogoutBtn, rbbnUserBtn, rbbnAboutBtn;
    private JButton bitmBackBtn;
    private JButton blstBackBtn;
    private JButton upinBackBtn;
    private JButton tranBackBtn;
    private JButton lgnLoginBtn;
    private JPanel loginPanel, mainPanel, mainRibbonPanel, mainContentPanel, ctntMenuPanel, ctntBorrowItemPanel, ctntBorrowerListPanel, ctntUpdateInventoryPanel, ctntTransactionHistoryPanel;
    private BufferedImage upLogo, lgnUPLogoResized, rbbnUPLogoResized;
    private Font sizedFontPalatinoSmall, sizedFontPalatinoBig;
    private Color maroon, lightgray, white, lightergray; 
    private JPasswordField lgnInputPasswordField;
    private JTextField lgnInputUsernameField;
    private JFrame mainFrame;

    public GraphicalUserInterface(){
        initializeBranding();
        intializeMainFrame();
        initializeLoginPanel();
        initializeMainPanel();
    }

    public void intializeMainFrame(){
        mainFrame = new JFrame("UPTC General Laboratory Database");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setMinimumSize(new Dimension(1000, 700));
    }

    public void initializeLoginPanel(){
        loginPanel = new JPanel();
        loginPanel.setBackground(maroon);
        loginPanel.setLayout(new GridBagLayout());
        
        JPanel lgnTitlePanel = new JPanel();
        lgnTitlePanel.setBackground(white);
        lgnTitlePanel.setLayout(new GridBagLayout());
        lgnTitlePanel.setOpaque(false);

        JLabel lgnUPLogo = new JLabel();
        lgnUPLogo.setIcon(new ImageIcon(lgnUPLogoResized));
        
        JLabel lgnTitleLabel1 = new JLabel("UP TACLOBAN COLLEGE");
        JLabel lgnTitleLabel2 = new JLabel("General Laboratory Database");
        
        lgnTitleLabel1.setFont(sizedFontPalatinoBig);
        lgnTitleLabel2.setFont(sizedFontPalatinoSmall);
        lgnTitleLabel1.setForeground(white);
        lgnTitleLabel2.setForeground(white);

        GridBagConstraints lgnTitlePanelGBC = new GridBagConstraints();
        lgnTitlePanelGBC.insets = new Insets(0, 0, 30, 0);
        lgnTitlePanelGBC.gridy = 0;
        lgnTitlePanel.add(lgnUPLogo, lgnTitlePanelGBC);
        lgnTitlePanelGBC.insets = new Insets(0, 0, 15, 0);
        lgnTitlePanelGBC.gridy = 1;
        lgnTitlePanel.add(lgnTitleLabel1, lgnTitlePanelGBC);
        lgnTitlePanelGBC.gridy = 2;
        lgnTitlePanel.add(lgnTitleLabel2, lgnTitlePanelGBC);
    
        JPanel lgnInputPanel = new JPanel();
        lgnInputPanel.setBackground(lightgray);
        lgnInputPanel.setLayout(new GridBagLayout());
        lgnInputPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JLabel lgnLoginLabel = new JLabel("Login", SwingConstants.CENTER);
        lgnLoginLabel.setFont(new Font("Roboto", Font.PLAIN, 25));
        lgnLoginLabel.setForeground(maroon);

        GridLayout gridLayoutForLabels = new GridLayout(1,2);
        JPanel inputUserPanel = new JPanel(gridLayoutForLabels);
        JPanel inputPasswordPanel = new JPanel(gridLayoutForLabels);
        JLabel lgnIputUsernameLabel = new JLabel("Enter Username: ");
        JLabel lgnIputPasswordLabel = new JLabel("Enter Password: ");
        inputUserPanel.add(lgnIputUsernameLabel);
        inputPasswordPanel.add(lgnIputPasswordLabel);
        lgnIputUsernameLabel.setForeground(maroon);
        lgnIputPasswordLabel.setForeground(maroon);

        lgnInputUsernameField = new JTextField(10);
        lgnInputPasswordField = new JPasswordField(10);
        lgnInputUsernameField.setPreferredSize(new Dimension(200, 30));
        lgnInputPasswordField.setPreferredSize(new Dimension(200, 30));

        lgnLoginBtn = new JButton("Login");
        lgnLoginBtn.setBackground(maroon);
        lgnLoginBtn.setForeground(white);
        lgnLoginBtn.setFocusable(false);
        lgnLoginBtn.setPreferredSize(new Dimension(250, 35));
        lgnLoginBtn.addActionListener(this);

        JLabel lgnStatusLabel = new JLabel("<Status Goes Here>", SwingConstants.CENTER); //.setText("Login Confirmed, Login Denied")
        lgnStatusLabel.setForeground(maroon);
        
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

        JPanel lgnContainer = new JPanel();
        lgnContainer.setPreferredSize(new Dimension(900, 400));
        lgnContainer.setLayout(new GridBagLayout());
        lgnContainer.setOpaque(false);

        GridBagConstraints lgnContainerGBC = new GridBagConstraints();
        lgnContainerGBC.fill = GridBagConstraints.BOTH;
        lgnContainerGBC.weightx = 1;
        lgnContainerGBC.weighty = 1;
        lgnContainerGBC.gridx = 0;
        lgnContainer.add(lgnTitlePanel, lgnContainerGBC);
        lgnContainerGBC.weightx = 0.2;
        lgnContainerGBC.gridx = 1;
        lgnContainer.add(lgnInputPanel, lgnContainerGBC);

        loginPanel.add(lgnContainer);
        mainFrame.add(loginPanel);
        mainFrame.setVisible(true);
    }

    public void initializeMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);
        mainPanel.setLayout(new GridBagLayout());

        initializeMainRibbonPanel();
        initializeMainContentPanel();

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
        // Main Ribbon Panel
        mainRibbonPanel = new JPanel();
        mainRibbonPanel.setBackground(maroon);
        mainRibbonPanel.setLayout(new GridLayout(1,1));

        // Ribbon Title Panel
        JPanel rbbnTitlePanel = new JPanel();
        rbbnTitlePanel.setLayout(new BoxLayout(rbbnTitlePanel, BoxLayout.Y_AXIS));
        rbbnTitlePanel.setOpaque(false);
        JLabel rbbnTitleLabel1 = new JLabel("UP TACLOBAN COLLEGE");
        JLabel rbbnTitleLabel2 = new JLabel("General Laboratory Database");
        rbbnTitleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rbbnTitleLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        rbbnTitleLabel1.setFont(sizedFontPalatinoBig);
        rbbnTitleLabel2.setFont(sizedFontPalatinoSmall);
        rbbnTitleLabel1.setForeground(white);
        rbbnTitleLabel2.setForeground(white);
        rbbnTitlePanel.add(rbbnTitleLabel1);
        rbbnTitlePanel.add(rbbnTitleLabel2);

        // Ribbon Menu Panel
        JPanel rbbnMenuPanel = new JPanel();
        rbbnMenuPanel.setBackground(lightgray);
        rbbnMenuPanel.setOpaque(false);
        rbbnMenuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 35, 8));
        rbbnLogoutBtn = new JButton("Logout");
        rbbnUserBtn = new JButton("User");
        rbbnAboutBtn = new JButton("About");
        rbbnLogoutBtn.setContentAreaFilled(true);
        rbbnLogoutBtn.setBackground(maroon);
        rbbnLogoutBtn.setBorderPainted(false);
        rbbnLogoutBtn.setFocusable(false);
        rbbnLogoutBtn.setForeground(white);
        rbbnUserBtn.setContentAreaFilled(true);
        rbbnUserBtn.setBackground(maroon);
        rbbnUserBtn.setBorderPainted(false);
        rbbnUserBtn.setFocusable(false);
        rbbnUserBtn.setForeground(white);
        rbbnAboutBtn.setContentAreaFilled(true);
        rbbnAboutBtn.setBackground(maroon);
        rbbnAboutBtn.setBorderPainted(false);
        rbbnAboutBtn.setFocusable(false);
        rbbnAboutBtn.setForeground(white);
        rbbnLogoutBtn.addActionListener(this);
        rbbnUserBtn.addActionListener(this);
        rbbnAboutBtn.addActionListener(this);
        rbbnMenuPanel.add(rbbnUserBtn);
        rbbnMenuPanel.add(rbbnAboutBtn);
        rbbnMenuPanel.add(rbbnLogoutBtn);

        // Ribbon Logo
        JLabel rbbnLogoLabel = new JLabel();
        rbbnLogoLabel.setIcon(new ImageIcon(rbbnUPLogoResized));

        // Main Ribbon Panel
        JPanel rbbnPanelContainer1 = new JPanel(new BorderLayout());
        rbbnPanelContainer1.setOpaque(false);
        rbbnPanelContainer1.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
        rbbnPanelContainer1.add(rbbnTitlePanel, BorderLayout.NORTH); 
        rbbnPanelContainer1.add(rbbnMenuPanel, BorderLayout.SOUTH);

        JPanel rbbnPanelContainer2 = new JPanel(new BorderLayout());
        rbbnPanelContainer2.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
        rbbnPanelContainer2.add(rbbnLogoLabel, BorderLayout.WEST);
        rbbnPanelContainer2.setOpaque(false);

        JPanel rbbnOverlayPanel = new JPanel();
        rbbnOverlayPanel.setLayout(new OverlayLayout(rbbnOverlayPanel));
        rbbnOverlayPanel.setOpaque(false);

        rbbnOverlayPanel.add(rbbnPanelContainer1);
        rbbnOverlayPanel.add(rbbnPanelContainer2);
    
        mainRibbonPanel.add(rbbnOverlayPanel);
    }

    public void initializeMainContentPanel(){
        // Main Content Panel 
        mainContentPanel = new JPanel();
        mainContentPanel.setBackground(white);
        mainContentPanel.setLayout(new GridBagLayout());

        ctntMenuPanel = new JPanel(); 
        ctntMenuPanel.setPreferredSize(new Dimension(600, 400)); // 900x500 Big, 600x400 Small
        ctntMenuPanel.setBorder(BorderFactory.createMatteBorder(40, 5, 5, 5, maroon));
        ctntMenuPanel.setBackground(lightgray);
        ctntMenuPanel.setLayout(new GridBagLayout());

        ctntBorrowItemBtn = new JButton("Borrow Item");
        ctntBorrowerListBtn = new JButton("Borrower List");
        ctntUpdateInventoryBtn = new JButton("Update Inventory");
        ctntTransactionHistoryBtn = new JButton("Transaction History");
        
        ctntBorrowItemBtn.setForeground(maroon);
        ctntBorrowerListBtn.setForeground(maroon);
        ctntUpdateInventoryBtn.setForeground(maroon);
        ctntTransactionHistoryBtn.setForeground(maroon);

        ctntBorrowItemBtn.setBackground(lightergray);
        ctntBorrowerListBtn.setBackground(lightergray);
        ctntUpdateInventoryBtn.setBackground(lightergray);
        ctntTransactionHistoryBtn.setBackground(lightergray);

        ctntBorrowItemBtn.setFocusable(false);
        ctntBorrowerListBtn.setFocusable(false);
        ctntUpdateInventoryBtn.setFocusable(false);
        ctntTransactionHistoryBtn.setFocusable(false);

        ctntBorrowItemBtn.setPreferredSize(new Dimension(270, 35));
        ctntBorrowerListBtn.setPreferredSize(new Dimension(270, 35));
        ctntUpdateInventoryBtn.setPreferredSize(new Dimension(270, 35));
        ctntTransactionHistoryBtn.setPreferredSize(new Dimension(270, 35));

        ctntBorrowItemBtn.addActionListener(this);
        ctntBorrowerListBtn.addActionListener(this);
        ctntUpdateInventoryBtn.addActionListener(this);
        ctntTransactionHistoryBtn.addActionListener(this);

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

        mainContentPanel.add(ctntMenuPanel);
        initializeBorrowItemPanel();
        initializeBorrowerListPanel();
        initializeUpdateInventoryPanel();
        initializeTransactionHistoryPanel();
    }


    public void initializeBorrowItemPanel(){
        ctntBorrowItemPanel = new JPanel();
        ctntBorrowItemPanel.setLayout(new GridBagLayout());
        ctntBorrowItemPanel.setBackground(lightgray);
        ctntBorrowItemPanel.setPreferredSize(new Dimension(900, 500));
        ctntBorrowItemPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, maroon));
        bitmBackBtn = new JButton("Go Back");
        bitmBackBtn.setBackground(lightergray);
        bitmBackBtn.setForeground(maroon);
        bitmBackBtn.addActionListener(this);
        ctntBorrowItemPanel.add(bitmBackBtn);
    }

    public void initializeBorrowerListPanel(){
        ctntBorrowerListPanel = new JPanel();
        ctntBorrowerListPanel.setLayout(new GridBagLayout());
        ctntBorrowerListPanel.setBackground(lightgray);
        ctntBorrowerListPanel.setPreferredSize(new Dimension(900, 500));
        ctntBorrowerListPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, maroon));
        blstBackBtn = new JButton("Go Back");
        blstBackBtn.setBackground(lightergray);
        blstBackBtn.setForeground(maroon);
        blstBackBtn.addActionListener(this);
        ctntBorrowerListPanel.add(blstBackBtn);
    }

    public void initializeUpdateInventoryPanel(){
        ctntUpdateInventoryPanel = new JPanel();
        ctntUpdateInventoryPanel.setLayout(new GridBagLayout());
        ctntUpdateInventoryPanel.setBackground(lightgray);
        ctntUpdateInventoryPanel.setPreferredSize(new Dimension(900, 500));
        ctntUpdateInventoryPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, maroon));
        upinBackBtn = new JButton("Go Back");
        upinBackBtn.setBackground(lightergray);
        upinBackBtn.setForeground(maroon);
        upinBackBtn.addActionListener(this);
        ctntUpdateInventoryPanel.add(upinBackBtn);
    }

    public void initializeTransactionHistoryPanel(){
        ctntTransactionHistoryPanel = new JPanel();
        ctntTransactionHistoryPanel.setLayout(new GridBagLayout());
        ctntTransactionHistoryPanel.setBackground(lightgray);
        ctntTransactionHistoryPanel.setPreferredSize(new Dimension(900, 500));
        ctntTransactionHistoryPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, maroon));
        tranBackBtn = new JButton("Go Back");
        tranBackBtn.setBackground(lightergray);
        tranBackBtn.setForeground(maroon);
        tranBackBtn.addActionListener(this);
        ctntTransactionHistoryPanel.add(tranBackBtn);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void initializeBranding(){
        maroon = new Color(94,38,5);
        lightgray = new Color(238, 224, 229);
        lightergray = new Color(242, 242, 242); 
        white = new Color(255, 255, 255);

        try {
            Font palatinoFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Font/Palatino.ttf"));
            sizedFontPalatinoBig = palatinoFont.deriveFont(Font.PLAIN, 35);
            sizedFontPalatinoSmall = palatinoFont.deriveFont(Font.PLAIN, 27);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            upLogo = ImageIO.read(new File("Assets/Logo/UP Logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lgnUPLogoResized = resizeImage(upLogo, 150, 150);
        rbbnUPLogoResized = resizeImage(upLogo, 110, 110);
        
        UIManager.put("OptionPane.background", lightgray);
        UIManager.put("Panel.background", lightgray);
        UIManager.put("OptionPane.messageForeground", maroon);
        UIManager.put("Button.background", maroon);
        UIManager.put("Button.foreground", white);
        UIManager.put("Label.font", new Font("Roboto", Font.PLAIN, 15));
        UIManager.put("Button.font", new Font("Roboto", Font.PLAIN, 15));
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    // Button Logic

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lgnLoginBtn) {
            mainFrame.remove(loginPanel);
            mainFrame.add(mainPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
        } else if (e.getSource() == ctntBorrowItemBtn) {
            mainContentPanel.removeAll();
            mainContentPanel.add(ctntBorrowItemPanel);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
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
                System.out.println("LOGOUT");
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
