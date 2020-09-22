package Application;

import Renderer.Renderer;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Window of the actual game, Chap's Challenge
 *
 * @author Iqbal
 */
public class ChapsChallenge extends JFrame {

    //window dimensions
    private final int WINDOW_WIDTH = 950;
    private final int WINDOW_HEIGHT = 750;

    //info panel
    private final int INFO_WIDTH = 105;
    private final int INFO_HEIGHT = 540;

    /**
     * Game instance
     */
    public ChapsChallenge(){
        initUI();

        createMenuBar();
        Renderer renderer = new Renderer();
        JPanel gameplay = createGameScreen(renderer);
        add(gameplay, BorderLayout.CENTER); //TODO: Position this properly. Currently not positioned as desired.
    }

    /**
     * Initializes window properties
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        //adding menu selections to the menu
        gameMenu.add(restartItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        setJMenuBar(menuBar);
    }

    // ===========================================
    // JPanels
    // ===========================================

    /**
     * Gameplay of the game is displayed here.
     * @return Gameplay panel
     */
    public JPanel createGameScreen(Renderer renderer){
        JPanel gameScreen = new JPanel();
        gameScreen.add(renderer);

        return gameScreen;
    }

    /**
     * Game information such as timer, chips remaining and player inventory is displayed here.
     * @return Info panel
     */
    public JPanel createInfoScreen(){
        return null;
    }

}
