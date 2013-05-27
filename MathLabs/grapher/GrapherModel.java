package physics.grapher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.prefs.Preferences;

public class GrapherModel {

	//The computer screen's dimensions
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//The view for the grapher program
	private GrapherGUI view;
	//The current equation type being used by the grapher
	private int currentEquationType;
	//The current trig equation type
	private int trigFunctionType;
	//The custom equation JTextField's text
	private String customEditorText;
	//Determines whether the graph has an integer or pi scale
	private boolean isIntegerScale;
	//The polynomial coefficients
	private Double[] polynomialCoefficients;
	//The transformation coefficients for trig and exponential equations
	private Double[] transformations;
	//The equation text for non-rational equations from the lbl_equation JLabel
	private String equationText;
	//The grapher's background color, graph color, and grid color
	private Color bgColor, graphColor, gridColor;
	//The grapher's preferences
	private Preferences prefs;
	//The JSpinner values for x scale and y scale (used to zoom in/out)
	private int xScaleValue, yScaleValue;
	/* The xGrid/yGrid values represents the Grid center or (0,0) on the graph
	 * The xUnit/yUnit values represent the length in px of 1 unit in the x/y directions
	 * The graphPercision value represents the length in px of each fine line the graph consists of 
     * A graphPercision of 1 has the finest precision while the largest value of 200 has the least.
    */
	int xGrid, yGrid, xUnit, yUnit, graphPrecision;
	//window width and height
	private int width, height;
	//graph width/height
	private int graphWidth, graphHeight;
	//The mouse coordinates according to the Grid
	private double mouseCoordinateX, mouseCoordinateY;
	//Rational equation numerator, denominator, and underline text
	private String equationNum, equationDen, equationUnderline;
	//The derivative value, must be initialized as null because zero is a legitimate value
	private Double slopeValue;
	//The point whose slope is to be found
	private Double slopePoint;
	//Integral bound values a to b
	private Double aBounds, bBounds;
	//Actual integral (physical area of graph) under a to b
	private Double integralValue;
	
	/* Update boolean flags are changed to true whenever the corresponding controller event
	 * method is run, prompting the update method in GUI to update only necessary elements.
	 * The flag is then reset to false after the update is acknowledged by the GUI.
	*/
	private boolean updateEquationTypeFlag;
	private boolean updateTrigTypeFlag;
	private boolean updateScaleTypePiFlag;
	private boolean updateScaleTypeIntegerFlag;
	private boolean updatePolyCoefficientsFlag;
	private boolean updateTransformationsFlag;
	private boolean updateEquationTextFlag;
	private boolean updateRationalEquationFlag;
	private boolean updateColorFlag;
	private boolean updateScaleFlag;
	private boolean updateComponentDimensionsFlag;
	private boolean updateMouseCoordinatesFlag;
	private boolean updateSlopeValueFlag;
	private boolean updateIntegralValueFlag;
	
