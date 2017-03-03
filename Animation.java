// Graphics program

/* By Jacob Bryant */

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ShellApplet extends JApplet
{
	Graphics g;
	Random rnd = new Random();

	int z = 0;
	int h = 0;

	public void paint(Graphics g)
	{
		super.paint(g);
		Color foA = new Color(0,70,50);
		g.setColor(foA);
		g.setFont(new Font("Verdana", Font.ITALIC, 30));
		g.drawString("~~~~~~~~~~~~", 70, 100-20);
		g.drawString("~~~~~~~~~~~~", 70, 100-30);

		Color foB = new Color(0,20,150);
		g.setColor(foB);

		g.drawString("The Little Robot",100,100);

		Color foC = new Color(0,70,50);
		g.setColor(foC);
		g.drawString("~~~~~~~~~~~~", 70, 100+20);
		g.drawString("~~~~~~~~~~~~", 70, 100+30);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 11));
		animate(500);

		int coor = rnd.nextInt(100);
		int bub=240;

		for(int i=240;i>0;i--)
		{
			Color foD = new Color(0,70,0+i);
			g.setColor(foD);
			g.drawLine(100,240,30+i,14+i);
			g.drawLine(-100,-240,30+i,14+i);
			foD = new Color(0,70,0+i);
			g.setColor(foD);
			g.drawLine(10000,0+i,100+i,14+i);
			g.drawLine(-10000,0+i,100+i,14+i);
			if(i<148)
			{
				while(bub>0)
				{
				coor = rnd.nextInt(100);
				Color foE = new Color(0,100,0+bub);
				g.setColor(foE);
				g.fillOval(340-bub,coor+bub-100,13,13);
				animate(170);
				bub-=1;
				break;
				}
			}
			animate(170);
		}

		for(int i=240;i>0;i--)
		{
			coor = rnd.nextInt(100);
			Color foE = new Color(0,100,0+i);
			g.setColor(foE);
			g.fillOval(340-i+coor,coor+i-100,20,20);
			animate(170);
		}

		g.setColor(Color.BLACK);
		animate(2000);

		int sX = 150;
		int sY = 0;
		int i = 0;
		int j = 0;

		setfigures(g);

		try {
						Thread.sleep(1000);
				} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
		}

		h=1;

		while(h>0)
		{
			if(sX+i==300)
			{
				z=1;
				setfigures(g);
				int y = rnd.nextInt(55);
				Color lash = new Color(y+200,y+20,0);
				g.setColor(lash);
				g.fillOval(sX+209+y, sY+180, 70+y, 70+y);
				g.fillOval(sX+209+y, sY+190, 70+y, 70+y);
				g.fillOval(sX+209+y, sY+200, 70+y, 70+y);
				g.fillOval(sX+150+y, sY+180, 70+y, 70+y);
				g.fillOval(sX+150+y, sY+190, 70+y, 70+y);
				g.fillOval(sX+150+y, sY+200, 70+y, 70+y);

				g.fillOval(sX+209, sY+180-y, 70+y, 70+y);
				g.fillOval(sX+209, sY+190-y, 70+y, 70+y);
				g.fillOval(sX+209, sY+200-y, 70+y, 70+y);
				g.fillOval(sX+150, sY+180-y, 70+y, 70+y);
				g.fillOval(sX+150, sY+190-y, 70+y, 70+y);
				g.fillOval(sX+150, sY+200-y, 70+y, 70+y);

				g.fillOval(sX+209-y, sY+180-y, 70+y, 70+y);
				g.fillOval(sX+209-y, sY+190-y, 70+y, 70+y);
				g.fillOval(sX+209-y, sY+200-y, 70+y, 70+y);
				g.fillOval(sX+150-y, sY+180-y, 70+y, 70+y);
				g.fillOval(sX+150-y, sY+190-y, 70+y, 70+y);
				g.fillOval(sX+150-y, sY+200-y, 70+y, 70+y);
				g.drawString("Yikes!!!",y+5+j,y+5+j);
				animate(200);
				j++;
			}
			else
			{
				int e = rnd.nextInt(55);
				Color mash = new Color(e+200,e+20,0);
				g.setColor(mash);
				e = rnd.nextInt(55);
				g.fillOval(sX+i, sY+i+e, 50+e, 50+e);
				button(g);
				g.drawString("________", sX+i+50, sY+273-40);
				g.drawString("________", sX+i+50, sY+270-40);
				g.drawString("________", sX+i+50, sY+260-40);
				g.drawString("________", sX+i+50, sY+257-40);

				g.drawString("___________________________", sX+399-i, sY+273-40);
				g.drawString("___________________________", sX+399-i, sY+270-40);
				g.drawString("___________________________", sX+399-i, sY+260-40);
				g.drawString("___________________________", sX+399-i+50, sY+257-40);


				animate(200);

				i++;
			}
		}
	}

	public void animate(int w)
	{
		try {
				Thread.sleep(w-155);
		} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
		}
	}
	public void button(Graphics g)
	{
			int sX = 150;
			int sY = 0;
			int loop = 0;
			int z = rnd.nextInt(55);
			Color flash = new Color(z,z,z);
			g.setColor(flash);
			g.fillOval(sX+209, sY+180, 10, 10);
			g.fillOval(sX+209, sY+190, 10, 10);
			g.fillOval(sX+209, sY+200, 10, 10);

			int y = rnd.nextInt(55);
			Color lash = new Color(y+200,y+20,0);
			g.setColor(lash);
			g.fillOval(sX+209, sY+180, 10, 10);
			g.fillOval(sX+209, sY+190, 10, 10);
			g.fillOval(sX+209, sY+200, 10, 10);
	}
	public void setfigures(Graphics g)
	{
			int sX = 0;
			int sY = 0;
			boolean yes=true;
			g.clearRect(sX, sY, 500, 500);

			if(z==0)
			{
				try {
						final BufferedImage image = ImageIO.read(new File("background.jpg"));
						g.drawImage(image, 0, 0, null);
					} catch(Exception ex) {
						ex.printStackTrace();
				}
			}
			else
				super.paint(g);
			Random rnd = new Random();
			rnd.setSeed(50);

			sX+=150;

			g.fillOval(sX+200, sY+150, 20, 20);
			g.drawOval(sX+200, sY+150, 30, 20);

			g.fillOval(sX+180, sY+150, 20, 20);
			g.drawOval(sX+180, sY+150, 30, 20);

			g.draw3DRect(sX+180, sY+150, 20, 20, yes);
			g.draw3DRect(sX+190, sY+150, 30, 20, yes);

			g.drawRect(sX+200, sY+160,25,20);

			g.setColor(Color.BLUE);
			g.fillOval(sX+194, sY+175,40,40);

			g.setColor(Color.RED);
			g.fillOval(sX+194, sY+175,10,10);
			g.setColor(Color.BLUE);

			if(z==0)
				g.drawString("What is that in the sky??? :o", sX+25+50, sY+270-40);


			Color legs = new Color(0,0,54);
			g.setColor(legs);

			Polygon leg_l = new Polygon();
			leg_l.addPoint(sX+75+125, sY+110);	// 40
			leg_l.addPoint(sX+25+125, sY+150); // 100
			leg_l.addPoint(sX+125+125, sY+150); // 25
			g.fillPolygon(leg_l);
	}
}
