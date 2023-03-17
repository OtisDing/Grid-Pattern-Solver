//Written by Otis Ding
//Student ID: 251220811
//MyMap class - constructs the graph we need with the given input, and finds the path to victory!

//Importing some java libraries. For file input and some other stuff
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;


public class MyMap {

	//Initialize variables
	private int start, end, width, length, private_roads, construction_roads, mapSize;
	private Graph map;
	Stack nodeStack;
	
	//Constructor - Initialize variables as well as the graph with the input file
	public MyMap(String inputFile) throws MapException, FileNotFoundException {
		
		//Making an array to store the first 7 numbers of the input file
		int arr[] = new int[7];
		
		//Checks if the input file exists
		File f = new File(inputFile);
		if (!f.exists()) { 
			throw new MapException("Error: input file does not exist");
		}
		
		//Making a new scanner object to read the file
		Scanner fileReader = new Scanner(f);

		//Iterates through the first 7 lines of the file and puts them in the array as integers
		for (int i = 0; i < 7; i ++) {
			String initialData = fileReader.nextLine();
			int data = Integer.parseInt(initialData);
			arr[i] = data;
		}
		
		//Initializes our instanced variables to what the input file specifies
		this.start = arr[1];
		this.end = arr[2];
		this.width = arr[3];
		this.length = arr[4];
		this.private_roads = arr[5];
		this.construction_roads = arr[6];
		this.mapSize = this.width * this.length;
		
		//Creates a new map object
		this.map = new Graph(this.mapSize);
		
		//Creates a mew stack object
		this.nodeStack = new Stack(this.mapSize);
		
		//The algorithm to construct the graph, as specified in class and in the video
		boolean horiz = true;
		int first = 0;
		int c = 0;
		int i = 1;
		
		//Loops for as many lines as the file has left
		while (fileReader.hasNextLine()) {
			String edgeData = fileReader.nextLine();
			
			//Checks if horiz is true, since we treat the horizontal lines differently than the vertical ones
			if (horiz == true) {
				c = first;
				i = 1;
				while (i < edgeData.length()) {
					try {
						
						//We check what type the edge is, and then we input it into our graph
						//We then update both i and c
						if (edgeData.charAt(i) == 'B') {
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'V'){
							map.addEdge(map.getNode(c), map.getNode(c+1), "private");
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'P') {
							map.addEdge(map.getNode(c), map.getNode(c+1), "public");
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'C') {
							map.addEdge(map.getNode(c), map.getNode(c+1), "construction");
							c++;
							i = i + 2;
						}
					} catch (GraphException e) {
					}
				}
				
				//the loop ends with i being greater than the width of the graph, in which case we switch horiz to false
				horiz = false;
			} else if (horiz == false) {
				i = 0;
				c = first;
				while (i <= edgeData.length()) {
					try {
						
						//This time i is intialized to 0, and we check all the vertical edges
						//We then input the edges into the graph depending on their type, and increment both i and c
						if (edgeData.charAt(i) == 'B') {
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'V'){
							map.addEdge(map.getNode(c), map.getNode(c + this.width), "private");
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'P') {
							map.addEdge(map.getNode(c), map.getNode(c + this.width), "public");
							c++;
							i = i + 2;
						} else if (edgeData.charAt(i) == 'C') {
							map.addEdge(map.getNode(c), map.getNode(c + this.width), "construction");
							c++;
							i = i + 2;
						}
					} catch (GraphException e) {
					}
				}
				//Once the vertical line has been inputed, we move onto the next horizontal
				//We thus need to update first and switch horiz back to true
				first = first + this.width;
				horiz = true;
			}
		}
		
		//Finally we close the file
		fileReader.close();
	}
	
	//Returns the graph
	public Graph getGraph() {
		return this.map;
	}
	
	//Returns the starting node
	public int getStartingNode() {
		return this.start;
	}
	
	//Returns the destination node
	public int getDestinationNode() {
		return this.end;
	}
	
	//Returns the maximum number of private roads this path can take
	public int maxPrivateRoads() {
		return this.private_roads;
	}
	
	//Returns the maximun number of construction roads this path can take
	public int maxConstructionRoads() {
		return this.construction_roads;
	}
	
