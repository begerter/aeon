package com.example.testactivity;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.font.*;

import android.graphics.Typeface;
import android.view.MotionEvent;

public class BattleScene extends Scene implements IOnSceneTouchListener {
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private ITextureRegion mBackgroundTextureRegion, mRing1, mRing2, mRing3;
	private Texture mTexture,mEnemyTexture;
	private BuildableBitmapTextureAtlas mTextureAtlas;
	private ITiledTextureRegion mPlayerTextureRegion,mEnemyTextureRegion;
	private Sprite mTower3;
	private AnimatedSprite mTower1, mTower2;
	private Stack mStack1, mStack2, mStack3;
	private float x_vel,y_vel,enemy_x_vel,enemy_y_vel,x_pos,y_pos,x_start,y_start;
	private boolean flip = false;
	private boolean released = true;
	private Font mFont;
	private FontManager mTextFont;
	private Text mText;
	private int hits;
	private TestActivity act;
	private Scene returnScene;
	

	public BattleScene(Scene s, TestActivity activity) {
		returnScene = s;
		onCreateScene(activity);
	}
	

	protected Scene onCreateScene(TestActivity a) {
		act = a;
		// 1 - Create new scene
		Sprite backgroundSprite = new Sprite(0, 0, a.mBattleBackgroundTextureRegion, a.getVertexBufferObjectManager());
		attachChild(backgroundSprite);
		// 2 - Add the towers
		mTower1 = new AnimatedSprite(300,100, a.mPlayerTextureRegion, a.getVertexBufferObjectManager());
		mTower2 = new AnimatedSprite(500,200, a.mEnemyTextureRegion, a.getVertexBufferObjectManager());
		attachChild(mTower1);
		attachChild(mTower2);
		hits = 0;
		// 6 - Add touch handlers
		setOnSceneTouchListener(this);
		mTower2.animate(200);

		mFont = FontFactory.create(a.getFontManager(), a.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();
		mText = new Text(100, 40, this.mFont,"", 200, a.getVertexBufferObjectManager());
		attachChild(mText);
		return this;
	}
	
	public void onUpdate()
	{
		float X  = mTower1.getX();
		float Y = mTower1.getY();
		if(x_vel > 0 && !flip) {
		    flip = true;
		    mTower1.setFlippedHorizontal(true);
		}
		if(x_vel < 0 && flip) {
		    flip = false;
		    mTower1.setFlippedHorizontal(false);
		}
		float enemyX = mTower2.getX();
		float enemyY = mTower2.getY();
		if(mTower1.collidesWith(mTower2) && ((x_vel > 0 && X <= enemyX)  || (x_vel < 0 && X >= enemyX))){
			x_vel = 0;
			y_vel = 0;
			mTower1.stopAnimation();
		}
		mTower1.setPosition(X + x_vel, Y + y_vel);
		mText.setText(String.valueOf(x_vel));
		if((x_vel > 0 && x_pos <= X)  || (x_vel < 0 && x_pos >= X) || (y_vel > 0 && y_pos <= Y) || (y_vel < 0 && y_pos >= Y)) {
		    x_vel = 0;
		    y_vel = 0;
		    mTower1.stopAnimation();
		}
		mTower2.setPosition(mTower2.getX() + enemy_x_vel, mTower2.getY() + enemy_y_vel);
		enemy_x_vel = (float)0.5*(mTower1.getX() - mTower2.getX())/(Math.abs(mTower1.getX() - mTower2.getX()));
     	enemy_y_vel = (float)0.5*(mTower1.getY() - mTower2.getY())/Math.abs(mTower1.getX() - mTower2.getX());
     	
     	while(Math.abs(enemy_x_vel) > 2 || Math.abs(enemy_y_vel) > 2) {
     		enemy_y_vel /= 2;
     	    enemy_x_vel /= 2;
     	}
		if(mTower1.collidesWith(mTower2) && ((enemy_x_vel > 0 && mTower2.getX() <= mTower1.getX())  || (enemy_x_vel < 0 && mTower2.getX() >= mTower1.getX()))){
			enemy_x_vel = 0;
			enemy_y_vel = 0;
		}
	}
	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        int myEventAction = pSceneTouchEvent.getAction(); 


        switch (myEventAction) {
           case MotionEvent.ACTION_UP: {
                x_pos = pSceneTouchEvent.getX();
                y_pos = pSceneTouchEvent.getY();
                released = true;
        	    float xdif,ydif;
        	    xdif = x_start - x_pos;
        	    ydif = y_start - y_pos;
	        	if(xdif*xdif + ydif*ydif > 500)
	        	{
	        		hits++;
	        		if(x_start <= mTower2.getX() && x_pos >= mTower2.getX() && y_start <= mTower2.getY() && y_pos >= mTower2.getY())
	        		{
	        			if(!mTower1.isFlippedHorizontal())
	        				mTower1.setFlippedHorizontal(true);
	        			mText.setText("Down slash");
	        		}
	        		else if(x_start <= mTower2.getX() && x_pos >= mTower2.getX() && y_start >= mTower2.getY() && y_pos <= mTower2.getY())
	        		{
	        			if(!mTower1.isFlippedHorizontal())
	        				mTower1.setFlippedHorizontal(true);
	        			mText.setText("Up slash");
	        		}
	        		else if(x_start >= mTower2.getX() && x_pos <= mTower2.getX() && y_start >= mTower2.getY() && y_pos <= mTower2.getY())
	        		{
	        			if(mTower1.isFlippedHorizontal())
	        				mTower1.setFlippedHorizontal(false);
	        			mText.setText("Up slash");
	        		}
	        		else if(x_start >= mTower2.getX() && x_pos <= mTower2.getX() && y_start <= mTower2.getY() && y_pos >= mTower2.getY())
	        		{
	        			if(mTower1.isFlippedHorizontal())
	        				mTower1.setFlippedHorizontal(false);
	        			mText.setText("Down slash");
	        		}
	        		else
	        		{
	        			mText.setText("Missed");
	        			hits--;
	        			
	        		}
        	    }
	        	else
	        	{
	        	    mTower1.animate(200);
	             	x_vel = (float)2*(x_pos - mTower1.getX())/(Math.abs(x_pos - mTower1.getX()));
	             	y_vel = (float)2*(y_pos - mTower1.getY())/Math.abs(x_pos - mTower1.getX());
	             	while(Math.abs(x_vel) > 5 || Math.abs(y_vel) > 5) {
	             		y_vel /= 2;
	             	    x_vel /= 2;
	             	}
	        	}
        	    break;
           }
           case MotionEvent.ACTION_MOVE:
        	   
           case MotionEvent.ACTION_DOWN:
        	    if(released) {
        	    	x_start = pSceneTouchEvent.getX();
        	    	y_start = pSceneTouchEvent.getY();
        	    	released = false;
        	    }
                break;
        }
        
        if (hits > 3)
        {
        	detachChildren();
        	act.switchScene(returnScene);
        }
        return true;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}