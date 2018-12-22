/* LinkedListPractice
*  Anderson, Franceschi
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LinkedListPractice extends JFrame
{
  /*
        INSTANCE VARIABLES
           These variables are directly responsible for
           handling the student code: instantiating an
           object of the student's list, displaying it graphically,
           manipulating it by calling the various methods.
  */

  /**
   *  rationale:  use AbstractList so that an instructor can have
   *              students create any other list type and still
   *              be able to use all the same method calls.  The only
   *              change that needs to be made to this code is
   *              changing the instantiation to whatever type the
   *              instructor wants.
   **/
  private AbstractList list;

  /**
   *   this class is on its honor to not modify the Visualizer.
   *   the list instance returns a reference to its Visualizer
   *   so that this class can add it in to the JFrame, but this
   *   class should never directly modify that instance.
   **/
  private Visualizer v;

  /*
      GUI ELEMENTS
  */

  // controls
  private JPanel controls;     // holds the following:
  private JPanel dataPanel;    // text box and label
  private JButton insertNode;
  private JButton deleteNode;
  private JButton traverseList;
  private JButton clearList;
  private JButton countNodes;

  private JTextField data;    // text box and label
  private JLabel  dataLabel;  // for dataPanel

  // output
  private JScrollPane   outputPane;  // holds the output text area
  private JTextArea     outputArea;  // the output text area
  private String outputDisplay = ""; // message to outputArea
  private JScrollPane   visualPane;  //  holds the Visualizer

  private boolean mouseDisabled = false; // prevent clicking while drawing

  private static int MAX_DATA_LENGTH = 4; // max chars allowed in the data field

  /*
       CONSTRUCTOR
  */

  /**
   *  instantiates a new ListControl with the given width and height.
   *  sets up the controls, initializes the Visualizer, makes everything
   *  visible.
   *
   **/
  public LinkedListPractice( int width, int height )
  {
    super( ); // call to Object( ) for completeness
    setTitle( " Linked List Visualizer" );

    // set up the list and visualizer
    list = new LinkList( );
    v = list.getVisualizer( );
    visualPane = new JScrollPane( v );

    // instantiate GUI list controls

    dataPanel = new JPanel( );
    dataPanel.setLayout( new GridLayout( 2,1 ) );

    data         = new JTextField( );
    dataLabel    = new JLabel( "Node Data:" );

    dataPanel.add( dataLabel );
    dataPanel.add( data );

    controls = new JPanel( );
    controls.setLayout( new GridLayout( 6, 1 ) );

    insertNode   = new JButton( "insert" );
    deleteNode   = new JButton( "delete" );
    traverseList = new JButton( "traverse" );
    countNodes   = new JButton( "count" );
    clearList    = new JButton( "clear" );

    // set up handlers for controls
    //   private inner classes seem more readable
    //   than anonymous inner classes or one large handler
    data.addKeyListener( new DataListener( ) );
    insertNode.addActionListener( new ActionInsert( ) );
    deleteNode.addActionListener( new ActionDelete( ) );
    traverseList.addActionListener( new ActionTraverse( ) );
    countNodes.addActionListener( new ActionCount( ) );
    clearList.addActionListener( new ActionClear( ) );

    // add controls to the control pane
    controls.add( dataPanel );
    controls.add( insertNode );
    controls.add( deleteNode );
    controls.add( traverseList );
    controls.add( countNodes );
    controls.add( clearList );

    // set up GUI output
    outputArea = new JTextArea( 5, 40 );
    outputPane = new JScrollPane( outputArea );
    outputArea.setEditable( false );

    // set up ListControl GUI
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    getContentPane( ).setLayout( new BorderLayout( ) );
    getContentPane( ).add( visualPane,BorderLayout.CENTER );
    getContentPane( ).add( controls,BorderLayout.WEST );
    getContentPane( ).add( outputPane,BorderLayout.SOUTH );

    setSize( width,height );
    setVisible( true );
    doLayout( );
    validate( );
  }  // end default constructor

  /*
       METHODS
  */

  /** turn off all the buttons so they can't be clicked
       during operations **/
  private void disableButtons( )
  {
    insertNode.setEnabled( false );
    deleteNode.setEnabled( false );
    traverseList.setEnabled( false );
    clearList.setEnabled( false );
    countNodes.setEnabled( false );
    mouseDisabled = true;
  }

  /** turn on the buttons **/
  private void enableButtons( )
  {
    insertNode.setEnabled( true );
    deleteNode.setEnabled( true );
    traverseList.setEnabled( true );
    clearList.setEnabled( true );
    countNodes.setEnabled( true );
    mouseDisabled = false;
  }

  /** give focus to the text field and select any text it holds **/
  private void selectData( )
  {
    data.select( 0, data.getText( ).length( ) );
    data.requestFocusInWindow( );
  }

  private int getData( ) throws NumberFormatException
  {
    return ( new Integer( data.getText( ) ) ).intValue( );
  }

  /*
      PRIVATE INNER CLASSES - action handlers
         each class is for a specific button,
         so the event source is not checked

         for any method that requires nodes
         to be highlighted, a new thread is created
         and started to handle calling the method.
         this allows the GUI to handle the highlighting
         and delay routines appropriately.
  */

  private class ActionInsert implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      //SwingUtilities.invokeLater( new InsertThread( ) );
      ( new InsertThread( ) ).start( );
    }

    private class InsertThread extends Thread
    {
      public void run( )
      {
        disableButtons( );
        try
        {
          int i = getData( );
          outputDisplay += "insert called with " + i + " as data\n";
          outputArea.setText( outputDisplay );
          list.insert(i);
        }
        catch ( Exception e )
        {
          outputDisplay += "invalid number\n";
          outputArea.setText( outputDisplay );
        }
        enableButtons( );
        selectData( );
      }
    }
  }  // end ActionInsert

  private class ActionDelete implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      ( new DeleteThread( ) ).start( );
    }

    private class DeleteThread extends Thread
    {
      public void run( )
      {
        disableButtons( );
        outputDisplay += ( "delete called with " + data.getText( ) + ": " );
        outputArea.setText( outputDisplay );
        try
        {
          boolean deleted = list.delete( new Integer( data.getText( ) ).intValue( ) );
          outputDisplay += "node " + ( (deleted)?"deleted\n":"not found\n" );
          outputArea.setText( outputDisplay );
        }
        catch( NumberFormatException nfe )
        {
          outputDisplay += ( "data not an integer\n" );
          outputArea.setText( outputDisplay );
        }
        enableButtons( );
        selectData( );
      }
    }
  }

  private class ActionTraverse implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      outputDisplay += "traversing the list: ";
      outputArea.setText( outputDisplay );
      ( new Thread( ){
            public void run( ){
               disableButtons( );
               list.traverse( );
               outputDisplay += ( list.getTraversal( ) + "\n" );
               outputArea.setText( outputDisplay );
               enableButtons( );
               selectData( );
            }
         }).start( );
      }
   } // end ActionTraverse

  private class ActionCount implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      ( new CountThread( ) ).start( );
    }

    private class CountThread extends Thread
    {
      public void run( )
      {
        disableButtons( );
        outputDisplay += "count called: ";
        outputArea.setText( outputDisplay );
        int n = list.count( );
        outputDisplay += ( "number of nodes = " + n + "\n" );
        outputArea.setText( outputDisplay );
        enableButtons( );
        selectData( );
      }
    }
  }  // end ActionCount

  private class ActionClear implements ActionListener
  {
    public void actionPerformed( ActionEvent e )
    {
      ( new ClearThread( ) ).start( );
    }

    private class ClearThread extends Thread
    {
      public void run( )
      {
        disableButtons( );
        outputDisplay += "clear list called\n";
        outputArea.setText( outputDisplay );
        list.clear( );
        enableButtons( );
        selectData( );
      }
    }
  }  // end ActionClear


  /** detects when key is entered in to data text field, and ensures
       that the field is limited to MAX_DATA_LENGTH. **/
  private class DataListener extends KeyAdapter
  {
    int key = 0;  // which key was pressed
    //keyPressed will detect back space, but doesn't properly consume
    // the event, so the field still gets it.  so, trap the key here
    // and assign it to variable key.  thankfully by its very nature
    // keyPressed happens before keyTyped.
    public void keyPressed( KeyEvent e )
    {
      key = e.getKeyCode( );
    }

    // keyTyped will properly consume the event, but can't detect when
    // backspace was pressed. it also lets arrow keys, home, etc. through.
    // key has been trapped by keyPressed, so it can be properly compared
    // to backspace, and consumed if it is not back space and field is
    // over 15 chars.
    public void keyTyped( KeyEvent e )
    {
      // must compare to MAX_DATA_LENGTH-1 because the key currently being examined
      // will be the last character in the field.

      if( key == KeyEvent.VK_BACK_SPACE )
      {
        // never consume backspace
        return;
      }
      if( data.getText( ).length( ) > MAX_DATA_LENGTH-1
            || key < KeyEvent.VK_0 || key > KeyEvent.VK_9 )
      {
        // if field is full, or invalid value entered, consume the key
        e.consume( );
        return;
      }
    }
  }

  public static void main( String args[] )
  {
    LinkedListPractice app = new LinkedListPractice( 600, 400 );
  }
}