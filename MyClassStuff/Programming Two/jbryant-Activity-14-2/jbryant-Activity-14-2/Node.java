/* Node
*  Anderson, Franceschi
*/

public class Node
{
  /**  reference to the next node in the list **/
  private Node nextNode;

  /**  the data object held by this node **/
  private int data;


  /**
   *
   *  construct a new node with the given Object as data
   *
   **/
  public Node( int i )
  {
    data = i;
  }

  /**
   *
   *  construct a new node with the given Object as data
   *  and references to the next and previous nodes.
   *
   **/
  public Node( int i, Node next )
  {
    data = i;
    nextNode = next;
  }

  /**
   *
   *  set the reference to the next node
   *
   **/
  public void setNext( Node n )
  {
    nextNode = n;
  }

  /**
   *
   *  return the reference to the next node
   *  @return reference to the next node, or null if there is no next node
   *
   **/
  public Node getNext( )
  {
    return nextNode;
  }

  /**
   *
   *  set the data object held by this node
   *
   **/
  public void setData( int i )
  {
    data = i;
  }

  /**
   *
   *  get the data object held by this node
   *
   **/
  public int getData( )
  {
    return data;
  }

} // end node class