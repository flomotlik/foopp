package at.tuwien.foop.snake.client.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import at.tuwien.foop.snake.client.SnakeClient;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;

public class SnakeWindow extends JFrame {
	
	private SnakeClient client;

	private PlayingField display;
	
	// TODO just for debugging, remove later
	public static void main(String[] args) throws IOException {
		SnakeWindow wnd = new SnakeWindow(10,10);
		wnd.setVisible(true);
		
		wnd.connect("localhost", 12349);
		
		// flo's code goes here :)
		Colour[][] gameData = null;
		wnd.setGameData(0, 0, gameData);
	}
	
	public SnakeWindow(int height, int width) {
		super("Snake");
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.display = new PlayingField(width, height);
		this.add(this.display);
		this.pack();
		
		// TODO add keylisteners
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Key pressed: " + e.getKeyCode());
				try {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					client.setNextMove(Direction.DOWN);
				case KeyEvent.VK_UP:
					client.setNextMove(Direction.UP);
				case KeyEvent.VK_LEFT:
					client.setNextMove(Direction.LEFT);
				case KeyEvent.VK_RIGHT:
					client.setNextMove(Direction.RIGHT);
				default:
				}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		});
	}
	
	public void connect(String host, int port) throws IOException {
		this.client = new SnakeClient(host, port);
		while (true) {
			// TODO border-state
			this.setGameData(0, 0, this.client.getGameData());
		}
	}
	
	public void setGameData(int x, int y, Colour[][] gameData) {
		System.out.println("Updating display");
		this.display.setGameData(x, y, gameData);
	}
}
