package Maze.BoardObjects.Tiles;

import Maze.Position;

/**
 * The lock to the room containing the exit portal,
 * unlocked when the player collects all of the treasures.
 */
public class ExitLock extends AbstractTile {

    /**
     * .
     * @param position .
     */
    public ExitLock(Position position) {
        super(position);
    }
}
