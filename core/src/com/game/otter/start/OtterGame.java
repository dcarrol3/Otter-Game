// Otter game
// Main window class
// By Doug Carroll and Jon Jordan

package com.game.otter.start;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.game.otter.game.Prefs;
import com.game.otter.menu.MainMenu;


public class OtterGame extends Game {
	
	// Shared game wide
	public SpriteBatch batch;
	public BitmapFont font;        	 		// Default font	
	public BitmapFont toonyFont;			// Default toony
	public BitmapFont toonyFontLarge;		// Large toony
	public BitmapFont doubleFont;			// Toony font for score increase
	public OrthographicCamera camera;
	
	private int HEIGHT = 480;		// game height
	private int WIDTH = 800;		// Game width
	private float screenHeight;		// Native screen height
	private float screenWidth;		// Native screen width
	
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
		return (xCoord * (WIDTH / screenWidth));
	}
	
	// Converts Y coords for android
	public float convertY(float yCoord){
		return (yCoord * (HEIGHT / screenHeight));
	}
	
	public void dispose() {
        batch.dispose();
        font.dispose();
    }
	
	
	void fonts(){
		// Default Font
		font = new BitmapFont();
		
		// Custom fonts
		// Generator for toony font
		FreeTypeFontGenerator toonyGen = new FreeTypeFontGenerator(Gdx.files.internal("Toony.ttf"));
		FreeTypeFontParameter toonyParms = new FreeTypeFontParameter(); // Sets default options for font
		
		// Default
		toonyParms.size = 18; // Sets default font size
		toonyFont = toonyGen.generateFont(toonyParms); // Load in font
		
		// Large
		toonyParms.size = 32; // Sets Large font size
		toonyFontLarge = toonyGen.generateFont(toonyParms); // Load in large font
		
		// Double
		toonyParms.size = 22; // Sets Large font size
		doubleFont = toonyGen.generateFont(toonyParms); // Load in large font
		
		// Font colors
		toonyFont.setColor(1, 1, 1, 1);
		doubleFont.setColor(1, 0, 0, 1);
		
		// Dispose of generators
		toonyGen.dispose();
	}
}
