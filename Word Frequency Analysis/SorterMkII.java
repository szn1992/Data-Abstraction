package main;
import phaseA.FourHeap;
import providedCode.Comparator;


/** 
 *  TODO: Replace this comment with your own as appropriate.
 *  Implement the sorting methods below. Do not change the provided method signature,
 *  but you may add as many other methods as you want.
 */
public class Sorter {
	
	/**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * This code uses insertion sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * @param counts array to be sorted.
	 * @param comparator for comparing elements.
     */
    public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
        for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j=i-1; j>=0; j--) {
                if (comparator.compare(x,array[j]) >= 0) { break; }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }
    
    public static <E> void heapSort(E[] array, Comparator<E> comparator) {
    	// TODO: To-be implemented
    	FourHeap<E> heap = new FourHeap<E>(comparator);
    	for(E element : array)
    		heap.insert(element);
    	int i = 0;
    	while(!heap.isEmpty()) {
    		array[i] = heap.deleteMin();
    		i++;
    	}
    }
    
    public static <E> void topKSort(E[] array, Comparator<E> comparator, int k) {
    	FourHeap<E> heap = new FourHeap<E>(comparator);
    	for(int i = 0; i < k; i++)
    		heap.insert(array[i]);
    	for(int j = k; j < array.length; j++) {
    		if(comparator.compare(heap.findMin(), array[j]) < 0); {
    			heap.deleteMin();
    			heap.insert(array[j]);
    		}
    	}
    	for(int a = 0; a < k; a++) 
    		array[k - 1 - a] = heap.deleteMin();
    	for(int b = 0; b < k; b++) 
    		System.out.println(array[b]);
    }
    
    public static <E> void otherSort(E[] array, Comparator<E> comparator) {
    	@SuppressWarnings("unchecked")
		E[] temp = (E[])new Object[array.length];
    	mergeSort(array, temp, 0, array.length, comparator);
    }
    
    private static <E> void mergeSort(E[] array, E[] temp, int left, int right, Comparator<E> comparator) {
    	if(left < right) {
    		int mid = (left + right) / 2;
    		mergeSort(array, temp, left, mid, comparator);
    		mergeSort(array, temp, mid + 1, right, comparator);
    		merge(array, temp, left, mid, right, comparator);
    	}
    }
    
    private static <E> void merge(E[] array, E[] temp, int left, int mid, int right, Comparator<E> comparator) {
    	int i, j, k, l, target;
    	i = left;
    	j = mid + 1;
    	target = left;
    	while(i <= mid && j <= right) {
    		if(comparator.compare(array[i], array[j]) <= 0)
    			temp[target] = array[i++];
    		else
    			temp[target] = array[j++];
    		target++;
    	}
    	
    	if(i > mid) {
    		for(k = left; k <= target; k++)
    			array[k] = temp[k];
    	}
    	
    	if (j > right) {
    		k = mid;
    		l = right;
    		while(k >= i)
    			array[l--] = array[k--];
    		for(k = left; k <= target - 1; k++)
    			array[k] = temp[k];
    	}
    }
}
