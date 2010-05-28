package at.tuwien.foop.snake.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import at.tuwien.foop.snake.interfaces.Colour;

// TODO name Canvas
public class PlayingField extends Canvas {
	
	private static int elementSize = 15;
	private static int margin = 3;
	
	private int width;
	private int height;
	private Colour[][] gameData;
	
	// TODO maybe make this class more observer-like?
	public PlayingField(int width, int height) {
		this.width = width;
		this.height = height;
		this.setBackground(Color.WHITE);
		Dimension size = new Dimension(width*elementSize + (width+1)*margin, height*elementSize + (height+1)*margin);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
	}
	
	// TODO currentBorderMode and nextBorderMode don't do anything at the moment, give them any value
	public void setGameData(int currentBorderMode, int nextBorderMode, Colour[][] gameData) {
		this.gameData = gameData;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO border
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				g.setColor(Color.BLACK);
				g.drawRect(PlayingField.margin+i*(PlayingField.margin+PlayingField.elementSize), PlayingField.margin+j*(PlayingField.margin+PlayingField.elementSize), PlayingField.elementSize, PlayingField.elementSize);
				if (gameData != null) {
					g.setColor(PlayingField.getColor(gameData[i][j]));
					g.fillRect(PlayingField.margin+i*(PlayingField.margin+PlayingField.elementSize), PlayingField.margin+j*(PlayingField.margin+PlayingField.elementSize), PlayingField.elementSize, PlayingField.elementSize);
				}
			}
		}
	}
	
	/**
	 * Returns the AWT-Color corresponding to the enum value of the data model's Colour
	 * @param c 
	 * @return
	 */
	public static Color getColor(Colour c) {
		switch (c) {
		case RED:
			return Color.RED;
		case BLUE:
			return Color.BLUE;
		case GREEN:
			return Color.GREEN;
		}
		assert(false);
		return Color.WHITE;
	}

}
