// Otter game
// Game Over screen
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class GameOver implements Screen {
	
	final OtterGame game;
	OrthographicCamera camera;	// Camera
	Button menu;				// Main menu button
	Button quit;				// Quit button
	Button replay;				// Play again button
	Texture background;			// background
	private int score;			// Score
	private int oldScore;		// Previous score
	String scoreDisplay;		// Score to string
	String timeDisplay;			// Displays round time
	String levelDisplay;
	
	public GameOver(final OtterGame gam, int score, String time, int level){
		
		game = gam;
		timeDisplay = time;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Screen size to 800x600
        this.score = score;
        oldScore = Prefs.getHighScore();
        
        buttons();	// Creates buttons
        scoreToString(score);
        levelToString(level);
        
        // Textures
     	background = new Texture("gamebackground.png");
     	
     	// Update score - must be at end of constructor
     	Prefs.setHighScore(score);
	}
	
	@Override
	public void render(float delta) {
		// Background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1); // Background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Updates camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        // Sprite display - Background MUST be first
        game.batch.begin();
        game.batch.draw(background, 0, 0); // Background
        display();
        game.batch.end();
        
        // Handles button events
        buttonLogic();
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
		
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	// Dispose of textures
	@Override
	public void dispose() {
		menu.dispose();
		quit.dispose();
		replay.dispose();
		background.dispose();
	}
	
	// Displays sprites
	void display(){
		 game.font.draw(game.batch, "Game Over", 360, 210);
		 displayHighScore(); // Displays high score - 240
		 game.font.draw(game.batch, scoreDisplay, 360, 270);
		 game.font.draw(game.batch, timeDisplay, 360, 330);
		 game.font.draw(game.batch, levelDisplay, 360, 300);
		 quit.display();
		 replay.display();
		 menu.display();
	}
	
	// Creates buttons
	private void buttons(){
		// Create quit button
        quit = new Button(game, "quit_np.png", 84, 43, 600, 100);
        quit.setPressedTexture("quit_p.png");
        
        // Create play again button
        replay = new Button(game, "replay_np.png", 132, 43, 350, 100);
        replay.setPressedTexture("replay_p.png");
        
        // Create main menu button
        menu = new Button(game, "menu_np.png", 136, 43, 100, 100);
        menu.setPressedTexture("menu_p.png");
	}
	
	// Handles button events
	private void buttonLogic(){
		// Button logic
        // Re-runs game
        if (replay.isPressed()) {
        	dispose();
            game.setScreen(new GameScreen(game));
        }
        
        // Main menu
        if(menu.isPressed()){
        	dispose();
        	game.setScreen(new MainMenu(game));
        }
        
        // Handles back button for Android
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean keyUp(final int keycode) {
                if (keycode == Keys.BACK) {
                	dispose();
                	game.setScreen(new MainMenu(game));
                }
                return false;
            }
        });
        
        // Exits game
        if(quit.isPressed())
        	System.exit(0);
	}
	
	// How to display score via text
	private void scoreToString(int score){
		scoreDisplay = "Your score this round was: " + score;
	}
	
	// How to display levels
	private void levelToString(int level){
		levelDisplay = "Level reached: " + level;
	}
	
	// Displays score
	private void displayHighScore(){
		
		if(score > oldScore)
			game.font.draw(game.batch, "NEW High Score! - " + score, 360, 240);
		else
			game.font.draw(game.batch, "High Score: " + oldScore, 360, 240);
	
	}
}
