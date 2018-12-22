/* Visualizer
*  Anderson, Franceschi
*/

import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.ArrayList;

public class Visualizer extends JPanel
{
  public static final int nodeWidth  = 100;
  public static final int nodeHeight = 100;
  public static final int halfWidth  = nodeWidth  / 2;
  public static final int halfHeight = nodeHeight / 2;
  public static final int qrtrWidth  = nodeWidth  / 4;
  public static final int qrtrHeight = nodeHeight / 4;

  public static final int nodeSpacer = halfWidth;

  /** node is drawn normally **/
  public static final int ACTION_NONE          = 0;
  /** node is drawn highlighted **/
  public static final int ACTION_HIGHLIGHT     = 1;
  /** indicator is drawn after node **/
  public static final int ACTION_INSERT_AFTER  = 2;
  /** indicator is drawn before node **/
  public static final int ACTION_INSERT_BEFORE = 3;
  /** node is drawn with delete indicator **/
  public static final int ACTION_DELETE        = 4;
  /** node is drawn with retrieve indicator **/
  public static final int ACTION_RETRIEVE      = 5;

  private int initialHeight = 0;
  private ArrayList<NodeWrapper> nodeData = new ArrayList<NodeWrapper>( );

  /**
   *
   *  creates a new Visualizer with the given width
   *  and height in pixels.  sets the preferred size
   *  to those values, and makes the Visualizer visible.
   *
   **/
  public Visualizer( int width, int height )
  {
    super( );

    initialHeight = height;

    setSize( width,height );
    setPreferredSize( new Dimension( width, height ) );
    setVisible( true );
    validate( );

  } // end default constructor

  /**
   *
   *  draws all nodes currently contained in the list of nodes.
   *
   **/
  protected void paintComponent( Graphics g )
  {
    super.paintComponent( g );

    for( int n = 0; n < nodeData.size( ); n++)
    {
      nodeData.get( n ).draw( g );
    }

    // the following lines let the scrollpane know to update
    revalidate( );
  } // end paintComponent

  /**
   *
   *  convenience method that is the same as calling
   *  drawList(Node, null, Visualizer.ACTION_NONE)
   *  so that the list is drawn with no nodes highlighted.
   *
   **/
  public synchronized void drawList( Node head )
  {
    nodeData.clear( );
    Node current = head;
    //highlightData = target;
    //this.action = action;
    int x = nodeSpacer;
    int y = 50;

    while( current != null )
    {
      nodeData.add( new NodeWrapper( current, x, y, ACTION_NONE ) );

      current = current.getNext( );
      x += ( nodeWidth+nodeSpacer );
    }
    setPreferredSize( new Dimension (x + nodeWidth + nodeSpacer, initialHeight ) );
    repaint( );
  } // end drawList

  /**
   *
   *  convenience method that is the same as calling
   *  drawList(Node,Object,Visualizer.ACTION_HIGHLIGHT)
   *  so that the list is drawn with a node that has a data
   *  object matching the given object highlighted.
   *
   **/
  public synchronized void drawList( Node head, int highlight )
  {
    drawList( head, highlight, Visualizer.ACTION_HIGHLIGHT );
  } // end drawList with a node highlighted

  public synchronized void drawList( Node head, Node highlight )
  {
    drawList( head, highlight, Visualizer.ACTION_HIGHLIGHT );
  } // end drawList with a node highlighted

  /**
   *
   *  draws graphical representations of link list nodes.  starting
   *  from the head node, the list is traversed in order and each
   *  node is displayed.  if a node contains a data object that is
   *  equal to target, that node will be drawn in such a way
   *  as to stand out from the other nodes according to the
   *  specified action.
   *
   *  @param head the head Node of a linked list.
   *
   *  @param highlight a data object to be matched against the data
   *                   objects in the list. if a match is found, that
   *                   node will be highlighted. to not highlight any
   *                   nodes, send null as the highlight parameter.
   *
   *  @param action the action to display upon finding a matching node.
   *                See the ACTION_<i>actiontype</i> final static ints
   *                specified by this class.
   *
   **/
  public synchronized void drawList( Node head, int target, int action )
  {
    nodeData.clear( );
    Node current = head;
    //highlightData = target;
    //this.action = action;
    int x = nodeSpacer;
    int y = 50;

    while( current != null )
    {
      if( target == current.getData( ) )
      {
        nodeData.add( new NodeWrapper( current, x, y, action ) );
        action = ACTION_NONE;
      }
      else
      {
        nodeData.add( new NodeWrapper( current,x , y, ACTION_NONE ) );
      }
      current = current.getNext( );
       x += ( nodeWidth + nodeSpacer );
    }
    setPreferredSize( new Dimension( x + nodeWidth + nodeSpacer, initialHeight ) );

    repaint( );
  } // end draw list (head, target, action)


