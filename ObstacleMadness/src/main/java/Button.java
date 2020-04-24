import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class Button extends JButton {

    private Image image;
    private int x;
    private int y;
    private JButton jButton;

    public Button(Image image, int x, int y) {
        this.image = image;
        ImageIcon icon = new ImageIcon(image);
        this.x = x;
        this.y = y;
        this.jButton = new JButton();
        this.jButton.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
    }

    public void addButton(Graphics2D g2, ActionListener imageObserver, MouseAdapter mouseAdapter) {
        g2.drawImage(this.image, this.jButton.getX(), this.jButton.getY(), this.jButton.getWidth(), this.jButton.getHeight(), (ImageObserver) imageObserver);
    }

}
