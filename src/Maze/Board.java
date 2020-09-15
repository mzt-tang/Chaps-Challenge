package Maze;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Board {

    private AbstractTile[][] map;

    public Board(AbstractTile[][] tileMap) {
        //this.board = board;
        this.map = new AbstractTile[9][9];
    }

    public AbstractTile[][] getUnmodMap(){
        //NEED TO CHANGE
        return map;
    }

    public AbstractTile[][] getMap() {
        return map;
    }
}