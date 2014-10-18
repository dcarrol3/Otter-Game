package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class DoubleClam extends Clam {
	
	public DoubleClam(OtterGame gam, int speedOffset) {
		super(gam, speedOffset);
		clamSprite = new Texture("redclam.png"); // Double clam sprite
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}

}
