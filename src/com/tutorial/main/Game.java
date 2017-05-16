package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;



public class Game extends Canvas implements Runnable{
	

	private static final long serialVersionUID = 1550691097823471818L;

	// Game window dimensions
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	
	// Default for game is running to false
	private boolean running = false;
	
	public Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	// Gamestates 
	public enum STATE{
		Menu,
		Game,
		Help,
		Death
	};
	
	// Start game at menu screen 
	
	public static STATE gameState = STATE.Menu;
	
	
	public Game(){
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		// Create game window
		new Window(WIDTH, HEIGHT, "NEW GAME!", this);
		
		
		spawner = new Spawn(handler, hud);
		
		r = new Random();
		
		if(gameState == STATE.Game){
		// Add players and one enemy on game start	
			handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32,ID.Player, handler));
			handler.addObject(new Player2(Game.WIDTH / 2 + 32, Game.HEIGHT / 2 - 32,ID.Player2, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 45), handler, ID.BasicEnemy));
		}
		else{
			// Add menu particle effect 
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
				frames = 0;
			}
		}
		stop();
		
	}
	
	private void tick(){
		
		handler.tick();
		if(gameState == STATE.Game){
			// Make sure hud updates and spawner activates while game is running 
			hud.tick();
			spawner.tick();
			
			for(float i = 0; i < handler.object.size(); i++){
				GameObject tempObject = handler.object.get((int) i);
				
				// If player 1 dies, remove him and record their score
				if(HUD.Health <= 0 && tempObject.getID() == ID.Player && Player.dead == false){
					Player.dead = true;
					hud.score1(hud.getScore1());
					handler.removeObject(tempObject);
					
														
				}
				// If player 2 dies, remove him and record their score
				else if (HUD.Health2 <= 0 && tempObject.getID() == ID.Player2 && Player2.dead == false){
					Player2.dead = true;
					hud.score2(hud.getScore2());
					handler.removeObject(tempObject);
					
				}
				// If both players die, clear the screen, go to death state and reset the health bars
				else if (HUD.Health <= 0 && HUD.Health2 <= 0){
					handler.clearEnemies();
					gameState = STATE.Death;
					HUD.Health = 100;
					HUD.Health2 = 100;
				}
			}
		}
		// Initiate menu tick on death and menu screen
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
		
		// Render HUD
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
	// Ensure nothing can go above max or below min (i.e health bars)
	public static float clamp(float var, float min, float max){
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
		
	}
	// Run the game
	public static void main(String args[]){
		new Game();
	}
}
