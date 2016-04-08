package aco.World;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aco.Ant.Controller;
import aco.Gui.MainPanel;

public class World extends JPanel {
	
	Node[][] world;
	int x_width;
	int y_width;
	//public Food[] f;
	public ArrayList<Food> foods; 
	
	
	Node startNode;
	MainPanel mainPanel;
	
	public World(int x_width, int y_width)
	{
		this.x_width = x_width;
		this.y_width = y_width;
		//new member of the object node
		world = new Node[x_width][y_width];
	}

	public Node getStartNode(){
		return startNode;
	}
	
	public int getXWidth(){
		return x_width;
	}
	
	public int getYWidth (){
		return y_width;
	}
	

  
	public void initializeWorld( int startX, int startY)
	{
	

		for(int i = 0; i < x_width; i++)
		{
			for(int j = 0; j < y_width; j++)
			{
				Node node = new Node(i, j, mainPanel);
			//	f.setLocation(i, j);
//     		   this.theFoods.add(f);
			
				world[i][j] = node;
			}
		}
		startNode = world[startX][startY];
		
	}

	public void initializeFood( ArrayList<Food> food1)
	{
		
		
	
		this.foods = food1;
		
		Iterator itr = this.foods.iterator();
		while(itr.hasNext())
		{
			Food food = (Food) itr.next();
			
			
			if (food.lowerX >= x_width)
				food.lowerX = x_width - 5;
			if (food.lowerY >= y_width)
				food.lowerY = y_width - 5;
			
			int hx = food.lowerX+food.width;
			int hy =food.lowerY+food.height;;
			//conditional operator
			hx = (hx > x_width)? x_width : hx; 
			hy = (hy > y_width)? y_width : hy;
			
			for (int k = food.lowerX; k <hx; k++) {
				
			
				for (int j = food.lowerY; j < hy; j++) {
					try {
						world[k][j].setIsFood(k, j );
						
					} catch (Exception e) {
						//eat any index out of bound exception
					}
				}
			}
			
	
		}

		
		
		/*
//		this.f= food;
		for (int i = 0; i < foods.length; i++) {
			
		//	ArrayList<Food> a = new ArrayList(); 
		//	a.add(food[i]);
			
			

			//First check if the food is within the limits of the world
			//If it is beyond the boundary, set it atleast 5 units before the boundary
			if (foods[i].lowerX >= x_width)
				foods[i].lowerX = x_width - 5;
			if (foods[i].lowerY >= y_width)
				foods[i].lowerY = y_width - 5;
			
			int hx = foods[i].lowerX+foods[i].width;
			int hy = foods[i].lowerY+foods[i].height;;
			//conditional operator
			hx = (hx > x_width)? x_width : hx; 
			hy = (hy > y_width)? y_width : hy;
			
			for (int k = foods[i].lowerX; k <hx; k++) {
				
			
				for (int j = foods[i].lowerY; j < hy; j++) {
					try {
						world[k][j].setIsFood(k, j );
						
					} catch (Exception e) {
						//eat any index out of bound exception
					}
				}
			}
		}*/
	}

	public Node getNode (int x, int y){
		return world[x][y];
	}
	
	
}
