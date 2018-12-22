/* LinkList
 *  Anderson, Franceschi
 */

/**
 * this class is a concrete implementation of the AbstractList.
 *
 * properties of this implementation are such that: - the list is singly linked
 * - data contained in the nodes is limited to integers - nodes are sorted in
 * ascending order of data - duplicate data is allowed - note that duplicate
 * data may cause inconsistent behavior in the Visualizer because the delete
 * method searches for the first instance of data. if a node besides the first
 * one is highlighted, the first one will still be deleted.
 */
public class LinkList extends AbstractList
{
  private Node head = null;

  public LinkList()
  {
    super(500, 400);
    v.drawList(head);
  }

  public LinkList(Node head)
  {
    super(500, 400); // set size for visualizer
    // set up the list
    head = head;

    animate(head);
  }

  public void insert(int i)
  {
  	Node node = new Node(i);

  	if (head == null)
  	{
    	// List is empty, so node is the new head:
    	head = node;
    	numberNodes++;
    	animate(head);
    	return;
  	}

  	Node current = head;
  	Node previous = null;

  // Traverse list to find insert point:
	while((current!=null)&&(current.getData()<=i))
	{
		animate(head, current);
		previous = current;
		current = current.getNext();
	}
  // Insert between previous and current:
	if(current == head)
	{
		animate(head, head, Visualizer.ACTION_INSERT_BEFORE);
		node.setNext(current);
		head = node;
	}
  // else Case 2: insert in the middle or at the end of the list
	else
	{
		animate(head, previous, Visualizer.ACTION_INSERT_AFTER);
		node.setNext(current);
		previous.setNext(node);
	}

  // Case 1: insert at the beginning:

    // Part 1 student code ends here.

    numberNodes++;
    // call animate again to show the status of the list
    animate(head);
  }

  public boolean delete(int i)
  {
    Node current = head;
    Node previous = null;

    // Look for node to delete:
    while((current!=null)&&(current.getData()!=i))
    {
		animate(head, current);
		if(current.getData()>i)
		{
			return false;
		}
		else
		{
			previous = current;
			current = current.getNext(); // check for getNext()
		}
    }

    // If not found, return false:
    if (current == null)
    {
	  return false;
    }

    animate(head, current, Visualizer.ACTION_DELETE);

    // Delete node:
    // Case 1: Its the head node:
	if(current == head)
	{
		head.setNext(current.getNext());
	}
    // else Case 2: Its not the head node:
	else
	{
		previous.setNext(current.getNext());
	}

    // Part 2 student code ends here.

    // At this point, the item has been deleted.
    // Decrement the number of nodes:
    numberNodes--;
    // Call animate again to show the status of the list:
    animate(head);
    return true;
  }

  public int count()
  {
    int n = 0;
    Node current = head;
    while (current != null)
    {
      animate(head, current);
      n++;
      current = current.getNext();
    }
    return n;
  }

  public void traverse()
  {
    traversal = "";
    Node current = head;
    while (current != null)
    {
      animate(head, current);
      traversal += (current.getData() + "  ");
      current = current.getNext();
    }
    v.drawList(head);
  }

  public void clear()
  {
    head = null;
    v.drawList(head);
  }

  public void animate(Node h, Node nd)
  {
    v.drawList(h, nd);
    delay(1000);
  }

  public void animate(Node h)
  {
    v.drawList(h);
  }

  public void animate(Node h, Node nd, int mode)
  {
    v.drawList(h, nd, mode);
    delay(1000);
  }
}
