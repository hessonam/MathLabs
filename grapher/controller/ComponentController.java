package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;

public class ComponentController implements ComponentListener
{
	
	private GrapherModel model;
	private GrapherGUI view;
	
	public ComponentController(GrapherModel model, GrapherGUI view)
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		int currentWidth = this.view.getWidth();
		int currentHeight = this.view.getHeight();
		this.model.setWidth(currentWidth);
		this.model.setHeight(currentHeight);
		this.model.setGraphWidth(currentWidth);
	    this.model.setGraphHeight(currentHeight - 180);
	}

	@Override
	public void componentShown(ComponentEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
}
