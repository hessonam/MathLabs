package physics.grapher;

import physics.*;
import physics.grapher.controller.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;

public class GrapherGUI extends JFrame implements ActionListener
{
	public final static String[] EQUATION_TYPES = {"Polynomial", "Trigonometric", "Exponential", "Rational", "Custom"};
	public final static String[] TRIG_FUNCTIONS = {"sin( θ )", "cos( θ )", "tan( θ )", "csc( θ )", "sec( θ )", "cot( θ )"};
	final private String INSTRUCTIONS, ABOUT;
    private int xFrame;
    private int yFrame;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JButton back;
	private JLabel lbl_equationTypes;
	private JLabel lbl_graphPercision;
	private JLabel lbl_function;
	private JLabel lbl_xScale;
	private JLabel lbl_yScale;
	private JLabel lbl_equation;
	private JLabel lbl_equation_n;
	private JLabel lbl_equation_underline;
	private JLabel lbl_equation_d;
	private JComboBox box_equationTypes;
	private JComboBox box_trigFunctions;
	private JTextField[] txt_coefficients;
	static JTextField[] txt_transformations;
	static JTextField txt_exponentialBase;
	static JTextField[] txt_coefficients_n;
	static JTextField[] txt_coefficients_d;
	private JTextField txt_slope;
	private JLabel[] lbl_coefficients_n;
	private JLabel[] lbl_coefficients_d;
	private JLabel lbl_exponentialBase;
	private JLabel[] lbl_coefficients;
	private JLabel lbl_numerator;
	private JLabel lbl_denominator;
	private JLabel lbl_verticalTransformation;
	private JLabel lbl_horizontalTransformation;
	private JLabel lbl_horizontalTranslation;
	private JLabel lbl_verticalTranslation;
	private JLabel lbl_slope;
	private JLabel lbl_slopeValue;
	private JLabel lbl_integral;
	private JTextField txt_aBounds;
	private JTextField txt_bBounds;
	private JLabel lbl_integralValue;
	private JLabel mouseCoordinates;
	private SpinnerModel positiveIntegers;
	private SpinnerModel xScaleIntegers;
	private SpinnerModel yScaleIntegers;
	private JSpinner setGraphPrecision;
	private JSpinner setXScale;
	private JSpinner setYScale;
	private JButton reset;
	private JRadioButton piScale;
	private JRadioButton integerScale;
	private ButtonGroup scaleGroup;
	private JLabel lbl_customEquation;
    private JButton graphIt;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu settingsMenu;
    private JMenuItem pictureMenuItem;
    private JMenuItem closeMenuItem;
    private JMenu colorMenu;
    private JMenu customColorMenu;
    private JMenu selectColorMenu;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private JMenuItem instructionsMenuItem;
    private JMenuItem bgColorMenuItem;
    private JMenuItem grapherColorMenuItem;
    private JMenuItem gridColorMenuItem;
    private JMenuItem originalColorTheme;
    private JMenuItem blackColorTheme;
    private JMenuItem toggleMouseCoordinates;
    private JMenuItem preferencesMenuItem;   
    private GrapherModel model;
	private Graph graph;
	private CustomEditor equation;
	
