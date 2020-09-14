package Maze.BoardObjects.Tiles;

import Maze.Position;

public class Treasure extends AbstractTile {


    private boolean collected;

    /**
     * .
     * @param position .
     */
    public Treasure(Position position) {
        super(position);
    }

    public boolean isCollected() {
        return collected;
    }
}
