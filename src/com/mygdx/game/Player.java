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
	Texture playerSprite; 					// Player texture
	public final byte SPRITEWIDTH = 100; 	// Player sprite width (y)
	public final byte SPRITEHEIGHT = 50;	// Player sprite height (x)
	public final byte SPEED = 4; 			// Player speed
	private int ammo;			  			// Number of clams for shooting
	public final double  SHOTDELAY = .1;    // Delay between shots in seconds
	private int xCoord;	  					// Player x coord
	private int yCoord;						// Player y coord
	Rectangle hitBox;	  					// Set hitbox for player
	Life[] lifeArray;						// Life holder
	public final int MAXLIVES = 5;			// Max lives
	private int lives = 3;					// Starting lives
	public final int INVINTIME = 3;			// Time in seconds for otter to be invincible
	private long timer = 0;					// Timer for hitting sharks
	ArrayList<Bullet> bulletList;			// Handles bullets
	Sound bite;								// Shark bite sound
	Sound shoot;							// Throw clam sound
	Sound grabClam;							// Pick up clam sound
	
	public Player(final OtterGame gam, ArrayList<Bullet> bulletList){
		this.game = gam;
		this.bulletList = bulletList;
		playerSprite = new Texture("player.png");
		bite = Gdx.audio.newSound(Gdx.files.internal("bite.wav")); // Player hits shark sound
		shoot = Gdx.audio.newSound(Gdx.files.internal("shoot.mp3")); // Shoot sound
		grabClam = Gdx.audio.newSound(Gdx.files.internal("grabClam.wav")); // Grab clam sound
		ammo = 5;
		
		// Set first position
		xCoord = 675;
		yCoord = 214;
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(120, 30); // Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
		
		// Build lives
		lifeArray = new Life[MAXLIVES];
		for (int i = 0; i < MAXLIVES; i++) {
			lifeArray[i] = new Life(game);
		}
	}
	
	
	
	public int getAmmo() {
		return ammo;
	}

	// Getter for xCoord
	public int getxCoord() {
		return xCoord;
	}


	// Getter for yCoord
	public int getyCoord() {
		return yCoord;
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
			lifeArray[i].display(lifex);
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
	String playerInput(){
		
		String direction = "";
		
		// Left
		if(Gdx.input.isKeyPressed(Keys.A)){
			direction = "left";
			// Left and up
			if(Gdx.input.isKeyPressed(Keys.W))
				direction = "left-up";
			// Left and down
			else if(Gdx.input.isKeyPressed(Keys.S))
				direction = "left-down";
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = "";
		}
		
		// Right
		if(Gdx.input.isKeyPressed(Keys.D)){
			direction = "right";
			// Right and up
			if(Gdx.input.isKeyPressed(Keys.W))
				direction = "right-up";
			// Right and down
			else if(Gdx.input.isKeyPressed(Keys.S))
				direction = "right-down";
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.A))
				direction = "";
		}
		
		// Down
		if(Gdx.input.isKeyPressed(Keys.S)){
			direction = "down";
			// Down and left
			if(Gdx.input.isKeyPressed(Keys.A))
				direction = "left-down";
			// Down and right
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = "right-down";
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.W))
				direction = "";
		}
		
		// Up
		if(Gdx.input.isKeyPressed(Keys.W)){
			direction = "up";
			// Up and left
			if(Gdx.input.isKeyPressed(Keys.A))
				direction = "left-up";
			// Up and right
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = "right-up";
			// No direction
			else if(Gdx.input.isKeyPressed(Keys.D))
				direction = "right";
		}
		
		return direction;
		
	}
	
	void movement(){
		
		String direction = playerInput();
		boundries();
		
		// Left
		if(direction.equals("left"))
			xCoord -= SPEED;
		
		// Left and up
		if(direction.equals("left-up")){
			yCoord += SPEED;
			xCoord -= SPEED;
		}
		
		// Left and down
		if(direction.equals("left-down")){
			yCoord -= SPEED;
			xCoord -= SPEED;
		}
		
		// Right
		if(direction.equals("right"))
			xCoord += SPEED;
		
		// Right and up
		if(direction.equals("right-up")){
			yCoord += SPEED;
			xCoord += SPEED;
		}
		
		// Right and down
		if(direction.equals("right-down")){
			yCoord -= SPEED;
			xCoord += SPEED;
		}
		
		// Down
		if(direction.equals("down"))
			yCoord -= SPEED;
		
		// Up
		if(direction.equals("up"))
			yCoord += SPEED;
		
		hitBox.setPosition(xCoord, yCoord); // Match location with shark
	}
	
	//Player hits a shark
	void hitByShark(){
		
		long time = System.currentTimeMillis();
		// Set time and timer
		if(timer == 0){
			bite.play();
			timer = time + (INVINTIME * 1000);
			lives--;
			lifeArray[lives].dispose(); // Dispose of life
		}
		// If invincible time is up
		if(timer != 0 && time > timer)
			timer = 0;
	}
	
	// Player hits a clam
	void hitByClam(){
		
		grabClam.play(); // Play grab clam sound
		ammo++; // Increase player ammo
		
	}
	
	// Checks shot delay
	void fireCheck(){
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && ammo > 0)
				fire();
	}
	
	void fire(){
		
		shoot.play(); // Play fire sound
		bulletList.add(new Bullet(game, xCoord, yCoord));
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
	
	void addlife(){}
	
	void removeLife(){}


	// Garbage collection
	void dispose() {
		playerSprite.dispose();
		bite.dispose();
	}
	
}
