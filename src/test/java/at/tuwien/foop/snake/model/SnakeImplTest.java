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

public class SnakeImplTest {

    private Game game = Mockito.mock(Game.class);
    private Client client = Mockito.mock(Client.class);

    public SnakeImplTest() {
        Mockito.when(
            game.nextCoordinates(Mockito.any(Direction.class), Mockito.any(Direction.class), Mockito
                .any(Coordinates.class))).thenReturn(new CoordinatesImpl(2, 2));

        Mockito.when(client.nextDirection()).thenReturn(Direction.UP);
    }

    @Test
    public void ingestByRedSnake() {
        final Snake snake = new SnakeImpl(new CoordinatesImpl(1, 1), Colour.RED, game, client);
        snake.ingest(new ElementImpl(new CoordinatesImpl(1, 1), Colour.BLUE));

        Iterator<Element> elements = snake.getElements();
        checkElement(elements.next(), Colour.RED, 1, 1);
        Assert.assertFalse(elements.hasNext());

        snake.move();
        elements = snake.getElements();
        checkElement(elements.next(), Colour.RED, 2, 2);
        checkElement(elements.next(), Colour.RED, 1, 1);
        Assert.assertFalse(elements.hasNext());
    }

    @Test
    public void testMove() {
        final Snake snake = new SnakeImpl(new CoordinatesImpl(1, 1), Colour.BLUE, game, client);
        snake.move();
    }

    private void checkElement(final Element element, Colour colour, int x, int y) {
        Assert.assertEquals(colour, element.getColour());
        Assert.assertEquals(new CoordinatesImpl(x, y), element.getCoordinates());
    }
}
