package RecordAndReplay;

/**
 * A Action object associates itself with a single action or change that takes place in the game.
 */
public class Action {
    ActionType type;
    Maze.Game.DIRECTION direction;

    public Action() {

    }

    public void setType(ActionType type) {
        this.type = type;
    }
    public ActionType getType() {
        return type;
    }

}
