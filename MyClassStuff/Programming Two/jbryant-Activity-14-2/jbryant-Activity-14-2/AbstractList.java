/* AbstractList
*  Anderson, Franceschi
*/

public abstract class AbstractList
{
  /*
       INSTANCE VARIABLES
  */

  /**
   *  a Visualizer instance is maintained here so that the student
   *  can call it from the child class without worrying about the
   *  implementation of the Visualizer.  This is intended to mimic
   *  the use of a Graphic object in the Swing or AWT package.
   **/
  protected Visualizer v;

  protected int numberNodes;
  protected String traversal; // holds current traversal of the list

  /*
       CONSTRUCTORS
  */

  /**
   *   instantiate with the given GUI width and height for the Visualizer.
   *
   **/
  public  AbstractList( int width, int height )
  {
    v = new Visualizer( width, height );
    numberNodes = 0;
  }

  /*
      METHODS STUDENTS MUST OVERRIDE
  */

  /**
   *
   *  insert an object in to the list.
   *
   **/
  public abstract void insert( int i );

  /**
   *
   *  delete a node from the list based on reference.
   *
   *  @ return true if the node was deleted, false otherwise
   **/


  /**
   *
   *  delete a node from the list based on data.
   *
   *  @return true if the node was deleted, false otherwise
   *
   **/
  public abstract boolean delete( int i );

  /**
   *
   *  returns the number of Nodes in the list.
   *
   **/
  public abstract int count( );

  /**
   *
   *  walks the list and displays the data it contains.
   *
   **/
  public abstract void traverse( );

  /**
   *
   *  removes all nodes from the list.
   *
   **/
  public abstract void clear( );

  /*
     METHODS FOR CONTROLLING VISUALIZER
  */

  /**
   *
   *  sets the width and height in pixels for the visualizer.
   *
   **/
  public void setSize( int width, int height )
  {
    v.setSize( width, height );
    v.validate( );
  }

  /**
   *
   *  returns a reference to the Visualizer being used by this object.
   *  Any object calling this method is on its honor to not modify
   *  the Visualizer.
   *
   **/
  public Visualizer getVisualizer( )
  {
    return v;
  }

  /**
   *
   *  simple delay routine that makes the thread sleep for the given
   *  number of milliseconds.  useful for when a node is being
   *  highlighted by the visualizer.
   *
   **/
  public void delay( int milliseconds )
  {
    try
    {
      Thread.sleep( milliseconds );
    }
    catch ( Exception e )
    {
      System.out.println(" e: "+e );
    }
  }

  /*
   *
   * the following are wrapper methods to make calling the visualizer
   * a little more convienient.  see the wrapped Visualizer methods
   * for more information.
   *
   */

  public void drawList( Node headNode )
  {
    v.drawList( headNode );
  }

  public void drawList( Node headNode, int highlight )
  {
    v.drawList( headNode, highlight );
  }

  public void drawList( Node headNode, Node highlight )
  {
    v.drawList( headNode, highlight );
  }

  public void drawList( Node headNode, int target, int action )
  {
    v.drawList( headNode, target, action );
  }

  public void drawList( Node headNode, Node target, int action )
  {
    v.drawList( headNode, target, action );
  }

  public String getTraversal( )
  {
    return traversal;
  }

} // end abstract list