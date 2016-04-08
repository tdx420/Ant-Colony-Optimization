package aco.Ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import aco.World.Edge;
import aco.World.Node;
import aco.World.Path;
import aco.World.World;
import aco.World.Food;

// simplest event distribution method  that decouples source (Obervable) and listener (Observer)
// useful to notify several objects (Observer) when something has happened to one instance (Observable).
public class Ant extends Observable implements Runnable
{
	private int 		antID;    
	private Node 		curNode;
	private Node 		startNode;
	private Path 		bestPath;
	private Path 		curPath;
	private double		bestPathValue;
	ArrayList<Food> foods;
	//private Food[] food;
	private boolean foundFood;
	private Random pseudoRandom;

	//the direction that ant would prefer more , which is 8 directions n,w,e,s,nw,es,ew,sw
	private int curDir;
	
	//8 possible directions for ant to explore  
	// so for 8 different possible direction, 8 x 8 
	private static int maxDir = GlobalSettings.ANTS_MAX_DIR;
	
	//These are common to all ants, hence static.
	protected static int stepsPerIteration;
	protected static AntColony antColony;

    private static int antCounter = 0;
    
    //Store the boundary x, y for faster computation
    private static int maxX, maxY, minY;
    
    private static World world;
    
 
    //constuct, takes a node as parameter
    private void construct (Node nStartNode){
        antCounter++;
        antID    = antCounter;
        startNode = nStartNode;
        curNode = startNode;
        
        foundFood = false;
        bestPath = null;
        curPath = new Path();
        bestPathValue = -1;
        
        pseudoRandom = new Random();
 
        changeDir();
    }
    
    // instatiating ant will require passing starting node 
       public Ant(Node nStartNode) {
    	construct(nStartNode);
    }
    
    //8 possible directions for ant to explore , so each ant at a current position can choose to go in 8 more direction     
    private void changeDir(){
        double d = pseudoRandom.nextDouble();
        
        curDir = (int)(d*maxDir*8.0);
    }
    
    
 
    public static void initialize(AntColony ac, int stepsPerIter) {
    	Ant.antColony = ac;
    	Ant.stepsPerIteration = stepsPerIter;
    	world = antColony.getGraph();
    	maxX = world.getXWidth()-1;
    	maxY = world.getYWidth()-1;
    }
    
  
    public void start()
    {
        Thread thread = new Thread(this);
        thread.setName("Ant " + antID);
        thread.start();
    }

    public  Path getBestPath()
    {
    	return this.bestPath;
    }
    
    public double getBestPathLength()
    {
    	return this.bestPathValue;
    }
    
    //implements runnable, represents an job that ant has to do, i.e functioning of the ant
    public void run()
    {
    	
    	int stepCounter = 0;
    	
        // repeat till the stepsPerIteration is complete
        while(stepCounter < stepsPerIteration && !foundFood)
        {
            Node newNode;
            
            // synchronize access to the world
            synchronized(world)
            {
                // apply the State Transition Rule
                newNode = stateTransitionRule( curNode);
            }
                        
            synchronized(world)
            {
            	//update the curPath
                updateCurPath(curNode, newNode);
            }
            
            // update the current node
            curNode = newNode;
            stepCounter++;
        }
        synchronized (antColony) {

        	if (foundFood) {
        		// ant colony updateBestPath method which updates the best path when the shortest path is found
        		antColony.updateBestPath( bestPath, bestPathValue);
        		foundFood = false;
        	}
        	// update the observer
        	antColony.update(this, null);
        }
    }
    
   
    //This is the 8 dirs rule in which each dir can have max dir probablilty
    private Node stateTransitionRule( Node n){
    
	    	foods = world.foods;
	    
	    	
	    	
	    	//Do the boundary check
	    	if (n.getX() >= maxX || n.getY() >= maxY ||n.getX() <= 2|| n.getY() <=2) {
	    		//set ant to start node
	    		//change the direction when we hit the boundary or find food
	    		changeDir();
	    		return startNode;
	    	}
	    
	    	Node[] neighbours;
	    	neighbours = getAllneighbours(n);
	
	    	// food condition    	
	    	for (int i = 0; i < neighbours.length; i++) {
	    	
	    		ArrayList<Food> copy = new ArrayList<Food>(foods);
					    		Iterator itr = copy.iterator();
					    		while(itr.hasNext())
					    		{
					    			
					    			Food food = (Food) itr.next();
					    		
					    			if (neighbours[i].isFood()) {
						    			changeDir();
						    			//Set found food
						    			this.setFoundFood(true);			
						    				//if food is found it goes back to nest ,so return the srart node
						    			//Again set the ant to the start node
						    			return startNode;
						    		}
					    			
					    			
					    		}
	    		
	    		
	    		
	    		
					    	
	    		
	    	}
	   	
	    	Node randNode = exploreTransitionRule(neighbours);
		
	    	return followTrailTransitionRule(randNode, neighbours);
	
	    	
    }
    
  
 
