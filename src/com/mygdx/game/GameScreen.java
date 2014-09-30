// Otter Game
// Main game screen
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;		// Main game camera
	Texture background;				// Background texture
	Player player;					// Player or otter
	ArrayList<Shark> sharkList;		// Shark list
	final int STARTINGSHARKS = 5;	// Number of sharks to start with
	final int MAXSHARKS = 20;		// Max sharks that will ever be in the game
	int score;						// Handles player score
	ArrayList<Bullet> bulletList;	// Handles bullets
	Music music;					// Background music
	private float playTimeSec = 0.0f; 	  // Game timer seconds
	private int playTimeMin = 0;	// Game time minutes
	private float levelTimeCount;	// Helper for keeping track of time
	private int level;				// Level number
	public final float levelTime = 15.0f; // Time between levels
	public final int levelDifficulty = 2; // Sets shark speed increase per level
	private int state;				// Controls game state 0 for paused, 1 for running
	private float delta;			// In game time keeper
	// Pause objects
	Button quit;					// Quits game
	Button menu;					// Goes to menu
	Button restart;					// Restarts game
	Button resume;					// Resumes game
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		bulletList = new ArrayList<Bullet>();
		sharkList = new ArrayList<Shark>();
		player = new Player(game, bulletList);
		buildPause();
		score = 0;	 // Starting score	
		state = 1;   // 1 for running, 0 for paused
		level = 1;   // Starting level
		levelTimeCount = 0.0f;
		
		
		// Build shark list
		for (int i = 0; i < STARTINGSHARKS; i++) {
			sharkList.add(new Shark(game, (level-1)*levelDifficulty));
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public void render(float delta) {
		
		// Clears screen and repaints with background color
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	    
	    // Updates the screen
	    camera.update();
	    
	    this.delta = delta;
	    
	    // Tells sprites to render on screen
	    game.batch.setProjectionMatrix(camera.combined);
	    
	    // Checks for game state
	    gameState();
	    
	    // Updates game screen
	    update();
	    
	    //Start new sprite batch
	    game.batch.begin();
	    display();
	    pauseDisplay();  // Pause display - MUST be after normal display
	    game.batch.end();
	   
	    // Checks if player is dead - MUST be at bottom of render
	    ifDead();
	  
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
	
	// Handles pause menu
	@Override
	public void pause() {
		buttonLogic();
	}

	@Override
	public void resume() {}
	
	// Disposes of objects on screen
	@Override
	public void dispose() {
		music.stop(); // Stop music
		background.dispose();
		player.dispose();
		
		// Dispose of sharks
		sharkList.clear();
		
		// Pause dispose
		quit.dispose();
		menu.dispose();
		restart.dispose();
	}
	
	
	private void moveSharks(){
		for(int i = 0; i < sharkList.size(); i++)
			sharkList.get(i).movement();
	}
	
	// Display and move sharks
	private void displaySharks(){
		for (int i = 0; i < sharkList.size(); i++) 
			sharkList.get(i).display();
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
		for (int i = 0; i < sharkList.size(); i++)  {
			
			// Player hits shark
			if(sharkList.get(i).hitBox.overlaps(player.hitBox))
				player.hitByShark();
			
			// If sharks hit sharks
			for (int j = 0; j < sharkList.size(); j++) {
				// Shark hits shark
				if(sharkList.get(i).hitBox.overlaps(sharkList.get(j).hitBox) && i != j){ 
					sharkList.get(i).respawnShark();
					sharkList.get(j).respawnShark();
				}
			}
			// If bullets hit sharks kill shark and remove bullet
			for (int k = 0; k < bulletList.size(); k++){
				if(sharkList.get(i).hitBox.overlaps(bulletList.get(k).hitBox)){
					sharkList.get(i).respawnShark();
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
			game.setScreen(new GameOver(game, score, playTime(), level));
		}
	}
	
	// Handles levels
	void levels(){
	
		levelTimeCount += delta;

		// Checks if hits level time, must be 0.03 instead of 0
		if(levelTimeCount >= levelTime){
			level++;
			// Increase sharks speed
			for (int i = 0; i < sharkList.size(); i++) {
				sharkList.get(i).setSpeed((sharkList.get(i).getSpeed() + levelDifficulty));
			}
			// Add shark
			sharkList.add(new Shark(game, (level-1)*levelDifficulty));
			levelTimeCount = 0.0f;
		}		
	}
	
	// Increases game time
	void increaseTime(){
		playTimeSec += delta;
	}
	
	// Handles playtime and converting it to string
	String playTime(){
		
		// Handles converting to minutes
		if(playTimeSec >= 60){
			playTimeMin++;
			playTimeSec = 0.0f;
		}	
		 
		return "Time: " + playTimeMin + (Math.round(playTimeSec) < 10? ":0" : ":") + Math.round(playTimeSec);
	}
	
	// Sets game state
	void gameState(){
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			// Pause
			if(state == 1){
				state = 0;
				music.pause(); // Pause music
			}
			// Resume
			else if(state == 0){
				state = 1;
				music.play();
			}
		}
	}
	
	// Updates game elements
	void update(){
		// If game is running
		if(state == 1){
			// Increases game time
			increaseTime();
			
			// movements 
		    player.movement();
		    moveSharks();
		    
		    // collision check with sharks and player
		    sharkCollision();
		    
		    // Logic Checks
		    player.fireCheck();	// Checks if user hits fire button
		    levels();
		}
		// If paused
		else
			pause();
		
	}
	
	// Handles display for GameScreen
	void display(){
		game.batch.draw(background, 0, 0); // Background
	    game.font.draw(game.batch, ("Ammo: " + player.getAmmo()), 100, 380);
	    game.font.draw(game.batch, playTime(), 100, 450);
	    game.font.draw(game.batch, ("Level: " + level), 30, 450);
	    displayBullets();	// Displays bullets
	    displaySharks(); 	// Display shark sprite
	    player.display(); 	  // Display player sprite - Must be before sharks
	    player.displayLife(); // Display lives
	}
	
	// Pause display
	void pauseDisplay(){
		// If game is paused
		if(state == 0){
			quit.display();
			restart.display();
			menu.display();
			resume.display();
		}
	}
	
	// Builds pause menu
	void buildPause(){
		// Create quit button
		quit = new Button(game, "quit_np.png", 84, 43, 400-(84/2), 150);
        quit.setPressedTexture("quit_p.png");
        
        // Create main menu button
        menu = new Button(game, "menu_np.png", 136, 43, 400-(136/2), 200);
        menu.setPressedTexture("menu_p.png");
        
        // Create play again button
        restart = new Button(game, "replay_np.png", 132, 43, 400-(132/2), 250);
        restart.setPressedTexture("replay_p.png");
        
        // Create resume button
        resume = new Button(game, "resume_np.png", 113, 43, 400-(113/2), 300);
        resume.setPressedTexture("resume_p.png");
	}
	
	// Handles button logic for pause menu
	private void buttonLogic(){
		// Button logic - MUST be else-ifs
        
		// Resumes game
		if(resume.isPressed()){
			state = 1;
			music.play();
		}
		// Re-runs game
		else if (restart.isPressed()) {
        	dispose();
        	game.setScreen(new GameScreen(game));
        }
        
        // Main menu
		else if(menu.isPressed()){
        	dispose();
        	game.setScreen(new MainMenu(game));
        }
        
        // Exits game
		else if(quit.isPressed())
        	System.exit(0);
	}
}





