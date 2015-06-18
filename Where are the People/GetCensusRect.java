//Zhuonan Sun && Jiahui Xu
//02/26/2014
//CSE332 Project3 PhaseA

//For version 2: simple and parallel
//This class uses fork-join parallelism to compute four corners of the U.S. rectangle
import java.util.concurrent.RecursiveTask;
public class GetCensusRect extends RecursiveTask<Rectangle>{

	private static final long serialVersionUID = 1L;
	static final int SEQUENTIAL_CUTOFF = 100;
	CensusGroup[] blocks;
	
	int lo;
	int hi;
	// result is the american census data 
	// rect is the user queried rectangle
	GetCensusRect(CensusGroup[] blocks, int low, int high) {
		this.blocks = blocks;
		lo = low;
		hi = high;
	}
	
	@Override
	protected Rectangle compute() {
		if(hi - lo < SEQUENTIAL_CUTOFF) {
			CensusGroup group = blocks[lo];
			Rectangle censusRect = new Rectangle(group.longitude, group.longitude,
					group.latitude, group.latitude);
			for(int i = lo + 1; i < hi; i++) {
				group = blocks[i];
				censusRect = censusRect.encompass(new Rectangle(group.longitude, 
						group.longitude, group.latitude, 
						group.latitude));
			}
			
			return censusRect;
		} else {
			GetCensusRect left = new GetCensusRect(blocks, lo, (lo + hi) / 2);
			GetCensusRect right = new GetCensusRect(blocks, (lo + hi) / 2, hi);
			left.fork();
			Rectangle rightAns = right.compute();
			Rectangle leftAns = left.join();

			return leftAns.encompass(rightAns);
		
		}
	}
}
