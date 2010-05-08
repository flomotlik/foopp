package at.tuwien.foop.snake.interfaces;

import java.util.Iterator;

public interface Snake {
    public Iterator<Element> getElements();

    public Intersection cut(Element head);
    
    public void move();
    
    public int size();
    
    public void ingest(Element toIngest);
    
    public boolean isDead();
}
