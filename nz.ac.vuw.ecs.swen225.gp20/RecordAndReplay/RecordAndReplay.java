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
 * ////////////////////////////////////////////////////////////////
 * Player can move using arrow keys only.
 * Then it checks if the player used item.
 * Then it checks if the player has interacted with any creatures or items.
 *
 * On a completely separate tick cycle, enemies will move on their own.
 * Every move, it checks if they have interacted with player.
 *
 * Multiple moves (aka changes) can happen at once.
 * Every change should be an array of changes.
 * The recording should be an array, of the array of changes.
 *
 * NOTE: each playermovement has a "direction" enum... might have to use.
 *       I don't think a creature can move yet...
 * ////////////////////////////////////////////////////////////////
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
    private Recorder recorder;

    /**
     * Creates a RecordAndReplay object associated with a board.
     * Ideally created whenever a new level is loaded.
     *
     * CURRENT ASSUMPTION: RecordAndReplay object is associated with the game.
     *                      Each will contain it's own board.
     *
     * @param board The Board object which this level is associated with
     */
    public RecordAndReplay(Board board) {
        this.board = board;
        this.recorder = new Recorder();
    }

    /**
     * Parameterless constructor. (might not even need. delete later...)
     */
    public RecordAndReplay() {
        this.recorder = new Recorder();
    }

    //=====RECORDER=====//
    //Effectively relays all the recorder's functions here. Doing this to save me from headache.




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
