package aco.World;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

import aco.Ant.Controller;

import aco.Gui.MainPanel;

public class World extends JPanel {
	
	Node[][] world;
	int x_width;
	int y_width;
	public Food[] f;
	
	
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

	public void initializeFood(Food food[], final MainPanel mainPanel)
	{
		this.mainPanel=mainPanel;
		
		this.f= food;
		for (int i = 0; i < f.length; i++) {

			//First check if the food is within the limits of the world
			//If it is beyond the boundary, set it atleast 5 units before the boundary
			if (f[i].lowerX >= x_width)
				f[i].lowerX = x_width - 5;
			if (f[i].lowerY >= y_width)
				f[i].lowerY = y_width - 5;
			
			int hx = f[i].lowerX+f[i].width;
			int hy = f[i].lowerY+f[i].height;;
			//conditional operator
			hx = (hx > x_width)? x_width : hx; 
			hy = (hy > y_width)? y_width : hy;
			
			for (int k = f[i].lowerX; k <hx; k++) {
				for (int j = f[i].lowerY; j < hy; j++) {
					try {
						world[k][j].setIsFood(k, j );
						
					} catch (Exception e) {
						//eat any index out of bound exception
					}
				}
			}
			
			
		
			
		}
	}

	public Node getNode (int x, int y){
		return world[x][y];
	}
	
	
}
