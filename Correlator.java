package phaseB;

import java.io.IOException;

import phaseA.AVLTree;
import phaseA.MoveToFrontList;
import phaseA.StringComparator;
import providedCode.BinarySearchTree;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.FileWordReader;
import providedCode.SimpleIterator;


public class Correlator {
	//private static DataCounter<String> counter1;
	//private static DataCounter<String> counter2;
    public static void main(String[] args) {
    	
    	if(args.length == 3) {
    		DataCounter<String> counter1 = helper(args[0]);
    		DataCounter<String> counter2 = helper(args[0]);
    		int size1 = countWords(args[1], counter1, 0);
    		int size2 = countWords(args[2], counter2, 0);
    		
	    	System.out.println(getVariance(size1, size2, counter1, counter2));  // IMPORTANT: Do not change printing format. Just print the double.
    	} else {
            System.err.println("Usage: filename of document to analyze");
            System.exit(1);
    	}
    }
    
    private static double getVariance(int size1, int size2, DataCounter<String> counter1,
    									DataCounter<String> counter2) {
    	SimpleIterator<DataCount<String>> its = counter1.getIterator();
    	DataCount<String> dc = null;
    	double frequency1, frequency2, runningSum = 0.0;
    	System.out.println(size1);
    	System.out.println(size2);
    	

    	while(its.hasNext()) {
    		dc = its.next();
    		frequency1 = (double) dc.count / size1;
    		frequency2 = (double) counter2.getCount(dc.data) / size2;

    		if(frequency1 <= 0.01 && frequency1 >= 0.0001) {
    			if(frequency2 <= 0.01 && frequency2 >= 0.0001) {
    				runningSum += Math.pow(frequency1 - frequency2, 2);

    			}
    		}
    	}

    	return runningSum;
    }
    
    private static DataCounter<String> helper(String first) {
    	DataCounter<String> counter = null;
		if (first.equals("-b")) {	// binary search tree
			counter = new BinarySearchTree<String>(new StringComparator());
		} else if (first.equals("-a")) { // AVL tree
			counter = new AVLTree<String>(new StringComparator());
		} else if (first.equals("-m")) { // move to front list
			counter = new MoveToFrontList<String>(new StringComparator());
		} else if (first.equals("-h")) { // hash table
			counter = new HashTable<String>(new StringComparator(), new StringHasher());
		} else {
            System.err.println("Incorrect argument");
            System.exit(1);
		}
		return counter;
    }
    
    private static int countWords(String file, DataCounter<String> counter, int size) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
            	size++;          	
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
		return size;
    }

}
