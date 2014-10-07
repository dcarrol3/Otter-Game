// Options menu
// by Doug Carroll and Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Options implements Screen{
	
	OtterGame game;
	OrthographicCamera camera;	// Options camera
	Button back;			// Back to previous screen
	MuteButton mute;		// Mute in game music
	Button musicUp;			// Music volume up
	Button musicDown;		// Music volume down
	private static Preferences saveFile = Gdx.app.getPreferences("OtterGame"); // Options file
	
	Options(OtterGame gam){
		game = gam;
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Screen size to 800x480
		
		buildButtons();
	}
	
	public static float getVolume(){
		return  saveFile.getFloat("Volume");
	}
	
	public static boolean isMuted(){
		return (getVolume() < 0.1f);
	}
	
	
	@Override
	public void render(float delta) {
		// Background
        Gdx.gl.glClearColor(1, 0, 0.2f, 1); // Background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Updates camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
		
		// Check button states
		logic();
		mute.swapMute();
		
		// Display screen
		game.batch.begin();
		game.font.draw(game.batch, ("Volume: " + (int) (getVolume()*10)), 340, 230);
		display();
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

	@Override
	public void dispose() {
		back.dispose();
		mute.dispose();
		musicUp.dispose();
		musicDown.dispose();
		
	}
	
	private void logic(){
		
		// Go back
		if(back.isPressed() || Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			goBack();
		
		// Mute and unmute
		else if(mute.isPressed()){
			if(!isMuted())
				mute();
			else
				unmute();
		}
		
		// Volume up
		else if(musicUp.isPressed())
			volumeUp();
		
		// Volume down
		else if(musicDown.isPressed())
			volumeDown();
		
	}
	
	// Returns to previous screen
	private void goBack() {
		saveFile.flush();	// Saves the save file
		dispose();
    	game.setScreen(new MainMenu(game));
	}
	
	// Mutes
	private void mute(){
		saveFile.putFloat("Volume", 0.0f);
	}
	
	// Unmutes
	private void unmute(){
		saveFile.putFloat("Volume", 1.0f);
	}
	
	// Increases volume
	private void volumeUp(){
		if(getVolume() < 1.0f)
			saveFile.putFloat("Volume", (getVolume() + 0.1f));
	}
	
	// Decreases volume
	private void volumeDown(){
		if(!isMuted())
			saveFile.putFloat("Volume", (getVolume() - 0.1f));
	}
	
	// Load default values if needed
	public static void defaults(){
		
		// Creates volume in file
		if (!saveFile.contains("Volume")) {
			saveFile.putFloat("Volume", 1.0f);
		}
	}
	
	private void display(){
		back.display();
		mute.display();
		musicUp.display();
		musicDown.display();
	}
	
	private void buildButtons(){
		// Create back button
        back = new Button(game, "back_np.png", 40, 64, 50, 400);
        back.setPressedTexture("back_p.png");
        
        // Create mute button
        mute = new MuteButton(game, 350, 240);
        
        // Create Volume up button
        musicUp = new Button(game, "up.png", 48, 48, 420, 240);
        
        // Create Volume down button
        musicDown = new Button(game, "down.png", 48, 48, 280, 240);
	}
	
}
