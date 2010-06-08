package at.tuwien.foop.snake.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;

public class SnakeClient {
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public SnakeClient(String host, int port) throws IOException {
		this.socket = new Socket(host, port);
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
		
		// TODO read size of playing field!
	}
	
	public void setNextMove(Direction move) throws IOException {
		this.out.writeObject(move);
	}
	
	// this call blocks!
	public Colour[][] getGameData() {
		try {
			System.out.println("Receiving new game data..");
			return (Colour[][]) this.in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
