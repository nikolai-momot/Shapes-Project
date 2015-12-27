package proj;

public interface MyIterator{
	public void first(); // first makes the first item in the list the current item

	public void next(); // next makes the next item in the list the current item

	public boolean isDone();// isDone is true  when the current item has gone past the last item

    public Shape currentItem(); // currentItem retrieves the value in current item
}
