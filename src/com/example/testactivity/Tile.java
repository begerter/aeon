package com.example.testactivity;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

	public class Tile {
		
		public static final int BATTLE = 1;
		public static final int CONVO = 2;
		public static final int NORMAL = 0;
		
		private Sprite backgroundSprite;
		private int x;
		private int y;
		private int width;
		private int height;
		private int special;
		
		Tile(ITextureRegion tex, int x, int y, int width, int height, VertexBufferObjectManager objManager)
		{
			this.backgroundSprite = new Sprite(x,y,tex, objManager);
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.special = NORMAL;
		}
		
		Tile(ITextureRegion tex, int x, int y, int width, int height, int special, VertexBufferObjectManager objManager)
		{
			this.backgroundSprite = new Sprite(x,y,tex, objManager);
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.special = special;
		}
		
		int specialEffects()
		{
			if (special == BATTLE)
			{
				special = NORMAL;
				return BATTLE;
			}
			if (special == CONVO)
			{
				special = NORMAL;
				return CONVO;
			}
			return NORMAL;
		}
		
		void draw(Scene scene)
		{
			scene.attachChild(this.backgroundSprite);
		}
		
		void undraw(Scene scene)
		{
			scene.detachChild(this.backgroundSprite);
		}
		
		void move(int dX, int dY)
		{
			this.x += dX;
			this.y += dY;
			this.backgroundSprite.setPosition(this.x, this.y);
			
		}
		
		void setLoc(int x, int y)
		{
			this.x = x;
			this.y = y;
			this.backgroundSprite.setPosition(this.x, this.y);
		}
		
	}