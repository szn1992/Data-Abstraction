// Zhuonan Sun && Jiahui Xu
// 3/10/2014
// CSE332 Project3 PhaseB
// Test file of FindPopulation

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestFindPopulation {
	private static final int TIMEOUT = 2000; // 2000ms = 2sec
	private CensusData result = PopulationQuery.parse("CenPop2010.txt");	
	UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, null, 20, 25, 0);
	private CensusData result2 = PopulationQuery.parse("test_population.txt");		// test txt. file that we created
	UnchangedInfo info2 = new UnchangedInfo(result2.data, result2.data_size, null, 20, 25, 0);
	
	@Before 
	public void setup() {
		info.censusRect = null;
		info.totalPop = 0;
		info2.censusRect = null;
		info2.totalPop = 0;
	}
	
	/** Test CensusRect =============================================================================================== **/
	@Test(timeout = TIMEOUT)
	public void test_getCensusRect_v1() {
		FindPopulation.getCensusRect(info, "-v1");
		check_censusRectangles(info.censusRect);
		
		FindPopulation.getCensusRect(info2, "-v1");
		check_censusRectangle_small_file(info2.censusRect);
	}
	
	public void test_getCensusRect_v2() {
		FindPopulation.getCensusRect(info, "-v2");
		check_censusRectangles(info.censusRect);
		
		FindPopulation.getCensusRect(info2, "-v2");
		check_censusRectangle_small_file(info2.censusRect);
	}
	
	public void test_getCensusRect_v3() {
		FindPopulation.getCensusRect(info, "-v3");
		check_censusRectangles(info.censusRect);
		
		FindPopulation.getCensusRect(info2, "-v3");
		check_censusRectangle_small_file(info2.censusRect);
	}
	public void test_getCensusRect_v4() {
		FindPopulation.getCensusRect(info, "-v4");
		check_censusRectangles(info.censusRect);
		
		FindPopulation.getCensusRect(info2, "-v4");
		check_censusRectangle_small_file(info2.censusRect);
	}
	public void test_getCensusRect_v5() {
		FindPopulation.getCensusRect(info, "-v5");
		check_censusRectangles(info.censusRect);
		
		FindPopulation.getCensusRect(info2, "-v5");
		check_censusRectangle_small_file(info2.censusRect);
	}
	
	private void check_censusRectangle_small_file(Rectangle rect) {
		assertEquals("left should be -86.486916", -86.486916, rect.left, 0.01);
		assertEquals("right should be -86.45751", -86.45751, rect.right, 0.01);
		assertEquals("top should be 0.5999871", 0.5999871, rect.top, 0.01);
		assertEquals("bottom should be 0.59962344", 0.59962344, rect.bottom, 0.01);
	}
	
	private void check_censusRectangles(Rectangle rect) {
		assertEquals("left should be -173.033", -173.033, rect.left, 0.01);
		assertEquals("right should be -65.30086", -65.30086, rect.right, 0.01);
		assertEquals("top should be 1.8039697", 1.8039697, rect.top, 0.01);
		assertEquals("bottom should be 0.31838202", 0.31838202, rect.bottom, 0.01);
	}

	/** Test Version 1 =================================================================================================== **/
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_small_file() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info2, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info2, rect);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 698", 698, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_small_file2() {
		Rectangle rect = new Rectangle(9, 20, 25, 1);
		FindPopulation.getCensusRect(info2, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info2, rect);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 5543", 5543, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_hawaii() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 1360301", 1360301, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_alaska() {
		Rectangle rect = new Rectangle(1, 9, 25, 12);
		FindPopulation.getCensusRect(info, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 710231", 710231, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_whole_grid() {
		Rectangle rect = new Rectangle(1, 20, 25, 1);
		FindPopulation.getCensusRect(info, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 312471327", 312471327, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_bottom_four_rows() {
		Rectangle rect = new Rectangle(1, 20, 4, 1);
		FindPopulation.getCensusRect(info, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 36493611", 36493611, pop.requiredPop);
	}
	@Test(timeout = TIMEOUT)
	public void test_simple_and_sequential_middle_three_columns() {
		Rectangle rect = new Rectangle(9, 11, 25, 1);
		FindPopulation.getCensusRect(info, "-v1");
		Population pop = FindPopulation.simpleAndSequential(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 52392739", 52392739, pop.requiredPop);
	}
	
	/** Test Version 2 =================================================================================================== **/
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_small_file() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info2, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info2, rect);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 698", 698, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_small_file2() {
		Rectangle rect = new Rectangle(9, 20, 25, 1);
		FindPopulation.getCensusRect(info2, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info2, rect);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 5543", 5543, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_hawaii() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 1360301", 1360301, pop.requiredPop);
	}

	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_alaska() {
		Rectangle rect = new Rectangle(1, 9, 25, 12);
		FindPopulation.getCensusRect(info, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 710231", 710231, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_whole_grid() {
		Rectangle rect = new Rectangle(1, 20, 25, 1);
		FindPopulation.getCensusRect(info, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 312471327", 312471327, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_bottom_four_rows() {
		Rectangle rect = new Rectangle(1, 20, 4, 1);
		FindPopulation.getCensusRect(info, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 36493611", 36493611, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_simple_and_parallel_middle_three_columns() {
		Rectangle rect = new Rectangle(9, 11, 25, 1);
		FindPopulation.getCensusRect(info, "-v2");
		Population pop = FindPopulation.simpleAndParallel(info, rect);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 52392739", 52392739, pop.requiredPop);
	}
	
	/** Test Version 3 =================================================================================================== **/
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential_small_file() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info2, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 698", 698, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential_small_file2() {
		Rectangle rect = new Rectangle(9, 20, 25, 1);
		FindPopulation.getCensusRect(info2, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 5543", 5543, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential_hawaii() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 1360301", 1360301, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential_alaska() {
		Rectangle rect = new Rectangle(1, 9, 25, 12);
		FindPopulation.getCensusRect(info, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 710231", 710231, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential__whole_grid() {
		Rectangle rect = new Rectangle(1, 20, 25, 1);
		FindPopulation.getCensusRect(info, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 312471327", 312471327, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential__bottom_four_rows() {
		Rectangle rect = new Rectangle(1, 20, 4, 1);
		FindPopulation.getCensusRect(info, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 36493611", 36493611, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_sequential__middle_three_columns() {
		Rectangle rect = new Rectangle(9, 11, 25, 1);
		FindPopulation.getCensusRect(info, "-v3");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 52392739", 52392739, pop.requiredPop);
	}
	
	/** Test Version 4 =================================================================================================== **/
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel_small_file() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info2, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 698", 698, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel_small_file2() {
		Rectangle rect = new Rectangle(9, 20, 25, 1);
		FindPopulation.getCensusRect(info2, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 5543", 5543, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel_hawaii() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 1360301", 1360301, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel_alaska() {
		Rectangle rect = new Rectangle(1, 9, 25, 12);
		FindPopulation.getCensusRect(info, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 710231", 710231, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel__whole_grid() {
		Rectangle rect = new Rectangle(1, 20, 25, 1);
		FindPopulation.getCensusRect(info, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 312471327", 312471327, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel__bottom_four_rows() {
		Rectangle rect = new Rectangle(1, 20, 4, 1);
		FindPopulation.getCensusRect(info, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 36493611", 36493611, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_parallel__middle_three_columns() {
		Rectangle rect = new Rectangle(9, 11, 25, 1);
		FindPopulation.getCensusRect(info, "-v4");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 52392739", 52392739, pop.requiredPop);
	}
	
	/** Test Version 5 =================================================================================================== **/
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based_small_file() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info2, "-v5");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 698", 698, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based_small_file2() {
		Rectangle rect = new Rectangle(9, 20, 25, 1);
		FindPopulation.getCensusRect(info2, "-v5");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info2, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info2.totalPop);
		assertEquals("Total population should be 7455", 7455, pop.totalPop);
		assertEquals("Required population should be 5543", 5543, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based_hawaii() {
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		FindPopulation.getCensusRect(info, "-v2");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 1360301", 1360301, pop.requiredPop);
	}

	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based_alaska() {
		Rectangle rect = new Rectangle(1, 9, 25, 12);
		FindPopulation.getCensusRect(info, "-v2");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 710231", 710231, pop.requiredPop);
	}
	 
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based__whole_grid() {
		Rectangle rect = new Rectangle(1, 20, 25, 1);
		FindPopulation.getCensusRect(info, "-v5");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 312471327", 312471327, pop.requiredPop);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based__bottom_four_rows() {
		Rectangle rect = new Rectangle(1, 20, 4, 1);
		FindPopulation.getCensusRect(info, "-v5");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 36493611", 36493611, pop.requiredPop);
	}
	

	@Test(timeout = TIMEOUT)
	public void test_smarter_and_lock_based__middle_three_columns() {
		Rectangle rect = new Rectangle(9, 11, 25, 1);
		FindPopulation.getCensusRect(info, "-v5");
		int[][] grid = new int[20][25];
		grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v5");
		Population pop = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
		assertEquals("Total population should be 312471327", 312471327, pop.totalPop);
		assertEquals("Required population should be 52392739", 52392739, pop.requiredPop);
	}
}	