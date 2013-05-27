package physics.sim;

import javax.swing.*;

import physics.MathMenu;

import java.awt.*;
import java.awt.event.*;

public class Sierpinski extends JFrame implements MouseListener, ActionListener
{
	static int width = 600;
    static int height = 520;
    boolean isValidated = false;
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;
    JPanel mainPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    SierpinskiTriangle sierpinski = new SierpinskiTriangle();
    SierpinskiDesign sd;
    JLabel jlbStatus = new JLabel();
    JLabel settings = new JLabel("(n, r) = (");
    JLabel bracket = new JLabel(")");
    JTextField jtfN = new JTextField();
    JTextField jtfR = new JTextField();
    JButton begin = new JButton("Begin");
    JButton faster = new JButton(">");
    JButton slower = new JButton("<");
    JButton back = new JButton("Back");
    JButton help = new JButton("Help");
    JButton enter = new JButton("Enter");
    static int n;
    static double r;
    int status = 0;
    static int px=0, py=0;
    static int[] x;
    static int[] y;
    
    public Sierpinski()
    {
    	super("Chaos Game & The Sierpinski Triangle");
    	setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        getContentPane ().add (mainPanel);
        mainPanel.setLayout(null);
        mainPanel.add(controlPanel);
        mainPanel.add(sierpinski);
        sierpinski.setBounds(0,0,width, height - 120);
        sierpinski.setBackground(Color.BLACK);
        sierpinski.addMouseListener(this);
        controlPanel.setBounds(0,height-120, width,120);
        controlPanel.setLayout(null);
        controlPanel.add(jlbStatus);
        controlPanel.add(begin);
        controlPanel.add(faster);
        controlPanel.add(slower);
        controlPanel.add(back);
        controlPanel.add(help);
        controlPanel.add(settings);
        controlPanel.add(jtfN);
        controlPanel.add(jtfR);
        controlPanel.add(bracket);
        controlPanel.add(enter);
        
        slower.setBounds(470,15,45,30);
        faster.setBounds(515,15,45,30);
        help.setBounds(340,55,85,30);
        back.setBounds(435,55,95,30);
        settings.setBounds(20,55,65,30);
        jtfN.setBounds(85,55,30,30);
        jtfR.setBounds(115,55,30,30);
        bracket.setBounds(150,55,5,30);
        enter.setBounds(180,55,100,30);
        jlbStatus.setBounds(20,15,width-40,30);
        
        jlbStatus.setText("<html>Input values for <i>n</i> and <i>r</i>, then click \"Enter\".</html>");
        faster.addActionListener(this);
        slower.addActionListener(this);
        begin.addActionListener(this);
        back.addActionListener(this);
        help.addActionListener(this);
        enter.addActionListener(this);
        begin.setBounds(340,15,100,30);
        begin.setEnabled(false);
        faster.setToolTipText("Faster");
        slower.setToolTipText("Slower");
    }
    
    public void mouseClicked (MouseEvent e)
    {
    	if (isValidated)
    	{
    		status++;
    		if (status <= n)
    			sierpinski.drawVertex(x[status-1] = e.getX(),y[status-1] = e.getY());
	    	if(status == 1)
	    		jlbStatus.setText("<html>Select the point for the 2<sup>nd</sup> vertex.<html>");
	    	else if (status == 2)
	    		jlbStatus.setText("<html>Select the point for the 3<sup>rd</sup> vertex.</html>");
	    	else if (status < n)
	    		jlbStatus.setText("<html>Select the point for the "+(status+1)+"<sup>th</sup> vertex.</html>");
	    	else if (status == n)
	    		jlbStatus.setText("Select the starting point.");
	    	else
	    	{
	    		jlbStatus.setText("Click \"Begin\" to begin the chaos game.");
	    		sierpinski.drawVertex(px = e.getX(),py = e.getY());
	    		begin.setEnabled(true);
	    	}
	    	repaint();
    	}
    }
    
    public void mouseReleased(MouseEvent e)
    {
    	
    }
    
    public void mouseEntered (MouseEvent e)
    {
    	
    }
    public void mousePressed (MouseEvent e)
    {
    	
    }
    
    public void mouseExited (MouseEvent e)
    {
    	
    }
    
    public void actionPerformed (ActionEvent e)
    {
    	if(e.getSource() == begin)
    	{
    		jlbStatus.setText("Watch the formation of a fractal.");
	    	mainPanel.remove(sierpinski);
	    	sd = new SierpinskiDesign(px, py);
	    	mainPanel.add(sd);
	    	sd.setBounds(0,0,width, height-100);
	    	sd.setOpaque(false);
	    	mainPanel.add(sierpinski);
	    	begin.setEnabled(false);
    	}
    	
    	else if(e.getSource() == faster)
    	{
    		slower.setEnabled(true);
    		if (sd.fps > 50)
    			sd.fps -= 50;
    		else if(sd.fps>10)
    			sd.fps-=10;
    		else if (sd.fps == 10)
    			faster.setEnabled(false);
    	}
    	
    	else if(e.getSource() == slower)
    	{
    		faster.setEnabled(true);
    		if (sd.fps >= 3000)
    			slower.setEnabled(false);
    		else if (sd.fps > 50)
    			sd.fps += 100;
    		else if(sd.fps>10)
    			sd.fps+=10;
    	}
    	else if (e.getSource()==enter)
    	{
    		try{
    		n = Integer.parseInt(jtfN.getText());
    		r = Double.parseDouble(jtfR.getText());
    		}
    		catch(NumberFormatException ex)
    		{
    			ex.printStackTrace();
    			Toolkit.getDefaultToolkit().beep();
    			JOptionPane.showMessageDialog(this,"Please make sure that numbers are inserted as input.", "Parsing Error", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		if (n%1 == 0 && n>2 && r<1 && r>0)
    		{
    			x = new int[n];
    			y = new int[n];
    			isValidated = true;
    			enter.setEnabled(false);
    			jlbStatus.setText("<html>Select the point for the 1<sup>st</sup> vertex.<html>");
    		}
    		else
    		{
    			Toolkit.getDefaultToolkit().beep();
    			JOptionPane.showMessageDialog(this,"<html><i>n</i> ≥ 3; <i>n</i> ∈ <b>Z</b>  and  0 &#060; <i>r</i> &#060; 1; <i>r</i>  ∈ <b>R</b></html>", "Restriction Error", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    	else if(e.getSource() == back)
    	{
    		try{
    		sd.terminateThread();
    		}
    		catch(NullPointerException npe)
    		{
    			npe.printStackTrace();
    		}
    		new MathMenu().setVisible(true);
    		this.dispose();
    	}
    	else if (e.getSource() == help)
    	{
    		JOptionPane.showMessageDialog(this, "Please read this article:\n\n http://mathworld.wolfram.com/ChaosGame.html\n\n<html>Please note that the value for <i>r</i> expressed on that page is the</html>\ndistance from the vertex to the point while the opposite is true\n<html>for the usage of <i>r</i> in this program. i.e. r = 3/8 should be</html>\nexpressed as 5/8.", "Help", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
    public static void main (String[] args)
    {
    	new Sierpinski().setVisible(true);
    }
}
