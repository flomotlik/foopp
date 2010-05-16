package at.tuwien.foop.snake.model;

import at.tuwien.foop.snake.interfaces.Coordinates;

public class CoordinatesImpl implements Coordinates {

    private final int x;

    private final int y;

    public CoordinatesImpl(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoordinatesImpl other = (CoordinatesImpl) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}