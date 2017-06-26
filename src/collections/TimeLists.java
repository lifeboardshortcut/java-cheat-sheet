package collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import utils.print.PrintUtils;
import utils.timing.DataStructureTimingResult;

/*
 * This class times access, insert, search, and delete
 * operations on java lists. Tests are performed on a variety
 * of cases and on a definable number of arrays of different
 * size in order to see the effect of size on performance
 * 
 * TimingResult class is used for the recording timing of 
 * tests and then printing everything in the desired order
 * afterwards
 */
public class TimeLists {
	private static Integer[] arrayListSizes = new Integer[]{10000, 20000, 30000};
	private static Integer[] linkedListSizes = new Integer[]{10000, 20000, 30000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	private final static int rangeLow = 0;
	private final static int rangeHigh = 999;
	
	public static void main(String[] args){
		// Array Lists
		List<List<Integer>> arrayLists = new ArrayList<List<Integer>>();
		for(int i = 0; i < arrayListSizes.length; i++){
			arrayLists.add(new ArrayList<Integer>());
		}
		timingResults.addAll(timeLists(arrayLists, arrayListSizes));
		
		// Linked Lists
		List<List<Integer>> linkedLists = new ArrayList<List<Integer>>();
		for(int i = 0; i < linkedListSizes.length; i++){
			linkedLists.add(new LinkedList<Integer>());
		}
		timingResults.addAll(timeLists(linkedLists, linkedListSizes));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	private static List<DataStructureTimingResult> timeLists(List<List<Integer>> lists, Integer[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		
		// Insertion
		results.addAll(timeListAdd(lists, sizes));
		results.addAll(timeListInsert(lists));

		// Access
		results.addAll(timeListAccess(lists));

		// Search
		results.addAll(timeListIndexOf(lists));
		results.addAll(timeListContains(lists));

		// Update
		results.addAll(timeListSet(lists));

		// Deletion
		results.addAll(timeListDeletion(lists));
		
		return results;
	}
	
	/*
	 * ********************
	 *     list.add()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeListAdd(List<List<Integer>> lists, Integer[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".add()";
		for(int i = 0; i < lists.size(); i++){
			List<Integer> list = lists.get(i);
			results.add(timeAddToList(list, method, 1, "Add to Empty List"));
			results.addAll(timeAddToListInLoop(list, method, 2, sizes[i]));
			results.add(timeAddToList(list, method, 5, "Add to Full List"));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeAddToList(List<Integer> list, String method, int order, String details){
		int size_before = list.size();
		final long add_0 = System.nanoTime();
		list.add(0);
		final long add_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, 
				size_before, size_after, add_1 - add_0);
	}
	
	private static List<DataStructureTimingResult> timeAddToListInLoop(List<Integer> list, String method, int order, int size){
		int value;
		int size_before = list.size();
		long addLoop_0, addLoop_1, addLoop_time, addLoop_total = 0, 
				addLoop_max = Long.MIN_VALUE, addLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			value = ThreadLocalRandom.current().nextInt(rangeLow, rangeHigh);
			addLoop_0 = System.nanoTime();
			list.add(value);
			addLoop_1 = System.nanoTime();
			
			addLoop_time = addLoop_1 - addLoop_0;
			addLoop_total += addLoop_time;
			if(addLoop_time > addLoop_max) { addLoop_max = addLoop_time; }
			if(addLoop_time < addLoop_min) { addLoop_min = addLoop_time; }
		}
		int size_after = list.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(list, method, order, "Add Loop: Min Time", size_before, size_after, addLoop_min));
		results.add(new DataStructureTimingResult(list, method, order + 1, "Add Loop: Max Time", size_before, size_after, addLoop_max));
		results.add(new DataStructureTimingResult(list, method, order + 2, "Add Loop: Avg Time", size_before, size_after, addLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *   list.add(i, val)
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeListInsert(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".add(i, val)";
		for(List<Integer> list : lists){
			results.add(timeListInsertAtIndex(list, method, 1, 0, "Insert at Front"));
			results.add(timeListInsertAtIndex(list, method, 2, list.size() / 2, "Insert in Middle"));
			results.add(timeListInsertAtIndex(list, method, 3, list.size() - 1, "Insert Near End"));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeListInsertAtIndex(List<Integer> list, String method, int order, int index, String details){
		int size_before = list.size();
		final long insert_0 = System.nanoTime();
		list.add(index, 0);
		final long insert_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, insert_1 - insert_0);

	}
		

	
	/*
	 * ********************
	 *     list.get()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeListAccess(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".get()";
		for(List<Integer> list : lists){
			results.add(testListAccessAtIndex(list, method, 1, 0, "Access First Element"));
			results.add(testListAccessAtIndex(list, method, 2, list.size() / 2, "Access Middle Element"));
			results.add(testListAccessAtIndex(list, method, 3, list.size() - 1, "Access Last Element"));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult testListAccessAtIndex(List<Integer> list, String method, int order, int index, String details){
		int size_before = list.size();
		final long get_0 = System.nanoTime();
		list.get(index);
		final long get_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, get_1 - get_0);
	}
	
	/*
	 * ********************
	 *    list.indexOf()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeListIndexOf(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".indexOf()";
		for(List<Integer> list : lists){
			results.add(timeListIndexOfAtIndex(list, method, 1, 0, "Index of First Element"));
			results.add(timeListIndexOfAtIndex(list, method, 2, list.size() / 2, "Index of Middle Element"));
			results.add(timeListIndexOfAtIndex(list, method, 3, list.size() - 1, "Index of Last Element"));
		}
		
		return results;
	}
	
	
	private static DataStructureTimingResult timeListIndexOfAtIndex(List<Integer> list, String method, int order, int index, String details){
		int originalValue = list.get(index);
		int value = rangeHigh + 1; // Unique
		list.set(index, value); 
		int size_before = list.size();
		final long indexOf_0 = System.nanoTime();
		list.indexOf(value);
		final long indexOf_1 = System.nanoTime();
		int size_after = list.size();
		
		list.set(index, originalValue); // Reset
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, indexOf_1 - indexOf_0);
	}

	/*
	 * ********************
	 *   list.contains()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeListContains(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".contains()";
		for(List<Integer> list : lists){
			results.add(timeListContainsAtIndex(list, method, 1, 0, "Contains First Element"));
			results.add(timeListContainsAtIndex(list, method, 2, list.size() / 2, "Contains Middle Element"));
			results.add(timeListContainsAtIndex(list, method, 3, list.size() - 1, "Contains Last Element"));
		}
		
		return results;
	}
	

	private static DataStructureTimingResult timeListContainsAtIndex(List<Integer> list, String method, int order, int index, String details){
		int originalValue = list.get(index);
		int value = rangeHigh + 1; // Unique
		list.set(index, value); 
		int size_before = list.size();
		final long contains_0 = System.nanoTime();
		list.contains(value);
		final long contains_1 = System.nanoTime();
		int size_after = list.size();
		
		list.set(index, originalValue); // Reset
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, contains_1 - contains_0);

	}

	
	/*
	 * ********************
	 *     list.set()
	 * ********************
	 */	
	private static List<DataStructureTimingResult> timeListSet(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".set()";
		for(List<Integer> list : lists){
			results.add(timeListSetAtIndex(list, method, 1, 0, "Update First Element"));
			results.add(timeListSetAtIndex(list, method, 2, list.size() / 2, "Update Middle Element"));
			results.add(timeListSetAtIndex(list, method, 3, list.size() - 1, "Update Last Element"));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeListSetAtIndex(List<Integer> list, String method, int order, int index, String details){
		int value = list.get(index) == 0 ? 1 : 0;
		int size_before = list.size();
		final long set_0 = System.nanoTime();
		list.set(0, value);
		final long set_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, set_1 - set_0);

	}
		
	/*
	 * ********************
	 *   list.remove()
	 * ********************
	 */	
	private static List<DataStructureTimingResult> timeListDeletion(List<List<Integer>> lists){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".remove()";
		for(List<Integer> list : lists){
			results.add(timeListRemoveAtIndex(list, method, 1, 0, "Remove Front"));
			results.add(timeListRemoveAtIndex(list, method, 2, list.size() / 2, "Remove Middle"));
			results.add(timeListRemoveAtIndex(list, method, 3, list.size() - 1, "Remove End"));
			results.add(timeListRemoveElement(list, method, 4, 0, "Remove Element @ Front"));
			results.add(timeListRemoveElement(list, method, 5, list.size() / 2, "Remove Element @ Middle"));
			results.add(timeListRemoveElement(list, method, 6, list.size() - 1, "Remove Element @ End"));
			results.add(timeListRemoveWithIterator(list, method, 7, 0, "Iterator Remove @ Front"));
			results.add(timeListRemoveWithIterator(list, method, 8, list.size() / 2, "Iterator Remove @ Middle"));
			results.add(timeListRemoveWithIterator(list, method, 9, list.size() - 1, "Iterator Remove @ End"));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeListRemoveAtIndex(List<Integer> list, String method, int order, int index, String details){
		int size_before = list.size();
		final long removeIndex_0 = System.nanoTime();
		list.remove(index);
		final long removeIndex_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, removeIndex_1 - removeIndex_0);
	}
	
	private static DataStructureTimingResult timeListRemoveElement(List<Integer> list, String method, int order, int index, String details){
		Integer value = list.size() + 1 > rangeHigh ? list.size() + 1 : rangeHigh + 1; // unique
		list.set(index, value);
		int size_before = list.size();
		final long removeValue_0 = System.nanoTime();
		list.remove(value);
		final long removeValue_1 = System.nanoTime();
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, removeValue_1 - removeValue_0);
	}

	private static DataStructureTimingResult timeListRemoveWithIterator(List<Integer> list, String method, int order, int index, String details){
		Integer value = list.size() + 1 > rangeHigh ? list.size() + 1 : rangeHigh + 1; // unique
		list.set(index, value);
		
		Iterator<Integer> iterator = list.iterator();
		long removeIterator_0 = 0, removeIterator_1 = -1;
		
		int size_before = list.size();
		while(iterator.hasNext()){
			if(iterator.next() == value){
				removeIterator_0 = System.nanoTime();
				iterator.remove();
				removeIterator_1 = System.nanoTime();
				break;
			}
		}
		int size_after = list.size();
		
		return new DataStructureTimingResult(list, method, order, details, size_before, size_after, removeIterator_1 - removeIterator_0);
	}
}
