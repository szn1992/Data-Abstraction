package main;
import phaseA.FourHeap;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCountStringComparator2;


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

    	for (int i = 0; i < k; i++) 
    		heap.insert(array[i]);
    
    	if(k == 0) {
    		return;
    	}
    	for(int j = k; j < array.length; j++) {
    		if(comparator.compare(heap.findMin(), array[j]) < 0) {
    			heap.deleteMin();
    			heap.insert(array[j]);
    		}
    	} 
    	
    	for(int a = 0; a < k; a++) 
    		array[k - a - 1] = heap.deleteMin();
    	
    }
      

    public static <E> void otherSort(E[] array, Comparator<E> comparator) {
		E[] temp = (E[])new Object[array.length];
    	mergeSort(array, temp, 0, array.length - 1, comparator);
    }
    
    private static <E> void mergeSort(E[] array, E[] temp, int left, int right, Comparator<E> comparator) {
    	if(left < right) {
    		int mid = (left + right) / 2;
    		mergeSort(array, temp, left, mid, comparator);
    		mergeSort(array, temp, mid + 1, right, comparator);
    		merge(array, temp, left, mid + 1, right, comparator);
    	}
    }
    
    private static <E> void merge(E[] array, E[] temp, int leftPos, int rightPos, int rightEnd, Comparator<E> comparator) {
    	int leftEnd = rightPos - 1;
    	int tmpPos = leftPos;
    	int numElements = rightEnd - leftPos + 1;
    	
    	while(leftPos <= leftEnd && rightPos <= rightEnd) {
    		if(comparator.compare(array[leftPos], array[rightPos]) <= 0)
    			temp[tmpPos++] = array[leftPos++];
    		else
    			temp[tmpPos++] = array[rightPos++];
    	}
    	
    	while(leftPos <= leftEnd)
    		temp[tmpPos++] = array[leftPos++];
    	while(rightPos <= rightEnd)
    		temp[tmpPos++] = array[rightPos++];
    	for(int i = 0; i < numElements; i++, rightEnd--)
    		array[rightEnd] = temp[rightEnd];
    }
}
