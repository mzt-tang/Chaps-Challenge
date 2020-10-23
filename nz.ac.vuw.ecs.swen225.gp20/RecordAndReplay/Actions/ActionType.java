package RecordAndReplay.Actions;

/**
 * Created to avoid spaghetti code later.
 * This is the list of changes that can occur on the level.
 * A player could move or an enemy could move
 *
 * An "actiontype" is an action that can spark multiple functions (ie: checks to see if creature attacks player)
 *
 */
public enum ActionType {
    PLAYERACTION("playerAction"), ENEMYACTION("enemyAction");

    private String asString;

    /**
     * Basic constructor
     * @param asString This enum as a string.
     */
    ActionType(String asString) {
        this.asString = asString;
    }

    /**
     * Return this enum's string value.
     * @return .
     */
    public String getString() { return asString; }
}
