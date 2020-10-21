package Persistence;
import java.util.ArrayList;

import Maze.Position;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;


public class Level {

  /**
   * @param jsonName -  The name of the JSON file to use.
   */
	private AbstractTile[][] levelArray;
	private Player player;
	private int time;
	private ArrayList<EnemyBlueprint> enemies;
	
	public Level(int pTime, Player pPlayer, AbstractTile[][] pArray, ArrayList<EnemyBlueprint> pEnemies) {
		time = pTime;
		levelArray = pArray;
		player = pPlayer;
		enemies = pEnemies;
	}
	public int getTime() {
		return time;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public AbstractTile[][] getTileArray(){
		return levelArray;
	}
	
	public ArrayList<EnemyBlueprint> getEnemies() {
		return enemies;
	}
}