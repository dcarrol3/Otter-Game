// Mute button
// Doug Carroll and Jon Jordon

package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class MuteButton extends Button{
	
	MuteButton(OtterGame gam, int x, int y){
		// Width/Height is 48/48
		super(gam, "mute.png", 48, 48, x, y);
		swapMute();
	}
	
	// Swaps mute texture as needed
	public void swapMute(){
		if(Options.isMuted()){
			setTexture("muted.png");
			setPressedTexture("muted.png");
		}
		else{
			setTexture("mute.png");
			setPressedTexture("mute.png");
		}
	}
	
	// Checks if button was pressed
	boolean isPressed(){
		boolean pressed = false;
		if(isHighlighted()){
			if(Gdx.input.justTouched()){
				pressed = true;
				sound.play(1.0f); // Plays sound
				// Sleeps so sound can be played
				try {
				    Thread.sleep(200);                
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}
		return pressed;
	}
}
