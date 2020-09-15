package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.BoardObject;
import Maze.Position;

import javax.swing.*;
import java.awt.*;

/**
 * An implementation of the Tile interface,
 * all other tiles object will use the methods
 * within this abstract class.
 */
public abstract class AbstractTile implements BoardObject {

    public final Position position; //Final cause tiles don't move to different places (although they can be picked up)
    public final Image image;

    /**
     * .
     * @param position .
     */
    public AbstractTile(Position position, Image image) {
        this.position = position;
        this.image = image;
    }

    public boolean interact(){
        return true;
    }

}
