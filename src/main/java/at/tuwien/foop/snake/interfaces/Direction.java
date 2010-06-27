package at.tuwien.foop.snake.interfaces;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, NONE;

    public boolean isOpposite(Direction direction) {
        switch (this) {
            case UP:
                return direction == Direction.DOWN;
            case DOWN:
                return direction == Direction.UP;
            case LEFT:
                return direction == Direction.RIGHT;
            case RIGHT:
                return direction == Direction.LEFT;
            case NONE:
                return false;
            default:
                throw new IllegalArgumentException();
        }
    }
}
