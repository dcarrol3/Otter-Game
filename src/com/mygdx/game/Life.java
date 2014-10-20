// Otter game
// Player lives
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

class Life {
	final OtterGame game;
	private Texture lifeSprite;  // Life sprite
	private int yCoord = 400;	 // Height for life sprite
	
	public Life(OtterGame gam) {
		game = gam;
		lifeSprite = new Texture("life.png"); // Life sprite
	}
	// Displays this life
	public void display(int xCoord){
		
		game.batch.draw(lifeSprite, xCoord, yCoord);
	}

	void dispose() {
		lifeSprite.dispose();
		
	}
}
