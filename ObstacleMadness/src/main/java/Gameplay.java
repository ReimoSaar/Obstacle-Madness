
import javax.swing.*;
import javax.swing.Timer;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private RectangleImage player;
    Timer timer = new Timer(13, this);
    public boolean isAlive = true;
    private int health = 100;
    private Image dblImage;
    private Graphics dblGraphics;
    private RectangleImage hud = new RectangleImage(getImage("hud.png"), 0, 980);
    ArrayList<RectangleImage> monster = new ArrayList<RectangleImage>();
    java.util.Timer spawnTimer;
    private int xVel;
    private int yVel;
    public JButton restart;

    private int FPS = 60;
    private int averageFPS;
    long startTime;
    long URDTimeMillis;
    long waitTime;
    long totalTime = 0;
    int frameCount = 0;
    int maxFrameCount = 60;
    long targetTime = 1000 / FPS;

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paint(Graphics g) {
        dblImage = createImage(getWidth(), getHeight());
        dblGraphics = dblImage.getGraphics();
        paintComponent(dblGraphics);
        g.drawImage(dblImage, 0, 0, this);
        String hitCounterString = "" + health;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
        g.drawString("HEALTH:" + hitCounterString, 876, 1043);
        g.drawString("FPS:" + averageFPS, 100, 100);
        if (health == 0) {
            g.setColor(Color.RED);
            g.fillRect(1600, 1005, 200, 50);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
            g.drawString("RESTART", 1620, 1043);
        }
    }

    public void paintComponent(Graphics g) {
        startTime = System.nanoTime();
        URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
        waitTime = targetTime - URDTimeMillis;
        try {
            Thread.sleep(waitTime);
        } catch (Exception e) {

        }
        totalTime += System.nanoTime() - startTime;
        frameCount++;
        if (frameCount == maxFrameCount) {
            averageFPS = (int) Math.ceil(1000.0 / ((totalTime / frameCount) / 1000000));
            frameCount = 0;
            totalTime = 0;
        }

        if (player == null) {
            restart = new JButton();
            restart.setName("RESTART");
            restart.setBounds(1600, 1005, 200, 50);
            restart.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    health = 100;
                    isAlive = true;
                    monster.clear();
                }
            });
            player = new RectangleImage(getImage("player.png"), getRandomX(), getRandomY());
            spawnTimer = new java.util.Timer();
            spawnTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isAlive) {
                        monster.add(new RectangleImage(getImage("monster" + getRandomImage() + ".png"), getRandomX(), getRandomY(), getRandomXVelocity(), getRandomYVelocity()));
                    }
                }
            }, 5000 * maxFrameCount / FPS, 2000 * maxFrameCount / FPS);
        }
        final Graphics2D g2 = (Graphics2D) g;
        player.draw(g2, this);
        hud.draw(g2, this);

        if (health == 0) {
            this.add(restart);
        }
        for (RectangleImage monster : monster) {
            monster.draw(g2, this);
            if (player.getRectangle().intersects(monster.getRectangle())) {
                health--;
            }
        }
        if (health <= 0) {
            health = 0;
            isAlive = false;
        }
        timer.start();
    }

    public Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Gameplay.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            System.out.println("An error has occured -" + e.getMessage());
        }
        return tempImage;
    }

    public int getRandomImage() {
        Random rand = new Random();
        int max = 2;
        int randomNum = rand.nextInt((max - 1) + 1) + 1;
        return randomNum;
    }

    private int getRandomXVelocity() {
        Random random = new Random();
        int randomXVel = random.nextInt(15);
        return randomXVel;
    }

    private int getRandomYVelocity() {
        Random random = new Random();
        int randomYVel = random.nextInt(15);
        return randomYVel;
    }

    private int getRandomX() {
        Random random = new Random();
        int randomX = random.nextInt(1820);
        return randomX;
    }

    private int getRandomY() {
        Random random = new Random();
        int randomY = random.nextInt(880);
        return randomY;
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN) {
            down();
        }
        if (keyCode == KeyEvent.VK_UP) {
            up();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            right();
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            left();
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {
        xVel = 0;
        yVel = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAlive) {
            player.getRectangle().x += xVel;
            player.getRectangle().y += yVel;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (player.getRectangle().x <= 0 || player.getRectangle().x >= screenSize.width - player.getRectangle().getWidth()) {
                xVel = 0;
            }
            if (player.getRectangle().y <= 0 || player.getRectangle().y >= screenSize.height - player.getRectangle().getHeight() - hud.getRectangle().getHeight()) {
                yVel = 0;
            }
            for (RectangleImage monster : monster) {
                monster.getRectangle().x += monster.xVel;
                monster.getRectangle().y += monster.yVel;
                if (monster.getRectangle().y >= 880) {
                    monster.yVel = -monster.yVel;
                }
                if (monster.getRectangle().y <= 0) {
                    monster.yVel = -monster.yVel;
                }
                if (monster.getRectangle().x >= 1820) {
                    monster.xVel = -monster.xVel;
                }
                if (monster.getRectangle().x <= 0) {
                    monster.xVel = -monster.xVel;
                }
            }
        }
        repaint();
    }

    private void up() {
        if (player.getRectangle().y > 0 && isAlive) {
            yVel = -15;
            xVel = 0;
        }
    }

    private void down() {
        if (player.getRectangle().y <= 880 && isAlive)
            yVel = 15;
        xVel = 0;
    }

    private void left() {
        if (player.getRectangle().x > 0 && isAlive) {
            xVel = -15;
            yVel = 0;
        }
    }

    private void right() {
        if (player.getRectangle().x < 1820 && isAlive) {
            xVel = 15;
            yVel = 0;
        }
    }

    private void rightUp() {
        if (player.getRectangle().x < 1907 || player.getRectangle().y > 0 && isAlive) {
            player.getRectangle().x += 20;
            player.getRectangle().y += 20;
        }
    }
}
