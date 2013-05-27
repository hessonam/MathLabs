package physics.sim;
/*
import javax.media.j3d.*;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;
*/
import javax.swing.*;
import physics.MainMenu;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({ "restriction", "serial" })
public class ThirdDimension extends JFrame implements ActionListener, ComponentListener
{
	String number = "";
    Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
    int width = 900;
    int height = screenSize.height -100;
    int xFrame = (screenSize.width - width) / 2;
    int yFrame = (screenSize.height - height) / 2;
    JPanel mainPanel = new JPanel ();
    JPanel controlPanel = new JPanel();
    JLabel lbl_xSpeed = new JLabel ("Horizontal Speed: ");
    JComboBox box_xSpeed = new JComboBox ();
    JLabel lbl_ySpeed = new JLabel ("Vertical Speed: ");
    JComboBox box_ySpeed = new JComboBox ();
    JLabel lbl_zSpeed = new JLabel ("3D Speed: ");
    JComboBox box_zSpeed = new JComboBox ();
    JButton back = new JButton("Back");
    JLabel author = new JLabel("Amer Hesson - 2010");
    DimensionDraw object = new DimensionDraw(); 
    int x=0;

    public ThirdDimension ()
    {
        super ("Motion of a ball in space");
        setSize (width, height);
        setLocation (xFrame, yFrame);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        getContentPane ().add (mainPanel);
        mainPanel.setLayout (null);
        //3D Test Code
        /*
        mainPanel.add(canvas);
        canvas.setBounds(0,0,500,500);
        universe = new SimpleUniverse(canvas);
        group = new BranchGroup();
        Sphere sphere = new Sphere(0.45f,1,50);
	    TransformGroup tg = new TransformGroup();
	    Transform3D transform = new Transform3D();
	    Vector3f vector = new Vector3f(0, .0f, .0f);
	    transform.setTranslation(vector);
	    tg.setTransform(transform);
	    tg.addChild(sphere);
	    group.addChild(tg);
	    Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
	    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	    Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
	    DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	    light1.setInfluencingBounds(bounds);
		group.addChild(light1);
		universe.getViewingPlatform().setNominalViewingTransform();
		// add the group of objects to the Universe
		universe.addBranchGraph(group);
		*/

        for (int i = 0 ; i <= 5 ; i++)
        {
            number = Integer.toString (i);
            box_xSpeed.addItem (number);
            box_ySpeed.addItem (number);
            if (i != 5)
                box_zSpeed.addItem (number);
        }

        box_xSpeed.setSelectedIndex (1);
        box_ySpeed.setSelectedIndex (1);
        box_zSpeed.setSelectedIndex (1);

        mainPanel.add (object);
        mainPanel.add(controlPanel);
        controlPanel.setBounds(0,height - 110, width, 110);
        controlPanel.setLayout(null);
        object.setBounds (0, 0, 900, height - 110);
        controlPanel.add (lbl_xSpeed);
        lbl_xSpeed.setBounds (10, 0, 120, 50);
        controlPanel.add (box_xSpeed);
        box_xSpeed.setBounds (130, 12, 40, 25);
        controlPanel.add (lbl_ySpeed);
        lbl_ySpeed.setBounds (190, 0, 120, 50);
        controlPanel.add (box_ySpeed);
        box_ySpeed.setBounds (310, 12, 40, 25);
        controlPanel.add (lbl_zSpeed);
        lbl_zSpeed.setBounds (370, 0, 120, 50);
        controlPanel.add (box_zSpeed);
        box_zSpeed.setBounds (460, 12, 40, 25);
        controlPanel.add(back);
        back.setBounds(700, 12, 70, 25);
        controlPanel.add(author);
        author.setForeground(Color.GRAY);
        author.setBounds(390, 45, 115, 25);

        back.addActionListener(this);
        box_xSpeed.addItemListener (
                new ItemListener ()
        {
            public void itemStateChanged (ItemEvent event)
            {
                object.x_speed = box_xSpeed.getSelectedIndex ();
            }
        }
        );

        box_ySpeed.addItemListener (
                new ItemListener ()
        {
            public void itemStateChanged (ItemEvent event)
            {
                object.y_speed = box_ySpeed.getSelectedIndex ();
            }
        }
        );

        box_zSpeed.addItemListener (
                new ItemListener ()
        {
            public void itemStateChanged (ItemEvent event)
            {
                if (box_zSpeed.getSelectedIndex () != 0)
                {
                    object.z_speed = box_zSpeed.getSelectedIndex ();
                    object.ThreeDMultiplyer_isInfinity = false;
                }
                else
                {
                    object.ThreeDMultiplyer = (int) (Double.POSITIVE_INFINITY);
                    object.ThreeDMultiplyer_isInfinity = true;
                }
            }
        }
        );
        addComponentListener(this);
    }

    public void componentResized(ComponentEvent e)
	{
		height = getHeight();
		width = getWidth();
		object.setBounds (0, 0, width, height - 110);
		controlPanel.setBounds(0,height - 110, width, 110);
		author.setBounds(width/2 - 57, 45 , 115, 30);
	}
	
	public void componentMoved(ComponentEvent e){}
	public void componentShown(ComponentEvent e){}
	public void componentHidden(ComponentEvent e){}
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource() == back)
    	{
    		object.terminateThread();
    		new MainMenu().setVisible(true);
    		this.dispose();
    	}
    }
    
    public static void main (String args[])
    {
        new ThirdDimension ().setVisible (true);
    }
}

