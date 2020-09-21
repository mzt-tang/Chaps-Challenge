package Renderer;

import Maze.Board;
import Maze.BoardObjects.Tiles.*;
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

    /**
     * Creates a new renderer canvas
     */
    public Renderer(){
        images = new HashMap<>();
        //Really compact way of loading all the images into memory
        //It iterates through all the files in /images and maps the file names to the loaded images
        File[] files = new File(System.getProperty("user.dir") + "/images").listFiles();
        for (File file : files){
            images.put(file.getName().substring(0,file.getName().length()-5), //removes .jpeg extension
                    Toolkit.getDefaultToolkit().getImage(file.getPath()));
        }

        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; //Graphics 2D gives you more drawing options

        //Set background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        //Will get the real board once a level has been created
        AbstractTile[][] board = aRandomBoard();

        //Will put the focus area around the player once that is possible
        for (int y = 0; y < FOCUS_SIZE; y++) {
            for (int x = 0; x < FOCUS_SIZE; x++) {
                g2.drawImage(getTileImage(board[x][y]), x*IMAGE_SIZE, y*IMAGE_SIZE, this);
            }
        }
    }

    private Image getTileImage(AbstractTile tile){
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
            String colour = lockedDoor.getKey().getColour();
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
    }

    private AbstractTile[][] aRandomBoard(){
        AbstractTile[][] board = new AbstractTile[9][9];
        for (int y = 0; y < 9; y++){
            for (int x = 0; x < 9; x++){
                board[x][y] = new FreeTile(new Position(x,y));
            }
        }
        board[0][0] = new ExitLock(new Position(0,0));
        ExitLock elV = new ExitLock(new Position(0,1));
        elV.setVertical(); //Maybe rotation should be in the AbstractTile constructor
        board[0][1] = elV;
        board[1][0] = new ExitPortal(new Position(1,0));
        board[2][0] = new InfoField(new Position(2,0), "Hello");
        board[3][0] = new Key(new Position(3,0), "Blue");
        board[3][1] = new Key(new Position(3,1), "Green");
        board[3][2] = new Key(new Position(3,2), "Red");
        board[3][3] = new Key(new Position(3,3), "Yellow");
        board[4][0] = new LockedDoor(new Position(4,0), false, new Key(new Position(3,0),  "Blue"));
        board[4][1] = new LockedDoor(new Position(4,1), false, new Key(new Position(3,0), "Green"));
        board[4][2] = new LockedDoor(new Position(4,2), false, new Key(new Position(3,2), "Red"));
        board[4][3] = new LockedDoor(new Position(4,3), false, new Key(new Position(3,0), "Yellow"));
        LockedDoor ldV1 = new LockedDoor(new Position(4,4), true, new Key(new Position(3,0), "Blue"));
        board[4][4] = ldV1;
        LockedDoor ldV2 = new LockedDoor(new Position(4,5), true, new Key(new Position(3,0), "Green"));
        ldV2.setVertical();
        board[4][5] = ldV2;
        LockedDoor ldV3 = new LockedDoor(new Position(4,6), true, new Key(new Position(3,0), "Red"));
        ldV3.setVertical();
        board[4][6] = ldV3;
        LockedDoor ldV4 = new LockedDoor(new Position(4,7), true, new Key(new Position(3,0), "Yellow"));
        ldV4.setVertical();
        board[4][7] = ldV4;
        board[5][0] = new Treasure(new Position(5,0));
        board[6][0] = new Wall(new Position(6,0));
        return board;
    }

    public static void main(String[] args) {
        Canvas canvas = new Renderer(); //Just for testing (and so I don't interfere with other modules)
        JFrame frame = new JFrame("Test");
        JPanel main = new JPanel();
        main.add(canvas);
        frame.add(main);
        frame.pack();
        frame.setVisible(true);
    }
}
