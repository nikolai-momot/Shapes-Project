package proj;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	protected Coordinates referencePoint; 
	protected Coordinates lastMousePosition; 
	/* 
	 * If a shape has been  selected for moving, lastMousePosition gives the position where the 
	 * user has pressed the mouse.
	 */
	
	protected Color colorShape; // color of a a shape
	protected Color originalColor;// Used to save the original color when the color changes temporarily
	
	protected boolean shapeSelected = false;
	protected boolean perimeterSelected = false;

	abstract double calculateArea();

	public abstract void showMe(Graphics g);
	
	abstract boolean onThePerimeter(Coordinates mousePosition );
	
	public abstract String toString();
	
	public abstract boolean shapeIsSelected( Coordinates positionOfMouse ); 
	
	protected abstract void moveShape(Coordinates currentPositionMouse);
	
	public Shape(){
		referencePoint = new Coordinates(200, 200);  // new figure is at the center of frame
		return;
	}
	
	protected void moveCompoundShape(int x, int y, Coordinates currentMousePosition){
			
		referencePoint.setX(referencePoint.getX() + x);
		referencePoint.setY(referencePoint.getY() + y);
		
		lastMousePosition = currentMousePosition;

		return;
	}
	
	public void changeColor(Color c){
		colorShape = c ;
		
		return;
	}
	
	public void changeColorTemporarily(){	 
		originalColor = colorShape; 
		colorShape = Color.magenta ;
		perimeterSelected = true;
		
		return;
	}
	
	public void changeColorBack(){	 
		colorShape = originalColor; 
		perimeterSelected = false;
		
		return;
	}
	
	public boolean shapeIsSelected(){
		return shapeSelected;
	}
	
	public void resetShapeSelected(){
		shapeSelected = false;
		
		return;
	}

	public void savePositionWhereUserPressedMouse(int x, int y){
		lastMousePosition = new Coordinates(x, y);
		
		return;
	}
	
	public Coordinates getReferencePoint(){
		return referencePoint;
	}	
	
}