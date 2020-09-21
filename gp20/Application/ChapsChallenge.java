package Application;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

    /**
     * Game instance
     */
    public ChapsChallenge(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        createMenuBar();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Gameplay of the game is held here.
     * @return Gameplay panel
     */
    public JPanel createGameScreen(){
        JPanel boardView = new JPanel();

        return boardView;
    }

    /**
     * Menu bar with options to change the game state
     */
    public void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        //selections
        JMenuItem restartItem = new JMenuItem("Restart");
        //restartItem.addActionListener((e) -> System.exit(0)); //TODO: add functionality

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((e) -> System.exit(0));


        //adding it to the menus
        gameMenu.add(restartItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }

}
