package com.example.testactivity;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

public class TestScene3 extends Scene implements IOnSceneTouchListener
{
	private TestActivity act;
	private int counter = 0;
	private String[] text = {"Foo", "Bar"};
	private Scene returnScene;
	
	public TestScene3(Scene s, TestActivity t)
	{
		returnScene = s;
		act = t;
		setOnSceneTouchListener(this);
		Sprite backgroundSprite = new Sprite(0,0, act.mBackgroundTextureRegion, act.getVertexBufferObjectManager());
		//scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		attachChild(backgroundSprite);

		attachChild(act.mRobin);
		act.mRobin.setVisible(true);
		
		attachChild(act.mLouis);
		act.mLouis.setVisible(false);

		attachChild(act.mConvbar);
		act.mConvbar.setVisible(true);
		
		act.mText = new Text(50, 600, act.mFont, text[counter], new TextOptions(HorizontalAlign.CENTER), act.getVertexBufferObjectManager());
		attachChild(act.mText);

	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		  if (pSceneTouchEvent.isActionDown())
		  {
			if (counter < text.length - 1)
		      counter++;
			else
			{
				detachChildren();
				act.switchScene(returnScene);
				return false;
			}
			
		    act.mText.setText(text[counter]);

			if (counter % 2 == 0)
			{
				act.mRobin.setVisible(true);
				act.mLouis.setVisible(false);
			}
			else
			{
				act.mRobin.setVisible(false);
				act.mLouis.setVisible(true);
			}

			return true;
		  }
		  return false;
	}
}