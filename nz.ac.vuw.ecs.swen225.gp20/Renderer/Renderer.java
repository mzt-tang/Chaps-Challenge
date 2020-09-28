package Renderer;

import Application.ChapsChallenge;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.*;
import Maze.Game;
import Maze.Position;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This is responsible for displaying the Maze on the screen
 * including all it's tiles, animations and sound effects
 * @author Chris (ID: 300498017)
 */
public class Renderer extends Canvas {
    private static final int FOCUS_SIZE = 9; //The grid size of the board shown, which is 9x9 tiles
    private static final int IMAGE_SIZE = 60;
    private static final int CANVAS_SIZE = 540; //Size in pixels, 9 x 60px images

    private final Map<String, Image> images;

    private boolean playerFlipped = false;
    private Position playerPrevPos;

    ChapsChallenge application;

    /**
     * Creates a new renderer canvas
     */
    public Renderer(ChapsChallenge application){
        images = new HashMap<>();
        this.application = application;
        playerPrevPos = application.getGame().getPlayer().getPos();

        //Really compact way of loading all the images into memory
        //It iterates through all the files in a folder and maps the file names to the loaded images
        File[] files = new File(System.getProperty("user.dir") + "/Resources/tiles").listFiles();
        for (File file : files){
            images.put(file.getName().substring(0,file.getName().length()-5), //removes .jpeg extension
                    Toolkit.getDefaultToolkit().getImage(file.getPath()));
        }

        files = new File(System.getProperty("user.dir") + "/Resources/actors").listFiles();
        for (File file : files){
            images.put(file.getName().substring(0,file.getName().length()-4), //removes .png extension
                    Toolkit.getDefaultToolkit().getImage(file.getPath()));
        }

        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; //Graphics 2D gives you more drawing options
        System.out.println("Paint called");

        //Set background (Doesn't have to be white, could be space because Among Us, could even be animated)
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        //Will get the real board once a level has been created
        AbstractTile[][] board = application.getGame().getBoard().getMap();

        //Will get the actual player's position once the application creates the game with I can get the player from
        Player player = application.getGame().getPlayer();
        int playerX = player.getPos().getX();
        int playerY = player.getPos().getY();

        //Draw all tiles in the focus area
        for (int y = -4; y <= 4; y++) {
            for (int x = -4; x <= 4; x++) {
                //If in board bounds
                if (playerX + x >= 0 && playerY + y >= 0 && playerX + x < board.length && playerY + y < board[0].length){
                    g2.drawImage(board[playerX + x][playerY + y].getCurrentImage(),
                            (x+4) * IMAGE_SIZE, (y+4) * IMAGE_SIZE, this);
                }
            }
        }

        //Can't test this until the player can move
        System.out.println(playerX);
        System.out.println(playerPrevPos.getX());
        if (playerX < playerPrevPos.getX()){
            playerFlipped = true;
        }
        if (playerX > playerPrevPos.getX()){
            playerFlipped = false;
        }

        //Draw player on the centre of the screen
        if (playerFlipped) {
            g2.drawImage(images.get("AstronautFlipped"), 4 * IMAGE_SIZE, 4 * IMAGE_SIZE, this);
        } else {
            g2.drawImage(images.get("Astronaut"), 4 * IMAGE_SIZE, 4 * IMAGE_SIZE, this);
        }
        playerPrevPos = player.getPos().getPositionCopy();
    }

   /* private Image getTileImage(AbstractTile tile){
        if (tile instanceof ExitLock){
            if (tile.isRotated()){
                return images.get("ExitLockVertical");
            } else {
                return images.get("ExitLockHorizontal");
            }
        }
        if (tile instanceof ExitPortal){
            return images.get("Vent");
        }
        if (tile instanceof FreeTile){
            return images.get("FloorTile");
        }
        if (tile instanceof InfoField){
            return images.get("InfoField");
        }
        if (tile instanceof Key){
            Key key = (Key)tile;
            return images.get("SwipeCard" + key.getColour());
        }
        if (tile instanceof LockedDoor){
            LockedDoor lockedDoor = (LockedDoor)tile;
            String colour = lockedDoor.getDoorColour();
            if (lockedDoor.isRotated()){
                return images.get("DoorVertical" + colour);
            } else {
                return images.get("DoorHorizontal" + colour);
            }
        }
        if (tile instanceof Treasure){
            return images.get("Files");
        }
        if (tile instanceof Wall){
            return images.get("WallTile");
        }
        return null;
    } */

    public static AbstractTile[][] aRandomBoard(){
        AbstractTile[][] board = new AbstractTile[9][9];
        for (int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++){
                board[x][y] = new FreeTile(new Position(x,y));
            }
        }
        board[0][0] = new ExitLock(new Position(0,0), false);
        board[0][1] = new ExitLock(new Position(0,1), true);
        board[1][0] = new ExitPortal(new Position(1,0));
        board[2][0] = new InfoField(new Position(2,0), "Hello");
        board[3][0] = new Key(new Position(3,0), "Blue");
        board[3][1] = new Key(new Position(3,1), "Green");
        board[3][2] = new Key(new Position(3,2), "Red");
        board[3][3] = new Key(new Position(3,3), "Yellow");
        board[4][0] = new LockedDoor(new Position(4,0), false, "Blue");
        board[4][1] = new LockedDoor(new Position(4,1), false, "Green");
        board[4][2] = new LockedDoor(new Position(4,2), false, "Red");
        board[4][3] = new LockedDoor(new Position(4,3), false, "Yellow");
        board[4][4] = new LockedDoor(new Position(4,4), true, "Blue");
        board[4][5] = new LockedDoor(new Position(4,5), true, "Green");
        board[4][6] = new LockedDoor(new Position(4,6), true, "Red");
        board[4][7] = new LockedDoor(new Position(4,7), true, "Yellow");
        board[5][0] = new Treasure(new Position(5,0));
        board[6][0] = new Wall(new Position(6,0));
        return board;
    }

    public static AbstractTile[][] level1(){
        AbstractTile[][] board = new AbstractTile[21][21];
        for (int y = 0; y < 21; y++){
            for (int x = 0; x < 21; x++){
                if (x == 0 || y == 0 || x == 20 || y == 20){
                    board[x][y] = new Wall(new Position(x,y));
                } else {
                    board[x][y] = new FreeTile(new Position(x, y));
                }
            }
        }
        board[10][10] = new ExitPortal(new Position(10,10));
        return board;
    }

    //Just for testing
    /*public static void main(String[] args) {
        Canvas canvas = new Renderer();
        JFrame frame = new JFrame("Test");
        JPanel main = new JPanel();
        main.add(canvas);
        frame.add(main);
        frame.pack();
        frame.setVisible(true);
    } */
}
