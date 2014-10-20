// Otter game
// Clams
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Clam {
	
	protected Texture clamSprite;	// Clam texture
	final OtterGame game;	
	protected Rectangle hitBox;	// Clam hitbox
	protected Random rand;		// Random
	protected float xCoord;		// X coord
	protected float yCoord;		// Y coord
	protected float speed = 4;	// Speed of clams
	
	public Clam(final OtterGame gam){
		
		game = gam;
		rand = new Random();
		clamSprite = new Texture("yellowclam.png"); // Clam sprite
		int temp = rand.nextInt(1000)+100*2; 	// Random number
		xCoord = temp-temp*2; 					// Randomly spawns the clams
		yCoord = rand.nextInt(game.getHeight()-40);
		
		// Construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(38, 28); 			// Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with clam
	}
	
	
	public float getxCoord() {
		return xCoord;
	}


	public float getyCoord() {
		return yCoord;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	void respawnClam(){
		yCoord = rand.nextInt(game.getHeight()-40);
		int temp = rand.nextInt(1000)+100*2;
		xCoord = temp-temp*2;
	}
	
	// Handles clam movement
	void movement(){
		positionReset();
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}
	
	// Checks if clam reaches end of screen
	void positionReset() {
		
		if(xCoord >= game.getWidth()+200) 
			respawnClam();
		
	}
	
	// Clam display
	public void display(){
		game.batch.draw(clamSprite, xCoord, yCoord);
	}
	
	void dispose() {
		clamSprite.dispose();
	}
	
}

