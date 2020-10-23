package RecordAndReplay.Actions;

import Maze.Game;

/**
 * The action type of the player moving.
 *
 * @author Jeremiah Choi 300474835
 */
public class PlayerMove extends Action {
    private Game.DIRECTION direction;

    /**
     * Basic constructor.
     * @param direction Direction the player moved, or is moving to.
     */
    public PlayerMove(Game.DIRECTION direction) {
        super(ActionType.PLAYERACTION);
        this.direction = direction;
    }

    /**
     * Get this action's direction enum value.
     * @return .
     */
    public Game.DIRECTION getDirection() { return direction; }
}
