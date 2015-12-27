package proj;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends Shape {
	
	private Rectangle square;
	
	private int height, width;
	
	public Square(Color c){
		super();
		colorShape = c;
		square =  new Rectangle(c);
		height = square.getHeight();
		width = square.getWidth();

		return;
	}
	
	public void changeColor( Color c ){
		super.changeColor(c);
		
		square.changeColor(c);

		return;
	}
	
	public void changeColorTemporarily(){
		super.changeColorTemporarily();
		
		square.changeColorTemporarily();

		return;
	}
	
	public void changeColorBack(){
		super.changeColorBack();
		
		square.changeColorBack();

		return;
	}
	
	public boolean shapeIsSelected(){
		return square.shapeIsSelected();
	}
	
	public  boolean shapeIsSelected( Coordinates positionOfMouse ){
		shapeSelected = square.shapeIsSelected( positionOfMouse );
		
		return shapeSelected;
	}
	
	public void resetShapeSelected(){
		super.resetShapeSelected();
		
		square.resetShapeSelected();

		return;
	}

	public void savePositionWhereUserPressedMouse(int x, int y){
		super.savePositionWhereUserPressedMouse(x, y);
		
		square.savePositionWhereUserPressedMouse(x, y);

		return;
	}
	
	public void showMe(Graphics g){
		square.showMe(g);

		return;
	}
	
	public String toString(){
		return ("Square with reference point " + referencePoint + " having width "
				+ width + " and height " + height + "\n");
	}

	double calculateArea(){ 
		return (width * height);
	}
	
	
	protected void moveShape(Coordinates currentPositionMouse){	      
		square.moveShape(currentPositionMouse);
		
		referencePoint = square.getReferencePoint();
		
		lastMousePosition =  currentPositionMouse;
		return;
	}
	
	boolean onThePerimeter(Coordinates mousePosition){
		int xWhereMousePressed, yWhereMousePressed;

		xWhereMousePressed = mousePosition.getX();
		yWhereMousePressed = mousePosition.getY();

		/*
		 * If the position where the user pressed the mouse button is within 5 pixels of 
		 * any side of the rectangle, the method will return true;
		 * Otherwise, it will return false.
		 */
		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + 5)))) return true;// top edge is edge # 0

		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + height + 5)))) return true;// left edge is edge # 1

		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() + height - 5) &&
				(yWhereMousePressed <= (referencePoint.getY()+ height + 5)))) return true;//bottom edge-edge # 2

		if (((xWhereMousePressed >= referencePoint.getX() + width - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + height + 5)))) return true;// right edge-edge # 3

		return false;
	}
	
	public void changeSize(int newSize){
		square.changeHeight(newSize);
		square.changeWidth(newSize);
		
		width = newSize;
		height = newSize;

		return;
	}
}
