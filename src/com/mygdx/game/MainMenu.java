package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenu implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;	// Main menu camera
	
	public MainMenu(final OtterGame gam){
		
		game = gam;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // Screen size to 800x600
	
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
        game.font.draw(game.batch, "Otter Game ", 100, 150);
        game.font.draw(game.batch, "Click anywhere to begin!", 100, 100);
        game.batch.end();
        
        // Checks if screen is clicked and runs game
        if (Gdx.input.isTouched() || Gdx.input.justTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
}
