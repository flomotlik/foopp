package at.tuwien.foop.snake.interfaces;

import java.util.List;

public interface Intersection {

    public boolean intersects();

    public Element getIntersection();

    public List<Element> getElementsAfterIntersection();

}
