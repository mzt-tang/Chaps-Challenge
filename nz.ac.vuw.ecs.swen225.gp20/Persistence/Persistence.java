package Persistence;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Persistence {
	public Level getLevel(int levelNumber){
		String levelString = "levels/level" + levelNumber + ".JSON";
		JSONReader readJSON = new JSONReader();
		Level returnLevel = readJSON.readJSON(levelString);
		return returnLevel;
	}
}
