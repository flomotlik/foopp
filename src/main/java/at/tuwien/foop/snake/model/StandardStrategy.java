package at.tuwien.foop.snake.model;

import java.util.HashMap;
import java.util.Map;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Strategy;

public class StandardStrategy implements Strategy {

    private Map<Colour, Map<Colour, Colour>> strategies = new HashMap<Colour, Map<Colour, Colour>>();

    private Map<Colour, Colour> eats = new HashMap<Colour, Colour>();

    public StandardStrategy() {
        final Map<Colour, Colour> redStrategy = new HashMap<Colour, Colour>();
        redStrategy.put(Colour.BLUE, Colour.RED);
        redStrategy.put(Colour.GREEN, Colour.GREEN);
        strategies.put(Colour.RED, redStrategy);

        eats.put(Colour.RED, Colour.BLUE);

        final Map<Colour, Colour> blueStrategy = new HashMap<Colour, Colour>();
        blueStrategy.put(Colour.GREEN, Colour.BLUE);
        blueStrategy.put(Colour.RED, Colour.RED);
        strategies.put(Colour.BLUE, blueStrategy);

        eats.put(Colour.BLUE, Colour.GREEN);

        final Map<Colour, Colour> greenStrategy = new HashMap<Colour, Colour>();
        greenStrategy.put(Colour.RED, Colour.GREEN);
        greenStrategy.put(Colour.BLUE, Colour.BLUE);
        strategies.put(Colour.GREEN, greenStrategy);

        eats.put(Colour.GREEN, Colour.RED);
    }

    @Override
    public Colour decideOnColour(Colour currentHead, Colour ingestedElement) {
        if (currentHead == ingestedElement) {
            return currentHead;
        }
        return this.strategies.get(currentHead).get(ingestedElement);
    }

    @Override
    public boolean eats(Colour eater, Colour toEat) {
        return this.eats.get(eater) == toEat;
    }

}
