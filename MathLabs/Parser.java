package physics;

import java.util.Vector;

/**
 * Class Parser translates a String representing a mathematical expression into a real number.
 * Written: 2010/2011
 * Last Modified: 2012 
 * 
 * @author 		Amer Hesson
 * @since 		1.0
 * @version 	2.0
 */

public final class Parser {
	
	private final static int NO_ERROR = 0, SYNTAX_ERROR = 1;
	private final static String NUMBERS = "0123456789";
	private final static String OPERATORS = "!^/*+-";
	private final static String INVALID  = "@#$%&_~`|:\'\"";
	
	 /**
	 * Prevents this class from being instantiated.
	 */
	private Parser() {
	}
	
	/**
	 * Parses a string that comprises a mathematical expression. A real number representing the evaluated expression is returned.
	 * 
	 * @param expression 	expression to be evaluated. <code>mparse</code> will evaluate
	 * the expression based on order of operations. Multiple operations and parentheses are acceptable.
	 * <p>
	 * Order of evaluation:
	 * <ul>
	 * <li> Any operations enclosed within parentheses will be evaluated first. </li>
	 * <li> x !   : The factorial operator returns the product of every positive integer that is less than or equal to <i>x</i>.</li>
	 * <li> x ^ y : The power operator returns the value of <i>x</i> raised to the <i>y</i><sup>th</sup> power.</li>
	 * <li> x / y : The division operator returns the value of <i>x</i> divided by <i>y</i>.</li>
	 * <li> x * y : The multiplication operator returns the product of <i>x</i> and <i>y</i>.</li>
	 * <li> x + y : The addition operator returns the sum of <i>x</i> and <i>y</i>.</li>
	 * <li> x - y : The subtraction operator returns the value of <i>y</i> subtracted from <i>x</i>.</li>
	 * </ul>
	 * where <i>x</i> and <i>y</i> are real numbers.
	 * </p>
	 * 
	 * @return 		a real number representing the evaluated expression. For example, the input String, "(1+2)^3 * 3!/2", will return 81.0.
	 */
	public static Double mparse(String expression)
	{
		//Stores the number of open and close parentheses in the expression.
		int numOpenParentheses = 0, numCloseParentheses = 0;
		//Stores the indices of the subexpression between the deepest open and close parentheses.
		int openPosition, closePosition;
		//
		int openIndex = 0, counter = 0;
		/*
		Stores the depth level of the first sub-expression to be evaluated. The first open
		parenthesis is considered one level deep and every open parenthesis within it is an
		extra level deep.
		*/
		int depthLevel = 0;
		//Stores the expression within the deepest parentheses of the expression.
		String subExpression= "";
		//Counts the number of open and close brackets within an expression. Also determines
		//the depth level.
		for (int i=0; i<expression.length(); i++)
		{
			if (expression.charAt(i) == '(')
			{
				numOpenParentheses++;
				depthLevel = Math.max(depthLevel, numOpenParentheses-numCloseParentheses);
			}
			else if (expression.charAt(i)== ')')
				numCloseParentheses++;
		}
		//Uses the depth level to determine the opening index of the sub-expression
		for (int i=0; i<expression.length();i++)
		{
			if (expression.charAt(i) == '(')
				counter++;
			if (counter == depthLevel)
			{
				openIndex = i;
				break;
			}
		}
		openPosition = expression.indexOf('(', openIndex);
		closePosition = expression.indexOf(')', openPosition);
		//Syntax error if the number of opening parentheses does not match the number of closing parentheses. 
		if (numOpenParentheses != numCloseParentheses)
		{
			error(SYNTAX_ERROR);
			return null;
		}
		//If parentheses exist, creates a sub-expression of the expression within the parentheses.
		if (openPosition != -1)
		{
			subExpression = expression.substring(openPosition+1, closePosition);
			subExpression = new String(vectorToString(simplify(separate(subExpression))));
		}
		//Otherwise, the sub-expression is the expression itself.
		else
			subExpression = expression;
		//Prepares a Vector to be inputed in the bedmas() method.
		Vector<String> input = simplify(separate(subExpression));
		//Simplifies the sub-expression one operation at a time, according to BEDMAS.
		for (int i = 0; i<OPERATORS.length()-1; i++)
			subExpression = bedmas(OPERATORS.substring(i,i+1), input);
		//Uses recursion to repeat the process if necessary, until only a real number is left.
		if (numOpenParentheses > 0)
			return mparse(expression.substring(0, openPosition) 
					+ subExpression + expression.substring(closePosition+1, expression.length()));
		else
			return sparse(subExpression);
	}
	
	/*
	 * Parameters: expression - expression to be evaluated. The expression expected must be 
	 * exactly two real numbers separated by some defined operation.
	 * Returns: a real number representing the evaluated expression.
	 */
	private static double sparse(String expression){
		
		Vector<String> separated = separate(expression);
		
		separated = new Vector<String>(simplify(separated));
		
		if (separated.size() == 1 && isNumber(separated.get(0)))
			return Double.parseDouble(separated.get(0));
		//System.out.println(separated);
		
		if (separated.get(0).equals( "NaN") || separated.get(0).equals( "Infinity"))
			return Double.POSITIVE_INFINITY;
		
		//identify operation
		if (separated.get(1).equals("+"))
			return Double.parseDouble(separated.get(0)) + Double.parseDouble(separated.get(2));
		else if (separated.get(1).equals("-"))
			return Double.parseDouble(separated.get(0)) - Double.parseDouble(separated.get(2));
		else if (separated.get(1).equals("*"))
			return Double.parseDouble(separated.get(0)) * Double.parseDouble(separated.get(2));
		else if (separated.get(1).equals("/"))
		{
			if (Double.parseDouble(separated.get(2))!= 0)
				return Double.parseDouble(separated.get(0)) / Double.parseDouble(separated.get(2));
			else
				return Double.POSITIVE_INFINITY;
		}
		else if (separated.get(1).equals("^"))
			return Math.pow(Double.parseDouble(separated.get(0)), 
						Double.parseDouble(separated.get(2)));
		else
			return 0;
	}
	
