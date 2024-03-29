//Written by Otis Ding
//Student ID: 251220811

public class LinearNode<E>
{
    private LinearNode<E> next;
    private E element;
    
    //Creates an empty node. 
    public LinearNode()
    {
        next = null;
        element = null;
    }
    
    //Creates a node storing the specified element.
    public LinearNode (E elem)
    {
        next = null;
        element = elem;
    }
    
    //Returns the node that follows this one.
    public LinearNode<E> getNext()
    {
        return next;
    }
    
    //Sets the node that follows this one.
    public void setNext (LinearNode<E> node)
    {
        next = node;
    }
    
    //Returns the element stored in this node.
    public E getElement()
    {
        return element;
    }
    

    //Sets the element stored in this node.
    public void setElement (E elem)
    {
        element = elem;
    }
}
