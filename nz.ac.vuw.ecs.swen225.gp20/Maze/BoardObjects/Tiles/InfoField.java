package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

public class InfoField extends AbstractTile {

    private final String infoText; //Maybe it won't be final

    public InfoField(Position position, String infoText) {
        super(position, false);
        this.infoText = infoText;
        images.put("InfoField", Toolkit.getDefaultToolkit().getImage("Resources/tiles/Infofield.jpeg"));
        currentImage = images.get("Infofield");
    }

    @Override
    public boolean interact(Player player) {
        //Display text - NEED TO CHANGE
        System.out.println(infoText);
        return super.interact(player);
    }

    public String getInfoText() {
        return infoText;
    }
}
