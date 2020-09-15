package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

public class InfoField extends AbstractTile {

    private final String infoText; //Maybe it won't be final

    public InfoField(Position position, Image image, String infoText) {
        super(position, image);
        this.infoText = infoText;
    }

    public String getInfoText() {
        return infoText;
    }
}
