package at.tuwien.foop.snake.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import at.tuwien.foop.snake.interfaces.Client;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;

public class ClientHandler implements Client {
	
	private Socket client;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Direction currentDirection;
	
	public ClientHandler(Socket client) throws IOException {
		this.client = client;
		this.out = new ObjectOutputStream(this.client.getOutputStream());
		this.in = new ObjectInputStream(this.client.getInputStream());
		// starting-direction
		this.currentDirection = Direction.RIGHT;
	}
	
	public void init(int width, int height) throws IOException {
		// send out size of playing field
		this.out.writeInt(width);
		this.out.writeInt(height);
		this.out.flush();
	}
	
	// shut down all sockets
	public void disconnect() {
		this.currentDirection = Direction.NONE;
		try {
			this.out.close();
		} catch (IOException e) {
		}
		try {
			this.in.close();
		} catch (IOException e) {
		}
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Client " + this.client + " disconnected!");
	}
	
	// returns the move or action sent by this client
	@Override
	public Direction nextDirection() {
		// take the last direction that was transmitted within the last cycle
		try {
			while (this.in.available() > 0) {
				this.currentDirection = (Direction) this.in.readObject();
			}
		} catch (IOException e) {
			this.currentDirection = Direction.NONE;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			this.currentDirection = Direction.NONE;
			e.printStackTrace();
		}
		System.out.println("Move reported by client " + this.client + " is: " + this.currentDirection);
		return this.currentDirection;
	}
	
	// TODO border-type
	public void updateGameData(Colour[][] data) throws IOException {
		this.out.writeObject(data);
	}

}
