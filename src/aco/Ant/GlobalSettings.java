package aco.Ant;
/*
	This class enables user to directly change the variable and see its effect in simulation.
	 All the static variables are defined.
 */

public class GlobalSettings {

	//World Parameters
	public static  int WORLD_WIDTH = 600;
	public static  int WORLD_HEIGHT = 600;

	//Food Parameters
	public static  int FOOD_X = 500;
	public static  int FOOD_Y = 250;
	public static  int FOOD_WIDTH = 25;
	public static  int FOOD_HEIGHT = 25;
	
	//Ant Parameters
	//public static  int TOTAL_ANTS = 0;
	

	// how long does the simulation iterate for
	public static  int TOTAL_ITERATIONS = 5000;
	
	//Updates the pheromone of the best path found, so the best path found can be reinforced
	public static boolean REINFORCE_SHORTEST_PATH = true;


	//number of steps ant will do before notifying the obeserver
	//increasing this number leads to faster rate of iteration, thus faster, can be used as a slider to increase decrease the speed
	public static  int STEPS_PER_ITERATION = 2;
//	public static int Evaporation_step =2;
	

	//8 possible directions for ant to explore  , so an ant can have 64 direction to choose from those possible direction
	// so for 8 different possible direction, 8 x 8 

	public static  int ANTS_MAX_DIR =64; 
	
   	//minimum probability of selecting node in current direction
	public static  double PROB_CUR_DIR_LOWER = 0.4; 
	//maximum probability of selecting node in current direction

	// cur direction varies from lower to upper value, generally between lower and (lower + higher)
//	public static  double PROB_CUR_DIR_HIGHER = 0.4; 
	
	//probability of selecting a node in relative to one with pheromonenode vs random node
	//increasing this means more ant will find optimal path
	public static  double PROB_PHEROMONE_NODE = 0.6;

	//used in node class
	//used as index for array  . 
	//this should be choosen so that (0.6)^N+1 is closer to 0
	// affects evaporation rate
	public static  int LOWEST_PHEROMONE_INDEX = 99;
	
}
