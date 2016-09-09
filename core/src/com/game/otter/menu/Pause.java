package com.game.otter.menu;

import com.game.otter.game.GameScreen;
import com.game.otter.start.OtterGame;

public class Pause {

	private OtterGame game;
	private Button quit;							// Quits game
	private Button menu;							// Goes to menu
	private Button restart;							// Restarts game
	private Button resume;							// Resumes game
	
	public Pause(OtterGame game){
		this.game = game;
		buildPause();
	}
	
	// Builds pause menu
	void buildPause(){
		// Create quit button
		quit = new Button(game, "quitPause_np.png", 93, 30, 400-(93/2), 150);
        quit.setPressedTexture("quitPause_p.png");
        
        // Create main menu button
        menu = new Button(game, "pauseMenu_np.png", 110, 28, 400-(110/2), 200);
        menu.setPressedTexture("pauseMenu_p.png");
        
        // Create play again button
        restart = new Button(game, "replayPause_np.png", 153, 47, 400-(153/2), 240);
        restart.setPressedTexture("replayPause_p.png");
        
        // Create resume button
        resume = new Button(game, "resume_np.png", 162, 30, 400-(162/2), 305);
        resume.setPressedTexture("resume_p.png");
	}
	
	// Handles button logic for pause menu
	private void buttonLogic(){
		// Button logic - MUST be else-ifs
        
		// Resumes game
		if(resume.isPressed()){
			GameScreen.setState(0);
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
	
	// Pause display
	public void display(){
		// If game is paused
		quit.display();
		restart.display();
		menu.display();
		resume.display();
	}

	public void dispose() {
		quit.dispose();
		menu.dispose();
		resume.dispose();
		restart.dispose();
	}
	
	public void update(){
		buttonLogic();
	}
	
}
