package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;



public class Game extends Canvas implements Runnable{
	

	private static final long serialVersionUID = 1550691097823471818L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	public Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	
	public enum STATE{
		Menu,
		Game,
		Help,
		Death
	};
	
	public static STATE gameState = STATE.Menu;
	
	
	public Game(){
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		new Window(WIDTH, HEIGHT, "NEW GAME!", this);
		
		
		spawner = new Spawn(handler, hud);
		
		r = new Random();
		if(gameState == STATE.Game){
			
			handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32,ID.Player, handler));
			handler.addObject(new Player2(Game.WIDTH / 2 + 32, Game.HEIGHT / 2 - 32,ID.Player2, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 45), handler, ID.BasicEnemy));
		}
		else{
			for(int i = 0; i < 10; i++){
				handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 20), handler, ID.MenuParticle));
			}
		}
	}
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void run(){
		this.requestFocus();
		
		// Main game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1){
				tick();
				delta--;
			
			}
			if(running)
				
				render();
			// Frame rate counter
				frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				
				//System.out.println("FPS: " + frames);
				
				frames = 0;
			}
		}
		stop();
		
	}
	
	private void tick(){
		
		handler.tick();
		if(gameState == STATE.Game){
			hud.tick();
			spawner.tick();
			
			for(float i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get((int) i);
				if(HUD.Health <= 0 && tempObject.getID() == ID.Player && Player.dead == false){
					Player.dead = true;
					hud.score1(hud.getScore1());
					handler.removeObject(tempObject);
					
														
				}
				else if (HUD.Health2 <= 0 && tempObject.getID() == ID.Player2 && Player2.dead == false){
					Player2.dead = true;
					hud.score2(hud.getScore2());
					handler.removeObject(tempObject);
					
				}
				else if (HUD.Health <= 0 && HUD.Health2 <= 0){
					handler.clearEnemies();
					gameState = STATE.Death;
					HUD.Health = 100;
					HUD.Health2 = 100;
				}
			}
		}
		else if(gameState == STATE.Menu || gameState == STATE.Death){
		menu.tick();	
		}
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		// Draw Background
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		handler.render(g);
		if(gameState == STATE.Game){
			hud.render(g);
			
		}
		else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Death){
			menu.render(g);	
			}
		
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max){
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
		
	}
	public static void main(String args[]){
		new Game();
	}
}
