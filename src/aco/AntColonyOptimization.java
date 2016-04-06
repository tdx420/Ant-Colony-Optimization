package aco;

import aco.Ant.GlobalSettings;
import aco.Gui.AntApplication;


public class AntColonyOptimization {
	public static void main(String[] args) {
		AntApplication l = new AntApplication(GlobalSettings.WORLD_HEIGHT,GlobalSettings.WORLD_HEIGHT);
		l.start();
	}
}
