package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

public class ExitPortal extends AbstractTile {
    public ExitPortal(Position position) {
        super(position, false);
        images.put("Vent", Toolkit.getDefaultToolkit().getImage("Resources/tiles/Vent.jpeg"));
        currentImage = images.get("Vent");
    }
}
