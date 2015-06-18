
public class UnchangedInfo {
	public CensusGroup[] input;
	public int size, x, y;
	public Rectangle censusRect;
	
	// input is blocks
	public UnchangedInfo(CensusGroup[] input, int size, Rectangle censusRect, int x, int y) {
		this.input = input; // censusData.data
		this.size = size; // censusData.data_size
		this.censusRect = censusRect;
		this.x = x;
		this.y = y;
	}
}
