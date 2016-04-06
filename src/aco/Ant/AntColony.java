package aco.Ant;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import aco.Gui.AntApplication;
import aco.World.Node;
import aco.World.Path;
import aco.World.World;
import aco.World.Food;



public class AntColony implements Observer
{
	protected Ant[]    ants;
	protected World    world;
	
	protected int      numAnts;
    public void setNumAnts(int numAnts) {
		this.numAnts = numAnts;
	}



	protected int      antCounter;
    
    protected int      iterCounter;
    protected int      iterations;

    private Double globalBestpathValue;
    
    public void setGlobalBestpathValue(Double globalBestpathValue) {
		this.globalBestpathValue = globalBestpathValue;
	}

	

	private Path globalBestPath;
    private int numAntsStarted;
    
    private int numberOfPaths;

    private static final int ANTS_PER_BATCH = 1;
    //this is initialized in launcher, the constructor of antcolony takes controller
    protected Controller controller;

    protected LinkedList<Node> nodesWithPheromone;
    
    public AntColony(World graph, int nAnts, int nIterations, Controller controller)
    {
        world = graph;
        numAnts = nAnts;
        
        iterations = nIterations;
        globalBestpathValue = -1.0;
        globalBestPath = null;
        this.controller = controller;
        nodesWithPheromone = new LinkedList<Node>();
    }
    
    public synchronized void start()
    {
        // creates all ants
        ants  = createAnts(world, numAnts);
        
        iterCounter = 0;
        
        // loop for all iterations
        while(iterCounter < iterations)
        {
            //Used for calculating the rate of change of path len
          
            
        	controller.setStartOfIteration();
        	
            // run the iteration
            iteration();
            try
            {
                wait();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            
            // synchronize the access to the graph
            synchronized(world)
            {
                // apply global updating rule
                globalUpdatingRule();
            }

    		if (DebugSettings.PATH_LEN_RATE && numberOfPaths>0) {
    			
    			DebugSettings.writeToFile("Rate of change of path len = "+numberOfPaths);
    		}
            controller.setEndOfIteration();
        }
        DebugSettings.closeFile();
        System.out.println("END OF SIMULATION");
        
    }
    
    private void iteration()
    {
        antCounter = 0;
        
        //increment the iterCounter by 1
        iterCounter++;
        
        numAntsStarted +=ANTS_PER_BATCH;
        if (numAntsStarted > numAnts) {
        	numAntsStarted = numAnts;
        }
        
        //start all the ants
        for(int i = 0; i < numAntsStarted; i++)
        {
        	try{
            ants[i].start();}
        	catch(ArrayIndexOutOfBoundsException e){
        		
        	}
        }
    }
    
    
	public int getAnts()
    {
        return ants.length;
    }
    
	public World getGraph()
    {
        return world;
    }
	
    public int getIterations()
    {
        return iterations;
    }
    
    public int getIterationCounter()
    {
        return iterCounter;
    }
    
        //update method of observe
        //without this the ant and food wont appear on the frame
        //uses the interface controller, set 
    public synchronized void update(Observable ant, Object obj)
    {
    	Ant a = (Ant)ant;
    	Node n = a.getCurNode();
    	
    	controller.setAntAt(n.getX(), n.getY());
    	controller.setFoodAt();
        antCounter++;
        
        if(antCounter == numAntsStarted)
        {          
            notify();       
        }
    }
    
    //find the best global path length
    public double getBestPathValue()
    {    			
        return this.globalBestpathValue;
    }
      /*
    public boolean done()
    {
        return iterCounter == iterations;
    }
*/
//the shortest path found so far, update as the better paths are found
	public void updateBestPath(  Path bestPath, double bestPathValue) {
	
		Food f[] = world.f;
		for (int i = 0; i < f.length; i++) 
		{
			numberOfPaths++;
	
		
			if (globalBestpathValue == -1 || bestPathValue < globalBestpathValue)
			{
				
				globalBestpathValue = bestPathValue;
				globalBestPath = bestPath;
				System.out.println("Shortest Path so far  "+ f[i].name + globalBestpathValue);
					DebugSettings.writeToFile( "Shortest Path so far  "+ globalBestpathValue);
			//	DebugSettings.writeToFile(" " + globalBestpathValue);
					AntApplication.setDisplayLabel(globalBestpathValue.toString());
			
			}
		}
	}
	
    //find the best global path
    public Path getBestPath(){return globalBestPath;}
          
    //method to create the ants
    protected Ant[] createAnts(World graph, int ants) {
    	Ant[] antArr = new Ant[ants];
    	for (int i = 0; i < antArr.length; i++) {
			antArr[i] = new Ant(graph.getStartNode());
		}
    	numAntsStarted = 0;
    	return antArr;
    }
    
    //method to update the pheromone globally (pheromone evaporation)
    protected void globalUpdatingRule(){
    	
    	//ant has not found food , so just return
    	if (globalBestpathValue < 0)
    		return;
    	
    	//Keep reinforcing the shortest path
    
    	if (GlobalSettings.REINFORCE_SHORTEST_PATH)
    		globalBestPath.updatePheromone(world, nodesWithPheromone);
    	
    	//Traverse thru the nodes in the list and evaporate
    	for (Iterator iterator = nodesWithPheromone.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			//The 1st arg is stepsPerIteration
			//when false, the pheromones have evaporated so we remove the pheromones
			if (node.evaporateAndUpdateController(GlobalSettings.STEPS_PER_ITERATION, controller) == false)
			{
				iterator.remove();
			}
		}

    }

}
