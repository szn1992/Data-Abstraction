// Jiahui Xu				
// Zhuonan Sun						
// 1/30/2014					
// CSE 332 A					
// Project 2 Phase A 
package main;
import java.io.IOException;

import phaseA.*;
import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {
	
	// return DataCounter after reading the article and counting words
    private static DataCounter<String> countWords(String file, DataCounter<String> counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
		return counter;
    }
    
    
    // Returns an array of DataCount objects containing each unique word
    private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		SimpleIterator<DataCount<E>> itr = counter.getIterator();
 		DataCount<E>[] result = new DataCount[counter.getSize()];
 		for (int i = 0; i < result.length; i++) {
 			// use arguments iterator to retrieve all elements
 			result[i] = itr.next();
 		}
 		return result;
 	}
    
 	
    // IMPORTANT: Used for grading. Do not modify the printing format!
 	// You may modify this method if you want.
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    
    // runs the chosen type DataCounter and uses the chosen sorting algorithm
    // prints out the results of word frequency analysis
    public static void main(String[] args) {

    	DataCounter<String> counter = null;
    	if (args.length == 3 || args.length == 4) {
    		counter = helper(args[0]);
    		if (args.length == 3) {
    			counter = countWords(args[2], counter);
    		} else {
    			counter = countWords(args[3], counter);
    		}
	        DataCount<String>[] counts = getCountsArray(counter);
	       
    		if (args[1].equals("-is")) {	//insertion sort
    	        Sorter.insertionSort(counts, new DataCountStringComparator());
    		} else if (args[1].equals("-hs")) {		//heap sort
    	        Sorter.heapSort(counts, new DataCountStringComparator());
    		} else if (args[1].equals("-os")) { 	// other sort	
    			Sorter.otherSort(counts, new DataCountStringComparator());
    		} else if (args[1].equals("-k") && (args.length == 4)) {// top k sort
    			printTopKElement(counts, new DataCountStringComparator2(), Integer.parseInt(args[2]));
    			return;
    		} else {
                System.err.println("Wrong input");
                System.exit(1);
    		}
    		
    		printDataCount(counts);
    	} else {
            System.err.println("Usage: filename of document to analyze");
            System.exit(1);
    	}
    	
    }
    
    // helper method to construct DataCounter
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
    
    // print out top k elements in decending order
    private static <E> void printTopKElement(DataCount<String>[] counts, Comparator<DataCount<String>> comparator2, int k) {
    	
		Sorter.topKSort(counts, comparator2, k);
		for (int i = 0; i < k; i++) {
			System.out.println(counts[i].count + "\t" + counts[i].data);
		}
    }

}
