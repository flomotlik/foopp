package at.tuwien.foop.snake.interfaces;

public interface NextMove {
    public Coordinates computeNextMove(Coordinates currentCoordinates,
            Direction direction);
}
