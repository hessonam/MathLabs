package physics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class SpecialRelativity extends JFrame implements ActionListener{

    static int width = 700;
    static int height = 550;
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;
    JPanel mainPanel = new JPanel();
    JPanel clockPanel = new JPanel();
    JPanel controlPanel = new JPanel();
    JButton enter = new JButton("Reset");
    JButton back = new JButton("Back");
    JLabel v1 = new JLabel("<html>v<sub>1</sub> (m/s) :</html>");
    JLabel v2 = new JLabel("<html>v<sub>2</sub> (m/s) :</html>");
    JLabel instruction = new JLabel("Enter velocity as number or in terms of c:");
    JTextField velocity1 = new JTextField("0");
    JTextField velocity2 = new JTextField("0");
    JLabel contraction = new JLabel("Lorentz-FitzGerald contraction:");
    JLabel elapsed1 = new JLabel("<html>t<sub>1</sub> (s) :</html>");
    JLabel elapsed2 = new JLabel("<html>t<sub>2</sub> (s) :</html>");
    static JTextField jtfElapsed1 = new JTextField("0");
    static JTextField jtfElapsed2 = new JTextField("0");
    
    Clock clock1 = new Clock();
    Clock clock2 = new Clock();
    static ClockHand hand1;
    static ClockHand hand2;
    
    final double c = 299792458;
    double v1_value = 0;
    double v2_value = 0;
	
	public SpecialRelativity()
	{
		super("Einstein's Special Relativity");
		setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        setLayout(null);
        getContentPane().add(mainPanel);
        
        hand1 = new ClockHand();
        hand2 = new ClockHand();
        
        mainPanel.setBounds(0,0,width, height);
        mainPanel.add(clockPanel);
        mainPanel.add(controlPanel);
        mainPanel.setLayout(null);
        
        clockPanel.setLayout(null);
        controlPanel.setLayout(null);
        clockPanel.setBounds(0,0,width, height -150);
        controlPanel.setBounds(0, height -150, width, 150);
        
        clock1.setBackground(Color.BLACK);
        clock2.setBackground(Color.BLACK);
        clockPanel.add(hand1);
        clockPanel.add(hand2);
        hand1.setOpaque(false);
        hand1.setBounds(0,0,350,400);
        hand2.setOpaque(false);
        hand2.setBounds(350,0,350,400);
        clockPanel.add(clock1);
        clock1.setBounds(0,0,SpecialRelativity.width/2, SpecialRelativity.height -150);
        clockPanel.add(clock2);
        clock2.setBounds(SpecialRelativity.width/2, 0, SpecialRelativity.width/2, SpecialRelativity.height -150);
        clock1.add(contraction);
        clock1.setLayout(null);
        contraction.setForeground(Color.WHITE);
        contraction.setBounds(20,330,300,30);
        contraction.setOpaque(false);
    
        controlPanel.add(v1);
        controlPanel.add(v2);
        controlPanel.add(instruction);
        controlPanel.add(velocity1);
        controlPanel.add(velocity2);
        controlPanel.add(enter);
        controlPanel.add(elapsed1);
        controlPanel.add(elapsed2);
        controlPanel.add(jtfElapsed1);
        controlPanel.add(jtfElapsed2);
        controlPanel.add(back);
        
        jtfElapsed1.setEditable(false);
        jtfElapsed2.setEditable(false);
        
        instruction.setBounds(30,25,350,30);
        v1.setBounds(30,55,70,30);  
        velocity1.setBounds(110, 55, 100, 30);
        v2.setBounds(width/2 + 20, 55, 70, 30);
        velocity2.setBounds(width/2 + 90, 55,100,30);
        enter.setBounds(width - 140, 55, 90, 30);
        elapsed1.setBounds(30,100,70,30);
        elapsed2.setBounds(width/2 + 20, 100, 70, 30);
        jtfElapsed1.setBounds(100, 100,100,30);
        jtfElapsed2.setBounds(width/2 + 90, 100,100,30);
        back.setBounds(width - 140, 100,90,30);
        
        enter.addActionListener(this);
        back.addActionListener(this);
        velocity1.addActionListener(this);
        velocity2.addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		
		if (e.getSource() == back)
		{
			hand1.terminateThread();
			hand2.terminateThread();
			this.dispose();
			new MainMenu().setVisible(true);
		}
		else
		{
			String speed1 = velocity1.getText().replace(" ","");
			String speed2 = velocity2.getText().replace(" ","");
			if (velocity1.getText().indexOf("c") == -1)
				v1_value = Double.parseDouble(speed1);
			else if (speed1.equals("c"))
				v1_value = c;
			else
				v1_value = Double.parseDouble(speed1.substring(0, speed1.indexOf('c'))) * c;
			if (velocity2.getText().indexOf("c") == -1)
				v2_value = Double.parseDouble(speed2);
			else if (speed2.equals("c"))
				v2_value = c;
			else
				v2_value = Double.parseDouble(speed2.substring(0, speed2.indexOf('c'))) * c;
			if (v1_value >= c || v2_value >= c)
			{
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Error: invalid speed.", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			else
			{
				double dilation1 = 1/(Math.sqrt(1 - ((v1_value*v1_value)/(c*c))));
				double dilation2 = 1/(Math.sqrt(1 - ((v2_value*v2_value)/(c*c))));
				hand1.setFactor(15.5*dilation1);
				hand2.setFactor(15.5*dilation2);
				hand1.setLorentz(dilation1);
				hand2.setLorentz(dilation2);
			}
		}
	}
	
	public static void changer()
	{
		jtfElapsed1.setText(hand1.getTime());
		jtfElapsed2.setText(hand2.getTime());
	}
	
	public static void main(String[] args)
	{
		new SpecialRelativity().setVisible(true);
	}
}
