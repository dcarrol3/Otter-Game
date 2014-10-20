// Otter Game
// Handles the preferences file
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
	private static Preferences saveFile = Gdx.app.getPreferences("OtterGame"); // Options/Score file
	
	public static float getVolume(){
		return  saveFile.getFloat("Volume");
	}
	
	public static void setVolume(float v){
		Prefs.saveFile.putFloat("Volume", 0.0f);
		Prefs.saveFile.flush();	// Saves the save file
	}
	
	public static int getHighScore(){
		return saveFile.getInteger("HighScore");
	}
	
	public static void setHighScore(int score){
		saveFile.putInteger("HighScore", score);
		Prefs.saveFile.flush();	// Saves the save file
	}
	
	// Load default values if needed
	public static void defaults(){
		
		// Creates volume in file
		if (!saveFile.contains("Volume")) {
			saveFile.putFloat("Volume", 1.0f);
		}
		// Creates High score
		if (!saveFile.contains("HighScore")){
			saveFile.putInteger("HighScore", 0);
		}
		Prefs.saveFile.flush();	// Saves the save file
	}
}
