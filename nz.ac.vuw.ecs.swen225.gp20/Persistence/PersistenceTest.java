package Persistence;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Scanner;

import Maze.Position;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.PatternEnemy;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;

public class PersistenceTest {
	public static void main(String[] args) {
	    CSVReader csvRead = new CSVReader();
	    LevelJSONMaker makeJSON = new LevelJSONMaker();
	    Persistence getJson = new Persistence();
	    
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter the level number you wish to work with (1-9)");
		String levelNumberString = sc.nextLine();
		
		int levelNumber = StringToInt(levelNumberString);
		if(levelNumber > 10 || levelNumber <= 0) {
			System.out.println("Proper number please.");
			return;
		}
		System.out.println("Enter SKIP if you don't want to convert level.csv into levels/level"+levelNumber+".json");
		String skipString = sc.nextLine();
		sc.close();
		String levelString = "levels/level" + levelNumber + ".JSON";
		
		if(skipString.equals("SKIP") == false) {
			makeJSON.makeJSON(csvRead.readCSV("nz.ac.vuw.ecs.swen225.gp20/Persistence/level.csv"), levelString);
		}
	    
	    Level testLevel = getJson.getLevel(levelNumber);    	        
	    getJson.saveGame(91, testLevel.getPlayer(), testLevel.getEnemies(), levelNumber, testLevel.getTileArray());    
	    Level newLevel = Persistence.loadGame(levelNumber);
	    if(newLevel.getEnemies().size()>0) {
	    	System.out.println("Enemy count is > 0 " + newLevel.getEnemies().get(0).getPos());
	    }
	}
	
	  private static int StringToInt(String intString) {
		  int charInt = 0;
		  for(int i = 0; i < intString.length(); i++) {
			  charInt = charInt*10;
			  charInt += intString.charAt(i) - '0';
		  }
		  return charInt;
	  }
}