	//Construct the GUI of the Equation Grapher
	public GrapherGUI(GrapherModel model)
	{
		//Construct JFrame and set basic properties
		super("Equation Grapher");
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (true);
        
        //Initialize variables
        this.model = model;
        this.model.setGUI(this);
        this.xFrame = (GrapherModel.screenSize.width - this.model.getWidth()) / 2;
        this.yFrame = (GrapherModel.screenSize.height - this.model.getHeight()) / 2;
        
        //set size and location of window
        setSize (this.model.getWidth(), this.model.getHeight());
        setLocation (xFrame, yFrame);
        
        //Construct GUI elements
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        back = new JButton("Back");
        lbl_equationTypes = new JLabel("Equation type:");
        lbl_graphPercision = new JLabel("Graph percision:");
        lbl_function = new JLabel("Function:");
        lbl_xScale = new JLabel("Scale (x):");
        lbl_yScale = new JLabel("Scale (y):");
        lbl_equation = new JLabel();
        lbl_equation_n = new JLabel();
        lbl_equation_underline = new JLabel();
        lbl_equation_d = new JLabel();
        box_equationTypes = new JComboBox(EQUATION_TYPES);
        box_trigFunctions = new JComboBox (TRIG_FUNCTIONS);
        txt_coefficients = new JTextField[5];
    	txt_transformations = new JTextField[4];
    	txt_exponentialBase = new JTextField("0");
    	txt_coefficients_n = new JTextField[4];
    	txt_coefficients_d = new JTextField[4];
    	txt_slope = new JTextField();
    	lbl_coefficients_n = new JLabel[4];
    	lbl_coefficients_d = new JLabel[4];
    	lbl_exponentialBase = new JLabel("b =");
    	lbl_numerator = new JLabel("P(x):");
    	lbl_denominator = new JLabel("Q(x):");
    	lbl_verticalTransformation = new JLabel("a =");
    	lbl_horizontalTransformation = new JLabel("k =");
    	lbl_horizontalTranslation = new JLabel("p =");
    	lbl_verticalTranslation = new JLabel("q =");
    	lbl_slope = new JLabel("dy/dx at x = ");
    	lbl_slopeValue = new JLabel();
    	lbl_integral = new JLabel("∫f(x) dx [");
    	txt_aBounds = new JTextField();
    	txt_bBounds = new JTextField();
    	lbl_integralValue = new JLabel("]");
    	mouseCoordinates = new JLabel();
    	positiveIntegers = new SpinnerNumberModel (1, 1, 200, 1);
        lbl_coefficients = new JLabel[5];
        lbl_coefficients[0] = new JLabel("e =");
        lbl_coefficients[1] = new JLabel("d =");
        lbl_coefficients[2] = new JLabel("c =");
        lbl_coefficients[3] = new JLabel("b =");
        lbl_coefficients[4] = new JLabel("a =");
        //set JSpinners with default value from Preferences, min value of 2, max value of 200, and discrete interval of 1
        xScaleIntegers = new SpinnerNumberModel (this.model.getXScaleValue(), 2, 200, 1);
        yScaleIntegers = new SpinnerNumberModel (this.model.getYScaleValue(), 2, 200, 1);
        setGraphPrecision = new JSpinner (positiveIntegers);
        setXScale = new JSpinner(xScaleIntegers);
        setYScale = new JSpinner(yScaleIntegers);
        reset = new JButton("Reset");
    	piScale = new JRadioButton("Scale: \"π\" units");
    	integerScale = new JRadioButton("Scale: Integer units", true);
    	lbl_customEquation = new JLabel("Enter equation: f(x) =");
        graphIt = new JButton("Graph");
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        pictureMenuItem = new JMenuItem("Save");
        closeMenuItem = new JMenuItem("Close");
        colorMenu = new JMenu("Change Colors");
        customColorMenu = new JMenu("Custom");
        selectColorMenu = new JMenu("Premade Themes");
        helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");
        instructionsMenuItem = new JMenuItem("Instructions");
        bgColorMenuItem = new JMenuItem("Background");
        grapherColorMenuItem = new JMenuItem("Graph");
        gridColorMenuItem = new JMenuItem("Grid");
        originalColorTheme = new JMenuItem("Original");
        blackColorTheme = new JMenuItem("Black & White");
        toggleMouseCoordinates = new JMenuItem("Show/Hide Coordinates");
        preferencesMenuItem = new JMenuItem("Start-up Preferences");
        
        graph = new Graph(model);
        
        //Set panel layouts
        mainPanel.setLayout(null);
        controlPanel.setLayout(null);
        
        getContentPane().add(mainPanel);
        mainPanel.add(graph);
        mainPanel.add(controlPanel);
        graph.setBounds(0, 0, this.model.getGraphWidth(), this.model.getGraphHeight());
        controlPanel.setBounds(0,this.model.getGraphHeight(), this.model.getGraphWidth(), 180);
        
        int bgR = this.model.getBgColor().getRed();
        int bgG = this.model.getBgColor().getGreen();
        int bgB = this.model.getBgColor().getBlue();
        mouseCoordinates.setForeground(new Color(255 - bgR, 255 - bgG, 255 - bgB));
		if (Math.abs((255 - bgR) - bgR) <= 10 && Math.abs((255 - bgG) - bgG) <= 10 && Math.abs((255 - bgB) - bgB)<= 10)
		{
			mouseCoordinates.setForeground(Color.WHITE);
		}
        INSTRUCTIONS = "Welcome to Amer Hesson's Equation Grapher. \n\nFeatures of the Equation Grapher: \n- Polynomial, sinusoidal, exponential, and rational functions are all supported.\n- Includes a hole detector for rational functions with common factors in the numerator and denominator.\n- Zoom in/out using the scale spinners.\n- Find the derivative of a polynomial, sinusoidal, or exponential function.\n- Find the integral of a polynomial function over a specified interval.\n- Control the transformations and translations of sinusoidal and exponential functions.\n- Change the colours of the graph.\n- Save a picture of the graph in JPEG format.\n- Graph a custom equation.\n\n Plese read the included \"help.txt\" file for more specific details regarding the Equation Grapher.";
        ABOUT = "Equation Grapher \nVersion:  1.0\n(c) Amer Hesson 2010-2011. All rights reserved.\n\nPlease report any bugs to: 696062@gmail.com";
        
        preferencesMenuItem.addActionListener(this);
        toggleMouseCoordinates.addActionListener(this);
        pictureMenuItem.addActionListener(this);
        closeMenuItem.addActionListener(this);
        bgColorMenuItem.addActionListener(this);
        grapherColorMenuItem.addActionListener(this);
        gridColorMenuItem.addActionListener(this);
        originalColorTheme.addActionListener(this);
        blackColorTheme.addActionListener(this);
        aboutMenuItem.addActionListener(this);
        instructionsMenuItem.addActionListener(this);
        customColorMenu.add(bgColorMenuItem);
        customColorMenu.add(grapherColorMenuItem);
        customColorMenu.add(gridColorMenuItem);
        selectColorMenu.add(originalColorTheme);
        selectColorMenu.add(blackColorTheme);       
        fileMenu.add(pictureMenuItem);
        fileMenu.add(closeMenuItem);
        settingsMenu.add(colorMenu);
        settingsMenu.add(toggleMouseCoordinates);
        settingsMenu.add(preferencesMenuItem);
        colorMenu.add(customColorMenu);
        colorMenu.add(selectColorMenu);
        helpMenu.add(aboutMenuItem);
        helpMenu.add(instructionsMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);       
              
        for (int i =0; i<5;i++)
        {
        	txt_coefficients[i] = new JTextField("0.0");
        }
        
        for (int i =0; i<4;i++)
		{
        	txt_coefficients_n[i] = new JTextField("0.0");
        	txt_coefficients_d[i] = new JTextField("0.0");
			if(i>1)
				txt_transformations[i] = new JTextField("0.0");
			else
				txt_transformations[i] = new JTextField("1.0");
			txt_coefficients_n[i].addActionListener(this);
			txt_coefficients_d[i].addActionListener(this);
			txt_transformations[i].addActionListener(this);	
		}
        txt_exponentialBase.addActionListener(this);
        
        lbl_coefficients_n[0] = new JLabel("<html>a<sub>1</sub> =</html>");
        lbl_coefficients_n[1] = new JLabel("<html>b<sub>1</sub> =</html>");
        lbl_coefficients_n[2] = new JLabel("<html>c<sub>1</sub> =</html>");
        lbl_coefficients_n[3] = new JLabel("<html>d<sub>1</sub> =</html>");
        lbl_coefficients_d[0] = new JLabel("<html>a<sub>2</sub> =</html>");
        lbl_coefficients_d[1] = new JLabel("<html>b<sub>2</sub> =</html>");
        lbl_coefficients_d[2] = new JLabel("<html>c<sub>2</sub> =</html>");
        lbl_coefficients_d[3] = new JLabel("<html>d<sub>2</sub> =</html>");
        
        scaleGroup = new ButtonGroup();
        scaleGroup.add(piScale);
        scaleGroup.add(integerScale);
       
        basicToolLoader();
        loadPolynomialTools();
        
        graph.setBackground(this.model.getBgColor());
        reset.addActionListener(this);
        back.addActionListener(this);
        graphIt.addActionListener(this);
        
        //register action listeners with respective elements
        registerControllers();
	}
	
