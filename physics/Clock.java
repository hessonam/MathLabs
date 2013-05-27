package physics;

import javax.swing.*;
import java.awt.*;

class Clock extends JPanel{
	
	final static int radius = 125;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.ORANGE);
		g.fillOval(50,50,2*radius,2*radius);
		g.setColor(Color.BLACK);
		g.fillOval(165,165,20,20);
		g.fillRect(173, 50, 4, 20);
		g.fillRect(280, 173, 20, 4);
		g.fillRect(173,280,4,20);
		g.fillRect(50, 173, 20, 4);
	}
}
