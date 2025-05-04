
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUITransactionHistoryPanel extends JPanel{
    private Branding branding;
    private JPanel screen1, scrn1TransactionContentPanel;
    private JButton screen1BackButton;

    public GUITransactionHistoryPanel(Branding branding, JButton tranBackBtn ){
        this.branding = branding;
        this.screen1BackButton = tranBackBtn;
        this.setLayout(new GridBagLayout());
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));
        
        // Different Screens Inside GUITransactionHistoryPanel (in this case 1 screen lang)
        initializeScreen1();
        

        GridBagConstraints transactionHistoryPanelGBC = new GridBagConstraints();
        transactionHistoryPanelGBC.fill = GridBagConstraints.BOTH;
        transactionHistoryPanelGBC.weightx = 1;
        transactionHistoryPanelGBC.weighty = 1;
        this.add(screen1, transactionHistoryPanelGBC);

    }
    
    public void initializeScreen1(){
        screen1 = new JPanel();
        screen1.setLayout(new GridBagLayout());
        screen1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        screen1.setBackground(branding.lightgray);
        
        scrn1TransactionContentPanel = new JPanel();
        scrn1TransactionContentPanel.setLayout(new BoxLayout(scrn1TransactionContentPanel, BoxLayout.Y_AXIS));
        scrn1TransactionContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        // Temporary data only. To be replaced by the final sql to java implementation 
        String[][] entries = {
            {"Borrow", "Roberto, Jack Cole", "202300000", "2:43 PM", "1 03 Feb 2023"},
            {"Return", "Roberto, Jack Cole", "202300000", "5:23 PM", "2 03 Feb 2023"},
            {"Borrow", "Montes, Jhun Kenneth", "10030302", "11:22 AM", "3 05 Feb 2023"},
            {"Return", "Montes, Jhun Kenneth", "10030302", "3:22 PM", "4 05 Feb 2023"},
            {"Borrow", "Roberto, Jack Cole", "202300000", "2:43 PM", "5 03 Feb 2023"},
            {"Return", "Roberto, Jack Cole", "202300000", "5:23 PM", "6 03 Feb 2023"},
            {"Borrow", "Montes, Jhun Kenneth", "10030302", "11:22 AM", "7 05 Feb 2023"},
            {"Return", "Montes, Jhun Kenneth", "10030302", "3:22 PM", "8 05 Feb 2023"},
            {"Borrow", "Roberto, Jack Cole", "202300000", "2:43 PM", "9 03 Feb 2023"},
            {"Return", "Roberto, Jack Cole", "202300000", "5:23 PM", "10 03 Feb 2023"},
            {"Borrow", "Montes, Jhun Kenneth", "10030302", "11:22 AM", "11 05 Feb 2023"},
            {"Return", "Montes, Jhun Kenneth", "10030302", "3:22 PM", "12 05 Feb 2023"},
        };
        
        for (String[] tuple: entries){
            int i = 0;
            JLabel stateLabel = new JLabel();
            switch (tuple[i++]) {
                case "Borrow" -> stateLabel.setIcon(new ImageIcon(branding.borrowIcon));
                case "Return" -> stateLabel.setIcon(new ImageIcon(branding.returnIcon));
                default -> System.err.println("Transaction History Label Error");
            }
            JLabel nameLabel = new JLabel(tuple[i++]);
            JLabel studentIdLabel = new JLabel(tuple[i++]);
            JLabel timeLabel = new JLabel(tuple[i++]);
            JLabel dateLabel = new JLabel(tuple[i++]);

            nameLabel.setForeground(branding.white);
            studentIdLabel.setForeground(branding.white);
            timeLabel.setForeground(branding.white);
            dateLabel.setForeground(branding.white);

            JPanel statePanel = new JPanel();
            JPanel namePanel = new JPanel();
            JPanel studentIdPanel = new JPanel();
            JPanel timePanel = new JPanel();
            JPanel datePanel = new JPanel();

            statePanel.setLayout(new BorderLayout());
            namePanel.setLayout(new BorderLayout());
            studentIdPanel.setLayout(new BorderLayout());
            timePanel.setLayout(new BorderLayout());
            datePanel.setLayout(new BorderLayout());

            statePanel.setPreferredSize(new Dimension(30, 70));
            namePanel.setPreferredSize(new Dimension(70, 70));
            studentIdPanel.setPreferredSize(new Dimension(50, 70));
            timePanel.setPreferredSize(new Dimension(50, 70));
            datePanel.setPreferredSize(new Dimension(50, 70));

            statePanel.setOpaque(false);
            namePanel.setOpaque(false);
            studentIdPanel.setOpaque(false);
            timePanel.setOpaque(false);
            datePanel.setOpaque(false);

            statePanel.add(stateLabel, BorderLayout.WEST);
            namePanel.add(nameLabel, BorderLayout.WEST);
            studentIdPanel.add(studentIdLabel, BorderLayout.WEST);
            timePanel.add(timeLabel, BorderLayout.WEST);
            datePanel.add(dateLabel, BorderLayout.WEST);

            JPanel tupleInfoPanel = new JPanel();
            tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
            tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            tupleInfoPanel.setLayout(new GridBagLayout());
            tupleInfoPanel.setBackground(branding.maroon);


            GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
            tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            tupleInfoPanelGBC.gridx = 0;
            tupleInfoPanelGBC.ipadx = 50;
            tupleInfoPanelGBC.weightx = 0.1;
            tupleInfoPanel.add(statePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 150;
            tupleInfoPanelGBC.weightx = 0.3;
            tupleInfoPanel.add(namePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(studentIdPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(timePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(datePanel, tupleInfoPanelGBC);
            scrn1TransactionContentPanel.add(tupleInfoPanel);
            scrn1TransactionContentPanel.add(Box.createVerticalStrut(10)); //add gaps between touples
        }

        JScrollPane scrollContentPanel = new JScrollPane(scrn1TransactionContentPanel);
        scrollContentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollContentPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollContentPanel.setBorder(null);
        branding.reskinScrollBar(scrollContentPanel, branding.gray);

        JPanel scrn1MenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        scrn1MenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 28));
        scrn1MenuPanel.setBackground(branding.maroon);
        scrn1MenuPanel.setOpaque(false);

        screen1BackButton.setPreferredSize(new Dimension(150, 30));
        screen1BackButton.setBackground(branding.maroon);
        screen1BackButton.setForeground(branding.lightergray);
        scrn1MenuPanel.add(screen1BackButton);

        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.fill = GridBagConstraints.BOTH;
        screen1GBC.weightx = 1;
        screen1GBC.weighty = 0.9;
        screen1GBC.gridx = 0;
        screen1GBC.gridy = 0;
        screen1.add(scrollContentPanel, screen1GBC);
        screen1GBC.weighty = 0.01;
        screen1GBC.gridy = 1;
        screen1.add(scrn1MenuPanel, screen1GBC);
    }

    public void refreshEntries(String[][] entries) {
        scrn1TransactionContentPanel.removeAll();

        for (String[] tuple : entries){
            int i = 0;
            JLabel stateLabel = new JLabel();
            switch (tuple[i++]) {
                case "Borrow" -> stateLabel.setIcon(new ImageIcon(branding.borrowIcon));
                case "Return" -> stateLabel.setIcon(new ImageIcon(branding.returnIcon));
                default -> System.err.println("Transaction History Label Error");
            }
            JLabel label1 = new JLabel(tuple[i++]);
            JLabel label2 = new JLabel(tuple[i++]);
            JLabel label3 = new JLabel(tuple[i++]);
            JLabel label4 = new JLabel(tuple[i++]);
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

            statePanel.setPreferredSize(new Dimension(30, 70));
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
            tupleInfoPanelGBC.weightx = 0.1;
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
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 70;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(label4Panel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 70;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(label5Panel, tupleInfoPanelGBC);
            scrn1TransactionContentPanel.add(tupleInfoPanel);
            scrn1TransactionContentPanel.add(Box.createVerticalStrut(10)); //add gaps between touples
        }

        scrn1TransactionContentPanel.revalidate();
        scrn1TransactionContentPanel.repaint();
    }
}
