package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;
import javax.swing.*;

public class SlopeValueController implements ActionListener
{
	private GrapherModel model;
	private JTextField txt_slope;
	
	public SlopeValueController(GrapherModel model, JTextField txt_slope)
	{
		this.model = model;
		this.txt_slope = txt_slope;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Double slope = null;
		try
		{
			slope = Double.parseDouble(this.txt_slope.getText());
		}catch(NumberFormatException ne)
		{
			return;
		}
		this.model.setSlopePoint(slope);
	}
}
