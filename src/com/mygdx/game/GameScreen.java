package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	final OtterGame game;
	Texture clam;					// Main clam texture
	Texture redClam;				// Double score clam texture
	Texture purpClam;				// Turbo clam texture
	OrthographicCamera camera;		// Main game camera
	Player player;
	Shark[] sharkArray;
	final byte STARTINGSHARKS = 5;	// Number of sharks to start with
	final byte MAXSHARKS = 20;
	byte numSharks = STARTINGSHARKS;
	Array<Rectangle> sharks;
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		player = new Player(game);
		
		// Build shark array
		sharkArray = new Shark[MAXSHARKS];
		for (int i = 0; i < STARTINGSHARKS; i++) {
			sharkArray[i] = new Shark(game);
		}
		
		// Textures
		clam = new Texture("yellowclam.png");
		redClam = new Texture("redclam.png");
		purpClam = new Texture("purpleclam.png");
		
		// Sounds
		
		// Camera
		 camera = new OrthographicCamera();
		 camera.setToOrtho(false, 800, 480); // Screen size
	}

	@Override
	public void render(float delta) {
		
		// Clears screen and repaints with background color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    
	    // Updates the screen
	    camera.update();
	    
	    
	    // Tells sprites to render on screen
	    game.batch.setProjectionMatrix(camera.combined);
	    // movements 
	    player.movement();
	    moveSharks();
	    
	    // collision check
	    sharkCollison();
	    
	    //Start new sprite batch
	    game.batch.begin();
	    player.display(); 	// Display player sprite
	    displaySharks(); 	// Display shark sprite
	    game.batch.end();
	    
	    // start new shark sprite batch
	  
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
	
	// Move  sharks
	private void moveSharks(){
		for (int i = 0; i < numSharks; i++) {
			sharkArray[i].movement();
		}
	}
	// display sharks
	private void displaySharks(){
		for (int i = 0; i < numSharks; i++) {
			sharkArray[i].display();
		}
	}
	// check for shark collison and reset
	private void sharkCollison() {
		for (int i = 0; i < numSharks; i++)  { 
			for (int j = 0; j < numSharks; j++) {
				if(sharkArray[i].hitBox.overlaps(sharkArray[j].hitBox) && i != j){ 
					sharkArray[i].respawnShark();
					sharkArray[j].respawnShark();
				}
			}
		}
			
		
	}
}