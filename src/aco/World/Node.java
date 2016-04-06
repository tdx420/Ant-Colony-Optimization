package aco.World;

import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import aco.Ant.Controller;
import aco.Ant.GlobalSettings;

import aco.Gui.MainPanel;


public class Node extends JPanel{


	Food food;
	private int x;
	private int y;



	//create an array, where the index holds the current pheromone level. 
	//lowest pheromone index =99
	public final static int LOWEST_PHEROMONE_INDEX = GlobalSettings.LOWEST_PHEROMONE_INDEX;
	
	//
	public final static double[] PHEROMONE_LEVELS = new double[LOWEST_PHEROMONE_INDEX+1];
	static {
		//The highest pheromone level is at index , highest pheromone level can be 1
		PHEROMONE_LEVELS[0]=1;

		for (int i = 1; i < PHEROMONE_LEVELS.length-1; i++) {
			// N is chosen so that 0.6 ^N+1 is close to 0, n =99 so, the lowest pheromone will be 0
			PHEROMONE_LEVELS[i]=PHEROMONE_LEVELS[i-1]*0.6;
		}
		PHEROMONE_LEVELS[PHEROMONE_LEVELS.length-1]=0;
	}

	//This holds the index of the level. 
	private int pheromoneIndex;

	//-1 is used for food
	private static final int FOOD_VALUE = -1;

	public Node(int x, int y, final MainPanel mainPanel)
	{
		this.x = x;
		this.y = y;
		//Start with the lowest value of pheromone, so that PHEROMONE_LEVELS[LOWEST_PHEROMONE_INDEX] calculates to 0 
		// this is also not a food
		pheromoneIndex = LOWEST_PHEROMONE_INDEX;
		
		//makes the nodes draggable
        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                setLocation(e.getX() + getLocation().x,
                        e.getY() + getLocation().x);
                mainPanel.repaint();
            }
        });

	}

	public int getPheromoneIndex(){
		return pheromoneIndex;
	}

	public void setPheromoneIndex(int index){
		this.pheromoneIndex = index;
	}

	public void evaporatePheromone(int steps){
		
	//evaporate by steps
/*		if (pheromoneIndex == 0) {
			pheromoneIndex++;
		} else {*/
			pheromoneIndex = (pheromoneIndex + steps);
			//lowest_phermone index is 99
			//if pheromoneIndex > Lowest_pheromone_index then return lowset Pheromone_index else,pheromone INdex 
			pheromoneIndex = (pheromoneIndex>LOWEST_PHEROMONE_INDEX)?LOWEST_PHEROMONE_INDEX:pheromoneIndex;
		//}
	}
	

	//Returns true, if the controller was updated.
	//setPheromoneAt - 
	public boolean evaporateAndUpdateController(int steps, Controller controller){

		this.evaporatePheromone(steps);
		
		//if there is a pheromone in node, update the controller, set pheromone at 
	
		if (pheromoneIndex != LOWEST_PHEROMONE_INDEX) {
			controller.setPheromoneAt(this.x, this.y, pheromoneIndex,LOWEST_PHEROMONE_INDEX);
			return true;
		}
		return false;
	}
	
	
	
	
	public void setIsFood (int x , int y ){
		
		 //  this.food = newFood;
		//   food.pheromoneIndex = this.pheromoneIndex;
	//	newFood.setLocation(lx, ly);
		   this.pheromoneIndex = FOOD_VALUE;
		   setLocation(x,y);
		//	this.theFoods.add(newFood);
	}

	public boolean isFood(){
		
		return pheromoneIndex ==  FOOD_VALUE;
	
	}


	
	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}
	

	
	@Override
	public String toString() {

		return "x = "+x+" y = "+y;
	}	
}
