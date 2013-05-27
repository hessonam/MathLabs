package physics.grapher.controller;

import physics.grapher.*;
import java.awt.event.*;
import javax.swing.*;

public class IntegralValueController implements ActionListener
{
	private GrapherModel model;
	private JTextField txt_aBounds;
	private JTextField txt_bBounds;
	
	public IntegralValueController(GrapherModel model, JTextField txt_aBounds, JTextField txt_bBounds)
	{
		this.model = model;
		this.txt_aBounds = txt_aBounds;
		this.txt_bBounds = txt_bBounds;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Double aBounds = null, bBounds = null;
		try
		{
			aBounds = Double.parseDouble(this.txt_aBounds.getText());
			bBounds = Double.parseDouble(this.txt_bBounds.getText());
		}catch(NumberFormatException ne)
		{
			return;
		}
		this.model.setBounds(aBounds, bBounds);
	}
}
