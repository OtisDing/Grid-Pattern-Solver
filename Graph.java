//Written by Otis Ding
//Student ID: 251220811
//Graph Class - the data structure that will be storing all our edges and nodes

import java.util.*;

public class Graph implements GraphADT{

	//Initialize some variables
	private Node nodeList[];
	private LinearNode edgeList[];
	private int graphSize;
	
	//Constructor - initialize instance variables and create and empty list for all the edges, and a full list of all the nodes
	public Graph(int n) {
		this.nodeList = new Node[n];
		this.edgeList = new LinearNode[n];
		this.graphSize = n - 1;
		
		for (int i = 0; i < 0; i++) {
			this.edgeList[i] = null;
		}
		
		for (int i = 0; i < n; i++) {
			this.nodeList[i] = new Node(i);
		}
	}
	
	//GetNode - Checks if the given node id is valid, if so, returns the whole node object
	public Node getNode(int id) throws GraphException{
		if((id > this.graphSize) || (id < 0)) {
			throw new GraphException("Error: Invalid ID");
		} else {
			return this.nodeList[id];
		}
	}
	
	//AddEdge - Adds an edge object to its appropriate place in the edgelist array
	public void addEdge(Node u, Node v, String edgeType) throws GraphException{
		
		//Checks that both provided nodes are valid
		if ((u.getId() > this.graphSize) || (u.getId() < 0)){
			throw new GraphException("Error: Invalid Node");
		}
		if ((v.getId() > this.graphSize) || (v.getId() < 0)) {
			throw new GraphException("Error: Invalid Node");
		}
		
		//Some flags to make sure we've inputed the correct edge for both nodes
		boolean inputedU = false;
		boolean inputedV = false;
		
		
		//We set curr to the first edge of u's edgelist and we check if u already has any edges or not
		LinearNode curr = this.edgeList[u.getId()];
		if (curr == null) {
			
			//If this is u's first edge, we put it in a linearNode and attach it to its position in the edge list
			LinearNode newNode = new LinearNode();
			Edge newEdge = new Edge(u, v, edgeType);
			newNode.setElement(newEdge);
			
			this.edgeList[u.getId()] = newNode;
			
			//We mark that the edge has been inputed for u
			inputedU = true;
		}
		
		//Same thing as above, only for v this time
		curr = this.edgeList[v.getId()];
		if (curr == null) {
			LinearNode newNode = new LinearNode();
			Edge newEdge = new Edge(u, v, edgeType);
			newNode.setElement(newEdge);
			
			this.edgeList[v.getId()] = newNode;
			
			//And we mark that v's edge has been inputed
			inputedV = true;
		}
		
		//We check if both nodes have been inputed, if so, we immediately return
		if ((inputedV == true) && (inputedU == true)) {
			return;
		}
		
		
		//We set two curr variables for each node u and v
		LinearNode currU = this.edgeList[u.getId()];
		LinearNode currV = this.edgeList[v.getId()];
		
		//We loop through both of the adjacency lists for the edges and check if the edge already appears in both of them, if it does, then it means the edge has already been inputed into the list
		while (currU != null) {
			if (((((Edge)currU.getElement()).firstNode().getId() == u.getId()) && (((Edge)currU.getElement()).secondNode().getId() == v.getId())) || ((((Edge)currU.getElement()).firstNode().getId() == v.getId()) && (((Edge)currU.getElement()).secondNode().getId() == u.getId()))) {
				currV = this.edgeList[v.getId()];
				while (currV != null) {
					if (((((Edge)currV.getElement()).firstNode().getId() == u.getId()) && (((Edge)currV.getElement()).secondNode().getId() == v.getId())) || ((((Edge)currV.getElement()).firstNode().getId() == v.getId()) && (((Edge)currV.getElement()).secondNode().getId() == u.getId()))) {
						throw new GraphException("Error: Edge already inputed");
					}
					currV = currV.getNext();
				}
			}
			currU = currU.getNext();
		}
		
		//If the edge hasn't been inputed for u yet, we add the edge to the end of its linked list of edges
		if (inputedU == false) {
			curr = this.edgeList[u.getId()];
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			LinearNode uNode = new LinearNode();
			curr.setNext(uNode);
			Edge uEdge = new Edge(u, v, edgeType);
			uNode.setElement(uEdge);
		}
		
		//If the edge hasn't been inputed for v yet, we add the edge to the end of its linked list of edges
		if (inputedV == false) {
			curr = this.edgeList[v.getId()];
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			LinearNode vNode = new LinearNode();
			curr.setNext(vNode);
			Edge vEdge = new Edge(u, v, edgeType);
			vNode.setElement(vEdge);
		}
	}
	
	//Iterator method - we return an interator of the edges incident around a given node u
	public Iterator incidentEdges(Node u) throws GraphException { // NEEDS WORK
		
		//Checks if u is a valid node
		if ((u.getId() > this.graphSize) || (u.getId() < 0)) {
			throw new GraphException("Error: Node not in graph");
		}
		
		//Makes an ArrayList out of the linked list of edges
		ArrayList<Edge> incidentEdges = new ArrayList<Edge>();
		LinearNode curr = this.edgeList[u.getId()];
		
		if (curr == null) {
			return null;
		}
		
		while (curr != null) {
			incidentEdges.add((Edge) curr.getElement());
			curr = curr.getNext();
		}
		
		//Makes the ArrayList into an interator object and returns it
		Iterator<Edge> it = incidentEdges.iterator();
		return it;
	}

	
	
	//GetEdge - returns an edge given the two nodes connecting to it
	public Edge getEdge(Node u, Node v) throws GraphException {
		
		//Checks if the nodes are valid
		if (((u.getId() > this.graphSize) || (u.getId() < 0)) || ((v.getId() > this.graphSize) || (v.getId() < 0))) {
			throw new GraphException("Error: Invalid Nodes");
		}
		
		LinearNode curr = this.edgeList[u.getId()];
		
		//We only need to check the edge list for one of the nodes since we always input the edges into both of the nodes
		//We iterate through the linked list until we find the edge we're looking for, which we then return
		while (curr != null) {
			if (((((Edge)curr.getElement()).firstNode().getId() == u.getId()) && (((Edge)curr.getElement()).secondNode().getId() == v.getId())) || ((((Edge)curr.getElement()).firstNode().getId() == v.getId()) && (((Edge)curr.getElement()).secondNode().getId() == u.getId()))) {
				return (Edge) curr.getElement();
			}
			curr = curr.getNext();
		}
		
		throw new GraphException("Error: Edge does not exist");
		
	}

	
	
	
	//areAdjacent - checks if two nodes are adjacent to each other
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		
		//Checks that both nodes are valid
		if (((u.getId() > this.graphSize) || (u.getId() < 0)) || ((v.getId() > this.graphSize) || (v.getId() < 0))) {
			throw new GraphException("Error: Invalid Nodes");
		}
		
		LinearNode curr = this.edgeList[u.getId()];
		
		//Loops through the edgelist of one of the nodes to check if an edge exists between the two of them
		//If it does, returns true
		while (curr != null) {
			if (((((Edge)curr.getElement()).firstNode().getId() == u.getId()) && (((Edge)curr.getElement()).secondNode().getId() == v.getId())) || ((((Edge)curr.getElement()).firstNode().getId() == v.getId()) && (((Edge)curr.getElement()).secondNode().getId() == u.getId()))) {
				return true;
			}
			curr = curr.getNext();
		}
		return false;
		
	}
	
	
}
