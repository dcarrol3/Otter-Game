package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

class Life {
	final OtterGame game;
	Texture lifeSprite; 						// Life sprite
	private int yCoord = 400;
	
	public Life(OtterGame gam) {
		game = gam;
		lifeSprite = new Texture("life.png"); // Life sprite
	}
	
	void display(int xCoord){
		
		game.batch.draw(lifeSprite, xCoord, yCoord);
	}
	
}
