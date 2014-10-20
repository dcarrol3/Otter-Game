// Mute button
// Doug Carroll and Jon Jordon

package com.mygdx.game;

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
}
