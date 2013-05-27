package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;
import javax.swing.*;

public class ScaleTypeController implements ItemListener
{
	private GrapherModel model;
	private JRadioButton piScale;
	private JRadioButton integerScale;
	
	public ScaleTypeController(GrapherModel model, JRadioButton piScale, JRadioButton integerScale)
	{
		this.model = model;
		this.piScale = piScale;
		this.integerScale = integerScale;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getItem() == piScale) 
	    {
			model.setScaleType(false);
	    }
	    else if (e.getItem() == integerScale)
	    {
	    	model.setScaleType(true);
	    }   		
	}

}
