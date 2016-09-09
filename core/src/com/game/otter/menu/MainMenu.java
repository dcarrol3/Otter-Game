// Otter game
// Main Menu
// By Doug Carroll and Jon Jordan

package com.game.otter.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.game.otter.game.GameScreen;
import com.game.otter.game.Prefs;
import com.game.otter.start.OtterGame;

public class MainMenu implements Screen {
	
	final OtterGame game;
	private Texture background;		// Main menu background
	private Texture title;			// Main menu title
	private Button startButton;		// Main start button
	private Button quitButton;		// Menu quit button
	private Button optionsButton;	// Menu options button
	
	public MainMenu(final OtterGame gam){
		
		game = gam;
		
		background = new Texture("menuBackground.png");
		title = new Texture("title.png");
		
		// Capture back button - Android/iOS
		Gdx.input.setCatchBackKey(true); 
		
        // Creates buttons
        createButtons();        
	}
	
	
	// Rendering of main menu
    @Override
    public void render(float delta) {
    	// Background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1); // Background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Updates camera
        game.batch.setProjectionMatrix(game.camera.combined);
        game.camera.update();
        
        // Sprite batch
        game.batch.begin();
        game.batch.draw(background, 0, 0); // Background - Must be at top
        game.batch.draw(title, 0, 0);      // Title - Must be at top below background
        displaySprites();
        game.batch.end();
        
        // Checks if start is clicked and runs game
        if (startButton.isPressed()){ 
        	dispose();
    		game.setScreen(new GameScreen(game));
        }
        if (optionsButton.isPressed()){
        	dispose();
        	game.setScreen(new Options(game, background));
        }
        // Checks if quit is hit and exits game
        if(quitButton.isPressed() || Gdx.input.isKeyPressed(Keys.BACK))
        	System.exit(0);
    }


	@Override
	public void resize(int width, int height) {}


	@Override
	public void show() {}


	@Override
	public void hide() {}


	@Override
	public void pause() {}


	@Override
	public void resume() {}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		startButton.dispose();
		quitButton.dispose();
		
	}
	
	void displaySprites(){
		 game.toonyFontLarge.draw(game.batch, "High Score: " + Prefs.getHighScore(), 330, 205); // Displays high score
		 startButton.display();
		 quitButton.display();
		 optionsButton.display();
	}
	
	// Creates buttons
	void createButtons(){
		// Create start button
        startButton = new Button(game, "start_np.png", 127, 120, 100, 50);
        startButton.setPressedTexture("start_p.png");
        
        // Create quit button
        quitButton = new Button(game, "quit_np.png", 127, 120, 600, 50);
        quitButton.setPressedTexture("quit_p.png");
        
        // Create options button
        optionsButton = new Button(game, "options_np.png", 127, 120, 343, 50);
        optionsButton.setPressedTexture("options_p.png");
	}
}
