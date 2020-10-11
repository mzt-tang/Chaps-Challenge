package Maze.BoardObjects.Actors.stalker_enemy;

import Maze.Board;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.Position;

import java.util.Set;

public class StalkerEnemy extends AbstractActor {

    /*
     * @param position the position of the actors
     */
    public StalkerEnemy(Position position) {
        super(position);
    }

    @Override
    public void move(Player player, Board board) {


    }

    @Override
    public void interact(Player player) {

    }

    private Set<AbstractTile> findNeighbours(AbstractTile tile, Board board) {
        AbstractTile[][] map = board.getMap();
        Position position;
        outer:
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; i++){
                if(tile == map[i][j]){
                    position = new Position(i, j);
                }
            }
        }
        return null;
    }
}
