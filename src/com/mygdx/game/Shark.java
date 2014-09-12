package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

class Shark  {
	Texture sharkSprite;
	private int xCoord; 
	private int yCoord;
	private int speed;
	Random rand;
	final OtterGame game;
	// create an array of sharks and have them randomly spawn
	// spawn coordinates
	public Shark(final OtterGame gam) {
		this.game = gam;
		
		sharkSprite = new Texture("shark.pdf");
		speed = 3;
		rand = new Random();
		xCoord = -100;
		yCoord = rand.nextInt(480);
	}
	
	
	void movement() {
		xCoord += speed;
	}
	
	void animations() {}
	
	void hit() {}
	
	void positionReset() {}


	public int getxCoord() {
		return xCoord;
	}


	public int getyCoord() {
		return yCoord;
	}


	
	
	
}
