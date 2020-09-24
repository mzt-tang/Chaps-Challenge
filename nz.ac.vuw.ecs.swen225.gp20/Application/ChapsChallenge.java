package Application;

import Maze.Board;
import Maze.BoardObjects.Actors.Player;
import Maze.Game;
import Maze.Position;
import Renderer.Renderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

    private Game game;

    /**
     * Game instance
     */
    public ChapsChallenge(){
        initUI();

        JPanel basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.X_AXIS));

        JPanel gameplay = createGamePanel(new Renderer());
        basePanel.add(gameplay);

        JPanel info = createInfoPanel();
        basePanel.add(info);

        add(basePanel);

        game = new Game(new Board(Renderer.aRandomBoard()), new Player(new Position(4, 4)), null); //FIXME: later

        this.setVisible(true);
    }

    /**
     * Initializes window properties
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        createMenuBar();

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
    public JPanel createGamePanel(Renderer renderer){
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.add(renderer);


        int verticalGap = 85;
        int horizontalGap = 65;
        gamePanel.setBorder(new EmptyBorder(new Insets(verticalGap, horizontalGap, verticalGap, 50)));

        return gamePanel;
    }

    /**
     * Game information such as timer, chips remaining and player inventory is displayed here.
     * @return Info panel
     */
    public JPanel createInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.BLACK);

        //level
        JLabel levelLabel = new JLabel("Level X: placeholder");
        levelLabel.setForeground(Color.RED);

        //time remaining
        JLabel timeLabel = new JLabel("Time Remaining: ");
        timeLabel.setForeground(Color.RED);

        //chips remaining
        JLabel chipsLabel = new JLabel("Chips Remaining: ");
        chipsLabel.setForeground(Color.RED);


        //inventory view


        infoPanel.add(levelLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(chipsLabel);

        return infoPanel;
    }

    /**
     * Getter for game.
     * @return game
     */
    public Game getGame() {
        return game;
    }
}
