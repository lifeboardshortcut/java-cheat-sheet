package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import utils.timing.SortTimingResult;
import utils.print.PrintUtils;

public class TimeSorting {
	static int[] empty,
		oneElement,
		oneValue,
		uniform_unordered,
		uniform_ordered,
		uniform_backwards,
		random_unordered,
		random_ordered,
		random_backwards;
	
	private static List<SortTimingResult> timingResults = new ArrayList<SortTimingResult>();
	static int[] arraySizes = {1000, 2000, 3000};
	static int[] mergeSortSizes = {10000, 20000, 30000};
	static int[] quickSortSizes = {10000, 20000, 30000};
	static int[] selectionSortSizes = {10000, 20000, 30000};
	static int[] insertionSortSizes = {10000, 20000, 30000};
	
	public static void main(String[] args){
		for(int size : arraySizes){
			populateArrays(size);
			timingResults.addAll(timeMergeSorts());
			timingResults.addAll(timeQuickSorts());
			timingResults.addAll(timeSelectionSorts());
			timingResults.addAll(timeInsertionSorts());
		}

		// Print Results
		int breakOnSortIndex = 1;
		SortTimingResult.SortTRComp comparator = 
				SortTimingResult.SortTRComp.INPUT_SORT_SIZE_TIME;
		PrintUtils.printSortResults(timingResults, comparator, breakOnSortIndex);
	}
	
	public static void populateArrays(int size){
		empty = new int[0];		
		oneElement = new int[]{8};		
		oneValue = new int[size];
		Arrays.fill(oneValue, 8);		
		uniform_unordered = fillUniform(1, size);		
		uniform_ordered = fillUniform(2, size);		
		uniform_backwards = fillUniform(3, size);		
		random_unordered = fillRandom(1, size);		
		random_ordered = fillRandom(2, size);
		random_backwards = fillRandom(3, size);
	}
	
	public static int[] fillUniform(int ordering, int size){
		int[] array = new int[size];
		for(int i = 0; i < size; i++)
		{
			if(ordering == 3){ // backwards
				array[i] = size - 1 - i;
			} else {
				array[i] = i;
			}
		}
		if(ordering == 1){ // unordered
			shuffleArray(array);
		}
		return array;
	}
	
	public static int[] fillRandom(int ordering, int size){
		int[] array = new int[size];
		int upperBound = size;
		Random rnd = ThreadLocalRandom.current();
		for(int i = 0; i < size; i++)
		{
			array[i] = rnd.nextInt(upperBound);
		}
		
		if(ordering == 2 || ordering == 3){ // ordered fwd
			Arrays.sort(array);
			if(ordering == 3){ // ordered bwd
				reverseArray(array);
			}
		}
		return array;
	}
	
	public static void reverseArray(int[] array) {
	    int tmp;    

	    for (int i = 0; i < array.length / 2; i++) {
	        tmp = array[i];
	        array[i] = array[array.length - 1 - i];
	        array[array.length - 1 - i] = tmp;
	    }
	}
	
