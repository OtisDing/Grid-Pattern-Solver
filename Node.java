//Written by Otis Ding
//Student ID: 251220811
//Node Class - creates the nodes in our graph

public class Node {

	//Initialize our instanced variables
	private int id;
	private boolean mark = false;
	
	//Construction - Initialize the Node's ID
	public Node(int id){
		this.id = id;
	}
	
	//MarkNode - Marks the node
	public void markNode(boolean mark){
		this.mark = mark;
	}
	
	//Returns the mark of the node, whether it is marked or not
	public boolean getMark() {
		return this.mark;
	}
	
	//Returns the ID
	public int getId() {
		return this.id;
	}
	
}
