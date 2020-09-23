package Maze;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.*;

import java.util.Set;

public class Game {

    public enum DIRECTION {
        UP, DOWN, LEFT, RIGHT;
    }

    private Board board;
    private Player player;
    private Set<AbstractActor> computerPlayers;

    public Game(Board board, Player player, Set<AbstractActor> computerPlayers) {
        //GUI calls the persistence and sends the Game object the necessary files
        this.board = board;
        this.player = player;
        this.computerPlayers = computerPlayers;

        //Temporary board for testing
        AbstractTile[][] testMaze = board.getMap();
        for (int i = 0; i < testMaze.length; i++) {
            for (int j = 0; j < testMaze[0].length; j++) {
                testMaze[i][j] = new FreeTile(new Position(i, j));
            }
        }
    }

    public void moveEnemyCheckWin() {
        for(AbstractActor c : computerPlayers) {
            c.move();
        }
    }

    /**
     * The player interacts with the block of the direction of their position.
     * The player moves in that direction if possible.
     * @param direction The direction of the position that the player's
     *                 current position is interacting with/moving towards.
     */
    public void movePlayer(DIRECTION direction) {
        Position newPos;
        switch (direction) {
            case UP:
                newPos = new Position(player.getPos(), DIRECTION.UP);
                break;
            case DOWN:
                newPos = new Position(player.getPos(), DIRECTION.DOWN);
                break;
            case LEFT:
                newPos = new Position(player.getPos(), DIRECTION.LEFT);
                break;
            case RIGHT:
                newPos = new Position(player.getPos(), DIRECTION.RIGHT);
                break;
            default:
                throw new IllegalStateException("Unexpected direction: " + direction);
        }
        assert (newPos.getX() >= 0 &&
                newPos.getX() <= board.getMap().length - 1 &&
                newPos.getY() >= 0 &&
                newPos.getY() <= board.getMap()[0].length - 1)
                : "New position is out of bounds.";
        assert (board.getMap()[newPos.getX()][newPos.getY()] != null)
                : "Position at array is null. If you're here then something really bad happened...";

        //CHECK IF MOVING INTO ENEMY

        //Interact with the square and move there if possible.
        AbstractTile moveToTile = board.getMap()[newPos.getX()][newPos.getY()];
        if(moveToTile.interact(player)) {
            //Lets the player pickup the any Treasure or Key
            if (moveToTile instanceof Treasure) {
                player.getTreasures().add((Treasure) moveToTile);
                if (allTreasuresCollected()) unlockExitLock();
            } else if (moveToTile instanceof Key) {
                player.getKeys().add((Key) moveToTile);
            }
            //The tile doesn't need to be replaced if it's an info tile or already a free tile
            if(!(moveToTile instanceof InfoField) && !(moveToTile instanceof FreeTile)) {
                board.getMap()[newPos.getX()][newPos.getY()] = new FreeTile(newPos);
            }
            player.getPos().move(direction);    //Move the player
        }

    }

    private void unlockExitLock() {
        for (int i = 0; i < board.getMap().length; i++) {
            for (int j = 0; j < board.getMap()[0].length; j++) {
                if(board.getMap()[i][j] instanceof ExitLock){
                    board.getMap()[i][j] = new FreeTile(board.getMap()[i][j].position);
                }
            }
        }
    }

    private boolean allTreasuresCollected() {
        for (int i = 0; i < board.getMap().length; i++) {
            for (int j = 0; j < board.getMap()[0].length; j++) {
                if(board.getMap()[i][j] instanceof Treasure) return false;
            }
        }
        return true;
    }

}
