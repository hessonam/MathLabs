package physics.grapher.controller;

import physics.grapher.*;
import javax.swing.event.*;
import javax.swing.JSpinner;

public class GraphPrecisionController implements ChangeListener
{
	private GrapherModel model;
	private JSpinner setGraphPrecision;
	
	public GraphPrecisionController(GrapherModel model, JSpinner setGraphPrecision)
	{
		this.model = model;
		this.setGraphPrecision = setGraphPrecision;
	}
	@Override
	public void stateChanged(ChangeEvent e)
	{
			this.model.setGraphPrecision(((Integer)this.setGraphPrecision.getValue()).intValue());
	}

}
