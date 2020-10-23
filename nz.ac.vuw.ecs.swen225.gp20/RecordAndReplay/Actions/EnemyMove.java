package RecordAndReplay.Actions;

import Maze.Game;

/**
 * The action type of an enemy moving.
 */
public class EnemyMove extends Action{
    private int x, y; //the x and y which the move from
    private Game.DIRECTION direction;

    /**
     * Basic constructor.
     *
     * @param x Previous X position.
     * @param y Previous Y position.
     * @param direction Direction it moved, or is moving to.
     */
    public EnemyMove(int x, int y, Game.DIRECTION direction) {
        super(ActionType.ENEMYACTION);
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Return this direction enum value.
     * @return .
     */
    public Game.DIRECTION getDirection() {
        return direction;
    }

    /**
     * Return x location.
     * @return .
     */
    public int getX() {
        return x;
    }

    /**
     * Return y location.
     * @return .
     */
    public int getY() {
        return y;
    }

}
