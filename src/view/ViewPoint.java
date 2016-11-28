package view;

import java.awt.Color;
import java.awt.Graphics;

public class ViewPoint implements IShape{

	private double x;
	private double y;
	public static Color colorDefault = Color.lightGray;
	private static int radius = 10;
	private static int radiusBIG = 20;
	private int calculedX;
	private int calculedY;
	private int id;
	public Color color = colorDefault;
	
	/**
	 * Normal Constructor of the view point. Coordinates are double because they are expressed in Frame Percentage
	 * @param x
	 * @param y
	 */
	public ViewPoint(double x, double y, int id)
	{
		this.x=x;
		this.y=y;
		this.id=id;
	}
	@Override
	public boolean contains(int x, int y) {
		return ( (calculedX-radius)< x && x < (calculedX+radius) )&& ( (calculedY-radius)< y && y < (calculedY+radius) );
	}

	
	public void drawShape(Graphics g, int width, int height) {
		// TODO Auto-generated method stub
		g.setColor(color);
		calculedX = (int) (width*x);
		calculedY = (int) (height*y);
		
		int usedRadius = radius;
		
		if(!color.equals(ViewPoint.colorDefault))
			usedRadius = radiusBIG;
		
		int dx = calculedX-(usedRadius/2);
		int dy = calculedY-(usedRadius/2);
		g.fillOval(dx,dy,usedRadius,usedRadius);
	}
	
	public int getId() { return id;}
	
	public int getCalculedX(){ return calculedX;}
	
	public int getCalculedY(){ return calculedY;}

}
