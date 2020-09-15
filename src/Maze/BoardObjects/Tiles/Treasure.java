package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

public class Treasure extends AbstractTile {


    private boolean collected;

    /**
     * .
     * @param position .
     */
    public Treasure(Position position, Image image) {
        super(position, image);
    }

    public boolean isCollected() {
        return collected;
    }
}
