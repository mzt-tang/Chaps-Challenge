package Maze;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Board {

    private AbstractTile[][] board;

    public Board(AbstractTile[][] board) {
        this.board = board;
    }

    public AbstractTile[][] getUnmodBoard(){
        //NEED TO CHANGE
        return board;
    }

}