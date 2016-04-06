package aco.Ant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DebugSettings {

	private static String FILE_NAME="Statistics";
	
	public static boolean ALL_PATH_LENGTHS=false;
	public static boolean PATH_LEN_RATE = false;

	
	private static BufferedWriter writer;
	
	static {
		try {
			writer = new BufferedWriter(new FileWriter(FILE_NAME));
		} catch (IOException e) {
			System.out.println("IO ERROR "+e.toString());
		}
	}
	
	public synchronized static void writeToFile(String s){
		try {
			writer.write(s);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			System.out.println("IO ERROR "+e.toString());
		}
	}
	public static void closeFile(){
		try {
			writer.close();
		} catch (IOException e) {
			//do nothing
		}
	}
}
