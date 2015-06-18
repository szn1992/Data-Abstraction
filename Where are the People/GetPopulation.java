// Zhuonan Sun && Jiahui Xu
// 02/26/2014
// CSE332 Project3 PhaseA

// For version 2: simple and parallel
// This class uses fork-join parallelism to compute the total population and
// the queried population in the grid
import java.util.concurrent.RecursiveTask;

public class GetPopulation extends RecursiveTask<Population> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int SEQUENTIAL_CUTOFF = 100;
	UnchangedInfo info;
	CensusGroup[] blocks;
	Rectangle rect;
	Rectangle censusRect;
	
	int x, y, lo, hi;
	// rect is the user queried rectangle
	// result is american census data
	// x and y is the width and height of the whole rectangle
	public GetPopulation(UnchangedInfo info, Rectangle rect, int low, int high) {
		this.info = info;
		this.blocks = info.input;
		this.rect = rect;
		this.censusRect = info.censusRect;
		this.x = info.x;
		this.y = info.y;
		lo = low;
		hi = high;
	}
	
	@Override
	protected Population compute() {
		if(hi - lo < SEQUENTIAL_CUTOFF) {
			Population pop = new Population(0, 0);
			
			float censusX = censusRect.right - censusRect.left;
			float censusY = censusRect.top - censusRect.bottom;
			
			// find the grid position for each census-block-group
			float censusLeft = ((rect.left - 1) * censusX / x) + censusRect.left;
			float censusRight = ((rect.right) * censusX / x) + censusRect.left;
			float censusTop = ((rect.top) * censusY / y) + censusRect.bottom;
			float censusBottom = ((rect.bottom - 1) * censusY / y) + censusRect.bottom;	
			
			for(int j = lo; j < hi; j++) {
				pop.totalPop += blocks[j].population;
				if(blocks[j].latitude >= censusBottom && blocks[j].latitude <= censusTop
						&& blocks[j].longitude >= censusLeft && blocks[j].longitude <= censusRight) {
					pop.requiredPop += blocks[j].population;
				}	
			}
			
			return pop;
		} else {
			GetPopulation left = new GetPopulation(info, rect, lo, (lo + hi) / 2);
			GetPopulation right = new GetPopulation(info, rect, (lo + hi) / 2, hi);
			left.fork();
			Population rightAns = right.compute();
			Population leftAns = left.join();
			return new Population(leftAns.requiredPop + rightAns.requiredPop, 
									leftAns.totalPop + rightAns.totalPop);
		}
	}
}