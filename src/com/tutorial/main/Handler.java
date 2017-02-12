package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
	}
	
	public void render(Graphics g){
		
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			tempObject.render(g);
			
		}
	}
	public void addObject(GameObject object){
		this.object.add(object);
		
	}
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	public void clearEnemies(){
		for (int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			object.clear();
			if (tempObject.getID() == ID.Player || tempObject.getID() == ID.Player2){
				if(Game.gameState != Game.STATE.Death){
					if(tempObject.getID() == ID.Player && tempObject.getID() == ID.Player2){
							addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, this));					
							addObject(new Player2((int)tempObject.getX() + 64, (int)tempObject.getY(), ID.Player2, this));
						}
					else if(tempObject.getID() == ID.Player && Player2.dead == true){
						addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, this));					
					}
					else if(tempObject.getID() == ID.Player2 && Player.dead == true){
						addObject(new Player2((int)tempObject.getX() + 64, (int)tempObject.getY(), ID.Player2, this));
					}
				}
			}
		}
	}
}
