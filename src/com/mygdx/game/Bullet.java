// Otter Game
// Handles shooting clams
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Bullet{
	
	final OtterGame game;
	Texture bullet;					// Bullet Texture
	Rectangle hitBox;				// Bullet hitBox
	private float xCoord;			// X Coord on map
	private float yCoord;			// Y Coord on map
	private float speed;			// Handles bullet speed
	
	Bullet(final OtterGame gam, float x, float y){
		game = gam;
		bullet = new Texture("bullet.png");
		
		speed = 7.0f;
		xCoord = x;
		yCoord = y;
		
		// Build hit box
		hitBox = new Rectangle();
		hitBox.setSize(28, 28); // TODO Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
	}
	
	Bullet(final OtterGame gam, float x, float y, float speed){
		game = gam;
		bullet = new Texture("bullet.png");
		
		this.speed = speed;
		xCoord = x;
		yCoord = y;
		
		// Build hit box
		hitBox = new Rectangle();
		hitBox.setSize(28, 28); // TODO Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
	}
	
	public float getxCoord() {
		return xCoord;
	}

	public float getyCoord() {
		return yCoord;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	// Handles bullet movement
	void movement(){
		xCoord -= speed;
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
