package physics;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

class VectorAdder extends JFrame implements ItemListener, ChangeListener,
		ActionListener, KeyListener {
	int width = 500;
	int height = 310;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int xFrame = (screenSize.width - width) / 2;
	int yFrame = (screenSize.height - height) / 2;
	int numberOfVectors = 2;
	JTextField[] magnitude;
	JTextField angle[];
	JLabel Vector1, Vector2, Vector3, Vector4, VectorR, magnitudeR, directionR;
	JLabel magnitudeHeading;
	JLabel angleHeading;
	JLabel lbl_numberOfVectors;
	JLabel author;
	String[] directions = { "N", "S", "W", "E" };
	String[] x_directions = { "W", "E" };
	JComboBox direction[];
	SpinnerModel model;
	JSpinner jsp_numberOfVectors;
	JButton calculate;
	JButton back;
	JButton reset;
	double xTotal, yTotal, resultantVector, resultantAngle;
	double[] mag = new double[4];
	double[] ang = new double[4];
	double[] xComp = new double[4];
	double[] yComp = new double[4];
	int multiplyer = 1;

	public VectorAdder() {
		super("Vector Adder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(xFrame, yFrame);
		setSize(width, height);
		setLayout(null);
		setResizable(false);

		direction = new JComboBox[8];
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0)
				direction[i] = new JComboBox(directions);
			else
				direction[i] = new JComboBox(x_directions);
		}
		angle = new JTextField[4];
		magnitude = new JTextField[4];
		Vector1 = new JLabel("First Vector:");
		Vector2 = new JLabel("Second Vector:");
		Vector3 = new JLabel("Third Vector:");
		Vector4 = new JLabel("Fourth Vector:");
		VectorR = new JLabel("Resultant Vector:");
		magnitudeHeading = new JLabel("Magnitude");
		angleHeading = new JLabel("Angle");
		author = new JLabel("Amer Hesson - 2010");
		model = new SpinnerNumberModel(2, 2, 4, 1);
		jsp_numberOfVectors = new JSpinner(model);
		lbl_numberOfVectors = new JLabel("Number of vectors:");
		magnitudeR = new JLabel();
		directionR = new JLabel();
		calculate = new JButton("Calculate");
		back = new JButton("Back");
		reset = new JButton("Reset");

		author.setForeground(Color.GRAY);

		for (int i = 0; i < 4; i++) {
			angle[i] = new JTextField();
			magnitude[i] = new JTextField();
			add(angle[i]);
			add(magnitude[i]);
			angle[i].addKeyListener(this);
			magnitude[i].addKeyListener(this);
		}
		for (int i = 0; i < 8; i++)
			add(direction[i]);
		add(Vector1);
		add(Vector2);
		add(Vector3);
		add(Vector4);
		add(VectorR);
		add(magnitudeHeading);
		add(angleHeading);
		add(jsp_numberOfVectors);
		add(lbl_numberOfVectors);
		add(magnitudeR);
		add(calculate);
		add(back);
		add(directionR);
		add(reset);
		add(author);

		magnitudeHeading.setBounds(150, 10, 90, 25);
		angleHeading.setBounds(330, 10, 100, 25);
		Vector1.setBounds(20, 40, 120, 25);
		Vector2.setBounds(20, 70, 120, 25);
		magnitude[0].setBounds(150, 40, 120, 25);
		magnitude[1].setBounds(150, 70, 120, 25);
		direction[0].setBounds(280, 40, 40, 25);
		angle[0].setBounds(330, 40, 40, 25);
		direction[1].setBounds(380, 40, 40, 25);
		direction[2].setBounds(280, 70, 40, 25);
		angle[1].setBounds(330, 70, 40, 25);
		direction[3].setBounds(380, 70, 40, 25);
		Vector3.setBounds(20, 100, 120, 25);
		magnitude[2].setBounds(150, 100, 120, 25);
		direction[4].setBounds(280, 100, 40, 25);
		angle[2].setBounds(330, 100, 40, 25);
		direction[5].setBounds(380, 100, 40, 25);
		Vector4.setBounds(20, 130, 120, 25);
		magnitude[3].setBounds(150, 130, 120, 25);
		direction[6].setBounds(280, 130, 40, 25);
		angle[3].setBounds(330, 130, 40, 25);
		direction[7].setBounds(380, 130, 40, 25);
		VectorR.setBounds(20, 180, 150, 25);
		magnitudeR.setBounds(150, 180, 160, 25);
		directionR.setBounds(320, 180, 120, 25);
		author.setBounds(190, 260, 180, 25);

		redraw();

		lbl_numberOfVectors.setBounds(145, 225, 150, 25);
		jsp_numberOfVectors.setBounds(285, 225, 40, 25);
		calculate.setBounds(20, 225, 110, 25);
		back.setBounds(380, 245, 90, 25);
		reset.setBounds(380, 215, 90, 25);
		for (int i = 0; i < 8; i++)
			direction[i].addItemListener(this);
		jsp_numberOfVectors.addChangeListener(this);

		calculate.addActionListener(this);
		back.addActionListener(this);
		reset.addActionListener(this);
	}

	public void redraw() {
		Vector3.setVisible(false);
		magnitude[2].setVisible(false);
		direction[4].setVisible(false);
		angle[2].setVisible(false);
		direction[5].setVisible(false);
		Vector4.setVisible(false);
		magnitude[3].setVisible(false);
		direction[6].setVisible(false);
		angle[3].setVisible(false);
		direction[7].setVisible(false);

		if (numberOfVectors > 2) {
			Vector3.setVisible(true);
			magnitude[2].setVisible(true);
			direction[4].setVisible(true);
			angle[2].setVisible(true);
			direction[5].setVisible(true);

			if (numberOfVectors == 4) {
				Vector4.setVisible(true);
				magnitude[3].setVisible(true);
				direction[6].setVisible(true);
				angle[3].setVisible(true);
				direction[7].setVisible(true);
			}
		}
	}

	Double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		if (d != 0)
			return Double.valueOf(twoDForm.format(d));
		else
			return 0.0;
	}

	Double roundFiveDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.#####");
		if (d != 0)
			return Double.valueOf(twoDForm.format(d));
		else
			return 0.0;
	}

	public static void main(String args[]) {
		new VectorAdder().setVisible(true);
	}

	public void itemStateChanged(ItemEvent e) {
		for (int x = 0; x <= 6; x += 2) {
			if (e.getSource() == direction[x]) {
				if (direction[x].getSelectedIndex() < 2) {
					direction[x + 1].removeAllItems();
					direction[x + 1].addItem("W");
					direction[x + 1].addItem("E");
				} else {
					direction[x + 1].removeAllItems();
					direction[x + 1].addItem("N");
					direction[x + 1].addItem("S");
				}
			}
		}
	}

	public void stateChanged(ChangeEvent e) {
		numberOfVectors = ((Integer) jsp_numberOfVectors.getValue()).intValue();
		redraw();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == calculate) {
			xTotal = 0;
			yTotal = 0;
			for (int x = 0; x < numberOfVectors; x++) {
				if (((magnitude[x].getText()).trim()).length() == 0
						|| angle[x].getText().trim().length() == 0) {
					magnitudeR.setForeground(Color.RED);
					magnitudeR.setText("Empty text fields");
					directionR.setText("");
					return;
				}
				ang[x] = Math.toRadians(Double.parseDouble(angle[x].getText()));
				if (ang[x] < 0 || ang[x] > Math.toRadians(90)) {
					magnitudeR.setForeground(Color.RED);
					magnitudeR.setText("Invalid angle");
					directionR.setText("");
					return;
				}
				mag[x] = Double.parseDouble(magnitude[x].getText());
				if (direction[2 * x].getSelectedIndex() == 0) {
					if (direction[2 * x + 1].getSelectedIndex() == 0)
						multiplyer = -1;
					else
						multiplyer = 1;
					xComp[x] = multiplyer * mag[x] * Math.sin(ang[x]);
					yComp[x] = mag[x] * Math.cos(ang[x]);
				} else if (direction[2 * x].getSelectedIndex() == 1) {
					if (direction[2 * x + 1].getSelectedIndex() == 0)
						multiplyer = -1;
					else
						multiplyer = 1;
					xComp[x] = multiplyer * mag[x] * Math.sin(ang[x]);
					yComp[x] = -mag[x] * Math.cos(ang[x]);
				} else if (direction[2 * x].getSelectedIndex() == 2) {
					if (direction[2 * x + 1].getSelectedIndex() == 0)
						multiplyer = 1;
					else
						multiplyer = -1;
					xComp[x] = -mag[x] * Math.cos(ang[x]);
					yComp[x] = multiplyer * mag[x] * Math.sin(ang[x]);
				} else {
					if (direction[2 * x + 1].getSelectedIndex() == 0)
						multiplyer = 1;
					else
						multiplyer = -1;
					xComp[x] = mag[x] * Math.cos(ang[x]);
					yComp[x] = multiplyer * mag[x] * Math.sin(ang[x]);
				}
				xTotal = xTotal + xComp[x];
				yTotal = yTotal + yComp[x];
			}
			magnitudeR.setForeground(Color.BLACK);
			resultantVector = roundFiveDecimals(Math.sqrt(Math.pow(xTotal, 2)
					+ Math.pow(yTotal, 2)));
			if (Math.abs(yTotal) < Math.abs(xTotal)) {
				if (xTotal != 0)
					resultantAngle = Math.abs(roundTwoDecimals(Math
							.toDegrees(Math.atan(yTotal / xTotal))));
				else
					resultantAngle = 0;
				directionR.setText((xTotal < 0 ? "[W  " : "[E  ")
						+ resultantAngle + (yTotal < 0 ? "  S]" : "  N]"));
			} else {
				if (yTotal != 0)
					resultantAngle = Math.abs(roundTwoDecimals(Math
							.toDegrees(Math.atan(xTotal / yTotal))));
				else
					resultantAngle = 0;
				directionR.setText((yTotal < 0 ? "[S  " : "[N  ")
						+ resultantAngle + (xTotal < 0 ? "  W]" : "  E]"));
			}
			if (resultantAngle == 0)
				directionR.setText((xTotal == 0 ? (yTotal > 0 ? "[N]" : "[S]")
						: (xTotal > 0 ? "[E]" : "[W]")));
			else if (resultantAngle == 45)
				directionR.setText((xTotal > 0 && yTotal > 0 ? "[NE]"
						: (xTotal > 0 && yTotal < 0 ? "[SE]" : (xTotal < 0
								&& yTotal > 0 ? "[NW]" : "[SW]"))));
			magnitudeR.setText("" + resultantVector);
			if (resultantAngle == 0 && resultantVector == 0) {
				directionR.setText("");
			}
		} else if (e.getSource() == reset) {
			jsp_numberOfVectors.setValue(2);
			for (int i = 0; i < 4; i++) {
				magnitude[i].setText("");
				angle[i].setText("");
				magnitudeR.setText("");
				directionR.setText("");
			}
			for (int i = 0; i < 8; i++)
				direction[i].setSelectedIndex(0);
		} else if (e.getSource() == back) {
			this.dispose();
			new MathMenu().setVisible(true);
		}
	}

	public void keyReleased(KeyEvent k) {}	
	public void keyPressed(KeyEvent k) {}

	public void keyTyped(KeyEvent k) {
		char c = k.getKeyChar();
		if (((!(Character.isDigit(c) || Character.isJavaIdentifierPart(c)))
				|| Character.isLetter(c) || c == '$' || c == '_')
				&& (c == '.' ? (isFloat(k) == true ? true : false) : true)
				&& (c == '-' ? (((JTextField) k.getSource()).getText().length() == 0 ? false
						: true)
						: true)
				&& k.getKeyCode() != 39
				&& k.getKeyCode() != 37
				&& c != '\n') {
			Toolkit.getDefaultToolkit().beep();
		} else
			return;
		k.consume();
	}

	private boolean isFloat(KeyEvent k) {
		boolean retVal = false;
		String characters = ((JTextField) (k.getSource())).getText();
		for (int i = 0; i < characters.trim().length(); i++)
			if (characters.charAt(i) == '.')
				return true;

		return retVal;
	}
}
