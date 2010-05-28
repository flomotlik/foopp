package at.tuwien.foop.snake.client.gui;

import javax.swing.JFrame;

import at.tuwien.foop.snake.interfaces.Colour;

public class SnakeWindow extends JFrame {

	public PlayingField display;
	
	// TODO just for debugging, remove later
	public static void main(String[] args) {
		SnakeWindow wnd = new SnakeWindow();
		wnd.setVisible(true);
		
		// flo's code goes here :)
		Colour[][] gameData = null;
		wnd.setGameData(0, 0, gameData);
	}
	
	public SnakeWindow() {
		super("Snake");
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.display = new PlayingField(10, 10);
		this.add(this.display);
		this.pack();
		
	}
	
	public void setGameData(int x, int y, Colour[][] gameData) {
		this.display.setGameData(x, y, gameData);
	}
}
