// Jiahui Xu				
// Zhuonan Sun						
// 1/30/2014					
// CSE 332 A					
// Project 2 Phase A 

package test;
import static org.junit.Assert.*;

import java.util.Arrays;

import main.Sorter;

import org.junit.Before;
import org.junit.Test;

import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCountStringComparator;
import providedCode.DataCountStringComparator2;


public class TestSorter {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private Comparator<Integer> comp = new Comparator<Integer>() {
        public int compare(Integer e1, Integer e2) {
            return (Integer) e1 - (Integer) e2;
        }
    };
	private Comparator<DataCount<String>> comp2 = new DataCountStringComparator2();

	
	/** Test Size =======================================================================================**/
	// test heap size when heap is zero
	@Test(timeout = TIMEOUT)
	public void test_heap_sort_empty() {
		Integer[] array = new Integer[0];
		sort(array);
		assertEquals("This array is empty", 0, array.length);
	}
	
	// test heap elements after adding one element to the heap
	@Test(timeout = TIMEOUT)
	public void test_sort_one_num() {
		Integer[] array = new Integer[1];
		array[0] = 1;
		sort(array);
		assertEquals("This array has one number", new Integer(1), array[0]);
	}
	
	// test heap elements when adding multiple different numbers to the heap
	@Test(timeout = TIMEOUT)
	public void test_sort_multiple_diff_num() {
		Integer[] array = new Integer[4];
		array[0] = 1;
		array[1] = 5;
		array[2] = 3;
		array[3] = 2;
		sort(array);
		assertArrayEquals("This array has 4 numbers", new Integer[]{1, 2, 3, 5}, array);
	}
	
	// test heap elements after adding multiple same numbers into the heap
	@Test(timeout = TIMEOUT)
	public void test_sort_duplicate_num() {
		Integer[] array = new Integer[]{1, 1, 3, 3, 2, 2};
		sort(array);
		assertArrayEquals("This array has six sorted numbers", new Integer[]{1, 1, 2, 2, 3, 3}, array);
	}
	

	// if two heaps are the same after adding a hundred numbers to each of them in different order
	@Test(timeout = TIMEOUT)
	public void test_sort_hundred_num() {
		Integer[] expected = new Integer[100];
		Integer[] actual = new Integer[100];
		for (int i = 0; i < 100; i++) {
			expected[i] = i;
		}
		int count = 0;
		for (int i = 99; i >= 0; i--) {
			actual[count] = i;
			count++;
		}
		
		sort(actual);
		assertArrayEquals("This two arrays should be the same", expected, actual);
	}

	/* test topKSort *///////////////////////////////////////////////////////////////////
	@Test(timeout = TIMEOUT)
	public void test_top_one_num() {
		DataCount[] array = {new DataCount<String>("b", 1), new DataCount<String>("a", 2)};
		DataCount[] correct = {new DataCount<String>("a", 2), new DataCount<String>("a", 2)};
		
		testTopKSort(array, 1);
		checkDataAndCount(array, correct);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_top_multiple_num() {
		DataCount[] array = {new DataCount<String>("b", 1), new DataCount<String>("z", 2), 
							new DataCount<String>("cj", 2), new DataCount<String>("a", 5), 
							new DataCount<String>("wow", 8), new DataCount<String>("ch", 2)};
		
		DataCount[] correct = {new DataCount<String>("wow", 8), new DataCount<String>("a", 5), 
							new DataCount<String>("ch", 2), new DataCount<String>("cj", 2), 
							new DataCount<String>("wow", 8), new DataCount<String>("ch", 2)};
		testTopKSort(array, 4);
		checkDataAndCount(array, correct);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_top_same_frequncy() {
		DataCount[] array = {new DataCount<String>("cbb", 8), new DataCount<String>("cbc", 8), 
							new DataCount<String>("c", 8), new DataCount<String>("cb", 8), 
							new DataCount<String>("cz", 8), new DataCount<String>("a", 8)};
		
		DataCount[] correct = {new DataCount<String>("a", 8), new DataCount<String>("c", 8), 
							new DataCount<String>("cb", 8), new DataCount<String>("cbb", 8), 
							new DataCount<String>("cbc", 8), new DataCount<String>("a", 8)};
		testTopKSort(array, 5);
		checkDataAndCount(array, correct);
	}
	

	@Test(timeout = TIMEOUT)
	public void test_k_is_zero() {
		DataCount[] array = {new DataCount<String>("cbb", 8), new DataCount<String>("cbc", 8), 
							new DataCount<String>("c", 8), new DataCount<String>("cb", 8), 
							new DataCount<String>("cz", 8), new DataCount<String>("a", 8)};
		
		DataCount[] correct = {new DataCount<String>("cbb", 8), new DataCount<String>("cbc", 8), 
								new DataCount<String>("c", 8), new DataCount<String>("cb", 8), 
								new DataCount<String>("cz", 8), new DataCount<String>("a", 8)};
		testTopKSort(array, 0);
		checkDataAndCount(array, correct);
	}
	
	/**=====================================================================================================
	 private helper methods
	 */
	
	// Given an array of Integers, sort this array in descending order, and resolve ties by comparator
	private void sort(Integer[] array) {
		Sorter.heapSort(array, comp);
		Sorter.otherSort(array, comp);
	}
	
	// test topKSort
	private void testTopKSort(DataCount<String>[] array, int k) {
		Sorter.topKSort(array, comp2, k);
	}
	
	private void checkDataAndCount(DataCount<String>[] array, DataCount<String>[] correct) {

		
		for(int i = 0; i < array.length; i++) {
			System.out.println(array[i].data + "  " + correct[i].data);
			assertEquals("data should be " + correct[i].data , correct[i].data, array[i].data);
			
			assertEquals("count should be " + correct[i].count, correct[i].count, array[i].count);
		}
	}
}
