package at.tuwien.foop.snake.server;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Game;
import at.tuwien.foop.snake.model.GameImpl;

public class SnakeServer extends Thread {

	private ClientListener clientListener;
	
	private List<ClientHandler> clients = new ArrayList<ClientHandler>();
	
	private long lastTime;
	
	// in ms
	private long cycleLength = 1000;
	
	private Game game;
	
	public SnakeServer(int width, int height) throws IOException {
		this.clientListener = new ClientListener(12349);
		this.game = new GameImpl(width, height);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO parse args[] for size of game area
		SnakeServer s = new SnakeServer(30, 15);
		s.start();
	}
	
	public void run() {
		System.out.println("SnakeServer starting up..");
		this.clientListener.start();
		try {
			this.lastTime = System.currentTimeMillis();
			do {
				// TODO iterate one step
				System.out.println("TICK");
				Colour data[][] = this.game.nextMove();
				for (ClientHandler client : this.clients) {
					try {
						client.updateGameData(data);
					} catch (SocketException e) {
						e.printStackTrace();
						// TODO kick client!
					}
				}
				
				// accept new clients
				Queue<ClientHandler> newClients = this.clientListener.getNewClients();
				ClientHandler newClient;
				while ((newClient = newClients.poll()) != null) {
					this.game.addSnake(Colour.RED, newClient);
					this.clients.add(newClient);
				}
				long elapsed = System.currentTimeMillis() - this.lastTime;
				if (elapsed < this.cycleLength) {
					// wait until cycleLength has passed
					try {
						System.out.println("Sleeping for " + (this.cycleLength - elapsed) + "ms");
						Thread.sleep(this.cycleLength - elapsed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				this.lastTime = System.currentTimeMillis();
			// quit when something is typed into the server's console
			} while (System.in.available() == 0);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.clientListener.close();
		
		// TODO get rid of all clients
		for (ClientHandler client : this.clients) {
			// TODO
		}
	}

}
