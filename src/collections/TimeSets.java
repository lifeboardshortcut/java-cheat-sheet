package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import utils.print.PrintUtils;
import utils.timing.DataStructureTimingResult;

/*
 * This class times access, insert, search, and delete
 * operations on java sets. Tests are performed on a variety
 * of cases and on a definable number of arrays of different
 * size in order to see the effect of size on performance
 * 
 * TimingResult class is used for the recording timing of 
 * tests and then printing everything in the desired order
 * afterwards
 */
public class TimeSets {
	private static Integer[] hashSetSizes = new Integer[]{10000, 20000, 30000};
	private static Integer[] treeSetSizes = new Integer[]{10000, 20000, 40000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	private static Integer maxHash = Stream.of(hashSetSizes).max(Integer::compare).get();
	private static Integer maxTree = Stream.of(treeSetSizes).max(Integer::compare).get();
	private static Integer maxMap = Math.max(maxHash, maxTree);
	private static Integer offset = 1;
	
	public static void main(String[] args){
		List<Integer> setKeys = IntStream.range(0, maxMap).boxed().collect(Collectors.toList());
		Collections.shuffle(setKeys);
		
		// Hash Maps
		List<Set<Integer>> hashSets = new ArrayList<Set<Integer>>();
		for(int i = 0; i < hashSetSizes.length; i++){
			hashSets.add(new HashSet<Integer>());
		}
		timingResults.addAll(timeSets(hashSets, hashSetSizes, setKeys));
		
		// Tree Maps
		List<Set<Integer>> treeSets = new ArrayList<Set<Integer>>();
		for(int i = 0; i < treeSetSizes.length; i++){
			treeSets.add(new TreeSet<Integer>());
		}
		timingResults.addAll(timeSets(treeSets, treeSetSizes, setKeys));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	private static List<DataStructureTimingResult> timeSets(List<Set<Integer>> sets, 
			Integer[] sizes, List<Integer> setKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		
		// Insertion
		results.addAll(timeSetAdd(sets, sizes, setKeys));

		// Search
		results.addAll(timeSetContains(sets, sizes, setKeys));

		// Iterate
		results.addAll(timeSetIterations(sets));

		// Deletion
		results.addAll(timeSetDeletion(sets, sizes, setKeys));
		
		return results;
	}
	
	/*
	 * ********************
	 *      set.add()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeSetAdd(List<Set<Integer>> sets, Integer[] sizes,
			List<Integer> setKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".add()";
		for(int i = 0; i < sets.size(); i++){
			Set<Integer> list = sets.get(i);
			results.add(timeAddToList(list, method, 1, "Add to Empty Set", maxMap + offset++));
			results.addAll(timeAddToListInLoop(list, method, 2, sizes[i], setKeys));
			results.add(timeAddToList(list, method, 5, "Add to Full Set", maxMap + offset++));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeAddToList(Set<Integer> set, String method, 
			int order, String details, int value){
		int size_before = set.size();
		final long add_0 = System.nanoTime();
		set.add(value);
		final long add_1 = System.nanoTime();
		int size_after = set.size();
		
		return new DataStructureTimingResult(set, method, order, details, 
				size_before, size_after, add_1 - add_0);
	}
	
	private static List<DataStructureTimingResult> timeAddToListInLoop(Set<Integer> set, String method, 
			int order, int size, List<Integer> setKeys){
		int value;
		int size_before = set.size();
		long setLoop_0, setLoop_1, setLoop_time, setLoop_total = 0, 
				setLoop_max = Long.MIN_VALUE, setLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			value = setKeys.get(i);
			setLoop_0 = System.nanoTime();
			set.add(value);
			setLoop_1 = System.nanoTime();
			
			setLoop_time = setLoop_1 - setLoop_0;
			setLoop_total += setLoop_time;
			if(setLoop_time > setLoop_max) { setLoop_max = setLoop_time; }
			if(setLoop_time < setLoop_min) { setLoop_min = setLoop_time; }
		}
		int size_after = set.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(set, method, order, "Add Loop: Min Time", size_before, size_after, setLoop_min));
		results.add(new DataStructureTimingResult(set, method, order + 1, "Add Loop: Max Time", size_before, size_after, setLoop_max));
		results.add(new DataStructureTimingResult(set, method, order + 2, "Add Loop: Avg Time", size_before, size_after, setLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *   set.contains()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeSetContains(List<Set<Integer>> sets, 
			Integer[] sizes, List<Integer> setKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".contains()";
		for(int i = 0; i < sets.size(); i++){
			Set<Integer> map = sets.get(i);
			results.add(timeSetContains(map, method, 1, "Contains Nonexistant Key", maxMap + offset++));
			results.addAll(timeSetContainsInLoop(map, method, 2, sizes[i], setKeys));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeSetContains(Set<Integer> set, 
			String method, int order, String details, int key)
	{
		int size_before = set.size();
		final long containsKey_0 = System.nanoTime();
		set.contains(key);
		final long containsKey_1 = System.nanoTime();
		int size_after = set.size();
		
		return new DataStructureTimingResult(set, method, order, details, 
				size_before, size_after, containsKey_1 - containsKey_0);
	}
	
	private static List<DataStructureTimingResult> timeSetContainsInLoop(Set<Integer> set, 
			String method, int order, int size, List<Integer> setKeys)
	{
		int key;
		int size_before = set.size();
		long containsLoop_0, containsLoop_1, containsLoop_time, containsLoop_total = 0, 
				containsLoop_max = Long.MIN_VALUE, containsLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = setKeys.get(i);
			containsLoop_0 = System.nanoTime();
			set.contains(key);
			containsLoop_1 = System.nanoTime();
			
			containsLoop_time = containsLoop_1 - containsLoop_0;
			containsLoop_total += containsLoop_time;
			if(containsLoop_time > containsLoop_max) { containsLoop_max = containsLoop_time; }
			if(containsLoop_time < containsLoop_min) { containsLoop_min = containsLoop_time; }
		}
		int size_after = set.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(set, method, order, "Contains Loop: Min Time", size_before, size_after, containsLoop_min));
		results.add(new DataStructureTimingResult(set, method, order + 1, "Contains Loop: Max Time", size_before, size_after, containsLoop_max));
		results.add(new DataStructureTimingResult(set, method, order + 2, "Contains Loop: Avg Time", size_before, size_after, containsLoop_total / size));
		return results;
	}
	

	/*
	 * ********************
	 *   iterator.next()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeSetIterations(List<Set<Integer>> sets){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".iterator.next()";
		for(int i = 0; i < sets.size(); i++){
			Set<Integer> set = sets.get(i);
			results.addAll(timeSetIterator(set, method, 1));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeSetIterator(Set<Integer> set, 
			String method, int order)
	{
		int size_before = set.size();
		long iterator_0, iterator_1, iterator_time, iterator_total = 0, 
				iterator_max = Long.MIN_VALUE, iterator_min = Long.MAX_VALUE;
		Iterator<Integer> iterator = set.iterator();
		while(iterator.hasNext()){
			iterator_0 = System.nanoTime();
			iterator.next();
			iterator_1 = System.nanoTime();
			
			iterator_time = iterator_1 - iterator_0;
			iterator_total += iterator_time;
			if(iterator_time > iterator_max) { iterator_max = iterator_time; }
			if(iterator_time < iterator_min) { iterator_min = iterator_time; }
		}
		int size_after = set.size();

		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(set, method, order, "Iterator Loop: Min Time", size_before, size_after, iterator_min));
		results.add(new DataStructureTimingResult(set, method, order + 1, "Iterator Loop: Max Time", size_before, size_after, iterator_max));
		results.add(new DataStructureTimingResult(set, method, order + 2, "Iterator Loop: Avg Time", size_before, size_after, iterator_total / size_after));
		return results;
	} 
	

	/*
	 * ********************
	 *   set.remove()
	 * ********************
	 */	
	private static List<DataStructureTimingResult> timeSetDeletion(List<Set<Integer>> sets, 
			Integer[] sizes, List<Integer> setKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".remove()";
		for(int i = 0; i < sets.size(); i++){
			Set<Integer> set = sets.get(i);
			results.addAll(timeSetDeletionInLoop(set, method, 1, sizes[i], setKeys));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeSetDeletionInLoop(Set<Integer> set, 
			String method, int order, int size, List<Integer> setKeys)
	{
		int key;
		int size_before = set.size();
		long removeLoop_0, removeLoop_1, removeLoop_time, removeLoop_total = 0, 
				removeLoop_max = Long.MIN_VALUE, removeLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = setKeys.get(i);
			removeLoop_0 = System.nanoTime();
			set.remove(key);
			removeLoop_1 = System.nanoTime();
			
			removeLoop_time = removeLoop_1 - removeLoop_0;
			removeLoop_total += removeLoop_time;
			if(removeLoop_time > removeLoop_max) { removeLoop_max = removeLoop_time; }
			if(removeLoop_time < removeLoop_min) { removeLoop_min = removeLoop_time; }
		}
		int size_after = set.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(set, method, order, "Remove Loop: Min Time", size_before, size_after, removeLoop_min));
		results.add(new DataStructureTimingResult(set, method, order + 1, "Remove Loop: Max Time", size_before, size_after, removeLoop_max));
		results.add(new DataStructureTimingResult(set, method, order + 2, "Remove Loop: Avg Time", size_before, size_after, removeLoop_total / size));
		return results;
	}
}
