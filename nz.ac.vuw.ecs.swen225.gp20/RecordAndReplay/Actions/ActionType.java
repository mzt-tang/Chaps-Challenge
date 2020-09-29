package RecordAndReplay.Actions;

import RecordAndReplay.RecordAndReplay;

/**
 * Just to avoid spaghetti code.
 * This is the list of changes that can occur on the level.
 * A player could move, a creature could move, a map could change.
 *
 * An "actiontype" is an action that can spark multiple functions (ie: checks to see if creature attacks player)
 *
 */
public enum ActionType {
    PLAYERACTION("playeraction"), CREATUREACTION("creatureaction"), MAPACTION("mapaction");

    //NOTE TO SELF: an action type will have a list of variables on what happens.
    //              We will decide this later, depending on how the game will control and feel.
    //              ATM, focus on recording, writing, reading, replaying.
    private String asString;
    ActionType(String asString) {
        this.asString = asString; //to make it easier to write n stuff
    }
    public String getString() { return asString; }
}
