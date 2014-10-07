// Otter game
// Main Menu
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;	// Main menu camera
	Button startButton;			// Main start button
	Button quitButton;			// Menu quit button
	Button optionsButton;		// Menu options button
	
	public MainMenu(final OtterGame gam){
		
		game = gam;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Screen size to 800x480
       
        
        // Creates buttons
        createButtons();
        
        // Sets default options if needed
        Options.defaults();
        
	}
	
	
	// Rendering of main menu
    @Override
    public void render(float delta) {
    	// Background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1); // Background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Updates camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
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
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		startButton.dispose();
		quitButton.dispose();
		
	}
	
	void displaySprites(){
		 game.font.draw(game.batch, "Otter Game ", 108, 180);
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
