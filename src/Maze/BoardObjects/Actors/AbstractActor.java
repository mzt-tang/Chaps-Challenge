package Maze.BoardObjects.Actors;

import Maze.BoardObjects.BoardObject;
import Maze.Position;

/**
 * A abstract class that represents any movable objects on the board.
 */
public abstract class AbstractActor implements BoardObject {

    private Position position;

    /**
     * Basic constructor for any actors
     * @param position the position of the actors
     */
    public AbstractActor(Position position) {
        this.position = position;
    }


    /**
     * Returns the position of the actor.
     * @return .
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the actor.
     * @param position .
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
