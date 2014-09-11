package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

class Player {
	
	final OtterGame game;
	public Texture player;
	int clams;
	private int xCoord;
	private int yCoord;
	final byte SPEED = 3;
	
	public Player(final OtterGame gam){
		this.game = gam;
		// Set first position
		xCoord = 675;
		yCoord = 214;
	}
	
	
	
	// Getter for xCoord
	public int getxCoord() {
		return xCoord;
	}


	// Getter for yCoord
	public int getyCoord() {
		return yCoord;
	}

	
	
	
	void spawn(){
		
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
			xCoord += SPEED;
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
		
		
	}
	
	void hit(){}
	
	void fire(){}
	
	void addClam(){}
	
	void removeClam(){}
	
	
}
