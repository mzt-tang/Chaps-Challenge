package Application;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Actual game
 *
 * @author Iqbal
 */
public class ChapsChallenge extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ChapsChallenge frame = new ChapsChallenge();
            frame.setVisible(true);
        });
    }

    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 750;

    public ChapsChallenge(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel createGameScreen(){
        JPanel boardView = new JPanel();

        return boardView;
    }
}