	//Iterator - We return an iterator object of all the nodes that comprise of a valid path from the start node to the destination node
	public Iterator findPath(int start, int destination, int maxPrivate, int maxConstruction) {
		
		//Checks if path is true, if it is, then a valid path exists, and our instanced stack has all the nodes we need
		if (path(this.start, this.end, this.nodeStack, this.maxPrivateRoads(), this.maxConstructionRoads())) {
			
			//We make an ArrayList with the nodes in the stack
			//Then we make that ArrayList into an interator object, which we then return
			ArrayList<Node> arrListNodes = new ArrayList<Node>();
			
			for (int i = this.nodeStack.getTopInt(); i >= 0; i--) {
				arrListNodes.add(this.nodeStack.pop());
			}
			
			Iterator<Node> it = arrListNodes.iterator();
			return it;
		} else {
			
			//If a valid path cannot be found, we return null
			return null;
		}
		
		
	}
	
	//Private path class - A modified version of the path class given to us in class
	private boolean path(int start, int destination, Stack stack, int maxPrivate, int maxConstruction) {
		
		try {
			//Marks the node, and pushes it into the stack
			Node curr = this.map.getNode(start);
			this.map.getNode(start).markNode(true);
			this.nodeStack.push(curr);
			
			//Grab the iterator for all the edges this node has
			Iterator iter = this.map.incidentEdges(curr);
			
			//We check if start == destination
			//If this condition is true, then it means we've found our destination and we should return true
			if (curr.getId() == this.map.getNode(destination).getId()) {
				return true;
			}
			
			//Iterates through all the edges connected to the start node
			while(iter.hasNext()) {
				
				//Assigns currentEdge to the edge we're currently looking at
				Edge currentEdge = (Edge) iter.next();
				
				//We check if currentEdge is a road that is currently accessible to us
				//Whether its private or we have enough construction or private roads to access it
				if (((currentEdge.getType().equals("private")) && (maxPrivate > 0)) || ((currentEdge.getType().equals("construction") && (maxConstruction > 0)) || (currentEdge.getType().equals("public")))) {
					
					//We Assign variables to both nodes connected on both ends of this edge
					Node node1 = currentEdge.firstNode();
					Node node2 = currentEdge.secondNode();
					
					//Checks if node1 is our start node
					if (node1.getId() == start) {
						
						//Checks if node2 is marked already
						if (node2.getMark() == false) {
							
							//If node2 is not markes, we'll move to it next
							//Which means we have to check if the currentEdge is private or construction
							//If it is either of those two, we must update the remaining private or construction roads we can use
							if (currentEdge.getType().equals("private")) {
								maxPrivate = maxPrivate - 1;
							} else if(currentEdge.getType().equals("construction")) {
								maxConstruction = maxConstruction - 1;
							}
							
							//Recursive step to search further into the graph
							if (path(node2.getId(), destination, stack, maxPrivate, maxConstruction) == true) {
								return true;
								
							//If we reach a deadend, we reverse our step and regain a private or construction road if appropriate
							} else {
								if (currentEdge.getType().equals("private")) {
									maxPrivate++;
								} else if (currentEdge.getType().equals("construction")) {
									maxConstruction++;
								}
							}
						}
						
					//Same thing as above, only if node2 is the start node
					} else if (node2.getId() == start) {
						if (node1.getMark() == false) {
							if (currentEdge.getType().equals("private")) {
								maxPrivate = maxPrivate - 1;
							} else if(currentEdge.getType().equals("construction")) {
								maxConstruction = maxConstruction - 1;
							}
							if (path(node1.getId(), destination, stack, maxPrivate, maxConstruction) == true) {
								return true;
							} else {
								if (currentEdge.getType().equals("private")) {
									maxPrivate++;
								} else if (currentEdge.getType().equals("construction")) {
									maxConstruction++;
								}
							}
						}
					}
				}
			}
			
			//If we've iterated through all the available edges of this node and haven't come to a solution, it means we're at a dead end
			//We unmark the node, pop it from our stack, and return to the previous node
			curr.markNode(false);
			this.nodeStack.pop();
			return false;
			
			
		} catch (GraphException e) {
		}
		return false;
		
	}
	
	
	
	
	
}
