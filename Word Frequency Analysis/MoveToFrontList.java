package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items (with a count of 1) to the front of the list.
 * 3. Whenever an existing item has its count incremented by incCount or 
 *    retrieved by getCount, move it to the front of the list. That means 
 *    you remove the node from its current position and make it the first 
 *    node in the list.
 * 4. You need to implement an iterator. The iterator should not move elements
 *    to the front. The iterator should return elements in the order they are 
 *    stored in the list, starting with the first element in the list.
 * TODO: Develop appropriate JUnit tests for your MoveToFrontList.
 */
public class MoveToFrontList<E> extends DataCounter<E> {
	private ListNode front;
	private int size;
	private Comparator<? super E> comparator;
	
	public MoveToFrontList(Comparator<? super E> c) {
		// TODO: To-be implemented
		size = 0;
		front = null;
		comparator = c;
	}
	
	@Override
	// Given a value of type E, increment the count of this value
	// in the list. Moves this value to the front of the list.
	public void incCount(E data) {
		ListNode curr = front;
		boolean find = false;
		
		if (front == null) {
			front = new ListNode(data, 1);
			size++;
			find = true;
		}
		// if the value is in the first element of the list
		// then keep this value in the first node
		if (comparator.compare((E)curr.data, data) == 0) {
			curr.count++;
			find = true;
		} else {
			while (curr.next != null) {
				if(comparator.compare((E)curr.next.data, data) == 0) {
					// find the value in the middle of the list
					find = true;
					curr.count++;
					// move this node to the front of the list
					ListNode temp = curr.next;
					curr.next = temp.next;
					temp.next = front;
					front = temp;
					
				}
				curr = curr.next;
			}
		}
		
		// if the value is not in the list
		if (!find) {
			front = new ListNode(data, front.next, 1);
			size++;
		}
	}

	@Override
	// return the size of the list
	public int getSize() {
		return size;
	}

	@Override
	// Given a value of type E, returns the corresponding count for
	// that word. Return 0 if the element is not in the list. 
	// Move this element to the front of the list.
	public int getCount(E data) {
		ListNode curr = front;
		int count = 0;
		
		// if the value is in the first node, then keep this value
		// in the first node
		if (comparator.compare((E)curr.data, data) == 0) {
			count = curr.count;
		} else {
			while (curr.next != null) {
				if (comparator.compare((E)curr.next.data, data) == 0) {
					count = curr.count;
					ListNode temp = curr.next;
					curr.next = temp.next;
					temp.next = front;
					front = temp;
				}
				curr = curr.next;
			}
			
		}	
		return count;
	}

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			public ListNode current = front;
			
			public DataCount<E> next() {
				if(!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
				
				DataCount<E> count = new DataCount(current.data, current.count);
				current = current.next;
				return count;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return current != null;
			}
		};
	}
	
	// inner class for linked list nodes
	// ListNode class is a class for storing a single node of
	// a linked list. This node class is a list of String values. 
	private class ListNode {
		private E data;
		private ListNode next;
		private int count;
		
		
		// constructor
		public ListNode(E data, int count) {
			this(data, null, 0);
		}
		
		// constructor
		public ListNode(E data, ListNode next, int count) {
			this.data = data;
			this.next = next;
			this.count = count;
		}
	}

}
