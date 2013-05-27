package physics;

import javax.swing.*;
import java.awt.*;
import java.text.*;

class OpticsDraw extends JPanel
{
    int type = 0;
    int focalLength = 10;
    double height_image, height_object, distance_image, magnification;
    int arrow_x = 150, arrow_y2 = 160, radiusOfCurvature;
    double dy_1, dx_1, slope_1, y_intercept_1, dy_2, dx_2, slope_2, y_intercept_2;
    int x_screenIntersection_1, y_screenIntersection_1, x_screenIntersection_2, y_screenIntersection_2, x_pointOfIntersection, y_pointOfIntersection;
    double arrowCoordinate_x, arrowCoordinate_y;
    boolean lenses = true;
    int x_arcIntersection_1, x_arcIntersection_2, y_arcIntersection_2;
    float quadratic_a, quadratic_b, quadratic_c, discriminant;
    int startingPosition = 350;

	private Double roundTwoDecimals (double d)
    {
        DecimalFormat twoDForm = new DecimalFormat ("#.##");
        return Double.valueOf (twoDForm.format (d));
    }


    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        g.setColor (Color.WHITE);
        //Draws a white arrow and the principal axis
        g.drawLine (0, 200, 700, 200);
        g.drawLine (arrow_x, 200, arrow_x, arrow_y2);

        //Show coordinates of arrow tip
        arrowCoordinate_x = arrow_x - startingPosition;
        arrowCoordinate_x /= 10;
        arrowCoordinate_y = 200 - arrow_y2;
        arrowCoordinate_y /= 10;

        //Coordinates
        Optics.lbl_arrowCoordinates.setText ("<html>(d<sub>o</sub>, h<sub>o</sub>) = (" + arrowCoordinate_x + ", " + arrowCoordinate_y + ")</html>");

        if (arrow_y2 < 200) //if arrow is above principal axis
        {
            g.drawLine (arrow_x, arrow_y2, arrow_x - 7, arrow_y2 + 7);
            g.drawLine (arrow_x, arrow_y2, arrow_x + 7, arrow_y2 + 7);
        }
        else if (arrow_y2 > 200) //if arrow is below principal axis
        {
            g.drawLine (arrow_x, arrow_y2, arrow_x - 7, arrow_y2 - 7);
            g.drawLine (arrow_x, arrow_y2, arrow_x + 7, arrow_y2 - 7);
        }
        //Draws lines for the grid
        if (lenses)
            startingPosition = 350;
        else
        {	
            radiusOfCurvature = 20 * focalLength;
            if (type == 0)
                startingPosition = 500;
            else
                startingPosition = 350;
        }
        {
            for (int i = startingPosition ; i <= 700 ; i += 10)
            {
                if ((i - startingPosition) % (10 * focalLength) == 0)
                {
                    g.setColor (Color.ORANGE);
                    g.drawLine (i, 195, i, 205);
                }
                else
                {
                    g.setColor (Color.WHITE);
                    g.drawLine (i, 197, i, 203);
                }
            }
            for (int i = startingPosition ; i >= 0 ; i -= 10)
            {
                if ((i - startingPosition) % (10 * focalLength) == 0 && i != 0)
                {
                    g.setColor (Color.ORANGE);
                    g.drawLine (i, 195, i, 205);
                }
                else
                {
                    g.setColor (Color.WHITE);
                    g.drawLine (i, 197, i, 203);
                }
            }
        }
        g.setColor (Color.WHITE);

