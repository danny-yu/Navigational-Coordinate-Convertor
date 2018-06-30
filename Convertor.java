/*TODO
 * Add spinner
 */

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
//import java.awt.event.KeyEvent;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Convertor extends JFrame{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 420;
	
	private JTextField jtfAngle;
	private JTextField outputAngle;
	private JTextField outputAngle2;
	private JRadioButton radNavToCoor;
	private JRadioButton radCoorToNav;
	private JRadioButton towardsBtn;
	private JRadioButton awayBtn;
	private JLabel jtfAngleLabel;
	private JLabel outputAngleLabel;
	private JLabel outputAngleLabel2;
	
	private JPanel panel;
	private DrawCanvas visualization;
	

	public Convertor(){
    	setTitle("Navigational Angle Convertor");
    	setSize(800,610);
    	setResizable(false);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	//center window
    	
    	Toolkit toolkit = getToolkit();
    	Dimension size = toolkit.getScreenSize();
    	setLocation(size.width/2-getWidth()/2, size.height/2-getHeight()/2);
    	
    	//
    	
    	panel = new JPanel();
    	getContentPane().add(panel);
    	
    	panel.setLayout(null);
    	panel.setToolTipText("A Panel container");
    	
    	//
    	
    	visualization = new DrawCanvas();
    	visualization.setBounds(20, 20, 550, 550);
    	
    	visualization.addMouseListener(new MouseAdapter() {
    		public void mousePressed(MouseEvent e) {
    			mouseConvert(e);
        	}
    	});
    	
    	visualization.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
            	mouseConvert(e);
            }
        });
    	
    	panel.add(visualization);
    	
    	
    	
    	//radio buttons
    	
    	radNavToCoor = new JRadioButton("Nav to Coor",true);
    	radCoorToNav = new JRadioButton("Coor to Nav");
    	    	
    	radNavToCoor.setBounds(590,10,200,40);
    	radCoorToNav.setBounds(590,50,200,40);
    	
    	radNavToCoor.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {         
            	if (e.getStateChange() == ItemEvent.SELECTED){ //if nav to coor
            		towardsBtn.setVisible(true);
            		awayBtn.setVisible(true);
            		outputAngleLabel.setText("Output:");
            		outputAngleLabel2.setVisible(false);
            		outputAngle.setText("");
            		outputAngle2.setVisible(false);
            		
            	}
            	else{											//if coor to nav
            		towardsBtn.setVisible(false);
            		awayBtn.setVisible(false);
            		outputAngleLabel.setText("Away:");
            		outputAngleLabel2.setVisible(true);
            		outputAngle.setText("");
            		outputAngle2.setText("");
            		outputAngle2.setVisible(true);
            		
            	}
            }
         });
    	
    	
    	towardsBtn = new JRadioButton("Towards");
    	awayBtn = new JRadioButton("Away",true);
    	
    	towardsBtn.setBounds(590,160,200,40);
    	awayBtn.setBounds(590,120,200,40);
    	
    	ButtonGroup conversionType = new ButtonGroup();
    	conversionType.add(radNavToCoor);
    	conversionType.add(radCoorToNav);
    	
    	
    	ButtonGroup navDirection = new ButtonGroup();
    	navDirection.add(towardsBtn);
    	navDirection.add(awayBtn);
    	
    	//Textfields
    	
    	jtfAngle = new JTextField("90");
    	jtfAngle.setBounds(680, 410, 100, 30);
    	
    	jtfAngleLabel = new JLabel("Input:");
    	jtfAngleLabel.setBounds(590,410,70,30);
    	
    	
    	
    	outputAngle = new JTextField("0.0");
    	
    	outputAngle.setBorder(BorderFactory.createEtchedBorder(
				 EtchedBorder.RAISED));
    	outputAngle.setEditable(false);
    	outputAngle.setBounds(680, 470, 100, 30);
    	
    	outputAngle2 = new JTextField();
    	outputAngle2.setBorder(BorderFactory.createEtchedBorder(
				 EtchedBorder.RAISED));
    	outputAngle2.setEditable(false);
    	outputAngle2.setBounds(680,530, 100, 30);
    	
    	outputAngleLabel = new JLabel("Output:");
    	outputAngleLabel.setBounds(590, 470, 70, 30);
    	
    	outputAngleLabel2 = new JLabel("Towards:");
    	outputAngleLabel2.setBounds(590, 530, 70, 30);
    	
    	
    	AngleHandler handler = new AngleHandler();
    	jtfAngle.addActionListener(handler);
    	
    	
    	panel.add(radCoorToNav);
    	panel.add(radNavToCoor);
    	panel.add(towardsBtn);
    	panel.add(awayBtn);
    	
    	panel.add(jtfAngle);
    	panel.add(outputAngle);
    	panel.add(outputAngle2);
    	
    	panel.add(jtfAngleLabel);
    	panel.add(outputAngleLabel);
    	panel.add(outputAngleLabel2);
    	
    	
    	outputAngle2.setVisible(false);
    	outputAngleLabel2.setVisible(false);
    	
    }
	private class AngleHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			enterData();
		}
	}
	private class DrawCanvas extends JPanel{
		
		double angle=0; //red
		double towardsAngle=0; //blue
		
		boolean towards=false;
		
		
		
		//@Override
		public void paintComponent(Graphics g){
			
	        super.paintComponent(g);     // paint parent's background
	        setBackground(Color.WHITE);  // set background color for this JPanel
			
			setBorder(BorderFactory.createEtchedBorder(
				 EtchedBorder.RAISED));
			
	         g.setColor(Color.LIGHT_GRAY);
	         for (int i=0;i<=550;i+=25){
	        	 g.drawLine(i, 0, i, 550);
		         g.drawLine(0, i, 550, i);
	         }
	         
	         g.setColor(Color.BLACK);    // set the drawing color
	         
	         //radius = 250
	         g.drawOval(25,25,500,500);
	         
	         g.drawLine(275, 0, 275, 550);
	         g.drawLine(0, 275, 550, 275);
	         
	         g.setFont(new Font("Monospaced", Font.PLAIN, 15));
	         g.drawString("x", 535, 290);
	         g.drawString("y", 280, 15);
	         
	         g.setColor(Color.RED);
	         angle = Math.toRadians(angle);
	         g.drawLine(275,275,(int)(275+250*Math.cos(angle)),(int)(275-250*Math.sin(angle)));
	         
	         if (towards){
		         g.setColor(Color.BLUE);
		         towardsAngle = Math.toRadians(towardsAngle);
		         g.drawLine(275,275,(int)(275+250*Math.cos(towardsAngle)),(int)(275-250*Math.sin(towardsAngle)));
	         }
		}
		
	}

    public static void main(String[] args) {
    	Convertor a = new Convertor();
    	a.setVisible(true);
    }
	/* Can convert from nav to reg or reg to nag
	 * from nav to reg: origAngle = nav angle, towards = whether nav is towards or away
	 * from reg to nag: origAngle = reg angle, towards = whether final angle is in terms of toward or away
	 */
	public static double angleConvertor(double origAngle, boolean towards){
		origAngle %= 360;
		double angle = 450 - origAngle;
		if (towards) angle += 180;
		angle %= 360;
		return angle;
	}
	private void enterData(){
		String text = jtfAngle.getText();
		double inputAngle = Double.parseDouble(text);
		
		double awayOutput = angleConvertor(inputAngle, false);
		double towardsOutput = angleConvertor(inputAngle, true);
		double output;
		if (radNavToCoor.isSelected()){
			if (towardsBtn.isSelected()){
				visualization.towards = true;
				visualization.angle = towardsOutput;
				visualization.towardsAngle = awayOutput;
				output = towardsOutput;
			}
			else{
				visualization.towards = false;
				visualization.angle = awayOutput;
				output = awayOutput;
			}
			
		}
		else{
			visualization.towards = true;
			visualization.angle = inputAngle;
			visualization.towardsAngle = 180+inputAngle;
			outputAngle2.setText(""+towardsOutput);
			output = awayOutput;
		}
		outputAngle.setText(""+output);
		
		visualization.repaint();
	}
	public void mouseConvert(MouseEvent e){
		double x = e.getX()-275;
		double y = 275-e.getY();
		double angle = Math.round(Math.toDegrees(Math.atan(y/x)));
		if (x<0) angle = 180+angle; //adjustment for second and third quadrant
		else if (y<0) angle = 360+angle; //adjustment for fourth quadrant;
		if (radNavToCoor.isSelected()) angle = angleConvertor(angle,false);
		jtfAngle.setText(""+angle);
		enterData();
	}
}
