package RecordAndReplay.Actions;

/**
 * A Action object associates itself with a single action or change that takes place in the game.
 * The record and replaying part reads and applies an array of these at once.
 * Because multiple changes can take place at one time. (IE: multiple hostiles moving in sync)
 */
public abstract class Action {
    ActionType type;

    /**
     * Basic constructor.
     * @param type The type this action is associated with.
     */
    public Action(ActionType type) {
        this.type = type;
    }

    /**
     * Change the type this action is associated with.
     * @param type .
     */
    public void setType(ActionType type) {
        this.type = type;
    }

    /**
     * Get the type this action is associated with.
     * @return .
     */
    public ActionType getType() { return type; }
}
