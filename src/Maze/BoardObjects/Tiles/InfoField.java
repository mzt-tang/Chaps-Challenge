package Maze.BoardObjects.Tiles;

import Maze.Position;

public class InfoField extends AbstractTile {

    private final String infoText; //Maybe it won't be final

    public InfoField(Position position, String infoText) {
        super(position);
        this.infoText = infoText;
    }

    public String getInfoText() {
        return infoText;
    }
}
