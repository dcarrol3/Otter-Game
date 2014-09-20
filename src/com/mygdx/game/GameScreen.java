// Otter Game
// Main game screen
// By Doug Carroll and Jon Jordan

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
	OrthographicCamera camera;		// Main game camera
	Texture background;				// Background texture
	Player player;					// Player or otter
	Shark[] sharkArray;				// Shark array
	final int STARTINGSHARKS = 5;	// Number of sharks to start with
	final int MAXSHARKS = 20;		// Max sharks that will ever be in the game
	int numSharks = STARTINGSHARKS; // Keeps track of number of sharks
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		player = new Player(game);
		
		// Build shark array
		sharkArray = new Shark[MAXSHARKS];
		for (int i = 0; i < STARTINGSHARKS; i++) {
			sharkArray[i] = new Shark(game);
		}
		
		// Textures
		background = new Texture("gamebackground.png");
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
	    
	    // collision check with sharks and player
	    sharkCollision();
	    ifDead();
	    
	    //Start new sprite batch - Backround MUST be first!
	    game.batch.begin();
	    game.batch.draw(background, 0, 0); // Background
	    player.display(); 	// Display player sprite - Must be before sharks
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
		
		game.setScreen(new GameOver(game));
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
	// collision check with sharks and player
	private void sharkCollision() {
		for (int i = 0; i < numSharks; i++)  { 
			for (int j = 0; j < numSharks; j++) {
				// Shark hits shark
				if(sharkArray[i].hitBox.overlaps(sharkArray[j].hitBox) && i != j){ 
					sharkArray[i].respawnShark();
					sharkArray[j].respawnShark();
				}
				// Player hits shark
				if(sharkArray[j].hitBox.overlaps(player.hitBox))
					player.hitByShark();
				
				}
		}
			
		
	}
	void ifDead(){
		
		if(player.getLives() <= 0)
			dispose();
	}
}