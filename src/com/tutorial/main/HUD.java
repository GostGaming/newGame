package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class HUD {

	
	public static float Health = 100;
	public static float Health2 = 100;
	private float greenVal = 255f; 
	private float greenVal2 = 255f;
	private float score1 = 0;
	private float level1 = 1;
	private float score2 = 0;
	private float level2 = 1;

	public void tick(){
		
		Health = Game.clamp(Health, 0, 100);
		Health2 = Game.clamp(Health2, 0, 100);
		greenVal = Game.clamp(greenVal, 0, 255);
		greenVal = Health * 2;
		greenVal2 = Game.clamp(greenVal2, 0, 255);
		greenVal2 = Health2 * 2;
		
		

		
		if(Player.dead == false){
			score1++;
		}
		if(Player2.dead == false){
			score2++;
		}
		
	}
	
	public void render(Graphics g){
		
		Font fnt = new Font("Georgia", 1, 12);
		g.setFont(fnt);
		// Player 1 health bar
		g.setColor(Color.gray);
		g.fillRect(15, 20, 200, 32);
		g.setColor(new Color(75, (int) greenVal, 0));
		g.fillRect(15, 20, (int) (Health * 2), 32);
		g.setColor(Color.white);
		g.drawRect(15, 20, 200, 32);
		
		// Player 2 health bar
		
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH - 220, 20, 200, 32);
		g.setColor(new Color(75, (int) greenVal2, 0));
		g.fillRect(Game.WIDTH - 220, 20, (int) (Health2 * 2), 32);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH - 220, 20, 200, 32);
		

		g.drawString("Player 1", 90, 10);
		g.drawString("Score: " + (int) getScore1(), 15, 64);
		g.drawString("Level: " + (int) getLevel1(), 15, 80);
		
		g.drawString("Player 2", Game.WIDTH - 120, 10);
		g.drawString("Score: " + (int) getScore2(), Game.WIDTH - 220, 64);
		g.drawString("Level: " + (int) getLevel2(), Game.WIDTH - 220, 80);

	}
	
	public void score1(float score1){
		this.score1 = score1;
		
	}
	public float getScore1(){
		return  score1;
	}
	public float getLevel1(){
		return  level1;
	}
	public void setLevel1(float level1){
		this.level1 = level1;
	}
	public void score2(float score2){
		this.score2 = score2;
		
	}
	public float getScore2(){
		return  score2;
	}
	public float getLevel2(){
		return  level2;
	}
	public void setLevel2(float level2){
		this.level2 = level2;
	}
}
