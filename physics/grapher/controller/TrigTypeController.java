package physics.grapher.controller;

import physics.grapher.*;

import java.awt.event.*;
import javax.swing.*;

public class TrigTypeController implements ItemListener
{
	private GrapherModel model;
	private JComboBox box_trigFunctions;
	
	public TrigTypeController(GrapherModel model, JComboBox box_trigFunctions)
	{
		this.model = model;
		this.box_trigFunctions = box_trigFunctions;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		model.setTrigFunctionType(box_trigFunctions.getSelectedIndex());
	}

}
