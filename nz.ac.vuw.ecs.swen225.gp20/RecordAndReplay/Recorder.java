package RecordAndReplay;

import Maze.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * This handles all the recording that needs to take place in the game.
 * It's a "recorder" haha get it???
 * Also, it should be able to report "recordedChanges" to RecordAndReplay,
 * So that RecordAndReplay can pass it to "writer".
 *
 * NOTE: ONLY RecordAndReplay.java should have access to this!!!
 *
 */
public class Recorder {
    private List<ArrayList<Action>> recordedChanges; //ArrayList chosen instead of stack, so you can go back and fourth n shit. ORDER IS IMPORTANT!!!
    private ArrayList<Action> changeBuffer;

    public Recorder() {
        recordedChanges = new ArrayList<ArrayList<Action>>();
        changeBuffer = new ArrayList<Action>();
    }

    /**LIST OF RECORD ACTIONS!!!!**/
    /**
     * Records a player movement and stores it in the buffer
     */
    public void recordPlayerMove() {
        //FIRST, create new action.
    }
    /**
     * Records a creature movement and stores it in the buffer.
     */
    public void recordCreatureMove() {
        //FIRST, create

        //Find's the creature at (x, y) and moves it in a direction.
    }
    /**
     * Record a tile's state changing.
     */
    public void recordTileChange() {
        //derp
    }
    //Add move as needed
    /**
     * Clears the buffer, stores it into the recordedChanges array.
     * Call this often!!!
     */
    public void storeBuffer() {

    }

    /**
     * Record a singular change. Ideally done if it's only ONE actor moving.
     */
    /*public <E> void recordChange(E action) {
        //FIRST, find out what kinda move it is (REMINDER: you may need to create a method for every kinda move if the others refuse
        //to implement an interface into their design.
        Action a = new Action();
        //Eugh, brute force...
        if(action instanceof Maze.Game.DIRECTION) {
            //SECOND, create an Action object using the input

            Maze.Game.DIRECTION direction = (Game.DIRECTION) action;

        }

        //THIRD, add the singular action to it's own array
        ArrayList<Action> addThis = new ArrayList<Action>();
        addThis.add(a);
        recordedChanges.add(addThis);
    }*/
}
