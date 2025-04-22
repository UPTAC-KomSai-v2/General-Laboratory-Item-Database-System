
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUITransactionHistoryPanel extends JPanel {
    public GUITransactionHistoryPanel(Branding branding, JButton tranBackBtn ){
        this.setLayout(new GridBagLayout());
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));
        tranBackBtn.setBackground(branding.lightergray);
        tranBackBtn.setForeground(branding.maroon);
        this.add(tranBackBtn);
    }
    
}
