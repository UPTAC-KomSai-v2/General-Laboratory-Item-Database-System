
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class GUIBorrowerListPanel extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private Branding branding;
    private JButton screen2BackBtn, screen2CompleteBtn, screen2ConfirmBtn;
    private JPanel screen1, screen2, scrn1BorrowerListContentPanel, scrn2BorrowedItemsContentPanel;
    private JLabel borrowerLabel, borrowerNameLabel, studentIdLabel, timeLabel, dateLabel;

    private String[] borrowerInfo;

    public GUIBorrowerListPanel(Branding branding, JButton blstBackBtn ){
        this.branding = branding;
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        //this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));

        initializeScreen1(blstBackBtn);
        initializeScreen2();

        this.add(screen1, "Panel1");
        this.add(screen2, "Panel2");
    }

    private void initializeScreen1(JButton blstBackBtn){
        screen1 = new JPanel();
        screen1.setLayout(new GridBagLayout());
        screen1.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(branding.maroon, 5),
        BorderFactory.createEmptyBorder(20, 0, 0, 0)));

        screen1.setBackground(branding.lightgray);

        scrn1BorrowerListContentPanel = new JPanel();
        scrn1BorrowerListContentPanel.setLayout(new BoxLayout(scrn1BorrowerListContentPanel, BoxLayout.Y_AXIS));
        scrn1BorrowerListContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        // Temporary data only. To be replaced by the final sql to java implementation  
        String[][] entries = {
            {"Tung Tung Sahur", "202401234", "4:20 AM", "7 11 Feb 2023"},
            {"Chimpanzini Bananini", "30303030", "3:33 AM", "5 08 Feb 2023"},
            {"Skibidi Ohio Sigma", "69696969", "12:01 PM", "1 13 Feb 2023"},
            {"Sussy Baka", "12345678", "9:11 PM", "2 14 Feb 2023"},
            {"Tralalelo Tropalang", "98765432", "10:10 AM", "4 15 Feb 2023"},
            {"Bombardini Dipinili", "11223344", "1:00 PM", "6 17 Feb 2023"},
            {"Gigachad McMuscle", "55667788", "7:45 AM", "3 19 Feb 2023"},
            {"Juan Dela Cruz", "42042042", "6:30 PM", "5 20 Feb 2023"},
            {"Beluga Whale", "00000001", "2:22 AM", "2 21 Feb 2023"},
            {"Sigma Rizzler", "77777777", "8:08 AM", "7 22 Feb 2023"},
        };
        
        for (String[] tuple: entries){
            int i = 0;
            
            JLabel nameLabel = new JLabel(tuple[i++]);
            JLabel studentIdLabel = new JLabel(tuple[i++]);
            JLabel timeLabel = new JLabel(tuple[i++]);
            JLabel dateLabel = new JLabel(tuple[i++]);
            JLabel arrowLabel = new JLabel();
            arrowLabel.setIcon(new ImageIcon(branding.arrowIcon));

            nameLabel.setForeground(branding.white);
            studentIdLabel.setForeground(branding.white);
            timeLabel.setForeground(branding.white);
            dateLabel.setForeground(branding.white);
            
            JPanel namePanel = new JPanel();
            JPanel studentIdPanel = new JPanel();
            JPanel timePanel = new JPanel();
            JPanel datePanel = new JPanel();
            JPanel arrowPanel = new JPanel();

            namePanel.setLayout(new BorderLayout());
            studentIdPanel.setLayout(new BorderLayout());
            timePanel.setLayout(new BorderLayout());
            datePanel.setLayout(new BorderLayout());
            arrowPanel.setLayout(new BorderLayout());

            namePanel.setPreferredSize(new Dimension(60, 70));
            studentIdPanel.setPreferredSize(new Dimension(40, 70));
            timePanel.setPreferredSize(new Dimension(40, 70));
            datePanel.setPreferredSize(new Dimension(40, 70));
            arrowPanel.setPreferredSize(new Dimension(1, 70));
            
            namePanel.setOpaque(false);
            studentIdPanel.setOpaque(false);
            timePanel.setOpaque(false);
            datePanel.setOpaque(false);
            arrowPanel.setOpaque(false);

            namePanel.add(nameLabel, BorderLayout.WEST);
            studentIdPanel.add(studentIdLabel, BorderLayout.WEST);
            timePanel.add(timeLabel, BorderLayout.WEST);
            datePanel.add(dateLabel, BorderLayout.WEST);
            arrowPanel.add(arrowLabel, BorderLayout.EAST);

            JPanel tupleInfoPanel = new JPanel();
            tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
            tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 20));
            tupleInfoPanel.setLayout(new GridBagLayout());
            tupleInfoPanel.setBackground(branding.maroon);
            
            GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
            tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            tupleInfoPanelGBC.gridx = 0;
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
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(arrowPanel, tupleInfoPanelGBC);

            tupleInfoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Double Click To Enter");
                    if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        tupleInfoPanel.setBackground(branding.white);
                        cardLayout.next(GUIBorrowerListPanel.this);
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    tupleInfoPanel.setBackground(branding.darkermaroon);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    tupleInfoPanel.setBackground(branding.maroon);
                }
            });

            scrn1BorrowerListContentPanel.add(tupleInfoPanel);
            scrn1BorrowerListContentPanel.add(Box.createVerticalStrut(10)); //add gaps between touples
        }

        JScrollPane scrn1ScrollContentPanel = new JScrollPane(scrn1BorrowerListContentPanel);
        scrn1ScrollContentPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrn1ScrollContentPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrn1ScrollContentPanel.setBorder(null);
        branding.reskinScrollBar(scrn1ScrollContentPanel, branding.gray);

        JPanel scrn1MenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        scrn1MenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 28));
        scrn1MenuPanel.setBackground(branding.maroon);
        scrn1MenuPanel.setOpaque(false);

        blstBackBtn.setPreferredSize(new Dimension(150, 30));
        blstBackBtn.setBackground(branding.maroon);
        blstBackBtn.setForeground(branding.lightergray);
        scrn1MenuPanel.add(blstBackBtn);

        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.fill = GridBagConstraints.BOTH;
        screen1GBC.weightx = 1;
        screen1GBC.weighty = 0.9;
        screen1GBC.gridx = 0;
        screen1GBC.gridy = 0;
        screen1.add(scrn1ScrollContentPanel, screen1GBC);
        screen1GBC.weighty = 0.01;
        screen1GBC.gridy = 1;
        screen1.add(scrn1MenuPanel, screen1GBC);
    }

    public void refreshEntries1(String[][] entries, Queries queries) {
        scrn1BorrowerListContentPanel.removeAll();
    
        if (entries == null || entries.length == 0) {
            JLabel noDataLabel = new JLabel("No borrow entries found.");
            noDataLabel.setForeground(branding.white);
            scrn1BorrowerListContentPanel.add(noDataLabel);
        } else {
            for (String[] tuple : entries) {
                int i = 0;
                
                JLabel nameLabel = new JLabel(tuple[i++]);
                JLabel studentIdLabel = new JLabel(tuple[i++]);
                JLabel timeLabel = new JLabel(tuple[i++]);
                JLabel dateLabel = new JLabel(tuple[i++]);
                JLabel arrowLabel = new JLabel();
                arrowLabel.setIcon(new ImageIcon(branding.arrowIcon));

                nameLabel.setForeground(branding.white);
                studentIdLabel.setForeground(branding.white);
                timeLabel.setForeground(branding.white);
                dateLabel.setForeground(branding.white);
                
                JPanel namePanel = new JPanel();
                JPanel studentIdPanel = new JPanel();
                JPanel timePanel = new JPanel();
                JPanel datePanel = new JPanel();
                JPanel arrowPanel = new JPanel();

                namePanel.setLayout(new BorderLayout());
                studentIdPanel.setLayout(new BorderLayout());
                timePanel.setLayout(new BorderLayout());
                datePanel.setLayout(new BorderLayout());
                arrowPanel.setLayout(new BorderLayout());

                namePanel.setPreferredSize(new Dimension(60, 70));
                studentIdPanel.setPreferredSize(new Dimension(40, 70));
                timePanel.setPreferredSize(new Dimension(40, 70));
                datePanel.setPreferredSize(new Dimension(40, 70));
                arrowPanel.setPreferredSize(new Dimension(1, 70));
                
                namePanel.setOpaque(false);
                studentIdPanel.setOpaque(false);
                timePanel.setOpaque(false);
                datePanel.setOpaque(false);
                arrowPanel.setOpaque(false);

                namePanel.add(nameLabel, BorderLayout.WEST);
                studentIdPanel.add(studentIdLabel, BorderLayout.WEST);
                timePanel.add(timeLabel, BorderLayout.WEST);
                datePanel.add(dateLabel, BorderLayout.WEST);
                arrowPanel.add(arrowLabel, BorderLayout.EAST);

                JPanel tupleInfoPanel = new JPanel();
                tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
                tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 20));
                tupleInfoPanel.setLayout(new GridBagLayout());
                tupleInfoPanel.setBackground(branding.maroon);
                
                GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
                tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
                tupleInfoPanelGBC.gridx = 0;
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
                tupleInfoPanelGBC.gridx++;
                tupleInfoPanelGBC.ipadx = 100;
                tupleInfoPanelGBC.weightx = 0.2;
                tupleInfoPanel.add(arrowPanel, tupleInfoPanelGBC);

                tupleInfoPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("Double Click To Enter");
                        if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                            tupleInfoPanel.setBackground(branding.white);
                            cardLayout.next(GUIBorrowerListPanel.this);
                            borrowerInfo = new String[]{tuple[0],tuple[1],tuple[4],tuple[5] + " - " + tuple[6]};
                            refreshEntries2(borrowerInfo, queries.getItemsBorrowed(tuple[1],tuple[2],tuple[3]), queries);
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        tupleInfoPanel.setBackground(branding.darkermaroon);
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        tupleInfoPanel.setBackground(branding.maroon);
                    }
                });

                scrn1BorrowerListContentPanel.add(tupleInfoPanel);
                scrn1BorrowerListContentPanel.add(Box.createVerticalStrut(10)); //add gaps between touples
            }
        }
    
        scrn1BorrowerListContentPanel.revalidate();
        scrn1BorrowerListContentPanel.repaint();
    }

    public void initializeScreen2(){
        screen2 = new JPanel();
        screen2.setLayout(new GridBagLayout());
        screen2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        screen2.setBackground(branding.maroon);

        scrn2BorrowedItemsContentPanel = new JPanel();
        scrn2BorrowedItemsContentPanel.setLayout(new BoxLayout(scrn2BorrowedItemsContentPanel, BoxLayout.Y_AXIS));
        scrn2BorrowedItemsContentPanel.setBackground(branding.maroon);
        scrn2BorrowedItemsContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

        // Temporary data only. To be replaced by the final sql to java implementation 
        String[][] entries = {
            {"2x", "Beaker", "100 ml"},
            {"1x", "Petri Dish", ""},
            {"3x", "AquaFlask", "1000 ml"},
            {"2x", "Beaker", "100 ml"},
            {"1x", "Petri Dish", ""},
            {"3x", "AquaFlask", "1000 ml"},
            {"2x", "Beaker", "100 ml"},
            {"1x", "Petri Dish", ""},
            {"3x", "AquaFlask", "1000 ml"},
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
            returnItemBtn.addActionListener(this);
            
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

        borrowerInfo = new String[]{"Example, John Pork", "69696969", "12:01 PM", "13 Feb 2023"};

        JPanel scrn2BorrowerInfoPanel = new JPanel();
        scrn2BorrowerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 0));
        scrn2BorrowerInfoPanel.setLayout(new GridBagLayout());
        scrn2BorrowerInfoPanel.setOpaque(false);
        int i = 0;
        borrowerLabel = new JLabel("Borrower: ");
        borrowerNameLabel = new JLabel(borrowerInfo[i++]);
        studentIdLabel = new JLabel(borrowerInfo[i++]);
        timeLabel = new JLabel(borrowerInfo[i++]);
        dateLabel = new JLabel(borrowerInfo[i++]);

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

        screen2BackBtn = new JButton("Go Back");
        screen2CompleteBtn = new JButton("Complete All");
        screen2ConfirmBtn = new JButton("Confirm");

        screen2BackBtn.setPreferredSize(new Dimension(150, 30));
        screen2BackBtn.setBackground(branding.maroon);
        screen2BackBtn.setForeground(branding.white);
        screen2BackBtn.addActionListener(this);

        screen2CompleteBtn.setPreferredSize(new Dimension(150, 30));
        screen2CompleteBtn.setBackground(branding.maroon);
        screen2CompleteBtn.setForeground(branding.white);
        screen2CompleteBtn.addActionListener(this);

        screen2ConfirmBtn.setPreferredSize(new Dimension(150, 30));
        screen2ConfirmBtn.setBackground(branding.maroon);
        screen2ConfirmBtn.setForeground(branding.white);
        screen2ConfirmBtn.addActionListener(this);

        scrn2MenuPanel.add(screen2BackBtn);
        scrn2MenuPanel.add(screen2CompleteBtn);
        scrn2MenuPanel.add(screen2ConfirmBtn);

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

    public void refreshEntries2(String[] entries, String[][] items, Queries queries) {
        scrn2BorrowedItemsContentPanel.removeAll();

        int i = 0;
        borrowerNameLabel.setText(entries[i++]);
        studentIdLabel.setText(entries[i++]);
        timeLabel.setText(entries[i++]);
        dateLabel.setText(entries[i++]);

        for (String[] tuple: items){
            int j = 0;

            JLabel quantityLabel = new JLabel(tuple[j++] + "x");
            JLabel itemLabel = new JLabel(tuple[j++]);
            JLabel unitLabel = new JLabel();
            if(tuple[j] != null) {
                unitLabel.setText(tuple[j++]);
            } else {
                unitLabel.setText("No unit");
            }

            quantityLabel.setForeground(branding.maroon);
            itemLabel.setForeground(branding.maroon);
            unitLabel.setForeground(branding.maroon);

            JButton returnItemBtn = new JButton("Return");
            returnItemBtn.setPreferredSize(new Dimension(100, 35));
            returnItemBtn.setBackground(branding.maroon);
            returnItemBtn.setForeground(branding.white);
            returnItemBtn.addActionListener(this);
            
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

            returnItemBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(returnItemBtn.isEnabled()) {
                        System.out.println("Borrow ID: " + tuple[4]);
                        queries.updateActualReturnDate(Integer.parseInt(tuple[4]), entries[1]);
                        refreshEntries1(queries.getBorrowList(), queries);
                        returnItemBtn.setEnabled(false);
                    }
                }
            });

            scrn2BorrowedItemsContentPanel.add(tupleInfoPanel);
            scrn2BorrowedItemsContentPanel.add(Box.createVerticalStrut(10));
        }

        scrn2BorrowedItemsContentPanel.revalidate();
        scrn2BorrowedItemsContentPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == screen2BackBtn){
            cardLayout.previous(GUIBorrowerListPanel.this);         
        } else if (src == screen2CompleteBtn) {
            
        } else if (src == screen2ConfirmBtn) {
            
        }
    }
    
}