	private void registerControllers()
	{
		EquationTypeController equationTypeController = new EquationTypeController(this.model, this.box_equationTypes);
		box_equationTypes.addItemListener(equationTypeController);
		
		TrigTypeController trigTypeController = new TrigTypeController(this.model, this.box_trigFunctions);
		box_trigFunctions.addItemListener(trigTypeController);
		
		ScaleTypeController scaleTypeController = new ScaleTypeController(this.model, piScale, integerScale);
		piScale.addItemListener(scaleTypeController);
	    integerScale.addItemListener(scaleTypeController);
	    
	    TextFieldController textFieldController = new TextFieldController(this.model, this.txt_coefficients);
	    for (int x = 0; x < txt_coefficients.length; x++)
	    {
	    	txt_coefficients[x].addActionListener(textFieldController);
        	txt_coefficients[x].addKeyListener(textFieldController);
        	if (x < 4)
        	{
	        	txt_coefficients_n[x].addKeyListener(textFieldController);
				txt_coefficients_d[x].addKeyListener(textFieldController);
        	}
	    }
	    txt_exponentialBase.addKeyListener(textFieldController);
        txt_slope.addKeyListener(textFieldController);
        txt_aBounds.addKeyListener(textFieldController);
        txt_bBounds.addKeyListener(textFieldController);
	    
	    ComponentController componentController = new ComponentController(this.model, this);
	    this.addComponentListener(componentController);
	    
	    GraphMouseController graphMouseController = new GraphMouseController(this.model);
	    graph.addMouseListener(graphMouseController);
	    graph.addMouseMotionListener(graphMouseController);
	    graph.addMouseWheelListener(graphMouseController);
	    
	    GraphPrecisionController graphPrecisionController = new GraphPrecisionController(this.model, this.setGraphPrecision);
	    setGraphPrecision.addChangeListener(graphPrecisionController);
	    
	    ScaleValueController scaleValueController = new ScaleValueController(this.model, this.setXScale, this.setYScale);
	    setXScale.addChangeListener(scaleValueController);
	    setYScale.addChangeListener(scaleValueController);
	    
	    SlopeValueController slopeValueController = new SlopeValueController(this.model, this.txt_slope);
	    txt_slope.addActionListener(slopeValueController);
	    
	    IntegralValueController integralValueController = new IntegralValueController(this.model, this.txt_aBounds, this.txt_bBounds);
	    txt_aBounds.addActionListener(integralValueController);
	    txt_bBounds.addActionListener(integralValueController);
	}