	public GrapherModel()
	{
		super();
		this.currentEquationType = 0;
		this.trigFunctionType = 0;
		this.customEditorText = "";
		this.isIntegerScale = true;
		this.polynomialCoefficients = new Double[5];
		this.transformations = new Double[4];
		for (int x = 0; x < 5; x++)
		{
			polynomialCoefficients[x] = 0.0;
			if (x < 4)
			{
				if (x > 1)
					transformations[x] = 0.0;
				else
					transformations[x] = 1.0;
			}
		}
		this.equationText = "";
		this.equationNum = "";
		this.equationDen = "";
		this.equationUnderline = "";
		
		this.prefs = Preferences.userRoot().node(this.getClass().getName());
		this.bgColor = new Color(this.prefs.getInt("bgColor", Color.BLACK.getRGB()));
        this.gridColor = new Color(this.prefs.getInt("gridColor", Color.WHITE.getRGB()));
        this.graphColor = new Color(this.prefs.getInt("graphColor", Color.ORANGE.getRGB()));
        this.xScaleValue = this.prefs.getInt("ScaleX", 5);
        this.yScaleValue = this.prefs.getInt("ScaleY", 5);
        
        this.width = 800;
        this.height = screenSize.height - 100;
        this.graphWidth = this.width;
        this.graphHeight = this.height - 180;
        this.xGrid = this.graphWidth/2;
        this.yGrid = this.graphHeight/2;
        this.xUnit = 50;
        this.yUnit = 50;
        this.graphPrecision = 1;
        
        this.mouseCoordinateX = 0.0;
        this.mouseCoordinateY = 0.0;
        
        this.slopeValue = null;
        this.slopePoint = null;
        this.aBounds = null;
        this.bBounds = null;
        this.integralValue = null;
        
        this.updateEquationTypeFlag = false;
		this.updateTrigTypeFlag = false;
		this.updateScaleTypePiFlag = false;
		this.updateScaleTypeIntegerFlag = false;
		this.updatePolyCoefficientsFlag = false;
		this.updateTransformationsFlag = false;
		this.updateEquationTextFlag = false;
		this.updateRationalEquationFlag = false;
		this.updateColorFlag = false;
		this.updateScaleFlag = false;
		this.updateComponentDimensionsFlag = false;
		this.updateMouseCoordinatesFlag = false;
		this.updateSlopeValueFlag = false;
		this.updateIntegralValueFlag = false;
	}
	
	//Accessor and mutator methods
	
	public int getCurrentEquationType()
	{
		return this.currentEquationType;
	}
	
