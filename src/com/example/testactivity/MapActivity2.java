package com.example.testactivity;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.Engine;
import org.andengine.engine.Engine.UpdateThread;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;

/*
import org.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.andengine.entity.layer.tiled.tmx.TMXTile;
import org.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;*/
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
/*
import org.andengine.util.Debug;
import org.andengine.util.constants.Constants;*/

import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class MapActivity2 extends SimpleBaseGameActivity implements IOnSceneTouchListener {
	
    private Camera camera;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private static final int TILE_HEIGHT = 32;
	private static final int TILE_WIDTH = 32;
	private ITextureRegion mTextureRegion;
	private ITexture mTexture;
    
    
    @Override
    protected void onCreateResources() {
      /*try {
    	  final TMXLoader tmxLoader = new TMXLoader(getAssets(), getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, getVertexBufferObjectManager());
    	  //tmxLoader.loadFromAsset()
          //final TMXLoader tmxLoader = new TMXLoader(getAssets(), getTextureManager(), TextureOptions.NEAREST, getVertexBufferObjectManager());
          this.tiledMap = tmxLoader.loadFromAsset("TileMap.tmx");
          
          //this.tiledMap.setIsometricDrawMethod(TMXConstants.DRAW_METHOD_ISOMETRIC_CULLING_PADDING);
          
      } catch (final TMXLoadException e) {
           Debug.e(e);
      }
      try {
			mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("tmw_lab_spacing.png");
				}
			
			});
			
			
			mTexture.load();
			
			this.mTextureRegion = TextureRegionFactory.extractFromTexture(mTexture, 0, 0, TILE_HEIGHT, TILE_WIDTH);
		} catch (IOException e) {
			Debug.e(e);
		}*/

    }

    @Override
    protected Scene onCreateScene() {
    	Scene scene = new Scene();
    	scene.setBackground(new Background(50, 0, 0));
    	scene.setOnSceneTouchListener(this);
    	/*for (int i = 0; i < tiledMap.getTileColumns(); ++i)
    	{
    		for (int j = 0; j < tiledMap.getTileRows(); ++j)
    		{
    			scene.attachChild(new Sprite(TILE_HEIGHT*i, TILE_WIDTH*j, mTextureRegion, getVertexBufferObjectManager() ));
    		}
    	}*/
        //this.tmxLayer = this.tiledMap.getTMXLayers().get(0);  // the 0 is just an index of the layer. It depends on how many layers you created within Tiled
        //this.tmxLayer2 = this.tiledMap.getTMXLayers().get(2);
        //scene.attachChild(this.tmxLayer);
        //scene.attachChild(tmxLayer2);
          
        return scene;
    }

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
		//camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.setUpdateThread(new UpdateThread());
		return engineOptions;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
        
}