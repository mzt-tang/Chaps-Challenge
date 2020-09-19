package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

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

    /**
     * The tile interacts with the player. Allowing the player to pick up its treasure
     * and move on top of it.
     * @param player The player.
     * @return Returns true, allowing the player to move on top of it.
     */
    @Override
    public boolean interact(Player player) {


        return super.interact(player);
    }
}
