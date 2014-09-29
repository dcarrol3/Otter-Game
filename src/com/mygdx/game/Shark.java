// Otter game
// Sharks
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Shark  {
	Texture sharkSprite;		// Shark texture
	private int xCoord; 		// X Coord on map
	private int yCoord;			// Y Coord on map
	private int speed;	// Speed of sharks
	public final int xOffset = 60; // Hitbox offset from bottom-left corner
	Random rand;
	final OtterGame game;
	Rectangle hitBox;			// Hit box for sharks

	public Shark(final OtterGame gam, int speedOffset) {
	
		this.game = gam;
		
		sharkSprite = new Texture("shark.png"); // shark pics
		speed = 4 + speedOffset; 				// speed of sharks
		rand = new Random();
		int temp = rand.nextInt(1000)+100*2; 	// random
		xCoord = temp-temp*2; 					// randomly spawns the sharks
		yCoord = rand.nextInt(game.getHeight()-40);
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(65, 30); 				// Set size of rectangle
		hitBox.setPosition(xCoord + xOffset, yCoord);// Match loaction with shark
		 
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

	public void setSpeed(int spee) {
		speed = spee;
	}

	void movement() {
		positionReset();
		xCoord += speed;
		hitBox.setPosition(xCoord + xOffset, yCoord);
		
	}
	
	// TODO Handles animations
	void animations() {}
	
	// respawn
	void respawnShark() {
		yCoord = rand.nextInt(game.getHeight()-40);
		int temp = rand.nextInt(1000)+100*2;
		xCoord = temp-temp*2;
	}
	void positionReset() {
		// setting random variable
		if(xCoord >= game.getWidth()+200) {
			respawnShark();
		}
	}

	public void display() {
		game.batch.draw(sharkSprite, xCoord, yCoord);
	}

	void dispose() {
		sharkSprite.dispose();
	}
	
	
	
}
