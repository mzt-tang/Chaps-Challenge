package MonkeyTest;

import java.util.Random;

import org.junit.*;

import Maze.Board;
import Maze.Game;
import Maze.Position;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;
import Persistence.*;
import Renderer.Renderer;



public class TestSuite {
	private Game tester;
	
	public TestSuite() {
		
		tester = new Game(new Board(boardMaker()), new Player(new Position(4, 4)), null); //PLACEHOLDER, need to implement level loader
	}
	
	@Test public void randomMovement() {
		Random ran = new Random();
		float num = ran.nextFloat();
		System.out.println(tester.getPlayer().getPos());
		if(num < 0.25) {System.out.println("DEBUG - MOVE UP"); tester.movePlayer(Game.DIRECTION.UP);}
		else if(num < 0.5) {System.out.println("DEBUG - MOVE DOWN");tester.movePlayer(Game.DIRECTION.DOWN);}
		else if(num < 0.75) {System.out.println("DEBUG - MOVE LEFT");tester.movePlayer(Game.DIRECTION.LEFT);}
		else {System.out.println("DEBUG - MOVE RIGHT"); tester.movePlayer(Game.DIRECTION.RIGHT);}
	}
	
	@Test public void intelligentMovement() {
		
	}
	
	
	
	private AbstractTile[][] boardMaker() {
		CSVReader csvRead = new CSVReader();
	    JSONMaker makeJSON = new JSONMaker();
	    JSONReader readJSON = new JSONReader();
	    makeJSON.makeJSON(csvRead.readCSV("nz.ac.vuw.ecs.swen225.gp20/MonkeyTest/leveltest.csv"), "nz.ac.vuw.ecs.swen225.gp20/MonkeyTest/level.json");
	    AbstractTile[][] board = readJSON.readJSON("nz.ac.vuw.ecs.swen225.gp20/MonkeyTest/level.json");
		return board;
	}
	
}
