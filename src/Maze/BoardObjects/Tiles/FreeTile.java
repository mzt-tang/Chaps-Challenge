package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
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


    /**
     * The free tile, which allows the players to be on top of it.
     * @param player The player that interacts with the tile.
     * @return Returns true.
     */
    @Override
    public boolean interact(Player player) {
        return super.interact(player);
    }
}
