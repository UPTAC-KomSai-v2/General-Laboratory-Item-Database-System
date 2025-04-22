import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.UIManager;

public class Branding {
    BufferedImage upLogo, lgnUPLogoResized, rbbnUPLogoResized;
    Color maroon, lightgray, white, lightergray; 
    Font sizedFontPalatinoBig, sizedFontPalatinoSmall;


    public Branding(){
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
}