  /**
   * Draws the list as described in drawList(Node, int, int), except that
   * here, the target node is found by reference instead of by value.
   *
   * @param head
   * @param target
   * @param action
   */
  public synchronized void drawList( Node head, Node target, int action )
  {
    nodeData.clear( );
    Node current = head;
    //highlightData = target;
    //this.action = action;
    int x = nodeSpacer;
    int y = 50;

    while( current != null )
    {
      if( target == current )
      {
        nodeData.add( new NodeWrapper( current, x, y, action) );
        action = ACTION_NONE;
      }
      else
      {
        nodeData.add( new NodeWrapper( current, x, y, ACTION_NONE ) );
      }
      current = current.getNext( );
      x += ( nodeWidth + nodeSpacer );
    }
    setPreferredSize( new Dimension( x + nodeWidth + nodeSpacer, initialHeight ) );

    repaint( );
  } // end draw list (head, target, action)


  /** draw the reference to null **/
  private void drawNullRef( Graphics g, int x, int y )
  {
    int startY = y + ( ( int ) ( nodeHeight * ( 3.0f / 4.0f) ) );
    int endY = y + ( int ) ( ( nodeHeight *1.5f ) );
    g.drawLine( x, startY, x, endY );
    g.drawLine( x - 10, endY, x + 10, endY );
    g.drawLine( x - 5, endY + 5, x + 5, endY + 5 );
  } // end draw null ref

  /** draw reference arrows from node to node **/
  private void drawRefs( Graphics g, NodeWrapper wn )
  {
    int x = wn.getX( );
    int y = wn.getY( );
    Node n = wn.getNode( ).getNext( );

    if( n == null )
    {
      drawNullRef( g, x + halfWidth, y );
    }
    else
    {
      for( int i = 0; i < nodeData.size( ); i++ )
      {
        NodeWrapper nw = nodeData.get( i );
        if( n == nw.getNode( ) )
        {
          int targetX = nw.getX( );
          int targetY = nw.getY( );
          g.fillOval( x + halfWidth, y + ( (int ) ( nodeHeight * ( 3.0f / 4.0f ) ) ) - 3, 6, 6 );
          g.drawLine( x + halfWidth, y + ( ( int ) ( nodeHeight * ( 3.0f / 4.0f ) ) ), x + nodeWidth + 13, y + ( ( int ) ( nodeHeight * ( 3.0f / 4.0f ) ) ) );
          g.drawLine( x + nodeWidth + 13, y + ( ( int ) ( nodeHeight * ( 3.0f / 4.0f ) ) ), targetX - 13, targetY + 15 );
          g.drawLine( targetX - 13, targetY + 15, targetX, targetY + 15 );

          g.drawLine( targetX, targetY + 15, targetX - 7, targetY + 10 );
          g.drawLine( targetX, targetY + 15, targetX - 7, targetY + 20 );

          break;
        }
      }
    }
  } // end draw refs

  /**
   *
   *  detect if there is a node at the given the x,y coordinate.  if
   *  there is, it will be highlighted and that node will be returned.
   *
   *  @return the node at the given coordinate, or null if there is no
   *          node at the coordinate
   *
   **/
  public Node selectNode( int mouseX, int mouseY )
  {
    int x = 0;
    int y = 0;

    int size = nodeData.size( );
    Node n = null;
    for( int i = 0; i < size; i++ )
    {
      x = nodeData.get( i ).getX( );
      y = nodeData.get( i ).getY( );

      if(   x < mouseX && mouseX < x + nodeWidth
            && y < mouseY && mouseY < y + nodeHeight )
      {
            // if it was selected, hightlight it
            ( nodeData.get( i ) ).setAction( ACTION_HIGHLIGHT );

            // output.put( "panel clicked: \"" + ( nodeData.get( i ) ).getNode( ).getData( )+"\" selected" );
            n = ( nodeData.get( i ) ).getNode( );

      }
      else
      {
        // otherwise, no special treatment
        ( nodeData.get( i ) ).setAction( ACTION_NONE );
      }
    }
    repaint( );
    return n;
  } // end selectNode at coordinate


