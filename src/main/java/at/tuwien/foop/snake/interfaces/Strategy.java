package at.tuwien.foop.snake.interfaces;

public interface Strategy {

    public Colour decideOnColour(Colour colour, Colour colour2);
    
    public boolean eats(Colour eater, Colour toEat);

}
