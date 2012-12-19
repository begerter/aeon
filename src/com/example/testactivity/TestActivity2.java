package com.example.testactivity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
//import org.andengine.extension.tmx.*;
//import org.andengine.extension.tmx.util.exception.TMXLoadException;
//import org.andengine.extension.tmx.util.constants.TMXConstants;
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
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
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
public class TestActivity2 extends SimpleBaseGameActivity {
	private TMXTiledMap tiledMap;
    private TMXLayer tmxLayer;
    private Camera camera;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
    
    
    @Override
    protected void onCreateResources() {
      try {
          final TMXLoader tmxLoader = new TMXLoader(getAssets(), getTextureManager(), TextureOptions.NEAREST, getVertexBufferObjectManager());
          this.tiledMap = tmxLoader.loadFromAsset("TileMap_new.tmx");
          
          //this.tiledMap.setIsometricDrawMethod(TMXConstants.DRAW_METHOD_ISOMETRIC_CULLING_PADDING);
      } catch (final TMXLoadException e) {
           Debug.e(e);
      }

    }

    @Override
    protected Scene onCreateScene() {
    	Scene scene = new Scene();
          this.tmxLayer = this.tiledMap.getTMXLayers().get(0);  // the 0 is just an index of the layer. It depends on how many layers you created within Tiled
          scene.attachChild(this.tmxLayer);
          
          return scene;
    }

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}
        
}