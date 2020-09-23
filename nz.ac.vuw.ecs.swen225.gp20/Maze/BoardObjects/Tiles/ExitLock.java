package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

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
        super(position, false);
        //images.add(Toolkit.getDefaultToolkit().getImage("images/exitlock/");
    }

    /**
     * Unlocks the door if the player holds the key to this door.
     * @param player The player attempting to unlock the door.
     * @return Returns false because it is locked.
     */
    @Override
    public boolean interact(Player player) {
        return false;
    }
}
