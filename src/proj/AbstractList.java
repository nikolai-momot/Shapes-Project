package proj;

public abstract class AbstractList{
	public abstract AbstractIterator createIterator(int numElements);
	
	public abstract void append(Shape v);
	
	public abstract class AbstractIterator implements MyIterator{ }
}
