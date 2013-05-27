package physics.grapher;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.prefs.Preferences;

class GrapherPreferences extends JFrame implements ChangeListener, ActionListener
{

	private int width;
	private int height;
	private Dimension screenSize;
	private int xFrame;
	private int yFrame;
    private JPanel mainPanel;
    private JLabel defaultScaleX;
    private JLabel defaultScaleY;
    private JLabel defaultColors;
    private SpinnerModel xScaleIntegers;
    private SpinnerModel yScaleIntegers;
    private JSpinner setXScale;
    private JSpinner setYScale;
    private JButton ok;
    private JButton restoreToDefault;
    private JButton background;
    private JButton grid;
    private JButton graph;
    private GrapherModel model;
    
	public GrapherPreferences(GrapherModel model)
	{
		super("Preferences");
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        setResizable (false);
        
        this.model = model;
        
        width = 300;
    	height = 330;
    	screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    	xFrame = (screenSize.width - width) / 2;
    	yFrame = (screenSize.height - height) / 2;
        mainPanel = new JPanel();
        defaultScaleX = new JLabel("Set default scale (x) value:");
        defaultScaleY = new JLabel("Set default scale (y) value:");
        defaultColors = new JLabel("Set default colors:");
        ok = new JButton("OK");
        restoreToDefault = new JButton("Restore to Default");
        background = new JButton("Background");
        grid = new JButton("Grid");
        graph = new JButton("Graph");
        xScaleIntegers = new SpinnerNumberModel (this.model.getPreferences().getInt("ScaleX", 5), 2, 200, 1);
        yScaleIntegers = new SpinnerNumberModel (this.model.getPreferences().getInt("ScaleY", 5), 2, 200, 1);
        setXScale = new JSpinner(xScaleIntegers);
        setYScale = new JSpinner(yScaleIntegers);
        
        setSize (width, height);
        setLocation (xFrame, yFrame);
        
        getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.add(defaultScaleX);
        mainPanel.add(defaultScaleY);
        mainPanel.add(setYScale);
        mainPanel.add(setXScale);
        mainPanel.add(defaultColors);
        mainPanel.add(background);
        mainPanel.add(grid);
        mainPanel.add(graph);
        mainPanel.add(restoreToDefault);
        mainPanel.add(ok);
        
        defaultScaleX.setBounds(20,20,190,30);
        setXScale.setBounds(220,20,40,30);
        defaultScaleY.setBounds(20,60,190,30);
        setYScale.setBounds(220,60,40,30);
        defaultColors.setBounds(20,135,140,30);
        background.setBounds(160, 110, 120,25);
        grid.setBounds(160, 140, 120,25);
        graph.setBounds(160, 170, 120,25);
        restoreToDefault.setBounds(65,220,170,25);
        ok.setBounds(120,260,60,30);
        
        setXScale.addChangeListener(this);
        setYScale.addChangeListener(this);
        background.addActionListener(this);
        grid.addActionListener(this);
        graph.addActionListener(this);
        restoreToDefault.addActionListener(this);
        ok.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == background)
		{
			//System.out.println("prefs: " + new Color(this.model.getPreferences().getInt("bgColor", Color.BLACK.getRGB())));
			Color bgColor = JColorChooser.showDialog(null,"Select background color", new Color(this.model.getPreferences().getInt("bgColor", Color.BLACK.getRGB())));
			if (bgColor instanceof Color)
				this.model.getPreferences().putInt("bgColor", bgColor.getRGB());
		}
		else if (e.getSource() == grid)
		{
			Color gridColor = JColorChooser.showDialog(null,"Select grid color", new Color(this.model.getPreferences().getInt("gridColor", Color.WHITE.getRGB())));
			this.model.getPreferences().putInt("gridColor", gridColor.getRGB());
		}
		else if (e.getSource() == graph)
		{
			Color graphColor = JColorChooser.showDialog(null,"Select graph color", new Color(this.model.getPreferences().getInt("graphColor", Color.ORANGE.getRGB())));
			this.model.getPreferences().putInt("graphColor", graphColor.getRGB());
		}
		else if (e.getSource() == restoreToDefault)
		{
			this.model.getPreferences().putInt("ScaleX", 5);
			this.model.getPreferences().putInt("ScaleY", 5);
			this.model.getPreferences().putInt("bgColor", Color.BLACK.getRGB());
			this.model.getPreferences().putInt("gridColor", Color.WHITE.getRGB());
			this.model.getPreferences().putInt("graphColor", Color.ORANGE.getRGB());
			setXScale.setValue(5);
			setYScale.setValue(5);
			JOptionPane.showMessageDialog(this, "Default settings have been successfully restored.", "Restoration Successful", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == ok)
		{
			dispose();
		}
	}
	
	public void stateChanged (ChangeEvent e)
	{
		if (e.getSource() == setXScale)
		{
			this.model.getPreferences().putInt("ScaleX", ((Integer)setXScale.getValue()).intValue());
		}
		else if (e.getSource() == setYScale)
		{
			this.model.getPreferences().putInt("ScaleY", ((Integer)setYScale.getValue()).intValue());
		}
	}
}
