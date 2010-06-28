package at.tuwien.foop.snake.client.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import at.tuwien.foop.snake.client.SnakeClient;
import at.tuwien.foop.snake.interfaces.Colour;
import at.tuwien.foop.snake.interfaces.Direction;
import at.tuwien.foop.snake.server.SnakeServer;

public class SnakeWindow extends JFrame {
	
	private static final long serialVersionUID = 8641067741490473606L;
	
	private JPanel panel;
	private JTextField host;
	private JTextField port;
	private JButton button;

	private SnakeClient client;

	private SwingWorker<Void,Void> game;
	private PlayingField display;
	
	// start client window here
	public static void main(String[] args) throws IOException {
		SnakeWindow wnd = new SnakeWindow();
		wnd.setVisible(true);
	}
	
	public SnakeWindow() {
		super("Snake");
		this.setLocation(400, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setFocusable(false);
		
		this.panel = new JPanel();
		this.panel.setMinimumSize(new Dimension(300, 20));
		this.panel.setPreferredSize(new Dimension(300, 20));
		
		this.host = new JTextField("localhost");
		this.host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				button.doClick();
			}
		});
		this.host.setMinimumSize(new Dimension(120, 20));
		this.host.setPreferredSize(new Dimension(120, 20));
		this.panel.add(this.host);
		
		this.port = new JTextField(Integer.toString(SnakeServer.DEFAULTPORT));
		this.port.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				button.doClick();
			}
		});
		this.port.setMinimumSize(new Dimension(50, 20));
		this.port.setPreferredSize(new Dimension(50, 20));
		this.panel.add(this.port);
		
		this.button = new JButton("connect");
		this.button.setMinimumSize(new Dimension(100, 20));
		this.button.setPreferredSize(new Dimension(100, 20));
		this.button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					System.out.println("connecting");
					connect();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(panel, "Fatal internal error: " + e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(panel, e.getMessage());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(panel, "Please enter a valid port number: " + port.getText());
				} finally {
					// put input panel back in place
					add(panel);
					pack();
				}
			}
			
		});
		this.panel.add(this.button);
		this.add(this.panel);
		this.pack();
	}
	
	public void connect() throws IOException, ClassNotFoundException, NumberFormatException {
		this.connect(this.host.getText(), Integer.parseInt(this.port.getText()));
	}
	
	public void connect(String host, int port) throws IOException, ClassNotFoundException {
		this.client = new SnakeClient(host, port);
		// get size of playing field
		this.display = new PlayingField(this.client.getPlayingFieldSize());
		this.display.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
//				System.out.println("Key pressed: " + e.getKeyCode());
				try {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_DOWN:
						client.setNextMove(Direction.DOWN);
						break;
					case KeyEvent.VK_UP:
						client.setNextMove(Direction.UP);
						break;
					case KeyEvent.VK_LEFT:
						client.setNextMove(Direction.LEFT);
						break;
					case KeyEvent.VK_RIGHT:
						client.setNextMove(Direction.RIGHT);
						break;
					default:
						break;
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
		this.game = new SwingWorker<Void,Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					remove(panel);
					add(display);
					pack();
					while (true) {
						// TODO border-state
						setGameData(0, 0, client.getGameData());
						repaint();
					}
				} finally {
					// exception caught, back to insert connection data mode!
					remove(display);
					display = null;
					add(panel);
				}
			}
			
		};
		this.game.execute();
	}
	
	public void setGameData(int x, int y, Colour[][] gameData) {
		System.out.println("Updating display");
		this.display.setGameData(x, y, gameData);
	}
}
