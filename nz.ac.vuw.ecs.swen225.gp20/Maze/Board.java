package Maze;

import Maze.BoardObjects.Tiles.AbstractTile;
import com.google.common.base.Preconditions;

/**
 * The Board class containing the map array of tiles.
 * @author michael tang 300490290
 */
public class Board {

    private AbstractTile[][] map;

    /**
     * The board constructor
     * @param tileMap The array map of a level.
     */
    public Board(AbstractTile[][] tileMap) {
        this.map = Preconditions.checkNotNull(tileMap, "Tilemap must not be null");
    }

    /**
     * Gets the array tile map of the board.
     * @return Returns the array tile map of the board.
     */
    public AbstractTile[][] getMap(){
        return map;
    }


    /**
     * Finds the position of a tile in the board.
     * @param tile The tile to be found, MUST BE A POINTER TO THE ACTUAL TILE,
     *             NOT A COPY OF THE TILE.
     * @return Returns a position containing the x,y position of the tile in the array.
     */
    public Position findPosInBoard(AbstractTile tile){
        Preconditions.checkNotNull(tile, "Tile should not be null");
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                if(tile == map[i][j]){
                    return new Position(i, j);
                }
            }
        }
        return null; //Should never reach here
    }

}