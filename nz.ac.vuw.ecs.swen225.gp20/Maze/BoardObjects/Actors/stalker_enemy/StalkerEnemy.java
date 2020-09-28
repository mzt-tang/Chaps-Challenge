package Maze.BoardObjects.Actors.stalker_enemy;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.Position;

import java.util.PriorityQueue;
import java.util.Queue;

public class StalkerEnemy extends AbstractActor {

    /*
     * @param position the position of the actors
     */
    public StalkerEnemy(Position position) {
        super(position);
    }

    @Override
    public void move(Player player) {

    }

    @Override
    public void interact(Player player) {

    }

    private Position getNextPos(Node start, Node goal){
        Queue<Fringe> fringe = new PriorityQueue<>();
        return null;
    }
}
