package physics.sim;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

class SierpinskiTriangle extends JPanel
{
	Vector <Integer> x = new Vector<Integer>();
	Vector <Integer> y = new Vector<Integer>();
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
			for (int i = 0; i < y.size(); i ++)
			{
				g.fillOval(x.get(i)-1,y.get(i)-1,2,2);
			}
	}
	
	public void drawVertex(int x, int y)
	{	
		this.x.add(x);
		this.y.add(y);
	}
}
