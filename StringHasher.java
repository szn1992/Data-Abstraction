package phaseB;
import providedCode.Hasher;


public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int hashVal = 0;
		for(int i = 0; i < s.length(); i++)
			hashVal = 37 * hashVal + s.charAt(i);
		
		return hashVal;
	}
}
