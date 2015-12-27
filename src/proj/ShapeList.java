package proj;

import java.util.ArrayList;

public class ShapeList extends AbstractList{
	private ArrayList<Shape> table;

	public ShapeList(){
		table = new ArrayList<Shape>(100);
	}
	
	public class ShapeIterator extends AbstractIterator implements MyIterator{
		private ArrayList<Shape> result;			// result stores the array to be returned
		private int currentElement;
		private boolean endOfTable;
		
		public ShapeIterator( int size ){
			result  = new ArrayList<Shape>( size );
			
			for (int i = 0; i < table.size(); i++){
				result.add(table.get(i));
			}
			
			return;
		}
		
		// first makes the first item in the list the current item
		public void first(){
			currentElement = 0;
			
			if (result.size() > 0) endOfTable = false;
			else endOfTable = true;
			
			return;
		}
		
		// next makes the next item in the list the current item
		public void next(){
			if (currentElement < result.size() - 1)
				currentElement ++;
			else endOfTable = true;
			
			return;
		}

		// isDone is true  when the current item has gone past the last item
		public boolean isDone(){
			return endOfTable;
		}

		// currentItem retrieves the value in current item
	    public Shape currentItem(){
	    	return result.get(currentElement);
	    }
	}
	
	public ShapeIterator createIterator(int numElements){
		 return new ShapeIterator( numElements );
	}
	
	public void append(Shape shape){
		table.add(shape);
		
		return;
	}
	
	public void removeShape( Shape shape ){
		if( table.contains( shape ) )
			table.remove(shape);
	}
}
