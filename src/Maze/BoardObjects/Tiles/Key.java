package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;
import java.util.Objects;

public class Key extends AbstractTile {

    private String colour;

    /**
     * .
     * @param position .
     */
    public Key(Position position, String colour) {
        super(position);
        this.colour = colour;
    }

    @Override
    public boolean interact(Player player) {
        player.getKeys().add(this);
        return super.interact(player);
    }

    public String getColour() {
        return colour;
    }

    /**
     * Checks if the colours of the keys compared are the same.
     * @param o The other object being compared to.
     * @return True if the keys have the same colour.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(colour, key.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour);
    }
}
