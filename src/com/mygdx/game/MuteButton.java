package com.mygdx.game;

public class MuteButton extends Button{
	
	public final static int HEIGHT = 48;
	public final static int WIDTH = 48;
	
	MuteButton(OtterGame gam, int x, int y){
		super(gam, "mute.png", WIDTH, HEIGHT, x, y);
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
