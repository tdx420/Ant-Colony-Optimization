package aco.Gui;

import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;

import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import aco.Ant.Ant;
import aco.Ant.AntColony;
import aco.Ant.Controller;
import aco.Ant.GlobalSettings;
import aco.World.Edge;
import aco.World.Food;
import aco.World.Node;
import aco.World.World;


public class AntApplication extends JPanel implements Controller  {
	
	private BufferedImage currentBI;
	private int width, height;
	private AntColony ac;
//	private Food food[];
	private ArrayList<Food> foods; 
	private int totalFood;
	private World world;
	private Node node;
	private MainPanel mainPanel;
	private JPanel sidePanel,  sidePanel1, sidePanel2;
	private JPanel foodPanel, desc_foodPanel, add_foodPanel;
	private JPanel bottomPanel;
	private JFrame frame;
	private int mouseX, mouseY;
	private JComboBox sizeComboBox;
	Controller controller;
	 public static JLabel displayLabel ;
	 private Food food_A ,food_B, food_C;
		
	

	private int totalAnts;
	 

	//private final static int WINDOW_WIDTH = 900;
	//private final static int WINDOW_HEIGHT =600;
	private final static int COLOR_RED_VAL = 420<< 16;
	private final static int COLOR_GB_VAL = 420|(420<<8);
	
	public AntApplication (int width, int height) {
		controller = this;
		
		this.width = width;
		this.height = height;

		//Create world
		world = new World(width, height);

		//	//initialize world, start node initialises ant                                                                                                     
		world.initializeWorld(300, 300);
		
		totalFood =1;
		
		
		foods = new ArrayList<Food>();
		food_A = new Food(" a", 460, 320 ,GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		//foods.add(food_A);
		
		/*
		food = new Food[totalFood];
	
		for (int i =0 ;i<food.length; i++)
		{
		//	food[i] = new Food(" a ", 360, 320 ,GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
			
		//	food[1] = new Food(" a ", 560, 420 ,GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
			
			food[0] = new Food( "a ", 490, 220 , GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		//	food[1] = null;
			//food[2] =null;
		//	 ac.setGlobalBestpathValue(-1.0);
		//	food[1] = new Food( "b ", 290, 20 , GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		//	food[i] = new Food( "b ", mainPanel.getMouseX(), mainPanel.getMouseY() , GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		}*/
		
		
		food_B = new Food( "b",  150, 380, GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		food_C =new Food( "c", 200 , 190, GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);

		
	
		world.initializeFood(foods);
//		mainPanel.moveFood(food);
		
	 //makes the nodes draggable
        sidePanel = new JPanel(new GridLayout(2,0,0,0));
        
		sidePanel1 = new JPanel (new GridLayout(3,0,0,0));
		sidePanel2 = new JPanel(new GridLayout(1,0,0,0));
		foodPanel = new JPanel(new GridLayout(1,2,0,0));
		desc_foodPanel = new JPanel(new GridLayout(1,0,0,0));
		add_foodPanel = new JPanel(new GridLayout(3,0,0,0));
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton add_Food1 = new JRadioButton("1");
	//	frame.getRootPane().setDefaultButton(add_Food1);
	//	add_Food1.requestFocus();

	//	add_Food1.setFocusable(false);
		add_Food1.setFocusable(false);
		add_Food1.setSelected(true);
		add_Food1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
		
				// TODO Auto-generated method stub
				
			//	System.out.print("add");
				 if (add_Food1.isSelected()){
					 
					
			//		 if(foods.size() > 0){
					 foods.removeAll(foods);
					 foods.add(food_A);
					 foods.remove(food_B);
					 foods.remove(food_C);
					 ac.setGlobalBestpathValue(-1.0);
					 world.initializeFood(foods);
					 displayLabel.setText ("Ants are hunting for food" );
					 start();
				//	 }
					 //	foods.add(food_B);
					//	food[1] = new Food( "b ",  150, 380, GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
					//	ac.setGlobalBestpathValue(-1.0);
				
			//		 foods.remove(food_C); foods.remove(food_B);
					/* 
					 Iterator itr = foods.iterator();
					 while(itr.hasNext()){
						 Food food = (Food) itr.next();
						 foods.remove(food_C);
						 foods.remove(food_B);
						 
					 }*/
					 
					//	 foods.remove(food_C);
						//foods.remove(food_B);
					//	world.initializeFood(foods);
					//	displayLabel.setText ("Ants are hunting for food" );
					//	start();
					
						//System.out.print("add");
			//			 Iterator itr = foods.iterator();
					//	 while(itr.hasNext()){
							
						// }
					
				 }
			}});
		
		
		JRadioButton add_Food2 = new JRadioButton("2");
		add_Food2.setFocusable(false);
		
		
		add_Food2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
		
