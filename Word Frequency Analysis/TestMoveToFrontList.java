package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import phaseA.MoveToFrontList;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;
import test.TestDataCounter;


public class TestMoveToFrontList extends TestDataCounter<Integer> {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec

	
	/** Creates MoveToFrontList before each test cases **/
	public DataCounter<Integer> getDataCounter() {
		return new MoveToFrontList<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}

	
	/** Test Size =======================================================================================**/
	@Test(timeout = TIMEOUT)
	public void test_size_empty() {
		assertEquals("List should have size 0 when constructed", 0, dc.getSize());
	}                                                 
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_single_num() {
		dc.incCount(0);
		assertEquals("List should have size 1", 1, dc.getSize());
	}
	
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_mutiple_num() {
		dc.incCount(0);
		dc.incCount(1);
		dc.incCount(2);
		assertEquals("List should have size 3", 3, dc.getSize());
	}
	
	public void test_size_after_adding_mutiple_same_num() {
		dc.incCount(0);
		dc.incCount(0);
		dc.incCount(0);
		dc.incCount(0);
		assertEquals("List should have size 4", 4, dc.getSize());
	}
	
	/** Test incCount() =======================================================================================**/
	@Test(timeout = TIMEOUT)
	public void test_incCount_after_adding_one_num() {
		dc.incCount(1);
		assertNotNull(dc);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_incCount_after_adding_multiple_num() {
		dc.incCount(0);
		dc.incCount(0);
		dc.incCount(1);
		dc.incCount(0);
		assertNotNull(dc);
	}	
	
	/** Test getCount() =======================================================================================**/
	@Test(timeout = TIMEOUT)
	public void test_get_count_with_empty_list() {
		assertEquals("List should be empty", 0, dc.getCount(1));	
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_different_num() {
		dc.incCount(0);
		dc.incCount(1);
		assertEquals("List should have 1 zero", 1, dc.getCount(0));	
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_same_num() {
		dc.incCount(0);
		dc.incCount(1);
		dc.incCount(0);
		dc.incCount(1);
		dc.incCount(0);
		dc.incCount(1);
		assertEquals("List should have 3 zeros", 3, dc.getCount(0));	
	}
			
	@Test(timeout = TIMEOUT)
	public void test_get_count_if_num_not_in_the_list() {
		dc.incCount(0);
		dc.incCount(1);
		assertEquals("List should have 0 two", 0, dc.getCount(2));	
	}
	
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_hundred_num() {
		for (int i = 0; i < 100; i++) {
			dc.incCount(5);
		}
		assertEquals("List should have 100 fives", 100, dc.getCount(5));	
	}
	
	/** Test getIterator() =======================================================================================**/
	
	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_empty() {
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		iter.next();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_has_next() {
		dc.incCount(1);
		dc.incCount(2);
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		assertTrue(iter.hasNext());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_get_one_num() {
		dc.incCount(1);
		SimpleIterator<DataCount<Integer>> it = dc.getIterator();
		assertTrue(it.next().data.equals(1));
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_get_multiple_num() {
		dc.incCount(1);
		dc.incCount(2);
		dc.incCount(3);
		dc.incCount(4);
		dc.incCount(3);
		dc.incCount(2);
		SimpleIterator<DataCount<Integer>> it = dc.getIterator();
		assertTrue(it.next().data.equals(2));
		assertTrue(it.next().data.equals(3));
		assertTrue(it.next().data.equals(4));
		assertTrue(it.next().data.equals(1));
	}
	

	
}
