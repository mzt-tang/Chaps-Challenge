package Maze.BoardObjects.Actors.stalker_enemy;

import Maze.BoardObjects.Tiles.AbstractTile;

public class Fringe implements Comparable<Fringe>{

    private AbstractTile start;
    private Fringe previous;
    private int cost;

    public Fringe(AbstractTile start, Fringe previous, int cost){
        this.start = start;
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
}
