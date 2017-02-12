package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class EnemyBossBullet extends GameObject{

	
	private Handler handler;
	public int enemyWidth = 16;
	public int enemyHeight = 16;
	Random r = new Random();
	
	public EnemyBossBullet(float x, float y, Handler handler, ID id) {
		super(x, y, id);
		
		this.handler = handler;

		
		velX = (r.nextInt(5 - -5) + -5);
		velY = 5;
		
	}

	public void tick() {
		x += velX;
		y += velY;
		// Enemy movement, keeps inside game area
		//if(y <= 0 || y > Game.HEIGHT - (enemyHeight * 2)) velY *= -1;
		//if(x <= 0 || x > Game.WIDTH - enemyWidth) velX *= -1;
		if(y >= Game.HEIGHT) handler.removeObject(this);
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 16, 16, 0.08f, handler));
		
		
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, enemyWidth, enemyHeight);
		
	}
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, enemyWidth, enemyHeight);
	}
}
