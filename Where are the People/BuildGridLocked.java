import java.util.concurrent.ForkJoinPool;


public class BuildGridLocked extends BuildGrid{

	private static final long serialVersionUID = 1L;
	// one shared grid that different threads add to

	public int[][] grid;
	private Object[][] locks;
	private static final int THREADS = 4;
	
	// each element should have a different lock
	public BuildGridLocked(UnchangedInfo info, int low, int high, ForkJoinPool fjPool) {
		super(info, low, high, fjPool);
		grid = new int[x][y];
		locks = new Object[x][y];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				locks[i][j] = new Object();
			}
		}
		BuildGridLockedHelper[] initialLockedGrid = new BuildGridLockedHelper[THREADS];
		// divide the input array and start all of the threads
		for (int i = 0; i < THREADS; i++) {
			initialLockedGrid[i] = new BuildGridLockedHelper((i * info.size) / THREADS,
					(i + 1) * info.size / THREADS);
			initialLockedGrid[i].start();
		}
		
		// join all of the THREADS after computation completes
		for (int i = 0; i < THREADS; i++) {
			try {
				initialLockedGrid[i].join();
			} catch (InterruptedException e) {
				System.out.println("The join process has been interrupted.");
				e.printStackTrace();
			}
		}
	}


	// inner class
	private class BuildGridLockedHelper extends Thread {
		int hi, lo;
		
		public BuildGridLockedHelper(int lo, int hi) {
			this.hi = hi;
			this.lo = lo;
		}
		
		public void run() {		
			if (hi - lo <= SEQUENTIAL_CUTOFF) {
				// each parallel subproblem needs to return a grid
				// build grid
				CensusGroup[] blocks = info.input; 
				float censusX = censusRect.right - censusRect.left;
				float censusY = censusRect.top - censusRect.bottom;
				int gridX, gridY;
				for(int j = lo; j < hi; j++) {
					gridX = (int) ((blocks[j].longitude - censusRect.left) * x / censusX);
					gridY = (int) ((blocks[j].latitude - censusRect.bottom) * y / censusY);
					if(gridX == x) 
						gridX--;
					
					if(gridY == y) 
						gridY--;
					
					synchronized(locks[gridX][gridY]) {
						grid[gridX][gridY] += blocks[j].population;
					}
				}
			} else {
				BuildGridLockedHelper left = new BuildGridLockedHelper(lo, (lo + hi) / 2);
				BuildGridLockedHelper right = new BuildGridLockedHelper((lo + hi) /2, hi);				
				left.start();
				right.run();
				try {
					left.join();
				} catch (InterruptedException e) {
					System.out.println("The join process has been interrupted.");
					e.printStackTrace();
				} 
			}	
		}
	}

}
