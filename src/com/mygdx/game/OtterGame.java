package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OtterGame extends Game {
	
	short HEIGHT = 480;
	short WIDTH = 800;
	SpriteBatch batch;
	BitmapFont font;        // Default font
	
	
	// Getter for screen height
	public short getHeight(){return HEIGHT;}
	// Getter for screen width
	public short getWidth(){return WIDTH;}
	
	
	// Creates the game
	public void create () {
		
		// Textures
		batch = new SpriteBatch();
		
		// Font
		font = new BitmapFont();
		
		// Run Main Menu
		this.setScreen(new MainMenu(this));
		
	}
	
	// Renders the game to the screen
	public void render () {
		super.render();
	
		
	}
	
	public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
