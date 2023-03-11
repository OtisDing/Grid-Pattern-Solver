//Written by Otis Ding
//Student ID: 251220811
//Edge class - the objects that will represent the edges of our graph

public class Edge {

	//Initialize variables
	private Node firstNode, secondNode;
	private String type;
	
	
	//Constructor - Initiliza our instanced variables
	public Edge(Node u, Node v, String type) {
		this.firstNode = u;
		this.secondNode = v;
		this.type = type;
		
	}
	
	//Returns the firstNode the edge is connected to
	public Node firstNode() {
		return this.firstNode;
	}
	
	//Returns the second Node the edge is connected to
	public Node secondNode() {
		return this.secondNode;
	}
	
	
	//Returns the type of the edge
	public String getType() {
		return this.type;
	}
	
	
	
}
