package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;
import javax.swing.*;

public class EquationTypeController implements ItemListener
{
	private GrapherModel model;
	private JComboBox box_equationTypes;
	
	public EquationTypeController(GrapherModel model, JComboBox box_equationTypes)
	{
		this.model = model;
		this.box_equationTypes = box_equationTypes;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		model.setCurrentEquationType(this.box_equationTypes.getSelectedIndex());
	}
}
