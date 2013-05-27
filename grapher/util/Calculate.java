package physics.grapher.util;

final public class Calculate
{

	/**
	 * Prevents this class from being instantiated.
	 */
	private Calculate(){		
	}
	
	public static int polynomial(int x, Double[] coefficients, boolean isFirstPoint, int xGrid, int xUnit, int yUnit, int graphPercision)
	{	
		double yVal;
		int int_yVal;
		if (isFirstPoint) 
			yVal = x - xGrid;
		else
			yVal = x + graphPercision - xGrid;
		yVal = yVal/xUnit;
		yVal = coefficients[4] * Math.pow(yVal,4) + coefficients[3] * Math.pow(yVal,3) + coefficients[2] * Math.pow(yVal,2)  + (coefficients[1] * yVal) + coefficients[0];		
		yVal *= yUnit;
		int_yVal = (int)Math.round(yVal);
		return int_yVal;
	}//end polynomial
	
	public static double polynomialDerivative(double x, Double[] coefficients, int numCoefficients)
	{
		double slope;
		Double [] newCoefficients = new Double[numCoefficients];
		for (int i=0; i<numCoefficients; i++)
			newCoefficients[i] = coefficients[i] * i;
		slope = newCoefficients[4]*Math.pow(x,3) + newCoefficients[3]*Math.pow(x, 2)+ (newCoefficients[2] * x) + newCoefficients[1];		
		return slope;
	}
	
	public static double polynomialIntegral (double a, double b, Double[] coefficients)
	{
		double integral_a = 0, integral_b = 0;
		for(int i =4; i>=0; i--)
		{
			integral_b = integral_b + ((coefficients[i] * Math.pow(b, i+1))/(i+1));
			integral_a = integral_a + ((coefficients[i] * Math.pow(a, i+1))/(i+1));
		}
		return integral_b - integral_a;
	}
	
	public static double trigonometric (double x,Double[] transformations, boolean isFirstPoint, int trig, boolean findingDerivative, int xGrid, int xUnit, int yUnit, int graphPercision)
	{
		double yVal;
		Double[] newTransformations =new Double[4];
		for(int i =0;i<4; i++)
			newTransformations[i] = transformations[i];
		int int_yVal;
		if (isFirstPoint) 
			yVal = x - xGrid;
		else
			yVal = x + graphPercision - xGrid;
		yVal = yVal/xUnit;
		if(findingDerivative)
		{
			newTransformations[0]= newTransformations[0] * newTransformations[1];
			newTransformations[3]=0.0;
		}
		if (trig == 0)
		{
			yVal = newTransformations[0]*Math.sin(newTransformations[1]*(yVal-newTransformations[2]))+newTransformations[3];
		}
		else if (trig == 1)
		{
			yVal = newTransformations[0]*Math.cos(newTransformations[1]*(yVal-newTransformations[2]))+newTransformations[3]; 
		}
		else if (trig == 2)
			yVal = newTransformations[0]*Math.tan(newTransformations[1]*(yVal-newTransformations[2]))+newTransformations[3]; 
		else if (trig == 3)
			yVal = newTransformations[0]*Math.pow(Math.sin(newTransformations[1]*(yVal-newTransformations[2])), -1) +newTransformations[3];
		else if (trig == 4)
			yVal = newTransformations[0]*Math.pow(Math.cos(newTransformations[1]*(yVal-newTransformations[2])), -1)+newTransformations[3];
		else if (trig == 5)
			yVal = newTransformations[0]*Math.pow(Math.tan(newTransformations[1]*(yVal-newTransformations[2])), -1)+newTransformations[3];
		yVal *= yUnit;
		int_yVal = (int)Math.round(yVal);
		if(findingDerivative)
			return yVal;
		else
			return int_yVal;
	}
	
	public static double trigonometricDerivative(double x, Double transformations[], int xGrid, int xUnit, int yUnit, int trigFunction)
	{
		double slope = 0;
		if(x>=0)
			x= xGrid + x*xUnit;
		else
			x= xGrid - x*xUnit;
			
		if (trigFunction == 0)
			slope = trigonometric(x, transformations, true ,1, true, xGrid, xUnit, yUnit, 0);
		else if (trigFunction == 1)
			slope = -trigonometric(x, transformations, true ,0, true, xGrid, xUnit, yUnit, 0);
		else if (trigFunction == 2)
			slope = Math.pow(trigonometric(x, transformations, true ,4, true, xGrid, xUnit, yUnit, 0),2);
		else if (trigFunction == 3)
			slope = - trigonometric(x, transformations, true ,3, true, xGrid, xUnit, yUnit, 0) * trigonometric(x, transformations, true ,5, true, xGrid, xUnit, yUnit, 0);
		else if (trigFunction == 4)
			slope = trigonometric(x, transformations, true ,2, true, xGrid, xUnit, yUnit, 0) * trigonometric(x, transformations, true ,4, true, xGrid, xUnit, yUnit, 0);
		else if (trigFunction == 5)
			slope =- Math.pow(trigonometric(x, transformations, true ,3, true, xGrid, xUnit, yUnit, 0),2);
		
		slope/= yUnit;
		if(trigFunction>1)
			slope/= yUnit;
		
		if (slope == -0)
			slope = 0;
		
		return slope;
	}
	
	public static int exponential (int x, double exponentialBase, Double[] transformations, boolean isFirstPoint, int xGrid, int xUnit, int yUnit, int graphPercision)
	{
		double yVal;
		int int_yVal;
		if (isFirstPoint) 
			yVal = x - xGrid;
		else
			yVal = x + graphPercision - xGrid;
		yVal = yVal/xUnit;
		yVal = transformations[0]*Math.pow(Math.abs(exponentialBase),transformations[1]*(yVal - transformations[2])) + transformations[3];
		yVal *= yUnit;
		int_yVal = (int)Math.round(yVal);
		return int_yVal;
	}
	
	public static double exponentialDerivative(double x, double b, Double[] transformations)
	{		
		return Math.pow(b,(x - transformations[2]))*Math.log(b)*transformations[1] * transformations[0];
	}
	
	public static Integer rational(int x, Double[] n, Double[] d, boolean isFirstPoint, int xGrid, int xUnit, int yUnit, int graphPercision)
	{
		double yVal;
		double numerator, denominator;
		int int_yVal=0;
		if (isFirstPoint) 
			yVal = x - xGrid;
		else
			yVal = x + graphPercision - xGrid;
		yVal /=xUnit;		
		numerator =   (n[3] * Math.pow(yVal,3) + n[2] * Math.pow( yVal,2) + (n[1] * yVal)  + n[0]);
		denominator = (d[3] * Math.pow(yVal,3) + d[2] * Math.pow( yVal,2) + (d[1] * yVal)  + d[0]);
		if (denominator == 0) return null;
		yVal = numerator/denominator;
		if (Double.isNaN(numerator/denominator) || ((numerator/denominator) == Double.POSITIVE_INFINITY) || ((numerator/denominator) == Double.NEGATIVE_INFINITY))
			return null;
		yVal *= yUnit;
		int_yVal = (int)Math.round(yVal);
		return int_yVal;
	}
}
