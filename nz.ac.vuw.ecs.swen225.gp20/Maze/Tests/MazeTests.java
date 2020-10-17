package Maze.Tests;

import Maze.Board;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.BoardObjects.Tiles.FreeTile;
import Maze.Game;
import org.junit.Test;

public class MazeTests {

    @Test
    public void test1(){
        //AbstractTile[][] map = {};
        //Game game = new Game(new Board(), );
    }

    private AbstractTile[][] makeMap(){
        AbstractTile[][] map = new AbstractTile[4][4];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                map[i][j] = new FreeTile();
            }
        }



        return map;
    }
}