				// TODO Auto-generated method stub
				
			//	System.out.print("add");
				 if (add_Food2.isSelected()){
					 foods.removeAll(foods);
					 	foods.add(food_A);
					 	foods.add(food_B);
					 	foods.remove(food_C);
						
					//	foods.remove(food_C);
					//	food[1] = new Food( "b ",  150, 380, GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
					//	 ac.setGlobalBestpathValue(-1.0);
					//	world.initializeFood(foods);
					//	displayLabel.setText ("Ants are hunting for food" );
					//	start();
					 	 ac.setGlobalBestpathValue(-1.0);
						 world.initializeFood(foods);
						 displayLabel.setText ("Ants are hunting for food" );
						 start();
						System.out.print("add");
		
					
			        }
			}});
		
		
		JRadioButton add_Food3 = new JRadioButton("3");
		add_Food3.setFocusable(false);
		
		add_Food3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			
				 if (add_Food3.isSelected()){
					 //foods.remove(food_B);
					// 	foods.add(food_B);	
					 foods.removeAll(foods);
					 	foods.add(food_A)	;
					 	foods.add(food_B);
					 	foods.add(food_C);
						//foods.remove(food_B);
						
						 ac.setGlobalBestpathValue(-1.0);
						 world.initializeFood(foods);
						 displayLabel.setText ("Ants are hunting for food" );
						 start();
					//	ac.setGlobalBestpathValue(-1.0);
					//	world.initializeFood(foods);
				//		displayLabel.setText ("Ants are hunting for food" );
				//		start();
			        }
				
			}});
		
		group.add(add_Food1);
		group.add(add_Food2);
		group.add(add_Food3);
	
		//sidePanel.add(group);
		add_foodPanel.add(add_Food1);
		add_foodPanel.add(add_Food2);
		add_foodPanel.add(add_Food3);
		
		foodPanel.add(desc_foodPanel);
		foodPanel.add(add_foodPanel);
		
		JLabel label_foodDesc = new JLabel("Add food");
		desc_foodPanel.add(label_foodDesc);
		
		sidePanel1.add(foodPanel);
		
		
