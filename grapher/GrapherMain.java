package physics.grapher;

public class GrapherMain {

	public static void main (String[] args)
	{
		GrapherModel model = new GrapherModel();
		GrapherGUI view = new GrapherGUI(model);
		view.setVisible(true);
	}
}
