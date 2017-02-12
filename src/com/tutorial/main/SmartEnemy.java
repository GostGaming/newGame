package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class SmartEnemy extends GameObject{

	
	private Handler handler;
	public int enemyWidth = 16;
	public int enemyHeight = 16;
	private GameObject player;
	public Random r;
	public boolean player1 = false;
	
	
	
	
	public SmartEnemy(float x, float y, Handler handler, ID id) {
		super(x, y, id);
	
		
		this.handler = handler;
	
		
		// Find player
		r = new Random();
		if(r.nextInt(2) == 1){
			player1 = true;
		}
		else player1 = false;
		
	}

	public void tick() {
		
		
		
		for(float i = 0; i < handler.object.size(); i++){
			if((player1 == true && Player.dead == false) || (player1 == false && Player2.dead == true)){
				if(handler.object.get((int) i).getID() == ID.Player) player = handler.object.get((int) i);
			}
			else if((player1 == false && Player2.dead == false) || (player1 == true && Player.dead == true)){
				if(handler.object.get((int) i).getID() == ID.Player2) player = handler.object.get((int) i);
			}
		}
			
		
		
		x += velX;
		y += velY;
		
		
	
		// Chase player!
		float diffX = x - player.getX() - 16;
		float diffY = y - player.getY() - 16;
		
		float distance = (float) Math.sqrt( (x - player.getX()) * (x - player.getX()) + (y - player.getY())*(y - player.getY()));
		
		velX = (float) ((-1.0 / distance) * diffX);
		velY = (float) ((-1.0 / distance) * diffY);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, 16, 16, 0.08f, handler));		
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, enemyWidth, enemyHeight);
		
	}
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, enemyWidth, enemyHeight);
	}
}