//		mainPanel.moveFood(food,world);
		
	
		totalAnts =55;
		//Create ant colony, Nants  = 2nd arg and 50000 iterations
		ac = new AntColony(world,  totalAnts , GlobalSettings.TOTAL_ITERATIONS, this);
		
		antSizeComboBox();
		//Initialize ant, steps per iteration
		Ant.initialize(ac, GlobalSettings.STEPS_PER_ITERATION);
		
		//Creating the panels can be done outside the swing thread
		createGUI();
		
		//This is done so that the gui is in a separate thread
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				frame.setVisible(true);
				//frame.pack();
			}
		});
	}

	public void start () {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				ac.start();
			}
		};
		Thread t  = new Thread(r);
		t.start();
		
		// we need the above to create a new thread when we call the start method, otherwise it will crash, for e.g, in our restart button,
		
		//Actually, i think we dont need to create the thread as above,
		//The following line shld be sufficient
		
		
		//ac.start();
	}

	private void createGUI() {
		frame = new JFrame("Ant Colony Optimization");

		//THe currentBI has to be available before we create the panel
		this.drawInitialImage();
	//	frame.add(mainPanel);
	//	mainPanel.add(this);
		mainPanel = new MainPanel(currentBI, new Dimension(this.width, this.height));
		bottomPanel = new JPanel();
//		addFoodButton(frame);
		frame.add(mainPanel,BorderLayout.EAST);
		frame.add(sidePanel,BorderLayout.WEST);
		frame.add(bottomPanel,BorderLayout.SOUTH);
		mainPanel.setBorder(new LineBorder(Color.black, 4));
		sidePanel1.setBorder(new LineBorder(Color.black, 4));
		sidePanel2.setBorder(new LineBorder(Color.black, 4));
		bottomPanel.setBorder(new LineBorder(Color.black, 4));
		mainPanel.setBackground(new Color(250,100,170));
	//	sidePanel2.setBackground(new Color(1,1,1));
		sidePanel2.setBackground(Color.WHITE);
		//String b = a.toString();
		
		displayLabel = new JLabel();
		
		//displayLabel.setText(ac.getGlobalBestpathValue().toString());
		
	//	System.out.println(	ac.getGlobalBestpathValue());
		 
      	displayLabel.setText ("Ants are hunting for food" );
		bottomPanel.add(displayLabel);
		
		
		//sidePanel1.setSize(100, 400);

		JButton restartButton = new JButton("Restart") ;
		restartButton.setPreferredSize(null);
		
		restartButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ac.setGlobalBestpathValue(-1.0);
				displayLabel.setText ("Ants are hunting for food" );
		//	currentBI.flush();
			//setPheromoneAt(0,0,0,0);
				// setEndOfIteration();
				//displayLabel.repaint();
				//mainPanel.repaint();
			//	bottomPanel.repaint();
			
				start ();
				
				
			}});
		sidePanel1.add(restartButton);
		
	
		try{
	
			BufferedImage bi= ImageIO.read(new File("ant.png"));
			JLabel picLabel = new JLabel(new ImageIcon( bi ));
			//picLabel.setSize(new Dimension(10,10));
			picLabel.setBackground(new Color(150,100,170));
			picLabel.setPreferredSize(null);
		//	picLabel.setSize(2,2);
			
			sidePanel.add(sidePanel1);
			
		
			sidePanel2.add(picLabel);
			sidePanel.add(sidePanel2);
		
			
		//	sidePanel1.add(picLabel);
		
			
		}
		catch(IOException ex){	}
	
	//	JScrollPane p = new JScrollPane(mainPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	//	int w,h;
		
		//Adjust the size of the scroll panel
	//	w = (this.width < WINDOW_WIDTH)? this.width : WINDOW_WIDTH;
	//	h = (this.height < WINDOW_HEIGHT)? this.height : WINDOW_HEIGHT;
		//Note: The size is inclusive of the width of the scroll bar, hence we incr by 5
	//	p.setPreferredSize(new Dimension(w+5, h+5));
	//	frame.setContentPane(p);
		//frame.setContentPane(mainPanel);
		//frame.setContentPane(sidePanel1);



		sidePanel1.setPreferredSize(new Dimension(170,0));
		
		
		mainPanel.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e) {
            	
            	System.out.println("hello11");
            	System.out.println(e.getX()+" "+ " " + e.getY());
            	
            	Iterator itr = foods.iterator();
        		while(itr.hasNext())
        		{
        			Food food = (Food) itr.next();
        			
					ac.setGlobalBestpathValue(-1.0);
            		
            		food.setLowerX(e.getX());	
            		food.setLowerY(e.getY());	
            		//food[0] = new Food("A", e.getX(), e.getY(), GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
            
            	
            		
        			
        			//Edge currentEdge = (Edge) itr.next();
        			//length = length + currentEdge.getLength();
        		}
        		world.initializeFood(foods);
            	
           
            	
         
		}
		
        });
		
		
		frame.pack();
		frame.setLocation(0,0);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}


	private void drawInitialImage(){
		currentBI = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
		
		Graphics2D g2 = currentBI.createGraphics();
		
		//Default color is white, hence we have a white background
			g2.setColor(Color.yellow);
		g2.fillRect(0, 0, width, height);	
	
	}
	
	public static void setDisplayLabel(String text){
		displayLabel.setText("Shortest Path found so far " + text);
	}
	
	
	
	

	@Override
	public void setFoodAt()
	{
			synchronized (currentBI) {
			Graphics2D g2 = currentBI.createGraphics();
			ArrayList<Food> copy = new ArrayList<Food>(foods);
			
			Iterator itr = copy.iterator();
			while(itr.hasNext())
			{
				Food food = (Food) itr.next();
				g2.setColor(new Color(150,100,170));
				
				
				int x = food.lowerX;
				int y=food.lowerY;
				//drawRect(int x, int y, int width, int height) 
	           	g2.fillRect(x, y,food.width, food.height);
			
			}
			
			
		
			
		}
	}
	
	@Override
	
	public void setAntAt(int x, int y) {
		


		synchronized (currentBI) {
			Graphics2D g2 = currentBI.createGraphics();
			//Ants are black
			g2.setColor(Color.black);
			g2.fillRect(x, y, 2, 2);			
			g2.dispose();
				
		

		}
	}

	//The start and end of iteration methods need not be synchronized
	@Override
	public void setStartOfIteration() {
		//Create a new image when an iteration starts
		drawInitialImage();
	}
	
	@Override
	public void setEndOfIteration() {
		//No more changes to the Image, hence flush its resources.
		//Not sure if it has any performance gain.
		currentBI.flush();
		mainPanel.setImage(currentBI);
	}

	@Override
	// parameters are updated at Node
	public void setPheromoneAt(int x, int y, int level_index, int lowest_index) {
		synchronized (currentBI) {
			int mask = (int) (((float)level_index / lowest_index) * COLOR_GB_VAL);
			
			//The larger the mask, the more white the color wud be
			//Pheromone red color, todo : color changes as the level reduces
			currentBI.setRGB(x, y, (int)(COLOR_RED_VAL|mask));
		}
		
	
	}
	

	
	public void antSizeComboBox() {
		
	//	Dimension controlDimension = new Dimension(5, 5);
		sizeComboBox = new JComboBox();
		sizeComboBox.setFocusable(false);
		//sizeComboBox.setMinimumSize(controlDimension);
	//	sizeComboBox.setMaximumSize(controlDimension);
	//	sizeComboBox.setPreferredSize(controlDimension);
		//sizeComboBox.addItem("10 X 10");
	
		sizeComboBox.addItem("");
		sizeComboBox.addItem("1");
		sizeComboBox.addItem("20");
		sizeComboBox.addItem("50");

	
		sizeComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				synchronized(this){
				
				 if(sizeComboBox.getSelectedItem().equals("1")){
					ac.setGlobalBestpathValue(-1.0);
					displayLabel.setText ("Ants are hunting for food" );
					 ac.setNumAnts(1);
					 start();
			
				 }
				else if(sizeComboBox.getSelectedItem().equals("20")){
					ac.setGlobalBestpathValue(-1.0);
					displayLabel.setText ("Ants are hunting for food" );
					ac.setNumAnts(20);
					 start();
				}
				else if(sizeComboBox.getSelectedItem().equals("50")){
					ac.setGlobalBestpathValue(-1.0);
					displayLabel.setText ("Ants are hunting for food" );
					ac.setNumAnts(50);
				 	start();
				}	
			}
			}
		});

		
		
		
		
		sizeComboBox.setSelectedItem("");
		
		JPanel combo_panel = new JPanel(new GridLayout(0,2,10,0));
		//combo_panel.preferredSize(new Dimension(30,30));

		JLabel antSize_label = new JLabel ("Ant Size: ");
		
		combo_panel.add(antSize_label);
		combo_panel.add(sizeComboBox);
		combo_panel.setPreferredSize(null);
		sidePanel1.add(combo_panel);
	//	sidePanel1.setPreferredSize(new Dimension( 100, 40 ));
		//   combo_panel.setPreferredSize(new Dimension(100, 10));
	}
	

}
