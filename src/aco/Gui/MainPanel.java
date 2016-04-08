package aco.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.List;

import aco.World.Food;

public class MainPanel extends JPanel  {
	BufferedImage bi;
	 private int mouseX, mouseY;
	 private Food[] food;

	  
	public MainPanel(BufferedImage bi, Dimension size) {
		super();
		this.bi = bi;
		this.setPreferredSize(size);
		

		
	}
	
	public void moveFood(Food  f[]){

		this.food = f;
		 addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e) {
            	
            	System.out.println("hello");
           
		   mouseX = e.getX();
		   mouseY = e.getY();
		   repaint();
		   
		   	for(int i =0; i < food.length; i ++)
		   		
                setLocation(mouseX + food[i].getLowerX(),
                 mouseY + food[i].getLowerX());

		}
		
        });
	}	
	
	/*
	
	public int getMouseX()
	{
		return this.mouseX;
	}
		public int getMouseY()
	{
		return this.mouseY;
	}
	*/

	public void paint(Graphics g) {
		//super.paint(g);
		
		//Image already flushed
		//bi.flush();
		g.drawImage(bi, 0, 0, null);
	}
		
	
	public void setImage(BufferedImage newBI){
		synchronized (bi) {
			this.bi = newBI;
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				repaint();
			}
		});

	}
}
