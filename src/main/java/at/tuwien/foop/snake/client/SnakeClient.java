package at.tuwien.foop.snake.client;

import java.awt.Dimension;
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
	
	private Dimension playingFieldSize;
	
	public SnakeClient(String host, int port) throws IOException {
		System.out.println("Connecting to " + host + ":" + port);
		this.socket = new Socket(host, port);
		System.out.println("Connected! Local socket " + this.socket);
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
		
		// read size of playing field!
		this.playingFieldSize = new Dimension(this.in.readInt(), this.in.readInt());
	}
	
	public Dimension getPlayingFieldSize() {
		return this.playingFieldSize;
	}
	
	public void setNextMove(Direction move) throws IOException {
		System.out.println("Sending move " + move + " to Server");
		this.out.writeObject(move);
//		this.out.flush();
	}
	
	// this call blocks!
	public Colour[][] getGameData() throws IOException, ClassNotFoundException {
		return (Colour[][]) this.in.readObject();
	}

}
