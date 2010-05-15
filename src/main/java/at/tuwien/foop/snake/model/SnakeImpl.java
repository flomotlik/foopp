package at.tuwien.foop.snake.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    private boolean dead;

    private final List<ElementImpl> elements = new ArrayList<ElementImpl>();

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

    @Override
    public Intersection cut(Snake snake) {
        final Element head = snake.getHead();
        final Iterator<Element> elements = this.getElements();
        Element next = null;
        for (int i = 0; i < this.size(); i++) {
            next = this.elements.get(i);
            if (head.intersects(next)) {
                return new IntersectionImpl(new ElementIterator(elements.next()), next, true);
            }
        }
        return IntersectionImpl.noIntersection();
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
    public boolean isDead() {
        return this.dead;
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
        final Direction newDirection = this.client.nextDirection();
        Coordinates newCoordinates = this.game.nextCoordinates(this.lastDirection, newDirection, this.getHead()
            .getCoordinates());
        if (this.ingestOnNextMove != null) {
            if (this.ingestOnNextMove.getColour() == this.getHead().getColour()) {
                this.elements.remove(this.elements.size() - 1);
                if (this.elements.size() == 0) {
                    this.dead = true;
                }
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

    private static class ElementIterator implements Iterator<Element> {

        private Element formerHead = null;

        private Element head = null;

        public ElementIterator(Element head) {
            this.head = head;
        }

        @Override
        public boolean hasNext() {
            if (head != null) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Element next() {
            if (this.hasNext()) {
                this.formerHead = this.head;
                // this.head = this.head.followingElement();
                return this.formerHead;
            } else {
                throw new NoSuchElementException();
            }

        }

        @Override
        public void remove() {
            // this.formerHead.cutAfterThis();
        }
    }
}