	public static void shuffleArray(int[] array)
	{
		Random rnd = ThreadLocalRandom.current();
		for (int i = array.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
	}

	public static List<SortTimingResult> timeQuickSorts() {
		List<SortTimingResult> results = new ArrayList<SortTimingResult>();
		
		int[] quick_empty = Arrays.copyOf(empty, empty.length);
		int[] quick_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] quick_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] quick_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] quick_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] quick_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] quick_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] quick_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] quick_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		results.add(timeQuickSort(quick_empty, 1, "Empty"));
		results.add(timeQuickSort(quick_oneElement, 2, "One Element"));
		results.add(timeQuickSort(quick_oneValue, 3, "One Value"));
		results.add(timeQuickSort(quick_uniform_unordered, 4, "Uniform: Unordered"));
		results.add(timeQuickSort(quick_uniform_ordered, 5, "Uniform: Ordered"));
		results.add(timeQuickSort(quick_uniform_backwards, 6, "Uniform: Backwards"));
		results.add(timeQuickSort(quick_random_unordered, 7, "Random: Unordered"));
		results.add(timeQuickSort(quick_random_ordered, 8, "Random: Ordered"));
		results.add(timeQuickSort(quick_random_backwards, 9, "Random: Backwards"));
		
		return results;
	}
	
	public static SortTimingResult timeQuickSort(int[] array, int order, String details){
		QuickSort qs = new QuickSort();
		long sort_0 = System.nanoTime();
		qs.quickSort(array, 0, array.length - 1);
		long sort_1 = System.nanoTime();
		
		return new SortTimingResult("QuickSort", sort_1 - sort_0, array.length, order, details);
	}

	public static List<SortTimingResult> timeMergeSorts() {
		List<SortTimingResult> results = new ArrayList<SortTimingResult>();

		int[] merge_empty = Arrays.copyOf(empty, empty.length);
		int[] merge_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] merge_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] merge_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] merge_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] merge_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] merge_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] merge_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] merge_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);

		results.add(timeMergeSort(merge_empty, 1, "Empty"));
		results.add(timeMergeSort(merge_oneElement, 2, "One Element"));
		results.add(timeMergeSort(merge_oneValue, 3, "One Value"));
		results.add(timeMergeSort(merge_uniform_unordered, 4, "Uniform: Unordered"));
		results.add(timeMergeSort(merge_uniform_ordered, 5, "Uniform: Ordered"));
		results.add(timeMergeSort(merge_uniform_backwards, 6, "Uniform: Backwards"));
		results.add(timeMergeSort(merge_random_unordered, 7, "Random: Unordered"));
		results.add(timeMergeSort(merge_random_ordered, 8, "Random: Ordered"));
		results.add(timeMergeSort(merge_random_backwards, 9, "Random: Backwards"));
		
		return results;
	}
	
	public static SortTimingResult timeMergeSort(int[] array, int order, String details){
		MergeSort ms = new MergeSort();
		long sort_0 = System.nanoTime();
		ms.mergeSort(array, 0, array.length - 1);
		long sort_1 = System.nanoTime();
		
		return new SortTimingResult("MergeSort", sort_1 - sort_0, array.length, order, details);
	}
	
	public static List<SortTimingResult> timeSelectionSorts() {
		List<SortTimingResult> results = new ArrayList<SortTimingResult>();
		
		int[] selection_empty = Arrays.copyOf(empty, empty.length);
		int[] selection_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] selection_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] selection_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] selection_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] selection_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] selection_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] selection_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] selection_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		results.add(timeSelectionSort(selection_empty, 1, "Empty"));
		results.add(timeSelectionSort(selection_oneElement, 2, "One Element"));
		results.add(timeSelectionSort(selection_oneValue, 3, "One Value"));
		results.add(timeSelectionSort(selection_uniform_unordered, 4, "Uniform: Unordered"));
		results.add(timeSelectionSort(selection_uniform_ordered, 5, "Uniform: Ordered"));
		results.add(timeSelectionSort(selection_uniform_backwards, 6, "Uniform: Backwards"));
		results.add(timeSelectionSort(selection_random_unordered, 7, "Random: Unordered"));
		results.add(timeSelectionSort(selection_random_ordered, 8, "Random: Ordered"));
		results.add(timeSelectionSort(selection_random_backwards, 9, "Random: Backwards"));
		
		return results;
	}
	
	public static SortTimingResult timeSelectionSort(int[] array, int order, String details){
		SelectionSort sel = new SelectionSort();
		long sort_0 = System.nanoTime();
		sel.selectionSort(array);
		long sort_1 = System.nanoTime();
		
		return new SortTimingResult("SelectionSort", sort_1 - sort_0, array.length, order, details);
	}
	
	public static List<SortTimingResult> timeInsertionSorts() {
		List<SortTimingResult> results = new ArrayList<SortTimingResult>();
		
		int[] insertion_empty = Arrays.copyOf(empty, empty.length);
		int[] insertion_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] insertion_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] insertion_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] insertion_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] insertion_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] insertion_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] insertion_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] insertion_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		results.add(timeInsertionSort(insertion_empty, 1, "Empty"));
		results.add(timeInsertionSort(insertion_oneElement, 2, "One Element"));
		results.add(timeInsertionSort(insertion_oneValue, 3, "One Value"));
		results.add(timeInsertionSort(insertion_uniform_unordered, 4, "Uniform: Unordered"));
		results.add(timeInsertionSort(insertion_uniform_ordered, 5, "Uniform: Ordered"));
		results.add(timeInsertionSort(insertion_uniform_backwards, 6, "Uniform: Backwards"));
		results.add(timeInsertionSort(insertion_random_unordered, 7, "Random: Unordered"));
		results.add(timeInsertionSort(insertion_random_ordered, 8, "Random: Ordered"));
		results.add(timeInsertionSort(insertion_random_backwards, 9, "Random: Backwards"));
		
		return results;
	}
	
	public static SortTimingResult timeInsertionSort(int[] array, int order, String details){
		InsertionSort ins = new InsertionSort();
		long sort_0 = System.nanoTime();
		ins.insertionSort(array);
		long sort_1 = System.nanoTime();
		
		return new SortTimingResult("InsertionSort", sort_1 - sort_0, array.length, order, details);
	}
}
