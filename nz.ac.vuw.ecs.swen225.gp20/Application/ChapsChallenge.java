package Application;

import Maze.Board;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.Game;
import Maze.Position;
import Persistence.Persistence;
import Persistence.Level;
import RecordAndReplay.RecordAndReplay;
import Renderer.Renderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * Window of the actual game, Chap's Challenge.
 *
 * @author Iqbal
 */
public class ChapsChallenge extends JFrame {

    //Panels
    private JPanel gameplayPanel;
    private JPanel infoPanel;
    public static final int INFO_WIDTH = 240;

    //Recording JMenuItems
    private JMenuItem recordItem;
    private JMenuItem replayItem;

    //Game
    private Game game;
    private Level currentLevel;
    private int levelCount = 1;
    private final int maxLevel = 3;
    private volatile boolean isPaused = false;
    private volatile Thread paintThread;

    //Informating stored for info panel
    private volatile Timer timer;
    private int timerDelay = 1000;
    private int timeRemaining;
    private InventoryView inventoryView;

    //Other modules
    private Renderer renderer;
    private RecordAndReplay recordAndReplayer;
    private boolean replayModeActive = false; //sets the replay mode

    /**
     * Construct a game instance from a given level count.
     *
     * @param levelToPlay The level that first appears when the game is started
     */
    public ChapsChallenge(int levelToPlay){
        this.levelCount = levelToPlay;

        initUI();

        // Initialize modules
        initModules();

        // Initialize hotkeys
        addHotKeys();

        // More window properties
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Construct a game instance from a Level object.
     *
     @param lastSavedLevel Load in from a level (optional argument, use null if not used)
     */
    public ChapsChallenge(Level lastSavedLevel){
        this.currentLevel = lastSavedLevel;

        initUI();

        // Initialize modules
        initModules();

        // Initialize hotkeys
        addHotKeys();

        loadLevel(Persistence.loadGame(StartMenu.lastLevel), StartMenu.lastLevel);

        // More window properties
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Initializes the Maze, Renderer, Persistence & Levels, Record & Replay modules.
     */
    public void initModules(){
        // Persistence, Maze and Renderer module
        loadLevel(levelCount);

        // Record & Replay module
        recordAndReplayer = new RecordAndReplay(this);
    }

    /**
     * Initializes the gameplay panel and the info panel.
     */
    public void initPanels(){
        //GUI base panel
        JPanel basePanel = new JPanel();
        basePanel.setBackground(Color.BLACK);
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.X_AXIS));

        int verticalGap = 85;
        int horizontalGap = 65;
        basePanel.setBorder(new EmptyBorder(new Insets(verticalGap, horizontalGap, verticalGap, horizontalGap)));

        // Gameplay panel
        gameplayPanel = createGamePanel();
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
    }

