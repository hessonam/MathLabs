package physics;

import java.awt.*;
import javax.swing.*;

class ClockHand extends JPanel implements Runnable{

	double xs = 175, ys = 100;
	double factor = 15.5;
	double timer = -15*factor;
	final int radius = Clock.radius;
	final Color DARK_ORANGE = new Color(255,110,0);
	int lorentzFactor = 50;
	boolean runThread = true;
	
	public ClockHand()
	{
		runThread = true;
		Thread time = new Thread (this);
        time.start ();
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		g.setColor(DARK_ORANGE);
		g.drawLine(175,175,(int)Math.round(xs),(int)Math.round(ys));
		g.fillOval(260,320,lorentzFactor,50);
	}
	
	public void run()
	{
		Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);
        while (runThread == true)
        {       
        	timer++;
        	ys = 175 + (radius - 50)*Math.sin(Math.toRadians(timer*6)/factor);
        	xs = 175 + (radius - 50)*Math.cos(Math.toRadians(timer*6)/factor);
        	SpecialRelativity.changer();
           
                try {
					Thread.sleep (50);
				} catch (InterruptedException e) {

				}
            this.repaint ();
        }
	}

	public void setFactor(double factor)
	{
		this.factor = factor;
		timer = -15 * factor;
	}
	
	public void setLorentz (double factor)
	{
		lorentzFactor = (int) Math.round(50 / factor);
	}
	
	public String getTime ()
	{
		return Double.toString((timer + (15* factor))/factor);
	}
	
	public void terminateThread()
    {
    	runThread = false;
    }
}
