package pack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObj {
	//Note: x,y refer to coordinates, while w,h refer to the object size
	protected int x;
	protected int y;
	protected int velY;
	//added w and h so I can implement getBounds here (no code duplication)
	protected int w; //width
	protected int h; //height
	public abstract void tick();
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);
	}
	
	Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getVelY() {
		return velY;
	}
	void setX(int x) {
		this.x = x;
	}
	void setY(int y) {
		this.y = y;
	}
	void setVelY(int velY) {
		this.velY = velY;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	void setWidth(int w) {
		this.w = w;
	}
	void setHeight(int h) {
		this.h = h;
	}
	
}
