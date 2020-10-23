package RecordAndReplay;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.BoardObjects.Tiles.Key;
import Maze.BoardObjects.Tiles.Treasure;
import Maze.Game;
import Maze.Position;
import RecordAndReplay.Actions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This handles all the recording that needs to take place in the game.
 */
public class Recorder {
    private List<Change> recordedChanges;
    private ArrayList<Action> changeBuffer;
    private Position startingPosition;
    private ArrayList<Position> preMoveEnemies = new ArrayList<Position>();
    private ArrayList<Position> postMoveEnemies = new ArrayList<Position>();

    private List<Key> playerKeySet = new ArrayList<Key>();
    private Set<Treasure> playerTreasureSet = new HashSet<Treasure>();

    /**
     * Basic constructor.
     */
    public Recorder() {
        recordedChanges = new ArrayList<Change>();
        changeBuffer = new ArrayList<Action>();
    }

    /**
     * Records a player movement and stores it in the buffer
     */
    public void capturePlayerMove(Game.DIRECTION direction) {
        Action addthis = new PlayerMove(direction);
        changeBuffer.add(addthis);
    }

    /**
     * Records a player's interaction a tile and stores it in the buffer.
     * @param tile
     */
    public void captureTileInteraction (AbstractTile tile) {
        Action addthis = new PlayerTileInteraction(tile);
        changeBuffer.add(addthis);
    }

    /**
     * Captures enemy locations before a move.
     * @param enemies All enemies on the map.
     */
    public void captureEnemyPreMoves(Set<AbstractActor> enemies) {
        for(AbstractActor e: enemies) {
            Position p = new Position(e.getPos().getX(), e.getPos().getY());
            preMoveEnemies.add(p);
        }
    }

    /**
     * Captures enemy locations after a move and calculates the direction using
     * enemy's previous locations from captureEnemyPreMoves.
     * @param enemies All enemies on the map.
     */
    public void captureEnemyPostMoves(Set<AbstractActor> enemies) {
        for(AbstractActor e: enemies) {
            Position p = new Position(e.getPos().getX(), e.getPos().getY());
            postMoveEnemies.add(p);
        }

        for(int i = 0; i < preMoveEnemies.size(); i++) {
            //===Calculate directions here===//
            Game.DIRECTION direction = null;
            //FIRST find the positions
            Position preMovePos = preMoveEnemies.get(i);

            int preMoveX = preMovePos.getX();
            int preMoveY = preMovePos.getY();

            Position postMovePos = postMoveEnemies.get(i);
            int postMoveX = postMovePos.getX();
            int postMoveY = postMovePos.getY();

            //SECOND Brute force IF statements
            if(postMoveX < preMoveX) {
                direction = Game.DIRECTION.LEFT;
                changeBuffer.add(new EnemyMove(preMoveX, preMoveY, direction));
            } else if (preMoveX < postMoveX) {
                direction = Game.DIRECTION.RIGHT;
                changeBuffer.add(new EnemyMove(preMoveX, preMoveY, direction));
            } else if (postMoveY < preMoveY) {
                direction = Game.DIRECTION.UP;
                changeBuffer.add(new EnemyMove(preMoveX, preMoveY, direction));
            } else if (preMoveY < postMoveY) {
                direction = Game.DIRECTION.DOWN;
                changeBuffer.add(new EnemyMove(preMoveX, preMoveY, direction));
            }
        }
        preMoveEnemies.clear();
        postMoveEnemies.clear();
    }

    /**
     * Converts everything stored in the buffer into Action objects.
     * Clears the current buffer and stores all action objects in a separate array.
     *
     * @param timestamp The time remaining
     */
    public void storeBuffer(int timestamp) {
        if(changeBuffer.size() != 0) {
            Change c = new Change(changeBuffer, timestamp);

            recordedChanges.add(c);
            changeBuffer = new ArrayList<Action>();
        }
    }

    /**
     * Clears the buffer.
     */
    public void deleteBuffer() {
        changeBuffer = new ArrayList<Action>();
    }

    /**
     * Set the player's starting position.
     * @param pos .
     */
    public void setStartingPosition(Position pos) {
        startingPosition = pos;
    }

    /**
     * Get the player's currently set starting position.
     * @return .
     */
    public Position getStartingPosition() {
        return startingPosition;
    }

    /**
     * Get the list of gamplay changes that are stored in the recorder.
     * @return .
     */
    public List<Change> getRecordedChanges() {
        return recordedChanges;
    }

    /**
     * Private nested class object stores both the list of actions
     * AND a time stamp to associate itself with.
     */
    public static class Change {
        public final ArrayList<Action> actions;
        public final int timestamp;

        public Change(ArrayList<Action> actions, int timestamp) {
            this.actions = actions;
            this.timestamp = timestamp;
        }
    }
}
