package proj;

import java.awt.Color;
import java.awt.Graphics;

public class Circle  extends Shape {
	
	public final double PI = 3.14;
	private int diameter;
	
	public Circle(Color currentColor){
		super();
		colorShape = currentColor;  
		diameter = 50;

		return;
	}
	
	public void changeDiameter(int newDiameter){
		diameter = newDiameter;

		return;
	}

	public boolean onThePerimeter(Coordinates mousePosition)
	{// the perimeter is selected if the distance from the point (x, y) to the center  is 
		// between radius - 5 and radius + 5
		int distanceFromCenter;
		Coordinates centreOfCircle;
		
		centreOfCircle = new Coordinates(referencePoint.getX() + diameter/2,
				                         referencePoint.getY() + diameter/2);
		distanceFromCenter = (int) distance(mousePosition, centreOfCircle);
		if ((distanceFromCenter >= diameter/2 - 5) && 
				(distanceFromCenter <= diameter/2 + 5)) {
			return true;
		} else{
			return false;
		}
	}

	public boolean shapeIsSelected(Coordinates positionOfMouse){ 
		// Check if the user pressed the mouse button inside the shape
		double distance;

		Coordinates centreOfCircle;
		centreOfCircle = computeCenterOfCircle(referencePoint, diameter);
		distance = distance(positionOfMouse, centreOfCircle);
		if (distance < diameter/2 - 5){
			shapeSelected = true;
			lastMousePosition = positionOfMouse;
			return true; 
		} else {
			shapeSelected = false;
			return false;
		}
	}

	/*
	 * changeShape changes the diameter of the circle, if
	 * currentPhase is 3 .
	 * currentPhase = 0, means the user is moving the shape
	 *              = 1, a new shape is being created
	 *              = 2, the the color of the new shape is being modified,
	 *              = 3, the diameter of the circle is being modified,
	 */

	public void changeShape(int currentPhase, int size){
		int newReferencePointX, newReferencePointY;
		if (currentPhase == 3){
			newReferencePointX = referencePoint.getX() + (diameter - size)/2;
			newReferencePointY = referencePoint.getY() + (diameter - size)/2;
			referencePoint = new Coordinates(newReferencePointX, newReferencePointY);
			diameter = size; 
		} 

		return;
	}

	/*
	 * Method moveShape(currentPositionMouse) allows the user to drag the circle by dragging the mouse
	 * button. The shape is first selected by pressing the mouse button with the cursor inside the shape.
	 * Then, if the user drags the mouse, the shape should move with the mouse.
	 * Moving a circle simply means modifying the reference point of the circle so that the
	 * circle moves with the mouse position.
	 * The idea is that if the x and the y coordinate of the position of the mouse is moved by specified
	 * amounts,the reference point must change by exactly the same amount.
	 */

	protected void moveShape(Coordinates currentPositionMouse){	     	
		if (shapeSelected) {		
			// If a shape is selected for a move operation, change the reference point 
			// as the mouse is being dragged.
			referencePoint.setX(referencePoint.getX() + 
					currentPositionMouse.getX() -
					lastMousePosition.getX());
			referencePoint.setY(referencePoint.getY() +
					currentPositionMouse.getY() -
					lastMousePosition.getY());
			lastMousePosition = currentPositionMouse;
		}

		return;
	}

	private double distance(Coordinates point1, Coordinates point2){
		double dist; // dist is the distance from point1 to point2
		dist = Math.sqrt((double) ((point1.getX() - point2.getX())*
				(point1.getX() - point2.getX()) +
				(point1.getY() - point2.getY())*(point1.getY() - point2.getY())));
		return dist ;
	} 
	public Coordinates computeCenterOfCircle(Coordinates referencePoint, int diameter){
		int xValueOfCenter,yValueOfCenter;
		xValueOfCenter = referencePoint.getX() + diameter/2;;
		yValueOfCenter = referencePoint.getY() + diameter/2;
		return new Coordinates(xValueOfCenter, yValueOfCenter);
	}

	public void resetShapeSelected(){
		shapeSelected = false;
	}
	/*
	 * calculateArea() returns the area of the rectangle
	 */

	double calculateArea(){ 
		return (PI * diameter * diameter/4);
	}

	/*
	 * showMe(g) displays the rectangle using the Graphic object g.
	 * It sets the color to be used, and draws the rectangle using the specified 
	 * reference point, the width and the height.
	 */

	public void showMe(Graphics g){
		g.setColor(colorShape);
		g.drawOval(referencePoint.getX(), // Draw a circle with the specified 
				// corner point and diameter
				referencePoint.getY(),   
				diameter,
				diameter);

		return;
	}

	/* 
	 * toString() returns the description of the rectangle - the color, 
	 * the reference point and the size.
	 */

	public String toString(){
		return ("Circle with reference point " + referencePoint + " having diameter " + diameter + "\n");
	}

	public void savePositionWhereUserPressedMouse(int x, int y){
		lastMousePosition = new Coordinates(x, y);

		return;
	}

}