        if (lenses)
        {
            if (type == 0) //If Converging
            {
                // Draws a converging lens
                g.drawArc (340, 50, 40, 300, 120, 120);
                g.drawArc (320, 50, 40, 300, 60, -120);
                //draws horizontal line from the tip of the arrow to the lens (line 1/3)
                g.setColor (Color.RED);
                g.drawLine (arrow_x, arrow_y2, 350, arrow_y2);
                // calculates necessary information to form equation of line from lens to focal point (line 2/3)

                dy_1 = 200 - arrow_y2;

                if (arrow_x > 350)
                    dx_1 = -10 * focalLength;
                else
                    dx_1 = 10 * focalLength;
                slope_1 = dy_1 / dx_1;


                if (arrow_x > 350)
                    y_intercept_1 = 200 - slope_1 * (350 - 10 * focalLength);
                else
                    y_intercept_1 = 200 - slope_1 * (10 * focalLength + 350);
                //Calculates coordinates of points on the edge of screen (endpoints)
                if (arrow_x <= 350)
                    y_screenIntersection_1 = (int) (Math.round (slope_1 * 700 + y_intercept_1));
                else
                    y_screenIntersection_1 = (int) (Math.round (y_intercept_1));
                if (slope_1 != 0)
                    if (arrow_y2 <= 200)
                        x_screenIntersection_1 = (int) (Math.round ((400 - y_intercept_1) / slope_1));
                    else
                        x_screenIntersection_1 = (int) (Math.round (-y_intercept_1 / slope_1));
                if (x_screenIntersection_1 >= 0 && x_screenIntersection_1 <= 700) //If endpoint is on the x-edge
                    if (arrow_y2 <= 200)
                        g.drawLine (350, arrow_y2, x_screenIntersection_1, 400);
                    else
                        g.drawLine (350, arrow_y2, x_screenIntersection_1, 0);
                else
                    if (arrow_x > 350)
                        g.drawLine (350, arrow_y2, 0, y_screenIntersection_1);
                    else
                        g.drawLine (350, arrow_y2, 700, y_screenIntersection_1); //Else: endpoint is on the y-edge
            }

            else //Else: Diverging
            {
                //Draws a diverging lens
                g.drawArc (360, 50, 40, 300, 120, 120);
                g.drawArc (300, 50, 40, 300, 60, -120);
                g.drawLine (330, 68, 370, 68);
                g.drawLine (330, 330, 370, 330);

                //draws horizontal line from the tip of the arrow to the lens (line 1/3)
                g.setColor (Color.RED);
                g.drawLine (arrow_x, arrow_y2, 350, arrow_y2);

                // calculates necessary information to form equation of line from lens to focal point (line 2/3)

                dy_1 = arrow_y2 - 200;

                if (arrow_x > 350)
                    dx_1 = -10 * focalLength;
                else
                    dx_1 = 10 * focalLength;
                slope_1 = dy_1 / dx_1;


                if (arrow_x > 350)
                    y_intercept_1 = 200 - slope_1 * (10 * focalLength + 350);
                else
                    y_intercept_1 = 200 - slope_1 * (350 - 10 * focalLength);
                //Calculates coordinates of points on the edge of screen (endpoints)
                if (arrow_x <= 350)
                    y_screenIntersection_1 = (int) (Math.round (slope_1 * 700 + y_intercept_1));
                else
                    y_screenIntersection_1 = (int) (Math.round (y_intercept_1));
                if (slope_1 != 0)
                    if (arrow_y2 <= 200)
                        x_screenIntersection_1 = (int) (Math.round (-y_intercept_1 / slope_1));
                    else
                        x_screenIntersection_1 = (int) (Math.round ((400 - y_intercept_1) / slope_1));
                if (x_screenIntersection_1 >= 0 && x_screenIntersection_1 <= 700) //If endpoint is on the x-edge
                    if (arrow_y2 <= 200)
                        g.drawLine (350, arrow_y2, x_screenIntersection_1, 0);
                    else
                        g.drawLine (350, arrow_y2, x_screenIntersection_1, 400);

                else //Else: endpoint is on the y-edge
                    if (arrow_x > 350)
                        g.drawLine (350, arrow_y2, 0, y_screenIntersection_1);
                    else
                        g.drawLine (350, arrow_y2, 700, y_screenIntersection_1);
            }
            //Line 3/3
            dy_2 = 200 - arrow_y2;
            dx_2 = 350 - arrow_x;
            slope_2 = dy_2 / dx_2;
            y_intercept_2 = 200 - slope_2 * 350;
            if (arrow_x <= 350)
                y_screenIntersection_2 = (int) (Math.round (slope_2 * 700 + y_intercept_2));
            else
                y_screenIntersection_2 = (int) (Math.round (y_intercept_2));
            if (slope_2 != 0)
                if (arrow_y2 <= 200)
                    x_screenIntersection_2 = (int) (Math.round ((400 - y_intercept_2) / slope_2));
                else
                    x_screenIntersection_2 = (int) (Math.round (-y_intercept_2 / slope_2));

            if (x_screenIntersection_2 >= 0 && x_screenIntersection_2 <= 700) //If endpoint is on the x-edge
                if (arrow_y2 <= 200)
                    g.drawLine (arrow_x, arrow_y2, x_screenIntersection_2, 400);
                else
                    g.drawLine (arrow_x, arrow_y2, x_screenIntersection_2, 0);
            else
                if (arrow_x <= 350)
                    g.drawLine (arrow_x, arrow_y2, 700, y_screenIntersection_2); //Else: endpoint is on the y-edge
                else
                    g.drawLine (arrow_x, arrow_y2, 0, y_screenIntersection_2);

            //POI between Line 2 & Line 3
            x_pointOfIntersection = (int) ((y_intercept_2 - y_intercept_1) / (slope_1 - slope_2));
            y_pointOfIntersection = (int) (slope_1 * x_pointOfIntersection + y_intercept_1);
            //Draw image
            g.setColor (Color.ORANGE);
            g.drawLine (x_pointOfIntersection, 200, x_pointOfIntersection, y_pointOfIntersection);
            if (y_pointOfIntersection < 200)
            {
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection + 7);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection + 7);
            }
            else
            {
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection - 7);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection - 7);
            }
            //Same side image line continuation
            if (((x_pointOfIntersection > 350 && arrow_x > 350) || (x_pointOfIntersection < 350 && arrow_x < 350)) && (arrow_x != 350 - 10 * focalLength && arrow_x != 350 + 10 * focalLength || type == 1))
            {
                g.setColor (Color.YELLOW);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, 350, arrow_y2);
                if (type == 0)
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, arrow_x, arrow_y2);
            }

            //Mag calculations
            height_image = 200 - y_pointOfIntersection;
            height_object = 200 - arrow_y2;
            if (height_object != 0)
                magnification = height_image / height_object;

            if (magnification <= 9999 && magnification >= -9999)
                Optics.txt_magnification.setText ("" + roundTwoDecimals (magnification));

            else if (magnification > 9999)
            {
                magnification = Double.POSITIVE_INFINITY;
                Optics.txt_magnification.setText ("N/A");
            }
            else
            {
                magnification = Double.NEGATIVE_INFINITY;
                Optics.txt_magnification.setText ("N/A");
            }
            //Characteristics
            g.setColor (Color.ORANGE);
            g.drawString ("Image Characteristics:", 20, 300);
            if (type == 0)
            {
                if ((Math.abs (magnification) > 1 && Math.abs (magnification) < 9999))
                    g.drawString ("Magnification:  Enlarged", 20, 320);
                else if (arrow_x == 350 - 20 * focalLength || arrow_x == 350 + 20 * focalLength || (int) (Math.abs (magnification)) == 1)
                    g.drawString ("Magnification:  None", 20, 320);
                else if (Math.abs (magnification) < 1 && Math.abs (magnification) > 0)
                    g.drawString ("Magnification:  Diminished", 20, 320);
                else
                    g.drawString ("Magnification:  N/A", 20, 320);
                if (arrow_x == 350 - 10 * focalLength || arrow_x == 350 + 10 * focalLength)
                    g.drawString ("Orientation:      N/A", 20, 335);
                else if ((arrow_y2 < 200 && y_pointOfIntersection < 200) || (arrow_y2 > 200 && y_pointOfIntersection > 200))
                    g.drawString ("Orientation:      Upright", 20, 335);
                else
                    g.drawString ("Orientation:      Inverted", 20, 335);
                if (arrow_x == 350 - 10 * focalLength || arrow_x == 350 + 10 * focalLength)
                    g.drawString ("Type:                 N/A", 20, 350);
                else if ((x_pointOfIntersection < 350 && arrow_x < 350) || (x_pointOfIntersection > 350 && arrow_x > 350))
                    g.drawString ("Type:                 Virtual", 20, 350);
                else
                    g.drawString ("Type:                 Real", 20, 350);
            }
            else
            {
                g.drawString ("Magnification:  Diminished", 20, 320);
                g.drawString ("Orientation:      Upright", 20, 335);
                g.drawString ("Type:                 Virtual", 20, 350);
            }

            height_image /= 10;

            if (height_image > 9999 || height_image < -9999)
                Optics.lbl_heightImage.setText ("<html>h<sub>i</sub>= N/A</html>");
            else
                Optics.lbl_heightImage.setText ("<html>h<sub>i</sub>= " + height_image + "</html>");

            distance_image = x_pointOfIntersection - 350;
            distance_image /= 10;
            if (distance_image > 9999 || distance_image < -9999)
                Optics.lbl_distanceImage.setText ("<html>d<sub>i</sub>= N/A</html>");
            else
                Optics.lbl_distanceImage.setText ("<html>d<sub>i</sub>= " + distance_image + "</html>");
        }
        else //Else: mirrors
        {

            if (type == 0) //If converging
            {
                //draws converging mirror
                g.drawArc (500 - 2 * radiusOfCurvature, 200 - radiusOfCurvature, 2 * radiusOfCurvature, 2 * radiusOfCurvature, 60, -120);
                //draws horizontal line from the tip of the arrow to the lens (line 1/4)
                g.setColor (Color.RED);
                x_arcIntersection_1 = (int) ((Math.sqrt (Math.abs (Math.pow (radiusOfCurvature, 2) - Math.pow (arrow_y2 - 200, 2)))) + (500 - radiusOfCurvature));
                g.drawLine (arrow_x, arrow_y2, x_arcIntersection_1, arrow_y2);

                //line 2/4
                dy_1 = arrow_y2 - 200;
                dx_1 = x_arcIntersection_1 - (500 - focalLength * 10);
                slope_1 = dy_1 / dx_1;
                y_intercept_1 = 200 - slope_1 * (500 - focalLength * 10);

                //Calculates coordinates of points on the edge of screen (endpoints)
                y_screenIntersection_1 = (int) (Math.round (y_intercept_1));
                if (slope_1 != 0)
                    if (arrow_y2 <= 200)
                        x_screenIntersection_1 = (int) (Math.round ((400 - y_intercept_1) / slope_1));
                    else
                        x_screenIntersection_1 = (int) (Math.round (-y_intercept_1 / slope_1));
                if (x_screenIntersection_1 >= 0 && x_screenIntersection_1 <= 700) //If endpoint is on the x-edge
                    if (arrow_y2 <= 200)
                        g.drawLine (x_arcIntersection_1, arrow_y2, x_screenIntersection_1, 400);
                    else
                        g.drawLine (x_arcIntersection_1, arrow_y2, x_screenIntersection_1, 0);
                else
                    g.drawLine (x_arcIntersection_1, arrow_y2, 0, y_screenIntersection_1); //Else: endpoint is on the y-edge
                //line 3/4
                if (!(arrow_x > 495 - focalLength * 10 && arrow_x < 505 - focalLength * 10))
                {
                    dy_2 = 200 - arrow_y2;
                    dx_2 = (500 - 10 * focalLength) - arrow_x;
                    slope_2 = dy_2 / dx_2;
                    y_intercept_2 = arrow_y2 - slope_2 * arrow_x;
                    quadratic_a = (float) (Math.pow (slope_2, 2) + 1);
                    quadratic_b = (float) (((2 * slope_2 * y_intercept_2) - (400 * slope_2) + ((radiusOfCurvature - 500) * 2)));
                    quadratic_c = (float) ((Math.pow (y_intercept_2, 2) - Math.pow (radiusOfCurvature, 2) - (400 * y_intercept_2) + 40000 + Math.pow ((radiusOfCurvature - 500), 2)));
                    discriminant = (float) (Math.pow (quadratic_b, 2) - (4 * quadratic_a * quadratic_c));
                    if (discriminant >= 0)
                        x_arcIntersection_2 = (int) (Math.max (((-quadratic_b + Math.sqrt (discriminant)) / (2 * quadratic_a)), ((-quadratic_b - Math.sqrt (discriminant)) / (2 * quadratic_a))));
                    else
                        System.out.println ("Error, imaginary root!");
                    y_arcIntersection_2 = (int) (slope_2 * x_arcIntersection_2 + y_intercept_2);
                    g.drawLine (arrow_x, arrow_y2, x_arcIntersection_2, y_arcIntersection_2);
                    //System.out.println ("slope: " + slope_2 + "\n yintercept: " + y_intercept_2 + "\n quadratic-a: " + quadratic_a + "\n quadratic-b: " + quadratic_b + "\n quadratic_c: " + quadratic_c + "\n discriminant: " + discriminant + "\n xarcintersection2: " + x_arcIntersection_2 + "\n yarcintersection2: " + y_arcIntersection_2);
                    //line 4/4
                    g.drawLine (x_arcIntersection_2, y_arcIntersection_2, 0, y_arcIntersection_2);

                    //POI between line 2 and line 4
                    x_pointOfIntersection = (int) ((y_arcIntersection_2 - y_intercept_1) / slope_1);
                    y_pointOfIntersection = y_arcIntersection_2;
                    g.setColor (Color.ORANGE);
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection, 200);

                    if (y_pointOfIntersection < 200)
                    {
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection + 7);
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection + 7);
                    }
                    else
                    {
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection - 7);
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection - 7);
                    }
                    //Same side image line continuation
                    if (arrow_x > 500 - 10 * focalLength)
                    {
                        g.setColor (Color.YELLOW);
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_arcIntersection_1, arrow_y2);
                        g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_arcIntersection_2, y_arcIntersection_2);
                    }
                }
            }
            else //Diverging
            {
                //draws converging mirror
                g.drawArc (350, 200 - radiusOfCurvature, 2 * radiusOfCurvature, 2 * radiusOfCurvature, 120, 120);
                //draws horizontal line from the tip of the arrow to the lens (line 1/4)
                g.setColor (Color.RED);
                x_arcIntersection_1 = (int) (-(Math.sqrt (Math.pow (radiusOfCurvature, 2) - Math.pow (arrow_y2 - 200, 2))) + (350 + radiusOfCurvature));
                g.drawLine (arrow_x, arrow_y2, x_arcIntersection_1, arrow_y2);

                //line 2/4
                dy_1 = arrow_y2 - 200;
                dx_1 = x_arcIntersection_1 - (350 + focalLength * 10);
                slope_1 = dy_1 / dx_1;
                y_intercept_1 = 200 - slope_1 * (350 + focalLength * 10);

                //Calculates coordinates of points on the edge of screen (endpoints)
                y_screenIntersection_1 = (int) (Math.round (y_intercept_1));
                if (slope_1 != 0)
                    if (arrow_y2 <= 200)
                        x_screenIntersection_1 = (int) (Math.round (-y_intercept_1 / slope_1));
                    else if (arrow_y2 > 200)
                        x_screenIntersection_1 = (int) (Math.round (400 - y_intercept_1 / slope_1));
                if (x_screenIntersection_1 >= 0 && x_screenIntersection_1 <= 700) //If endpoint is on the x-edge
                    if (arrow_y2 <= 200)
                        g.drawLine (x_arcIntersection_1, arrow_y2, x_screenIntersection_1, 0);
                    else
                        g.drawLine (x_arcIntersection_1, arrow_y2, x_screenIntersection_1, 400);
                else
                    g.drawLine (x_arcIntersection_1, arrow_y2, 0, y_screenIntersection_1); //Else: endpoint is on the y-edge
                //line 3/4

                dy_2 = 200 - arrow_y2;
                dx_2 = (350 + 10 * focalLength) - arrow_x;
                slope_2 = dy_2 / dx_2;
                y_intercept_2 = arrow_y2 - slope_2 * arrow_x;
                quadratic_a = (float) (Math.pow (slope_2, 2) + 1);
                quadratic_b = (float) ((2 * slope_2 * y_intercept_2) - (400 * slope_2) - (2 * radiusOfCurvature + 700));
                quadratic_c = (float) ((Math.pow (y_intercept_2, 2) - Math.pow (radiusOfCurvature, 2) - (400 * y_intercept_2) + 40000 + Math.pow ((radiusOfCurvature + 350), 2)));
                discriminant = (float) (Math.pow (quadratic_b, 2) - (4 * quadratic_a * quadratic_c));
                if (discriminant >= 0)
                    x_arcIntersection_2 = (int) (Math.min (((-quadratic_b + Math.sqrt (discriminant)) / (2 * quadratic_a)), ((-quadratic_b - Math.sqrt (discriminant)) / (2 * quadratic_a))));
                else
                    System.out.println ("Error, imaginary root!");
                y_arcIntersection_2 = (int) (slope_2 * x_arcIntersection_2 + y_intercept_2);
                g.drawLine (arrow_x, arrow_y2, x_arcIntersection_2, y_arcIntersection_2);
                //System.out.println ("slope: " + slope_2 + "\n yintercept: " + y_intercept_2 + "\n quadratic-a: " + quadratic_a + "\n quadratic-b: " + quadratic_b + "\n quadratic_c: " + quadratic_c + "\n discriminant: " + discriminant + "\n xarcintersection2: " + x_arcIntersection_2 + "\n yarcintersection2: " + y_arcIntersection_2);
                //line 4/4
                g.drawLine (x_arcIntersection_2, y_arcIntersection_2, 0, y_arcIntersection_2);

                //POI between line 2 and line 4
                x_pointOfIntersection = (int) ((y_arcIntersection_2 - y_intercept_1) / slope_1);
                y_pointOfIntersection = y_arcIntersection_2;
                g.setColor (Color.ORANGE);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection, 200);

                if (y_pointOfIntersection < 200)
                {
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection + 7);
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection + 7);
                }
                else
                {
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection - 7, y_pointOfIntersection - 7);
                    g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_pointOfIntersection + 7, y_pointOfIntersection - 7);
                }
                //Same side image line continuation
                g.setColor (Color.YELLOW);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_arcIntersection_1, arrow_y2);
                g.drawLine (x_pointOfIntersection, y_pointOfIntersection, x_arcIntersection_2, y_arcIntersection_2);
            }

            //Mag calculations
            height_image = 200 - y_pointOfIntersection;
            height_object = 200 - arrow_y2;
            if (height_object != 0)
                magnification = height_image / height_object;

            if (magnification <= 9999 && magnification >= -9999)
                Optics.txt_magnification.setText ("" + roundTwoDecimals (magnification));

            else if (magnification > 9999)
            {
                magnification = Double.POSITIVE_INFINITY;
                Optics.txt_magnification.setText ("N/A");
            }
            else
            {
                magnification = Double.NEGATIVE_INFINITY;
                Optics.txt_magnification.setText ("N/A");
            }
            //Characteristics
            g.setColor (Color.ORANGE);
            g.drawString ("Image Characteristics:", 20, 300);
            if (type == 0)
            {

                if ((Math.abs (magnification) > 1 && Math.abs (magnification) < 9999) && arrow_x != 500 - 10 * focalLength)
                    g.drawString ("Magnification:  Enlarged", 20, 320);
                else if ((int) (Math.abs (magnification)) == 1)
                    g.drawString ("Magnification:  None", 20, 320);
                else if (Math.abs (magnification) < 1 && Math.abs (magnification) > 0)
                    g.drawString ("Magnification:  Diminished", 20, 320);
                else
                {
                    g.drawString ("Magnification:  N/A", 20, 320);
                    Optics.txt_magnification.setText ("N/A");
                    Optics.lbl_distanceImage.setText ("<html>d<sub>i</sub>= N/A</html>");
                    Optics.lbl_heightImage.setText ("<html>h<sub>i</sub>= N/A</html>");
                }
                if (arrow_x == 500 - 10 * focalLength)
                    g.drawString ("Orientation:      N/A", 20, 335);
                else if ((arrow_y2 < 200 && y_pointOfIntersection < 200) || (arrow_y2 > 200 && y_pointOfIntersection > 200))
                    g.drawString ("Orientation:      Upright", 20, 335);
                else
                    g.drawString ("Orientation:      Inverted", 20, 335);
                if (arrow_x == 500 - 10 * focalLength)
                    g.drawString ("Type:                 N/A", 20, 350);
                else if (x_pointOfIntersection < 500 && arrow_x < 500)
                    g.drawString ("Type:                 Real", 20, 350);
                else if (x_pointOfIntersection > 500 && arrow_x < 500)
                    g.drawString ("Type:                 Virtual", 20, 350);
            }
            else
            {
                g.drawString ("Magnification:  Diminished", 20, 320);
                g.drawString ("Orientation:      Upright", 20, 335);
                g.drawString ("Type:                 Virtual", 20, 350);
            }

            height_image /= 10;

            if (height_image > 9999 || height_image < -9999 || arrow_x == 500 - 10 * focalLength)
                Optics.lbl_heightImage.setText ("<html>h<sub>i</sub>= N/A</html>");
            else
                Optics.lbl_heightImage.setText ("<html>h<sub>i</sub>= " + height_image + "</html>");
            if (type == 0)
                distance_image = x_pointOfIntersection - 500;
            else
                distance_image = x_pointOfIntersection - 350;
            distance_image /= 10;
            if (distance_image > 9999 || distance_image < -9999 || arrow_x == 500 - 10 * focalLength)
                Optics.lbl_distanceImage.setText ("<html>d<sub>i</sub>= N/A</html>");
            else
                Optics.lbl_distanceImage.setText ("<html>d<sub>i</sub>= " + distance_image + "</html>");
            
        }
    }
}