  /** wraps a Node with extra information so it can
      be displayed in the Visualizer.
  **/
  private class NodeWrapper
  {
    int nodeAction = ACTION_NONE;
    Node node = null;
    int x = 0;
    int y = 0;

    NodeWrapper( Node n, int x, int y, int action )
    {
      this.nodeAction = action;
      this.node = n;
      this.x = x;
      this.y = y;

    }

    public Node getNode( )
    {
      return node;
    }

    public int getX( )
    {
      return x;
    }

    public int getY( )
    {
      return y;
    }

    public void setAction( int action )
    {
      this.nodeAction = action;
    }

    /** draw the node at current x,y coord with current action type **/
    void draw( Graphics g )
    {
      // draw the node representation
      g.setColor( Color.BLACK );
      g.drawRect( x, y, nodeWidth, nodeHeight ); // main rectangle
      g.drawLine( x, y + halfHeight, x + nodeWidth, y + halfHeight );  //horizontal line
      //g.drawLine( x + halfWidth, y + halfHeight, x + halfWidth,y+nodeHeight );  // vertical line

      // in case the data.toString is really long, this will shrink the
      // font size so that it fits in the node drawing.
      int fontSize = 16;
      Font font = new Font( "SansSerif", Font.PLAIN, fontSize );
      FontMetrics metrics = getFontMetrics( font );
      String str = ""+node.getData( );
      while( fontSize > 0 && metrics.stringWidth( str ) > nodeWidth - 10 )
      {
        //System.out.println( "str: " + str + " width: " + metrics.stringWidth( str ) + " size: " + fontSize );
        font = new Font( "SansSerif", Font.PLAIN, fontSize-- );
        metrics = getFontMetrics( font );
      }

      g.setFont( font );

      g.drawString( str, x + 5, y + 20 ); // data as a string

      // draw the reference arrows
      drawRefs( g, this );

      // determine which action should be used, draw anything special for it.
      if( nodeAction == ACTION_HIGHLIGHT )
      {
        g.setColor( Color.BLUE );
        g.drawRect( x, y, nodeWidth, nodeHeight ); // main rectangle
        g.drawLine( x, y + halfHeight, x + nodeWidth, y + halfHeight );  //horizontal line
        // g.drawLine(x+halfWidth,y+halfHeight,x+halfWidth,y+nodeHeight);  // vertical line
        g.drawString(str,x+5,y+20);
        g.setColor(Color.magenta);
        drawRefs(g,this);
      }
      else if( nodeAction == ACTION_INSERT_BEFORE )
      {
        g.setColor( Color.GREEN );
        int offset = nodeSpacer>>1;
        g.drawLine( x - offset, y, x - offset, y - 30 );  // arrow shaft
        g.drawLine( x - offset, y, x - offset + 5, y - 10 );  // arrow left
        g.drawLine( x - offset, y, x - offset - 5, y - 10 );  // arrow right
      }
      else if( nodeAction == ACTION_INSERT_AFTER )
      {
        g.setColor( Color.GREEN );
        int offset = nodeSpacer >> 1;
        g.drawLine( x + nodeWidth + offset, y, x + nodeWidth + offset, y - 30 );  // arrow shaft
        g.drawLine( x + nodeWidth + offset, y, x + nodeWidth + offset + 5, y - 10 );  // arrow left
        g.drawLine( x + nodeWidth + offset, y, x + nodeWidth + offset - 5, y - 10 );  // arrow right
      }
      else if( nodeAction == ACTION_DELETE )
      {
        g.setColor( Color.red );
        g.drawLine( x - 10, y - 10, x + nodeWidth + 10, y + nodeHeight + 10 );
        g.drawLine( x - 10, y + nodeHeight + 10, x + nodeWidth + 10, y - 10 );
      }
      else if( nodeAction == ACTION_RETRIEVE )
      {
        g.setColor( Color.YELLOW );
        g.drawRect( x - 10, y - 10, nodeWidth + 20, nodeHeight + 20 );
      } // end drawing decision block

    } // end draw

  } // end node wrapper

} // end visualizer