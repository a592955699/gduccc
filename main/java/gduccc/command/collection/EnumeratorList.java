package gduccc.command.collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EnumeratorList<T> extends ArrayList<T>
implements IEnumerator<T> {
	public EnumeratorList()
	{
		
	}
	public EnumeratorList(int initialCapacity)
	{
		super(initialCapacity);  
	}
	
	public EnumeratorList(Collection<? extends T> c)
	{
		super(c);  
	}

	private int currentIndex=0;
	
	@Override
	public T getCurrent() {
		if(size()>0 && currentIndex<=size())
		{
			return get(currentIndex-1);
		}
		else
		{
			return null;
		}
	}

	@Override
	public Boolean moveNext() {
		
		if(size()>0&& currentIndex+1<=size())
		{
			currentIndex++;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void reset() {
		currentIndex=0;
		// TODO 自动生成的方法存根
	}
}
