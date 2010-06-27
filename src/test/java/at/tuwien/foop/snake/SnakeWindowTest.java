package at.tuwien.foop.snake;

import java.util.Random;

import org.mockito.Mockito;

import at.tuwien.foop.snake.client.gui.SnakeWindow;
import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;
import at.tuwien.foop.snake.model.GameImpl;

public class SnakeWindowTest {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int width = 10;
        int height = 10;
        SnakeWindow wnd = new SnakeWindow(height, width);
        wnd.setVisible(true);

        Client client = new Client() {

            private Direction[] okDirections = new Direction[] { Direction.UP,
                    Direction.DOWN, Direction.LEFT, Direction.RIGHT };

            @Override
            public Direction nextDirection() {
                int amountOfValues = okDirections.length;
                int nextInt = (new Random()).nextInt(amountOfValues);
                return okDirections[nextInt];
            }
        };
        final GameImpl impl = new GameImpl(width, height);

        for (int i = 0; i < 10; i++) {
            impl.addSnake(randomColour(), client);
        }
        while (true) {
            wnd.setGameData(0, 0, impl.nextMove());
            Thread.sleep(2000);
        }
    }

    public static Colour randomColour() {
        return Colour.values()[new Random().nextInt(Colour.values().length)];
    }
}
