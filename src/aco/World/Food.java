package aco.World;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import java.awt.Graphics;

import java.awt.Color;
public class Food extends JPanel {
	

		//Nodes with food have pheromoneIndex = FOOD_VALUE, generally -1

	public int pheromoneIndex;

	public String name; 
	public String getName() {
		return name;
	}

	public int lowerX,lowerY, width, height;
	
	public void setLowerX(int lowerX) {
		this.lowerX = lowerX;
	}



	public void setLowerY(int lowerY) {
		this.lowerY = lowerY;
	}


	public Food(String name, int lx, int ly, int width, int height) {

		this.lowerX = lx;
		this.lowerY = ly;
		this.width = width;
		this.height = height;
		this.name = name;
		
  
	}
	
	public int getLowerX()
	{
		return this.lowerX;
	}

	public int getLowerY()
	{
		return this.lowerY;
	}
	
}
