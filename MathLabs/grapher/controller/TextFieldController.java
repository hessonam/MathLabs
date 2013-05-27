package physics.grapher.controller;

import physics.grapher.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextFieldController implements ActionListener, KeyListener
{
	public final static String INVALID_CHARACTERS = "qwertyuiopQWERTYUIOP[]\\asdfghjklASDFGHJKL;\'zxcvbnmZXCVBNM,/{}|:\"<>?=_`~!@#$%^&*() ";
	private GrapherModel model;
	private JTextField[] txt_coefficients;
	
	public TextFieldController(GrapherModel model, JTextField[] txt_coefficients)
	{
		this.model = model;
		this.txt_coefficients = txt_coefficients;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String characters = ((JTextField)e.getSource()).getText();
		int currentPosition;
		String newCharacters = "";
		for (int i = 0 ; i <characters.length() ; i++) 
        {
            currentPosition = INVALID_CHARACTERS.indexOf (characters.charAt(i));

            if (currentPosition == -1)  
            {
                newCharacters += (characters.substring(i, i+1));
            }
        }

		((JTextField)e.getSource()).setText(newCharacters);
		Double[] newCoefficients = new Double[txt_coefficients.length];
		for (int x = 0; x < this.txt_coefficients.length; x++)
		{
			try
			{
				newCoefficients[x] = Double.parseDouble(this.txt_coefficients[x].getText());
			}catch(NumberFormatException ex)
			{
				newCoefficients[x] = 0.0;
			}
		}
		if (newCoefficients.length == 5)
			this.model.setCoefficients(newCoefficients);
		else if (newCoefficients.length == 4)
			this.model.setTransformations(newCoefficients);
		
	}
	
	@Override
	public void keyPressed(KeyEvent k){}
	@Override
	public void keyReleased(KeyEvent k){}
	@Override
	public void keyTyped(KeyEvent k)
	{		
		char c = k.getKeyChar();
		int caretPosition = ((JTextField)k.getSource()).getCaretPosition();
		if(INVALID_CHARACTERS.indexOf(c) != -1 || (c == '.' && alreadyExists(k, '.')) || ((c == '-' || c == '+') && caretPosition != 0))
		{
			Toolkit.getDefaultToolkit().beep();
		}
		else
			return;
		k.consume();
	}
	
	private boolean alreadyExists(KeyEvent k, char c)
	{
		String characters = ((JTextField)k.getSource()).getText();
		for (int i =0; i<characters.trim().length(); i++)
			if (characters.charAt(i) == c)
				return true;
		
		return false;
	} 
}
