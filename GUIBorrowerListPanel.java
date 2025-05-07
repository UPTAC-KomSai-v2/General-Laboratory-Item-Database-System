import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUIBorrowerListPanel extends JPanel implements ActionListener{
    private CardLayout cardLayout;
    private Branding branding;
    private JButton screen2BackBtn, screen2ReturnAllBtn, screen2ConfirmBtn;
    private JPanel screen1, screen2, scrn1BorrowerListContentPanel, scrn2BorrowedItemsContentPanel;
    private JLabel borrowerLabel, borrowerNameLabel, studentIdLabel, dateBorrowedLabel, expectedDateLabel;

    private List<JButton> returnButtons = new ArrayList<>();
    private List<String> borrowIds = new ArrayList<>();
    private List<String> borrowerIds = new ArrayList<>();
    private List<String> selectedBorrowerIds = new ArrayList<>();
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
        BorderFactory.createEmptyBorder(10, 0, 0, 0)));

        screen1.setBackground(branding.lightgray);

        scrn1BorrowerListContentPanel = new JPanel();
        scrn1BorrowerListContentPanel.setLayout(new BoxLayout(scrn1BorrowerListContentPanel, BoxLayout.Y_AXIS));
        scrn1BorrowerListContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

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

        JPanel scrn1BorrowerListHeaderPanel = new JPanel(new GridBagLayout());
        scrn1BorrowerListHeaderPanel.setOpaque(false);
        JLabel borrowPanelHeaderLabel = new JLabel("Borrower List");
        borrowPanelHeaderLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        borrowPanelHeaderLabel.setForeground(branding.maroon);
        scrn1BorrowerListHeaderPanel.add(borrowPanelHeaderLabel);

        JPanel scrn1tupleHeaderPanel = new JPanel(new GridBagLayout());
        scrn1tupleHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10,50,10,10));
        scrn1tupleHeaderPanel.setBackground(branding.lightgray);
        
        JLabel tupleNameLabel = new JLabel("Borrower Name");
        JLabel tupleStudentIdLabel = new JLabel("Student ID");
        JLabel tupleBorrowDateLabel = new JLabel("Borrow Date");
        JLabel tupleExpectedReturnLabel = new JLabel("Expected Return Date");
        tupleNameLabel.setForeground(branding.maroon);
        tupleStudentIdLabel.setForeground(branding.maroon);
        tupleBorrowDateLabel.setForeground(branding.maroon);
        tupleExpectedReturnLabel.setForeground(branding.maroon);

        GridBagConstraints scrn1tupleHeaderPanelGBC = new GridBagConstraints();
        scrn1tupleHeaderPanelGBC.fill = GridBagConstraints.BOTH;
        scrn1tupleHeaderPanelGBC.anchor = GridBagConstraints.WEST;
        scrn1tupleHeaderPanelGBC.weightx = 0.065;
        scrn1tupleHeaderPanelGBC.gridx = 0;
        scrn1tupleHeaderPanel.add(tupleNameLabel,scrn1tupleHeaderPanelGBC);
        scrn1tupleHeaderPanelGBC.weightx = 0.030;
        scrn1tupleHeaderPanelGBC.gridx++;
        scrn1tupleHeaderPanel.add(tupleStudentIdLabel,scrn1tupleHeaderPanelGBC);
        scrn1tupleHeaderPanelGBC.weightx = 0.08;
        scrn1tupleHeaderPanelGBC.gridx++;
        scrn1tupleHeaderPanel.add(tupleBorrowDateLabel,scrn1tupleHeaderPanelGBC);
        scrn1tupleHeaderPanelGBC.weightx = 0.13;
        scrn1tupleHeaderPanelGBC.gridx++;
        scrn1tupleHeaderPanel.add(tupleExpectedReturnLabel,scrn1tupleHeaderPanelGBC);        


        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.fill = GridBagConstraints.BOTH;
        screen1GBC.weightx = 1;
        screen1GBC.weighty = 0.03;
        screen1GBC.gridy = 0;
        screen1.add(scrn1BorrowerListHeaderPanel, screen1GBC);
        screen1GBC.weighty = 0.01;
        screen1GBC.gridy++;
        screen1.add(scrn1tupleHeaderPanel, screen1GBC);
        screen1GBC.weighty = 0.9;
        screen1GBC.gridy++;
        screen1.add(scrn1ScrollContentPanel, screen1GBC);
        screen1GBC.weighty = 0.01;
        screen1GBC.gridy++;
        screen1.add(scrn1MenuPanel, screen1GBC);
    }

    

    public void refreshEntries1(String[][] entries, Queries queries) {
        scrn1BorrowerListContentPanel.removeAll();
    
        for (String[] tuple : entries) {
            int i = 0;
            
            JLabel nameLabel = new JLabel(tuple[i++]);
            JLabel studentIdLabel = new JLabel(tuple[i++]);
            JLabel dateBorrowedLabel = new JLabel(branding.reformatDateLabel(tuple[i++]));
            JLabel expectedDateLabel = new JLabel(branding.reformatDateLabel(tuple[i++]));
            JButton viewButton = new JButton("View");
            viewButton.setPreferredSize(new Dimension(70, 30));

            viewButton.addActionListener(e ->{
                        cardLayout.next(GUIBorrowerListPanel.this);
                        borrowerInfo = new String[]{tuple[0],tuple[1],tuple[4],tuple[5] + " - " + tuple[6]};
                        refreshEntries2(borrowerInfo, queries.getItemsBorrowed(tuple[1],tuple[2],tuple[3]), queries);
            });

            nameLabel.setForeground(branding.white);
            studentIdLabel.setForeground(branding.white);
            dateBorrowedLabel.setForeground(branding.white);
            expectedDateLabel.setForeground(branding.white);
            
            JPanel namePanel = new JPanel();
            JPanel studentIdPanel = new JPanel();
            JPanel timePanel = new JPanel();
            JPanel datePanel = new JPanel();
            JPanel viewButtonPanel = new JPanel();

            namePanel.setLayout(new BorderLayout());
            studentIdPanel.setLayout(new BorderLayout());
            timePanel.setLayout(new BorderLayout());
            datePanel.setLayout(new BorderLayout());
            viewButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,10));

            namePanel.setPreferredSize(new Dimension(60, 70));
            studentIdPanel.setPreferredSize(new Dimension(40, 70));
            timePanel.setPreferredSize(new Dimension(40, 70));
            datePanel.setPreferredSize(new Dimension(40, 70));
            viewButtonPanel.setPreferredSize(new Dimension(10, 50));
            
            namePanel.setOpaque(false);
            studentIdPanel.setOpaque(false);
            timePanel.setOpaque(false);
            datePanel.setOpaque(false);
            viewButtonPanel.setOpaque(false);

            namePanel.add(nameLabel, BorderLayout.WEST);
            studentIdPanel.add(studentIdLabel, BorderLayout.WEST);
            timePanel.add(dateBorrowedLabel, BorderLayout.WEST);
            datePanel.add(expectedDateLabel, BorderLayout.WEST);
            viewButtonPanel.add(viewButton);

            JPanel tupleInfoPanel = new JPanel();
            tupleInfoPanel.setMaximumSize(new Dimension(900, 70));
            tupleInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            tupleInfoPanel.setLayout(new GridBagLayout());
            tupleInfoPanel.setBackground(branding.maroon);
            
            GridBagConstraints tupleInfoPanelGBC = new GridBagConstraints();
            tupleInfoPanelGBC.fill = GridBagConstraints.HORIZONTAL;
            tupleInfoPanelGBC.gridx = 0;
            tupleInfoPanelGBC.ipadx = 125;
            tupleInfoPanelGBC.weightx = 0.1;
            tupleInfoPanel.add(namePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 70;
            tupleInfoPanelGBC.weightx = 0.05;
            tupleInfoPanel.add(studentIdPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.5;
            tupleInfoPanel.add(timePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(datePanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.01;
            tupleInfoPanel.add(viewButtonPanel, tupleInfoPanelGBC);

            tupleInfoPanel.addMouseListener(new MouseAdapter() {
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

        
        borrowerInfo = new String[]{" ", " ", " ", " "};

        JPanel scrn2BorrowerInfoPanel = new JPanel();
        scrn2BorrowerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 0));
        scrn2BorrowerInfoPanel.setLayout(new GridBagLayout());
        scrn2BorrowerInfoPanel.setOpaque(false);
        int i = 0;
        borrowerLabel = new JLabel("Borrower: ");
        borrowerNameLabel = new JLabel(borrowerInfo[i++]);
        studentIdLabel = new JLabel(borrowerInfo[i++]);
        dateBorrowedLabel = new JLabel(borrowerInfo[i++]);
        expectedDateLabel = new JLabel(borrowerInfo[i++]);

        borrowerLabel.setForeground(branding.white);
        borrowerNameLabel.setForeground(branding.white);
        studentIdLabel.setForeground(branding.white);
        dateBorrowedLabel.setForeground(branding.white);
        expectedDateLabel.setForeground(branding.white);

        borrowerLabel.setFont(branding.sizedFontRobotoBold15);
        borrowerNameLabel.setFont(branding.sizedFontRobotoBold15);
        studentIdLabel.setFont(branding.sizedFontRobotoBold15);
        dateBorrowedLabel.setFont(branding.sizedFontRobotoBold15);
        expectedDateLabel.setFont(branding.sizedFontRobotoBold15);

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
        timePanel.add(dateBorrowedLabel, BorderLayout.WEST);
        datePanel.add(expectedDateLabel, BorderLayout.WEST);

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
        screen2ReturnAllBtn = new JButton("Complete All");
        screen2ConfirmBtn = new JButton("Confirm");

        screen2BackBtn.setPreferredSize(new Dimension(150, 30));
        screen2BackBtn.setBackground(branding.maroon);
        screen2BackBtn.setForeground(branding.white);
        screen2BackBtn.addActionListener(this);

        screen2ReturnAllBtn.setPreferredSize(new Dimension(150, 30));
        screen2ReturnAllBtn.setBackground(branding.maroon);
        screen2ReturnAllBtn.setForeground(branding.white);

        screen2ConfirmBtn.setPreferredSize(new Dimension(150, 30));
        screen2ConfirmBtn.setBackground(branding.maroon);
        screen2ConfirmBtn.setForeground(branding.white);

        scrn2MenuPanel.add(screen2BackBtn);
        scrn2MenuPanel.add(screen2ReturnAllBtn);
        scrn2MenuPanel.add(screen2ConfirmBtn);

        JPanel scrn2TupleHeaderPanel = new JPanel(new GridBagLayout());
        scrn2TupleHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10,57,10,10));
        scrn2TupleHeaderPanel.setOpaque(false);
        JLabel scrn2quantityLabel = new JLabel("Quantity");
        JLabel scrn2ItemNameLabel = new JLabel("Item Name");
        JLabel scrn2UnitLabel = new JLabel("Unit");
        scrn2quantityLabel.setForeground(branding.white);
        scrn2ItemNameLabel.setForeground(branding.white);
        scrn2UnitLabel.setForeground(branding.white);
        GridBagConstraints scrn2TupleHeaderPanelGBC = new GridBagConstraints();
        scrn2TupleHeaderPanelGBC.fill = GridBagConstraints.HORIZONTAL;
        scrn2TupleHeaderPanelGBC.weightx = 0.03;
        scrn2TupleHeaderPanelGBC.gridx = 0;
        scrn2TupleHeaderPanel.add(scrn2quantityLabel, scrn2TupleHeaderPanelGBC);
        scrn2TupleHeaderPanelGBC.weightx = 0.190;
        scrn2TupleHeaderPanelGBC.gridx++;
        scrn2TupleHeaderPanel.add(scrn2ItemNameLabel, scrn2TupleHeaderPanelGBC);
        scrn2TupleHeaderPanelGBC.weightx = 0.6;
        scrn2TupleHeaderPanelGBC.gridx++;
        scrn2TupleHeaderPanel.add(scrn2UnitLabel, scrn2TupleHeaderPanelGBC);

        GridBagConstraints screen2GBC = new GridBagConstraints();
        screen2GBC.fill = GridBagConstraints.BOTH;
        screen2GBC.weightx = 1;
        screen2GBC.weighty = 0.01;
        screen2GBC.gridy = 0;
        screen2.add(scrn2BorrowerInfoPanel, screen2GBC);
        screen2GBC.weighty = 0.01;
        screen2GBC.gridy++;
        screen2.add(scrn2TupleHeaderPanel, screen2GBC);
        screen2GBC.weighty = 0.9;
        screen2GBC.gridy++;
        screen2.add(scrn2ScrollContentPanel, screen2GBC);
        screen2GBC.weighty = 0.01;
        screen2GBC.gridy++;
        screen2.add(scrn2MenuPanel, screen2GBC);
    }

    public void refreshEntries2(String[] entries, String[][] items, Queries queries) {
        scrn2BorrowedItemsContentPanel.removeAll();
        returnButtons.clear();
        borrowIds.clear();
        borrowerIds.clear();
        selectedBorrowerIds.clear();

        int i = 0;
        borrowerNameLabel.setText(entries[i++]);
        studentIdLabel.setText(entries[i++]);
        dateBorrowedLabel.setText(entries[i++]);
        expectedDateLabel.setText(entries[i++]);

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
            tupleInfoPanelGBC.weightx = 0.3;
            tupleInfoPanel.add(itemPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 20;
            tupleInfoPanelGBC.weightx = 0.3;
            tupleInfoPanel.add(unitPanel, tupleInfoPanelGBC);
            tupleInfoPanelGBC.fill = GridBagConstraints.NONE;
            tupleInfoPanelGBC.anchor = GridBagConstraints.EAST;
            tupleInfoPanelGBC.gridx++;
            tupleInfoPanelGBC.ipadx = 100;
            tupleInfoPanelGBC.weightx = 0.2;
            tupleInfoPanel.add(returnPanel, tupleInfoPanelGBC);

            returnItemBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(returnItemBtn.getBackground().equals(branding.maroon)) {
                        System.out.println("Borrow ID: " + tuple[4] + " --> Enabled");
                        returnItemBtn.setBackground(Color.YELLOW);
                        selectedBorrowerIds.add(tuple[4]);
                        printSelectedBorrowID(selectedBorrowerIds);
                    } else {
                        System.out.println("Borrow ID: " + tuple[4] + " --> Disabled");
                        returnItemBtn.setBackground(branding.maroon);
                        selectedBorrowerIds.remove(tuple[4]);
                        printSelectedBorrowID(selectedBorrowerIds);
                    }

                    boolean allSameColor = true;
                    Color firstColor = Color.YELLOW;

                    for (JButton btn : returnButtons) {
                        if (!btn.getBackground().equals(firstColor)) {
                            allSameColor = false;
                            break;
                        }
                    }
                    if (allSameColor) {
                        System.out.println("Returned All 1");
                        screen2ReturnAllBtn.setText("Undo Return All");
                        screen2ReturnAllBtn.setBackground(Color.YELLOW);
                    } else {
                        System.out.println("Undo Return All 1");
                        screen2ReturnAllBtn.setText("Return All");
                        screen2ReturnAllBtn.setBackground(branding.maroon);
                    }
                }
            });

            returnButtons.add(returnItemBtn);
            borrowIds.add(tuple[4]);
            borrowerIds.add(entries[1]);

            scrn2BorrowedItemsContentPanel.add(tupleInfoPanel);
            scrn2BorrowedItemsContentPanel.add(Box.createVerticalStrut(10));
        }

        screen2ReturnAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(screen2ReturnAllBtn.getBackground().equals(branding.maroon)) {
                    for (int k = 0; k < returnButtons.size(); k++) {
                        JButton btn = returnButtons.get(k);
                        String borrowId = borrowIds.get(k);
                        String borrowerId = borrowerIds.get(k);
                        if (btn.getBackground().equals(branding.maroon)) {
                            btn.setBackground(Color.YELLOW);
                            selectedBorrowerIds.add(borrowId);
                        }
                    }

                    System.out.println("Returned All 2");
                    screen2ReturnAllBtn.setText("Undo Return All");
                    screen2ReturnAllBtn.setBackground(Color.YELLOW);
                    printSelectedBorrowID(selectedBorrowerIds);
                } else {
                    for (int k = 0; k < returnButtons.size(); k++) {
                        JButton btn = returnButtons.get(k);
                        btn.setBackground(branding.maroon);
                    }

                    selectedBorrowerIds.clear();

                    System.out.println("Undo Return All 2");
                    screen2ReturnAllBtn.setText("Return All");
                    screen2ReturnAllBtn.setBackground(branding.maroon);
                    printSelectedBorrowID(selectedBorrowerIds);
                }
            }
        });

        screen2ConfirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!selectedBorrowerIds.isEmpty()) {
                    int result = JOptionPane.showConfirmDialog(
                        null, "Are you sure you want to return selected items?",
                        "Confirm Return",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        for (JButton btn : returnButtons) {
                            btn.setBackground(branding.maroon);
                        }
            
                        for (ActionListener al1 : screen2ReturnAllBtn.getActionListeners()) {
                            screen2ReturnAllBtn.removeActionListener(al1);
                        }
            
                        for (ActionListener al2 : screen2ConfirmBtn.getActionListeners()) {
                            screen2ConfirmBtn.removeActionListener(al2);
                        }
                        screen2ReturnAllBtn.setBackground(branding.maroon);
                        cardLayout.previous(GUIBorrowerListPanel.this);
                    } else {
                        JOptionPane.showMessageDialog(
                        null,
                        "Confirmation canceled.",
                        "Unsuccessful Confirmation",
                        JOptionPane.WARNING_MESSAGE
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                    null,
                    "No items selected.",
                    "No Selected Items",
                    JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        scrn2BorrowedItemsContentPanel.revalidate();
        scrn2BorrowedItemsContentPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == screen2BackBtn) {
            for (JButton btn : returnButtons) {
                btn.setBackground(branding.maroon);
            }

            for (ActionListener al1 : screen2ReturnAllBtn.getActionListeners()) {
                screen2ReturnAllBtn.removeActionListener(al1);
            }

            for (ActionListener al2 : screen2ConfirmBtn.getActionListeners()) {
                screen2ConfirmBtn.removeActionListener(al2);
            }

            selectedBorrowerIds.clear();
            screen2ReturnAllBtn.setText("Return All");
            screen2ReturnAllBtn.setBackground(branding.maroon);
            cardLayout.previous(GUIBorrowerListPanel.this);
        }
    }

    private void printSelectedBorrowID(List<String> borrowIDs) {
        System.out.print("Selected Borrow ID's: ");
        for(String str : borrowIDs) {
            System.out.print(str + " ");
        } System.out.println("");
    }
    
}
