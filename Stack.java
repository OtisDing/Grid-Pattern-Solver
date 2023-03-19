//Written by Otis Ding
//Student ID: 251220811
//Stack class that will store the node's we've visited for the MyMap class

public class Stack {

	//Initialize some variables
	private int size, top;
	private Node stack[];
	
	//Constructor - Initialize instanced variables
	public Stack(int size) {
		this.size = size;
		this.top = -1;
		this.stack = new Node[size];
	}
	
	//Returns the node at the top of the stack
	public Node getTop() {
		return this.stack[this.top];
	}
	
	//Pushes a new node into the stack, and updates top
	public void push(Node input) {
		this.top++;
		this.stack[this.top] = input;
	}
	
	//Pop - removes the node at the top of the stack and returns it
	public Node pop() {
		Node output = getTop();
		this.stack[this.top] = null;
		this.top = this.top - 1;
		return output;
	}
	
	//Returns the number of items in the stack
	public int getTopInt() {
		return this.top;
	}
	
}
