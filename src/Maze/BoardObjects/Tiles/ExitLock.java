package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

/**
 * The lock to the room containing the exit portal,
 * unlocked when the player collects all of the treasures.
 */
public class ExitLock extends AbstractTile {

    private boolean locked;

    /**
     * .
     * @param position .
     */
    public ExitLock(Position position) {
        super(position);
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Unlocks the door if the player holds the key to this door.
     * @param player The player attempting to unlock the door.
     */
    public void unlock(Player player) {
        if(player.treasuresAllCollected()) locked = false;
    }
}
