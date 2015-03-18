// Otter Game
// Handles the preferences file
// By Doug Carroll and Jon Jordan

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {
	private static Preferences saveFile = Gdx.app.getPreferences("OtterGame"); // Options/Score file
	
	public static float getMusicVolume(){
		return  Prefs.saveFile.getFloat("MusicVolume");
	}
	
	public static void setMusicVolume(float v){
		Prefs.saveFile.putFloat("MusicVolume", v);
		Prefs.saveFile.flush();	// Saves the save file
	}
	
	public static int getHighScore(){
		return Prefs.saveFile.getInteger("HighScore");
	}
	
	public static void setHighScore(int score){
		Prefs.saveFile.putInteger("HighScore", score);
		Prefs.saveFile.flush();	// Saves the save file
	}
	
	public static float getSoundVolume(){
		return  Prefs.saveFile.getFloat("SoundVolume");
	}
	
	public static void setSoundVolume(float sv){
		Prefs.saveFile.putFloat("SoundVolume", sv);
		Prefs.saveFile.flush();	// Saves the save file
	}
	
	// Load default values if needed
	public static void defaults(){
		
		// Creates volume in file
		if (!saveFile.contains("MusicVolume")) {
			Prefs.saveFile.putFloat("MusicVolume", 1.0f);
		}
		
		// Creates volume in file
		if (!saveFile.contains("SoundVolume")) {
			Prefs.saveFile.putFloat("SoundVolume", 1.0f);
		}
		
		// Creates High score
		if (!saveFile.contains("HighScore")){
			Prefs.saveFile.putInteger("HighScore", 0);
		}
		Prefs.saveFile.flush();	// Saves the save file
	}
}
