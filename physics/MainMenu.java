package physics;

import javax.swing.*;

import physics.sim.*;

import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener
{
    int width = 300;
    int height = 300;
    String number = "";
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;
    JPanel mainPanel = new JPanel (new GridLayout (6, 1));
    JPanel nullPanel = new JPanel ();
    JPanel threeD = new JPanel ();
    JPanel optics = new JPanel ();
    JPanel specialRelativity = new JPanel();
    JPanel mathTools = new JPanel ();
    JPanel author = new JPanel ();
    JButton btn_threeD = new JButton ("Motion");
    JButton btn_optics = new JButton ("Optics");
    JButton btn_specialRelativity = new JButton("S. Relativity");
    JButton btn_mathTools = new JButton ("Math Tools");
    JLabel lbl_author = new JLabel ("Amer Hesson - 2010");

    public MainMenu () 
    {
        super("Amer Hesson's Physics Applet");
        setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        getContentPane ().add (mainPanel);
        mainPanel.add (nullPanel);
        mainPanel.add (threeD);
        mainPanel.add (optics);
        mainPanel.add(specialRelativity);
        mainPanel.add (mathTools);
        mainPanel.add (author);
        threeD.add (btn_threeD);
        optics.add (btn_optics);
        specialRelativity.add(btn_specialRelativity);
        mathTools.add (btn_mathTools);
        author.add (lbl_author);
        lbl_author.setForeground (Color.GRAY);
        btn_threeD.addActionListener (this);
        btn_optics.addActionListener (this);
        btn_mathTools.addActionListener(this);
        btn_specialRelativity.addActionListener(this);
    }

    public void actionPerformed (ActionEvent e)
    {
        this.dispose ();
        if (e.getSource() == btn_threeD)
            new ThirdDimension().setVisible (true);
        else if (e.getSource() == btn_optics)
            new Optics ().setVisible (true);
        else if (e.getSource() == btn_mathTools)
        	new MathMenu().setVisible(true);
        else if (e.getSource() == btn_specialRelativity)
        	new SpecialRelativity().setVisible(true);
    }
    
    public static void main (String[] args)
    {
        new MainMenu ().setVisible (true);
    }
}