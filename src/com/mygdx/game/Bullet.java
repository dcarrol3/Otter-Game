// Otter Game
// Handles shooting clams
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet{
	
	final OtterGame game;
	Texture bullet;				// Bullet Texture
	Rectangle hitBox;			// Bullet hitBox
	private int xCoord;			// X Coord on map
	private int yCoord;			// Y Coord on map
	public final int SPEED = 7;	// Handles bullet speed
	
	Bullet(final OtterGame gam, int x, int y){
		game = gam;
		bullet = new Texture("redclam.png");
		
		xCoord = x;
		yCoord = y;
		
		// Build hit box
		hitBox = new Rectangle();
		hitBox.setSize(50, 20); // TODO Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
	}
	
	public int getxCoord() {
		return xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	// Handles bullet movement
	void movement(){
		xCoord -= SPEED;
		hitBox.setPosition(xCoord, yCoord);
	}
	
	// Display bullet
	void display(){
		game.batch.draw(bullet, xCoord, yCoord);
	}
	
	void dispose(){
		bullet.dispose();
	}
	
	
}
