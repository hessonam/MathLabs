package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;

public class GraphMouseController implements MouseListener, MouseMotionListener, MouseWheelListener
{
	private GrapherModel model;
	
	private int mouseClickX, mouseClickY;
    private int startGridX, startGridY;
	
	public GraphMouseController(GrapherModel model)
	{
		this.model = model;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		int currentXScaleValue = model.getXScaleValue();
		int currentYScaleValue = model.getYScaleValue();
		
		if (e.getWheelRotation() > 0 && currentXScaleValue > 2)
		{
			model.setXScaleValue(currentXScaleValue - 1);
			model.setYScaleValue(currentYScaleValue - 1);
			model.setXUnit((currentXScaleValue - 1) * 10);
			model.setYUnit((currentYScaleValue - 1) * 10);
		}
		else if(e.getWheelRotation() < 0 && currentXScaleValue < 200)
		{
			model.setXScaleValue(currentXScaleValue + 1);
			model.setYScaleValue(currentYScaleValue + 1);
			model.setXUnit((currentXScaleValue + 1) * 10);
			model.setYUnit((currentXScaleValue + 1) * 10);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if (e.getX() < model.getGraphWidth() && e.getX() > 0)
		{
			model.setXGrid(startGridX + (e.getX() - mouseClickX));
		}
		if (e.getY() < model.getGraphHeight() && e.getY() > 0)
		{
			model.setYGrid(startGridY + (e.getY() - mouseClickY));
		}
		 
		//graph.existsError = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		double x = (e.getX() - model.getXGrid());
		x /= model.getXUnit();
		double y = (e.getY() - model.getYGrid());
		y /= model.getYUnit();
		this.model.setMouseCoordinates(x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		mouseClickX = e.getX();
		mouseClickY = e.getY();
		startGridX = this.model.getXGrid();
		startGridY = this.model.getYGrid();	
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
