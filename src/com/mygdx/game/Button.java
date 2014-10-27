// Class to handle buttons in libgdx
// By Doug Carroll and Jon Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Button {
	
	final OtterGame game;
	private Sound sound;	// Button sound
	private Texture button;	// Main button Texture
	private Texture highlightedTexture; // If texture is highlighted
	private float height;		// Button height
	private float width;		// Button width
	private float xCoord;		// Button x coord
	private float yCoord;		// Button y coord
	
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
		
		boolean highlighted = false;
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		
		switch(Gdx.app.getType()) {
			// Desktop
			case Desktop:
				if(x >= xCoord && x <= xCoord + width){
					if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
						highlighted = true;
				}
				break;
		   
			// Android
			case Android:
				// Android specific coords
				x = game.convertX(x);
				y = game.convertY(y); 
				
				// Compensates for screen resolution
				if(x >= xCoord && x <= xCoord + width){
					if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
						highlighted = true;
				}
				break;
			
			default:
				break;
		}
		
		return highlighted;
	}
	
	// Displays buttons
	void display(){
		// If isHighlighted is true and it's running on desktop
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
