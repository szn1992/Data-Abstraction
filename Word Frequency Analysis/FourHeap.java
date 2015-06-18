package phaseA;
import java.util.NoSuchElementException;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. It is exactly like the binary heap we studied, except nodes should have 4 children 
 * 	  instead of 2. Only leaves and at most one other node will have fewer children. 
 * 2. Use array-based implementation, beginning at index 0 (Root should be at index 0). 
 *    Construct the FourHeap by passing appropriate argument to superclass constructor.
 *    Hint: Complete written homework #2 before attempting this.   
 * 3. Throw appropriate exceptions in FourHeap whenever needed. For example, 
 * 	  when deleteMin on an empty heap, you could use UnderFlowException as is done in the Weiss text, 
 *    or you could use NoSuchElementException (in which case it will be fine if you want to import it). 
 * TODO: Develop appropriate JUnit tests for your FourHeap.
 */

// FourHeap implements Heap structure. FourHeap will have four children per node,
// and the smallest value will always be in the parent root of the heap. 
public class FourHeap<E> extends Heap<E> {
	private Comparator<? super E> comparator;
	private int size;
	private E[] heapArray;
	
	// Construct an empty FourHeap, pass a Comparator as an argument
	public FourHeap(Comparator<? super E> c) {
		comparator = c;
		heapArray = (E[])new Object[100];
		size = 0;
	}

	@Override
	// Insert the new value into the heap, and increases size of the heap
	// If the heap is full, double the size of the heap
	public void insert(E item) {
		if(size == heapArray.length - 1) {
			E[] newArray = (E[]) new Object[heapArray.length * 2];
			for(int i = 0; i < heapArray.length; i++) 
				newArray[i] = heapArray[i];
			heapArray = newArray;
		} 		
		size++;
		heapArray[size - 1] = item;
		percolateUp(size - 1);
	}
	
	// Private method that moves an element up to the heap,
	// if the value is less than its parent's value
	private void percolateUp(int hole) {
		while(hole > 0) {
			int parent = (int) Math.floor((hole - 1) / 4);
			if (comparator.compare(heapArray[hole], heapArray[parent]) < 0) {
				E temp = heapArray[hole];
				heapArray[hole] = heapArray[parent];
				heapArray[parent] = temp;
				hole = parent;
			} else {
				break;
			}
		}
	}

	@Override
	// Return the minimum value in the heap
	// Throw exception if the heap is empty
	public E findMin() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return heapArray[0];
	}

	@Override
	// Delete the minimum value in the heap
	// throw exception if the heap is empty
	public E deleteMin() {
		if(isEmpty())
			throw new NoSuchElementException();
		E minItem = findMin();
		heapArray[0] = heapArray[size - 1];
		percolateDown(0);
		size--;
		return minItem;
	}
	
	// Private method that moves an element down the FourHeap
	// if it is greater than the value of its children. 
	// Takes the index which will be filled by percolating.
	private void percolateDown(int hole) {		
		//boolean finish = false;
		while(true) {
			int min = hole;
			int i = hole * 4 + 1;
			for(; i <= size - 1 && i <= (hole * 4) + 4; i++) {
				if(comparator.compare(heapArray[i], heapArray[min]) < 0) {
					min = i;
				}
			}
			if (comparator.compare(heapArray[hole], heapArray[min]) > 0) {
				E minValue = heapArray[min];
				heapArray[min] = heapArray[hole];
				heapArray[hole] = minValue;
				hole = min;
			} else {
				break;
			}
		}
	}

	@Override
	// Return a boolean on whether the heap is
	// empty or not
	public boolean isEmpty() {
		return size == 0;
	}
}