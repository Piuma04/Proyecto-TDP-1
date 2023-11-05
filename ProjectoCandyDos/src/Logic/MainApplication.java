package Logic;

import java.awt.EventQueue;

public class MainApplication {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Game();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
