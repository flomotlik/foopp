package at.tuwien.foop.snake.model;

import java.util.List;

import at.tuwien.foop.snake.interfaces.Element;
import at.tuwien.foop.snake.interfaces.Intersection;

public class IntersectionImpl implements Intersection {

    private List<Element> elementsAfterIntersection;
    private Element intersection;
    private boolean intersects;

    public IntersectionImpl(List<Element> elementsAfterIntersection, Element intersection, boolean intersects) {
        super();
        this.elementsAfterIntersection = elementsAfterIntersection;
        this.intersection = intersection;
        this.intersects = intersects;
    }

    @Override
    public List<Element> getElementsAfterIntersection() {
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
