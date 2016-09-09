// Otter game
// Sharks
// By Doug Carroll and Jon Jordan

package com.game.otter.game;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.game.otter.start.OtterGame;

class Shark  {
	final OtterGame game;
	public final float STARTSPEED = 4.0f; // Starting speed for sharks
	public final int xOffset = 60;  	  // Hitbox offset from bottom-left corner
	private Texture sharkSprite;		// Shark texture
	private float xCoord; 				// X Coord on map
	private float yCoord;				// Y Coord on map
	private float speed = STARTSPEED;	// Speed of sharks
	private Random rand;				// Handles random
	Rectangle hitBox;					// Hit box for sharks

	public Shark(final OtterGame gam, int speedOffset) {
	
		this.game = gam;
		
		sharkSprite = new Texture("shark.png"); // Shark sprite
		speed = speed + speedOffset; 			// speed of sharks
		rand = new Random();
		int temp = rand.nextInt(1000)+100*2; 	// random
		xCoord = temp-temp*2; 					// randomly spawns the sharks
		yCoord = rand.nextInt(game.getHeight()-40);
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(65, 30); 				// Set size of rectangle
		hitBox.setPosition(xCoord + xOffset, yCoord);// Match loaction with shark
		 
	}
	// Constructor for setting speed
	public Shark(final OtterGame gam, int speedOffset, float speed) {
		
		this.game = gam;
		
		sharkSprite = new Texture("shark.png"); // Shark sprite
		this.speed = speed + speedOffset; 			// speed of sharks
		rand = new Random();
		int temp = rand.nextInt(1000)+100*2; 	// random
		xCoord = temp-temp*2; 					// randomly spawns the sharks
		yCoord = rand.nextInt(game.getHeight()-40);
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(65, 30); 				// Set size of rectangle
		hitBox.setPosition(xCoord + xOffset, yCoord);// Match loaction with shark
		 
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

	public void setSpeed(float spee) {
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
		if(xCoord >= game.getWidth()+200) 
			respawnShark();
	}

	public void display() {
		game.batch.draw(sharkSprite, xCoord, yCoord);
	}

	void dispose() {
		sharkSprite.dispose();
	}
}
