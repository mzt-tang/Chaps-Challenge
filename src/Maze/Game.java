package Maze;

import Maze.BoardObjects.Actors.AbstractActor;
import Maze.BoardObjects.Actors.Player;
import Maze.BoardObjects.Tiles.AbstractTile;
import Maze.BoardObjects.Tiles.FreeTile;

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
        for(int i = 0; i < testMaze.length; i++) {
            for(int j = 0; j < testMaze[0].length; j++){
                testMaze[i][j] = new FreeTile(new Position(i, j), null);
            }
        }
    }

    public void movePlayer(DIRECTION direction){
        switch (direction) {
            case UP -> {
                Position newPos = new Position(player.getPos(), DIRECTION.UP);
                assert (newPos.getX() >= 0 &&
                        newPos.getX() <= board.getMap().length-1 &&
                        newPos.getY() >= 0 &&
                        newPos.getY() <= board.getMap()[0].length-1)
                        : "New position is out of bounds.";
            }
            case DOWN -> {

            }
            case LEFT -> {

            }
            case RIGHT -> {

            }
        }
    }

}
