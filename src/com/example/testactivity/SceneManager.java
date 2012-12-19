package com.example.testactivity;

import org.andengine.entity.scene.Scene;

public class SceneManager {

	private static TestActivity game;
	private static SceneManager sm;
	
	private Scene mScene;
	
	private SceneManager() {}
	
	public static void init(TestActivity g)
	{
		SceneManager.game = g;
	}
	
	public static SceneManager getManager() {
		if(game == null)
			throw new IllegalStateException("You must initialize the game");
		if(sm == null)
			return sm = new SceneManager();
		
		return sm;
	}
	
	public void setGameScreen() {
		mScene = game.getScene();
		game.getEngine().setScene(mScene);
	}
	
	public Scene getCurrScene(){
		return mScene;
	}
	
}
