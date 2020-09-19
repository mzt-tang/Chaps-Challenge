package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

public class LockedDoor extends AbstractTile {

    private Key key;

    /**
     * .
     * @param position .
     */
    public LockedDoor(Position position, Key key) {
        super(position);
        this.key = key;
    }

    /**
     * Returns true if the player holds the key to this door, otherwise it acts like a wall tile.
     * @param player The player that interacts with the tile.
     * @return Returns true if the player holds the key to this door, otherwise it acts like a wall tile.
     */
    @Override
    public boolean interact(Player player) {
        if(!player.hasKey(key)) return false;
        return super.interact(player);
    }

}
