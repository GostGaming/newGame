package com.tutorial.main;

import java.util.Random;

public class Spawn {

	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private float scoreKeep = 0;
	
	public Spawn(Handler handler, HUD hud){
		this.handler = handler;
		this.hud = hud;
	}
	
	public void tick(){
		scoreKeep++;
		
		if(scoreKeep >= 250){
			scoreKeep = 0;
			hud.setLevel1(hud.getLevel1() + 1);
			hud.setLevel2(hud.getLevel2() + 1);
			
			
			if(hud.getLevel1() == 2 || hud.getLevel2() == 2){
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 30),r.nextInt(Game.HEIGHT - 30),handler, ID.BasicEnemy ));
			}
			else if(hud.getLevel1() == 3 || hud.getLevel2() == 3){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.FastEnemy));
			}
			else if(hud.getLevel1() == 4 || hud.getLevel2() == 4){
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.SmartEnemy));	
			}
			else if(hud.getLevel1() == 5 || hud.getLevel2() == 5){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.FastEnemy));	
			}
			else if(hud.getLevel1() == 6 || hud.getLevel2() == 6){
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.BasicEnemy));	
			}
			else if(hud.getLevel1() == 7 || hud.getLevel2() == 7){
				handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.FastEnemy));	
			}
			else if(hud.getLevel1() == 8 || hud.getLevel2() == 8){
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.SmartEnemy));	
			}
			else if(hud.getLevel1() == 9 || hud.getLevel2() == 9){
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 30), r.nextInt(Game.HEIGHT - 30), handler, ID.BasicEnemy));	
			}
			else if(hud.getLevel1() == 10 || hud.getLevel2() == 10){
				handler.clearEnemies();
				handler.addObject(new BossEnemy((Game.WIDTH / 2) - 48, -100 ,handler, ID.BossEnemy ));
			}
		}
	}
}
