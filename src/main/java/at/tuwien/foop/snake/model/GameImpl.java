package at.tuwien.foop.snake.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Coordinates;
import at.tuwien.foop.snake.interfaces.Direction;
import at.tuwien.foop.snake.interfaces.Element;
import at.tuwien.foop.snake.interfaces.Game;
import at.tuwien.foop.snake.interfaces.Intersection;
import at.tuwien.foop.snake.interfaces.Snake;

public class GameImpl implements Game {

    private final List<Snake> snakes = new ArrayList<Snake>();
    private final List<Snake> deadSnakes = new ArrayList<Snake>();

    private final int heigth;
    private final int width;

    public GameImpl(int width, int heigth) {
        super();
        this.width = width;
        this.heigth = heigth;
    }

    @Override
    public Coordinates nextCoordinates(Direction lastDirection, Direction newDirection, Coordinates coordinates) {
        Direction direction = newDirection;
        if (lastDirection.isOpposite(newDirection)) {
            newDirection = lastDirection;
        }
        int newX = coordinates.getX();
        int newY = coordinates.getY();
        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
            default:
                throw new IllegalArgumentException();
        }
        newX = (newX + width) % (width);
        newY = (newY + heigth) % (heigth);
        return new CoordinatesImpl(newX, newY);
    }

    @Override
    public Colour[][] nextMove() {
        this.moveSnakes();
        this.cutSnakes();
        Element[][] computeGameBoard = this.computeGameBoard();
        Colour[][] gameBoard = new Colour[heigth][width];
        for (int x = 0; x < heigth; x++) {
            for (int y = 0; y < width; y++) {
                Element element = computeGameBoard[x][y];
                if (element != null) {
                    gameBoard[x][y] = element.getColour();
                }
            }
        }
        return gameBoard;
    }

    private void cutSnakes() {
        for (Snake snake : this.snakes) {
            if (snake.isAlive()) {
                cutAndAddToDeadSnakes(snake, this.snakes);
                cutAndAddToDeadSnakes(snake, this.deadSnakes);
            }
        }
        this.removeDeadSnakes();
    }

    private void removeDeadSnakes() {
        this.removeDeadSnakes(this.snakes.iterator());
        this.removeDeadSnakes(this.deadSnakes.iterator());
    }

    private void removeDeadSnakes(Iterator<Snake> iterator) {
        while (iterator.hasNext()) {
            Snake next = iterator.next();
            if (!next.isAlive()) {
                iterator.remove();
            }
        }

    }

    private void cutAndAddToDeadSnakes(Snake snake, List<Snake> snakesToCutWith) {
        for (Snake toCutWith : snakesToCutWith) {
            if (toCutWith.isAlive()) {
                Intersection cut = toCutWith.cut(snake.getHead());
                if (cut.intersects()) {
                    snake.ingest(cut.getIntersection());
                    if (cut.getElementsAfterIntersection().size() > 0) {
                        this.deadSnakes.add(new SnakeImpl(cut.getElementsAfterIntersection(), new StandardStrategy()));
                    }
                }
            }
        }
    }

    private void moveSnakes() {
        for (Snake snake : this.snakes) {
            snake.move();
        }
    }

    private Element[][] computeGameBoard() {
        final Element[][] board = new Element[heigth][width];
        for (Snake snake : this.snakes) {
            Iterator<Element> elements = snake.getElements();
            while (elements.hasNext()) {
                Element element = elements.next();
                Coordinates coordinates = element.getCoordinates();
                board[coordinates.getY()][coordinates.getX()] = element;
            }
        }
        return board;
    }

    @Override
    public void addSnake(Colour colour, Client client) {
        int randomWidth = new Random().nextInt(this.width);
        int randomHeight = new Random().nextInt(this.heigth);
        this.addSnake(new SnakeImpl(new CoordinatesImpl(randomWidth, randomHeight), colour, this, client,
            new StandardStrategy()));
    }
    
    @Override
    public void addSnake(Client client) {
        Colour[] values = Colour.values();
        Random random = new Random();
        Colour colour = values[random.nextInt(values.length)];
        int randomWidth = new Random().nextInt(this.width);
        int randomHeight = new Random().nextInt(this.heigth);
        this.addSnake(new SnakeImpl(new CoordinatesImpl(randomWidth, randomHeight), colour, this, client,
            new StandardStrategy()));
    }

    public void addSnake(Snake snake) {
        this.snakes.add(snake);
    }
}
