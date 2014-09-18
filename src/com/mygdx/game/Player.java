package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

class Player {
	
	final OtterGame game; 
	Texture playerSprite; // Player texture
	public final byte SPRITEWIDTH = 100; 	// Player sprite width (y)
	public final byte SPRITEHEIGHT = 50;	// Player sprite height (x)
	public final byte SPEED = 3; 			// Player speed
	int clams;			  					// Number of clams for shooting	
	private int xCoord;	  					// Player x coord
	private int yCoord;						// Player y coord
	Rectangle hitBox;	  					// Set hitbox for player
	Life[] lifeArray;						// Life holder
	public final int MAXLIVES = 5;			// Max lives
	private int lives = 3;					// Starting lives
	public final int INVINTIME = 3;			// Time in seconds for otter to be invincible
	private long time = 0;					// Keep track of time
	private long timer = 0;					// Used to add to time
	
	public Player(final OtterGame gam){
		this.game = gam;
		playerSprite = new Texture("player.png");
		
		// Set first position
		xCoord = 675;
		yCoord = 214;
		
		// construct hitbox
		hitBox = new Rectangle(); 
		hitBox.setSize(150, 50); // Set size of rectangle
		hitBox.setPosition(xCoord, yCoord); // Match loaction with shark
		
		// Build lives
		lifeArray = new Life[MAXLIVES];
		for (int i = 0; i < MAXLIVES; i++) {
			lifeArray[i] = new Life(game);
		}
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

	
	void display(){
		int lifex = 50;
		
		game.batch.draw(playerSprite, xCoord, yCoord);
		
		// Display life
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
		
		time = System.currentTimeMillis();
		// Set time and timer
		if(timer == 0){
			timer = time + (INVINTIME * 1000);
			lives--;
		}
			
		// If invincible time is up
		if(timer != 0 && time > timer)
			timer = 0;
		
	}
	
	// Player hits a clam
	void hitByClam(){}
	
	void fire(){}
	
	void addClam(){}
	
	void removeClam(){}
	
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
	
}
