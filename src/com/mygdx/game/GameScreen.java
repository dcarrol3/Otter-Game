package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	
	final OtterGame game;
	Texture clam;			// Main clam texture
	Texture redClam;		// Double score clam texture
	Texture purpClam;		// Turbo clam texture
	OrthographicCamera camera;		// Main game camera
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		
		// Textures
		clam = new Texture("yellowclam.png");
		redClam = new Texture("redclam.png");
		purpClam = new Texture("purpleclam.png");
		
		// Sounds
		
		// Camera
		 camera = new OrthographicCamera();
	     camera.setToOrtho(false, 800, 600); // Screen size
	}

	@Override
	public void render(float delta) {
		
		// Clears screen and repaints with backround color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        
        // Updates the screen
        camera.update();
        
        
        // Tells sprites to render on screen
        game.batch.setProjectionMatrix(camera.combined);
        
        
        //Start new sprite batch
        game.batch.begin();
        game.batch.draw(clam, 0, 0);   // Main clam
        game.batch.end();
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
	
	// Disposes of objects on screen
	@Override
	public void dispose() {
		clam.dispose();
		redClam.dispose();
		purpClam.dispose();
		
	}
}
