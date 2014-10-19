// Otter Game
// Main game screen
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

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
	ArrayList<Clam> clamList;		// Clam list
	ArrayList<Object> specialList;	// Specials list
	public final int STARTINGSHARKS = 5;	// Number of sharks to start with
	public final int MAXSHARKS = 20;		// Max sharks that will ever be in the game
	public final int STARTINGCLAMS = 3;		// Starting number of clams
	ArrayList<Bullet> bulletList;	// Handles bullets
	Music music;					// Background music
	private float playTimeSec = 0.0f; 	  // Game timer seconds
	private float levelTimeCount;	// Helper for keeping track of time
	private int level;				// Level number
	public final float levelTime = 15.0f; // Time between levels
	public final int levelDifficulty = 2; // Sets shark speed increase per level
	public final float DOUBLETIME = 15.0f;// Time for double bounus
	private int state;				// Controls game state 0 for paused, 1 for running
	private float delta;			// In game time keeper
	Random rand;					// Random
	ArrayList<Float> doubleTimers;	// Handles timers for double bonus
	private float slowMoTimer= 0.0f;// Hanldes SlowMo time
	private int slowMoState = 0;	// Handles state of slowmo
	public final float SLOWMULTI = 0.5f; // Multiplier for slow mo
	public final float SLOWTIME = 15.0f; // Time for SlowMo bonus 
	// Pause objects
	Button quit;					// Quits game
	Button menu;					// Goes to menu
	Button restart;					// Restarts game
	Button resume;					// Resumes game
	
	
	public GameScreen(final OtterGame gam){
		this.game = gam;
		bulletList = new ArrayList<Bullet>();
		sharkList = new ArrayList<Shark>();
		clamList = new ArrayList<Clam>();
		specialList = new ArrayList<Object>();
		doubleTimers = new ArrayList<Float>();
		player = new Player(game, bulletList);
		rand = new Random();
		buildPause();
		state = 1;   // 1 for running, 0 for paused
		level = 1;   // Starting level
		levelTimeCount = 0.0f;
		
		
		// Build shark list
		for (int i = 0; i < STARTINGSHARKS; i++) 
			sharkList.add(new Shark(game, (level-1)*levelDifficulty));
		
		
		// Build clam list
		for (int i = 0; i < STARTINGCLAMS; i++)
			clamList.add(new Clam(game));
		
		// Textures
		background = new Texture("gamebackground.png");
		
		// Sounds
		music = Gdx.audio.newMusic(Gdx.files.internal("runaway.mp3")); // Load in music file
		music.setVolume(Options.getVolume());
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
	
	// Updates game elements
	void update(){
		// If game is running
		if(state == 1){
			// Increases game time
			increaseTime();
			
			// Movements 
		    player.movement();
		    moveSharks();
		    moveClams();
		    moveSpecials();
		    
		    // Collision checks
		    sharkCollision();
		    clamCollision();
		    specialCollision();
		    
		    // Special spawns
		    specialChance();
		    
		    // Logic Checks
		    player.fireCheck();	// Checks if user hits fire button
		    levels();
		    removeSlowMo();
		    delDoubleBonus();
		}
		// If paused
		else
			pause();
		
	}
	
	// Handles display for GameScreen
	void display(){
		game.batch.draw(background, 0, 0); // Background
	    game.font.draw(game.batch, ("Ammo: " + player.getAmmo()), 30, 380);
	    game.font.draw(game.batch, ("Score: " + player.getScore()), 100, 380);
	    // Double bonus
	    if(player.getScoreOffset() > 1){game.font.draw(game.batch, (("x" + player.getScoreOffset())), 170, 380);}
	    // SlowMo
	    if(slowMoState == 1){game.font.draw(game.batch, "Slow-Mo", 190, 450);}
	    game.font.draw(game.batch, playTime(), 100, 450);
	    game.font.draw(game.batch, ("Level: " + level), 30, 450);
	    displayBullets();	// Displays bullets
	    displaySharks(); 	// Displays shark sprites
	    displayClams();		// Displays clam sprites
	    displaySpecials();	// Displays special clams
	    player.display(); 	  // Display player sprite - Must be before sharks
	    player.displayLife(); // Display lives
	}

	// Handles shark movement for the list
	private void moveSharks(){
		for(int i = 0; i < sharkList.size(); i++)
			sharkList.get(i).movement();
	}
	
	// Handles clam movement for the list
	private void moveClams(){
		for(int i = 0; i < clamList.size(); i++)
			clamList.get(i).movement();
	}
	
	// Handles specials movement
	private void moveSpecials(){
		for(int i = 0; i < specialList.size(); i++){
			// Slow mo clams
			if(specialList.get(i) instanceof SlowMoClam)
				((SlowMoClam) specialList.get(i)).movement();
			// Double clams
			else if(specialList.get(i) instanceof DoubleClam)
				((DoubleClam) specialList.get(i)).movement();
			// Health clams
			else if(specialList.get(i) instanceof HealthClam)
				((HealthClam) specialList.get(i)).movement();
		}
	}
	
	// Displays clams
	private void displayClams(){
		for(int i = 0; i < clamList.size(); i++)
			clamList.get(i).display();
	}
	
	// Handles displaying specials
	private void displaySpecials(){
		for(int i = 0; i < specialList.size(); i++){
			// Slow mo clams
			if(specialList.get(i) instanceof SlowMoClam)
				((SlowMoClam) specialList.get(i)).display();
			// Double clams
			else if(specialList.get(i) instanceof DoubleClam)
				((DoubleClam) specialList.get(i)).display();
			// Health clams
			else if(specialList.get(i) instanceof HealthClam)
				((HealthClam) specialList.get(i)).display();
		}
	}
	
	// Displays sharks
	private void displaySharks(){
		for (int i = 0; i < sharkList.size(); i++) 
			sharkList.get(i).display();
	}
	
	// Display bullets and handle bullet movement
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
	
	// If a special is to be spawned this picks which one
	void spawnSpecial(){
		
		int random = rand.nextInt(3)+1; // For special spawns
		
		if(random == 1)
			specialList.add(new SlowMoClam(game, clamList.get(0).getSpeed()));
		
		else if(random == 2)
			specialList.add(new DoubleClam(game, clamList.get(0).getSpeed()));
		
		else if(random == 3)
			specialList.add(new HealthClam(game, clamList.get(0).getSpeed()));
	}
	
	// Handles special spawning
	void specialChance(){
		
		// 5 out of 100 chance per cycle
		int random = rand.nextInt(100) + 1;
		
		if(random <= 5)
			spawnSpecial();
	}
	
	// Special collisions and end of screen
	private void specialCollision(){
		for(int i = 0; i < specialList.size(); i++){
			
			// Player collision or off screen
			// Slow mo clams
			if(specialList.get(i) instanceof SlowMoClam){
				// Player
				if(((SlowMoClam)specialList.get(i)).hitBox.overlaps(player.hitBox)){
					specialList.remove(i);
					player.hitByClam();
					addSlowMo();		// Starts slow mo
				}
				// Off screen
				else if(((SlowMoClam)specialList.get(i)).getxCoord() > game.getWidth() + 200){
					specialList.remove(i);
				}
			}	
			// Double clams
			else if(specialList.get(i) instanceof DoubleClam){
				// Player
				if(((DoubleClam)specialList.get(i)).hitBox.overlaps(player.hitBox)){
					specialList.remove(i);
					player.hitByClam();
					addDoubleBonus();
				}
				// Off screen
				else if(((DoubleClam)specialList.get(i)).getxCoord() > game.getWidth() + 200){
					specialList.remove(i);
				}
			}
			// Health clams
			else if(specialList.get(i) instanceof HealthClam){
				// Player
				if(((HealthClam)specialList.get(i)).hitBox.overlaps(player.hitBox)){
					specialList.remove(i);
					player.hitByClam();
					player.addLife();	// Add life bonus
				
				}
				// Off screen
				else if(((HealthClam)specialList.get(i)).getxCoord() > game.getWidth() + 200){
					specialList.remove(i);
				}
				
			}
		}
	}
	
	// Clam collision
	private void clamCollision(){
		
		for(int i = 0; i < clamList.size(); i++){
			// Player his clam
			if(clamList.get(i).hitBox.overlaps(player.hitBox)){
				clamList.get(i).respawnClam(); // Kill clam
				player.hitByClam();
			}
			// Clam hits clam
			for(int j = 0; j < clamList.size(); j++){
				// Clam hits clam
				if(clamList.get(i).hitBox.overlaps(clamList.get(j).hitBox) && i != j){ 
					clamList.get(i).respawnClam();
					clamList.get(j).respawnClam();
				}
			}
			// Clam spawns on special
			for(int k = 0; k < specialList.size(); k++){
				// Respawn that special
				// Slow mo clams
				if(specialList.get(k) instanceof SlowMoClam)
					if(((SlowMoClam)specialList.get(k)).hitBox.overlaps(clamList.get(i).hitBox))
						((SlowMoClam) specialList.get(k)).respawnClam();
				// Double clams
				else if(specialList.get(k) instanceof DoubleClam)
					if(((DoubleClam)specialList.get(k)).hitBox.overlaps(clamList.get(i).hitBox))
						((DoubleClam) specialList.get(k)).respawnClam();
				// Health clams
				else if(specialList.get(k) instanceof HealthClam)
					if(((HealthClam)specialList.get(k)).hitBox.overlaps(clamList.get(i).hitBox))
						((HealthClam) specialList.get(k)).respawnClam();
			}
		}
	}
	
 	// Collision check with sharks
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
			// If shark spawns on clam at same speed
			for (int l = 0; l < clamList.size(); l++){
				if(sharkList.get(i).getSpeed() == sharkList.get(i).STARTSPEED 
				&& clamList.get(l).hitBox.overlaps(sharkList.get(i).hitBox))
					// Respawn that clam
					clamList.get(l).respawnClam();
			}
			// If shark spawns on special clam at same speed
			for(int m = 0; m < specialList.size(); m++){
				if(sharkList.get(i).getSpeed() == sharkList.get(i).STARTSPEED){
					// Respawn that special
					// Slow mo clams
					if(specialList.get(m) instanceof SlowMoClam)
						if(((SlowMoClam)specialList.get(m)).hitBox.overlaps(sharkList.get(i).hitBox))
							((SlowMoClam) specialList.get(m)).respawnClam();
					// Double clams
					else if(specialList.get(m) instanceof DoubleClam)
						if(((DoubleClam)specialList.get(m)).hitBox.overlaps(sharkList.get(i).hitBox))
							((DoubleClam) specialList.get(m)).respawnClam();
					// Health clams
					else if(specialList.get(m) instanceof HealthClam)
						if(((HealthClam)specialList.get(m)).hitBox.overlaps(sharkList.get(i).hitBox))
							((HealthClam) specialList.get(m)).respawnClam();
				}	
			}
		}		
	}
	
	// Handles adding double bounses
	void addDoubleBonus(){
		doubleTimers.add(playTimeSec); // Starts new double timer
		player.increaseScoreMulti(1);
	}
	
	// Handles removing double bonuses - runs in update
	void delDoubleBonus(){
		
		for(int i = 0; i < doubleTimers.size(); i++)
			if((doubleTimers.get(i) + DOUBLETIME) <= playTimeSec){
				player.decreaseScoreMulti(1);
				doubleTimers.remove(i);
			}	
	}
	
	// Adds SlowMo
	void addSlowMo(){
		
		
		slowMoTimer = playTimeSec; // Start slow mo timer
		
		if(slowMoState == 0){
			
			slowMoState = 1;
			
			
			// Slow sharks down
			for(int i = 0; i < sharkList.size(); i++){
				sharkList.get(i).setSpeed(sharkList.get(i).getSpeed() * SLOWMULTI);
			}
			
			// Slow bullets down
			for(int i = 0; i < player.bulletList.size(); i++){
				bulletList.get(i).setSpeed(bulletList.get(i).getSpeed() * SLOWMULTI);
			}
			
			// Slow future bullets down
			player.setBulletSpeed(player.getBulletSpeed() * SLOWMULTI);
			
			// Slow player movement
			player.setSpeed(player.getSpeed() * SLOWMULTI);
			
			
			// Slow clams
			for(int i = 0; i < clamList.size(); i++){
				clamList.get(i).setSpeed(clamList.get(i).getSpeed() * SLOWMULTI);
			}
			
			// Slow specials
			for(int i = 0; i < specialList.size(); i++){
				
				if(specialList.get(i) instanceof SlowMoClam)
					((SlowMoClam) specialList.get(i)).setSpeed(((SlowMoClam) specialList.get(i)).getSpeed() * SLOWMULTI);
				
				else if(specialList.get(i) instanceof DoubleClam)
					((DoubleClam) specialList.get(i)).setSpeed(((DoubleClam) specialList.get(i)).getSpeed() * SLOWMULTI);
				
				else if(specialList.get(i) instanceof HealthClam)
					((HealthClam) specialList.get(i)).setSpeed(((HealthClam) specialList.get(i)).getSpeed() * SLOWMULTI);
			}
		}
		
	}
	
	// Removes SlowMo - runs in update
	void removeSlowMo(){
		
		if(slowMoState == 1 && (slowMoTimer + SLOWTIME <= playTimeSec)){
			
			// Resume sharks
			for(int i = 0; i < sharkList.size(); i++){
				sharkList.get(i).setSpeed(sharkList.get(i).getSpeed() / SLOWMULTI);
				System.out.println("hi");
			}
			
			// Resume bullets
			for(int i = 0; i < player.bulletList.size(); i++){
				bulletList.get(i).setSpeed(bulletList.get(i).getSpeed() / SLOWMULTI);
			}
			
			// Slow future bullets down
			player.setBulletSpeed(player.getBulletSpeed() / SLOWMULTI);
			
			// Resume player movement
			player.setSpeed(player.getSpeed() / SLOWMULTI);
			
			// Resume clams
			for(int i = 0; i < clamList.size(); i++){
				clamList.get(i).setSpeed(clamList.get(i).getSpeed() / SLOWMULTI);
			}
			
			// Resume specials
			for(int i = 0; i < specialList.size(); i++){
				
				if(specialList.get(i) instanceof SlowMoClam)
					((SlowMoClam) specialList.get(i)).setSpeed(((SlowMoClam) specialList.get(i)).getSpeed() / SLOWMULTI);
				
				else if(specialList.get(i) instanceof DoubleClam)
					((DoubleClam) specialList.get(i)).setSpeed(((DoubleClam) specialList.get(i)).getSpeed() / SLOWMULTI);
				
				else if(specialList.get(i) instanceof HealthClam)
					((HealthClam) specialList.get(i)).setSpeed(((HealthClam) specialList.get(i)).getSpeed() / SLOWMULTI);
			}
			
			slowMoState = 0; // Reset Slow Mo state
			
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
			game.setScreen(new GameOver(game, player.getScore(), playTime(), level));
		}
	}
	
	// Handles levels
	void levels(){
		
		levelTimeCount += delta;

		// Checks if hits level time, must be 0.03 instead of 0
		if(levelTimeCount >= levelTime){
			level++;
			spawnSpecial();
			// Increase sharks speed
			for (int i = 0; i < sharkList.size(); i++) {
				
				// Check slow mo state
				if(slowMoState == 0)
					sharkList.get(i).setSpeed((sharkList.get(i).getSpeed() + levelDifficulty));
				else
					sharkList.get(i).setSpeed((sharkList.get(i).getSpeed() + (levelDifficulty * SLOWMULTI)));
			}
			// Add shark (speed constructor for slowmo)
			sharkList.add(new Shark(game, 0, sharkList.get(0).getSpeed()));
			levelTimeCount = 0.0f;
		}		
	}
	
	// Increases game time
	void increaseTime(){
		playTimeSec += delta;
	}
	
	// Handles playtime and converting it to string
	String playTime(){
		
			int playTimeMin = (int) (playTimeSec / 60);
			int secondsOffset = playTimeMin;
			
		return "Time: " + playTimeMin + (Math.round((playTimeSec - (60 * secondsOffset))) < 10? ":0" : ":") + Math.round((playTimeSec - (60 * secondsOffset)));
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





