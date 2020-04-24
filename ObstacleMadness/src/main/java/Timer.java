import java.util.TimerTask;

public class Timer {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i <= 60; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }
    }
}
