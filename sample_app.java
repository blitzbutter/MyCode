/* Jacob Bryant */
/* Reverse file project */
/* Programming II */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.lang.*;

public class ReverseFileViaStack extends JFrame
{
	// Everything in your class (see below).

	// Fonts:
	private final Font fontBold = new Font(Font.DIALOG, Font.BOLD, 14);
	private final Font fontPlain = new Font(Font.DIALOG, Font.PLAIN, 14);

	// Lists:
	private JList<String> list1;
	private JList<String> list2;

	// List models:
	private DefaultListModel<String> listModel1 = new DefaultListModel<>();
	private DefaultListModel<String> listModel2 = new DefaultListModel<>();

	// Stack (from the Java Collections Framework):
	private Stack<String> stack = new Stack<>();

	// Sets up GUI
	private Container contents;
    private BorderLayout bl;

	// Declares JLabel variables
	private JLabel title;
	private JLabel label1;
	private JLabel label2;

	// Declares JPanel variables
	private JPanel jp;
	private JPanel lp;
	private JPanel bp;

	// Declares Button variables
	private JButton read;
	private JButton reverse;
	private JButton write;

	// Declares JScrollPane variables
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;

	// Declares a button handler
	private ButtonHandler blh;

	// Constructor.
	public ReverseFileViaStack()
	{
	  super("Reverse Text File via Stack");	// Set window title
	  setFonts();							// Sets the fonts for the text


	  // List initialization
	  listModel1 = new DefaultListModel();	// Sets a list model to handle list1
	  list1 = new JList<>(listModel1);		// Sets a list with listModel1 as its argument

	  listModel2 = new DefaultListModel();	// Sets a list model to handle list2
	  list2 = new JList<>(listModel2);		// Sets a list with listModel2 as its argument

	  scrollPane1 = new JScrollPane(list1);	// Initializes scrollPane1 with list1 as a handle
	  scrollPane2 = new JScrollPane(list2); // Initializes scrollPane2 with list2 as a handle

      scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	// Sets the vertial scroll bar for scrollPane1 to always-on
      scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	// Sets the vertial scroll bar for scrollPane2 to always-on

      scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	// Sets the horizontal scroll bar for scrollPane2 to always-off
      scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Sets the hotizontal scroll bar for scrollPane2 to always-off

	  setSpecificSize(scrollPane1, new Dimension(200,235));	// Sets the dimensions of scrollPane1
	  setSpecificSize(scrollPane2, new Dimension(200,235));	// Sets the dimensions of scrollPane2

      // Content pane
	  contents = getContentPane();				// Sets the contents variable as the handle for content pane
	  contents.setLayout(new BorderLayout());	// Sets the layout of the content pane as a border layout

	  // Initializes the JPanels
	  jp = new JPanel();		// jp stands for jpanel, as it is a general panel that lays out both labels and a title
	  lp = new JPanel();		// lp stands for list panel, as it lays out the list components
	  bp = new JPanel();		// bp stands for button panels, as it lays out the button components

	  // Sets the respective layouts for each JPanel
	  jp.setLayout(new BorderLayout());
      lp.setLayout(new FlowLayout());
	  bp.setLayout(new FlowLayout());

	  // Labels
	  label1 = new JLabel("            Original order: ");				// Sets the label for list1
	  setSpecificSize(label1, new Dimension(300,16));					// Sets the dimensions of label1
	  label1.setFont(new Font(label1.getName(), Font.BOLD, 12));		// Sets the font of label1

	  label2 = new JLabel("                     Reversed order: ");	    // Sets the label for list 2
	  setSpecificSize(label2, new Dimension(300,16));					// Sets the dimensions of label2
	  label2.setFont(new Font(label2.getName(), Font.BOLD, 12));		// Sets the font of label2

	  // Initializes the title
      title = new JLabel("Reverse a Text File via a Stack", SwingConstants.CENTER);	// Initializes the title

	  // Initializes the buttons
	  read = new JButton("Read");
	  reverse = new JButton("Reverse");
	  write = new JButton("Write");

	  // Enables and disables each respective button
	  read.setEnabled(true);
	  reverse.setEnabled(false);
	  write.setEnabled(false);

	  // Initializes the buttonn handler
	  blh = new ButtonHandler();

	  // Attaches the button handler as the action listener for read, reverse, and write buttons
	  read.addActionListener(blh);
	  reverse.addActionListener(blh);
	  write.addActionListener(blh);

	  // Adds the three panels to the content pane
	  contents.add(jp, BorderLayout.NORTH);
	  contents.add(lp, BorderLayout.CENTER);
	  contents.add(bp, BorderLayout.SOUTH);

	  // Adds the components for the jp panel
	  jp.add(title, BorderLayout.NORTH);	 				// Adds the title
	  jp.add(Box.createRigidArea(new Dimension(0,10)));		// Gives it a margin
	  jp.add(label1, BorderLayout.WEST);					// Adds label1
	  jp.add(label2, BorderLayout.EAST);					// Adds label2

	  // Adds the components for the jp panel
	  lp.add(scrollPane1, BorderLayout.CENTER);				// Adds scrollPane1
	  lp.add(Box.createRigidArea(new Dimension(60,0)));		// Sets some distance between the scroll panes
	  lp.add(scrollPane2, BorderLayout.CENTER);				// Adds scrollPane2

	  // Adds the components for the jp panel
	  bp.add(read, BorderLayout.SOUTH);						// Adds the read button
	  bp.add(Box.createRigidArea(new Dimension(50,0)));		// Sets some distance between the read and reverse buttons
	  bp.add(reverse, BorderLayout.SOUTH);					// Adds the reverse button
 	  bp.add(Box.createRigidArea(new Dimension(50,0)));		// Sets some distance between the reverse and write buttons
	  bp.add(write, BorderLayout.SOUTH);					// Adds the write button

	  // Sets the dimensions of the windows and makes it visible
	  setSize(550, 350);
	  setVisible(true);
	}

