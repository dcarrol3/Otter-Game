package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class OtterGame extends Game {
	
	int HEIGHT = 480;
	int WIDTH = 800;
	SpriteBatch batch;
	BitmapFont font;        // Default font
	
	
	// Getter for screen height
	public int getHeight(){return HEIGHT;}
	// Getter for screen width
	public int getWidth(){return WIDTH;}
	
	
	// Creates the game
	public void create () {
		
		// Textures
		batch = new SpriteBatch();
		
		// Font
		fonts();
		
		
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
	
	
	void fonts(){
		// Fonts
		font = new BitmapFont();
	}
}
