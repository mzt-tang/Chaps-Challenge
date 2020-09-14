package Maze.BoardObjects.Actors;

import Maze.BoardObjects.Tiles.Key;
import Maze.BoardObjects.Tiles.Treasure;
import Maze.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * The actor that the player is controlling.
 */
public class Player extends AbstractActor{

    private Set<Key> keySet = new HashSet<>(); //Keys that the player has picked up.
    private Set<Treasure> treasureSet; //The treasures that the player needs to collect.

    /**
     * .
     * @param position .
     */
    public Player(Position position, Set<Treasure> treasures) {
        super(position);
        treasureSet = treasures;
    }

    public boolean treasuresAllCollected(){
        for(Treasure t : treasureSet) {
            if(!t.isCollected()) return false;
        }
        return true;
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
