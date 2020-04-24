

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.TimerTask;

public class RectangleImage {
    private Image image;
    private Rectangle rectangle;
    public int xVel;
    public int yVel;
    private int x;
    private int y;
    private Gameplay gameplay;


    public RectangleImage(Image image, int x, int y) {
        this.image = image;
        ImageIcon icon = new ImageIcon(image);
        this.x = x;
        this.y = y;
        this.rectangle = new Rectangle(x, y, icon.getIconWidth(), icon.getIconHeight());
    }

    public RectangleImage(Image image, int x, int y, int xVel, int yVel) {
        this(image, x, y);
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public int getXVelocity() {
        return this.xVel;
    }
    public int getYVelocity() {
        return this.yVel;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return  this.y;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public Image getImage() {
        return this.image;
    }

    public void move(int x, int y) {
        this.rectangle.setBounds(x, y, rectangle.width, rectangle.height);
    }

    public void draw(Graphics2D g2, ActionListener imageObserver) {
            g2.drawImage(this.image, this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height, (ImageObserver) imageObserver);
    }

    public boolean intersects(Rectangle rectangle) {
        return this.rectangle.intersects(rectangle);
    }

    public Rectangle intersection(Rectangle rectangle) {
        return this.rectangle.intersection(rectangle);
    }

}
