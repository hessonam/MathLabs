package physics.grapher;

import java.awt.*;

import physics.grapher.util.*;

public class Graph extends Grid
{
	//int width, height;
	int xGrid, yGrid, xUnit, yUnit, graphPrecision;
	int y1, y2;
	String sign[] = new String[5];
	Integer power[] = new Integer[5];
	Double coefficients[];
	boolean isOne = false, isNegativeOne = false;
	boolean existsError = false;
	double exponentialBase;
	Double transformations[] = new Double[4];
	Double coefficients_n[] = new Double[4];
	Double coefficients_d[] = new Double[4];
	int[] holes = new int[3];
	int numHoles = 0;
	int numCharacters = 0;
	double xSlope= Double.POSITIVE_INFINITY;
	double aBounds, bBounds;
	private GrapherModel model;
	
	public Graph(GrapherModel model)
	{
		super(model);
		this.model = model;
	}
	
	public void paintComponent (Graphics g0)
	{
		super.paintComponent(g0);
		Graphics2D g = (Graphics2D)g0;
		if (g0 instanceof Graphics2D)
		{		
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		this.xGrid = super.xGrid;
		this.yGrid = super.yGrid;
		this.xUnit = super.xUnit;
		this.yUnit = super.yUnit;
		this.graphPrecision = super.graphPrecision;
		int equationType = model.getCurrentEquationType();
		
		if (equationType == 0)
		{
			coefficients = model.getCoefficients();
		}
		else if (equationType == 1 || equationType == 2)
			for (int i =0; i<4; i++) {
				try {
					transformations[i] = new Double(Double.parseDouble(GrapherGUI.txt_transformations[i].getText()));
				} catch (Exception e) {
					transformations[i] = 0D;
				}
			}
		else if (equationType == 3)
			for (int i =0; i<4;i++)
			{
				coefficients_n[i] = new Double(GrapherGUI.txt_coefficients_n[i].getText());
				coefficients_d[i] = new Double(GrapherGUI.txt_coefficients_d[i].getText());
			}
		exponentialBase = Double.parseDouble(GrapherGUI.txt_exponentialBase.getText());
		
		Double slopePoint = this.model.getSlopePoint();
		if(slopePoint != null)
			xSlope = slopePoint;
		if(this.model.getABounds() != null)
			aBounds = model.getABounds();
		if(this.model.getBBounds() != null)
			bBounds = model.getBBounds();
		
		if(equationType == 0)
		{
			
			if(!(coefficients[0] == 0 && coefficients[1] == 0 && coefficients[2] == 0 && coefficients[3] == 0 && coefficients[4] == 0))
			{
				for (int x=xGrid; x<width; x+=graphPrecision)
				{		
					y1 = Calculate.polynomial(x,coefficients, true, xGrid, xUnit, yUnit, graphPrecision);					
					y2 = Calculate.polynomial(x,coefficients, false, xGrid, xUnit, yUnit, graphPrecision);
					g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
				for (int x=xGrid; x>0; x-=graphPrecision)
				{	
					y1 = Calculate.polynomial(x,coefficients, true, xGrid, xUnit, yUnit, graphPrecision);					
					y2= Calculate.polynomial(x,coefficients, false, xGrid, xUnit, yUnit, graphPrecision);
					g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
				
				if (slopePoint != null)
					this.model.setSlopeValue(Calculate.polynomialDerivative(xSlope,coefficients,5));
				if (this.model.getABounds() != null && this.model.getBBounds() != null)
					this.model.setIntegralValue(Calculate.polynomialIntegral(aBounds, bBounds, coefficients));
				
				//Equation writer
				this.model.setEquationText("<html>f(x) = " + polynomialEquationWriter(coefficients, 5) + "</html>");
				
			}//end if(!(coefficient_a == 0 && coefficient_b == 0 && coefficient_c == 0 && coefficient_d == 0 && coefficient_e == 0))
			else
			{
				this.model.setEquationText("");
			}
		}//end if(GrapherGUI.currentEquationType == 0)
		
		else if (equationType == 1)
		{
			int trigFunction = model.getTrigFunctionType();
				for (int x=xGrid; x<width; x+=graphPrecision)
				{		
					y1 = (int)(Calculate.trigonometric(x,transformations,true, trigFunction, false, xGrid, xUnit, yUnit, graphPrecision));
					y2 = (int)(Calculate.trigonometric(x,transformations,false, trigFunction, false, xGrid, xUnit, yUnit, graphPrecision));
					if (Math.abs(y2-y1)<=1000)
						g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
				for (int x=xGrid; x>0; x-=graphPrecision)
				{	
					y1 = (int)(Calculate.trigonometric(x,transformations,true, trigFunction, false, xGrid, xUnit, yUnit, graphPrecision));
					y2 = (int)(Calculate.trigonometric(x,transformations,false, trigFunction, false, xGrid, xUnit, yUnit, graphPrecision));
					if (Math.abs(y2-y1)<=1000)
							g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
				if (slopePoint != null)
					this.model.setSlopeValue((Calculate.trigonometricDerivative(xSlope, transformations, xGrid, xUnit, yUnit, trigFunction)));
				
				this.model.setEquationText("<html>f(x) = " + 
						(transformations[0] == 1 ? "":(transformations[0] % 1 == 0 ? (decimalToInt(transformations[0]) == -1 ? removeOne(transformations[0]): decimalToInt(transformations[0])) + " ( ":transformations[0] + "( ")) + removeLast3Digits(GrapherGUI.TRIG_FUNCTIONS[trigFunction])+ " " + 
						(transformations[1] == 1 ? "":(transformations[1] % 1 == 0 ? (decimalToInt(transformations[1]) == -1 ? removeOne(transformations[1])+" ( ": decimalToInt(transformations[1])+ " ( "):transformations[1]+ " ( "))  + "θ " +
						(transformations[2] == 0 ? " )":(transformations[2] > 0 ? " -" + (transformations[2] % 1 == 0 ? decimalToInt(transformations[2])+"":transformations[2]) + " )": " + " + (transformations[2] % 1 == 0 ? Math.abs(decimalToInt(transformations[2]))+" )":Math.abs(transformations[2])+" )"))) + (transformations[1] != 1 ? " )":"") +
						(transformations[0] == 1 ? "":" )") +(transformations[3] == 0 ? "":(transformations[3]>0 ? " + " + (transformations[3] % 1 == 0 ? decimalToInt(transformations[3])+"":transformations[3]):" " + (transformations[3] % 1 == 0 ? decimalToInt(transformations[3])+"":transformations[3])))+ "</html>" );
				
		}//end if (GrapherGUI.currentEquationType == 1)
		
		else if (equationType == 2)
		{
			if (exponentialBase !=0)
			{
				for (int x=xGrid; x<width; x+=graphPrecision)
				{		
					y1 = Calculate.exponential(x,exponentialBase,transformations,true, xGrid, xUnit, yUnit, graphPrecision);
					y2 = Calculate.exponential(x,exponentialBase,transformations,false, xGrid, xUnit, yUnit, graphPrecision);
						g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
				for (int x=xGrid; x>0; x-=graphPrecision)
				{	
					y1 = Calculate.exponential(x,exponentialBase,transformations,true, xGrid, xUnit, yUnit, graphPrecision);
					y2 = Calculate.exponential(x,exponentialBase,transformations,false, xGrid, xUnit, yUnit, graphPrecision);
					g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);	
				}
				if (slopePoint != null)
					this.model.setSlopeValue((Calculate.exponentialDerivative(xSlope,exponentialBase, transformations)));
				
				this.model.setEquationText("<html>f(x) = " + 
						(transformations[0] == 1 ? "":(transformations[0] % 1 == 0 ? (decimalToInt(transformations[0]) == -1 ? removeOne(transformations[0]): decimalToInt(transformations[0])) + " ( ":transformations[0] + "( ")) + (exponentialBase % 1 ==0 ? decimalToInt(exponentialBase) + "" :exponentialBase) + 
						"<sup>" + (transformations[1] == 1 ? "":(transformations[1] % 1 == 0 ? (decimalToInt(transformations[1]) == -1 ? removeOne(transformations[1]): decimalToInt(transformations[1])+""):transformations[1])) + " (x" + 
						(transformations[2] == 0 ? ")":(transformations[2] > 0 ? " -" + (transformations[2] % 1 == 0 ? decimalToInt(transformations[2])+"":transformations[2]) + ")": " + " + (transformations[2] % 1 == 0 ? Math.abs(decimalToInt(transformations[2]))+" )":Math.abs(transformations[2])+" )"))) + "</sup>"+
						(transformations[0] == 1 ? "":" )") + (transformations[3] == 0 ? "":(transformations[3]>0 ? " + " + (transformations[3] % 1 == 0 ? decimalToInt(transformations[3])+"":transformations[3]):" " + (transformations[3] % 1 == 0 ? decimalToInt(transformations[3])+"":transformations[3]))) + "</html>" );
			}
			else
			{
				this.model.setEquationText("");
			}
		}//end if (GrapherGUI.currentEquationType == 2)
		else if (equationType == 3)
		{
			if (coefficients_d[0] != 0 || coefficients_d[1] != 0 || coefficients_d[2] != 0 || coefficients_d[3] != 0 ) {
				for (int i =0; i<3; i++)
					holes[i] = (int)(Double.POSITIVE_INFINITY);
			
				for (int x=0; x<=width; x++)
					holeCheck(x, coefficients_d);
			}
			for (int x=xGrid; x<width; x+=graphPrecision)
			{
				//System.out.println(coefficients_d[3]);
				try{
					y1 = Calculate.rational(x, coefficients_n, coefficients_d, true, xGrid, xUnit, yUnit, graphPrecision);
					y2 = Calculate.rational(x, coefficients_n, coefficients_d, false, xGrid, xUnit, yUnit, graphPrecision);
				}
				catch(NullPointerException npe)
				{
					existsError = true;
					//npe.printStackTrace();
				}
				if (Math.abs(y2 -y1)<= 1000 && rationalGraphCondition(x)) {
					//System.out.println("Printing that shit.");
					g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
				}
			}
			for (int x=xGrid-1; x>0; x-=graphPrecision)
			{
				try{
					y1 = Calculate.rational(x,coefficients_n,coefficients_d, true, xGrid, xUnit, yUnit, graphPrecision);					
					y2 = Calculate.rational(x,coefficients_n, coefficients_d, false, xGrid, xUnit, yUnit, graphPrecision);
				}
				catch (NullPointerException npe){
					existsError = true;
				}
				if (Math.abs(y2 -y1)<= 1000 && rationalGraphCondition(x))
					g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
			}
			for (int i =0; i<3; i++)
			{
				if(i<numHoles)
				{
					g.setColor(this.model.getBgColor());
					g.fillOval(holes[i] -4,yGrid - Calculate.rational(holes[i]-1, coefficients_n, coefficients_d,true, xGrid, xUnit, yUnit, graphPrecision)-4,8,8 );
					g.setColor(this.model.getGraphColor());
					g.drawOval(holes[i] -4, yGrid - Calculate.rational(holes[i]-1, coefficients_n, coefficients_d,true, xGrid, xUnit, yUnit, graphPrecision)-4,8,8 );
				}
			}
			
			numCharacters = 0;
			this.model.setEquationUnderline("");
			
			if (this.model.getEquationNum().trim().length() >= this.model.getEquationDen().trim().length())
			{
				for (int i = 3; i >= 0; i--)
				{
					if (coefficients_n[i] != 0)
					{
						if (i != 0)
							numCharacters += 3;
						if(coefficients_n[i] != 1)
						{
							numCharacters += numberOfDigits(coefficients_n[i]);	
						}
						if(coefficients_n[i]!= -1)
							numCharacters++;
						else
							numCharacters--;
					}
				}
			}	
			else
			{
				for (int i = 3 ; i >= 0; i--)
				{
					if (coefficients_d[i] != 0)
					{
						if (i!= 0)
							numCharacters += 3;
						if(coefficients_d[i] != 1)
						{
							numCharacters += numberOfDigits(coefficients_d[i]);
						}	
						if(coefficients_n[i]!= -1)
							numCharacters++;
						else
							numCharacters--;
					}
				}
			}
			if (numCharacters > 20)
				numCharacters = 20;
			for (int i = 0; i < numCharacters; i++)
				this.model.setEquationUnderline(this.model.getEquationUnderline() + "_");		
			
			resetRational();
			this.model.setEquationText("f(x) =");
			this.model.setEquationNum("<html>" + polynomialEquationWriter(coefficients_n, 4) + "</html>");
			this.model.setEquationDen("<html>" + polynomialEquationWriter(coefficients_d, 4) + "</html>");
						
		}//end if (GrapherGUI.currentEquationType == 3)
		
		else if (equationType == 4)
		{
			for (int i =0; i<3; i++)
				holes[i] = (int)(Double.POSITIVE_INFINITY);
			if (!existsError)
			{
				String function = model.getCustomEditorText();
				String deno = function.substring(function.indexOf("/")+1);
				if (function.trim().length() != 0)
				{
					for (int x=xGrid; x<width; x+=graphPrecision)
					{
						try{
							if(f(x,true)!= 9999){
								y1 = f(x, true);					
								y2 = f(x, false);
								g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
							}
						}
						catch(Exception e)
						{
							existsError = true;
						}
					}
					for (int x=xGrid; x>0; x-=graphPrecision)
					{	
						try{
							if(f(x,true)!= 9999)
							{
								y1 = f(x, true);					
								y2 = f(x, false);
								g.drawLine(x,yGrid - y1,x+graphPrecision,yGrid - y2);
							}
						}
						catch(Exception e)
						{
							existsError = true;
							e.printStackTrace();
						}
					}
				}
				for (int i =0; i<3; i++)
				{
					if(i<numHoles)
					{
						g.setColor(this.model.getBgColor());
						g.fillOval(holes[i] -4,yGrid - Calculate.rational(holes[i]-1, coefficients_n, coefficients_d,true, xGrid, xUnit, yUnit, graphPrecision)-4,8,8 );
						g.setColor(this.model.getGraphColor());
						g.drawOval(holes[i] -4,yGrid - Calculate.rational(holes[i]-1, coefficients_n, coefficients_d,true, xGrid, xUnit, yUnit, graphPrecision)-4,8,8 );
					}
				}
			}
		}
	}
	
	public Integer f(int x, boolean isFirstPoint)
	{
		String function = model.getCustomEditorText();
		double yVal;
		int int_yVal;
		if (isFirstPoint) 
			yVal = x - xGrid;
		else
			yVal = x + graphPrecision - xGrid;
		yVal = yVal/xUnit;
		try{
			if (Double.isNaN(Parser.mparse(function.replace("x", ""+yVal))) || Parser.mparse(function.replace("x", ""+yVal)) == Double.POSITIVE_INFINITY || Parser.mparse(function.replace("x", ""+yVal)) == Double.NEGATIVE_INFINITY)
				return null;
			yVal = Parser.mparse(function.replace("x", ""+yVal));
		}
		catch(Exception npe)
		{
			return null;
		}
		yVal *= yUnit;
		int_yVal = (int)Math.round(yVal);
		return int_yVal;
	}
	
	public void holeCheck(int x, Double[] d)
	{
		float yVal;
		float denominator;
		boolean alreadyExists = false;
		yVal = x - xGrid;
		yVal /=xUnit;		
		denominator = (float) (d[3] * Math.pow(yVal,3) + d[2] * Math.pow(yVal,2) + (d[1] * yVal)  + d[0]);
		if (denominator == 0 )
		{
			if(isHole(x, d)==true)
			{
				for (int i=0; i<3; i++)
				{
					if (holes[i]==x)
						alreadyExists = true;
				}
				if (!(alreadyExists) && numHoles <4)
				{
				numHoles++;
				holes[numHoles-1]=x;
				}
			}
			else
				yVal = Float.POSITIVE_INFINITY;
		}
		else
			return;
	}
	
	public boolean isHole(int x, Double[] d)
	{
		double y1, y2;
		y1 = Calculate.rational(x-1, coefficients_n, coefficients_d, true, xGrid, xUnit, yUnit, graphPrecision);
		y2 = Calculate.rational(x+1, coefficients_n, coefficients_d, true, xGrid, xUnit, yUnit, graphPrecision);
		
		if(Math.abs(y2 - y1) >= 100)
		{
			return false;
		}
			return true;
	}
	
	public void resetRational()
	{
		numHoles =0;
		holes = new int[3];
		for (int i =0; i<3; i++)
			holes[i] = (int)(Double.POSITIVE_INFINITY);
	}
	
	public int decimalToInt(double value)
	{
		int retVal;		
		retVal = Integer.parseInt(Double.toString(value).substring(0,Double.toString(value).length() -2));
		return retVal;
	}
	
	
	public String removeOne (double value)
	{
		String retVal;
		retVal = Double.toString(value).substring(0,Double.toString(value).length() -3 );
		return retVal;
	}
	
	public String removeLast3Digits (Object string)
	{
		String retVal;
		retVal = string.toString().substring(0, string.toString().length() -3);
		return retVal;
	}
	
	public String polynomialEquationWriter(Double[] coefficients, int numOfCoefficients)
	{
		
		String retVal = "";
		for (int i = numOfCoefficients-1; i>=0;i--)
		{		
			if (coefficients[i] != 0)
			{
				if (coefficients[i] > 0 && !isFirstNonZeroCoefficient(coefficients, i))
				{
					retVal += " + ";
				}
				else if (coefficients[i] < 0)
				{
					retVal += " - ";
				}
				
				if ((coefficients[i] != 1 && coefficients[i] != -1) || i == 0)
				{
					if (coefficients[i] % 1 == 0.0)
						retVal += (int)(double)Math.abs(coefficients[i]);
					else
						retVal += Math.abs(coefficients[i]);
				}
				if (i != 0)
				{
					retVal += "x";
				}
				if (i != 0 && i!= 1)
				{
					retVal += "<sup>"+i+"</sup>";
				}
			}
		}
		return retVal;
	}
	

	
	private boolean isFirstNonZeroCoefficient(Double[] coefficients, int i)
	{
		for (int x = coefficients.length - 1; x > i; x--)
		{
			if (coefficients[x] != 0)
				return false;
		}
		return true;
	}

	public boolean rationalGraphCondition(int x)
	{	
			for(int i =0; i<3;i++)
			{
				if (Math.abs(x - holes[i]) <= 1 )
					return false;
			}
			return true;
	}
	
	public int numberOfDigits(double number)
	{
		int new_number;
		if(number % 1 == 0)
		{
			new_number = (int)(number);
			new_number = Integer.toString(new_number).trim().length();
		}
		else
			new_number = Double.toString(number).trim().length();
		return (int)(new_number);
	}
	
	public void updateGraph(int graphWidth, int graphHeight)
	{
		this.width = graphWidth;
		this.height = graphHeight;
		updateGrid(graphWidth, graphHeight);
	}
}
