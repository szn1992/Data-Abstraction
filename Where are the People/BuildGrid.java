import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

// For version 4, smarter and parallel
public class BuildGrid extends RecursiveTask<int[][]>{

	private static final long serialVersionUID = 1L;
	int x, y, lo, hi;
	public Rectangle censusRect;
	public UnchangedInfo info;
	ForkJoinPool fjPool;
	static final int SEQUENTIAL_CUTOFF = 500;
	// rect is the user queried rectangle
	// result is american census data
	// x and y is the width and height of the whole rectangle
	
	public BuildGrid(UnchangedInfo info, int low, int high, ForkJoinPool fjPool) {
		this.info = info;
		this.censusRect = info.censusRect;
		this.x = info.x;
		this.y = info.y;
		lo = low;
		hi = high;
		this.fjPool = fjPool;
	}

	@Override
	protected int[][] compute() {
		if (hi - lo <= SEQUENTIAL_CUTOFF) {		
			// each parallel subproblem needs to return a grid
			// build grid
			CensusGroup[] blocks = info.input; 
			float censusX = censusRect.right - censusRect.left;
			float censusY = censusRect.top - censusRect.bottom;
			int[][] grid = new int[x][y];
			int gridX, gridY;
			//int totalPop = 0;
			for(int j = lo; j < hi; j++) {
				gridX = (int) ((blocks[j].longitude - censusRect.left) * x / censusX);
				gridY = (int) ((blocks[j].latitude - censusRect.bottom) * y / censusY);
				if(gridX == x) 
					gridX--;
	
				if(gridY == y) 
					gridY--;

				grid[gridX][gridY] += blocks[j].population;
			}
			return grid;
			
		} else {
			BuildGrid left = new BuildGrid(info, lo, (lo + hi) / 2, fjPool);
			BuildGrid right = new BuildGrid(info, (lo + hi) /2, hi, fjPool);
			
			left.fork();
			int[][] rightGrid = right.compute();
			int[][] leftGrid = left.join();		
			// add these two grid together
			fjPool.invoke(new AddGrid(0, x, 0, y, leftGrid, rightGrid));
			return rightGrid;
		}

	}
	
	public class AddGrid extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		int xhi, xlo, ylo, yhi;
		int[][] leftGrid, rightGrid;
		
		public AddGrid(int xlo, int xhi, int ylo, int yhi, int[][] leftGrid, int[][] rightGrid) {
			this.xlo = xlo;
			this.xhi = xhi;
			this.ylo = ylo;
			this.yhi = yhi;
			
			this.leftGrid = leftGrid;
			this.rightGrid = rightGrid;
		}
		@Override
		protected void compute() {
			if ((xhi - xlo) * (yhi - ylo) <= SEQUENTIAL_CUTOFF) {
				 for (int i = xlo; i < xhi; i++) {
					 for (int j = ylo; j < yhi; j++) {
						 rightGrid[i][j] += leftGrid[i][j];
					 }
				 }
			} else {
				AddGrid thread1, thread2;
				if((xhi - xlo) >= (yhi - ylo)) {
					thread1 = new AddGrid(xlo, (xhi + xlo) / 2, ylo, yhi, leftGrid, rightGrid);
					thread2 = new AddGrid((xhi + xlo) / 2, xhi, ylo, yhi, leftGrid, rightGrid);
				} else {
					thread1 = new AddGrid(xlo, xhi, ylo, (yhi + ylo) / 2, leftGrid, rightGrid);
					thread2 = new AddGrid(xlo, xhi, (yhi + ylo) / 2, yhi, leftGrid, rightGrid);
				}
				
				thread1.fork();
				thread2.compute();
				thread1.join();
			}
		}
	}
}
