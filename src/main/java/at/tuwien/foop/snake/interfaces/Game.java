package at.tuwien.foop.snake.interfaces;

public interface Game {

    public Coordinates nextCoordinates(Direction lastDirection, Direction newDirection, Coordinates coordinates);

}
