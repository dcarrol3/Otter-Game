package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class SlowMoClam extends Clam {
	
	public SlowMoClam(OtterGame gam, int speedOffset) {
		super(gam, speedOffset);
		clamSprite = new Texture("purpleclam.png"); // Set to SlowMo clam texture
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}
	
}
