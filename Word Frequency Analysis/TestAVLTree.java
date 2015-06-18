package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import phaseA.AVLTree;
import providedCode.Comparator;
import providedCode.DataCounter;
import test.TestDataCounter;


public class TestAVLTree extends TestDataCounter<Integer> {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	
	@Override
	public DataCounter<Integer> getDataCounter() {
		return new AVLTree<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}
	
	/** Test height ===========================================================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_height_if_tree_is_null() {
		int key = 5;
		int[] testArray = {};
		addAndTestHeight("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, -1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_left_rotation(){
		int key = 5;
		int[] testArray = {5, 6, 4, 3, 1};
		addAndTestHeight("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_left_right_rotation(){
		int key = 5;
		int[] testArray = {6, 3, 2, 5, 7, 4};
		addAndTestHeight("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_left_rotation(){
		int key = 6;
		int[] testArray = {4, 1, 7, 8, 6, 5};
		addAndTestHeight("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 2);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_height_after_right_right_rotation(){
		int key = 4;
		int[] testArray = {4, 1, 5, 8, 10};
		addAndTestHeight("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 2);
	}
	
	
	/** Private methods =======================================================================================**/
	private void addAndTestHeight(String message, int[] input, int key, int expected) {
		for(int num : input) { dc.incCount(num); }
		assertEquals(message, expected, ((AVLTree<Integer>)dc).getHeight(key));
	}
	
}


