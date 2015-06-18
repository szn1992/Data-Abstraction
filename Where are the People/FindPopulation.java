import java.util.concurrent.ForkJoinPool;


public class FindPopulation {
	
	public static final ForkJoinPool fjPool = new ForkJoinPool();
	// version 1: simple and sequential
	// rect is the user queried rectangle
	// result is american census data
	// x and y is the width and height of the whole rectangle
	public static Population simpleAndSequential(UnchangedInfo info, Rectangle rect) {
		Rectangle censusRect = info.censusRect;
		CensusGroup[] blocks = info.input;
		int size = info.size;
		int x = info.x;
		int y = info.y;
		
		Population population = new Population(0, 0);
		float censusX = censusRect.right - censusRect.left;
		float censusY = censusRect.top - censusRect.bottom;
	
		// find the grid position for each census-block-group
		float censusLeft = ((rect.left - 1) * censusX / x) + censusRect.left;
		float censusRight = ((rect.right) * censusX / x) + censusRect.left;
		float censusTop = ((rect.top) * censusY / y) + censusRect.bottom;
		float censusBottom = ((rect.bottom - 1) * censusY / y) + censusRect.bottom;
		
		// find the population inside the queried rectangle
		for(int j = 0; j < size; j++) {
			population.totalPop += blocks[j].population;
			if(blocks[j].latitude >= censusBottom && blocks[j].latitude <= censusTop
				&& blocks[j].longitude >= censusLeft && blocks[j].longitude <= censusRight) {
				
				population.requiredPop += blocks[j].population;
			}
		}
		return population;
	}
	
	// version2: simple and parallel
	// rect is the user queried rectangle
	// result is american census data
	// x and y is the width and height of the whole rectangle
	public static Population simpleAndParallel(UnchangedInfo info, Rectangle rect) {
		int size = info.size;
		Population population = fjPool.invoke(new GetPopulation(info, rect, 0, size));
		return population;
	}
	
	// v3, v4, v5
	public static Population returnGridPopulation(int[][] grid, Rectangle rect, int totalPop) {	
		if(rect.top == grid[0].length && rect.left == 1) {
			return new Population(grid[(int)rect.right - 1][(int)rect.bottom - 1], totalPop);
		
		} else if(rect.top != grid[0].length && rect.left == 1) {
			return new Population(grid[(int)rect.right - 1][(int)rect.bottom - 1] - 
									grid[(int)rect.right - 1][(int)rect.top], totalPop);
			
		} else if(rect.top == grid[0].length && rect.left != 1) {
			return new Population(grid[(int)rect.right - 1][(int)rect.bottom - 1] - 
									grid[(int)rect.left - 2][(int)rect.bottom - 1], totalPop);
		
		} else {
			return new Population(grid[(int)rect.right - 1][(int)rect.bottom - 1] - 
					grid[(int)rect.left - 2][(int)rect.bottom - 1] - 
					grid[(int)rect.right - 1][(int)rect.top] + 
					grid[(int)rect.left - 2][(int)rect.top], totalPop);
		}
	}
	
	// preprocessor v3, v4, v5 step 1 and 2
	public static int[][] getGridAndTotalPopulation(int[][] grid, UnchangedInfo info, String version) {
		// step 1
		Rectangle censusRect = info.censusRect;
		CensusGroup[] blocks = info.input;
		int size = info.size;
		int x = info.x;
		int y = info.y;
		
		if(version.equals("-v3")) {
			float censusX = censusRect.right - censusRect.left;
			float censusY = censusRect.top - censusRect.bottom;
			int gridX, gridY;
			for(int j = 0; j < size; j++) {
				gridX = (int) ((blocks[j].longitude - censusRect.left) * x / censusX);
				gridY = (int) ((blocks[j].latitude - censusRect.bottom) * y / censusY);
				if(gridX == x)
					gridX--;
				if(gridY == y)
					gridY--;
				grid[gridX][gridY] += blocks[j].population;
			}
		} else if (version.equals("-v4")){ // version equals v4
			grid = fjPool.invoke(new BuildGrid(info, 0, size, fjPool));
		} else { // version 5
			BuildGridLocked lockedGrid = new BuildGridLocked(info, 0, size, fjPool);
			grid = lockedGrid.grid;
			
		}
		// step 2
		for(int a = 0; a < grid.length; a++) {
			for(int b = grid[a].length - 1; b >= 0; b--) {
				if(a == 0 && b != grid[a].length - 1) 
					grid[a][b] += grid[a][b + 1];
				else if(a != 0 && b == grid[a].length - 1) 
					grid[a][b] += grid[a - 1][b];
				else if(a != 0 && b != grid[a].length - 1)
					grid[a][b] += grid[a - 1][b] + grid[a][b + 1] - grid[a - 1][b + 1];
			}	
		}
		info.totalPop = grid[info.x - 1][0];
		return grid;
	}
	
	// preprocessor
	// find US rectangle initial corners
	public static void getCensusRect(UnchangedInfo info, String version) {
		// get censusRect
		Rectangle censusRect = info.censusRect;
		CensusGroup[] blocks = info.input;
		int size = info.size;
		if(version.equals("-v1") || version.equals("-v3")) {
			censusRect = new Rectangle(blocks[0].longitude, blocks[0].longitude, 
										blocks[0].latitude, blocks[0].latitude);

			for(int i = 1; i < size; i++) {
				censusRect = censusRect.encompass(new Rectangle(blocks[i].longitude, 
												blocks[i].longitude, blocks[i].latitude, 
												blocks[i].latitude));
			}
		} else {
			censusRect = fjPool.invoke(new GetCensusRect(blocks, 0, size));	
		}
		// update censusRect information in the info object
		info.censusRect = censusRect;
	}
	
}	
