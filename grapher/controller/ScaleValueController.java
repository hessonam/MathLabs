package physics.grapher.controller;

import physics.grapher.*;
import javax.swing.event.*;
import javax.swing.JSpinner;

public class ScaleValueController implements ChangeListener
{
	private GrapherModel model;
	private JSpinner setXScale;
	private JSpinner setYScale;
	
	public ScaleValueController(GrapherModel model, JSpinner setXScale, JSpinner setYScale)
	{
		this.model = model;
		this.setXScale = setXScale;
		this.setYScale = setYScale;
	}
	
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (e.getSource() == setXScale)
			this.model.setXUnit(((Integer) setXScale.getValue()).intValue() * 10);
		else if (e.getSource() == setYScale)
			this.model.setYUnit (((Integer) setYScale.getValue()).intValue() * 10);
	}
}
