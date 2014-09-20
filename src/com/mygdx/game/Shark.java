// Otter game
// Sharks
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Shark  {
	Texture sharkSprite;
	private int xCoord; 
	private int yCoord;
	private int speed;
	Random rand;
	final OtterGame game;
	
	// Setting boundary of sharks
	Rectangle hitBox;
	
	// create an array of sharks and have them randomly spawn
	// spawn coordinates
	public Shark(final OtterGame gam) {
	
		this.game = gam;
		
		sharkSprite = new Texture("shark.png"); // shark pics
		speed = 3; // speed of sharks
		rand = new Random();
		int temp = rand.nextInt(1000)+100*2; // random
		xCoord = temp-temp*2; // randomly spawns the sharks
		yCoord = rand.nextInt(game.getHeight()-40);
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(101, 52); // Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
		 
	}
	
	
	void movement() {
		positionReset();
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
		
	}
	
	void animations() {}
	
	void hit() {}
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


	public int getxCoord() {
		return xCoord;
	}


	public int getyCoord() {
		return yCoord;
	}

	public void display() {
		game.batch.draw(sharkSprite, xCoord, yCoord);
	}
	
	
	
}
