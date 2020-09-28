package Maze.BoardObjects.Actors.stalker_enemy;

public class Fringe implements Comparable<Fringe>{

    private Node start;
    private Fringe previous;
    private int cost;

    public Fringe(Node start, Fringe previous, int cost){
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
