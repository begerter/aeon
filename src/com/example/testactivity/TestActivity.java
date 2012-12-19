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
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



import android.graphics.Typeface;


public class TestActivity extends SimpleBaseGameActivity {

	private Camera camera;
	public static final int CAMERA_WIDTH = 1024;
	public static final int CAMERA_HEIGHT = 768;
	public ITextureRegion mBackgroundTextureRegion, mLouisTextureRegion, mRobinTextureRegion, mConvbarTextureRegion,mLouisMapTextureRegion, mMapBackgroundTextureRegion;
	public Sprite mRobin, mLouis, mConvbar, mMapLouis;
	public String[] text = {"Hello", "World"};
	public Font mFont;
	public Text mText;
	public Scene scene;
	private SceneManager sm;
	public Tile grid[][];
	private HashMap<Integer, ITextureRegion> tileTextures;
	private int width = 50;
	private int height = 50;
	private BitmapTexture mTexture;
	public TextureRegion mBattleBackgroundTextureRegion;
	public BitmapTexture mEnemyTexture;
	public TiledTextureRegion mPlayerTextureRegion;
	public TiledTextureRegion mEnemyTextureRegion;
	public Stack mStack1;
	public Stack mStack2;
	public Stack mStack3;
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		SceneManager.init(this);
		sm = SceneManager.getManager();
		sm.setGameScreen();
		
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
			ITexture mapBackgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("tmw_lab_spacing.png");
				}
			
			});
			ITexture louisMapTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("louis_redo2.png");
				}
			
			});
			
			ITexture battleBackgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("background.png");
                }
            });
            battleBackgroundTexture.load();
            //towerTexture.load();
            // 3 - Set up texture regions
            BitmapTextureAtlas fontAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
            mFont = new Font(this.getEngine().getFontManager(), fontAtlas,Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32,true, Color.BLACK);
            mFont.load();
            this.mBattleBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(battleBackgroundTexture);
            this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("felicebattle.png");
                }
            });
            this.mEnemyTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("zombie.png");
                }
            });
            this.mTexture.load();
            this.mEnemyTexture.load();
            this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mTexture, 1, 3);
            this.mEnemyTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mEnemyTexture, 3, 1);
            // 4 - Create the stacks
            this.mStack1 = new Stack();
            this.mStack2 = new Stack();
            this.mStack3 = new Stack();

			mapBackgroundTexture.load();
			louisMapTexture.load();
			
			backgroundTexture.load();
			louisTexture.load();
			robinTexture.load();
			convbarTexture.load();
			
			this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
			this.mLouisTextureRegion = TextureRegionFactory.extractFromTexture(louisTexture);
			this.mRobinTextureRegion = TextureRegionFactory.extractFromTexture(robinTexture);
			this.mConvbarTextureRegion = TextureRegionFactory.extractFromTexture(convbarTexture);
			this.mMapBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture, 166, 100, 32, 32);
			this.mLouisMapTextureRegion = TextureRegionFactory.extractFromTexture(louisMapTexture);
			
			//File fXmlFile = new File("../assets/map.xml");
			InputStream istr = getAssets().open("map.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(istr);
			doc.getDocumentElement().normalize();
			
			NodeList mappings = doc.getElementsByTagName("mapping");
			
			this.tileTextures = new HashMap<Integer, ITextureRegion>();
			for (int temp = 0; temp < mappings.getLength(); temp++)
			{
				Node mNode = mappings.item(temp);
				Integer i = getTagValue("id", (Element) mNode);
				Integer x = getTagValue("x", (Element) mNode);
				Integer y = getTagValue("y", (Element) mNode);
				this.tileTextures.put(i,  TextureRegionFactory.extractFromTexture(mapBackgroundTexture, x, y, 32, 32));
			}
			this.grid = new Tile[width][height];
			NodeList rows = doc.getElementsByTagName("row");
			for (int temp = 0; temp < rows.getLength(); temp++) // height
			{
			    Element r = (Element)rows.item(temp);
			    NodeList tiles = r.getElementsByTagName("tile");
			    for (int t = 0; t < tiles.getLength(); t++) // width
			    {
			    	Integer i = getTagValue("id", (Element) tiles.item(t));
			    	Integer s = getSpecial((Element) tiles.item(t));
			    	this.grid[t][temp] = new Tile(this.tileTextures.get(i), temp*MapScene.tilesize, t*MapScene.tilesize, MapScene.tilesize, MapScene.tilesize, s, getVertexBufferObjectManager());
			    }
			}
		} catch (IOException e) {
			Debug.e(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
		}
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 
				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		this.mFont.load();
		/*this.grid = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				//this.grid[i][j] = new Tile(this.mBackgroundTextureRegion,i*this.tilesize, j*this.tilesize, this.tilesize, this.tilesize);
				this.grid[i][j] = new Tile(this.tileTextures.get(2),i*MapScene.tilesize, j*MapScene.tilesize, MapScene.tilesize, MapScene.tilesize, getVertexBufferObjectManager());
			}
		}*/
	}
	
	
	
	@Override
	protected Scene onCreateScene() {
		scene = new TestScene(this);
		

		return scene;
	}

	public Scene getScene()
	{
		return scene;
	}
	
	
	
	private class TestScene extends Scene implements  IOnSceneTouchListener 
	{
		private int counter;
		private TestActivity act;
		
		public TestScene(TestActivity t)
		{
			act = t;
			counter = 0;
			setOnSceneTouchListener(this);
			Sprite backgroundSprite = new Sprite(0,0, mBackgroundTextureRegion, getVertexBufferObjectManager());
			//scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
			attachChild(backgroundSprite);
			mRobin = new Sprite(0,0, mRobinTextureRegion, getVertexBufferObjectManager());
			attachChild(mRobin);
			
			mLouis = new Sprite(0,0, mLouisTextureRegion, getVertexBufferObjectManager());
			mLouis.setVisible(false);
			attachChild(mLouis);
			
			mConvbar = new Sprite(0,0, mConvbarTextureRegion, getVertexBufferObjectManager());
			attachChild(mConvbar);
			
			mText = new Text(50, 600, mFont, text[counter], new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
			attachChild(mText);
		}
		
		@Override
		public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
			  if (pSceneTouchEvent.isActionDown())
			  {
				if (counter < text.length - 1)
			      counter++;
				else
				{
					scene.detachChildren();
					
					scene = new MapScene(act);
					getEngine().setScene(scene);
					//sm.setGameScreen();
					return true;
				}
				
			    mText.setText(text[counter]);

				if (counter % 2 == 0)
				{
					mRobin.setVisible(true);
					mLouis.setVisible(false);
				}
				else
				{
					mRobin.setVisible(false);
					mLouis.setVisible(true);
				}

				return true;
			  }
			  return false;
		}
	}
	
	public void switchScene(Scene s)
	{
		//scene.detachChildren();
		scene = s;
		getEngine().setScene(scene);
	}

	private static Integer getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	 
		return Integer.parseInt(nValue.getNodeValue());
	  }
	
	private static Integer getSpecial(Element eElement) {
		NodeList nL = eElement.getElementsByTagName("special");
		
		if (nL.getLength() > 0)
		{
			NodeList nLL = nL.item(0).getChildNodes();
			Node nV = (Node) nLL.item(0);
			return Integer.parseInt(nV.getNodeValue());
		}
		return 0;
	}
	
}
