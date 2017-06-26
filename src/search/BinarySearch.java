package search;

import java.util.List;

/**
 * See BinarySearch.java by Robert Sedgewick and Kevin Wayne
 * for a detailed explanation
 * 
 * http://algs4.cs.princeton.edu/11model/BinarySearch.java.html
 *
 */

public class BinarySearch {
	public BinarySearch(){
		
	}
	
	public int binarySearch(int[] array, int key) {
	    int low = 0;
	    int hi = array.length - 1;
	    while (low <= hi) {
	        int mid = low + ((hi - low) / 2);
	        if(key < array[mid]){ 
	        	hi = mid - 1; 
	        } else if (key > array[mid]){
	        	low = mid + 1;
	        } else return mid;
	    }
	    return -1;
	}
	
	public int binarySearch(List<Integer> list, int key) {
	    int low = 0;
	    int hi = list.size() - 1;
	    while (low <= hi) {
	        int mid = low + ((hi - low) / 2);
	        if(key < list.get(mid)){ 
	        	hi = mid - 1; 
	        } else if (key > list.get(mid)){
	        	low = mid + 1;
	        } else return mid;
	    }
	    return -1;
	}
}
