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
	public int lowerX,lowerY, width, height;
	//boolean alive;
	public Food(String name, int lx, int ly, int width, int height) {
	 //alive =false;

		this.lowerX = lx;
		this.lowerY = ly;
		this.width = width;
		this.height = height;
		this.name = name;
		
      
		
       
	//	g2.fillRect(lowerX, lowerY, food.width, food.height);
	//	g2.fillRect(lowerX, food1.lowerY, food1.width, food1.height);
	//	g2.dispose();
		/*
		 addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
               displayPanel.setLocation(e.getX() + lowerX,
                        e.getY() + lowerY);
             	displayPanel.repaint();
            }
        });
        */
         //  repaint();
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
