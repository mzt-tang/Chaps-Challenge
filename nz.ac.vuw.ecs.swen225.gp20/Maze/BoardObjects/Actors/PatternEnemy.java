package Maze.BoardObjects.Actors;

import Maze.Board;
import Maze.BoardObjects.Tiles.*;
import Maze.Game;
import Maze.Position;

/**
 * An actor that follows a specific pattern/route defined by it's constructor parameter.
 */
public class PatternEnemy extends AbstractActor{

    private final char[] route;
    private int routePos = 0;

    public PatternEnemy(Position position, String routeStr) {
        super(position);
        this.route = routeStr.toCharArray();
    }

    @Override
    public void move(Player player, Board board){

        char direction = route[routePos]; //Direction of the next move;
        Position newPos;

        switch (Character.toLowerCase(direction)) {
            case 'w':
                newPos = new Position(player.getPos(), Game.DIRECTION.UP);
                break;
            case 's':
                newPos = new Position(player.getPos(), Game.DIRECTION.DOWN);
                break;
            case 'a':
                newPos = new Position(player.getPos(), Game.DIRECTION.LEFT);
                player.flipLeftImage(); //Changes the player image direction
                break;
            case 'd':
                newPos = new Position(player.getPos(), Game.DIRECTION.RIGHT);
                player.flipRightImage(); //Changes the player image direction
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


        AbstractTile tile = board.getMap()[newPos.getX()][newPos.getY()];

        //Don't move the actor into a wall or locked door.
        //Move the enemy to the new position
        if (tile instanceof Wall || (!(tile instanceof LockedDoor) || ((LockedDoor) tile).isLocked())) {
            position.setPosition(newPos);
        }

        //Interact with the player if positions are the same.
        if(player.getPos().equals(this.position)) {
            interact(player);
        }

        nextRPos(); //Move route position forward.
    }

    /**
     * The enemy "kills" the player and sends them back to their starting position.
     * @param player The player.
     */
    @Override
    public void interact(Player player) {
        player.getPos().setPosition(player.getStartingPos());
    }

    private void nextRPos(){
        if(routePos >= route.length - 1) routePos = 0;
        else routePos++;
    }

}
