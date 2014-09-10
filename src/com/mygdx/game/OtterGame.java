package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OtterGame extends Game {
	
	SpriteBatch batch;
	BitmapFont font;        // Default font
	
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
