package proj;
import java.awt.*;
import javax.swing.*;	
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("serial")
public class FrameShowingBigCircles extends JFrame implements Observer{
	Shape myShapes[];
	int numberOfShapes;
	
	public  void showCircles(ShapeList.ShapeIterator iterator){
		int i = 0;
		iterator.first();
		while( !iterator.isDone() ){
			myShapes[i++] = iterator.currentItem();
			iterator.next();
		}
		numberOfShapes = myShapes.length;
		repaint();
		
		return;
	}
	
	public void paint(Graphics g){
		Circle currentCircle;
		super.paint(g);
		//System.out.println("N = " + numberOfShapes);
		for (int index = 0; index < numberOfShapes; index ++){
			if (myShapes[index] instanceof Circle){
				currentCircle = (Circle) myShapes[index];
				if (currentCircle.calculateArea() >= 5000.00){
					currentCircle.showMe(g);
				}			
			}
		}	
		
		return;
	}
	public FrameShowingBigCircles(){
		super("Big Circles");
		myShapes = new Shape[100];
		setSize(TesterFrame.WIDTH, TesterFrame.HEIGHT); 
		setVisible(true);    
		setBackground(Color.WHITE);
		
		return;
	}

	public void update( Observable obs, Object obj){
		
		ShapeList.ShapeIterator iterator = ( ShapeList.ShapeIterator )obj;

		showCircles( iterator );
		
		return;
	}
}
