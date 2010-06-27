package at.tuwien.foop.snake.model;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Coordinates;
import at.tuwien.foop.snake.interfaces.Element;

public class ElementImpl implements Element {

    private Colour colour;

    private Coordinates coordinates;

    public ElementImpl(Coordinates coordinates, Colour colour) {
        super();
        this.colour = colour;
        this.coordinates = coordinates;
    }

    @Override
    public Colour getColour() {
        return this.colour;
    }

    @Override
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    @Override
    public boolean atSamePosition(Element element) {
        return this.coordinates.equals(element.getCoordinates());
    }
}
