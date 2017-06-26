package maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import utils.print.PrintUtils;
import utils.timing.DataStructureTimingResult;

/*
 * This class times access, insert, search, and delete
 * operations on java maps. Tests are performed on a variety
 * of cases and on a definable number of arrays of different
 * size in order to see the effect of size on performance
 * 
 * TimingResult class is used for the recording timing of 
 * tests and then printing everything in the desired order
 * afterwards
 */
public class TimeMaps {
	private static Integer[] hashMapSizes = new Integer[]{10000, 20000, 30000};
	private static Integer[] treeMapSizes = new Integer[]{10000, 20000, 40000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	private static Integer maxHash = Stream.of(hashMapSizes).max(Integer::compare).get();
	private static Integer maxTree = Stream.of(treeMapSizes).max(Integer::compare).get();
	private static Integer maxMap = Math.max(maxHash, maxTree);
	private static Integer offset = 1;
	
	public static void main(String[] args){
		List<Integer> mapKeys = IntStream.range(0, maxMap).boxed().collect(Collectors.toList());
		Collections.shuffle(mapKeys);
		
		// Hash Maps
		List<Map<Integer, Integer>> hashMaps = new ArrayList<Map<Integer, Integer>>();
		for(int i = 0; i < hashMapSizes.length; i++){
			hashMaps.add(new HashMap<Integer, Integer>());
		}
		timingResults.addAll(timeMaps(hashMaps, hashMapSizes, mapKeys));
		
		// Tree Maps
		List<Map<Integer, Integer>> treeMaps = new ArrayList<Map<Integer, Integer>>();
		for(int i = 0; i < treeMapSizes.length; i++){
			treeMaps.add(new TreeMap<Integer, Integer>());
		}
		timingResults.addAll(timeMaps(treeMaps, treeMapSizes, mapKeys));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	private static List<DataStructureTimingResult> timeMaps(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		
		// Insertion
		results.addAll(timeMapPutNew(maps, sizes, mapKeys));

		// Access
		results.addAll(timeMapAccess(maps, sizes, mapKeys));

		// Search
		results.addAll(timeMapContainsKey(maps, sizes, mapKeys));
		results.addAll(timeMapContainsValue(maps, sizes, mapKeys));

		// Iterate
		results.addAll(timeMapIterations(maps));
		
		// Update
		results.addAll(timeMapPutUpdate(maps, sizes, mapKeys));

		// Deletion
		results.addAll(timeMapDeletion(maps, sizes, mapKeys));
		
		return results;
	}
	
	/*
	 * ********************
	 *     map.put()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapPutNew(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".put()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.add(timeAddToMap(map, method, 1, "Add to Empty Map", maxMap + offset++));
			results.addAll(timeAddToMapInLoop(map, method, 2, sizes[i], mapKeys));
			results.add(timeAddToMap(map, method, 5, "Add to Full Map", maxMap + offset++));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeAddToMap(Map<Integer, Integer> map, 
			String method, int order, String details, int key)
	{
		int value = 0;
		int size_before = map.size();
		final long add_0 = System.nanoTime();
		map.put(key, value);
		final long add_1 = System.nanoTime();
		int size_after = map.size();
		
		return new DataStructureTimingResult(map, method, order, details, 
				size_before, size_after, add_1 - add_0);
	}
	
	private static List<DataStructureTimingResult> timeAddToMapInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int key, value;
		int size_before = map.size();
		long addLoop_0, addLoop_1, addLoop_time, addLoop_total = 0, 
				addLoop_max = Long.MIN_VALUE, addLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = mapKeys.get(i);
			value = key;
			addLoop_0 = System.nanoTime();
			map.put(key,  value);
			addLoop_1 = System.nanoTime();
			
			addLoop_time = addLoop_1 - addLoop_0;
			addLoop_total += addLoop_time;
			if(addLoop_time > addLoop_max) { addLoop_max = addLoop_time; }
			if(addLoop_time < addLoop_min) { addLoop_min = addLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "Add Loop: Min Time", size_before, size_after, addLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "Add Loop: Max Time", size_before, size_after, addLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "Add Loop: Avg Time", size_before, size_after, addLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *     map.get()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapAccess(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".get()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.add(timeAccessMapKey(map, method, 1, "Access Nonexistant Key", maxMap + offset++));
			results.addAll(timeAccessMapInLoop(map, method, 2, sizes[i], mapKeys));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeAccessMapKey(Map<Integer, Integer> map, 
			String method, int order, String details, int key)
	{
		int size_before = map.size();
		final long access_0 = System.nanoTime();
		map.get(key);
		final long access_1 = System.nanoTime();
		int size_after = map.size();
		
		return new DataStructureTimingResult(map, method, order, details, 
				size_before, size_after, access_1 - access_0);
	}
	
	private static List<DataStructureTimingResult> timeAccessMapInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int key;
		int size_before = map.size();
		long accessLoop_0, accessLoop_1, accessLoop_time, accessLoop_total = 0, 
				accessLoop_max = Long.MIN_VALUE, accessLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = mapKeys.get(i);
			accessLoop_0 = System.nanoTime();
			map.get(key);
			accessLoop_1 = System.nanoTime();
			
			accessLoop_time = accessLoop_1 - accessLoop_0;
			accessLoop_total += accessLoop_time;
			if(accessLoop_time > accessLoop_max) { accessLoop_max = accessLoop_time; }
			if(accessLoop_time < accessLoop_min) { accessLoop_min = accessLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "Access Loop: Min Time", size_before, size_after, accessLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "Access Loop: Max Time", size_before, size_after, accessLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "Access Loop: Avg Time", size_before, size_after, accessLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *   map.containsKey()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapContainsKey(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".containsKey()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.add(timeMapContainsKey(map, method, 1, "Contains Nonexistant Key", maxMap + offset++));
			results.addAll(timeMapContainsKeyInLoop(map, method, 2, sizes[i], mapKeys));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeMapContainsKey(Map<Integer, Integer> map, 
			String method, int order, String details, int key)
	{
		int size_before = map.size();
		final long containsKey_0 = System.nanoTime();
		map.containsKey(key);
		final long containsKey_1 = System.nanoTime();
		int size_after = map.size();
		
		return new DataStructureTimingResult(map, method, order, details, 
				size_before, size_after, containsKey_1 - containsKey_0);
	}
	
	private static List<DataStructureTimingResult> timeMapContainsKeyInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int key;
		int size_before = map.size();
		long containsKeyLoop_0, containsKeyLoop_1, containsKeyLoop_time, containsKeyLoop_total = 0, 
				containsKeyLoop_max = Long.MIN_VALUE, containsKeyLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = mapKeys.get(i);
			containsKeyLoop_0 = System.nanoTime();
			map.containsKey(key);
			containsKeyLoop_1 = System.nanoTime();
			
			containsKeyLoop_time = containsKeyLoop_1 - containsKeyLoop_0;
			containsKeyLoop_total += containsKeyLoop_time;
			if(containsKeyLoop_time > containsKeyLoop_max) { containsKeyLoop_max = containsKeyLoop_time; }
			if(containsKeyLoop_time < containsKeyLoop_min) { containsKeyLoop_min = containsKeyLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "ContainsKey Loop: Min Time", size_before, size_after, containsKeyLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "ContainsKey Loop: Max Time", size_before, size_after, containsKeyLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "ContainsKey Loop: Avg Time", size_before, size_after, containsKeyLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *  map.containsValue()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapContainsValue(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".containsValue()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.add(timeMapContainsValue(map, method, 1, "Contains Nonexistant Value", maxMap + offset++));
			results.addAll(timeMapContainsValueInLoop(map, method, 2, sizes[i], mapKeys));
		}
		
		return results;
	}
	
	
	private static DataStructureTimingResult timeMapContainsValue(Map<Integer, Integer> map, 
			String method, int order, String details, int value)
	{
		int size_before = map.size();
		final long containsValue_0 = System.nanoTime();
		map.containsValue(value);
		final long containsValue_1 = System.nanoTime();
		int size_after = map.size();
		
		return new DataStructureTimingResult(map, method, order, details, 
				size_before, size_after, containsValue_1 - containsValue_0);
	}
	
	private static List<DataStructureTimingResult> timeMapContainsValueInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int value;
		int size_before = map.size();
		long containsValueLoop_0, containsValueLoop_1, containsValueLoop_time, containsValueLoop_total = 0, 
				containsValueLoop_max = Long.MIN_VALUE, containsValueLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			value = mapKeys.get(i);
			containsValueLoop_0 = System.nanoTime();
			map.containsValue(value);
			containsValueLoop_1 = System.nanoTime();
			
			containsValueLoop_time = containsValueLoop_1 - containsValueLoop_0;
			containsValueLoop_total += containsValueLoop_time;
			if(containsValueLoop_time > containsValueLoop_max) { containsValueLoop_max = containsValueLoop_time; }
			if(containsValueLoop_time < containsValueLoop_min) { containsValueLoop_min = containsValueLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "ContainsValue Loop: Min Time", size_before, size_after, containsValueLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "ContainsValue Loop: Max Time", size_before, size_after, containsValueLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "ContainsValue Loop: Avg Time", size_before, size_after, containsValueLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *   iterator.next()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapIterations(List<Map<Integer, Integer>> maps){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".iterator.next()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.addAll(timeMapIterator(map, method, 1));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeMapIterator(Map<Integer, Integer> map, 
			String method, int order)
	{
		int size_before = map.size();
		long iterator_0, iterator_1, iterator_time, iterator_total = 0, 
				iterator_max = Long.MIN_VALUE, iterator_min = Long.MAX_VALUE;
		Set<Map.Entry<Integer, Integer>> entrySet = map.entrySet();
		Iterator<Map.Entry<Integer, Integer>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			iterator_0 = System.nanoTime();
			iterator.next();
			iterator_1 = System.nanoTime();
			
			iterator_time = iterator_1 - iterator_0;
			iterator_total += iterator_time;
			if(iterator_time > iterator_max) { iterator_max = iterator_time; }
			if(iterator_time < iterator_min) { iterator_min = iterator_time; }
		}
		int size_after = map.size();

		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "Iterator Loop: Min Time", size_before, size_after, iterator_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "Iterator Loop: Max Time", size_before, size_after, iterator_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "Iterator Loop: Avg Time", size_before, size_after, iterator_total / size_after));
		return results;
	} 
	
	/*
	 * ********************
	 *   map.put(existing)
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeMapPutUpdate(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".put(existing)";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.addAll(timeUpdateMapValueInLoop(map, method, 1, sizes[i], mapKeys));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeUpdateMapValueInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int key;
		int size_before = map.size();
		long updateValueLoop_0, updateValueLoop_1, updateValueLoop_time, updateValueLoop_total = 0, 
				updateValueLoop_max = Long.MIN_VALUE, updateValueLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = mapKeys.get(i);
			updateValueLoop_0 = System.nanoTime();
			map.put(key, 0);
			updateValueLoop_1 = System.nanoTime();
			
			updateValueLoop_time = updateValueLoop_1 - updateValueLoop_0;
			updateValueLoop_total += updateValueLoop_time;
			if(updateValueLoop_time > updateValueLoop_max) { updateValueLoop_max = updateValueLoop_time; }
			if(updateValueLoop_time < updateValueLoop_min) { updateValueLoop_min = updateValueLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "Update Loop: Min Time", size_before, size_after, updateValueLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "Update Loop: Max Time", size_before, size_after, updateValueLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "Update Loop: Avg Time", size_before, size_after, updateValueLoop_total / size));
		return results;
	}
		
	/*
	 * ********************
	 *   map.remove()
	 * ********************
	 */	
	private static List<DataStructureTimingResult> timeMapDeletion(List<Map<Integer, Integer>> maps, 
			Integer[] sizes, List<Integer> mapKeys){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".remove()";
		for(int i = 0; i < maps.size(); i++){
			Map<Integer, Integer> map = maps.get(i);
			results.addAll(timeMapDeletionInLoop(map, method, 1, sizes[i], mapKeys));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeMapDeletionInLoop(Map<Integer, Integer> map, 
			String method, int order, int size, List<Integer> mapKeys)
	{
		int key;
		int size_before = map.size();
		long removeLoop_0, removeLoop_1, removeLoop_time, removeLoop_total = 0, 
				removeLoop_max = Long.MIN_VALUE, removeLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = mapKeys.get(i);
			removeLoop_0 = System.nanoTime();
			map.remove(key);
			removeLoop_1 = System.nanoTime();
			
			removeLoop_time = removeLoop_1 - removeLoop_0;
			removeLoop_total += removeLoop_time;
			if(removeLoop_time > removeLoop_max) { removeLoop_max = removeLoop_time; }
			if(removeLoop_time < removeLoop_min) { removeLoop_min = removeLoop_time; }
		}
		int size_after = map.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(map, method, order, "Remove Loop: Min Time", size_before, size_after, removeLoop_min));
		results.add(new DataStructureTimingResult(map, method, order + 1, "Remove Loop: Max Time", size_before, size_after, removeLoop_max));
		results.add(new DataStructureTimingResult(map, method, order + 2, "Remove Loop: Avg Time", size_before, size_after, removeLoop_total / size));
		return results;
	}
}
