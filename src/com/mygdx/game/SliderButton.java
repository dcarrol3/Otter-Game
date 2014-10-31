// Slider Button
// Handles the sliders
// By Doug Carroll and Jon Jordan
// Only handles horizonal bars

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SliderButton extends Button {
	
	private Texture knobTexture;
	private float xKnob;			// xCoord for knob
	private float yKnob;			// yCoord for knob
	private int knobWidth;			// Width of knob
	public final int OFFSET = 30; 	// Offset for slider touch
	
	
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
			
			// Set knob to bar max
			if(xKnob > ((1.0f * width) + xCoord) - (knobWidth / 2))
				xKnob = ((1.0f * width) + xCoord) - (knobWidth / 2);
			
			// Set knob to bar min
			else if(xKnob < ((0.0f * width) + xCoord) - (knobWidth / 2))
				xKnob = ((0.0f * width) + xCoord) - (knobWidth / 2);
		}
	}
	
	// Override isPressed for slider
	@Override 
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
	@Override 
	boolean isHighlighted(){
		
		boolean highlighted = false;
		float x = game.convertX(Gdx.input.getX());
		float y = game.convertY(Gdx.input.getY());
			
		if(x >= xCoord - OFFSET && x <= xCoord + width + OFFSET){
			if(y <= (game.getHeight() - yCoord) && y >= (game.getHeight() - yCoord) - height)
				highlighted = true;
		}
				
		return highlighted;
	}
		
	
	// Display for slider
	@Override 
	void display(){
		game.batch.draw(button, xCoord, yCoord);
		game.batch.draw(knobTexture, xKnob, yKnob);
	}
	
	

}
