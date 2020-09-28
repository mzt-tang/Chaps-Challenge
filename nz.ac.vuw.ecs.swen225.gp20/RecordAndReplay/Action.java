package RecordAndReplay;

/**
 * A Action object associates itself with a single action or change that takes place in the game.
 * The record and replaying part reads and applies an array of these at once.
 * Because multiple changes can take place at one time. (IE: multiple hostiles moving in sync)
 */
public class Action {
    ActionType type;
    //Maze.Game.DIRECTION direction; NOTE TO SELF: dont worry about this yet, go to ActionType to see the full note.

    public Action() {
    }

    public void setType(ActionType type) {
        this.type = type;
    }
    public ActionType getType() {
        return type;
    }
}
