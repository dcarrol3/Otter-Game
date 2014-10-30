// Slider Button
// Handles the sliders
// By Doug Carroll and Jon Jordan
// Only handles horizonal bars now.

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SliderButton extends Button {
	
	private Texture knobTexture;
	private float xKnob;		// xCoord for knob
	private float yKnob;		// yCoord for knob
	private int knobWidth;		// Width of knob
	
	
	public SliderButton(OtterGame gam, String bar, String knob, int wBar, int wKnob, int hKnob, int x, int y, boolean moves) {
		super(gam, bar, wBar, hKnob, x, y);
		
		knobTexture = new Texture(knob);
		xKnob = x;
		yKnob = y;
		knobWidth = wKnob;
	}
	
	// Returns position on bar from 0.0 to 1.0
	// Horizontal
	float getPosOnBarH(){
		return ((xKnob + knobWidth / 2) - xCoord) / width; 
	}
	
	// Pos is a float from 0.0 to 1.0
	void setPosOnBarH(float pos){
		xKnob = ((pos * width) + xCoord) - (knobWidth / 2);
	}
	
	
	
	// Handles moving button for slider
	void moveButton(boolean horizontal){
		
		// Horizontal movement
		if(isPressed() && horizontal){
			xKnob = game.convertX(Gdx.input.getX()) - (knobWidth / 2);
		}
		
		// Vertical movement
		if(isPressed() && !(horizontal)){
			yKnob = game.convertY(Gdx.input.getY());
		}
		
	}
	
	// Override isPressed for slider
	boolean isPressed(){
		boolean pressed = false;
		if(isHighlighted()){
			if(Gdx.input.isTouched()){
				pressed = true;
			}
		}
		return pressed;
	}
	
	
	// Override of button isHighlighted
	boolean isHighlighted(){
		
		boolean highlighted = false;
		float x = game.convertX(Gdx.input.getX());
		float y = game.convertY(Gdx.input.getY());
			
		if(x >= xCoord && x <= xCoord + width){
			if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
				highlighted = true;
		}
				
		return highlighted;
	}
		
	
	// Display for slider
	void display(){
		game.batch.draw(button, xCoord, yCoord);
		game.batch.draw(knobTexture, xKnob, yKnob);
	}
	
	

}
