package com.example.testactivity;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

public class MapScene extends Scene implements IOnSceneTouchListener
{

	private TestActivity act;
	private int targetX = act.CAMERA_WIDTH/2;
	private int targetY = act.CAMERA_HEIGHT/2;
	private int currX = act.CAMERA_WIDTH/2;
	private int currY = act.CAMERA_HEIGHT/2;
	private int width = 50;
	private int height = 50;
	public static int tilesize = 32;
	private int dX = 0;
	private int dY = 0;
	private int baseMovement = 10;
	
	
	public MapScene(TestActivity t) {
		act = t;
		
		setBackground(new Background(1,1,1));
		setOnSceneTouchListener(this);
//		registerUpdateHandler(this);
		//backgroundSprite = new Sprite(0,0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		//scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		//scene.attachChild(backgroundSprite);
		
		act.mMapLouis = new Sprite(act.CAMERA_WIDTH/2 - act.mLouisMapTextureRegion.getWidth()/2,act.CAMERA_HEIGHT/2 - act.mLouisMapTextureRegion.getHeight()/2, act.mLouisMapTextureRegion, act.getVertexBufferObjectManager());
		
		//mText = new Text(100, 40, this.mFont, this.text[counter], new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
		//scene.attachChild(mText);
		/*ButtonSprite button = new ButtonSprite(200,200, this.mLouisTextureRegion, mEngine.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				/*if(pTouchEvent.isActionDown()) {
					if (counter < text.length - 1)
						counter++;
					mText.setText(text[counter]);
				}
				System.out.print("clicked!");
				return true;
			}
		};
		scene.attachChild(button);	*/
		drawBackground();
		//attachChild(act.mLouis);

	}
	
	private void drawBackground() {
		//scene.detachChildren();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				act.grid[i][j].draw(this);
			}
		}
		attachChild(act.mMapLouis);
		act.mMapLouis.setPosition(currX - act.mLouisMapTextureRegion.getWidth()/2, currY - act.mLouisMapTextureRegion.getHeight()/2);
		//mRobin = new Sprite(100,100, this.mRobinTextureRegion, getVertexBufferObjectManager());
		//scene.attachChild(mRobin);

	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		if (pSceneTouchEvent.isActionDown())
		{
			int cX = (int)pSceneTouchEvent.getX();
			int cY = (int)pSceneTouchEvent.getY();/*
			int cX = (int)pSceneTouchEvent.getMotionEvent().getX();
			int cY = (int)pSceneTouchEvent.getMotionEvent().getY();*/
			//this.targetX = this.currX + (cX - this.CAMERA_WIDTH/2);
			//this.targetY = this.currY + (cY - this.CAMERA_HEIGHT/2);
			this.targetX = cX;
			this.targetY = cY;
			// TODO: border case
			//scene.attachChild(new Sprite(cX,cY, this.mLouisTextureRegion, getVertexBufferObjectManager()));
			/*
			dX = 10;
			dY = 10;
			System.out.print("clicked!");
			if (pSceneTouchEvent.getX() < CAMERA_WIDTH/2)
			{
				dX = -10;
			}
			if (pSceneTouchEvent.getY() < CAMERA_HEIGHT/2)
			{
				dY = -10;
			}*/
			
			
			
			//cene.clearChildScene();
			
			/*if (counter < text.length - 1)
				counter++;
			mText = new Text(100, 40, mFont, text[counter], new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
			scene.attachChild(backgroundSprite);
			if (counter % 2 == 0)
				scene.attachChild(mRobin);
			else
				scene.attachChild(mLouis);
			scene.attachChild(mText);*/
			return true;
		}
		/*
		if (pSceneTouchEvent.isActionMove())
		{
			pScene.attachChild(mRobin);
			return true;
		}*/
		
		return false;
	}
	
	public void update(float pSecondsElapsed) {
		for (int i = 0; i < this.width; i++)
		{
			for (int j = 0; j < this.height; j++)
			{
				act.grid[i][j].move(dX, dY);
			}
		}
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {		
		// get unit vector 
		if (Math.abs(this.currX  + act.mLouisMapTextureRegion.getWidth()/2 - this.targetX) < 5 && Math.abs(this.currY + act.mLouisMapTextureRegion.getHeight()/2 - this.targetY) < 5)
		{
			//this.drawBackground(scene);
			return;
		}
		double dX = this.targetX - this.currX - act.mLouisMapTextureRegion.getWidth()/2;
		double dY = this.targetY - this.currY - act.mLouisMapTextureRegion.getHeight()/2;
		double norm = Math.sqrt(dX*dX + dY*dY);
		double goX = this.baseMovement*dX/norm;
		double goY = this.baseMovement*dY/norm;
		
		this.currX += (int)goX;
		this.currY += (int)goY;
		act.mMapLouis.setPosition(this.currX, this.currY);
		
		int tileheight = this.currY / this.tilesize;
		int tilewidth = this.currX / this.tilesize;
		if (tileheight >= 0 && tilewidth >= 0)
		{
		int res = act.grid[tileheight][tilewidth].specialEffects();
		if (res == 1)
		{
			act.switchScene(new BattleScene(this, act));
		}
		else if (res == 2)
		{
			act.switchScene(new TestScene3(this, act));
		}
		}
		//this.drawBackground(scene);
		// set dx, dy

		
	}
}
