package Maze.BoardObjects.Actors;

import Maze.BoardObjects.Tiles.Key;
import Maze.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * The actor that the player is controlling.
 */
public class Player extends AbstractActor{

    private Set<Key> keySet = new HashSet<>();

    /**
     * .
     * @param position .
     */
    public Player(Position position) {
        super(position);
    }


    public boolean hasKey(Key key) {
        for (Key k : keySet) {
            if(key.equals(k)) return true;
        }
        return false;
    }

    public Set<Key> getUnmodKeys(){

        Set<Key> keyCopies = new HashSet<>();

        for(Key k : keySet) {
            //Create copies of keys
        }

        return keyCopies;
    }

    public Set<Key> getKeys() {
        return keySet;
    }
}
