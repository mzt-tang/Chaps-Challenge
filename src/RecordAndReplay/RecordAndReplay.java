package RecordAndReplay;

import Maze.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Records gameplay.
 * + Stores recorded games in a JSON format file.
 *   - Chap's movement
 *   - Any and every other actors
 * + Can also load a recorded game and replay it.
 * + User should have controls for replay:
 *   - Step-by-step,
 *   - auto-reply,
 *   - set replay speed.
 *
 * INDEX:
 * > RUNNING
 * > SAVING
 * > LOADING
 */
public class RecordAndReplay {
    private Board board; //The board (level) that this record is associated with.//Change later...
    private List<ArrayList<Action>> recordedChanges; //ArrayList chosen instead of stack, so you can go back and fourth n shit. ORDER IS IMPORTANT!!!

    public RecordAndReplay(Board board) {
        this.board = board;
    }

    //=====RUNNING=====//
    //All functions that have to do with recording every move during the game.

    /**
     *
     */
    public void recordChange(ArrayList<Action> changes) {
        //FIRST, find out what kinda move it is (REMINDER: you may need to create a method for every kinda move if the others refuse
                                                           //to implement an interface into their design.
        //
    }

    /**
     * There may be multiple changes at one moment
     * If multiple actors have to move at the same time, add the array of "actions" here.
     *
     * @Param
     */
    public void recordChanges(ArrayList<Action> changes) {

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

}
