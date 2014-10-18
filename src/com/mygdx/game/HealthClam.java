package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class HealthClam extends Clam {

	public HealthClam(OtterGame gam, int speedOffset) {
		super(gam, speedOffset);
		clamSprite = new Texture("greenclam.png"); // health Clam sprite
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}

}
