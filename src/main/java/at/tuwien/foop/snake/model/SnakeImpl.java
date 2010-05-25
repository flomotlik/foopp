package at.tuwien.foop.snake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Coordinates;
import at.tuwien.foop.snake.interfaces.Direction;
import at.tuwien.foop.snake.interfaces.Element;
import at.tuwien.foop.snake.interfaces.Game;
import at.tuwien.foop.snake.interfaces.Intersection;
import at.tuwien.foop.snake.interfaces.Snake;
import at.tuwien.foop.snake.interfaces.Strategy;

public class SnakeImpl implements Snake {

    private boolean alive = true;

    private List<Element> elements = new ArrayList<Element>();

    private final Game game;

    private final Client client;

    private Direction lastDirection = Direction.NONE;

    private Element ingestOnNextMove = null;

    private final Strategy strategy;

    public SnakeImpl(Coordinates coordinates, Colour colour, Game game, Client client, Strategy strategy) {
        this.elements.add(new ElementImpl(coordinates, colour));
        this.game = game;
        this.client = client;
        this.strategy = strategy;
    }

    public SnakeImpl(List<Element> elementsAfterIntersection, Strategy strategy) {
        this.elements = new ArrayList<Element>(elementsAfterIntersection);
        this.strategy = strategy;
        this.client = null;
        this.game = null;
    }

    @Override
    public Intersection cut(final Element head) {
        Element next = null;
        for (int i = 0; i < this.size(); i++) {
            next = this.elements.get(i);
            if (head.atSamePosition(next) && head != next) {
                Intersection intersection = null;
                if (this.strategy.eats(head.getColour(), next.getColour())) {
                    intersection = new IntersectionImpl(
                        new ArrayList<Element>(elements.subList(i + 1, elements.size())), next, true);
                    this.elements = new ArrayList<Element>(this.elements.subList(0, i));
                    checkAlive();
                } else {
                    intersection = new IntersectionImpl(new ArrayList<Element>(), next, true);
                }
                return intersection;

            }
        }
        return IntersectionImpl.noIntersection();
    }

    private void checkAlive() {
        this.alive = this.elements.size() > 0;
    }

    @Override
    public Iterator<Element> getElements() {
        return Collections.unmodifiableList(new ArrayList<Element>(this.elements)).iterator();
    }

    @Override
    public void ingest(Element toIngest) {
        this.ingestOnNextMove = toIngest;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public Element getHead() {
        return this.elements.get(0);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void move() {
        Direction newDirection = this.client.nextDirection();
        if (newDirection.isOpposite(this.lastDirection)) {
            newDirection = lastDirection;
        } else {
            this.lastDirection = newDirection;
        }
        Coordinates newCoordinates = this.game.nextCoordinates(this.lastDirection, newDirection, this.getHead()
            .getCoordinates());
        if (this.ingestOnNextMove != null) {
            if (this.ingestOnNextMove.getColour() == this.getHead().getColour()) {
                this.elements.remove(this.elements.size() - 1);
                checkAlive();
            } else {
                Colour colour = this.strategy.decideOnColour(this.getHead().getColour(), this.ingestOnNextMove
                    .getColour());
                this.elements.add(0, new ElementImpl(newCoordinates, colour));
            }
            this.ingestOnNextMove = null;
        } else {
            if (newCoordinates.equals(this.getHead().getCoordinates())) {
                this.elements.remove(this.elements.size() - 1);
            } else {
                Element elementToMove = null;
                Colour colourToPassOnTo = this.getHead().getColour();
                for (int i = 0; i < this.elements.size(); i++) {
                    elementToMove = this.elements.get(i);
                    this.elements.set(i, new ElementImpl(newCoordinates, colourToPassOnTo));
                    newCoordinates = elementToMove.getCoordinates();
                    colourToPassOnTo = elementToMove.getColour();
                }
            }
        }
    }
}
