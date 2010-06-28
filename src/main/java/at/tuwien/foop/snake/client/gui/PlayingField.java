package at.tuwien.foop.snake.client.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import at.tuwien.foop.snake.interfaces.Colour;

// the PlayingField-Canvas
public class PlayingField extends Canvas {
	
	private static final long serialVersionUID = -118286530438632278L;
	
	private static int elementSize = 15;
	private static int margin = 3;
	
	private Dimension size;
	private Colour[][] gameData;
	
	// TODO maybe make this class more observer-like?
	public PlayingField(Dimension size) {
		this.size = size;
		this.setBackground(Color.WHITE);
		Dimension pixelSize = new Dimension(size.width*elementSize + (size.width+1)*margin, size.height*elementSize + (size.height+1)*margin);
		this.setMinimumSize(pixelSize);
		this.setMaximumSize(pixelSize);
		this.setPreferredSize(pixelSize);
	}
	
	// TODO currentBorderMode and nextBorderMode don't do anything at the moment, give them any value
	public void setGameData(int currentBorderMode, int nextBorderMode, Colour[][] gameData) {
		this.gameData = gameData;
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO border
		for (int i = 0; i < this.size.width; i++) {
			for (int j = 0; j < this.size.height; j++) {
				g.setColor(Color.BLACK);
				g.drawRect(PlayingField.margin+i*(PlayingField.margin+PlayingField.elementSize), PlayingField.margin+j*(PlayingField.margin+PlayingField.elementSize), PlayingField.elementSize, PlayingField.elementSize);
				if (this.gameData != null) {
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
	    if(c == null){
	        return Color.WHITE;
	    }
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
