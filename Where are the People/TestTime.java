
public class TestTime {
	public static final int NUM_TEST = 200;
	public static final int NUM_WARMUP = 80;
	
	public static void main(String[] args) {
		
		double runTime = 0.0;
		int changeCutOff = 0;
		String[] args1 = {"CenPop2010.txt", "20", "25", "-v1"};
		String[] args2 = {"CenPop2010.txt", "20", "25", "-v2"};
		String[] args3 = {"CenPop2010.txt", "20", "25", "-v3"};
		String[] args4 = {"CenPop2010.txt", "20", "25", "-v4"};
		String[] args5 = {"CenPop2010.txt", "20", "25", "-v5"};

		/*
			// test time for version 1 and version 2
			// filename, x, y, version	
			// calculate running time for finding initial corners in version 1
			// TODO: changeCutOff values
			changeCutOff = Integer.parseInt(args1[1]) * Integer.parseInt(args1[2]);
			runTime = getAverageRuntimeForFindingInitalCorner(args1, changeCutOff);
			System.out.println("find corners sequentially: " + runTime);
			
			// calculate running time for finding initial corners in version 2	
			// CUTOFF is 100 at this time;
			runTime = getAverageRuntimeForFindingInitalCorner(args2, changeCutOff);
			System.out.println("find corners in parallel: " + runTime);
			
		
			// test grid building time for v3 and v4
			runTime = getAverageRuntimeGridBuilding(args3, changeCutOff);
			System.out.println("build grid sequentially: " + runTime);
			
			// CUTOFF is 80 both for summing of census data and in combining of grids
			runTime = getAverageRuntimeGridBuilding(args4, changeCutOff);
			System.out.println("build grid in parallel: " + runTime);
			*/
			
			/*
				// for problem7		
				// TODO: change the grid size manually later
				runTime = getAverageRuntimeForVersionFourAndFive(args4);
				System.out.println("v4 performance: " + runTime);
				
				runTime = getAverageRuntimeForVersionFourAndFive(args5);
				System.out.println("v5 performance: " + runTime);
			*/
			
				// for problem8
		
				runTime = problem8Part1(args1);
				System.out.println("performance of v1 " + runTime);
				runTime = problem8Part1(args3);
				System.out.println("performance of v3 " + runTime);
				
				runTime = problem8Part2(args2);
				System.out.println("performance of v2 " + runTime);
				runTime = problem8Part2(args4);
				System.out.println("performance of v4 " + runTime);
		
	}
	
	
	// for question6
	// for version 1 and version 2 finding initial corners, vary the cutoff for V2
	public static double getAverageRuntimeForFindingInitalCorner(String[] args, int changeCutOff) {
		// change cutoff values
		CensusData result = PopulationQuery.parse(args[0]);
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	

	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	    	// change cut off
	    	double startTime = System.currentTimeMillis();
		    // calculate the time finding initial corners
		    FindPopulation.getCensusRect(info, args[3]);
		    double endTime = System.currentTimeMillis();
		    if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
		    	totalTime += (endTime - startTime);
		    }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
	// for question6
	// grid-building step for version 3 and version 4
	public static double getAverageRuntimeGridBuilding(String[] args, int changeCutOff) {
		// change cutoff values
		
		CensusData result = PopulationQuery.parse(args[0]);
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	
		// needs to find initial corners first
		// update info information
	    FindPopulation.getCensusRect(info, args[3]);
	    int[][] grid = new int[21][26];
	    // timing grid building 
	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	    	// TODO: change cut off
	    	double startTime = System.currentTimeMillis();	    	
	    	FindPopulation.getGridAndTotalPopulation(grid, info, args[3]);    	
		    double endTime = System.currentTimeMillis();
		    if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
		    	totalTime += (endTime - startTime);
		    }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
	
		
	// for problem7: version 4 and 5
	public static double getAverageRuntimeForVersionFourAndFive(String[] args) {
		// for small grids		
		CensusData result = PopulationQuery.parse(args[0]);
		// example: execute one query
		Rectangle rect = new Rectangle(1, 5, 4, 1);
		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	    
	    int[][] grid = new int[21][26];
	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	    	double startTime = System.currentTimeMillis();
		    FindPopulation.getCensusRect(info, args[3]);
	    	FindPopulation.getGridAndTotalPopulation(grid, info, args[3]);  	
		    double endTime = System.currentTimeMillis();
		    if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
		    	totalTime += (endTime - startTime);
		    }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
	
	// for problem8: compare version1 to version3
	public static double problem8Part1(String[] args) {
		CensusData result = PopulationQuery.parse(args[0]);
	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	    	double startTime = System.currentTimeMillis();    
	    	// answering a certain number of queries
			    if (args[3] == "-v1") {
			    	Rectangle rect = new Rectangle(1, 5, 4, 1);
		    		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	
		    		FindPopulation.getCensusRect(info, args[3]);
			    	for (int j = 0; j < 2; j++) {		
			    		FindPopulation.simpleAndSequential(info, rect);
			    	}
			    } else {
			    	
			    	int[][] grid = new int[21][26];
					Rectangle rect = null;
					UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	
					FindPopulation.getCensusRect(info, args[3]);	
			    	grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v3");
			    	for (int j = 0; j < 2; j++) {
			    		rect = new Rectangle(1, 5, 4, 1);
			    		FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
			    	}
			    }	    
		    double endTime = System.currentTimeMillis();
		    if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
		    	totalTime += (endTime - startTime);
		    }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
	// for problem8: compare version2 to version4
	public static double problem8Part2(String[] args) {
		CensusData result = PopulationQuery.parse(args[0]);

	    double totalTime = 0;
	    for(int i=0; i<NUM_TEST; i++) {
	    	double startTime = System.currentTimeMillis();  
			    if (args[3] == "-v2") {
			    		Rectangle rect = new Rectangle(1, 5, 4, 1);
			    		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	
			    		FindPopulation.getCensusRect(info, args[3]);
			    		for (int j = 0; j < 5; j++) {
			    			FindPopulation.simpleAndParallel(info, rect);
			    		}
			    	
			    } else {
					Rectangle rect = null;
		    		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, rect, 20, 25, 0);	
		    		FindPopulation.getCensusRect(info, args[3]);
			    	int[][] grid = new int[21][26];
			    	grid = FindPopulation.getGridAndTotalPopulation(grid, info, "-v4");
			    	for (int j = 0; j < 5; j++) {
			    		rect = new Rectangle(1, 5, 4, 1);
			    		FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
			    	}
			    }
		   
		    double endTime = System.currentTimeMillis();
		    if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
		    	totalTime += (endTime - startTime);
		    }
	    }
	    return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
	}
	
}
