package Maze.BoardObjects.Actors.stalker_enemy;

import Maze.Board;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.Position;

import java.util.*;

public class StalkerEnemy extends AbstractActor {

    /*
     * @param position the position of the actors
     */
    public StalkerEnemy(Position position) {
        super(position);
    }

    @Override
    public void move(Player player, Board board) {
        Fringe path = findPath(player, board);
        if(path.getPrevious() == null) {
            interact(player); //? maybe?
            return;
        }

        while(path.getPrevious().getPrevious() != null) {
            path = path.getPrevious();
        }

        position = board.findPosInBoard(path.getCurrent());
    }

    private Fringe findPath(Player player, Board board) {
        Queue<Fringe> fringeQueue = new ArrayDeque<>();
        fringeQueue.offer(new Fringe(board.getMap()[position.getX()][position.getY()], null));
        Set<AbstractTile> visited = new HashSet<>();

        while (!fringeQueue.isEmpty()) {
            Fringe current = fringeQueue.poll();
            AbstractTile node = current.getCurrent();

            if(!visited.contains(node)) {
                visited.add(node);

                //Found the shortest path
                if (board.findPosInBoard(node).equals(player.getPos())) {
                    return current;
                }

                for(AbstractTile n : findNeighbours(node, board)) {
                    if(!visited.contains(n)) {
                        fringeQueue.offer(new Fringe(n, current));
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void interact(Player player) {

    }

    private Set<AbstractTile> findNeighbours(AbstractTile tile, Board board) {
        AbstractTile[][] map = board.getMap();
        Position position = board.findPosInBoard(tile);

        Set<AbstractTile> neighbours = new HashSet<>();

        assert position != null;
        if(position.getX() != 0) {
            AbstractTile left = map[position.getX() - 1][position.getY()];
            neighbours.add(left);
        }

        if(position.getX() != map.length-1) {
            AbstractTile right = map[position.getX() + 1][position.getY()];
            neighbours.add(right);
        }

        if(position.getY() != 0) {
            AbstractTile up = map[position.getX()][position.getY() - 1];
            neighbours.add(up);
        }

        if(position.getY() != map[position.getX()].length) {
            AbstractTile down = map[position.getX() - 1][position.getY() + 1];
            neighbours.add(down);
        }

        return neighbours;
    }
}
