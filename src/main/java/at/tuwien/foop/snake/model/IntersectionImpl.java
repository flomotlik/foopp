package at.tuwien.foop.snake.model;

import java.util.Iterator;

import at.tuwien.foop.snake.interfaces.Element;
import at.tuwien.foop.snake.interfaces.Intersection;

public class IntersectionImpl implements Intersection {

    private Iterator<Element> elementsAfterIntersection;
    private Element intersection;
    private boolean intersects;

    public IntersectionImpl(Iterator<Element> elementsAfterIntersection, Element intersection, boolean intersects) {
        super();
        this.elementsAfterIntersection = elementsAfterIntersection;
        this.intersection = intersection;
        this.intersects = intersects;
    }

    @Override
    public Iterator<Element> getElementsAfterIntersection() {
        return this.elementsAfterIntersection;
    }

    @Override
    public Element getIntersection() {
        return this.intersection;
    }

    @Override
    public boolean intersects() {
        return this.intersects;
    }

    public static Intersection noIntersection() {
        return new IntersectionImpl(null, null, false);
    }

}