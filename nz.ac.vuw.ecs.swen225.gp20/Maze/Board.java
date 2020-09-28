package Maze;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Board {

    private AbstractTile[][] map;

    public Board(AbstractTile[][] tileMap) {
        //this.board = board;
        this.map = tileMap;
    }

    public AbstractTile[][] getUnmodMap(){
        //NEED TO CHANGE
        return map;
    }

    public AbstractTile[][] getMap() {
        return map;
    }
}