package Maze;

public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position, Game.DIRECTION direction) {
        switch (direction) {
            case UP -> {
                this.x = position.getX();
                this.y = position.getY()-1;
            } case DOWN -> {
                this.x = position.getX();
                this.y = position.getY()+1;
            } case LEFT -> {
                this.x = position.getX()-1;
                this.y = position.getY();
            } case RIGHT -> {
                this.x = position.getX()+1;
                this.y = position.getY();
            }
        }
    }

    public void move(Game.DIRECTION direction) {
        switch (direction) {
            case UP -> {
                y--;
            }
            case DOWN -> {
                y++;
            }
            case LEFT -> {
                x--;
            }
            case RIGHT -> {
                x++;
            }
            default -> throw new IllegalStateException("Unexpected direction: " + direction);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
