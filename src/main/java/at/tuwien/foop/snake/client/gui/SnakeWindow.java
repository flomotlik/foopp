package at.tuwien.foop.snake.client.gui;

import javax.swing.JFrame;

public class SnakeWindow extends JFrame {

	public PlayingField display;
	
	// TODO just for debugging, remove later
	public static void main(String[] args) {
		new SnakeWindow().setVisible(true);
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
}
