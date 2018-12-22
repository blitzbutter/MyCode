/* Wrote by Jacob Bryant */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.lang.*;
import javax.swing.BoxLayout;
import java.text.*;

class SmartSpart extends JFrame
{
		private JPanel jp;
		private JPanel fp;
		private JPanel rp;

		private JLabel l1;
		private JLabel save;

		private JTextField j1;
		private JTextField j2;
		private JTextField j3;
		private JTextField j4;
		private JTextField j5;

		private JTextField custom_t;
		private JButton custom;
		private JButton clear;

		private JLabel z1;
		private JTextField z2;
		private JLabel z3;
		private JTextField z4;
		private JLabel z5;
		private JTextField z6;
		private JLabel z7;
		private JTextField z8;
		private JLabel z9;
		private JTextField z10;

		private JLabel r1;

		private JButton enter;
		private JButton routine;
		private JButton read;
		private JTextField file_r;

		private ButtonHandler blh;
		private final Font fontBold = new Font(Font.DIALOG, Font.BOLD, 14);
		private final Font fontPlain = new Font(Font.DIALOG, Font.PLAIN, 14);

		private Calendar date;
		private GridLayout reps;

		private boolean c;
		private String file_n;

    public SmartSpart()
    {
			super("SmartSpart V.1");
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			setFonts();
			setTitle("Smart Spart V.1");
			setSize(400,400);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true);
			setBackground(Color.white);
			setLayout(new BorderLayout());
			setContentPane(new JLabel(new ImageIcon("Spartan.jpg")));
			setLayout(new FlowLayout());

			reps = new GridLayout(0,2);
			rp = new JPanel();
			rp.setBackground(Color.white);
			rp.setLayout(reps);

			jp = new JPanel();
			jp.setBackground(Color.white);
			jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

			fp = new JPanel();
			fp.setBackground(Color.white);
			fp.setLayout(new BoxLayout(fp, BoxLayout.Y_AXIS));

			custom = new JButton("Name file");
			custom_t = new JTextField();

			j1 = new JTextField();
			j2 = new JTextField();
			j3 = new JTextField();
			j4 = new JTextField();
			j5 = new JTextField();

			z1 = new JLabel("");
			z2 = new JTextField();
			z3 = new JLabel("");
			z4 = new JTextField();
			z5 = new JLabel("");
			z6 = new JTextField();
			z7 = new JLabel("");
			z8 = new JTextField();
			z9 = new JLabel("");
			z10 = new JTextField();

			l1 = new JLabel("Routines:");


			file_r = new JTextField();
			setSpecificSize(file_r, new Dimension(150,20));

			enter = new JButton("Enter");
			routine = new JButton("Routines"); // Will get the date and make a new file based off that date
			read = new JButton("Read File");
			clear = new JButton("Clear");
			blh = new ButtonHandler();

			enter.setBackground(Color.red.darker());
			enter.setForeground(Color.white);

			routine.setBackground(Color.red.darker());
			routine.setForeground(Color.white);

			read.setBackground(Color.red.darker());
			read.setForeground(Color.white);

			custom.setBackground(Color.red.darker());
			custom.setForeground(Color.white);

			clear.setBackground(Color.red.darker());
			clear.setForeground(Color.white);

			enter.addActionListener(blh);
			routine.addActionListener(blh);
			read.addActionListener(blh);
			custom.addActionListener(blh);
			clear.addActionListener(blh);

			l1.setFont(new Font(l1.getName(), Font.BOLD, 15));

			setSpecificSize(l1, new Dimension(100,40));
			jp.add(l1);
			setSpecificSize(j1, new Dimension(200,25));
			jp.add(j1);
			setSpecificSize(j2, new Dimension(200,25));
			jp.add(j2);
			setSpecificSize(j3, new Dimension(200,25));
			jp.add(j3);
			setSpecificSize(j4, new Dimension(200,25));
			jp.add(j4);
			setSpecificSize(j5, new Dimension(200,25));
			jp.add(j5);
			jp.add(Box.createRigidArea(new Dimension(0,25)));
			jp.add(custom_t);
			jp.add(custom);
			jp.add(Box.createRigidArea(new Dimension(0,15)));
			jp.add(enter);
			jp.add(Box.createRigidArea(new Dimension(0,15)));
			setSpecificSize(clear, new Dimension(100,30));
			jp.add(clear);

