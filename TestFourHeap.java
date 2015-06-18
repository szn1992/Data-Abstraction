package testA;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import phaseA.FourHeap;
import phaseA.MoveToFrontList;
import providedCode.Comparator;
import providedCode.DataCounter;
import providedCode.Heap;
import test.TestDataCounter;


public class TestFourHeap{
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private FourHeap<Integer> heap;
	
	/** Creates FourHeap before each test cases **/
    @Before
    public void setUp() throws Exception {
            heap = new FourHeap<Integer>(new Comparator<Integer>() {
                    public int compare(Integer e1, Integer e2) {
                            return e1 - e2;
                    };
            });
    }
    
	/** Test Size =======================================================================================**/
	@Test(timeout = TIMEOUT)
	public void test_empty_heap_size() {
		assertTrue("The heap is empty", heap.isEmpty());
	}
	
	
	@Test(timeout = TIMEOUT)
	public void test_heap_size_after_adding_one_num() {
		heap.insert(1);
		assertNotNull(heap);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_heap_size_after_adding_multiple_diff_num() {
		heap.insert(1);
		heap.insert(2);
		heap.insert(3);
		assertNotNull(heap);
	}
	
	
	/** Test findMin =======================================================================================**/

	@Test(timeout = TIMEOUT)
	public void test_findMin_after_inserting_one_num() {
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("Find minimum", 1, expected);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_findMin_after_inserting_multiple_diff_num() {
		heap.insert(3);
		heap.insert(1);
		heap.insert(2);
		heap.insert(-1);
		int expected = heap.findMin();
		assertEquals("Find minimum", -1, expected);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_findMin_after_inserting_multiple_same_num() {
		heap.insert(-3);
		heap.insert(-3);
		heap.insert(-3);
		heap.insert(-1);
		heap.insert(-1);
		heap.insert(-1);
		int expected = heap.findMin();
		assertEquals("Find minimum", -3, expected);
	}
	
	
	/** Test deleteMin =======================================================================================**/

	
	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_heap_size_after_deleting_empty_heap() {
		heap.deleteMin();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_heap_size_after_inserting_deleting() {
		heap.insert(1);
		heap.deleteMin();
		assertTrue(heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_heap_size_after_deleting() {
		heap.insert(1);
		heap.insert(2);
		heap.deleteMin();
		assertFalse(heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void test_find_min_after_deleting() {
		heap.insert(1);
		heap.insert(2);
		heap.insert(2);
		heap.deleteMin();
		int min = heap.findMin();
		assertEquals("Minimum should be 2", 2, min);
	}
	
	public void test_delete_min_after_hundred_remove() {
		for (int i = 0; i < 100; i++) {
			heap.insert(i);
		}
		
		for (int i = 0; i < 100; i++) {
			int actual = heap.deleteMin();
			assertEquals(i, actual);
		}
		
	}


	
	
	
	
	

	
	

}
