package physics.sim;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class SierpinskiDesign extends JPanel implements Runnable
{
	public static boolean shouldRun = true;
	int random;
	int px, py;
	int fps = 250;
	Vector <Integer> x = new Vector<Integer>();
	Vector <Integer> y = new Vector<Integer>();
	
	public SierpinskiDesign(int px,int py)
	{
		this.px = px;
		this.py = py;
		Thread time = new Thread (this);
        time.start ();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (int i = 0; i < y.size(); i ++)
		{
			g.setColor(Color.WHITE);
			g.fillOval(x.get(i)-1,y.get(i)-1,2,2);
		}
	}
	
	public void addPoint(int x, int y)
	{
		this.x.add(x);
		this.y.add(y);
	}
	
	public void run()
	{
		while (shouldRun)
		{
			random = (int)(Math.random()*Sierpinski.n);
		
			addPoint((int)Math.round(next(px,Sierpinski.x[random])),(int)Math.round(next(py,Sierpinski.y[random])));
			int oldPx = px;
			px = (int)Math.round(next(px,Sierpinski.x[random]));
			py = (int)Math.round(next(py,Sierpinski.y[random]));
			//System.out.println("px: "+px + "    x[random]: "+Sierpinski.x[random]+"   py: " + py+ "    y[random]: "+Sierpinski.y[random]);
			
			try {
				Thread.sleep (fps);
			} catch (InterruptedException e) {

			}
        this.repaint ();
		}
	}
	
	public void terminateThread()
    {
    	shouldRun = false;
    }
	
	public double distance (int x1,int y1,int x2,int y2)
    {
    	return Math.sqrt(Math.pow(x2 - x1,2)+Math.pow(y2 - y1,2));
    }
	
	public static double next(int num1, int num2)
	{
		return (double)((num2-num1)*Sierpinski.r)+num1;
	}
	
	public static double quadratic(double a, double b, double c)
	{
		return Math.max((Math.sqrt(b*b - (4*a*c))-b)/(2*a),(-Math.sqrt(b*b - (4*a*c))-b)/(2*a) );
	}
}
