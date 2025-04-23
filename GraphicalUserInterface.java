import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GraphicalUserInterface implements ActionListener {

    private JButton lgnLoginBtn;
    private JButton ctntBorrowItemBtn, ctntBorrowerListBtn, ctntUpdateInventoryBtn, ctntTransactionHistoryBtn;
    private JPanel ctntBorrowItemPanel, ctntBorrowerListPanel, ctntUpdateInventoryPanel, ctntTransactionHistoryPanel;
    private JButton rbbnLogoutBtn, rbbnUserBtn, rbbnAboutBtn;
    private JButton bitmBackBtn, blstBackBtn, upinBackBtn, tranBackBtn;
    private GUILoginPanel loginPanel;
    private GUIMainPanel mainPanel;
    private JFrame mainFrame;
    private Queries queries;
    private Branding branding;

    public GraphicalUserInterface() {
        queries = new Queries();
        branding = new Branding();

        intializeMainFrame();
        initializeLoginPanel();
        initializeMainPanel();
    }

    public void intializeMainFrame() {
        mainFrame = new JFrame("UPTC General Laboratory Database");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setMinimumSize(new Dimension(1000, 700));
    }

    public void initializeLoginPanel() {
        lgnLoginBtn = new JButton("Login");
        lgnLoginBtn.setBackground(branding.maroon);
        lgnLoginBtn.setForeground(branding.white);
        lgnLoginBtn.setFocusable(false);
        lgnLoginBtn.setPreferredSize(new Dimension(250, 35));
        lgnLoginBtn.addActionListener(this);

        loginPanel = new GUILoginPanel(branding, lgnLoginBtn);
        mainFrame.add(loginPanel);
        mainFrame.setVisible(true);
    }

    public void initializeMainPanel() {
        // --- Ribbon buttons ---
        rbbnLogoutBtn = new JButton("Logout");
        rbbnUserBtn = new JButton("User");
        rbbnAboutBtn = new JButton("About");

        JButton[] ribbonButtons = { rbbnLogoutBtn, rbbnUserBtn, rbbnAboutBtn };
        for (JButton btn : ribbonButtons) {
            btn.setContentAreaFilled(true);
            btn.setBackground(branding.maroon);
            btn.setBorderPainted(false);
            btn.setFocusable(false);
            btn.setForeground(branding.white);
            btn.addActionListener(this);
        }

        // --- Content buttons ---
        ctntBorrowItemBtn = new JButton("Borrow Item");
        ctntBorrowerListBtn = new JButton("Borrower List");
        ctntUpdateInventoryBtn = new JButton("Update Inventory");
        ctntTransactionHistoryBtn = new JButton("Transaction History");

        JButton[] contentButtons = { ctntBorrowItemBtn, ctntBorrowerListBtn, ctntUpdateInventoryBtn, ctntTransactionHistoryBtn };
        for (JButton btn : contentButtons) {
            btn.setForeground(branding.white);
            btn.setBackground(branding.maroon);
            btn.setFocusable(false);
            btn.setPreferredSize(new Dimension(270, 35));
            btn.addActionListener(this);
        }

        // --- Back buttons ---
        bitmBackBtn = new JButton("Go Back");
        blstBackBtn = new JButton("Go Back");
        upinBackBtn = new JButton("Go Back");
        tranBackBtn = new JButton("Go Back");

        JButton[] backButtons = { bitmBackBtn, blstBackBtn, upinBackBtn, tranBackBtn };
        for (JButton btn : backButtons) {
            btn.addActionListener(this);
        }

        // Main panel layout with buttons
        mainPanel = new GUIMainPanel(
            branding,
            this,
            rbbnLogoutBtn, rbbnUserBtn, rbbnAboutBtn,
            ctntBorrowItemBtn, ctntBorrowerListBtn, ctntUpdateInventoryBtn, ctntTransactionHistoryBtn
        );

        // Sub-panel views with back buttons
        ctntBorrowItemPanel = new GUIBorrowItemPanel(branding, bitmBackBtn);
        ctntBorrowerListPanel = new GUIBorrowItemPanel(branding, blstBackBtn);
        ctntUpdateInventoryPanel = new GUIBorrowItemPanel(branding, upinBackBtn);
        ctntTransactionHistoryPanel = new GUIBorrowItemPanel(branding, tranBackBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == lgnLoginBtn) {
            queries.login(loginPanel.lgnInputUsernameField, loginPanel.lgnInputPasswordField, loginPanel, mainFrame, mainPanel, loginPanel.lgnStatusLabel);
            loginPanel.lgnInputUsernameField.setText("");
            loginPanel.lgnInputPasswordField.setText("");

            mainFrame.remove(loginPanel);
            mainFrame.add(mainPanel);
            mainFrame.revalidate();
            mainFrame.repaint();

        } else if (src == ctntBorrowItemBtn) {
            showPanel(ctntBorrowItemPanel);
            queries.borrowItems(new java.util.Scanner(System.in));
        } else if (src == ctntBorrowerListBtn) {
            showPanel(ctntBorrowerListPanel);
        } else if (src == ctntUpdateInventoryBtn) {
            showPanel(ctntUpdateInventoryPanel);
        } else if (src == ctntTransactionHistoryBtn) {
            showPanel(ctntTransactionHistoryPanel);
        } else if (src == rbbnLogoutBtn) {
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
                //mainPanel.contentPanel.removeAll();
                mainPanel.revalidate();
                mainPanel.repaint();
                mainFrame.revalidate();
                mainFrame.repaint();
            }
        } else if (src == bitmBackBtn || src == blstBackBtn || src == upinBackBtn || src == tranBackBtn) {
            showMainMenu();
        }
    }

    private void showPanel(JPanel panel) {
        mainPanel.contentPanel.removeAll();
        mainPanel.contentPanel.add(panel);
        mainPanel.contentPanel.revalidate();
        mainPanel.contentPanel.repaint();
    }

    private void showMainMenu() {
        mainPanel.contentPanel.removeAll();
        mainPanel.contentPanel.add(mainPanel.menuButtonsPanel);
        mainPanel.contentPanel.revalidate();
        mainPanel.contentPanel.repaint();
    }
}
