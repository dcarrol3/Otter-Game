package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class DoubleClam extends Clam {
	
	public DoubleClam(OtterGame gam, float speed) {
		super(gam);
		clamSprite = new Texture("redclam.png"); // Double clam sprite
		this.speed = speed;
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}
}
