package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

/**
 * The "empty" tile on the board, any actor can move freely onto this tile.
 */
public class FreeTile extends AbstractTile {

    /**
     * .
     * @param position .
     */
    public FreeTile(Position position, Image image) {
        super(position, image);
    }
}
