package Maze.BoardObjects.Tiles;

import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.awt.*;

public class InfoField extends AbstractTile {

    private final String infoText; //Maybe it won't be final

    public InfoField(Position position, String infoText) {
        super(position, false);
        this.infoText = infoText;
        images.put("InfoField", Toolkit.getDefaultToolkit().getImage("Resources/tiles/InfoField.jpeg"));
        currentImage = images.get("InfoField");
    }

    @Override
    public boolean interact(Player player) {
        //Display text - NEED TO CHANGE
        System.out.println(infoText);
        return true;
    }

    public String getInfoText() {
        return infoText;
    }
}
