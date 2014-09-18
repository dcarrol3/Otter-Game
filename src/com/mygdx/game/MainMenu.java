package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;	// Main menu camera
	Button startButton;			// Main start button
	
	public MainMenu(final OtterGame gam){
		
		game = gam;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Screen size to 800x600
        
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
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        // Sprite batch
        game.batch.begin();
        displaySprites();
        game.batch.end();
        
        // Checks if button is clicked and runs game
        
    	if (startButton.isPressed()) 
    		game.setScreen(new GameScreen(game));
        	
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
		
	}
	
	void displaySprites(){
		 game.font.draw(game.batch, "Otter Game ", 108, 180);
		 startButton.display();
	}
	
	// Creates buttons
	void createButtons(){
		// Create start button
        startButton = new Button(game, "start_np.png", 94, 47, 100, 100);
        startButton.setPressedTexture("start_p.png");
	}
}
