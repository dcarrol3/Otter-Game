// Otter game
// Otter/Player class
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Player {
	
	final OtterGame game;
	// Handles directions for player
	private enum Direction {LEFT, RIGHT, UP, DOWN, LEFTUP, LEFTDOWN, RIGHTUP, RIGHTDOWN, STILL}
	Direction direction = null;				// Handles directions
	ArrayList<Bullet> bulletList;			// Handles bullets
	ArrayList<Life> lifeArray;				// Life holder
	Rectangle hitBox;	  					// Set hitbox for player
	public final byte MAXLIVES = 5;			// Max lives
	public final byte INVINTIME = 1;		// Time in seconds for otter to be invincible
	public final short SPRITEWIDTH = 179; 	// Player sprite width (x)
	public final byte SPRITEHEIGHT = 50;	// Player sprite height (y)
	public final double  SHOTDELAY = .1;    // Delay between shots in seconds
	private Texture playerSprite; 			// Player texture
	private float speed = 4.0f; 			// Player speed
	private int ammo;			  			// Number of clams for shooting
	private float xCoord;	  				// Player x coord
	private float yCoord;					// Player y coord
	private int lives = 3;					// Starting lives
	private long timer = 0;					// Timer for hitting sharks
	private float bulletSpeed;				// Handles bullet/throw speed
	private int score;						// Handles player score
	private int scoreOffset = 1;			// Score multiplier
	private Sound bite;						// Shark bite sound
	private Sound shoot;					// Throw clam sound
	private Sound grabClam;					// Pick up clam sound
	
	public Player(final OtterGame gam, ArrayList<Bullet> bulletList){
		this.game = gam;
		this.bulletList = bulletList;
		playerSprite = new Texture("player.png");
		bite = Gdx.audio.newSound(Gdx.files.internal("bite.wav")); // Player hits shark sound
		shoot = Gdx.audio.newSound(Gdx.files.internal("shoot.mp3")); // Shoot sound
		grabClam = Gdx.audio.newSound(Gdx.files.internal("grabClam.wav")); // Grab clam sound
		ammo = 5;	 // STarting ammo
		score = 0;	 // Starting score
		bulletSpeed = 7.0f;
		
		// Set first position
		xCoord = 675.0f;
		yCoord = 214.0f;
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(120, 30); // Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
		
		// Build lives
		lifeArray = new ArrayList<Life>();
		for (int i = 0; i < MAXLIVES; i++) {
			lifeArray.add(new Life(game));
		}
	}
	
	
	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}


	public int getScore() {
		
		return score;
	}
	
	public int getAmmo() {
		return ammo;
	}

	// Getter for xCoord
	public float getxCoord() {
		return xCoord;
	}


	// Getter for yCoord
	public float getyCoord() {
		return yCoord;
	}
	
	public int getScoreOffset() {
		return scoreOffset;
	}


	public void setScoreOffset(int scoreOffset) {
		this.scoreOffset = scoreOffset;
	}


	public float getBulletSpeed() {
		return bulletSpeed;
	}


	public void setBulletSpeed(float bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}


	public int getLives() {
		return lives;
	}
	
	
	// Displays player
	void display(){game.batch.draw(playerSprite, xCoord, yCoord);}
	
	// Displays life
	void displayLife(){
		int lifex = 50;
		
		for (int i = 0; i < lives; i++) {
			lifeArray.get(i).display(lifex);
			lifex += 50;
		}
		lifex = 50;
	}
	
	void respawn(){
		
		// reset position
		xCoord = 675;
		yCoord = 214;
		
	}
	
	// Main player input
	void playerInput(){
		
		direction = Direction.STILL;
		
		// Left
		if(Gdx.input.isKeyPressed(Keys.A)){
			direction = Direction.LEFT;
			// Left and up
			if(Gdx.input.isKeyPressed(Keys.W))
				direction = Direction.LEFTUP;
			// Left and down
			else if(Gdx.input.isKeyPressed(Keys.S))
				direction = Direction.LEFTDOWN;
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = Direction.STILL;
		}
		
		// Right
		if(Gdx.input.isKeyPressed(Keys.D)){
			direction = Direction.RIGHT;
			// Right and up
			if(Gdx.input.isKeyPressed(Keys.W))
				direction = Direction.RIGHTUP;
			// Right and down
			else if(Gdx.input.isKeyPressed(Keys.S))
				direction = Direction.RIGHTDOWN;
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.A))
				direction = Direction.STILL;
		}
		
		// Down
		if(Gdx.input.isKeyPressed(Keys.S)){
			direction = Direction.DOWN;
			// Down and left
			if(Gdx.input.isKeyPressed(Keys.A))
				direction = Direction.LEFTDOWN;
			// Down and right
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = Direction.RIGHTDOWN;
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.W))
				direction = Direction.STILL;
		}
		
		// Up
		if(Gdx.input.isKeyPressed(Keys.W)){
			direction = Direction.UP;
			// Up and left
			if(Gdx.input.isKeyPressed(Keys.A))
				direction = Direction.LEFTUP;
			// Up and right
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = Direction.RIGHTUP;
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.S))
				direction = Direction.STILL;
		}
	}
	
	// Handles player movement
	void movement(){
		
		
		
		switch(Gdx.app.getType()){
		
		// Desktop
		case Desktop:
			
			playerInput();
			
			switch (direction) {
			
			case STILL:
				break;
			
			case LEFT:
				xCoord -= speed;
				break;
				
			case RIGHT:
				xCoord += speed;
				break;
				
			case UP:
				yCoord += speed;
				break;
			
			case DOWN:
				yCoord -= speed;
				break;
			
			case LEFTUP:
				yCoord += speed;
				xCoord -= speed;
				break;
			
			case LEFTDOWN:
				yCoord -= speed;
				xCoord -= speed;
				break;
			
			case RIGHTUP:
				yCoord += speed;
				xCoord += speed;
				break;
				
			case RIGHTDOWN:
				yCoord -= speed;
				xCoord += speed;
				break;
			
			default:
				break;
				
			}
			
			boundries();
			break;
		
	    // Android
		case Android:
			
			float x = game.convertX(Gdx.input.getX());
			float y = game.convertY(Gdx.input.getY());
			
			xCoord = x - SPRITEWIDTH;
			// Y coord: fixes coordinate flip 
			yCoord = (game.getHeight() - y);
			boundries();
			
			break;
			
		default:
			break;
		}
		
		hitBox.setPosition(xCoord, yCoord); // Match location with player
	}
	
	//Player hits a shark
	void hitByShark(){
		
		long time = System.currentTimeMillis();
		// Set time and timer
		if(timer == 0){
			bite.play(Prefs.getSoundVolume());
			timer = time + (INVINTIME * 1000);
			lives--;
			lifeArray.get(lives).dispose(); // Dispose of life
			lifeArray.remove(lives);
		}
		// If invincible time is up
		if(timer != 0 && time > timer)
			timer = 0;
	}
	
	// Player hits a clam
	void hitByClam(){
		
		grabClam.play(Prefs.getSoundVolume()); // Play grab clam sound
		ammo++; // Increase player ammo
		score = score + (1 * scoreOffset); // Increase score
		
	}
	
	// Adds player life
	void addLife(){
		
		if(lives < MAXLIVES){
			lives++;
			lifeArray.add(new Life(game));
		}
	}
	
	// Checks for shooting requirements
	void fireCheck(){
		
		switch(Gdx.app.getType()){
		
		// Desktop
		case Desktop:
			if(Gdx.input.isKeyJustPressed(Keys.SPACE) && ammo > 0)
				fire();
			break;
		// Android
		case Android:
			//If second finger touches down
			if(Gdx.input.justTouched() && Gdx.input.isTouched(1) && ammo > 0){
				fire();
			}
			break;
		default:
			break;		
		}
	}
	
	void fire(){
		
		shoot.play(Prefs.getSoundVolume()); // Play fire sound
		bulletList.add(new Bullet(game, xCoord, yCoord, bulletSpeed));
		ammo--;
		
	}
	
	// Confine player to playable area
	void boundries(){
		
		// Width boundary
		if(xCoord <= 0) // Left boundary
			xCoord = 0;
		else if(xCoord + SPRITEWIDTH >= game.getWidth())
			xCoord = game.getWidth() - SPRITEWIDTH; // Right boundary
		
		// Height boundary
		if(yCoord <= 0) // Lower boundary
			yCoord = 0;
		else if(yCoord + SPRITEHEIGHT>= game.getHeight())
			yCoord = game.getHeight() - SPRITEHEIGHT; // Upper boundary
			
	}
	
	// Handles increasing score multiplier
	void increaseScoreMulti(int multi){
		scoreOffset++;
	}
	
	// Handles decreasing score multiplier
	void decreaseScoreMulti(int multi){
		scoreOffset--;
	}

	// Garbage collection
	void dispose() {
		playerSprite.dispose();
		bite.dispose();
	}
}
