// Class to handle buttons in libgdx
// By Doug Carroll and Jon Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Button {
	
	final OtterGame game;
	private Texture button;	// Main button Texture
	private Texture highlightedTexture; // If texture is highlighted
	Sound sound;
	private int height;		// Button height
	private int width;		// Button width
	private int xCoord;		// Button x coord
	private int yCoord;		// Button y coord
	
	public Button(final OtterGame gam, String file, int w, int h, int x, int y){
		game = gam;
		button = new Texture(file);
		highlightedTexture = new Texture(file);
		sound = Gdx.audio.newSound(Gdx.files.internal("button.wav"));
		height = h;
		width = w;
		xCoord = x;
		yCoord = y;
		
	}
	
	// Constructor for adding custom sound
	public Button(final OtterGame gam, String file, int w, int h, int x, int y, String soundFile ){
		game = gam;
		button = new Texture(file);
		highlightedTexture = new Texture(file);
		sound = Gdx.audio.newSound(Gdx.files.internal(soundFile));
		height = h;
		width = w;
		xCoord = x;
		yCoord = y;
		
	}
	// Getters/Setters
	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	// Set different highlighted button texture
	public void setPressedTexture(String file){
		highlightedTexture = new Texture(file);
	}
	
	// Checks if button was pressed
	boolean isPressed(){
		boolean pressed = false;
		if(isHighlighted()){
			if(Gdx.input.isTouched() || Gdx.input.justTouched()){
				pressed = true;
				sound.play(); // Plays sound
				// Sleeps so sound can be played
				try {
				    Thread.sleep(200);                
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}
		return pressed;
	}
	
	// Checks if button is hovered over by mouse
	private boolean isHighlighted(){
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		boolean highlighted = false;
		
		if(x >= xCoord && x <= xCoord + width){
			if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
				highlighted = true;
		}
		return highlighted;
	}
	
	// Displays buttons
	void display(){
		if(isHighlighted())
			game.batch.draw(highlightedTexture, xCoord, yCoord);
		else
			game.batch.draw(button, xCoord, yCoord);
	}
	
	// Kills texture objects
	void dispose(){
		button.dispose();
		highlightedTexture.dispose();
		sound.dispose();
	}
}
