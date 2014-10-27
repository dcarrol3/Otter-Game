// Otter game
// Main window class
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class OtterGame extends Game {
	
	private int HEIGHT = 480;		// game height
	private int WIDTH = 800;		// Game width
	private float screenHeight;		// Native screen height
	private float screenWidth;		// Native screen width
	OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;        // Default font	
	
	// Getter for screen height
	public int getHeight(){return HEIGHT;}
	// Getter for screen width
	public int getWidth(){return WIDTH;}
	
	
	public float getScreenHeight() {
		return screenHeight;
	}
	public float getScreenWidth() {
		return screenWidth;
	}
	// Creates the game
	public void create () {
		
		// Get native screen width and heights
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		// Camera
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.setToOrtho(false, WIDTH, HEIGHT); // Screen size
		
		// Textures
		batch = new SpriteBatch();
		
		// Font
		fonts();
		
		// Sets default prefs if needed
        Prefs.defaults();
		
		// Run Main Menu
		this.setScreen(new MainMenu(this));
		
	}
	
	// Renders the game to the screen
	public void render () {
		super.render();
	
		
	}
	
	// Converts X coords for android
	public float convertX(float xCoord){
		//return (xCoord / (screenWidth / WIDTH)) - (screenWidth % WIDTH);
		return (xCoord * (WIDTH / screenWidth));
	}
	
	// Converts Y coords for android
	public float convertY(float yCoord){
		//return (yCoord * (screenHeight / HEIGHT)) - (screenHeight % HEIGHT);
		return (yCoord * (HEIGHT / screenHeight));
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
