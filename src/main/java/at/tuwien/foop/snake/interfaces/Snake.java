package at.tuwien.foop.snake.interfaces;

import java.util.Iterator;

public interface Snake {
    public Iterator<Element> getElements();

    public Intersection cut(Snake snake);

    public int size();

    public void ingest(Element toIngest);

    public boolean isDead();

    public Element getHead();

    public void move();
}