	public void reset()
	{
		int equationType = model.getCurrentEquationType();
		this.model.setXGrid(this.model.getGraphWidth()/2);
		this.model.setYGrid(this.model.getGraphHeight()/2);
		this.model.setXUnit(this.model.getPreferences().getInt("ScaleX", 5)*10);
		this.model.setYUnit(this.model.getPreferences().getInt("ScaleY", 5)*10);
		this.model.setGraphPrecision(1);
		setXScale.setValue(this.model.getPreferences().getInt("ScaleX", 5));
		setYScale.setValue(this.model.getPreferences().getInt("ScaleY", 5));
		setGraphPrecision.setValue(1);
		lbl_equation.setText("");
		controlPanel.removeAll();
		basicToolLoader();
		if (equationType == 0)
			loadPolynomialTools();
		else if (equationType == 1)
			loadTrigonometricTools();
		else if (equationType == 2)
			loadExponentialTools();
		else if (equationType == 3)
			loadRationalTools();
		else if (equationType == 4)
			loadCustomTools();
		lbl_equation.setText("");
		lbl_equation_n.setText("");
		lbl_equation_d.setText("");
		lbl_equation_underline.setText("");
		txt_slope.setText("");
		txt_aBounds.setText("");
		txt_bBounds.setText("");
		txt_exponentialBase.setText("0");
		lbl_slopeValue.setText("");
		lbl_integralValue.setText("]");
		integerScale.setSelected(true);
		graph.xSlope = 0;
		resetTextFields();
		controlPanel.repaint();
		graph.repaint();
	}
	
	public void resetTextFields()
	{ 
		for (int i=0; i<4; i++)
		{
			txt_coefficients_n[i].setText("0.0");
			txt_coefficients_d[i].setText("0.0");
			if (i<2)
				txt_transformations[i].setText("1.0");
			else
				txt_transformations[i].setText("0.0");
		}
		for (int i =0; i<5; i++)
			txt_coefficients[i].setText("0.0");
		txt_exponentialBase.setText("0.0");
	}

