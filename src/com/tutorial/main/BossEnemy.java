package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject{

	
	private Handler handler;
	public int enemyWidth = 96;
	public int enemyHeight = 96;
	private int timer = 55;
	private int timer2 = 50;
	Random r = new Random();
	
	public BossEnemy(float x, float y, Handler handler, ID id) {
		super(x, y, id);
		
		this.handler = handler;

		
		velX = 0;
		velY = 2;
		
	}

	public void tick() {
		x += velX;
		y += velY;
		// Enemy movement, keeps inside game area
		//if(y <= 0 || y > Game.HEIGHT - (enemyHeight)) velY *= -1;
		if(x <= 0 || x > Game.WIDTH - enemyWidth) velX *= -1;
		if(timer <= 0) velY = 0;
		else timer--;
		
		if(timer <= 0) timer2--;
		if(timer2 <= 0){
			if(velX == 0) velX = 3;
			if (velX >0)velX += 0.01f;
			else if (velX < 0) velX -= 0.01f;
			
			velX = Game.clamp(velX, -10, 10);
			int spawn = r.nextInt(10);
			if (spawn == 0) handler.addObject(new EnemyBossBullet((int)x, (int)y, handler, ID.BasicEnemy));
		}
		handler.addObject(new Trail(x, y, ID.Trail, Color.magenta, enemyWidth, enemyHeight, 0.1f, handler));
		
		
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, enemyWidth, enemyHeight);
		
	}
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, enemyWidth, enemyHeight);
	}
}
