package Maze.BoardObjects.Tiles;

import Maze.Position;

import java.awt.*;

public class ExitPortal extends AbstractTile {
    public ExitPortal(Position position) {
        super(position, false);
        images.put("", Toolkit.getDefaultToolkit().getImage("Resources/tiles/Vent.jpeg"));
    }
}