	public void basicToolLoader ()
    {
		controlPanel.add(lbl_equationTypes);
		lbl_equationTypes.setBounds(10,10,110,25);
		controlPanel.add(box_equationTypes);
		box_equationTypes.setBounds(120,10,120,25);
		controlPanel.add(lbl_graphPercision);
		lbl_graphPercision.setBounds(250,10,120,25);
		controlPanel.add(setGraphPrecision);
    	setGraphPrecision.setBounds(375,10,50,25);
    	controlPanel.add(lbl_xScale);
    	lbl_xScale.setBounds(440,10,75,25);
    	controlPanel.add(setXScale);
    	setXScale.setBounds(510,10,40,25);
    	controlPanel.add(lbl_yScale);
    	lbl_yScale.setBounds(560,10,70,25);
    	controlPanel.add(setYScale);
    	setYScale.setBounds(630,10,40,25);
    	controlPanel.add(reset);
    	reset.setBounds(10,70,80,25);
        controlPanel.add(lbl_equation);
        lbl_equation.setBounds(360,40,200,25);
        setGraphPrecision.setValue(1);
        setGraphPrecision.setEnabled(true);
        setXScale.setEnabled(true);
        setYScale.setEnabled(true);
        controlPanel.add(back);
        back.setBounds(100, 70, 80, 25);
        
        graph.add(mouseCoordinates);
        graph.setLayout(null);
        mouseCoordinates.setBounds(10,10,100,25);
        
        equation = new CustomEditor();
		controlPanel.add(equation);
		equation.setBounds(250,40,300,40);
		equation.setVisible(false);
    }
	
	public void loadPolynomialTools()
    {
		for (int i=0; i<5; i++)
		{
			controlPanel.add(txt_coefficients[i]);
			controlPanel.add(lbl_coefficients[i]);
			txt_coefficients[i].setBounds(320 - i*70,40,30,25);
			lbl_coefficients[i].setBounds(290 - i*70,40,30,25);
		}
    	
		loadDerivativeTools();
    	
    	controlPanel.add(lbl_integral);
    	controlPanel.add(txt_aBounds);
    	controlPanel.add(txt_bBounds);
    	controlPanel.add(lbl_integralValue);
    	lbl_integral.setBounds(470,70,65,25);
    	txt_aBounds.setBounds(535,70,30,25);
    	txt_bBounds.setBounds(565,70,30,25);
    	lbl_integralValue.setBounds(600,70,160,25);
    }
	
	public void loadDerivativeTools() {
		controlPanel.add(lbl_slope);
    	controlPanel.add(txt_slope);
    	controlPanel.add(lbl_slopeValue);
    	lbl_slope.setBounds(195,70,100,25);
    	txt_slope.setBounds(290,70, 30, 25);
    	lbl_slopeValue.setBounds(305,70,160,25);
	}
	
	public void loadTrigonometricTools()
	{
		for (int i=0; i<4;i++)
			controlPanel.add(txt_transformations[i]);
		controlPanel.add(lbl_function);
		lbl_function.setBounds(10,40,80,25);
		controlPanel.add(box_trigFunctions);
		box_trigFunctions.setBounds(80,40,70,25);
		controlPanel.add(lbl_verticalTransformation);
		lbl_verticalTransformation.setBounds(160,40,30,25);
		txt_transformations[0].setBounds(190,40,30,25);
		controlPanel.add(lbl_horizontalTransformation);
		lbl_horizontalTransformation.setBounds(230,40,30,25);
		txt_transformations[1].setBounds(260,40,30,25);
		controlPanel.add(lbl_horizontalTranslation);
		lbl_horizontalTranslation.setBounds(300,40,30,25);
		txt_transformations[2].setBounds(330,40,30,25);
		controlPanel.add(lbl_verticalTranslation);
		lbl_verticalTranslation.setBounds(370,40,30,25);
		txt_transformations[3].setBounds(400,40,30,25);
		lbl_equation.setBounds(440,40,155,25);
		setGraphPrecision.setEnabled(false);
		controlPanel.add(piScale);
		piScale.setBounds(605,40,140,25);
		controlPanel.add(integerScale);
		integerScale.setBounds(605,70,180,25);
		integerScale.setSelected(true);
		
		loadDerivativeTools();
	}
	
