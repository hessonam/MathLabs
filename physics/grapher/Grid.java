package physics.grapher;

import physics.math.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

class Grid extends JPanel
{
	private GrapherModel model;
	protected int width, height;
	protected int xGrid , yGrid, xUnit, yUnit, graphPrecision;
	private int xCoordinate, yCoordinate;
	private int piDenominators[] = {2, 3, 4, 6, 12};
	private Font font = new Font("Cambria", 1, 12);
    private FontMetrics fontMetrics;
    
	public Grid(GrapherModel model)
	{
		this.model = model;	
	}
	
	public void paintComponent(Graphics g0)
	{
		super.paintComponent (g0);
		
		Graphics2D g = (Graphics2D)g0;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(font);
        fontMetrics = g.getFontMetrics();
		g.setColor(this.model.getGridColor());
		g.setFont(new Font("Impact", Font.PLAIN, 11));
		
		xGrid = this.model.getXGrid();
		yGrid = this.model.getYGrid();
		xUnit = this.model.getXUnit();
		yUnit = this.model.getYUnit();
		graphPrecision = this.model.getGraphPrecision();
		
		//Draws grid
		g.drawLine(xGrid, 0, xGrid, height);
		g.drawLine(0, yGrid, width, yGrid);
		
		//Draws a white line on the x and y grids every 10 pixels
		for(int x = xGrid; x <= width; x += 10)
			g.drawLine(x, yGrid+3, x, yGrid-3);
		
		for(int x = xGrid; x >= 0; x -= 10)
			g.drawLine(x, yGrid+3, x, yGrid-3);
		
		for(int y = yGrid; y<=height; y+=10)
			g.drawLine(xGrid+3, y, xGrid -3 , y);
		
		for(int y = yGrid; y>=0; y-=10)
			g.drawLine(xGrid+3, y, xGrid -3 , y);
		
		//Draws an orange line on the grid for every (default = 5) white lines
		g.setColor(this.model.getGraphColor());
		int piUnit = (int)Math.round((double)xUnit * Math.PI);
		
		if(model.isIntegerScale())
		{
			for(int x = xGrid + xUnit; x <= width; x += xUnit)
			{
				g.drawLine(x, yGrid + 5, x, yGrid - 5);
				xCoordinate = x - xGrid;
				xCoordinate /= xUnit;
				g.drawString("" + xCoordinate, x - 2, yGrid + 20);
			}
			
			for(int x = xGrid - xUnit; x >= 0; x -= xUnit)
			{
				g.drawLine(x, yGrid + 5, x, yGrid - 5);
				xCoordinate = x - xGrid;
				xCoordinate /= xUnit;
				g.drawString(""+xCoordinate, x - 5, yGrid + 20);
			}
		}
		else
		{
			/*
			int piScale = (int)Math.round(xUnit * Math.PI);
			for(int x = xGrid + piScale; x <= width; x += piScale)
			{
				g.drawLine(x, yGrid + 5, x, yGrid - 5);
				xCoordinate = x - (xGrid + piScale);
				xCoordinate = (xCoordinate/piScale) + 1;
				g.drawString(""+(xCoordinate == 1 ? "": xCoordinate) + "π", x-2, yGrid + 20);
			}
			
			for(int x = xGrid - piScale; x >= 0; x -= piScale)
			{
				g.drawLine(x, yGrid + 5, x, yGrid - 5);
				xCoordinate = x - (xGrid - piScale);
				xCoordinate = (xCoordinate/piScale) - 1;
				g.drawString(""+(xCoordinate == -1 ? "-": xCoordinate) + "π", x - 5, yGrid + 20);
			}
			*/
			
			int piStandardDeno = 1;
            for(int x = piDenominators.length - 1; x >= 0; x--)
            {
                if(piUnit / piDenominators[x] < 40)
                    continue;
                piStandardDeno = piDenominators[x];
                break;
            }
			
            if(piStandardDeno != 1)
            {
                int count = 1;
                for(int x = xGrid + (int)Math.round(((double)xUnit * Math.PI) / (double)piStandardDeno); x <= width; x = xGrid + (int)Math.round(((double)(count * xUnit) * Math.PI) / (double)piStandardDeno))
                {
                    if(count % piStandardDeno != 0)
                    {
                        g.drawLine(x, yGrid + 3, x, yGrid - 3);
                        int fraction[] = Algebra.simplifyFraction(count, piStandardDeno);
                        String label = (new StringBuilder()).append(fraction[0] != 1 ? ((Integer.valueOf(fraction[0]))) : "").append("\u03C0/").append(fraction[1]).toString();
                        g.drawString(label, x - fontMetrics.stringWidth(label) / 2, yGrid + 20);
                    }
                    count++;
                }
 
                count = 1;
                for(int x = xGrid - (int)Math.round(((double)xUnit * Math.PI) / (double)piStandardDeno); x >= 0; x = xGrid - (int)Math.round(((double)(count * xUnit) * 3.1415926535897931D) / (double)piStandardDeno))
                {
                    if(count % piStandardDeno != 0)
                    {
                        g.drawLine(x, yGrid + 3, x, yGrid - 3);
                        int fraction[] = Algebra.simplifyFraction(count, piStandardDeno);
                        String label = (new StringBuilder()).append(fraction[0] != 1 ? ((Integer.valueOf(-fraction[0]))) : "-").append("\u03C0/").append(fraction[1]).toString();
                        g.drawString(label, x - fontMetrics.stringWidth(label) / 2, yGrid + 20);
                    }
                    count++;
                }
 
            }
		}
		for(int y = yGrid + yUnit; y <= height; y += yUnit)
		{
			g.drawLine(xGrid + 5, y, xGrid - 5, y);
			yCoordinate = yGrid - y;
			yCoordinate /= yUnit;
			g.drawString(""+yCoordinate, xGrid + 20, y + 5);
		}
		for(int y = yGrid - yUnit; y >= 0; y -= yUnit)
		{
			g.drawLine(xGrid + 5, y, xGrid - 5, y);
			g.drawLine(xGrid + 5, y, xGrid - 5, y);
			yCoordinate = yGrid - y;
			yCoordinate /= yUnit;
			g.drawString(""+yCoordinate, xGrid + 20, y + 5);
		}
		//if polynomials
	}//end paintComponent
	
	public void updateGrid (int width, int height)
	{
		this.width = width;
		this.height = height;
		this.xGrid = this.width/2;
		this.yGrid = this.height/2;
	}
	
}
