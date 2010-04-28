package at.tuwien.foop.snake;

public interface NextMove {
    public Coordinates computeNextMove(Coordinates currentCoordinates,
            Direction direction);
}
