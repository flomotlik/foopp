package at.tuwien.foop.snake.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ClientListener extends Thread {

	private ServerSocket serverSocket;
	
	private boolean shutdown = false;
	
	private BlockingQueue<ClientHandler> newClients = new SynchronousQueue<ClientHandler>();
	
	public ClientListener(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}
	
	public void run() {
		System.out.println("ClientListener starting up..");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("New client connected: " + socket);
				this.newClients.put(new ClientHandler(socket));
			} catch (IOException e) {
				if (this.shutdown) {
					System.out.println("ServerSocket closed, shutting down ClientListener..");
					break;
				}
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.out.println("Failed to add new Client to Server because of interrupt!!");
			}
		}
	}
	
	public Queue<ClientHandler> getNewClients() {
		return this.newClients;
	}
	
	public void close() {
		this.shutdown = true;
		// TODO empty new clients
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
