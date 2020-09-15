package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

public class Key extends AbstractTile {
    /**
     * .
     * @param position .
     */
    public Key(Position position, Image image) {
        super(position, image);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //NEED TO CHANGE
        return super.equals(obj);
    }
}
