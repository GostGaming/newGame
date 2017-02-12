package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject{

	
	private Handler handler;
	public int enemyWidth = 16;
	public int enemyHeight = 16;
	Random r = new Random();

	private Color col;
	

	public MenuParticle(int x, int y, Handler handler, ID id) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = (r.nextInt(7) + 1);
		velY = (r.nextInt(7) + 1);
		
		col = new Color( r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y > Game.HEIGHT - (enemyHeight * 3)) velY *= -1;
		if(x <= 0 || x > Game.WIDTH - (enemyWidth * 2)) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, col, 16, 16, 0.08f, handler));
		
		
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, enemyWidth, enemyHeight);
		
	}
	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, enemyWidth, enemyHeight);
	}
}
