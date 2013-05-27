package physics.sim;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;

class DimensionDraw extends JPanel implements Runnable
{
    int x_ball = 300, y_ball = 300;
    int x_speed = 1, y_speed = 1, z_speed = 1, ThreeDMultiplyer;
    int x_direction = 1, y_direction = 1;
    int diameter = 41;
    int ThreeDPositioner;
    int milliseconds = 0;
    boolean maxDiameter = false;
    boolean ThreeDMultiplyer_isInfinity = false;
    boolean shouldRun = true;
    final Color GRAY = new Color (192, 192, 192);
    final Runnable beep = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

    public DimensionDraw()
    {
        Thread time = new Thread (this);
        time.start ();
    }


    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        g.setColor (Color.BLACK);
        g.drawRect (100, 50, 500, 450);
        g.setColor (GRAY);
        g.drawLine (250, 500, 600, 500);
        g.drawLine (600, 200, 600, 500);
        g.drawLine (600, 500, 750, 650);
        g.setColor (Color.BLACK);
        
        g.drawRect (250, 200, 500, 450);
        g.drawLine (100, 50, 250, 200);
        g.drawLine (100, 500, 250, 650);
        g.drawLine (600, 50, 750, 200);
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x_ball, y_ball, diameter, diameter);
    }

    public void run ()
    {
        Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);
        while (shouldRun)
        {       
            if (!ThreeDMultiplyer_isInfinity)
                ThreeDMultiplyer = (int) (Math.floor (6 / z_speed) * 10);

            milliseconds += 10;
            x_ball += x_speed * x_direction;
                y_ball += y_speed * y_direction;
            if (diameter < 60 && milliseconds % ThreeDMultiplyer == 0 && !maxDiameter)
                diameter++;
            else if (milliseconds % ThreeDMultiplyer == 0)
            {
                maxDiameter = true;
                diameter--;
                if (diameter == 20)
                {
                    maxDiameter = false;
                    playSound();
                }
                else if (diameter == 59) 
                	playSound();
            }
            ThreeDPositioner = (int) (3.75 * diameter - 75);
            if (y_ball + diameter >= 500 + ThreeDPositioner && y_direction == 1)
            {
                y_direction = -1;
                playSound();
    
            }

            else if (y_ball <= 50 + ThreeDPositioner && y_direction == -1)
            {
                y_direction = +1;
                playSound();
            }

            if (x_ball + diameter >= 600 + ThreeDPositioner && x_direction == 1)
            {
                x_direction = -1;
                playSound();  
            }
            else if (x_ball <= 100 + ThreeDPositioner && x_direction == -1)
            {
                x_direction = +1;
                playSound();
            }
            try
            {
                // Stop thread for 10 milliseconds
                Thread.sleep (20);
            }
            catch (InterruptedException ex)
            {
                // do nothing
            }
            repaint ();
            Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);
        }
    }
    
    public void playSound()
    {
    	if (beep != null)
     	   beep.run();
    }
    
	public void terminateThread()
    {
    	shouldRun = false;
    }
}
