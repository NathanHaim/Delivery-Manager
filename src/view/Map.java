package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import model.Tour;
import model.graph.Section;

public class Map extends JPanel {

	private ArrayList<ViewPoint> points;
	public HashMap<Integer,ViewEdge> edges;
	private ArrayList<ViewLabel> labels;
	private Color gradient;
	/**
	 * Create the panel.
	 */
	public Map() {
				
		points = new ArrayList<ViewPoint>();
		edges = new HashMap<Integer,ViewEdge>();
		labels = new ArrayList<ViewLabel>();
		//gradient = new Color(255,0,0);
		gradient = new Color(Color.HSBtoRGB(0, 1, 1));
		
		this.setBackground(Color.DARK_GRAY);
		// Action Listener
		MapMouseListener mouseListener = new MapMouseListener(this);
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}
	
	private void ajustColor()
	{
			float[] hsbVals = new float[3];
			Color.RGBtoHSB(gradient.getRed(), gradient.getGreen(), gradient.getBlue(), hsbVals);
			if(hsbVals[0] <=1.0f)
			{
				gradient = new Color(Color.HSBtoRGB(hsbVals[0]+0.01f, hsbVals[1], hsbVals[2]));
			}
	}
	
	public void addPoint(ViewPoint s,int id)
	{
		points.add(id,s);
	}
	
	public void addEdge(ViewEdge s, int id){
		edges.put(id,s);
	}
	
	public void addLabel(ViewLabel s){
		labels.add(s);
	}
	 
	
	
	/**
	 * This method display a tour on the map. 
	 * @param tour
	 */
	public void displayTour(Tour tour)
	{
		// Coloring Point
		for(Integer id: tour.getDeliveryPoints())
			points.get(id).color = Color.orange;
		
		// Iterate over the sections
		Iterator<Section> sectionIterator = tour.getSections().iterator();
					
		Section currSection;
		ViewEdge edge;
		
		// Coloring Entrepot
		points.get(tour.getEntrepotId()).color = Color.RED;
		
		//Color increment = Color.GREEN;
		Color increment = gradient;

		
		while(sectionIterator.hasNext())
		{			
			currSection = sectionIterator.next();
			edge = edges.get(currSection.getId());		
			edge.setColorId(increment,currSection.getId());
			ajustColor();
			increment = gradient;
		}
		
		repaint();
	}

	/**
	 * This is the draw method of the map. It iterate over its list of shapes, and draw all shapes.
	 */
	public void paintComponent(Graphics g) { 	
		super.paintComponent(g);
				
		g.setFont(new Font("Calibri", Font.PLAIN, 20)); 
		
		Iterator<ViewPoint> i = points.iterator();
		Iterator<ViewEdge> j = edges.values().iterator();
		Iterator<ViewLabel> k = labels.iterator();
		
		
		while(i.hasNext())
			i.next().drawShape(g, getWidth(), getHeight());

		while(j.hasNext())
		{
			ViewEdge curr =j.next();	
			curr.drawShape(g, getWidth(), getHeight());
		}
		
		i = points.iterator();
		while(i.hasNext())
			i.next().drawShape(g, getWidth(), getHeight());
		
		while(k.hasNext()){
			ViewLabel curr = k.next();
			curr.drawShape(g, getWidth(), getHeight());
		}
		g.dispose();
	}
	

	/**
	 * This method return the shape (if exists) that contains the (x,y) point passed in parameter.
	 */
	public ViewPoint containsPoint(int x, int y)
	{
		Iterator<ViewPoint> i = points.iterator();
		while(i.hasNext())
		{
			ViewPoint cur = i.next();
			
			if(cur.contains(x, y))
				return cur;
		}
		
		return null;
	}

	public ViewPoint getPoint(int id)
	{
		return (ViewPoint) points.get(id);
	}
	
	/**
	 * Return the first matching Section.
	 * @param x
	 * @param y
	 * @return
	 */
	public ViewEdge containsEdge(int x, int y)
	{
		Iterator<ViewEdge> i = edges.values().iterator();
		while(i.hasNext())
		{
			ViewEdge current = i.next();
			if(current.contains(x, y))
			{
				return current;
			}
		}
		
		return null;
	}
	
	public boolean labelsIsEmpty (){
		return labels.isEmpty();
	}
	
	public void setAllPointHoved(boolean hooved)
	{
		Iterator<ViewPoint> i = this.points.iterator();
		while(i.hasNext())
		{
			i.next().setHoved(hooved);
		}
	}
	
	public void removeAllLabels(){
		labels.clear();
	}
}
