package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class SlowMoClam extends Clam {
	
	public SlowMoClam(OtterGame gam, float speed) {
		super(gam);
		clamSprite = new Texture("purpleclam.png"); // Set to SlowMo clam texture
		this.speed = speed;
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}
	
}
