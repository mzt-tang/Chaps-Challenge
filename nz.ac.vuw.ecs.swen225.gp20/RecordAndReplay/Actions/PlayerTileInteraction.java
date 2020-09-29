package RecordAndReplay.Actions;

import Maze.BoardObjects.Tiles.*;

public class PlayerTileInteraction extends Action {
    private AbstractTile tile;
    private String tileName;

    public PlayerTileInteraction(AbstractTile tile) {
        super(ActionType.PLAYERACTION);
        this.tile = tile;

        //horrid spaghetti code for now
        if(tile instanceof ExitLock) {
            tileName = "ExitLock";
        } else if(tile instanceof ExitPortal) {
            tileName = "ExitPortal";
        } else if(tile instanceof FreeTile) {
            tileName = "FreeTile";
        } else if(tile instanceof InfoField) {
            tileName = "InfoField";
        } else if(tile instanceof Key) {
            tileName = "Key";
        } else if(tile instanceof LockedDoor) {
            tileName = "LockedDoor";
        } else if(tile instanceof Treasure) {
            tileName = "Treasure";
        } else if(tile instanceof Wall) {
            tileName = "Wall";
        }
    }

    public String getTileName() { return tileName; }
}
