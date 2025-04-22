
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUIBorrowerListPanel extends JPanel {
    public GUIBorrowerListPanel(Branding branding, JButton blstBackBtn ){
        this.setLayout(new GridBagLayout());
        this.setBackground(branding.lightgray);
        this.setPreferredSize(new Dimension(900, 500));
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, branding.maroon));
        blstBackBtn.setBackground(branding.lightergray);
        blstBackBtn.setForeground(branding.maroon);
        this.add(blstBackBtn);
    }
    
}
