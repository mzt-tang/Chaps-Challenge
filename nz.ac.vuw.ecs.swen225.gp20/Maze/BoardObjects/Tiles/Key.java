package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;
import java.util.Objects;

public class Key extends AbstractTile {

    private String colour;
    private boolean pickedUp = false;

    /**
     * .
     * @param position .
     */
    public Key(Position position, String colour) {
        super(position, false);
        this.colour = colour;
        images.put("Files", Toolkit.getDefaultToolkit().getImage("Resources/tiles/Files.jpeg"));
        currentImage = images.get("Files");
    }

    @Override
    public boolean interact(Player player) {
        player.getKeys().add(this);
        pickedUp = true;
        return true;
    }

    public String getColour() {
        return colour;
    }
}
