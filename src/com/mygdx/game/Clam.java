// Otter game
// Clams
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Clam {
	
	Texture clamSprite;		// Clam texture
	final OtterGame game;	
	Rectangle hitBox;		// Clam hitbox
	private int xCoord;		// X coord
	private int yCoord;		// Y coord
	private int speed;		// Speed of clams
	Random rand;
	
	public Clam(final OtterGame gam, int speedOffset){
		
		game = gam;
		rand = new Random();
		int temp = rand.nextInt(1000)+100*2; 	// Random number
		xCoord = temp-temp*2; 					// Randomly spawns the clams
		yCoord = rand.nextInt(game.getHeight()-40);
		speed = 4 + speedOffset; 				// Speed of sharks
	}
	
	
	public int getxCoord() {
		return xCoord;
	}


	public int getyCoord() {
		return yCoord;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	void respawn(){
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
			respawn();
		
	}
	
	// Clam display
	public void display(){
		game.batch.draw(clamSprite, xCoord, yCoord);
	}
	
	void dispose() {
		clamSprite.dispose();
	}
	
}

