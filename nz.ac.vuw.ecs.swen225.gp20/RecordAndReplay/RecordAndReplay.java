package RecordAndReplay;

import Application.ChapsChallenge;
import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.Game.DIRECTION;
import Maze.Position;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * Houses all the methods that will be called by Application to Record and Replay gameplay.
 */
public class RecordAndReplay<E> {
    private int level; //the Level the game is associated with. It's a string because that's how persistence works.
    private Recorder recorder;
    private Writer writer;
    private Replayer replayer;
    private Reader reader;
    private boolean recordingSwitch;
    private int startedRecording;
    private ArrayList<AbstractActor> enemies;

    /**
     * Parameterless constructor.
     */
    public RecordAndReplay(ChapsChallenge application) {
        recorder = new Recorder();
        writer = new Writer();
        replayer = new Replayer(application);
        reader = new Reader();
        recordingSwitch = false;
    }

    //ALL RECORDING METHODS:

    /**
     * Return's true if the game is recording. False otherwise
     * @return .
     */
    public boolean getRecordingBoolean() {
        return recordingSwitch;
    }

    /**
     * Toggle the state of RecordingSwtich.
     * @param s True if the game is recording, false otherwise.
     */
    public void setRecordingBoolean(Boolean s) {
        recordingSwitch = s;
    }

    /**
     * Set the starting position of the player.
     * @param pos Starting posistion of the player.
     */
    public void setStartingPosition(Position pos) {
        recorder.setStartingPosition(pos);
    }

    /**
     * Capture the Player's movement in a specific direction.
     * @param direction .
     */
    public void capturePlayerMove(DIRECTION direction) {
        recorder.capturePlayerMove(direction);
    }

    /**
     * Capture the player's interaction with tiles.
     * DEPRECIATED.
     * @param tile .
     */
    public void captureTileInteraction(AbstractTile tile) {
        recorder.captureTileInteraction(tile);
    }

    /**
     * Capture enemy's positions before a movement.
     * Do this right before their position is updated.
     * @param enemies Set of all enemies on the map.
     */
    public void captureEnemyPreMoves(Set<AbstractActor> enemies) {
        recorder.captureEnemyPreMoves(enemies);
    }

    /**
     * Capture enemy's positions after a movement.
     * Do this right after their positions are updated.
     * @param enemies Set of all enemies on the map.
     */
    public void captureEnemyPostMoves(Set<AbstractActor> enemies) {
        recorder.captureEnemyPostMoves(enemies);
    }

    /**
     * Mark down the time where recording has started.
     * @param timestamp The game's time remaining.
     */
    public void setStartedRecording(int timestamp) {
        startedRecording = timestamp;
    }

    /**
     * Clear Recording buffer.
     * Call this after an action (or multiple simultaneous actions)
     * have been taken.
     * @param timestamp The game's time remaining.
     */
    public void clearRecorderBuffer(int timestamp) {
        //deletes the recording buffer if it shouldnt be recording.
        if(recordingSwitch) recorder.storeBuffer(timestamp);
        else recorder.deleteBuffer();
    }

    //ALL SAVING AND WRITING METHODS:

    /**
     * Write the recorded gameplay so far into a Json file.
     * @param remainingTime The game's time remaining at the end of a recording session.
     * @param player .
     * @param tiles The map.
     */
    public void saveGameplay(int remainingTime, Player player, AbstractTile[][] tiles) {
        ArrayList<AbstractActor> enemyList = new ArrayList<AbstractActor>();
        for(AbstractActor e : enemies) {
            enemyList.add(e);
        };

        writer.writeRecording(recorder.getRecordedChanges(), recorder.getStartingPosition(), level, startedRecording, enemyList);
    }

    //ALL LOADING METHODS:

    /**
     * Display a confirmation frame for loading the game.
     * @param frame Application's main frame.
     * @return .
     */
    public boolean loadConfirmation(JFrame frame) {
        int selection = JOptionPane.showConfirmDialog(frame, "WARNING: Loading a replay will quit out of your current game.\n" +
                "Proceed?", "Load Replay Confirmation", JOptionPane.YES_NO_OPTION);
        if(selection == 0) return true;
        else return false;
    }

    /**
     * Allows the player to select a save file and immediately loads it upon selection.
     * @param frame The parent frame for the dialog box.
     */
    public void selectSaveFile(JFrame frame) {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir") + "/SavedReplay");

        int returnValue = jfc.showOpenDialog(frame);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedSaveFile = jfc.getSelectedFile();

            reader.readJson(selectedSaveFile);
            prepReplayer();
        }
    }

    //ALL REPLAYING METHODS:

    /**
     * Prepare the Replayer object with variables set from the reader.
     */
    public void prepReplayer() {
        replayer.setRecordedChanges(reader.getRecordedChanges());
        replayer.setLevel(reader.getLevel());
        replayer.setLoadState(reader.getLevel());
        replayer.setEnemyStartPos(reader.getEnemies());

        replayer.prepRecordedChanges();
        replayer.loadToStart();
    }

    /**
     * Presses Replayer's "next button" automatically.
     */
    public void tick() {
        replayer.tick();
    }

    /**
     * Display the Replayer's control window.
     */
    public void displayControlWindow() {
        replayer.controlsWindow();
    }

    /**
     * Check if the replayer is currently paused.
     * @return .
     */
    public boolean getPaused() {
        return replayer.isPaused();
    }

    //SETTERS:

    /**
     * Set the name of the json file this recorded session will be associated with.
     * @param level .
     */
    public void setLevelCount(int level) {
        this.level = level;
    }

    /**
     * Set the arraylist of enemies that exist in this game.
     * Only useful for the writing module
     * @param enemies The set of enemies
     */
    public void setEnemies(Set<AbstractActor> enemies) {
        ArrayList<AbstractActor> e = new ArrayList<AbstractActor>();
        for(AbstractActor a : enemies) {
            e.add(a);
        }
        this.enemies = e;
    }
}
