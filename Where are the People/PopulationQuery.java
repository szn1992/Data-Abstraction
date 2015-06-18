// Zhuonan Sun && Jiahui Xu
// 02/26/2014
// CSE332 Project3 PhaseB

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
		
	// parse the input file into a large array held in a CensusData object
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            @SuppressWarnings("resource")
			BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}

	// this is only used in testing
	public static boolean checkCommand(String[] args) {
		if(args.length != 4) 
			return false;
			
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		String version = args[3];
		
		if(x <= 0 || y <= 0 || !(version.equals("-v1") || version.equals("-v2") ||
			version.equals("-v3") || version.equals("-v4") || version.equals("-v5"))) {	
			return false;
		}
		return true;
	}
	
	// only used in testing
	public static boolean checkUserInput(String input, int x, int y) {
		input = input.trim();			
		String[] userInput = input.split("\\s{1,}");
		
		if (userInput.length != 4) 
			return false;
		
		int west = Integer.parseInt(userInput[0]);
		int south = Integer.parseInt(userInput[1]);
		int east = Integer.parseInt(userInput[2]);
		int north = Integer.parseInt(userInput[3]);		
			
		if (!((west >= 1 && west <= x) && (south >= 1 && south <= y) &&
				(east >= west && east <= x) && (north >= south && north <= y))) {
			return false;
		}
		return true;
	}
	
	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2 (PHASE A)
	public static void main(String[] args) {
		if(args.length != 4) {
			System.err.println("Error in argument");
			System.exit(1);
		}
		
		String filename = args[0];
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		String version = args[3];
		
		if(x <= 0 || y <= 0 || !(version.equals("-v1") || version.equals("-v2") ||
			version.equals("-v3") || version.equals("-v4") || version.equals("-v5"))) {
			
			System.err.println("Error in argument");
			System.exit(1);
		}
		
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		CensusData result = parse(filename);
		
		boolean arguments = true;
		boolean censusRectAquired = false;
		boolean gridAquired = false;
		
		CensusGroup[] blocks = result.data;
		Rectangle censusRect = new Rectangle(blocks[0].longitude, blocks[0].longitude, 
									blocks[0].latitude, blocks[0].latitude);
		
		UnchangedInfo info = new UnchangedInfo(result.data, result.data_size, censusRect, x, y, 0);
		
		int[][] grid = null;
		while(arguments) {
			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			String coordinates = console.nextLine().trim();			
			String[] userInput = coordinates.split("\\s{1,}");
			
			if (userInput.length != 4) {
				arguments = false;
			} else {
				int west = Integer.parseInt(userInput[0]);
				int south = Integer.parseInt(userInput[1]);
				int east = Integer.parseInt(userInput[2]);
				int north = Integer.parseInt(userInput[3]);		
				
				if (!((west >= 1 && west <= x) && (south >= 1 && south <= y) &&
						(east >= west && east <= x) && (north >= south && north <= y))) {
					// print an error message 
					// reprompt
					System.out.println("Input is outside the bounds of the grid");
				} else {
					Rectangle rect = new Rectangle(west, east, north, south);				
					if(!censusRectAquired) {
						FindPopulation.getCensusRect(info, version);
						System.out.println(info.censusRect.left + ",  " + info.censusRect.right + ",  " + info.censusRect.top + ",  " + info.censusRect.bottom);
						censusRectAquired = true;
					}		
					Population population = null;
					if(version.equals("-v1")) {
						population = FindPopulation.simpleAndSequential(info, rect);
					} else if(version.equals("-v2")) {
						population = FindPopulation.simpleAndParallel(info, rect);
					} else {  // for v3, v4, v5
						if(!gridAquired) {
							
							grid = new int[x][y];
							grid = FindPopulation.getGridAndTotalPopulation(grid, info, version);
							gridAquired = true;
						}				
						population = FindPopulation.returnGridPopulation(grid, rect, info.totalPop);
					}
					System.out.println(population.totalPop);
					printStats(population.requiredPop, population.totalPop);
				}
			}
		}
	}

	// output the population the queried rectangle
	// output the percentage of the queried population with total population
	public static void printStats(int requiredPop, int totalPop) {
		System.out.println("population of rectangle: " + requiredPop);
		System.out.println("percent of total population: " + String.format("%.2f", (requiredPop * 100.0 / totalPop)));
	}
}