	// Button handler.
	// Calls the appropriate function based on which button was clicked.
	private class ButtonHandler implements ActionListener					// Sets up a private class ButtonHandler that implements the ActionListener interface
	{
		public void actionPerformed(ActionEvent e)	// Overrides the actionPerformed() method
		{
		  Object source = e.getSource();			// Initializes and sets the Object variable source to the source of the triggered event

		  if (source == read)				// If button clicked is read
		  {
			read.setEnabled(false);			// Disables the read button
			reverse.setEnabled(true);		// Enables the reverse button
			readFile();						// Calls the readFile() method
			return;							// Returns out of the function
		  }
		  if (source == reverse)			// If button clicked is reverse
		  {
			reverse.setEnabled(false);		// Disables the reverse button
			write.setEnabled(true);			// Enables the write button
			reverseFile();					// Calls the reverseFile() method
			return;							// Returns out of the function
		  }
		  if (source == write)				// If button clicked is write
		  {
			write.setEnabled(false);		// Disables the write button
			writeFile();					// Calls the writeFile() method
			return;							// Returns out of the function
		  }

		}
	}

	// Reads the input text file line-by-line.
	// Adds each line it gets to the left list box.
	// To add each item (line) to the left list box, use its list model
	// addElement function.
	private void readFile()
	{
		// Thrown call to open and read file input.txt
		try(Scanner scannerFile = new Scanner(new File("input.txt")))	// Initializes a scanner scannerFile to read from the file input.txt
		{
			while(scannerFile.hasNext())				// While the file has a new line
			{
				String line = scannerFile.nextLine();	// Sets the String line as a handle to that line
				listModel1.addElement(line);			// Adds the line to the list1 through the addElement() methods of list model
			}
		}
		catch(FileNotFoundException fnf)				// File-not-found exception
		{
			System.out.println("Unable to find input.txt");	// Prints an error message to console
		}
	}

	// Pushes each item from the left list onto the stack.
	// This uses the stack's push function.
	// To read each item in the left list box, use its list model
	// elementAt function (with a loop index).
	// Then pops the items from the stack one-by-one and adds each of them
	// to the right list.
	// This uses the stack's pop function.
	// To add each item (line) to the right list box, use its list model
	// addElement function.
	// This results in the right list being in reverse order of the left.
	private void reverseFile()
	{
		Deque<String> stack = new ArrayDeque<String>();	// Declares and initializes a new stack data structure that contains String objects

		// Push
		for(int i=0; i < listModel1.getSize(); i++)	// Iterates through the first list
		{
			stack.push(listModel1.elementAt(i));	// Pushes each element onto the stack
		}

		// Pop
		for(int i=0; i < listModel1.getSize(); i++)	// Iterates through the first list
		{
			listModel2.addElement(stack.pop());		// Pops each element off of the stack
		}
	}

	// Reads through the right list item-by-item.
	// Uses a PrintWriter to write these items to the output file.
	// Be sure to close the PrintWriter.
	private void writeFile()
	{
		// Thrown call to create, open, write file input.txt
		try(PrintWriter pw = new PrintWriter(new File("output.txt")))	// Initializes a print writer pw to write to the file output.txt
		{
			for(int i=0;i<listModel2.getSize();i++)						// iterates through the second list
			{
                     pw.write(listModel2.elementAt(i));					// Writes each element from the second list to output.txt
                     pw.println();										// Prints a new line after each write
			}
		}
		catch(FileNotFoundException fnf)	// File-not-found exception
		{
			System.out.println("Unable to find output.txt");	// Prints an error message to console
		}
	}

	// See the video.
	private void setFonts()
	{
	  UIManager.put("Button.font", fontBold);
	  UIManager.put("ComboBox.font", fontBold);
	  UIManager.put("Label.font", fontBold);
	  UIManager.put("List.font", fontPlain);
	}

	// See the video.
	private void setSpecificSize(JComponent component, Dimension dimension)
	{
	  component.setMinimumSize(dimension);
	  component.setPreferredSize(dimension);
	  component.setMaximumSize(dimension);
	}

	// The main function.
	public static void main(String[] args)
	{
	  ReverseFileViaStack gui = new ReverseFileViaStack();	// Declares and initializes a new ReverseFileViaStack object
	  gui.setResizable(false);								// Sets the resizable property to false so the user does not expand and contort the layout
	  gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Sets the the close operation for the object
	}
}
