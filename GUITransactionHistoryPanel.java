import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class GUITransactionHistoryPanel extends JPanel {
    private Branding branding;
    private JPanel screen1, scrn1TransactionContentPanel;
    private JButton screen1BackButton, toggleTransactionTypeButton;
    private Controller ctrl;
    private List<String[]> allEntries;
    private boolean showingBorrows = true;

    public GUITransactionHistoryPanel(Controller ctrl, Branding branding, JButton tranBackBtn) {
        this.ctrl = ctrl;
        this.branding = branding;
        this.screen1BackButton = tranBackBtn;
        this.allEntries = new ArrayList<>();
        
        this.setLayout(new GridBagLayout());
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));
        
        initializeScreen1();

        GridBagConstraints transactionHistoryPanelGBC = new GridBagConstraints();
        transactionHistoryPanelGBC.fill = GridBagConstraints.BOTH;
        transactionHistoryPanelGBC.weightx = 1;
        transactionHistoryPanelGBC.weighty = 1;
        this.add(screen1, transactionHistoryPanelGBC);
    }
    
    public void initializeScreen1() {
        screen1 = new JPanel();
        screen1.setLayout(new GridBagLayout());
        screen1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        screen1.setBackground(branding.lightgray);
        
        scrn1TransactionContentPanel = new JPanel();
        scrn1TransactionContentPanel.setLayout(new BoxLayout(scrn1TransactionContentPanel, BoxLayout.Y_AXIS));
        scrn1TransactionContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        JScrollPane scrollContentPanel = new JScrollPane(scrn1TransactionContentPanel);
        scrollContentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollContentPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollContentPanel.setBorder(null);
        branding.reskinScrollBar(scrollContentPanel, branding.gray);

        // Add headers panel
        JPanel headersPanel = createBorrowsHeadersPanel();

        // Create toggle button panel
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        togglePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 28));
        togglePanel.setBackground(branding.maroon);
        togglePanel.setOpaque(false);

        // Toggle transaction type button
        toggleTransactionTypeButton = new JButton("View Returns");
        toggleTransactionTypeButton.setPreferredSize(new Dimension(150, 30));
        toggleTransactionTypeButton.setBackground(branding.maroon);
        toggleTransactionTypeButton.setForeground(branding.lightergray);
        toggleTransactionTypeButton.addActionListener(e -> toggleTransactionType());
        togglePanel.add(toggleTransactionTypeButton);

        // Back button
        screen1BackButton.setPreferredSize(new Dimension(150, 30));
        screen1BackButton.setBackground(branding.maroon);
        screen1BackButton.setForeground(branding.lightergray);
        togglePanel.add(screen1BackButton);

        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.fill = GridBagConstraints.BOTH;
        screen1GBC.weightx = 1;
        screen1GBC.weighty = 0.05; // Reduced to make room for headers
        screen1GBC.gridx = 0;
        screen1GBC.gridy = 0;
        screen1GBC.insets = new Insets(0, 0, 10, 0); 
        screen1.add(headersPanel, screen1GBC);

        screen1GBC.weighty = 0.85;
        screen1GBC.gridy = 1;
        screen1.add(scrollContentPanel, screen1GBC);

        screen1GBC.weighty = 0.1;
        screen1GBC.gridy = 2;
        screen1.add(togglePanel, screen1GBC);
    }
    
    private JPanel createBorrowsHeadersPanel() {
        JPanel headersPanel = new JPanel(new GridBagLayout());
        headersPanel.setBackground(branding.maroon);

        GridBagConstraints headerGBC = new GridBagConstraints();
        headerGBC.fill = GridBagConstraints.HORIZONTAL;
        
        // Create labels for headers
        JLabel[] headerLabels = new JLabel[3];
        headerLabels[0] = new JLabel("                     Date and Time");
        headerLabels[1] = new JLabel("Name");
        headerLabels[2] = new JLabel("Student Number");

        // Style headers
        for (JLabel label : headerLabels) {
            label.setForeground(branding.lightergray);
            label.setFont(new Font(label.getFont().getName(), Font.BOLD, 12));
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Add headers
        headerGBC.gridx = 1; // Skipping the first column (for icon)
        headerGBC.weightx = 0.3;
        headerGBC.ipadx = 100;
        //headerGBC.insets = new Insets(0, 80, 0, 0);
        headersPanel.add(headerLabels[0], headerGBC);
        
        headerGBC.gridx++;
        headerGBC.weightx = 0.2;
        headerGBC.ipadx = 70;
        headersPanel.add(headerLabels[1], headerGBC);
        
        headerGBC.gridx++;
        headersPanel.add(headerLabels[2], headerGBC);

        headersPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        return headersPanel;
    }
    
    private JPanel createReturnsHeadersPanel() {
        JPanel headersPanel = new JPanel(new GridBagLayout());
        headersPanel.setBackground(branding.maroon);
        GridBagConstraints headerGBC = new GridBagConstraints();
        headerGBC.fill = GridBagConstraints.HORIZONTAL;
        
        // Create labels for headers
        JLabel[] headerLabels = new JLabel[3];
        headerLabels[0] = new JLabel("                     Date and Time");
        headerLabels[1] = new JLabel("Name");
        headerLabels[2] = new JLabel("Item");
        JLabel accountabilityLabel = new JLabel("Accountability");

        // Style headers
        for (JLabel label : headerLabels) {
            label.setForeground(branding.lightergray);
            label.setFont(new Font(label.getFont().getName(), Font.BOLD, 12));
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }
        accountabilityLabel.setForeground(branding.lightergray);
        accountabilityLabel.setFont(new Font(accountabilityLabel.getFont().getName(), Font.BOLD, 12));
        accountabilityLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Add headers
        headerGBC.gridx = 1; // Skipping the first column (for icon)
        headerGBC.weightx = 0.3;
        headerGBC.ipadx = 100;
        headersPanel.add(headerLabels[0], headerGBC);
        
        headerGBC.gridx++;
        headerGBC.weightx = 0.2;
        headerGBC.ipadx = 70;
        headersPanel.add(headerLabels[1], headerGBC);
        
        headerGBC.gridx++;
        headersPanel.add(headerLabels[2], headerGBC);
        
        headerGBC.gridx++;
        headersPanel.add(accountabilityLabel, headerGBC);

        headersPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        return headersPanel;
    }

    private void toggleTransactionType() {
        showingBorrows = !showingBorrows;
        
        // Update button text
        toggleTransactionTypeButton.setText(showingBorrows ? "View Returns" : "View Borrows");
        
        // Update headers panel
        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.fill = GridBagConstraints.BOTH;
        screen1GBC.weightx = 1;
        screen1GBC.weighty = 0.05;
        screen1GBC.gridx = 0;
        screen1GBC.gridy = 0;
        screen1GBC.insets = new Insets(0, 0, 10, 0);
        
        // Remove existing component at the specified location
        Component[] components = screen1.getComponents();
        for (Component comp : components) {
            GridBagConstraints constraints = ((GridBagLayout)screen1.getLayout()).getConstraints(comp);
            if (constraints.gridx == screen1GBC.gridx && constraints.gridy == screen1GBC.gridy) {
                screen1.remove(comp);
                break;
            }
        }
        
        // Add appropriate headers
        screen1.add(showingBorrows ? createBorrowsHeadersPanel() : createReturnsHeadersPanel(), screen1GBC);
        
        // Revalidate and repaint to ensure visual update
        screen1.revalidate();
        screen1.repaint();
        
        // Refresh entries
        refreshEntries(allEntries);
    }

    public void refreshEntries(List<String[]> entries) {
        // Store all entries for toggle functionality
        this.allEntries = new ArrayList<>(entries);
        
        scrn1TransactionContentPanel.removeAll();

        for (String[] tuple : entries) {
            // Determine if the entry should be displayed based on current view
            boolean isBorrowTransaction = tuple[0].equals("Borrow");
            if ((showingBorrows && !isBorrowTransaction) || (!showingBorrows && isBorrowTransaction)) {
                continue;
            }

            int i = 0;
            JLabel stateLabel = new JLabel();
            switch (tuple[i++]) {
                case "Borrow" -> stateLabel.setIcon(new ImageIcon(branding.borrowIcon));
                case "Return" -> stateLabel.setIcon(new ImageIcon(branding.returnIcon));
                default -> System.err.println("Transaction History Label Error");
            }
            JLabel label1 = new JLabel(branding.reformatDateLabel(tuple[i++]));
            JLabel label2 = new JLabel(tuple[i++]);
            JLabel label3 = new JLabel(tuple[i++]);
            JLabel label4 = new JLabel("");
            i++; // Skip an entry if needed
            JLabel label5 = new JLabel();
            if(tuple[i] != null) label5.setText("Php " + tuple[i++]);
            else label5.setText("");
            
            label1.setForeground(branding.white);
            label2.setForeground(branding.white);
            label3.setForeground(branding.white);
            label4.setForeground(branding.white);
            label5.setForeground(branding.white);

            JPanel statePanel = new JPanel();
            JPanel label1Panel = new JPanel();
            JPanel label2Panel = new JPanel();
            JPanel label3Panel = new JPanel();
            JPanel label4Panel = new JPanel();
            JPanel label5Panel = new JPanel();

            statePanel.setLayout(new BorderLayout());
            label1Panel.setLayout(new BorderLayout());
            label2Panel.setLayout(new BorderLayout());
            label3Panel.setLayout(new BorderLayout());
            label4Panel.setLayout(new BorderLayout());
            label5Panel.setLayout(new BorderLayout());

            statePanel.setPreferredSize(new Dimension(10, 70));
            label1Panel.setPreferredSize(new Dimension(50, 70));
            label2Panel.setPreferredSize(new Dimension(70, 70));
            label3Panel.setPreferredSize(new Dimension(50, 70));
            label4Panel.setPreferredSize(new Dimension(50, 70));
            label5Panel.setPreferredSize(new Dimension(50, 70));

            statePanel.setOpaque(false);
            label1Panel.setOpaque(false);
            label2Panel.setOpaque(false);
            label3Panel.setOpaque(false);
            label4Panel.setOpaque(false);
            label5Panel.setOpaque(false);

            statePanel.add(stateLabel, BorderLayout.WEST);
            label1Panel.add(label1, BorderLayout.WEST);
            label2Panel.add(label2, BorderLayout.WEST);
            label3Panel.add(label3, BorderLayout.WEST);
            label4Panel.add(label4, BorderLayout.WEST);
            label5Panel.add(label5, BorderLayout.WEST);

            JPanel tupleInfoPanel = new JPanel();
            tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
            tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            tupleInfoPanel.setLayout(new GridBagLayout());
            tupleInfoPanel.setBackground(branding.maroon);

            GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
            tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            tupleInfoPanelGBC.gridx = 0;
            tupleInfoPanelGBC.ipadx = 50;
            tupleInfoPanelGBC.weightx = 0.01;
            tupleInfoPanel.add(statePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.3;
            tupleInfoPanel.add(label1Panel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 70;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(label2Panel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 70;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(label3Panel, tupleInfoPanelGBC);

            // For returns, add accountability column
            if (!showingBorrows) {
                tupleInfoPanelGBC.gridx++;
                tupleInfoPanelGBC.ipadx = 70;
                tupleInfoPanelGBC.weightx = 0.2;
                tupleInfoPanel.add(label5Panel, tupleInfoPanelGBC);
            }

            scrn1TransactionContentPanel.add(tupleInfoPanel);
            scrn1TransactionContentPanel.add(Box.createVerticalStrut(10)); //add gaps between tuples
        }

        scrn1TransactionContentPanel.revalidate();
        scrn1TransactionContentPanel.repaint();
    }
}