package at.tuwien.foop.snake;

import java.util.List;

public interface Intersection {

    public boolean intersects();

    public Element getIntersection();

    public List<Element> getElementsAfterIntersection();

}