	/*
	 * Parameters: 
	 * expression - expression to be separated into tokens. Operations and spaces are considered delimiters.
	 * Returns: 
	 * a Vector is returned with the expression separated for better analysis.
	 */
	private static Vector<String> separate(String expression)
	{
		Vector<String> finalExp = new Vector<String>();
		String newExp = "";
		
		for (int i = 0; i < expression.length(); i++)
		{
			if (i - 1 >= 0 && OPERATORS.indexOf(expression.charAt(i)) != -1 
					&& expression.charAt(i-1)!= 'E')
			{
				newExp = newExp + " " + expression.charAt(i) + " ";
			}
			else
			{
				newExp += expression.charAt(i);
			}
		}
		String[] temp = newExp.split(" ");
		for (String s : temp) {
			if (s.trim().length() != 0)
				finalExp.add(s.trim());
		}
		return finalExp;
	}
	
	/*
	 * Parameters: 
	 * expression - a Vector expression that has passed through the separate() method ready 
	 * to be simplified.
	 * Returns: 
	 * a simplified Vector ready to be evaluated.
	 */
	private static Vector<String> simplify(Vector<String> expression)
	{
		for (int i = 0; i<expression.size(); i++)
		{
			if (i + 1 < expression.size() && (expression.get(i).equals("+") 
					&& expression.get(i+1).equals("+")) || (expression.get(i).equals("-") 
					&& expression.get(i+1).equals("-")))
			{
				expression.removeElementAt(i+1);
				expression.setElementAt("+", i);
				i--;
			}
			else if (i + 1 < expression.size() && (expression.get(i).equals("+") 
					&& expression.get(i+1).equals("-")) || (expression.get(i).equals("-") 
					&& expression.get(i+1).equals("+")))
			{
				expression.removeElementAt(i+1);
				expression.setElementAt("-", i);
				i--;
			}
		}
		
		for (int i = 0; i < expression.size(); i++)
		{
			if (i + 2 < expression.size() && "*/^".indexOf(expression.get(i)) != -1 
					&& (expression.get(i+1).equals("-") || expression.get(i+1).equals("+")))
			{
				expression.setElementAt(expression.get(i+1) + expression.get(i+2), i+2);
				expression.removeElementAt(i+1);
				i--;
			}
			else if (i + 1 < expression.size() && i == 0 && "+-".indexOf(expression.get(i))!=-1)
			{
				expression.setElementAt(expression.get(0) + expression.get(1), 1);
				expression.removeElementAt(0);
			}
		}
		expression.trimToSize();
		//System.out.println(expression);
		return expression;
	}
	
	/*
	 * Parameters: 
	 * operation - a String representing the operation to be evaluated first.
	 * expression - a Vector containing a separated mathematical expression. This expression
	 * has already passed through the simplify() and separate() methods.
	 * Returns:
	 * a String which does not contain the inputed operation. Any occurrences of the provided
	 * operation within the provided expression will be simplified into real numbers. The new
	 * expression will be returned as a String.
	 */
	private static String bedmas(String operation, Vector<String> expression)
	{
		for (int i = 0; i< expression.size(); i++)
		{
			if (expression.get(i).equals(operation) || (operation.equals("+") 
					&& expression.get(i).equals("-")))
			{
				if (expression.get(i).equals("-"))
					operation = "-";
				if(operation.equals("!"))
				{
					expression.setElementAt("" + factorial(Integer.parseInt(expression.get(i-1))), i);
				}
				
				else
				{
					expression.setElementAt("" + sparse("" + expression.get(i-1) + operation + expression.get(i+1)), i);
					expression.removeElementAt(i+1);
				}
				expression.removeElementAt(i-1);
				i--;
			}
		}
		return vectorToString(expression);
	}
	
	/*
	 * Parameters:
	 * token - a String
	 * Returns: 
	 * true if the string can be parsed as a real number; false otherwise.
	 */
	private static boolean isNumber(String token)
	{
		try{
			Double.parseDouble(token);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	/*
	 * Parameters:
	 * number - an integer representing the number whose factorial will be evaluated.
	 * Return: 
	 * a real number representing the factorial of the number inputed as a parameter.
	 */
	private static double factorial(int number)
	{
		int i = number;
		if (i == 1 || i == 0)
			return 1;
		while (i > 1)
			number *= --i;		
		return number;
	}
	
	/*
	 * Parameters: 
	 * expression - a Vector to be converted to a String.
	 * Returns:
	 * a String representation of the elements of the provided Vector concatenated together with
	 * nothing to separate the elements.
	 */
	private static String vectorToString (Vector<String> expression)
	{
		String retVal = "";
		for (String s : expression)
			retVal = retVal + s;
		return retVal;
	}
	
	/*
	 * Displays an error message that states what kind of error has occurred.
	 * Parameters: 
	 * errorType - an integer representing the type of error that has occurred.
	 */
	private static void error (int errorType)
	{
		String error = "Unknown error has occured.";
		if (errorType == SYNTAX_ERROR)
			error = new String("Incorrect syntax.");
		System.out.println(error);
	}
}
