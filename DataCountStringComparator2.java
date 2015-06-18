
// compare the frequency of two elements

package providedCode;
import phaseA.*;

// if the frequency of the first string is less than the frequency of the second string
// return negative value
// otherwise, return non-negative value
public class DataCountStringComparator2 implements Comparator<DataCount<String>> {
	StringComparator alphabetical = new StringComparator();

	public int compare(DataCount<String> c1, DataCount<String> c2) {
		if (c1.count != c2.count)
			return c1.count - c2.count;
		return alphabetical.compare(c2.data, c1.data);
	}
}