    /**
     * Initializes window properties.
     */
    public void initUI(){
        setTitle("Chap's Challenge: Among Us Edition");
        createMenuBar();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * Menu bar with options to change the game state.
     */
    public void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu levelMenu = new JMenu("Select Level");

        //Game menu
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener((e) -> EventQueue.invokeLater(HelpScreen::new));

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener((e) -> {
            saveCurrentGame();
        });

        JMenuItem loadSaved = new JMenuItem("Load Saved Game");
        loadSaved.addActionListener((e) -> {
            //give the user a dropdown menu of which level to load their saved game from
            loadSavedGame();
        });

        JMenuItem pauseItem = new JMenuItem("Pause");
        pauseItem.addActionListener(e -> {
            if (!isPaused) pauseResume();
        });

        JMenuItem resumeItem = new JMenuItem("Resume");
        resumeItem.addActionListener(e -> {
            if (isPaused) pauseResume();
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((e) -> System.exit(0));

        //Select level
        JMenuItem level1 = new JMenuItem("Level 1");
        level1.addActionListener((e) -> loadLevel(1));

        JMenuItem level2 = new JMenuItem("Level 2");
        level2.addActionListener((e) -> loadLevel(2));

        JMenuItem level3 = new JMenuItem("Level 3");
        level3.addActionListener((e) -> loadLevel(3));

        //adding menu selections to the menu
        gameMenu.add(helpItem);
        gameMenu.add(saveItem);
        gameMenu.add(loadSaved);
        gameMenu.add(pauseItem);
        gameMenu.add(resumeItem);
        gameMenu.add(exitItem);

        levelMenu.add(level1);
        levelMenu.add(level2);
        levelMenu.add(level3);

        menuBar.add(gameMenu);
        menuBar.add(levelMenu);

        //Record & Replay Menu
        JMenu recordAndReplay = new JMenu("Record And Replay");

        //R&R menu selections
        recordItem = new JMenuItem("Start Recording");
        recordItem.addActionListener((e) -> recordTrigger());
        replayItem = new JMenuItem("Replay");
        replayItem.addActionListener((e) -> replayTrigger());

        //Add selections to RecordAndReplay Menu.
        recordAndReplay.add(recordItem);
        recordAndReplay.add(replayItem);

        menuBar.add(recordAndReplay);

        setJMenuBar(menuBar);
    }



    // ===========================================
    // JPanels
    // ===========================================

    /**
     * Gameplay of the game is displayed here.
     * @return Gameplay panel
     */
    public JPanel createGamePanel(){
        JPanel gamePanel = new JPanel();
        gamePanel.setBackground(Color.DARK_GRAY);
        gamePanel.add(renderer);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();

        //Star background on own thread
        paintThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!isPaused) {
                        try {
                            Thread.sleep(1000 / 30); //30FPS
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(!replayModeActive) {
                            recordAndReplayer.captureEnemyPreMoves(game.getComputerPlayers());
                            game.moveEnemies();
                            recordAndReplayer.captureEnemyPostMoves(game.getComputerPlayers());
                        }
                        repaint();
                    }
                    if(!replayModeActive) recordAndReplayer.clearRecorderBuffer(timeRemaining);
                }
            }
        });
        paintThread.start();

        //KeyListeners
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!isPaused && !replayModeActive) {
                    if (!e.isControlDown()) {
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
                        else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
                            System.out.println("Down");
                            movementRecordHelper(Game.DIRECTION.DOWN);
                            game.movePlayer(Game.DIRECTION.DOWN);
                        }
                        //right
                        else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            System.out.println("Right");
                            movementRecordHelper(Game.DIRECTION.RIGHT);
                            game.movePlayer(Game.DIRECTION.RIGHT);
                        } else {
                            //dead code
                        }
                        nextLevel(); //check if the player is on the vent
                        recordAndReplayer.clearRecorderBuffer(timeRemaining);
                    }
                }
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
        infoPanel.setBackground(Color.DARK_GRAY);

        int fontSize = 18;

        //Current level label
        JLabel levelLabel = new JLabel("LEVEL " + levelCount);
        levelLabel.setFont(new Font("VCR OSD Mono", Font.BOLD, fontSize));
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Timer thread
        JLabel timeLabel = new JLabel();
        JLabel chipsLabel = new JLabel();
        JLabel inventoryLabel = new JLabel("INVENTORY");
        inventoryLabel.setFont(new Font("VCR OSD Mono", Font.BOLD, fontSize));
        inventoryLabel.setForeground(Color.WHITE);
        inventoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timerDelay = 1000;
        timer = new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Time remaining
                timeLabel.setFont(new Font("VCR OSD Mono", Font.BOLD, fontSize));
                timeLabel.setText("TIME REMAINING: \n" + timeRemaining);
                //set it to red when it's half-time
                if (timeRemaining <= Persistence.getLevel(levelCount).getTime()/2) {
                    timeLabel.setForeground(Color.RED);
                }
                else {
                    timeLabel.setForeground(Color.WHITE);
                }
                timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Chips Remaining
                chipsLabel.setFont(new Font("VCR OSD Mono", Font.BOLD, fontSize));
                chipsLabel.setText("CHIPS REMAINING: " + game.treasuresLeft());
                chipsLabel.setForeground(Color.WHITE);
                chipsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                //Stopping the timer once it runs out of time
                if (timeRemaining == 0) {
                    timer.stop();
                    outOfTime();
                }
                if (!isPaused && !replayModeActive) {
                    timeRemaining--;
                }
                else if (replayModeActive) {
                    if(!recordAndReplayer.getPaused()) recordAndReplayer.tick();
                }

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

    @Override
    public void repaint() {
        super.repaint();
        renderer.revalidate();
        renderer.repaint();
    }


    // ===========================================
    // Controlling Game Status
    // ===========================================

    /**
     * Loads a level from a specified integer.
     * @param level Level number
     */
    public void loadLevel(int level){
        levelCount = level;
        isPaused = false; //make sure the game starts in an un-paused state
        killThreads();

        // Persistence and Levels module
        currentLevel =  Persistence.getLevel(level);
        timeRemaining = currentLevel.getTime();

        // Maze module
        game = new Game(new Board(currentLevel.getTileArray()), currentLevel.getPlayer(), currentLevel.getEnemies());

        // Initialize the player inventory
        inventoryView = new InventoryView(game.getPlayer()); //adding inventory view (Application)

        // Renderer module
        renderer = new Renderer(game);

        //Reset this JFrame and reinitialize panels
        this.getContentPane().removeAll();
        initPanels();
        addHotKeys();

        //reset focus in case this method was called from the menu bar
        gameplayPanel.setFocusable(true);
        gameplayPanel.requestFocusInWindow();
        gameplayPanel.requestFocus();
    }

    /**
     * Loads a level specified by a Level object.
     * @param level Level object
     * @param levelCount The level number
     */
    public void loadLevel(Level level, int levelCount){
        if (level != null) {
            this.levelCount = levelCount;
            isPaused = false; //make sure the game starts in an un-paused state
            killThreads();

            // Persistence and Levels module
            currentLevel = level;
            timeRemaining = currentLevel.getTime();

            // Maze module
            game = new Game(new Board(currentLevel.getTileArray()), currentLevel.getPlayer(), currentLevel.getEnemies());

            // Initialize the player inventory
            inventoryView = new InventoryView(game.getPlayer()); //adding inventory view (Application)

            // Renderer module
            renderer = new Renderer(game);

            //Reset this JFrame and reinitialize panels
            this.getContentPane().removeAll();
            initPanels();
            addHotKeys();

            //reset focus in case this method was called from the menu bar
            gameplayPanel.setFocusable(true);
            gameplayPanel.requestFocusInWindow();
            gameplayPanel.requestFocus();
        }
        //save file not found
        else {
            JOptionPane.showMessageDialog(null, "Save file for level " + levelCount + " not found. Please check your saves.", "Load Failed", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Prompts an option screen that lets the user load a saved level.
     */
    public void loadSavedGame(){
        String[] possibleValues = { "Level 1", "Level 2", "Level 3"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose a saved level to load (if it exists)", "Load Game",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        if (selectedValue == null){
            return; //dead code that prevents the NullPointerException
        }
        else if (selectedValue.equals("Level 1")){
            loadLevel(Persistence.loadGame(1), 1);
        }
        else if (selectedValue.equals("Level 2")){
            loadLevel(Persistence.loadGame(2), 2);
        }
        else if (selectedValue.equals("Level 3")){
            loadLevel(Persistence.loadGame(3), 3);
        }
        else {
            //we're not supposed to be here
            System.out.println("Selected level not found: " + selectedValue.toString());
        }
    }

    /**
     * Stops all the running threads. Useful for when exiting the game back to start menu.
     */
    public void killThreads(){
        if (paintThread != null && timer != null){
            paintThread.stop();
            timer.stop();
        }
    }

    /**
     * Save the current game state and show a message dialog when the player saves the game.
     */
    public void saveCurrentGame(){
        JOptionPane.showMessageDialog(null, "Saved current game at level " + levelCount + ".", "Game Saved!", JOptionPane.INFORMATION_MESSAGE);
        Persistence.saveGame(timeRemaining, game.getPlayer(), currentLevel.getEnemies(), levelCount, currentLevel.getTileArray());
    }

    /**
     * Adds different keybindings that controls the state of the game.
     */
    public void addHotKeys() {
        //CTRL + X: exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level
        KeyStroke exitGame = KeyStroke.getKeyStroke('X', InputEvent.CTRL_DOWN_MASK);
        gameplayPanel.getInputMap().put(exitGame, "exit_game");
        gameplayPanel.getActionMap().put("exit_game", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("EXIT CALLED");
                killThreads();
                dispose();
                EventQueue.invokeLater(() -> new StartMenu(levelCount));
            }
        });

        //CTRL + S: exit the game, saves the game state, game will resume next time the application will be started
        KeyStroke saveGame = KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK);
        gameplayPanel.getInputMap().put(saveGame, "save_game");
        gameplayPanel.getActionMap().put("save_game", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("SAVE CALLED");
                saveCurrentGame();
                killThreads();
                dispose();
                StartMenu.lastLevel = levelCount;
                EventQueue.invokeLater(() -> new StartMenu(Persistence.loadGame(levelCount)));
            }
        });

        //CTRL + R: resume a saved game
        KeyStroke resumeSavedGame = KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK);
        gameplayPanel.getInputMap().put(resumeSavedGame, "resume_saved_game");
        gameplayPanel.getActionMap().put("resume_saved_game", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("RESUME SAVED GAME");
                loadSavedGame();
            }
        });

        //CTRL + P: start a new game at the last unfinished level
        KeyStroke newGameLastLevel = KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK);
        gameplayPanel.getInputMap().put(newGameLastLevel, "new_game_last_level");
        gameplayPanel.getActionMap().put("new_game_last_level", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("NEW GAME LAST LEVEL");
                loadLevel(StartMenu.lastLevel);
            }
        });

        //CTRL + 1: start a new game at level 1
        KeyStroke newLevel1 = KeyStroke.getKeyStroke('1', InputEvent.CTRL_DOWN_MASK);
        gameplayPanel.getInputMap().put(newLevel1, "new_game_level_1");
        gameplayPanel.getActionMap().put("new_game_level_1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("NEW GAME LEVEL 1");
                loadLevel(1);
            }
        });

        //SPACEBAR: pause the game and display a “game is paused” dialog
        KeyStroke pauseGame = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        gameplayPanel.getInputMap().put(pauseGame, "pause");
        gameplayPanel.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("PAUSE CALLED");
                if (!isPaused) {
                    pauseResume();
                }
            }
        });

        //ESC: pause the game and display a “game is paused” dialog
        KeyStroke resumeGame = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        gameplayPanel.getInputMap().put(resumeGame, "resume");
        gameplayPanel.getActionMap().put("resume", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isPaused) {
                    System.out.println("RESUME CALLED");
                    pauseResume();
                }
            }
        });
    }

    /**
     * Ends the game when the game clock runs out of time.
     */
    public void outOfTime(){
        JOptionPane.showMessageDialog(null, "You ran out of time!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Checks if the current level has been completed. If so, run the next level.
     */
    public void nextLevel(){
        if (game.isLevelCompleted()) {
            isPaused = true; //stop the game

            //if the final level hasn't been reached
            if (levelCount != maxLevel) {
                int options = JOptionPane.showConfirmDialog(null, "Continue to next level?", "Level " + levelCount + " Completed!",
                        JOptionPane.YES_NO_OPTION);
                if (options == 0) {
                    levelCount++;
                    loadLevel(levelCount);
                } else {
                    System.exit(0);
                }
            }
            //player completes final level
            else {
                JOptionPane.showMessageDialog(null, "You have successfully completed all levels!", "Game Completed", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * Pauses/resumes the game.
     */
    public void pauseResume(){
        //request focus again as it may have been lost from using the menu bar
        gameplayPanel.requestFocusInWindow();
        gameplayPanel.requestFocus();

        isPaused = !isPaused;
        if (isPaused) {
            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            optionPane.setMessage("Press esc to resume the game.");
            JDialog dialog = optionPane.createDialog(null, "Game Paused");
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    isPaused = false;
                }
            });
            dialog.setVisible(true);
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

    // ===========================================
    // RecordAndReplay Helpers
    // ===========================================

    /**
     * Helper for recording.
     * Has an activated and deactivated state, which the menu item can switch on and off.
     * During it's activated state, it records all movement into the recordbuffer.
     * During it's deactivated state, it saves everything on the recordbuffer and stops recording.
     */
    public void recordTrigger() {
        if(recordAndReplayer.getRecordingBoolean()) {
            //if it's true right now. Save gameplay, and switch it to false.
            recordAndReplayer.setRecordingBoolean(false);
            recordAndReplayer.saveGameplay(timeRemaining, game.getPlayer(), game.getComputerPlayers(), game.getBoard().getMap());

            recordItem.setText("Start Recording");
        } else {
            //if it's false right now. Switch it to true. gameplay should start being recorded
            recordAndReplayer.setStartingPosition(game.getPlayer().getPos());
            recordAndReplayer.setRecordingBoolean(true);
            recordAndReplayer.setStartedRecording(timeRemaining);
            recordAndReplayer.setEnemies(game.getComputerPlayers());
            recordAndReplayer.setLevelCount(levelCount);

            Persistence.saveGame(timeRemaining, game.getPlayer(), game.getComputerPlayers(), levelCount, game.getBoard().getMap());

            recordItem.setText("Stop Recording");
        }
    }

    /**
     * Initiate replay mode
     */
    public void replayTrigger() {
        if(recordAndReplayer.getRecordingBoolean()) {
            JOptionPane.showMessageDialog(this, "ERROR: Cannot load replay while recording", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            if(!recordAndReplayer.loadConfirmation(this)) {
                return; //for some reason doesnt exit out of this method as intended...
            } else {
                recordAndReplayer.selectSaveFile(this);
                recordAndReplayer.displayControlWindow();
                replayModeActive = true;
            }
        }
    }

    /**
     * R&R needs a way to move the player in replay mode.
     * @param direction Direction
     */
    public void movePlayer(Game.DIRECTION direction) {
        game.movePlayer(direction);
    }

    /**
     * R&R needs a way to move the enemy in replay mode.
     * @param enemy Enemy
     * @param direction Direction
     */
    public void moveEnemy(AbstractActor enemy, Game.DIRECTION direction) {
        //FIRST: find new position
        Position oldPos = enemy.getPos();
        Position newPos = null;
        switch(direction) {
            case UP:
                newPos = new Position(oldPos, Game.DIRECTION.UP);
                break;
            case DOWN:
                newPos = new Position(oldPos, Game.DIRECTION.DOWN);
                break;
            case LEFT:
                newPos = new Position(oldPos, Game.DIRECTION.LEFT);
                break;
            case RIGHT:
                newPos = new Position(oldPos, Game.DIRECTION.RIGHT);
                break;
        }

        //SECOND: set enemy in new position
        if(game.getPlayer().getPos().equals(oldPos) || game.getPlayer().getPos().equals(newPos)) {
            game.getPlayer().getPos().setPosition(game.getPlayer().getStartingPos());
        }
        enemy.setPos(newPos);
        repaint();
    }

    /**
     * R&R needs a way to modify the time remaining when stepping through replays
     * @param timeRemaining
     */
    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    /**
     * R&R: speeds up the replay
     * @param t true/false for increased speed
     */
    public void setDoubleSpeed(boolean t) {
        if(t) {
            timerDelay = 200;
        } else {
            timerDelay = 1000;
        }
    }

    /**
     * Setter for replay mode
     * @param b true/false
     */
    public void setReplayMoveActive(boolean b) {
        replayModeActive = b;
    }

    /**
     * Getter for level count
     * @return Current level count
     */
    public int getCurrentLevelCount() {
        return levelCount;
    }

    /**
     * Finds the enemy position on the board if one exists
     * @param pos Position of the enemy
     * @return Enemy actor
     */
    public AbstractActor findEnemyAtPos(Position pos) {
        for(AbstractActor a : game.getComputerPlayers()) {
            if(a.getPos().equals(pos)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Teleports the enemies to their recorded position
     *
     * @param pos List of enemy starting positions
     */
    public void teleportEnemies(ArrayList<Position> pos) {
        //Get all enemies
        ArrayList<AbstractActor> enemies = new ArrayList<>();
        for(AbstractActor a : game.getComputerPlayers()) {
            enemies.add(a);
        }

        //teleport enemies to their starting positions
        for(int i = 0; i < pos.size(); i++) {
            System.out.println("teleporting: " + pos.get(i).getX() + ", " + pos.get(i).getY());
            enemies.get(i).setPos(pos.get(i));
        }
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