			setSpecificSize(z1, new Dimension(30,40));
			rp.add(z1);
			setSpecificSize(z2, new Dimension(30,40));
			rp.add(z2);
			setSpecificSize(z3, new Dimension(30,40));
			rp.add(z3);
			setSpecificSize(z4, new Dimension(30,40));
			rp.add(z4);
			setSpecificSize(z5, new Dimension(30,40));
			rp.add(z5);
			setSpecificSize(z6, new Dimension(30,40));
			rp.add(z6);
			setSpecificSize(z7, new Dimension(30,40));
			rp.add(z7);
			setSpecificSize(z8, new Dimension(30,40));
			rp.add(z8);
			setSpecificSize(z9, new Dimension(30,40));
			rp.add(z9);
			setSpecificSize(z10, new Dimension(30,40));
			rp.add(z10);

			// Lp stuff

			save = new JLabel("Save your routine:");
			save.setFont(new Font(save.getName(), Font.BOLD, 15));
			setSpecificSize(routine, new Dimension(100,16));
			fp.add(save);
			fp.add(routine);
			fp.add(file_r);
			fp.add(read);

			// Final stuff
			add(rp, BorderLayout.NORTH);
			add(Box.createRigidArea(new Dimension(600,300)));
			add(jp, BorderLayout.EAST);
			add(fp, BorderLayout.WEST);
			add(Box.createRigidArea(new Dimension(600,-100)));

			z1.setVisible(false);
			z2.setVisible(false);
			z3.setVisible(false);
			z4.setVisible(false);
			z5.setVisible(false);
			z6.setVisible(false);
			z7.setVisible(false);
			z8.setVisible(false);
			z9.setVisible(false);
			z10.setVisible(false);

			save.setVisible(false);
			routine.setVisible(false);

