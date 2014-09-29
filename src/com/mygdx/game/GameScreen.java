// Otter Game
// Main game screen
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;		// Main game camera
	Texture background;				// Background texture
	Player player;					// Player or otter
	Shark[] sharkArray;				// Shark array
	final int STARTINGSHARKS = 5;	// Number of sharks to start with
	final int MAXSHARKS = 20;		// Max sharks that will ever be in the game
	int numSharks = STARTINGSHARKS; // Keeps track of number of sharks
	int score;						// Handles player score
	ArrayList<Bullet> bulletList;	// Handles bullets
	Music music;					// Background music
	private float playTimeSec = 0.0f; 	  // Game timer seconds
	private int playTimeMin = 0;	// Game time minutes
	private float levelTimeCount;	// Helper for keeping track of time
	private int level;				// Level number
	public final float levelTime = 15.0f; // Time between levels
	public final int levelDifficulty = 2; // Sets shark speed increase per level
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		bulletList = new ArrayList<Bullet>();  
		player = new Player(game, bulletList);
		score = 0;
		level = 0;
		levelTimeCount = 0.0f;
		
		
		// Build shark array
		sharkArray = new Shark[MAXSHARKS];
		for (int i = 0; i < STARTINGSHARKS; i++) {
			sharkArray[i] = new Shark(game);
		}
		
		// Textures
		background = new Texture("gamebackground.png");
		
		// Sounds
		music = Gdx.audio.newMusic(Gdx.files.internal("runaway.mp3")); // Load in music file
		music.play(); // Play music
		 
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
	    
	    // Logic Checks
	    player.fireCheck();	// Checks if user hits fire button
	    levels(delta);
	    
	    // collision check with sharks and player
	    sharkCollision();
	    ifDead();
	    
	    //Start new sprite batch - Backround MUST be first!
	    game.batch.begin();
	    game.batch.draw(background, 0, 0); // Background
	    game.font.draw(game.batch, ("Ammo: " + player.getAmmo()), 100, 380);
	    game.font.draw(game.batch, playTime(delta), 100, 450);
	    game.font.draw(game.batch, ("Level: " + level), 30, 450);
	    displayBullets();	// Displays bullets
	    displaySharks(); 	// Display shark sprite
	    player.display(); 	  // Display player sprite - Must be before sharks
	    player.displayLife(); // Display lives
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
		music.stop(); // Stop music
		background.dispose();
		player.dispose();
		
		// Dispose of sharks
		for (int i = 0; i < numSharks; i++)  {
			sharkArray[i].dispose();
		}
	}
	
	
	// Display and move sharks
	private void displaySharks(){
		for (int i = 0; i < numSharks; i++) {
			sharkArray[i].movement();
			sharkArray[i].display();
		}
	}
	
	// Display bulets
	private void displayBullets(){
		for (int i = 0; i < bulletList.size(); i++){
			// Handles bullet movement
			bulletList.get(i).movement();
			
			// Display bullet
			bulletList.get(i).display();
			
			// Check if bullet goes off screen
			if(bulletList.get(i).getxCoord() < -100 || bulletList.get(i).getyCoord() < -100 || bulletList.get(i).getyCoord() > game.getHeight() + 100){
					bulletList.get(i).dispose();	
					bulletList.remove(bulletList.get(i));
			}		
		}
	}
 	// collision check with sharks
	private void sharkCollision() {
		for (int i = 0; i < numSharks; i++)  {
			
			// Player hits shark
			if(sharkArray[i].hitBox.overlaps(player.hitBox))
				player.hitByShark();
			
			// If sharks hit sharks
			for (int j = 0; j < numSharks; j++) {
				// Shark hits shark
				if(sharkArray[i].hitBox.overlaps(sharkArray[j].hitBox) && i != j){ 
					sharkArray[i].respawnShark();
					sharkArray[j].respawnShark();
				}
			}
			// If bullets hit sharks kill shark and remove bullet
			for (int k = 0; k < bulletList.size(); k++){
				if(sharkArray[i].hitBox.overlaps(bulletList.get(k).hitBox)){
					sharkArray[i].respawnShark();
					bulletList.remove(bulletList.get(k));
				}
			}
			
		}
			
		
	}
	
	// When lives are gone
	void ifDead(){
		
		if(player.getLives() <= 0){
			// Time for sounds
			try {
			    Thread.sleep(300);                
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			dispose();
			game.setScreen(new GameOver(game, score));
		}
	}
	
	// Handles levels
	void levels(float delta){
		
		levelTimeCount += delta;
		// Checks if hits level time, must be 0.03 instead of 0
		if(levelTimeCount >= levelTime){
			level++;
			Shark.setSpeed(Shark.getSpeed() + levelDifficulty);
			levelTimeCount = 0.0f;
		}		
	}
	
	// Handles playtime and converting it to string
	String playTime(float delta){
		 
		 playTimeSec += delta;
		 // Handles converting to minutes
		 if(playTimeSec >= 60){
			 playTimeMin++;
			 playTimeSec = 0.0f;
		 }	
		 
		 return "Time: " + playTimeMin + (Math.round(playTimeSec) < 10? ":0" : ":") + Math.round(playTimeSec);
	}
}