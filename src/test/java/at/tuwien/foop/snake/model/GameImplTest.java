package at.tuwien.foop.snake.model;

import org.junit.Test;
import org.mockito.Mockito;

import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;

public class GameImplTest {
    @Test
    public void testComputeCurrentColourBoard() {
        Client mock = Mockito.mock(Client.class);
        Mockito.when(mock.nextDirection()).thenReturn(Direction.DOWN);
        final GameImpl impl = new GameImpl(3, 6);
        impl.addSnake(Colour.RED, mock);
        impl.addSnake(Colour.BLUE, mock);
        impl.addSnake(Colour.GREEN, mock);
        printBoard(impl.nextMove());
        printBoard(impl.nextMove());
        printBoard(impl.nextMove());
    }

    @Test
    public void testCut() {
        Client mock = Mockito.mock(Client.class);
        Mockito.when(mock.nextDirection()).thenReturn(Direction.RIGHT, Direction.LEFT, Direction.RIGHT, Direction.LEFT);
        final GameImpl impl = new GameImpl(5, 1);
        StandardStrategy strategy = new StandardStrategy();
        impl.addSnake(new SnakeImpl(new CoordinatesImpl(0, 0), Colour.RED, impl, mock, strategy));
        impl.addSnake(new SnakeImpl(new CoordinatesImpl(4, 0), Colour.BLUE, impl, mock, strategy));
        printBoard(impl.nextMove());
        printBoard(impl.nextMove());
        printBoard(impl.nextMove());
        printBoard(impl.nextMove());
    }

    private void printBoard(Colour[][] computeCurrentColourBoard) {
        System.out.println(" Size: " + computeCurrentColourBoard.length + "------------------------------------------");
        for (Colour[] colours : computeCurrentColourBoard) {
            System.out.print("Size: " + computeCurrentColourBoard.length + "|");
            for (Colour colour : colours) {
                if (colour == null) {
                    System.out.print("    ");
                } else {
                    System.out.print(colour);
                }
                System.out.print("|");
            }
            System.out.println("");
        }
        System.out.println("------------------------------------------");
    }
}
