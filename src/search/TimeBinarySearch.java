package search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.timing.DataStructureTimingResult;
import utils.print.PrintUtils;

public class TimeBinarySearch {
	private static Integer[] listSizes = new Integer[]{10000, 20000, 40000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	
	public static void main(String[] args) throws IncorrectSearchException{
		List<List<Integer>> orderedLists = new ArrayList<List<Integer>>();
		for(int i = 0; i < listSizes.length; i++){
			List<Integer> ordered = IntStream.range(0, listSizes[i])
					.boxed().collect(Collectors.toList());
			orderedLists.add(ordered);
		}
		
		timingResults.addAll(timeSearch(orderedLists, listSizes));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	public static List<DataStructureTimingResult> timeSearch(List<List<Integer>> lists, 
			Integer[] sizes) throws IncorrectSearchException{
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".binarySearch()";
		for(int i = 0; i < lists.size(); i++){
			List<Integer> list = lists.get(i);
			results.addAll(timeSearchInLoop(list, method, 1, sizes[i]));		
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timeSearchInLoop(List<Integer> list, 
			String method, int order, int size) throws IncorrectSearchException{
		BinarySearch bs = new BinarySearch();
		int key;
		int size_before = list.size();
		long searchLoop_0, searchLoop_1, searchLoop_time, searchLoop_total = 0, 
				searchLoop_max = Long.MIN_VALUE, searchLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			key = list.get(i);
			searchLoop_0 = System.nanoTime();
			int index = bs.binarySearch(list, key);
			searchLoop_1 = System.nanoTime();
			
			if(index != i){ // keys are ordered/unique
				throw new IncorrectSearchException();
			}
			
			searchLoop_time = searchLoop_1 - searchLoop_0;
			searchLoop_total += searchLoop_time;
			if(searchLoop_time > searchLoop_max) { searchLoop_max = searchLoop_time; }
			if(searchLoop_time < searchLoop_min) { searchLoop_min = searchLoop_time; }
		}
		int size_after = list.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(list, method, order, "Loop: Min Time", size_before, size_after, searchLoop_min));
		results.add(new DataStructureTimingResult(list, method, order + 1, "Loop: Max Time", size_before, size_after, searchLoop_max));
		results.add(new DataStructureTimingResult(list, method, order + 2, "Loop: Avg Time", size_before, size_after, searchLoop_total / size));
		return results;
	}
}