	//this rule is based upon the explore factor that might take place, so probability will be different     
    private Node exploreTransitionRule(Node[] neighbours) {
    	
    	//Here we explore only based on probability
    	double d = pseudoRandom.nextDouble();
    	
    	int range = maxDir;
    	
    	/*probablity of selecting node in current direction
    	 * So for each of the 8 directions the curDir%range will be from 0 - range,
    	 * Hence the probability of selecting cur direction varies from 0.4 to 0.9
    	 */
    	 //cur direction is worked in changeDir()
    	double p1 = GlobalSettings.PROB_CUR_DIR_LOWER + (curDir%range +  1)*(GlobalSettings.PROB_CUR_DIR_LOWER/(range));
//    	System.out.println(p1);

//		double p1 = 64*d*8; 
    	//curDir is any value between 0 and range * 8
    	int index = (int)(curDir/range);

		if (d < p1) {
    		return neighbours[index];
    	} else if (d < (1 + p1)/2){
    		return neighbours[(index+1)%3];
    	} else {
    		return neighbours[(index+2)%3];
    	}

		/*
    	if (d < p1) {
    		return neighbours[index];
    	} else if (d < (1 + p1)/2){
    		return neighbours[(index+1)%7];
    	} else {
    		return neighbours[(index+2)%7];
    	}
    */
    
    /*
     *
     *	if (d < p1) {
    		return neighbours[index];
    	} else if (d < (1 + p1)/7){
    		return neighbours[(index+1)%8];
    	} else {
    		return neighbours[(index+2)%8];
    	}
    	*/
    	
    	
    /*
     *
     *	if (d < p1) {
    		return neighbours[index];
    	} else if (d < (1 + p1)/3){
    		return neighbours[(index+1)%4];   		
    	}  else if (d < (1 + p1)/3){
    		return neighbours[(index+2)%4];
    	}	else {
    		return neighbours[(index+3)%4];
    	}
    	*/
    		
    	/*
    	 if (d < (p1)) {
    		return neighbours[index];
    	} else if (d < (1 + p1)/7){
    		return neighbours[(index+1)%8];
    	} else if (d < (2 + p1)/7){
    		return neighbours[(index+2)%8];
    	} else if (d < (3 + p1)/7){
    		return neighbours[(index+3)%8];
    	} else if (d < (4 + p1)/7){
    		return neighbours[(index+4)%8];
    	} else if (d < (5 + p1)/7){
    		return neighbours[(index+5)%8];
    	} else if (d < (6 + p1)/7){
    		return neighbours[(index+6)%8];
    	}   		else {
    		return neighbours[(index+7)%8];
    	}    		
    */
     			
    }

   
    //gets the position of the neighbour nodes
    // so x +1 , y means East 
    private Node[] getAllneighbours(Node n){
    	Node[] neighbours = new Node[8];
    	int x = n.getX(), y = n.getY();
    	
    	neighbours[0]=world.getNode(x+1, y); // east
    	neighbours[1]=world.getNode(x, y+1); // south
    	neighbours[2]=world.getNode(x+1, y+1); // East south
    	
    	neighbours[3]=world.getNode(x-1, y); //west
    	neighbours[4]=world.getNode(x, y-1); //north
    	neighbours[5]=world.getNode(x+1, y-1); //East north  
    	
    	neighbours[6]=world.getNode(x-1, y+1); //west south
    	neighbours[7]=world.getNode(x-1, y-1); // west north
    	
    	return neighbours;
    }
        
