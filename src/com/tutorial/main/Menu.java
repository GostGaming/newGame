package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter{
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud){
		this.game = game;
		this.handler = handler;	
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Menu){
			// Play button
			
			if(mouseOver(mx,my,(Game.WIDTH /2 - 100), 100, 200, 64)){
				Game.gameState = STATE.Game;
				handler.object.clear();
				handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32,ID.Player, handler));
				handler.addObject(new Player2(Game.WIDTH / 2 + 32, Game.HEIGHT / 2 - 32,ID.Player2, handler));
				
				
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 40), r.nextInt(Game.HEIGHT - 40), handler, ID.BasicEnemy));
			}
			
			
			// Help button
			if(mouseOver(mx,my,(Game.WIDTH /2 - 100), 200, 200, 64)){
				Game.gameState = STATE.Help;
				
			}
			// Quit button
			if(mouseOver(mx, my, (Game.WIDTH /2 - 100), 300, 200, 64)){
				System.exit(1);
			}
		}
		else if(Game.gameState == STATE.Help){
			// Back button
			if(mouseOver(mx,my,(Game.WIDTH /2 - 75), 360, 150, 44)){
				Game.gameState = STATE.Menu;
				return;
			}
		}
		else if(Game.gameState == STATE.Death){
			// Back button
			if(mouseOver(mx,my,(Game.WIDTH /2 - 75), 360, 150, 44)){
				Game.gameState = STATE.Menu;
				hud.setLevel1(1);
				hud.score1(0);
				hud.setLevel2(1);
				hud.score2(0);
				Player.dead = false;
				Player2.dead = false;
				
				for(int i = 0; i < 10; i++){
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 20), r.nextInt(Game.HEIGHT - 20), handler, ID.MenuParticle));
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if (mx > x && mx < x + width){
			if (my > y && my < y + height){
				return true;
			}
			else return false;
		}
		else return false;
	}
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(Game.gameState == STATE.Menu){
			
			
			Font fnt = new Font("Georgia", 1, 50);
			Font fnt2 = new Font("Georgia", 1, 30);
			g.setColor(Color.white);
			g.setFont(fnt);
			
			g.drawString("Game", Game.WIDTH /2 - 75, 55);
			
			g.setFont(fnt2);
			
			g.drawString("Play", Game.WIDTH /2 - 40, 140);
			g.drawRect((Game.WIDTH /2 - 100), 100, 200, 64);
			
			
			g.drawString("Help", Game.WIDTH /2 - 40, 240);
			g.drawRect((Game.WIDTH /2 - 100), 200, 200, 64);
			
			g.drawString("Quit", Game.WIDTH /2 - 40, 340);
			g.drawRect((Game.WIDTH /2 - 100), 300, 200, 64);
		}
		else if(Game.gameState == STATE.Help){
			Font fnt = new Font("Georgia", 1, 50);
			Font fnt2 = new Font("Georgia", 1, 20);
			g.setColor(Color.white);
			g.setFont(fnt);
			
			g.drawString("Help", Game.WIDTH /2 - 75, 55);
			
			g.setFont(fnt2);
			
			g.drawString("Use WASD keys to move player and avoid enemies.", 50, 100);
			
			g.drawString("Back", Game.WIDTH /2 - 30, 390);
			g.drawRect((Game.WIDTH /2 - 75), 360, 150, 44);
		}
		else if(Game.gameState == STATE.Death){
			handler.clearEnemies();
			
			Font fnt = new Font("Georgia", 1, 50);
			Font fnt2 = new Font("Georgia", 1, 20);
			g.setColor(Color.white);
			g.setFont(fnt);
			
			g.drawString("You Died!", Game.WIDTH /2 - 75, 55);
			
			g.setFont(fnt2);
			
			g.drawString("Player 1 died with a score of " + (int)hud.getScore1() , 50, 100);
			g.drawString("Player 2 died with a score of " + (int)hud.getScore2() , 50, 150);
			
			g.drawString("Try Again", Game.WIDTH /2 - 40, 390);
			g.drawRect((Game.WIDTH /2 - 75), 360, 150, 44);
		}
	}
}
