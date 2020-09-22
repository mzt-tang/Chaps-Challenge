package Application;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ChapsChallenge frame = new ChapsChallenge();
            frame.setVisible(true);
        });
    }
}
