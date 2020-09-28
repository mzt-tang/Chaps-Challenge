package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

public class ExitPortal extends AbstractTile {
    public ExitPortal(Position position) {
        super(position, false);
        images.put("Vent", Toolkit.getDefaultToolkit().getImage("Resources/tiles/Vent.jpeg"));
        currentImage = images.get("Vent");
    }

    @Override
    public boolean interact(Player player) {
        return true;
    }


}