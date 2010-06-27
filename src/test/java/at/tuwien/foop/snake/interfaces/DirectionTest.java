package at.tuwien.foop.snake.interfaces;

import junit.framework.Assert;

import org.junit.Test;

public class DirectionTest {

    @Test
    public void isOpposite() {
        Assert.assertTrue(Direction.UP.isOpposite(Direction.DOWN));
        Assert.assertTrue(Direction.DOWN.isOpposite(Direction.UP));
        Assert.assertTrue(Direction.LEFT.isOpposite(Direction.RIGHT));
        Assert.assertTrue(Direction.RIGHT.isOpposite(Direction.LEFT));
        for (Direction direction : Direction.values()) {
            Assert.assertFalse(Direction.NONE.isOpposite(direction));
        }

    }

}
