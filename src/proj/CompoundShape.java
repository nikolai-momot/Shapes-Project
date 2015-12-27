package proj;

import java.awt.Graphics;
import java.util.ArrayList;

public class CompoundShape extends Shape{

	private ArrayList<Shape> shapes;
	
	public CompoundShape(){
		super();
		shapes = new ArrayList<Shape>();
		
		return;
	}
	
	public boolean onThePerimeter(Coordinates mousePosition) {
		boolean onPerimeter = false;
		
		for(Shape shape : shapes){
			if(shape.onThePerimeter(mousePosition)){
				onPerimeter = true;
			}
		}
		
		return onPerimeter;
	}

	public double calculateArea() {
		double area = 0;
		
		for(Shape shape : shapes)
			area += shape.calculateArea();
		
		return area;
	}

	
	public void showMe(Graphics g) {
		for(Shape shape : shapes)
			shape.showMe(g);
		return;
	}

	
	public String toString() {
		String output = "Compound shape  with reference point " + referencePoint +": \n";
		
		for(Shape shape : shapes)
			output += shape.toString();
		
		return output;
	}

	
	public boolean shapeIsSelected(Coordinates positionOfMouse) {
		
		for(Shape shape : shapes){
			if(shape.shapeIsSelected(positionOfMouse)){
				shapeSelected = true;
				break;
			}
		}
		
		if(shapeSelected){
			for(Shape shape: shapes)
				shape.shapeSelected = true;
		}
		
		return shapeSelected;
	}
	
	public void resetShapeSelected(){
		super.resetShapeSelected();
		
		for(Shape shape: shapes)
			shape.shapeSelected = false;
		
		return;
	}
	
	protected void moveShape(Coordinates currentMousePosition){
		int x = 0, y = 0;
		
		for(Shape shape: shapes){			
			
			if(shape.onThePerimeter(currentMousePosition)){
				x = currentMousePosition.getX() - shape.lastMousePosition.getX();
				y = currentMousePosition.getY() - shape.lastMousePosition.getY();
			}
			
		}
		
		for(Shape shape: shapes){
			shape.moveCompoundShape(x, y, currentMousePosition);
		}
		
		return;
	}
	
	protected void moveCompoundShape(int x, int y, Coordinates currentMousePosition){
		super.moveCompoundShape(x, y, currentMousePosition);
		for(Shape shape: shapes){
			shape.referencePoint.setX(shape.referencePoint.getX() + x);
			shape.referencePoint.setY(shape.referencePoint.getY() + y);
			
			if(shape instanceof CompoundShape)
				shape.moveCompoundShape(x, y, currentMousePosition);
		}
		
		lastMousePosition = currentMousePosition;
		
		return;
	}

	
	
	public void addShape( Shape shape ){
		shapes.add( shape );
		
		return;
	}

	public void savePositionWhereUserPressedMouse(int x, int y){
		lastMousePosition = new Coordinates(x, y);
		
		for(Shape shape: shapes)
			shape.lastMousePosition = lastMousePosition;
		
		return;
	}
	
	public void changeColorBack(){
		super.changeColorBack();
		
		for(Shape shape: shapes)
			shape.changeColorBack();
		
		return;
	}
	
	public void changeColorTemporarily(){	 
		super.changeColorTemporarily();
		
		for(Shape shape: shapes)
			shape.changeColorTemporarily();
		
		return;
	}
}