	public void loadExponentialTools()
	{
		//Construct transformation text fields
		for (int i=0; i<4;i++)
			controlPanel.add(txt_transformations[i]);
		controlPanel.add(lbl_exponentialBase);
		lbl_exponentialBase.setBounds(10,40,30,25);
		controlPanel.add(txt_exponentialBase);
		txt_exponentialBase.setBounds(40,40,30,25);
		controlPanel.add(lbl_verticalTransformation);
		lbl_verticalTransformation.setBounds(80,40,30,25);
		txt_transformations[0].setBounds(110,40,30,25);
		controlPanel.add(lbl_horizontalTransformation);
		lbl_horizontalTransformation.setBounds(150,40,30,25);
		txt_transformations[1].setBounds(180,40,30,25);
		controlPanel.add(lbl_horizontalTranslation);
		lbl_horizontalTranslation.setBounds(220,40,30,25);
		txt_transformations[2].setBounds(250,40,30,25);
		controlPanel.add(lbl_verticalTranslation);
		lbl_verticalTranslation.setBounds(290,40,30,25);
		txt_transformations[3].setBounds(320,40,30,25);
		
		loadDerivativeTools();
	}
	
	public void loadRationalTools()
	{
		for (int i=0; i<4;i++)
		{
			controlPanel.add(txt_coefficients_n[i]);
			controlPanel.add(txt_coefficients_d[i]);
			controlPanel.add(lbl_coefficients_n[i]);
			controlPanel.add(lbl_coefficients_d[i]);
		}
		controlPanel.add(lbl_numerator);
		controlPanel.add(lbl_denominator);
		controlPanel.add(lbl_equation_n);
		controlPanel.add(lbl_equation_d);
		controlPanel.add(lbl_equation_underline);
		
		lbl_numerator.setBounds(10,40,40,25);
		
		for (int i = 60; i <= 315; i+=85)
		{
			lbl_coefficients_n[(i-60)/85].setBounds(i,40,30,25);
			txt_coefficients_n[3- (i-60)/85].setBounds(i+35,40,30,25);
		}
		
		lbl_denominator.setBounds(395,40,45,25);
		
		for (int i = 455; i <= 725; i+=85)
		{
			lbl_coefficients_d[(i-455)/85].setBounds(i,40,30,25);
			txt_coefficients_d[3 -(i-455)/85].setBounds(i+35,40,30,25);
		}
		lbl_equation.setBounds(190,80,50,25);
		lbl_equation_n.setBounds(230,70,130,20);
		lbl_equation_d.setBounds(230,90,130,27);
		lbl_equation_underline.setBounds(230,75,130,20);
		lbl_equation_underline.setOpaque(false);
		setGraphPrecision.setEnabled(false);
	}
	
	public void loadCustomTools()
	{
		equation.setVisible(true);
		controlPanel.add(lbl_customEquation);
		lbl_customEquation.setBounds(70,40,160,25);
		controlPanel.add(graphIt);
		graphIt.setBounds(560, 40, 80, 30);
	}
	
