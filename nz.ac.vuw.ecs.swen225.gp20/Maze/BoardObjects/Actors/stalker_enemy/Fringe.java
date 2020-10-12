package Maze.BoardObjects.Actors.stalker_enemy;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Fringe implements Comparable<Fringe>{

    private AbstractTile current;
    private Fringe previous;
    private int cost;

    public Fringe(AbstractTile current, Fringe previous, int cost){
        this.current = current;
        this.previous = previous;
        this.cost = cost;
    }


    @Override
    public int compareTo(Fringe fringe) {
        return Integer.compare(cost, fringe.getCost());
    }

    public int getCost() {
        return cost;
    }

    public AbstractTile getCurrent() {
        return current;
    }

    public Fringe getPrevious() {
        return previous;
    }
}
