package proj;

import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("serial")
public class OutputFrame extends JFrame implements Observer{
	final int WIDTH = 500, HEIGHT = 300;
	private JTextArea outputArea;

	public OutputFrame(){
		super("Rectangles");
		outputArea = new JTextArea(20, 30);
		add(outputArea);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		
		return;
	}
	
	public void displayResult(String s){
		outputArea.setText(s);
		
		return;
	}
	
	public void update( Observable o, Object obj ){
		String output ="";	
		
		ShapeList.ShapeIterator iterator =  ( ShapeList.ShapeIterator )obj;
		
		iterator.first();
		while( !iterator.isDone() ){
			if( ( iterator.currentItem() ) instanceof Rectangle || ( iterator.currentItem() ) instanceof Square )
				output += ( iterator.currentItem() ).toString();
			
			iterator.next();
		}
		
		displayResult( output );
		
		return;
	}
}


