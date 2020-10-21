package Application;

import Maze.Board;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.PatternEnemy;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Actors.stalker_enemy.StalkerEnemy;
import Maze.BoardObjects.Tiles.Key;
import Maze.Game;
import Maze.Position;
import Persistence.Persistence;
import Persistence.Level;
import RecordAndReplay.RecordAndReplay;
import Renderer.Renderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

/**
 * Window of the actual game, Chap's Challenge
 *
 * @author Iqbal
 */
public class ChapsChallenge extends JFrame {

    //Panels
    private JPanel gameplayPanel;
    private JPanel infoPanel;
    public static final int INFO_WIDTH = 240;

    //Game
    private Game game;
    private boolean isPaused = false;

    //Informating stored for info panel
    private Timer timer;
    private int timeRemaining;
    private InventoryView inventoryView;


    private RecordAndReplay recordAndReplayer;

    /**
     * Game instance
     */
    public ChapsChallenge(){
        initUI();

        /////// TEST CODE
        Set<AbstractActor> test = new HashSet<>();

        StalkerEnemy enemy = new StalkerEnemy(new Position(10, 10), 1);
        test.add(enemy);

        PatternEnemy enemy1 = new PatternEnemy(new Position(2, 9), 1, "dddsssaaawww");
        test.add(enemy1);
        //////

        //Persistence and Levels
        Persistence persistence = new Persistence();
        Level currentLevel =  persistence.getLevel(1);
        timeRemaining = currentLevel.getTime();

        game = new Game(new Board(currentLevel.getTileArray()), new Player(currentLevel.getPlayerPos()), new HashSet<>()); //FIXME: placeholder replace later
        inventoryView = new InventoryView(game.getPlayer());

        //Record & Replay
        recordAndReplayer = new RecordAndReplay();

        //GUI
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

        //Star background on own thread
        Runnable clockThread = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000/30); //30FPS
                        renderer.revalidate();
                        renderer.repaint();
                        inventoryView.revalidate();
                        inventoryView.repaint();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(clockThread).start();

        //KeyListeners
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //up
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("Up");
                    movementRecordHelper(Game.DIRECTION.UP);
                    game.movePlayer(Game.DIRECTION.UP);
                }
                //left
                else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Left");
                    movementRecordHelper(Game.DIRECTION.LEFT);
                    game.movePlayer(Game.DIRECTION.LEFT);
                }
                //down
                else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
                    System.out.println("Down");
                    movementRecordHelper(Game.DIRECTION.DOWN);
                    game.movePlayer(Game.DIRECTION.DOWN);
                }
                //right
                else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
                    System.out.println("Right");
                    movementRecordHelper(Game.DIRECTION.RIGHT);
                    game.movePlayer(Game.DIRECTION.RIGHT);
                }
                else {
                    //dead code
                }
                nextLevel(); //check if the player is on the vent
                //recordAndReplayer.storeRecorderBuffer();
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

        //Current level label
        JLabel levelLabel = new JLabel("LEVEL X");
        levelLabel.setFont(new Font(levelLabel.getName(), Font.PLAIN, fontSize));
        levelLabel.setForeground(Color.RED);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Timer thread
        JLabel timeLabel = new JLabel();
        JLabel chipsLabel = new JLabel();
        JLabel inventoryLabel = new JLabel("INVENTORY");
        inventoryLabel.setFont(new Font(timeLabel.getName(), Font.PLAIN, fontSize));
        inventoryLabel.setForeground(Color.RED);
        inventoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Time remaining
                timeLabel.setFont(new Font(timeLabel.getName(), Font.PLAIN, fontSize));
                timeLabel.setText("TIME REMAINING: \n" + timeRemaining);
                timeLabel.setForeground(Color.RED);
                timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Chips Remaining
                chipsLabel.setFont(new Font(chipsLabel.getName(), Font.PLAIN, fontSize));
                chipsLabel.setText("CHIPS REMAINING: " + game.treasuresLeft());
                chipsLabel.setForeground(Color.RED);
//                if (game.treasuresLeft() == 0){
//                    chipsLabel.setForeground(Color.GREEN);
//                }
                chipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Stopping the timer once it runs out of time
                if (timeRemaining == 0) {
                    timer.stop();
                    outOfTime();
                }
                timeRemaining--;
            }
        });
        timer.start();



        //info panel
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 100)));
        infoPanel.add(levelLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 66)));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 66)));
        infoPanel.add(chipsLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 66)));
        infoPanel.add(inventoryLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 10)));
        infoPanel.add(inventoryView);
        infoPanel.add(Box.createRigidArea(new Dimension(INFO_WIDTH, 55)));

        return infoPanel;
    }


    // ===========================================
    // Controlling Game Status
    // ===========================================

    /**
     * Ends the game when the game clock runs out of time.
     */
    public void outOfTime(){
        JOptionPane.showMessageDialog(null, "You ran out of time!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public void nextLevel(){
        if (game.isLevelCompleted()) {
            int options = JOptionPane.showConfirmDialog(null, "Continue to next level?", "Level 1 Completed!",
                    JOptionPane.YES_NO_OPTION);
            if (options == 0) {
                System.out.println("Level 2 called...");
            } else {
                System.exit(0);
            }
        }
    }

    public void pauseGame(){
        if (!isPaused) {
            isPaused = true;
        }
    }

    public void resumeGame(){
        if (isPaused) {
            isPaused = false;
        }
    }

    // ===========================================
    // Getters
    // ===========================================

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
