package com.game.otter.game;
import com.badlogic.gdx.graphics.Texture;
import  com.game.otter.start.OtterGame;

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
