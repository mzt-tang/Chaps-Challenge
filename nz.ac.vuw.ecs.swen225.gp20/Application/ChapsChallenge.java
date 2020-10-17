package Application;

import Maze.Board;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Actors.stalker_enemy.StalkerEnemy;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.Game;
import Maze.Position;
import RecordAndReplay.RecordAndReplay;
import Renderer.Renderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import jdk.jfr.Event;

/**
 * Window of the actual game, Chap's Challenge
 *
 * @author Iqbal
 */
public class ChapsChallenge extends JFrame {

    //Panels
    private JPanel gameplayPanel;
    private JPanel infoPanel;

    private Game game;

    //Timer fields
    Timer timer;
    private int timeRemaining = 10;

    private RecordAndReplay recordAndReplayer;

    /**
     * Game instance
     */
    public ChapsChallenge(){
        initUI();

        /////// TEST CODE
        StalkerEnemy enemy = new StalkerEnemy(new Position(10, 10));
        Set<AbstractActor> test = new HashSet<>();
        test.add(enemy);
        //////

        game = new Game(new Board(Renderer.level1()), new Player(new Position(4, 4)), test); //FIXME: placeholder replace later


        recordAndReplayer = new RecordAndReplay();

        JPanel basePanel = new JPanel();
        basePanel.setBackground(Color.BLACK);

        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.X_AXIS));

        int verticalGap = 85;
        int horizontalGap = 65;
        basePanel.setBorder(new EmptyBorder(new Insets(verticalGap, horizontalGap, verticalGap, horizontalGap)));

        //PANELS
        // Gameplay panel
        gameplayPanel = createGamePanel(new Renderer(game));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                requestFocus();
                gameplayPanel.requestFocus();
            }
        });
        basePanel.add(gameplayPanel);
        basePanel.add(Box.createRigidArea(new Dimension(50, 0))); // Small gap between game and info panel

        // Info panel
        infoPanel = createInfoPanel();
        basePanel.add(infoPanel);

        add(basePanel);

        // More window properties
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Initializes window properties
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        createMenuBar();
        //test commit

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

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener((e) -> recordAndReplayer.saveGameplay());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((e) -> System.exit(0));

        //adding menu selections to the menu
        gameMenu.add(restartItem);
        gameMenu.add(saveItem);
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
        gamePanel.setBackground(Color.DARK_GRAY);
        gamePanel.add(renderer);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();

        //KeyListeners
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W:
                        System.out.println("Up");
                        movementRecordHelper(Game.DIRECTION.UP);
                        game.movePlayer(Game.DIRECTION.UP);
                        break;
                    case KeyEvent.VK_A:
                        System.out.println("Left");
                        movementRecordHelper(Game.DIRECTION.LEFT);
                        game.movePlayer(Game.DIRECTION.LEFT);
                        break;
                    case KeyEvent.VK_S:
                        System.out.println("Down");
                        movementRecordHelper(Game.DIRECTION.DOWN);
                        game.movePlayer(Game.DIRECTION.DOWN);
                        break;
                    case KeyEvent.VK_D:
                        System.out.println("Right");
                        movementRecordHelper(Game.DIRECTION.RIGHT);
                        game.movePlayer(Game.DIRECTION.RIGHT);
                        break;
                    default:
                        //if player isn't moving add a println here
//                        System.out.println("Key Pressed");
                        break;
                }
                recordAndReplayer.storeRecorderBuffer();
                renderer.revalidate();
                renderer.repaint();
            }
        });

        return gamePanel;
    }

    /**
     * Game information such as timer, chips remaining and player inventory is displayed here.
     * @return Info panel
     */
    public JPanel createInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        int fontSize = 16;

        //level
        JLabel levelLabel = new JLabel("LEVEL X");
        levelLabel.setFont(new Font(levelLabel.getName(), Font.PLAIN, fontSize));
        levelLabel.setForeground(Color.RED);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //time remaining
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font(timeLabel.getName(), Font.PLAIN, fontSize));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("TIME REMAINING: \n" + timeRemaining);
                if (timeRemaining == 0) {
                    timer.stop();
                    gameOver();
                }
                timeRemaining--;
            }
        });
        timer.start();
        timeLabel.setForeground(Color.RED);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //chips remaining
        JLabel chipsLabel = new JLabel("CHIPS REMAINING: ");
        chipsLabel.setFont(new Font(chipsLabel.getName(), Font.PLAIN, fontSize));
        chipsLabel.setForeground(Color.RED);
        chipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //game.treasuresLEFT


        //TODO: inventory view
        //player.getkeys

        //info panel
        int INFO_WIDTH = 240;
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 150)));
        infoPanel.add(levelLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 100)));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 100)));
        infoPanel.add(chipsLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 150)));


        return infoPanel;
    }

    //other

    /**
     *
     */
    public void gameOver(){
        JOptionPane.showMessageDialog(null, "You ran out of time!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }


    //getters

    /**
     * Getter for game.
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter for gameplay panel
     * @return gameplayPanel
     */
    public JPanel getGameplayPanel() {
        return gameplayPanel;
    }

    /**
     * Getter for info panel
     * @return infoPanel
     */
    public JPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * Activated whenever a player moves in a direction.
     * Also helps check tiles they are about to move into in case of anything
     * being on said tile.
     */
    public void movementRecordHelper(Game.DIRECTION direction) {
        recordAndReplayer.capturePlayerMove(direction);
        Position newPos = new Position(game.getPlayer().getPos(), direction);
        recordAndReplayer.captureTileInteraction(game.getBoard().getMap()[newPos.getX()][newPos.getY()]);

    }
}
