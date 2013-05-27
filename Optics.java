package physics;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

class Optics extends JFrame implements MouseMotionListener, ActionListener, ChangeListener
{
    JPanel mainPanel = new JPanel ();
    JLabel lbl_selectType = new JLabel ("Select type: ");
    JComboBox box_selectType = new JComboBox ();
    JLabel lbl_setFocal = new JLabel ("Set focal length: ");
    JLabel lbl_magnification = new JLabel ("M =");
    static JLabel lbl_heightImage = new JLabel ();
    static JLabel lbl_distanceImage = new JLabel ();
    static JLabel lbl_arrowCoordinates = new JLabel ();
    JLabel lbl_amer = new JLabel();
    static JTextField txt_magnification = new JTextField ();
    JButton reset = new JButton ("Reset");
    JButton mirrors = new JButton ("Mirrors");
    JButton back = new JButton ("Back");
    SpinnerModel positiveIntegers = new SpinnerNumberModel (10, 1, 9999, 1);
    JSpinner setFocal = new JSpinner (positiveIntegers);
    final OpticsDraw object = new OpticsDraw ();
    int width = 700;
    int height = 510;
    String number = "";
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;

    public Optics ()
    {
        super ("Behaviour of Light through Lenses and Mirrors");
        setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setResizable (false);
        getContentPane ().add (mainPanel);
        mainPanel.setLayout (null);
        mainPanel.add (object);
        object.setBackground (Color.BLACK);
        object.setBounds (0, 0, 700, 400);

        box_selectType.addItem ("Converging");
        box_selectType.addItem ("Diverging");

        mainPanel.add (lbl_selectType);
        lbl_selectType.setBounds (10, 400, 110, 50);
        mainPanel.add (box_selectType);
        box_selectType.setBounds (120, 412, 120, 25);
        mainPanel.add (lbl_setFocal);
        lbl_setFocal.setBounds (250, 400, 140, 50);
        mainPanel.add (setFocal);
        setFocal.setBounds (380, 412, 50, 25);
        mainPanel.add (lbl_magnification);
        lbl_magnification.setBounds (440, 400, 90, 50);
        mainPanel.add (txt_magnification);
        txt_magnification.setBounds (475, 412, 50, 25);
        mainPanel.add (lbl_distanceImage);
        lbl_distanceImage.setBounds (535, 408, 60, 40);
        mainPanel.add (lbl_heightImage);
        lbl_heightImage.setBounds (610, 408, 70, 40);
        mainPanel.add (mirrors);
        mirrors.setBounds(10, 443, 100, 25);
        mainPanel.add (reset);
        reset.setBounds (130, 443, 100, 25);
        mainPanel.add(lbl_amer);
        lbl_amer.setBounds(300,445,150,25);
        mainPanel.add(back);
        back.setBounds(570,443,80,25);
        lbl_amer.setForeground(Color.GRAY);
        lbl_amer.setText("Amer Hesson - 2010");
        
        object.add (lbl_arrowCoordinates);

        lbl_arrowCoordinates.setForeground (Color.WHITE);
        txt_magnification.setEditable (false);
        txt_magnification.setHorizontalAlignment (JTextField.CENTER);

        object.addMouseMotionListener (this);

        box_selectType.addItemListener (
                new ItemListener ()
        {
            public void itemStateChanged (ItemEvent event)
            {
                object.type = box_selectType.getSelectedIndex ();
                object.arrow_x =150;
                object.arrow_y2 = 160;
                object.repaint ();
            }
        }
        );

        reset.addActionListener (this);
        mirrors.addActionListener (this);
        back.addActionListener(this);
        setFocal.addChangeListener (this);
    }


    public void stateChanged (ChangeEvent event)
    {
        object.focalLength = ((Integer) setFocal.getValue ()).intValue ();
        object.repaint ();
    }


    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == reset)
        {
            object.focalLength = 10;
            object.arrow_x = 150;
            object.arrow_y2 = 160;
            Integer intObject = new Integer (10);
            setFocal.setValue (intObject);
            object.repaint ();
        }
        else if (e.getActionCommand ().equals ("Mirrors"))
        {
            object.lenses = false;
            mirrors.setText ("Lenses");
            object.arrow_x =150;
            object.arrow_y2 = 160;
        }
        else if (e.getActionCommand ().equals ("Lenses"))
        {
            object.lenses = true;
            mirrors.setText ("Mirrors");
        }
        else if (e.getSource() == back)
        {
        	this.dispose();
        	new MainMenu().setVisible(true);
        }
        object.repaint ();
    }

    public void mouseDragged (MouseEvent event)
    {
        if (object.lenses)
        {
            if (event.getY () >= 70 && event.getY () <= 330)
            {
                object.arrow_x = event.getX ();
                object.arrow_y2 = event.getY ();
            }
        }
        else
        {
            if (object.type == 0)
            {
                if (event.getX () < object.x_arcIntersection_1)
                    object.arrow_x = event.getX ();
            }
            else
            {
                if (event.getX () < 350)
                    object.arrow_x = event.getX ();
            }

            if (event.getY () > (int) (202 - object.radiusOfCurvature * Math.sin (Math.toRadians (60))) && event.getY () < (int) (198 + object.radiusOfCurvature * Math.sin (Math.toRadians (60))))
                if (event.getX () > 500 - (20 * object.focalLength - (object.radiusOfCurvature * Math.cos (Math.toRadians (60)))))
                {
                    if (object.arrow_y2 < 160 || object.arrow_y2 > 240)
                    {
                        if (event.getY () <= 200 && event.getY () > object.y_arcIntersection_2)
                            object.arrow_y2 = event.getY ();
                        else if (event.getY () > 200 && event.getY () < object.y_arcIntersection_2)
                            object.arrow_y2 = event.getY ();
                    }
                    else
                        object.arrow_y2 = event.getY ();
                }
                else if (event.getX () < 500 - (20 * object.focalLength - (object.radiusOfCurvature * Math.cos (Math.toRadians (60)))))
                    object.arrow_y2 = event.getY ();
        }
        object.repaint ();
    }

    public void mouseMoved (MouseEvent event){}

    static public void main (String[] args)
    {
        new Optics ().setVisible (true);
    }
}


