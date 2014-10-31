// Otter game
// Main Menu
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MainMenu implements Screen {
	
	final OtterGame game;
	private Button startButton;		// Main start button
	private Button quitButton;		// Menu quit button
	private Button optionsButton;	// Menu options button
	
	public MainMenu(final OtterGame gam){
		
		game = gam;
		
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
        displaySprites();
        game.batch.end();
        
        // Checks if start is clicked and runs game
        if (startButton.isPressed()){ 
        	dispose();
    		game.setScreen(new GameScreen(game));
        }
        if (optionsButton.isPressed()){
        	dispose();
        	game.setScreen(new Options(game));
        }
        // Checks if quit is hit and exits game
        if(quitButton.isPressed())
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
		 game.font.draw(game.batch, ("Conx: " + game.convertX(100)), 108, 360);
		 game.font.draw(game.batch, ("Cony: " + game.convertY(800)), 108, 340);
		 game.font.draw(game.batch, ("xCoord: " + Gdx.input.getX()), 108, 320);
		 game.font.draw(game.batch, ("yCoord: " + Gdx.input.getY()), 108, 300);
		 game.font.draw(game.batch, "Otter Game ", 108, 180);
		 game.font.draw(game.batch, "High Score: " + Prefs.getHighScore(), 353, 180); // Displays high score
		 startButton.display();
		 quitButton.display();
		 optionsButton.display();
	}
	
	// Creates buttons
	void createButtons(){
		// Create start button
        startButton = new Button(game, "start_np.png", 84, 43, 100, 100);
        startButton.setPressedTexture("start_p.png");
        
        // Create quit button
        quitButton = new Button(game, "quit_np.png", 84, 43, 600, 100);
        quitButton.setPressedTexture("quit_p.png");
        
        // Create options button
        optionsButton = new Button(game, "options_np.png", 110, 43, 343, 100);
        optionsButton.setPressedTexture("options_p.png");
	}
}
