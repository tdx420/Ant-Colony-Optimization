package aco.World;

import java.util.*;

import aco.Ant.Controller;


public class Path {

	private ArrayList<Edge> Edges;
	private double length = 0.0;
	private Edge lastEdge;
	
	public Path() {
		Edges = new ArrayList<Edge>();
		length = 0.0;
		lastEdge = null;
	}
	
	
	/**
     * Sets a connection between this edge and last edge
     * @param newEdge The edge to connect the last edge to
     */
	public void addEdge(Edge newEdge)
	{
		this.Edges.add(newEdge);
		lastEdge = newEdge;
	}
	
	public Edge getLastEdge (){
		return lastEdge;
	}
	
	/**
     * Get all the edges this edge is connected to
     * @return List of edges this edge is connected too
     */
	public ArrayList<Edge> getEdges()
	{
		return this.Edges;
	}
	/**
     * Get the length .return the total length as long as there is a next edge availiable
     *the distance formula is used to calculate the total length from source to destinnation in
     *in  getLength() method and the total length so far is added each time
     */
	public double getLength()
	{
		Iterator itr = this.Edges.iterator();
		while(itr.hasNext())
		{
			Edge currentEdge = (Edge) itr.next();
			length = length + currentEdge.getLength();
		}
		return length;
	}
	/**
     * Updates the pheromone
     * @param world the world 
     *updates the pheromone as long as there is the next edge
     */
	public void updatePheromone (World world, LinkedList<Node> list){
		Iterator<Edge> itr = this.Edges.iterator();
		while(itr.hasNext())
		{
			Edge currentEdge = (Edge) itr.next();
			currentEdge.updatePheromone(world, list);
		}
	}
}
