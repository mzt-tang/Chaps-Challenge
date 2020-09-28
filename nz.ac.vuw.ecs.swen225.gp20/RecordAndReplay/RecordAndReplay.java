package RecordAndReplay;

import Maze.Board;
import Maze.Game.DIRECTION;

import java.util.ArrayList;
import java.util.List;

/**
 * Records gameplay.
 * + Stores recorded games in a JSON format file.
 *   - Chap's movement
 *   - Any and every other actors
 * + Can also load a recorded game and replay it.
 * + User should have controls for replay:
 *   - Step-by-step,      -(ASSUMPTION)iterate through every action
 *                        -(ASSUMPTION)iterate through every tick
 *                        -(ASSUMPTION)Player can move forward and backward through the recording.
 *   - auto-reply,        -(ASSUMPTION)Play through normal tick speed
 *   - set replay speed.  -(ASSUMPTION)Set tick speed depending on seconds.
 * + (ASSUMPTION) Player CANNOT undo or redo.
 *
 *
 * INDEX:
 * > RECORDING
 * > SAVING
 * > LOADING
 * > PLAYING
 * > GETTERS/SETTERS
 */
public class RecordAndReplay<E> {
    private Board board; //The board (level) that this record is associated with.//Change later...
    private List<ArrayList<Action>> recordedChanges; //ArrayList chosen instead of stack, so you can go back and fourth n shit. ORDER IS IMPORTANT!!!

    /**
     * Creates a RecordAndReplay object associated with a board.
     * Ideally created whenever a new level is loaded.
     *
     * CURRENT ASSUMPTION: each level will have it's own RecordAndReplay object. Might need updates later.
     *                     The RecordAndReplay will ideally be called in the Game class BUT it should remember the board and the enemies/players within instead.
     * NOTE TO SELF: Change later if the object is associated with the GAME instead.
     *
     * @param board The Board object which this level is associated with
     */
    public RecordAndReplay(Board board) {
        this.board = board;
        recordedChanges = new ArrayList<ArrayList<Action>>();
    }

    /**
     * Parameterless constructor. (might not even need. delete later...)
     */
    public RecordAndReplay() {
        recordedChanges = new ArrayList<ArrayList<Action>>();
    }

    //=====RUNNING=====//
    //All functions that have to do with recording every move during the game.

    /**
     * Record a singular change. Ideally done if it's only ONE actor moving. IE: player.
     */
    public <E> void recordChange(E action) {
        //FIRST, find out what kinda move it is (REMINDER: you may need to create a method for every kinda move if the others refuse
                                                           //to implement an interface into their design.
        Action a = new Action();
        //Eugh, brute force...
        if(action instanceof Maze.Game.DIRECTION) {
            //SECOND, create an Action object using the input

            Maze.Game.DIRECTION direction = (DIRECTION) action;

        }

        //THIRD, add the singular action to it's own array
        ArrayList<Action> addThis = new ArrayList<Action>();
        addThis.add(a);
        recordedChanges.add(addThis);
    }

    /**
     * There may be multiple changes at one moment.
     * This is one way of recording them all: (Delete if no one likes it)
     * If multiple actors have to move at the same time, add the array of "actions" here.
     *
     * @Param changes ArrayList of Actions that change the enemy positions, player positions or the board.
     */
    public void recordChanges(ArrayList<Action> changes) {
    }

    /**
     * There may be multiple changes at one moment.
     * This is a second way of recording them all: (Delete if no one likes it)
     * If multiple actors have to move at the same time, put this in the same loop that update's their positions.
     * Just be sure to call "cacheBuffer" once the loop is completed.
     */
    public <E> void addChangeToBuffer(E action) {

    }

    /**
     * Clear the buffer of changes, but also record them as a singular action array.
     * DONT FORGET TO CALL THIS!!!!
     */
    public void cacheBuffer() {

    }

    /**
     * Have no idea application or maze will implement the moves of the player or actors
     * Just in case, heres a reminder for yourself
     */
    //public void recordPlayerMove() {}
    //public void recordActorMove() {}

    //=====SAVING=====//
    //All functions to do with creating a save via JSON is here.

    //=====LOADING=====//

    //=====PLAYING=====//
    //All functions to do with replaying, forward or backwards.

    //=====GETTERS/SETTERS=====//
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

}
