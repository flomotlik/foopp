package at.tuwien.foop.snake.interfaces;

public interface Element {
    public Coordinates getCoordinates();

    public Colour getColour();

    public boolean atSamePosition(Element element);
}
