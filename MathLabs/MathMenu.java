package physics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import physics.grapher.*;
import physics.sim.*;

public class MathMenu extends JFrame implements ActionListener
{
    int width = 300;
    int height = 300;
    String number = "";
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;
    JPanel mainPanel = new JPanel (new GridLayout (6, 1));
    JPanel nullPanel = new JPanel ();
    JPanel mainMenu = new JPanel();
    JPanel grapher = new JPanel ();
    JPanel vectorAdder = new JPanel ();
    JPanel sierpinski = new JPanel();
    JPanel author = new JPanel ();
    JButton btn_grapher = new JButton ("Grapher");
    JButton btn_vectorAdder = new JButton ("Vector Adder");
    JButton btn_mainMenu = new JButton("Main Menu");
    JButton btn_sierpinski = new JButton("Chaos Game");
    JLabel lbl_author = new JLabel ("Amer Hesson - 2010");

    public MathMenu () 
    {
        super ("Amer Hesson's Math Tools");
        setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        getContentPane ().add (mainPanel);
        mainPanel.add (nullPanel);
        mainPanel.add(mainMenu);
        mainPanel.add (grapher);
        mainPanel.add (vectorAdder);
        mainPanel.add(sierpinski);
        mainPanel.add (author);
        mainMenu.add(btn_mainMenu);
        grapher.add (btn_grapher);
        vectorAdder.add (btn_vectorAdder);
        sierpinski.add(btn_sierpinski);
        author.add (lbl_author);
        lbl_author.setForeground (Color.GRAY);
        btn_mainMenu.addActionListener(this);
        btn_grapher.addActionListener (this);
        btn_vectorAdder.addActionListener(this);
        btn_sierpinski.addActionListener(this);
        
        mainPanel.setBackground(Color.ORANGE);
        nullPanel.setOpaque(false);
        mainMenu.setOpaque(false);
        grapher.setOpaque(false);
        vectorAdder.setOpaque(false);
        sierpinski.setOpaque(false);
        author.setOpaque(false);
    }

    public void actionPerformed (ActionEvent e)
    {
    	dispose();
        if (e.getSource() == btn_mainMenu)
        	new MainMenu().setVisible(true);
        else if (e.getSource () == (btn_grapher)) {
        	GrapherModel model = new GrapherModel();
    		GrapherGUI view = new GrapherGUI(model);
    		view.setVisible(true);
        }
        else if (e.getSource () == (btn_vectorAdder))
            new VectorAdder().setVisible(true);
        else if (e.getSource() == btn_sierpinski)
        {
        	new Sierpinski().setVisible(true);
        	SierpinskiDesign.shouldRun = true;
        }
    }
    
    public static void main (String[] args)
    {
        new MainMenu ().setVisible (true);
    }
}