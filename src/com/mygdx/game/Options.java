// Options menu
// by Doug Carroll and Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Options implements Screen{
	
	final OtterGame game;
	private OrthographicCamera camera;	// Options camera
	private Button back;				// Back to previous screen
	private MuteButton mute;			// Mute in game music
	private SliderButton musicSlider;	// Volume Slider
	private SliderButton soundSlider;	// Sound effects slider
	
	Options(final OtterGame gam){
		game = gam;
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480); // Screen size to 800x480
		
		buildButtons();
		
	}
	
	public static boolean isMuted(){
		return (Prefs.getMusicVolume() < 0.1f) && (Prefs.getSoundVolume() < 0.1f);
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
		musicSlider.moveButton(true);
		soundSlider.moveButton(true);
		
		// Display screen
		game.batch.begin();
		game.font.draw(game.batch, ("Music Volume: " + (int) (Prefs.getMusicVolume()*10)), 340, 170);
		game.font.draw(game.batch, ("Effects Volume: " + (int) (Prefs.getSoundVolume()*10)), 340, 270);
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
	}
	
	private void logic(){
		
		
		Prefs.setMusicVolume(musicSlider.getPosOnBarH());
		Prefs.setSoundVolume(soundSlider.getPosOnBarH());
		
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
		
	}
	
	// Returns to previous screen
	private void goBack() {
		dispose();
    	game.setScreen(new MainMenu(game));
	}
	
	// Mutes 
	private void mute(){
		Prefs.setMusicVolume(0.0f);
		Prefs.setSoundVolume(0.0f);
		musicSlider.setPosOnBarH(Prefs.getMusicVolume());
		soundSlider.setPosOnBarH(Prefs.getSoundVolume());
	}
	
	// Unmutes
	private void unmute(){
		Prefs.setMusicVolume(1.0f);
		Prefs.setSoundVolume(1.0f);
		musicSlider.setPosOnBarH(Prefs.getMusicVolume());
		soundSlider.setPosOnBarH(Prefs.getSoundVolume());
	}

	
	private void display(){
		back.display();
		mute.display();
		musicSlider.display();
		soundSlider.display();
	}
	
	private void buildButtons(){
		// Create back button
        back = new Button(game, "back_np.png", 40, 64, 50, 400);
        back.setPressedTexture("back_p.png");
        
        //Sliders
        musicSlider = new SliderButton(game, "sliderBackground.png", "sliderKnob.png", 300, 36, 47, 240, 180, true);
        musicSlider.setPosOnBarH(Prefs.getMusicVolume());
        
        soundSlider = new SliderButton(game, "sliderBackground.png", "sliderKnob.png", 300, 36, 47, 240, 280, true);
        soundSlider.setPosOnBarH(Prefs.getSoundVolume());
        
        // Create mute button
        mute = new MuteButton(game, 690, 410);
        
	}
}
