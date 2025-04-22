import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUIBorrowItemPanel extends JPanel {
    public GUIBorrowItemPanel(Branding branding, JButton bitmBackBtn ){
        this.setLayout(new GridBagLayout());
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));
        bitmBackBtn.setBackground(branding.lightergray);
        bitmBackBtn.setForeground(branding.maroon);
        this.add(bitmBackBtn);
    }
    
}
