package at.tuwien.foop.snake.model;

import java.util.HashMap;
import java.util.Map;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Strategy;

public class StandardStrategy implements Strategy {

    private Map<Colour, Map<Colour, Colour>> strategies = new HashMap<Colour, Map<Colour, Colour>>();

    public StandardStrategy() {
        final Map<Colour, Colour> redStrategy = new HashMap<Colour, Colour>();
        redStrategy.put(Colour.BLUE, Colour.RED);
        redStrategy.put(Colour.GREEN, Colour.GREEN);
        strategies.put(Colour.RED, redStrategy);

        final Map<Colour, Colour> blueStrategy = new HashMap<Colour, Colour>();
        blueStrategy.put(Colour.GREEN, Colour.BLUE);
        blueStrategy.put(Colour.RED, Colour.RED);
        strategies.put(Colour.BLUE, blueStrategy);

        final Map<Colour, Colour> greenStrategy = new HashMap<Colour, Colour>();
        greenStrategy.put(Colour.RED, Colour.GREEN);
        greenStrategy.put(Colour.BLUE, Colour.BLUE);
        strategies.put(Colour.GREEN, greenStrategy);
    }

    @Override
    public Colour decideOnColour(Colour currentHead, Colour ingestedElement) {
        if (currentHead == ingestedElement) {
            return currentHead;
        }
        return this.strategies.get(currentHead).get(ingestedElement);
    }
}
