package phaseB;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You may implement any kind of HashTable discussed in class; the only 
 *    restriction is that it should not restrict the size of the input domain 
 *    (i.e., it must accept any key) or the number of inputs (i.e., it must 
 *    grow as necessary).
 * 2. You should use this implementation with the -h option.
 * 3. To use your HashTable for WordCount, you will need to be able to hash 
 *    strings. Implement your own hashing strategy using charAt and length. 
 *    Do not use Java's hashCode method.
 * TODO: Develop appropriate JUnit tests for your HashTable.
 */
public class HashTable<E> extends DataCounter<E> {
	private int size, sizeIndex;
	private Comparator<? super E> comparator;
	private Hasher<E> hasher;
	
	private ListNode[] array;
	private static final int[] TABLE_SIZE = {2, 5, 11, 23, 47, 97, 199, 401, 809, 1619, 3251, 
											6521, 13043, 26099, 52201, 104417, 208837};
	
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		size = 0;
		comparator = c;
		hasher = h;
		sizeIndex = 0;
		array = new HashTable.ListNode[TABLE_SIZE[sizeIndex]];
	}
	
	@Override
	public void incCount(E data) {
		if((double) size / array.length >= 0.5)
			array = rehash(array);
		
		int index = getIndex(data);
		if(array[index] == null) {
			array[index] = new ListNode(data);
			size++;
		} else {
			ListNode temp = array[index];
			if(comparator.compare(temp.data, data) == 0) {
				temp.count++;
				return;
			}
			while(temp.next != null) {
				temp = temp.next;
				if(comparator.compare(temp.data, data) == 0) {
					temp.count++;
					return;
				}
			}
			temp.next = new ListNode(data);
			size++;
		}
	}
	
	private ListNode[] rehash(ListNode[] array) {
		ListNode[] oldArray = array;
		array = new HashTable.ListNode[TABLE_SIZE[++sizeIndex]];
		
		for(int i = 0; i < oldArray.length; i++) {
			int index;
			ListNode list = oldArray[i];
			ListNode temp;
			
			while(list != null) {
				index = getIndex(list.data);
				temp = list;
				list = list.next;
				temp.next = array[index];
				array[index] = temp;
			}
		}
		return array;
	}
	
	private int getIndex(E key) {
		int hashVal = hasher.hash(key) % TABLE_SIZE[sizeIndex];
		if(hashVal < 0)
			hashVal += TABLE_SIZE[sizeIndex];
		return hashVal;
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(E data) {
		int index = getIndex(data);
		ListNode temp = array[index];
		
		while(temp != null) {
			if(comparator.compare(temp.data, data) == 0)
				return temp.count;
			temp = temp.next;
		}
		return 0;
	}

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			// record the number of items iterated so far
    		public int countNum = 0;
    		// find the first item in the table
    		public int index = 0;
    		// find bucket
    		public ListNode curr = array[0];
            
            
    		public boolean hasNext() {
        		return countNum < size;
        	}
        	public DataCount<E> next() {
				if(!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
				
				DataCount<E> dCount = null;
				// when current bucket is null
				if (curr == null) {
					for (int i = index + 1; i < array.length; i++) {
						if(array[i] != null) {
							index = i;
							curr = array[index];
							dCount = new DataCount<E>(curr.data, curr.count);
							curr = curr.next;
							break;
						}
					}
				} else {
					dCount = new DataCount<E>(curr.data, curr.count);
					curr = curr.next;
				}
				countNum++;
				return dCount;
                
        	}
    	};
	}

	public int getTableSize() {
		return TABLE_SIZE[sizeIndex];
	}
	
	public class ListNode {
		private E data;
		private ListNode next;
		private int count;
		
		public ListNode(E data) {
			this.data = data;
			next = null;
			count = 1;
		}
		
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
			count = 1;
		}
	}
}