	public void setCurrentEquationType (int newEquationType)
	{
		this.currentEquationType = newEquationType;
		this.updateEquationTypeFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateEquationTypeFlag()
	{
		return this.updateEquationTypeFlag;
	}
	
	public void setUpdateEquationTypeFlag(boolean flag)
	{
		this.updateEquationTypeFlag = flag;
	}
	
	public boolean getUpdateScaleTypePiFlag()
	{
		return this.updateScaleTypePiFlag;
	}
	
	public void setUpdateScaleTypePiFlag(boolean flag)
	{
		this.updateScaleTypePiFlag = flag;
	}
	
	public boolean getUpdateScaleTypeIntegerFlag()
	{
		return this.updateScaleTypeIntegerFlag;
	}
	
	public void setUpdateScaleTypeIntegerFlag(boolean flag)
	{
		this.updateScaleTypeIntegerFlag = flag;
	}
	
	public boolean isIntegerScale()
	{
		return this.isIntegerScale;
	}
	
	public void setScaleType (boolean isIntegerScale)
	{
		this.isIntegerScale = isIntegerScale;
		if (isIntegerScale)
		{
			this.updateScaleTypeIntegerFlag = true;
		}
		else
		{
			this.updateScaleTypePiFlag = true;
		}
		this.updateView();
	}
	
	public int getTrigFunctionType()
	{
		return this.trigFunctionType;
	}
	
	public void setTrigFunctionType(int newTrigFunction)
	{
		this.trigFunctionType = newTrigFunction;
		this.updateTrigTypeFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateTrigTypeFlag()
	{
		return this.updateTrigTypeFlag;
	}
	
	public void setUpdateTrigTypeFlag(boolean flag)
	{
		this.updateTrigTypeFlag = flag;
	}
	
	public String getCustomEditorText()
	{
		return this.customEditorText;
	}
	
	public void setCustomEditorText(String newText) {
		this.customEditorText = newText;
	}
	
	public Double[] getCoefficients()
	{
		return this.polynomialCoefficients;
	}
	
	public void setCoefficients(Double[] newCoefficients)
	{
		this.polynomialCoefficients = newCoefficients;
		this.updatePolyCoefficientsFlag = true;
		this.updateView();
	}
	
	public boolean getUpdatePolyCoefficientsFlag()
	{
		return this.updatePolyCoefficientsFlag;
	}
	
	public void setUpdatePolyCoefficientsFlag(boolean flag)
	{
		this.updatePolyCoefficientsFlag = flag;
	}
	
	public Double[] getTransformations()
	{
		return this.transformations;
	}
	
	public void setTransformations(Double [] newTransformations)
	{
		this.transformations = newTransformations;
		this.updateTransformationsFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateTransformationsFlag()
	{
		return this.updateTransformationsFlag;
	}
	
	public void setUpdateTransformationsFlag(boolean flag)
	{
		this.updateTransformationsFlag = flag;
	}
	
	public String getEquationText()
	{
		return this.equationText;
	}
	
	public void setEquationText (String text)
	{
		this.equationText = text;
		this.updateEquationTextFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateEquationTextFlag()
	{
		return this.updateEquationTextFlag;
	}
	
	public void setUpdateEquationTextFlag(boolean flag)
	{
		this.updateEquationTextFlag = flag;
	}
	
	public String getEquationNum()
	{
		return this.equationNum;
	}
	
	public void setEquationNum(String text)
	{
		this.equationNum = text;
		this.updateRationalEquationFlag = true;
		this.updateView();
	}
	
	public String getEquationDen()
	{
		return this.equationDen;
	}
	
	public void setEquationDen(String text)
	{
		this.equationDen = text;
		this.updateRationalEquationFlag = true;
		this.updateView();
	}
	
	public String getEquationUnderline()
	{
		return this.equationUnderline;
	}
	
	public void setEquationUnderline (String text)
	{
		this.equationUnderline = text;
		this.updateRationalEquationFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateRationalEquationFlag()
	{
		return this.updateRationalEquationFlag;
	}
	
	public void setUpdateRationalEquationFlag(boolean flag)
	{
		this.updateRationalEquationFlag = flag;
	}
	
	public Color getBgColor()
	{
		return this.bgColor;
	}
	
	public void setBgColor(Color newBgColor)
	{
		this.bgColor = newBgColor;
		this.updateColorFlag = true;
		this.updateView();
	}
	
	public Color getGraphColor()
	{
		return this.graphColor;
	}
	
	public void setGraphColor(Color newGraphColor)
	{
		this.graphColor = newGraphColor;
		this.updateColorFlag = true;
		this.updateView();
	}
	
	public Color getGridColor()
	{
		return this.gridColor;
	}
	
	public void setGridColor(Color newGridColor)
	{
		this.gridColor = newGridColor;
		this.updateColorFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateColorFlag()
	{
		return this.updateColorFlag;
	}
	
	public void setUpdateColorFlag(boolean flag)
	{
		this.updateColorFlag = flag;
	}
	
	public int getXScaleValue()
	{
		return this.xScaleValue;
	}
	
	public void setXScaleValue(int newXScale)
	{
		this.xScaleValue = newXScale;
		this.updateScaleFlag = true;
		this.updateView();
	}
	
	public int getYScaleValue()
	{
		return this.yScaleValue;
	}
	
	public void setYScaleValue(int newYScale)
	{
		this.yScaleValue = newYScale;
		this.updateScaleFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateScaleFlag()
	{
		return this.updateScaleFlag;
	}
	
	public void setUpdateScaleFlag(boolean flag)
	{
		this.updateScaleFlag = flag;
	}
	
	public Preferences getPreferences()
	{
		return this.prefs;
	}
	
	public int getXGrid()
	{
		return this.xGrid;
	}
	
	public void setXGrid(int newXGrid)
	{
		this.xGrid = newXGrid;
		this.updateView();
	}
	
	public int getYGrid()
	{
		return this.yGrid;
	}
	
	public void setYGrid(int newYGrid)
	{
		this.yGrid = newYGrid;
		this.updateView();
	}
	
	public int getXUnit()
	{
		return this.xUnit;
	}
	
	public void setXUnit(int newXUnit)
	{
		this.xUnit = newXUnit;
		this.updateScaleFlag = true;
		this.updateView();
	}
	
	public int getYUnit()
	{
		return this.yUnit;
	}
	
	public void setYUnit(int newYUnit)
	{
		this.yUnit = newYUnit;
		this.updateScaleFlag = true;
		this.updateView();
	}
	
	public int getGraphPrecision()
	{
		return this.graphPrecision;
	}
	
	public void setGraphPrecision(int newGraphPrecision)
	{
		this.graphPrecision = newGraphPrecision;
		this.updateView();
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public void setWidth(int newWidth)
	{
		this.width = newWidth;
		this.updateComponentDimensionsFlag = true;
		this.updateView();
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void setHeight(int newHeight)
	{
		this.height = newHeight;
		this.updateComponentDimensionsFlag = true;
		this.updateView();
	}
	
	public int getGraphWidth()
	{
		return this.graphWidth;
	}
	
	public void setGraphWidth(int newGraphWidth)
	{
		this.graphWidth = newGraphWidth;
		this.updateComponentDimensionsFlag = true;
		this.updateView();
	}
	
	public int getGraphHeight()
	{
		return this.graphHeight;
	}
	
	public void setGraphHeight(int newGraphHeight)
	{
		this.graphHeight = newGraphHeight;
		this.updateComponentDimensionsFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateComponentDimensionsFlag()
	{
		return this.updateComponentDimensionsFlag;
	}
	
	public void setUpdateComponentDimensionsFlag(boolean flag)
	{
		this.updateComponentDimensionsFlag = flag;
	}
	
	public double getMouseCoordinateX()
	{
		return this.mouseCoordinateX;
	}
	
	public double getMouseCoordinateY()
	{
		return this.mouseCoordinateY;
	}
	
	public void setMouseCoordinates(double newX, double newY)
	{
		this.mouseCoordinateX = newX;
		this.mouseCoordinateY = newY;
		this.updateMouseCoordinatesFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateMouseCoordinatesFlag()
	{
		return this.updateMouseCoordinatesFlag;
	}
	
	public void setUpdateMouseCoordinatesFlag(boolean flag)
	{
		this.updateMouseCoordinatesFlag = flag;
	}
	
	public Double getSlopeValue()
	{
		return this.slopeValue;
	}
	
	public void setSlopeValue (Double newSlope)
	{
		this.slopeValue = newSlope;
		this.updateSlopeValueFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateSlopeValueFlag()
	{
		return this.updateSlopeValueFlag;
	}
	
	public void setUpdateSlopeValueFlag (boolean flag)
	{
		this.updateSlopeValueFlag = flag;
	}
	
	public Double getSlopePoint ()
	{
		return this.slopePoint;
	}
	
	public void setSlopePoint(Double newPoint)
	{
		this.slopePoint = newPoint;
	}
	
	public Double getABounds()
	{
		return this.aBounds;
	}
	
	public Double getBBounds()
	{
		return this.bBounds;
	}
	
	public void setBounds(Double aBounds, Double bBounds)
	{
		this.aBounds = aBounds;
		this.bBounds = bBounds;
	}
	
	public Double getIntegralValue()
	{
		return this.integralValue;
	}
	
	public void setIntegralValue(Double newIntegralValue)
	{
		this.integralValue = newIntegralValue;
		this.updateIntegralValueFlag = true;
		this.updateView();
	}
	
	public boolean getUpdateIntegralValueFlag()
	{
		return this.updateIntegralValueFlag;
	}
	
	public void setUpdateIntegralValueFlag(boolean flag)
	{
		this.updateIntegralValueFlag = flag;
	}
	
	/**  Updates the view in the GUI*/
	public void updateView()
	{
	  view.update();
	}
	
	/** Sets the view for the game
    * @param currentGUI        The View used to display the game
    */ 
	public void setGUI(GrapherGUI currentGUI)
	{
		this.view = currentGUI;
	}
}