        // this will return either node with pherNode or randNode based on the pherNode in neighbours
    private Node followTrailTransitionRule (Node randNode, Node[] neighbours){
    	Node pherNode = neighbours[0];
    
    	//if food not found
    	if (antColony.getBestPathValue() == -1)
    		return randNode;
    	
    	for (int i = 1; i < neighbours.length; i++) {
        	//Lower the index the higher the value of pheromone
			if (neighbours[i].getPheromoneIndex() < pherNode.getPheromoneIndex()){
				pherNode = neighbours[i];
			}
		}

    	//Actually there is no pheromone in any neighbour, so just return random node
    	if (pherNode.getPheromoneIndex() == Node.LOWEST_PHEROMONE_INDEX)
    		return randNode;

    	
    	//We now pick the pheromone node with higher probablity
    	//this is very significant
    	double d = pseudoRandom.nextDouble();
    	
    
    	 // the probability can be decreased or increased from global settings, which signifiantly 
    	 // changes the result of the simulation gained, after a trial and error it was set to 0.6
    	 // increasing this ants follow the trail but for some reason dont seem to find new shorter path and decreasing it make
    	 //ant wander very randomly
    	if (d <= GlobalSettings.PROB_PHEROMONE_NODE)
    		return pherNode;
    	else
    		return randNode;
    }

  
    public void setFoundFood(boolean b){
    	foundFood = b;
    }
    public boolean isFoundFood (){
    	return foundFood;
    }
    
    
    public Node getCurNode(){
    	return curNode;
    }
    
   
    // when a next node is choosen, update the state of the ant 
    //update the current path, current path value
    public void updateCurPath(Node curNode, Node newNode) {
    	//First check if the new node is the start node.
    	if (newNode == startNode) {
    		//if food is found then we need to update bestpath
    		if (foundFood) {
    			updateBestPath();
    		}else {
        		if (DebugSettings.ALL_PATH_LENGTHS) {
        			DebugSettings.writeToFile("Not ending in food Path Len = "+curPath.getLength());
        		}
    		}
    		curPath = new Path();
 		
    		return;
    	}
    	
    	//Check if the new node falls on the same edge,
    	//We do this by taking the slope of the last edge and see our edge slope is the same
    	try {
			int x = newNode.getX(), y = newNode.getY(); 
			//get the lastedge
			Edge lastEdge = curPath.getLastEdge();
		
		
			Node source = lastEdge.getSource();
			Node dest = lastEdge.getDest();
			int sx = source.getX(), sy  = source.getY();
			int dx = dest.getX(), dy = dest.getY();
            
			// Check if source, destination and node are on the same line
			if ((sx == dx && x == dx && y>dy) || (sy == dy && y == dy && x>dx)){
				//update the lastEdge
				lastEdge.updateDestNode(newNode);
			} else if (sx != dx && sy != dy && x != dx && y != dy) {
				//update the lastEdge
				lastEdge.updateDestNode(newNode);
			} else {
				//create a new edge
				curPath.addEdge(new Edge(curNode,newNode));
			}
		} catch (NullPointerException e) {
			//This is the first edge
			curPath.addEdge(new Edge(curNode, newNode));
		}
    	
    }
    
    // updatedin updatecurPath if food found
    private void updateBestPath (){
    	
	    	
	    	double curLen = curPath.getLength();
		//if (DebugSettings.ALL_PATH_LENGTHS)
			{
				//	DebugSettings.writeToFile("" + antID);
				DebugSettings.writeToFile( "Total length to the food= " +curLen);
			
			}
	    	if (bestPath == null || bestPathValue > curLen){
	    		this.bestPath = curPath;
	    		bestPathValue = curLen;
	    	}
	    	updateAntState(this.curPath, this.bestPath);
	    
    }
    
    //update the bestPath and bestPathValue
    //Also update the pheromone content for the new path here
    public void updateAntState(Path curPath, Path bestPath){
    	curPath.updatePheromone(world, antColony.nodesWithPheromone);    	
    }
    
}
