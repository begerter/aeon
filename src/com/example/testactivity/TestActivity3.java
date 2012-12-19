package com.example.testactivity;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import java.io.IOException;
import java.io.InputStream;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;


import android.graphics.Typeface;


public class TestActivity3 extends SimpleBaseGameActivity {

	private Camera camera;
	private static final int CAMERA_WIDTH = 1024;
	private static final int CAMERA_HEIGHT = 768;
	private ITextureRegion mBackgroundTextureRegion, mLouisTextureRegion, mRobinTextureRegion, mConvbarTextureRegion;
	private Sprite mRobin, mLouis, mConvbar;
	private String[] text = {"Foo", "Bar"};
	private int counter = 0;
	private Font mFont;
	public Text mText;
	private Scene scene;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		try {
			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("dungeon0.png");
				}
			
			});
			ITexture louisTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("louis_normal1.png");
				}
			
			});
			ITexture robinTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("robin_normal1.png");
				}
			
			});
			ITexture convbarTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("convbar.png");
				}
			
			});
			backgroundTexture.load();
			louisTexture.load();
			robinTexture.load();
			convbarTexture.load();
			
			this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
			this.mLouisTextureRegion = TextureRegionFactory.extractFromTexture(louisTexture);
			this.mRobinTextureRegion = TextureRegionFactory.extractFromTexture(robinTexture);
			this.mConvbarTextureRegion = TextureRegionFactory.extractFromTexture(convbarTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 
				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.mFont.load();
	}
	
	@Override
	protected Scene onCreateScene() {
		//scene = new TestScene3();

		return scene;
	}

	public Scene getScene()
	{
		return scene;
	}
	
}
