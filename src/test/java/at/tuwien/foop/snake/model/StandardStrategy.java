package at.tuwien.foop.snake.model;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Strategy;

public class StandardStrategy implements Strategy {

    @Override
    public Colour decideOnColour(Colour currentHead, Colour ingestedElement) {
        if (currentHead == ingestedElement) {
            return currentHead;
        }

        switch (currentHead) {
            case RED:
                return redStrategy(ingestedElement);
            default:
                throw new IllegalArgumentException();
        }
    }

    private Colour redStrategy(Colour ingestedelement) {
        switch (ingestedelement) {
            case BLUE:
                return Colour.RED;
            case GREEN:
                return Colour.GREEN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
