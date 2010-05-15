package at.tuwien.foop.snake.model;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Coordinates;
import at.tuwien.foop.snake.interfaces.Direction;
import at.tuwien.foop.snake.interfaces.Element;
import at.tuwien.foop.snake.interfaces.Game;
import at.tuwien.foop.snake.interfaces.Snake;
import at.tuwien.foop.snake.interfaces.Strategy;

public class SnakeImplTest {

    private Game game = Mockito.mock(Game.class);
    private Client client = Mockito.mock(Client.class);
    private Strategy strategy = new StandardStrategy();

    public SnakeImplTest() {
        Mockito.when(
            game.nextCoordinates(Mockito.any(Direction.class), Mockito.any(Direction.class), Mockito
                .any(Coordinates.class))).thenReturn(new CoordinatesImpl(2, 2), new CoordinatesImpl(3, 3),
            new CoordinatesImpl(4, 4), new CoordinatesImpl(5, 5), new CoordinatesImpl(6, 6));

        Mockito.when(client.nextDirection()).thenReturn(Direction.UP);
    }

    @Test
    public void ingestByRedSnake() {
        this.ingestBySnake(Colour.RED, Colour.BLUE, Colour.GREEN);
    }

    @Test
    public void ingestByBlueSnake() {
        this.ingestBySnake(Colour.BLUE, Colour.GREEN, Colour.RED);
    }

    @Test
    public void ingestByGreenSnake() {
        this.ingestBySnake(Colour.GREEN, Colour.RED, Colour.BLUE);
    }

    private void ingestBySnake(Colour primary, Colour toEat, Colour toPassOnTo) {
        System.out.println("Check for " + primary + "-----------------------");
        final Snake snake = new SnakeImpl(new CoordinatesImpl(1, 1), primary, game, client, strategy);
        snake.ingest(new ElementImpl(new CoordinatesImpl(1, 1), toEat));

        checkColour(snake.getElements(), primary);

        snake.move();
        checkColour(snake.getElements(), primary, primary);

        snake.ingest(new ElementImpl(new CoordinatesImpl(0, 0), primary));
        snake.move();

        checkColour(snake.getElements(), primary);

        snake.ingest(new ElementImpl(new CoordinatesImpl(1, 1), toPassOnTo));
        snake.move();

        checkColour(snake.getElements(), toPassOnTo, primary);
        snake.move();
        checkColour(snake.getElements(), toPassOnTo, toPassOnTo);
    }

    @Test
    public void testMove() {
        final Snake snake = new SnakeImpl(new CoordinatesImpl(1, 1), Colour.BLUE, game, client, strategy);
        snake.move();
    }

    private void checkColour(final Iterator<Element> elements, Colour... colours) {
        for (Colour colour : colours) {
            Colour colourOfNextElement = elements.next().getColour();
            System.out.print(colour + "/" + colourOfNextElement + " ");
            Assert.assertEquals(colour, colourOfNextElement);
        }
        System.out.print(elements.hasNext() + "\n");

        Assert.assertFalse(elements.hasNext());
    }
}
