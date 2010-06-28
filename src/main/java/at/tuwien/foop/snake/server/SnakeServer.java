package at.tuwien.foop.snake.server;

import java.io.IOException;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Game;
import at.tuwien.foop.snake.model.GameImpl;

public class SnakeServer extends Thread {
	
	public static int DEFAULTPORT = 12349;

	public static void main(String[] args) throws IOException {
		// default size
		int width = 10;
		int height = 10;
		// parse args[] for other size of game area
		switch (args.length) {
			case 0:
				break;
			case 1:
				width = Integer.parseInt(args[0]);
				height = width;
				break;
			case 2:
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
				break;
			default:
				throw new IllegalArgumentException();
		}
		SnakeServer s = new SnakeServer(width, height);
		s.start();
	}
	
	private ClientListener clientListener;
	
	private List<ClientHandler> clients = new LinkedList<ClientHandler>();
	
	private long lastTime;
	
	// in ms
	private long cycleLength = 1000;
	
	private Game game;
	
	public SnakeServer(int width, int height) throws IOException {
		this.clientListener = new ClientListener(SnakeServer.DEFAULTPORT);
		this.game = new GameImpl(width, height);
	}
		
	public void run() {
		System.out.println("SnakeServer starting up..");
		this.clientListener.start();
		try {
			this.lastTime = System.currentTimeMillis();
			do {
				System.out.println("TICK");
				Colour data[][] = this.game.nextMove();
				// we need the iterator explicitly to be able to remove dropped clients
				Iterator<ClientHandler> i = this.clients.iterator();
				while (i.hasNext()) {
					ClientHandler client = i.next();
					try {
						client.updateGameData(data);
					} catch (SocketException e) {
						e.printStackTrace();
						i.remove();
						client.disconnect();
					}
				}
				
				// accept new clients
				Queue<ClientHandler> newClients = this.clientListener.getNewClients();
				ClientHandler newClient;
				while ((newClient = newClients.poll()) != null) {
					newClient.init(this.game.getWidth(), this.game.getHeight());
					this.game.addSnake(newClient);
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
		
		for (ClientHandler client : this.clients) {
			client.disconnect();
			// emptying list not necessary
		}
	}

}
