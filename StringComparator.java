package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * This comparator is used by the provided code for both data-counters and 
 * sorting. Because of how the output must be sorted in the case of ties, 
 * your implementation should return a negative number when the first argument
 * to compare comes first alphabetically. Do not use any String comparison 
 * provided in Java's standard library; the only String methods you should 
 * use are length and charAt.
 */
public class StringComparator implements Comparator<String>{

	@Override
	public int compare(String s1, String s2) {
		// TODO: To-be implemented
      int s2_length = s2.length();
		for(int i = 0; i < s1.length(); i++) {
         if(i == s2_length)
            return 0;
			if(s1.charAt(i) < s2.charAt(i))
				return -1;
			if(s1.charAt(i) > s2.charAt(i))
				return 0;
		}
		return -1;
	}
}