	public void actionPerformed (ActionEvent e)
	{
		for (int i =0; i<5; i ++)
		{
			if (txt_coefficients[i].getText().trim().length() == 0)
				txt_coefficients[i].setText("0");
			if (i!= 4)
			{
				if(txt_coefficients_n[i].getText().trim().length() == 0)
					txt_coefficients_n[i].setText("0.0");
				if (txt_coefficients_d[i].getText().trim().length() == 0)
					txt_coefficients_d[i].setText("0.0");
				if (i>1 && txt_transformations[i].getText().trim().length() == 0)
					txt_transformations[i].setText("0.0");
				if (i<=1 && txt_transformations[i].getText().trim().length() == 0)
					txt_transformations[i].setText("1.0");
			}
		}
		if (e.getSource() == reset)
		{
			reset();
		}
		else if (e.getSource() == back)
		{
			this.dispose();
			new MathMenu().setVisible(true);
		}
		
		else if (e.getSource() == graphIt){
			graph.existsError = false;
			this.model.setCustomEditorText(equation.getText());
		}
		
		else if (e.getActionCommand().equals("Save"))
		{
			JFileChooser pictureSave = new JFileChooser();
			int retval = pictureSave.showSaveDialog(this);
			if (retval != JFileChooser.CANCEL_OPTION) {
				try {
		            Robot robot = new Robot();
		            // Capture the screen shot of the area of the screen defined by the rectangle
		            BufferedImage bi = robot.createScreenCapture(new Rectangle(getLocation().x + 10, getLocation().y + 55, this.model.getGraphWidth() - 20, this.model.getGraphHeight()));
		            ImageIO.write(bi, "jpg", new File(pictureSave.getSelectedFile().toString() + (pictureSave.getSelectedFile().toString().endsWith(".jpg") ? "":".jpg")));
		          
		        } catch (AWTException awte) {
		            awte.printStackTrace();
		        } catch (IOException ioe) {
		            ioe.printStackTrace();
		        }
			}
		}
		
		else if (e.getActionCommand().equals("Background"))
		{
			Color currentBgColor = this.model.getBgColor();
			//System.out.println(currentBgColor);
			Color newColor = JColorChooser.showDialog(null, "Select background color", currentBgColor);
			if (newColor instanceof Color)
				this.model.setBgColor(newColor);
			mouseCoordinates.setForeground(new Color(255 - currentBgColor.getRed(), 255 - currentBgColor.getGreen(), 255 - currentBgColor.getBlue()));
			if (Math.abs((255 - currentBgColor.getRed()) - currentBgColor.getRed()) <= 10 && Math.abs((255 - currentBgColor.getGreen()) - currentBgColor.getGreen()) <= 10 && Math.abs((255 - currentBgColor.getBlue()) - currentBgColor.getBlue())<=10)
			{
				mouseCoordinates.setForeground(Color.WHITE);
			}
		}
	
		else if (e.getActionCommand().equals("Graph") && e.getSource() != graphIt)
		{
			Color currentGraphColor = this.model.getGraphColor();
			Color newColor = JColorChooser.showDialog(null,"Select graph color", currentGraphColor);
			if (newColor instanceof Color)
				this.model.setGraphColor(newColor);
		}
		
		else if (e.getActionCommand().equals("Grid"))
		{
			Color currentGridColor = this.model.getGridColor();
			Color newColor = JColorChooser.showDialog(null,"Select grid color", currentGridColor);
			if (newColor instanceof Color)
				this.model.setGridColor(newColor);
		}
		
		else if (e.getActionCommand().equals("Original"))
		{
			this.model.setBgColor(Color.BLACK);
			this.model.setGraphColor(Color.ORANGE);
			this.model.setGridColor(Color.WHITE);
			mouseCoordinates.setForeground(Color.WHITE);
		}
		
		else if (e.getActionCommand().equals("Black & White"))
		{
			this.model.setBgColor(Color.WHITE);
			this.model.setGraphColor(Color.BLACK);
			this.model.setGridColor(Color.GRAY);
			mouseCoordinates.setForeground(Color.BLACK);
		}
		
		else if (e.getActionCommand().equals("Show/Hide Coordinates"))
		{
			if (mouseCoordinates.isVisible() == true)
				mouseCoordinates.setVisible(false);
			else
				mouseCoordinates.setVisible(true);
		}
		
		else if (e.getActionCommand().equals("About"))
		{
			JOptionPane.showMessageDialog(graph, ABOUT, "About Equation Grapher", JOptionPane.PLAIN_MESSAGE);
		}
		
		else if (e.getActionCommand().equals("Instructions"))
		{
			JOptionPane.showMessageDialog(this, INSTRUCTIONS, "Instructions", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if (e.getActionCommand().equals("Close"))
		{
			System.exit(0);
		}
		
		else if (e.getActionCommand().equals("Start-up Preferences"))
		{
			new GrapherPreferences(this.model).setVisible(true);
		}
		
		else
		{
			graph.existsError = false;
			String characters = ((JTextField)e.getSource()).getText();
			int currentPosition;
			String newCharacters = "";
			 for (int i = 0 ; i <characters.length() ; i++) 
	            {
	                currentPosition = TextFieldController.INVALID_CHARACTERS.indexOf (characters.charAt (i));

	                if (currentPosition == -1)  
	                {
	                    newCharacters = newCharacters.concat(characters.substring(i, i+1));
	                }
	            }

			((JTextField)e.getSource()).setText(newCharacters);
		}
		graph.setBackground(this.model.getBgColor());
		graph.repaint();
	}
	
	private Double roundTwoDecimals (double d)
    {
        DecimalFormat twoDForm = new DecimalFormat ("#.##");
        return Double.valueOf (twoDForm.format (d));
    }
	
	public void update()
	{
		if (this.model.getUpdateEquationTypeFlag())
		{
			this.model.setUpdateEquationTypeFlag(false);
			
			resetTextFields();
	     	controlPanel.removeAll();
	     	basicToolLoader();
	     	
			int equationType = model.getCurrentEquationType();
			this.model.setScaleType(true);
			
			if (equationType == 0)
	        {
	        	loadPolynomialTools();
	        }
	        else if (equationType == 1)
	        {
	        	loadTrigonometricTools();
	        }
	        else if (equationType == 2)
	        {
	        	loadExponentialTools();
	        }
	        else if (equationType == 3)
	        {
	        	loadRationalTools();
	        }
	        else if (equationType == 4)
	        {
	        	loadCustomTools();
	        }
			
	        lbl_equation.setText("");
	        txt_slope.setText("");
	        lbl_slopeValue.setText("");
	        txt_aBounds.setText("");
	        txt_bBounds.setText("");
	        lbl_integralValue.setText("");
	        lbl_equation_n.setText("");
	        lbl_equation_underline.setText("");
	        lbl_equation_d.setText("");
	        controlPanel.repaint();
		}
		else if (this.model.getUpdateTrigTypeFlag())
		{
			this.model.setUpdateTrigTypeFlag(false);
		}
		else if (this.model.getUpdateScaleTypePiFlag())
		{
			this.model.setUpdateScaleTypePiFlag(false);
			
		}
		else if (this.model.getUpdateScaleTypeIntegerFlag())
		{
			this.model.setUpdateScaleTypeIntegerFlag(false);
			

		}
		else if (this.model.getUpdatePolyCoefficientsFlag())
		{
			this.model.setUpdatePolyCoefficientsFlag(false);
			
			Double[] currentCoefficients = this.model.getCoefficients();
			for (int x = 0; x < currentCoefficients.length; x++)
			{
				this.txt_coefficients[x].setText(currentCoefficients[x].toString());
			}
		}
		else if (this.model.getUpdateTransformationsFlag())
		{
			this.model.setUpdateTransformationsFlag(false);
			
			Double[] currentCoefficients = this.model.getTransformations();
			for (int x = 0; x < currentCoefficients.length; x++)
			{
				this.txt_transformations[x].setText(currentCoefficients[x].toString());
			}
			
		}
		else if (this.model.getUpdateColorFlag())
		{
			this.model.setUpdateColorFlag(false);
		}
		else if (this.model.getUpdateScaleFlag())
		{
			this.model.setUpdateScaleFlag(false);
			
			setXScale.setValue((Integer) this.model.getXUnit() / 10);
			setYScale.setValue((Integer) this.model.getYUnit() / 10);
		}
		else if (this.model.getUpdateComponentDimensionsFlag())
		{
			this.model.setUpdateComponentDimensionsFlag(false);
			
			graph.setBounds(0, 0, this.model.getGraphWidth(), this.model.getGraphHeight());
		    controlPanel.setBounds(0, this.model.getGraphHeight(), this.model.getGraphWidth(), 180);
		    graph.updateGraph(this.model.getGraphWidth(), this.model.getGraphHeight());
		}
		else if (this.model.getUpdateMouseCoordinatesFlag())
		{
			this.model.setUpdateMouseCoordinatesFlag(false);
			
			mouseCoordinates.setText("( " + roundTwoDecimals(this.model.getMouseCoordinateX()) +", " + (-roundTwoDecimals(this.model.getMouseCoordinateY()) == -0 ? 0: -roundTwoDecimals(this.model.getMouseCoordinateY())) + " )");
		}
		else if (this.model.getUpdateEquationTextFlag())
		{
			this.model.setUpdateEquationTextFlag(false);
			
			lbl_equation.setText(this.model.getEquationText());
		}
		else if (this.model.getUpdateRationalEquationFlag())
		{
			this.model.setUpdateRationalEquationFlag(false);
			
			lbl_equation_n.setText(this.model.getEquationNum());
			lbl_equation_underline.setText(this.model.getEquationUnderline());
			lbl_equation_d.setText(this.model.getEquationDen());
		}
		else if (this.model.getUpdateSlopeValueFlag())
		{
			this.model.setUpdateSlopeValueFlag(false);
			
			lbl_slopeValue.setText(" = " + this.model.getSlopeValue());
		}
		else if (this.model.getUpdateIntegralValueFlag())
		{
			this.model.setUpdateIntegralValueFlag(false);
			
			lbl_integralValue.setText(" = " + this.model.getIntegralValue());
		}
		graph.repaint();
	}
}
