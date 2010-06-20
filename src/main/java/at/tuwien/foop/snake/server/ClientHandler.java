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
	
	// shut down all sockets
	public void disconnect() {
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
		// TODO maybe make this even less prone to blocking by constantly reading in its own thread
		// take the last direction that was transmitted within the last cycle
		try {
			while (this.in.available() > 0) {
				currentDirection = (Direction) this.in.readObject();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Move reported by client " + this.client + " is: " + currentDirection);
		return currentDirection;
	}
	
	// TODO border-type
	public void updateGameData(Colour[][] data) throws IOException {
		this.out.writeObject(data);
	}

}
