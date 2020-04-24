import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setSize(screenSize.width, screenSize.height);
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setUndecorated(true);
        obj.setTitle("Frame");
        obj.setVisible(true);
        obj.setResizable(false);
        obj.add(gameplay);
    }
}
