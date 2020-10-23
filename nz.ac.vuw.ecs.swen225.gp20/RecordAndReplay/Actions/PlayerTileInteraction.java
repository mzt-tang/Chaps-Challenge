package RecordAndReplay.Actions;

import Maze.BoardObjects.Tiles.*;

/**
 * The action type of the player interacting with a tile.
 *
 * @author Jeremiah Choi 300474835
 */
public class PlayerTileInteraction extends Action {
    private AbstractTile tile;
    private String tileName;

    /**
     * Basic constructor.
     * @param tile The tile which the player is interacting with.
     */
    public PlayerTileInteraction(AbstractTile tile) {
        super(ActionType.PLAYERACTION);
        this.tile = tile;

        if(tile instanceof ExitLock) {
            tileName = "ExitLock";
        } else if(tile instanceof ExitPortal) {
            tileName = "ExitPortal";
        } else if(tile instanceof FreeTile) {
            tileName = "FreeTile";
        } else if(tile instanceof InfoField) {
            tileName = "InfoField";
        } else if(tile instanceof Key) {
            tileName = "Key"; //might have to add a little more, like "colour", if need be
        } else if(tile instanceof LockedDoor) {
            tileName = "LockedDoor";
        } else if(tile instanceof Treasure) {
            tileName = "Treasure";
        } else if(tile instanceof Wall) {
            tileName = "Wall"; //
        }
    }

    /**
     * Get this tile's name.
     * @return .
     */
    public String getTileName() { return tileName; }
}
