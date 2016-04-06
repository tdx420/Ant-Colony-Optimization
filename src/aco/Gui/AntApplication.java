package aco.Gui;

import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.List;

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
import aco.World.Food;
import aco.World.World;


public class AntApplication extends JPanel implements Controller  {
	
	private BufferedImage currentBI;
	private int width, height;
	private AntColony ac;
	private Food food[];
	private int totalFood;
	private World world;
//	private Node node;
	private MainPanel mainPanel;
	private JPanel sidePanel;
	private JPanel bottomPanel;
	private JFrame frame;
	private int mouseX, mouseY;
	private JComboBox sizeComboBox;
	
	 public static JLabel displayLabel ;


	

	private int totalAnts;
	 
	//private final static int WINDOW_WIDTH = 900;
	//private final static int WINDOW_HEIGHT =600;
	private final static int COLOR_RED_VAL = 420<< 16;
	private final static int COLOR_GB_VAL = 420|(420<<8);
	
	public AntApplication (int width, int height) {
		
		this.width = width;
		this.height = height;

		//Create world
		world = new World(width, height);

		//	//initialize world, start node initialises ant                                                                                                     
		world.initializeWorld(300, 300);
		
		totalFood =5;
		food = new Food[totalFood];
		
		for (int i =0 ; i <food.length ; i++)
		{	
		//	food[i] = new Food(" a ", 360, 320 ,GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
			
			
			
			
		//	food[1] = new Food(" a ", 560, 420 ,GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
			
			food[i] = new Food( "b ", 490, 220 , GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		//	food[i] = new Food( "b ", mainPanel.getMouseX(), mainPanel.getMouseY() , GlobalSettings.FOOD_WIDTH,GlobalSettings.FOOD_HEIGHT);
		
		
		
		}
		
		
	
		world.initializeFood(food, mainPanel);
//		mainPanel.moveFood(food);
		
	 //makes the nodes draggable
        
		sidePanel = new JPanel (new GridLayout(4,0,0,0));
		
		
		JRadioButton goal = new JRadioButton("Food");
		goal.setFocusable(false);
		sidePanel.add(goal);
		add(sidePanel, BorderLayout.WEST);
		
//		mainPanel.moveFood(food,world);
		antSizeComboBox();
	
		//Create ant colony, Nants  = 2nd arg and 50000 iterations
		ac = new AntColony(world,  totalAnts , GlobalSettings.TOTAL_ITERATIONS, this);
		
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
		sidePanel.setBorder(new LineBorder(Color.black, 4));
		bottomPanel.setBorder(new LineBorder(Color.black, 4));
		mainPanel.setBackground(new Color(250,100,170));
		
		//String b = a.toString();
		
		displayLabel = new JLabel();
		
		//displayLabel.setText(ac.getGlobalBestpathValue().toString());
		
	//	System.out.println(	ac.getGlobalBestpathValue());
		 
      	displayLabel.setText ("Ants are hunting for food" );
		bottomPanel.add(displayLabel);
		
		
		//sidePanel.setSize(100, 400);

		JButton restartButton = new JButton("Restart") ;
		restartButton.setPreferredSize(null);
		
		restartButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				displayLabel.setText ("Ants are hunting for food" );
		//	currentBI.flush();
			//setPheromoneAt(0,0,0,0);
				// setEndOfIteration();
				//displayLabel.repaint();
				//mainPanel.repaint();
			//	bottomPanel.repaint();
			
				start ();
				
				
			}});
		sidePanel.add(restartButton);
		
	//	frame.add(this);
		try{
	
			BufferedImage bi= ImageIO.read(new File("ant.png"));
			JLabel picLabel = new JLabel(new ImageIcon( bi ));
			//picLabel.setSize(new Dimension(10,10));
			picLabel.setBackground(new Color(150,100,170));
			picLabel.setPreferredSize(null);
		//	picLabel.setSize(2,2);
			sidePanel.add(picLabel);
			sidePanel.setPreferredSize(null);
			
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
		//frame.setContentPane(sidePanel);



	
		
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
		
			for (int i = 0; i < food.length; i++) {
				g2.setColor(new Color(150,100,170));
				int x = food[i].lowerX;
				int y=food[i].lowerY;
				//drawRect(int x, int y, int width, int height) 
	           	g2.fillRect(x, y,food[i].width, food[i].height);
			
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
	
	public void addFoodButton(JFrame a){
		sidePanel = new JPanel ();
	
		
		JRadioButton goal = new JRadioButton("Food");
		goal.setFocusable(false);
		sidePanel.add(goal);
		a.add(sidePanel, BorderLayout.WEST);
		
		goal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			//	ants.setTileToAdd(Ants.Tile.GOAL);
			}
		});
		
		
	}
	
	
	public void antSizeComboBox(){
		
		Dimension controlDimension = new Dimension(75, 25);
		sizeComboBox = new JComboBox();
		sizeComboBox.setFocusable(false);
		sizeComboBox.setMinimumSize(controlDimension);
		sizeComboBox.setMaximumSize(controlDimension);
		sizeComboBox.setPreferredSize(controlDimension);
		//sizeComboBox.addItem("10 X 10");
		sizeComboBox.addItem("0");
		sizeComboBox.addItem("50");
		sizeComboBox.addItem("200");

	
		sizeComboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				 if(sizeComboBox.getSelectedItem().equals("0")){
					AntApplication.this.totalAnts = 0;
				}
				else if(sizeComboBox.getSelectedItem().equals("50")){
					AntApplication.this.totalAnts = 50;
				}
				else if(sizeComboBox.getSelectedItem().equals("200")){
				AntApplication.this.totalAnts = 200;
					
				}	
			}
		});

		sizeComboBox.setSelectedItem("200");
		sidePanel.add(sizeComboBox);
		
	}
	

}
