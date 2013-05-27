package physics.sim;

import javax.swing.*;
import java.awt.*;

public class Screen {
	
	GraphicsDevice vc;
	public Screen()
	{
		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = genv.getDefaultScreenDevice();
	}
}
