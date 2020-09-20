package Renderer;

import Maze.Board;
import Maze.BoardObjects.Tiles.*;
import Maze.Position;

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
    private static final int focusSize = 9;
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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g; //Graphics 2D gives you more drawing options

        //Set background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private AbstractTile[][] aRandomBoard(){
        AbstractTile[][] board = new AbstractTile[9][9];
        for (int y = 0; y < focusSize; y++){
            for (int x = 0; x < focusSize; x++){
                board[x][y] = new FreeTile(new Position(x,y));
            }
        }
        board[0][0] = new ExitLock(new Position(0,0));
        board[1][0] = new ExitPortal(new Position(1,0));
        board[2][0] = new InfoField(new Position(2,0), "Hello");
        board[3][0] = new Key(new Position(3,0), "Red");
        board[4][0] = new LockedDoor(new Position(4,0), new Key(new Position(3,0), "Red"));
        board[5][0] = new Treasure(new Position(5,0));
        board[6][0] = new Wall(new Position(6,0));
        return board;
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
            return images.get("FreeTile");
        }
        if (tile instanceof InfoField){
            return images.get("InfoField");
        }
        if (tile instanceof Key){
            Key key = (Key)tile;
            switch (key.getColour()){
                case "Blue":
                    return images.get("SwipeCardBlue");
                case "Green":
                    return images.get("SwipeCardGreen");
                case "Red":
                    return images.get("SwipeCardRed");
                case "Yellow":
                    return images.get("SwipeCardYellow");
            }
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

    public static void main(String[] args) {
        new Renderer(); //Just for testing (and so I don't interfere with other modules)
    }
}
