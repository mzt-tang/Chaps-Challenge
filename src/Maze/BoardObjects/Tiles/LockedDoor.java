package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

public class LockedDoor extends AbstractTile {

    private boolean locked;
    private Key key;

    /**
     * .
     * @param position .
     */
    public LockedDoor(Position position) {
        super(position);
        locked = true;
    }


    public boolean isLocked() {
        return locked;
    }

    /**
     * Unlocks the door if the player holds the key to this door.
     * @param player The player attempting to unlock the door.
     */
    public void unlock(Player player) {
        if(player.hasKey(key)) locked = false;
    }
}
