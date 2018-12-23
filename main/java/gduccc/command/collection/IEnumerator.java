package gduccc.command.collection;
public interface IEnumerator<T> {
	T getCurrent();
	
	Boolean moveNext();
	
	void reset();
}
