package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Player2 extends GameObject{

	Random r = new Random();
	Handler handler;
	
	public int playerHeight = 32;
	public int playerWidth = 32;
	public static boolean dead = false;
		
	
	public Player2(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, playerWidth, playerHeight);
		
	}
	public void tick(){
		
		x += velX;
		y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - playerWidth - 8);
		y = Game.clamp(y, 0, Game.HEIGHT - (playerHeight * 2) - 5);

				handler.addObject(new Trail(x, y, ID.Trail, Color.pink, playerWidth, playerHeight, 0.08f, handler));
		
		collision();
		
	}
	public void collision(){
		for(float i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get((int) i);
			if(tempObject.getID() == ID.BasicEnemy || tempObject.getID() == ID.FastEnemy || tempObject.getID() == ID.SmartEnemy ||tempObject.getID() == ID.BossEnemy){
				if(getBounds().intersects(tempObject.getBounds())){
					HUD.Health2 -= 2;
					
				}
			}
		}
	}

	public void render(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		
		
		//g.setColor(Color.blue);
		g2d.draw(getBounds());
		g.setColor(Color.blue);
		g.fillRect((int)x, (int)y, playerWidth, playerHeight);
		
	}
}
