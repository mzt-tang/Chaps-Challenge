package Persistence;

import java.util.ArrayList;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;

public class Persistence {
	public static Level getLevel(int levelNumber){
		String levelString = "levels/level" + levelNumber + ".JSON";
		LevelJSONReader readJSON = new LevelJSONReader();
		Level returnLevel = readJSON.readJSON(levelString);
		return returnLevel;
	}
	
	public boolean saveGame(int remainingTime, Player player, ArrayList<AbstractActor> enemies, int levelNumber, AbstractTile[][] tiles) {
		String saveString = "saves/level"+levelNumber+".JSON";
		SaveJSONMaker.makeJSON(remainingTime, player, enemies, saveString, tiles);
		return false;
	}
	
	public boolean loadGame(String fileName) {
		
		return false;
	}
}
