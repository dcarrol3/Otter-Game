// Class to handle buttons in libgdx
// By Doug Carroll and Jon Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Button {
	
	final OtterGame game;
	protected Sound sound;		// Button sound
	protected Texture button;	// Main button Texture
	private Texture highlightedTexture; // If texture is highlighted
	protected float height;		// Button height
	protected float width;		// Button width
	protected float xCoord;		// Button x coord
	protected float yCoord;		// Button y coord
	
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
	public float getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public float getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public float getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setTexture(String file){
		button = new Texture(file);
	}
	
	// Set different highlighted button texture
	public void setPressedTexture(String file){
		highlightedTexture = new Texture(file);
	}
	
	// Checks if button was pressed
	boolean isPressed(){
		boolean pressed = false;
		if(isHighlighted()){
			if(Gdx.input.justTouched()){
				pressed = true;
				sound.play(Prefs.getSoundVolume()); // Plays sound
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
	boolean isHighlighted(){
		
		boolean highlighted = false;
		float x = game.convertX(Gdx.input.getX());
		float y = game.convertY(Gdx.input.getY());
			
		if(x >= xCoord && x <= xCoord + width){
			if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
				highlighted = true;
		}
				
		return highlighted;
	}
	
	
	// Displays buttons
	void display(){
		// If isHighlighted is true and it's running on desktop
		if(isHighlighted())
			displayPressed();
		else
			displayNotPressed();
	}
	
	// For only pressed texture to always display
	void displayPressed(){
		game.batch.draw(highlightedTexture, xCoord, yCoord);
	}
	
	// For only Non-Pressed texture to display
	void displayNotPressed(){
		game.batch.draw(button, xCoord, yCoord);
	}
	
	// Kills texture objects
	void dispose(){
		button.dispose();
		highlightedTexture.dispose();
		sound.dispose();
	}
}
