package RecordAndReplay.Actions;

import Maze.Game;

public class PlayerMove extends Action {
    private Game.DIRECTION direction;

    public PlayerMove(Game.DIRECTION direction) {
        super(ActionType.PLAYERACTION);
        this.direction = direction;
    }

    public Game.DIRECTION getDirection() { return direction; }
}