			setSize(899,419);
			setSize(900,420);
    }

    private class ButtonHandler implements ActionListener					// Sets up a private class ButtonHandler that implements the ActionListener interface
		{
			public void actionPerformed(ActionEvent e)	// Overrides the actionPerformed() method
			{
				Object source = e.getSource();			// Initializes and sets the Object variable source to the source of the triggered event

				if (source == enter)				// If button clicked is read
				{
					if(j1.getText().length()>2)
						z1.setText(j1.getText().substring(0,3));
					if(j2.getText().length()>2)
						z3.setText(j2.getText().substring(0,3));
					if(j3.getText().length()>2)
						z5.setText(j3.getText().substring(0,3));
					if(j4.getText().length()>2)
						z7.setText(j4.getText().substring(0,3));
					if(j5.getText().length()>2)
						z9.setText(j5.getText().substring(0,3));

					z1.setVisible(true);
					z2.setVisible(true);
					z3.setVisible(true);
					z4.setVisible(true);
					z5.setVisible(true);
					z6.setVisible(true);
					z7.setVisible(true);
					z8.setVisible(true);
					z9.setVisible(true);
					z10.setVisible(true);
					routine.setVisible(true);
					save.setVisible(true);

					clear.setVisible(false);
					custom.setVisible(false);
					custom_t.setVisible(false);
					file_r.setVisible(false);
					read.setVisible(false);
					j1.setVisible(false);
					j2.setVisible(false);
					j3.setVisible(false);
					j4.setVisible(false);
					j5.setVisible(false);
					enter.setVisible(false);
					l1.setVisible(false);

					return;							// Returns out of the function
				}
				if (source == routine)
				{
					//l1.setVisible(true);
					z1.setVisible(false);
					z2.setVisible(false);
					z3.setVisible(false);
					z4.setVisible(false);
					z5.setVisible(false);
					z6.setVisible(false);
					z7.setVisible(false);
					z8.setVisible(false);
					z9.setVisible(false);
					z10.setVisible(false);
					routine.setVisible(false);
					save.setVisible(false);

					clear.setVisible(true);
					custom.setVisible(true);
					custom_t.setVisible(true);
					file_r.setVisible(true);
					read.setVisible(true);
					j1.setVisible(true);
					j2.setVisible(true);
					j3.setVisible(true);
					j4.setVisible(true);
					j5.setVisible(true);
					enter.setVisible(true);
					l1.setVisible(true);
					writeFile();
					return;
				}

				if (source == read)
				{
					readFile();
					return;
				}

				if (source == custom)
				{
					c = true;
					file_n = custom_t.getText();
				}

				if (source == clear)
				{
					j1.setText("");
					j2.setText("");
					j3.setText("");
					j4.setText("");
					j5.setText("");
				}
			}
	}

	private void writeFile()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String data = dateFormat.format(date);
		Random seed = new Random();
		int rand = seed.nextInt(512351)+1;
		String file_name;
		if(!c)
			file_name = "Workout_" + data + "_" + rand + ".doc";
		else
			file_name = file_n + ".doc";
		try(PrintWriter pw = new PrintWriter(new File(file_name)))	// Initializes a print writer pw to write to the file output.txt
		{
			pw.write(data);
			pw.println();
			pw.write(j1.getText() + " " + z2.getText());
			pw.println();
			pw.write(j2.getText() + " " + z4.getText());
			pw.println();
			pw.write(j3.getText() + " " + z6.getText());
			pw.println();
			pw.write(j4.getText() + " " + z8.getText());
			pw.println();
			pw.write(j5.getText() + " " + z10.getText());
			pw.println();
		}
		catch(FileNotFoundException fnf)	// File-not-found exception
		{
			System.out.println("Unable to find " + file_name);	// Prints an error message to console
		}

		if(!c)
			file_name = "Workout_" + data + "_" + rand + ".txt";
		else
			file_name = file_n + ".txt";

		try(PrintWriter pw = new PrintWriter(new File(file_name)))	// Initializes a print writer pw to write to the file output.txt
		{
			pw.write(data);
			pw.println();
			pw.write(j1.getText() + " " + z2.getText());
			pw.println();
			pw.write(j2.getText() + " " + z4.getText());
			pw.println();
			pw.write(j3.getText() + " " + z6.getText());
			pw.println();
			pw.write(j4.getText() + " " + z8.getText());
			pw.println();
			pw.write(j5.getText() + " " + z10.getText());
			pw.println();
		}
		catch(FileNotFoundException fnf)	// File-not-found exception
		{
			System.out.println("Unable to find " + file_name);	// Prints an error message to console
		}
	}

	private void readFile()
	{
		String word = " ";
		int first=0;
		// Thrown call to open and read file input.txt
		try(Scanner scannerFile = new Scanner(new File(file_r.getText())))	// Initializes a scanner scannerFile to read from the file input.txt
		{
			// System.out.println(file_r);
			while(scannerFile.hasNext())				// While the file has a new line
			{
				String line = scannerFile.nextLine();
				if(first==0)
				{
					first=1;
					continue;
				}
				if(first==1)
				{
					word = readAct(line);
					j1.setText(word);
				}
				if(first==2)
				{
					word = readAct(line);
					j2.setText(word);
				}
				if(first==3)
				{
					word = readAct(line);
					j3.setText(word);
				}
				if(first==4)
				{
					word = readAct(line);
					j4.setText(word);
				}
				if(first==5)
				{
					word = readAct(line);
					j5.setText(word);
				}
				first+=1;
			}
		}
		catch(FileNotFoundException fnf)				// File-not-found exception
		{
			System.out.println("Unable to find " + file_r);	// Prints an error message to console
		}
	}// Change input.text

	private String readAct(String l)
	{
		int i = 0;
		String temp="";
		while(l.charAt(i)!=' ')
		{
			temp+=l.charAt(i);
			i+=1;
		}
		return temp;
	}

	private void setFonts()
	{
		UIManager.put("Button.font", fontBold);
		UIManager.put("ComboBox.font", fontBold);
		UIManager.put("Label.font", fontBold);
		UIManager.put("List.font", fontPlain);
	}

	private void setSpecificSize(JComponent component, Dimension dimension)
	{
		component.setMinimumSize(dimension);
		component.setPreferredSize(dimension);
		component.setMaximumSize(dimension);
	}

	public static void main(String args[])
	{
		SmartSpart gui = new SmartSpart();
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}