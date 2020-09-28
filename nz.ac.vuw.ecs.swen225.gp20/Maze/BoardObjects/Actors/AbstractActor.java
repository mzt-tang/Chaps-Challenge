package Maze.BoardObjects.Actors;

import Maze.BoardObjects.BoardObject;
import Maze.Position;

/**
 * A abstract class that represents any movable objects on the board.
 */
public abstract class AbstractActor implements BoardObject {

    private final Position position;

    /**
     * Basic constructor for any actors
     * @param position the position of the actors
     */
    public AbstractActor(Position position) {
        this.position = position;
    }

    public abstract void move(Player player);

    public abstract void interact(Player player);

    /**
     * Returns the position of the actor.
     * @return .
     */
    public Position getPos() {
        return position;
    }

}
