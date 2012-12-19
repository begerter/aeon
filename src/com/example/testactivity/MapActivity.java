//package com.example.testactivity;
//
//import org.andengine.engine.camera.Camera;
//import org.andengine.engine.handler.IUpdateHandler;
//import org.andengine.engine.options.EngineOptions;
//import org.andengine.engine.options.ScreenOrientation;
//import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
//import org.andengine.entity.scene.IOnSceneTouchListener;
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.scene.background.Background;
//import org.andengine.ui.activity.SimpleBaseGameActivity;
//import org.andengine.opengl.font.Font;
//import org.andengine.opengl.font.FontFactory;
//import org.andengine.opengl.texture.ITexture;
//import org.andengine.opengl.texture.bitmap.BitmapTexture;
//import org.andengine.util.HorizontalAlign;
//import org.andengine.util.adt.io.in.IInputStreamOpener;
//import org.andengine.util.debug.Debug;
//import java.io.IOException;
//import java.io.InputStream;
//import org.andengine.opengl.texture.region.ITextureRegion;
//import org.andengine.opengl.texture.region.TextureRegionFactory;
//import org.andengine.opengl.vbo.VertexBufferObjectManager;
//import org.andengine.entity.sprite.ButtonSprite;
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.entity.text.Text;
//import org.andengine.entity.text.TextOptions;
//import org.andengine.input.touch.TouchEvent;
//
//import android.graphics.Typeface;
//import android.view.MotionEvent;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Element;
//import org.xml.sax.SAXException;
//
//import java.io.File;
//import java.util.HashMap;
//
//
//public class MspActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener, IUpdateHandler { 
//
//
//	private Camera camera;
//	private static final int CAMERA_WIDTH = 1024;
//	private static final int CAMERA_HEIGHT = 768;
//	private ITextureRegion mBackgroundTextureRegion, mLouisMapTextureRegion, mRobinTextureRegion;
//	private HashMap<Integer, ITextureRegion> tileTextures;
//	private Sprite mRobin, mLouis, backgroundSprite;
//	private String[] text = {"Hi Louis!", "ROBIN BEHIND YOU", "OW", "I told you to watch out...", "..."};
//	private int counter = 0;
//	private Font mFont;
//	public Text mText;
//	private Scene scene;
//	
//	// Tile things
//	private Tile grid[][];
//	private int width = 50;
//	private int height = 50;
//	private int tilesize = 32;
//	
//	// things for movement yaaaaaaay
//	private int currX = this.CAMERA_WIDTH/2;
//	private int currY = this.CAMERA_HEIGHT/2;
//	private int targetX = this.CAMERA_WIDTH/2;
//	private int targetY = this.CAMERA_HEIGHT/2;
//	private int baseMovement = 10;
//	
//	
//	@Override
//	public EngineOptions onCreateEngineOptions() {
//		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
//		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
//				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
//		return engineOptions;
//	}
//
//	@Override
//	protected void onCreateResources() {
//		try {
//			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
//				@Override
//				public InputStream open() throws IOException {
//					return getAssets().open("tmw_lab_spacing.png");
//				}
//			
//			});
//			ITexture louisMapTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
//				@Override
//				public InputStream open() throws IOException {
//					return getAssets().open("louis_redo2.png");
//				}
//			
//			});
//
//			backgroundTexture.load();
//			louisMapTexture.load();
//
//			
//			this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture, 166, 100, 32, 32);
//			this.mLouisMapTextureRegion = TextureRegionFactory.extractFromTexture(louisMapTexture);
//
//			
//			//File fXmlFile = new File("../assets/map.xml");
//			InputStream istr = getAssets().open("map.xml");
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(istr);
//			doc.getDocumentElement().normalize();
//			
//			NodeList mappings = doc.getElementsByTagName("mapping");
//			
//			this.tileTextures = new HashMap<Integer, ITextureRegion>();
//			for (int temp = 0; temp < mappings.getLength(); temp++)
//			{
//				Node mNode = mappings.item(temp);
//				Integer i = getTagValue("id", (Element) mNode);
//				Integer x = getTagValue("x", (Element) mNode);
//				Integer y = getTagValue("y", (Element) mNode);
//				this.tileTextures.put(i,  TextureRegionFactory.extractFromTexture(backgroundTexture, x, y, 32, 32));
//			}
//			
//			//this.grid = new Tile[width][height];
//			/*NodeList rows = doc.getElementsByTagName("row");
//			for (int temp = 0; temp < rows.getLength(); temp++)
//			{
//				NodeList tiles = rows.item(temp).getChildNodes();
//				for (int t = 0; t < tiles.getLength(); t++)
//				{
//					Integer i = getTagValue("id", (Element) tiles.item(t));
//					this.grid[temp][t] = new Tile(this.tileTextures.get(i), temp*this.tilesize, t*this.tilesize, this.tilesize, this.tilesize);
//				}
//			}*/
//		} catch (IOException e) {
//			Debug.e(e);
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 
//				256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
//		this.mFont.load();
//		this.grid = new Tile[width][height];
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				//this.grid[i][j] = new Tile(this.mBackgroundTextureRegion,i*this.tilesize, j*this.tilesize, this.tilesize, this.tilesize);
//				this.grid[i][j] = new Tile(this.tileTextures.get(2),i*this.tilesize, j*this.tilesize, this.tilesize, this.tilesize);
//			}
//		}
//	}
//	
//	private static Integer getTagValue(String sTag, Element eElement) {
//		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
//	 
//	        Node nValue = (Node) nlList.item(0);
//	 
//		return Integer.parseInt(nValue.getNodeValue());
//	  }
//	
//	@Override
//	protected Scene onCreateScene() {
//		scene = new Scene();
//		scene.setBackground(new Background(1,1,1));
//		scene.setOnSceneTouchListener(this);
//		scene.registerUpdateHandler(this);
//		//backgroundSprite = new Sprite(0,0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
//		//scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
//		//scene.attachChild(backgroundSprite);
//		
//		mLouis = new Sprite(this.CAMERA_WIDTH/2 - this.mLouisTextureRegion.getWidth()/2,this.CAMERA_HEIGHT/2 - this.mLouisTextureRegion.getHeight()/2, this.mLouisTextureRegion, getVertexBufferObjectManager());
//		
//		//mText = new Text(100, 40, this.mFont, this.text[counter], new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
//		//scene.attachChild(mText);
//		/*ButtonSprite button = new ButtonSprite(200,200, this.mLouisTextureRegion, mEngine.getVertexBufferObjectManager()) {
//			@Override
//			public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//				/*if(pTouchEvent.isActionDown()) {
//					if (counter < text.length - 1)
//						counter++;
//					mText.setText(text[counter]);
//				}
//				System.out.print("clicked!");
//				return true;
//			}
//		};
//		scene.attachChild(button);	*/
//		scene = drawBackground(scene);
//		//scene.attachChild(mLouis);
//		return scene;
//	}
//
//	private Scene drawBackground(Scene scene) {
//		//scene.detachChildren();
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				scene = this.grid[i][j].draw(scene);
//			}
//		}
//		scene.attachChild(mLouis);
//		mLouis.setPosition(this.currX - this.mLouisTextureRegion.getWidth()/2, this.currY - this.mLouisTextureRegion.getHeight()/2);
//		//mRobin = new Sprite(100,100, this.mRobinTextureRegion, getVertexBufferObjectManager());
//		//scene.attachChild(mRobin);
//		return scene;
//	}
//	
//	@Override
//	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
//		
//		if (pSceneTouchEvent.isActionDown())
//		{
//			int cX = (int)pSceneTouchEvent.getX();
//			int cY = (int)pSceneTouchEvent.getY();/*
//			int cX = (int)pSceneTouchEvent.getMotionEvent().getX();
//			int cY = (int)pSceneTouchEvent.getMotionEvent().getY();*/
//			//this.targetX = this.currX + (cX - this.CAMERA_WIDTH/2);
//			//this.targetY = this.currY + (cY - this.CAMERA_HEIGHT/2);
//			this.targetX = cX;
//			this.targetY = cY;
//			// TODO: border case
//			//scene.attachChild(new Sprite(cX,cY, this.mLouisTextureRegion, getVertexBufferObjectManager()));
//			/*
//			dX = 10;
//			dY = 10;
//			System.out.print("clicked!");
//			if (pSceneTouchEvent.getX() < CAMERA_WIDTH/2)
//			{
//				dX = -10;
//			}
//			if (pSceneTouchEvent.getY() < CAMERA_HEIGHT/2)
//			{
//				dY = -10;
//			}*/
//			
//			
//			
//			//cene.clearChildScene();
//			
//			/*if (counter < text.length - 1)
//				counter++;
//			mText = new Text(100, 40, mFont, text[counter], new TextOptions(HorizontalAlign.CENTER), this.getVertexBufferObjectManager());
//			scene.attachChild(backgroundSprite);
//			if (counter % 2 == 0)
//				scene.attachChild(mRobin);
//			else
//				scene.attachChild(mLouis);
//			scene.attachChild(mText);*/
//			return true;
//		}
//		/*
//		if (pSceneTouchEvent.isActionMove())
//		{
//			pScene.attachChild(mRobin);
//			return true;
//		}*/
//		
//		return false;
//	}
//	
//	public void update(float pSecondsElapsed) {
//		for (int i = 0; i < this.width; i++)
//		{
//			for (int j = 0; j < this.height; j++)
//			{
//				this.grid[i][j].move(this.dX, this.dY);
//			}
//		}
//	}
//
//	@Override
//	public void onUpdate(float pSecondsElapsed) {
//		
//		// get unit vector 
//		if (Math.abs(this.currX  + this.mLouisTextureRegion.getWidth()/2 - this.targetX) < 5 && Math.abs(this.currY + this.mLouisTextureRegion.getHeight()/2 - this.targetY) < 5)
//		{
//			//this.drawBackground(scene);
//			return;
//		}
//		double dX = this.targetX - this.currX - this.mLouisTextureRegion.getWidth()/2;
//		double dY = this.targetY - this.currY - this.mLouisTextureRegion.getHeight()/2;
//		double norm = Math.sqrt(dX*dX + dY*dY);
//		double goX = this.baseMovement*dX/norm;
//		double goY = this.baseMovement*dY/norm;
//		
//		this.currX += (int)goX;
//		this.currY += (int)goY;
//		mLouis.setPosition(this.currX, this.currY);
//		//this.drawBackground(scene);
//		// set dx, dy
//		/*
//		if (Math.abs(this.currX - this.targetX) < this.baseMovement*2)
//		{
//			this.targetX = this.currX;
//			this.dX = 0;
//		}
//		else if (this.currX > this.targetX)
//		{
//			this.dX = -1*this.baseMovement;
//		}
//		else
//		{
//			this.dX = this.baseMovement;
//		}
//		if (Math.abs(this.currY - this.targetY)< this.baseMovement*2 )
//		{
//			this.targetY = this.currY;
//			this.dY = 0;
//		}
//		else if (this.currY > this.targetY)
//		{
//			this.dY = -1*this.baseMovement;
//		}
//		else
//		{
//			this.dY = this.baseMovement;
//		}
//		
//		// check if we collide with things, do stuff if not
//		// can move will deal with if something happens
//		if (this.canMove(this.currX+this.dX, this.currY+this.dY))
//		{
//			scene.detachChildren();
//			this.currX = this.currX+this.dX;
//			this.currY = this.currY+this.dY;
//			this.recenter();
//		}
//		
//		/*
//		if (Math.abs(this.currX - this.targetX) < this.baseMovement*2)
//		{
//			this.targetX = this.currX;
//			this.targetY = this.currY;
//			this.dX = 0;
//			this.dY = 0;
//		}
//		else
//		{
//			if (this.targetX > this.currX)
//			{
//				this.dX = this.baseMovement;
//			}
//			if (this.targetY > this.currY)
//			{
//				this.dY = this.baseMovement;
//			}
//			for (int i = 0; i < this.width; i++)
//			{
//				for (int j = 0; j < this.height; j++)
//				{
//					this.grid[i][j].move(this.dX, this.dY);
//				}
//			}
//		}*/
//		
//	}
//
//	public Boolean canMove(int x, int y)
//	{
//		if (x < 0 || y < 0 || x > this.tilesize*this.width || y > this.tilesize*this.height)
//		{
//			return false;
//		}
//		return true;
//	}
//	
//	public void recenter()
//	{
//		//if (!onBorder())
//		//{
//			int baseX = this.currX - this.CAMERA_WIDTH/2;
//			int baseY = this.currY - this.CAMERA_HEIGHT/2;
//			int m = 0;
//			for (int i = baseX/this.tilesize; i < baseX/this.tilesize+this.CAMERA_WIDTH/this.tilesize; i++)
//			{
//				int n = 0;
//				for (int j = baseY/this.tilesize; j < baseY/this.tilesize+this.CAMERA_HEIGHT/this.tilesize; j++)
//				{
//					this.grid[i][j].draw(scene);
//					this.grid[i][j].setLoc(m*this.tilesize, n*this.tilesize);
//					//this.grid[i][j].move(this.dX, this.dY);
//				}
//			}
//			scene.attachChild(mLouis);
//			mLouis.setPosition(this.CAMERA_WIDTH/2 - this.mLouisTextureRegion.getWidth()/2,this.CAMERA_HEIGHT/2 - this.mLouisTextureRegion.getHeight()/2);
//		//}
//	}
//	
//	public Boolean onBorder()
//	{
//		int bufferX = this.CAMERA_WIDTH/2;
//		int bufferY = this.CAMERA_HEIGHT/2;
//		Boolean trigger = false;
//		int i = 0, j = 0, iE = 0, jE = 0;
//		int xL = 0, yL = 0;
//		
//		// we are at x start
//		if (this.currX < bufferX)
//		{
//			i = 0;
//			iE = this.CAMERA_WIDTH/this.tilesize;
//			if (this.currY < bufferY)
//			{
//				j = 0;
//				jE = this.CAMERA_HEIGHT/this.tilesize;
//				// in top left corner
//			}	
//			else if (this.height*this.tilesize - this.currY < bufferY)
//			{
//				j = this.height - this.CAMERA_HEIGHT/this.tilesize;
//				jE = this.height;
//				// in bottom left corner
//			}
//			else
//			{
//
//				j = (this.currY - bufferY)/this.tilesize;
//				// in middle of left side
//			}
//			trigger = true;
//		}
//		else if (this.width*this.tilesize - this.currX < bufferX)
//		{
//			i = this.width - this.CAMERA_WIDTH/this.tilesize;
//			iE = this.width;
//			if (this.currY < bufferY)
//			{
//				// in top right corner
//				j = 0;
//				jE = this.CAMERA_HEIGHT/this.tilesize;
//			}
//			
//			else if (this.height*this.tilesize - this.currY < bufferY)
//			{
//				// in bottom right corner
//				j = this.height - this.CAMERA_HEIGHT/this.tilesize;
//				jE = this.height;
//			}
//			else
//			{
//				// in middle of right side
//				j = (this.currY - bufferY)/this.tilesize;
//			}
//			trigger = true;
//		}
//		else if (this.currY < bufferY)
//		{
//			// in middle of top
//			trigger = true;
//		}
//		else if (this.height*this.tilesize - this.currY < bufferY)
//		{
//			// in middle of bottom
//			trigger = true;
//		}
//
//		if (trigger)
//		{
//			int m = 0;
//			for (; i < iE; i++)
//			{
//				int n = 0;
//				for (; j < jE; j++)
//				{
//					this.grid[i][j].draw(scene);
//					this.grid[i][j].setLoc(m*this.tilesize, n*this.tilesize);
//					//this.grid[i][j].move(this.dX, this.dY);
//					n++;
//				}
//				m++;
//			}
//			return true;
//		}
//		return false;
//	}
//	
//	@Override
//	public void reset() {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
