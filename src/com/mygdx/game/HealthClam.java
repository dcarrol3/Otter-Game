package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class HealthClam extends Clam {

	public HealthClam(OtterGame gam, float speed) {
		super(gam);
		clamSprite = new Texture("greenclam.png"); // health Clam sprite
		this.speed = speed;
	}
	
	// Handles movement
	void movement(){
		xCoord += speed;
		hitBox.setPosition(xCoord, yCoord);
	}

}
