package sort;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

public class TestSorting {
	int[] empty, empty_sorted,
		oneElement, oneElement_sorted,
		oneValue, oneValue_sorted,
		uniform_unordered, uniform_unordered_sorted,
		uniform_ordered, uniform_ordered_sorted,
		uniform_backwards, uniform_backwards_sorted,
		random_unordered, random_unordered_sorted,
		random_ordered, random_ordered_sorted,
		random_backwards, random_backwards_sorted;
	
	int size = 100;
	
	@Before
	public void setUp(){
		empty = new int[0];
		empty_sorted = new int[0];
		
		oneElement = new int[]{8};
		oneElement_sorted = new int[]{8};
		
		oneValue = new int[size];
		oneValue_sorted = new int[size];
		Arrays.fill(oneValue, 8);
		Arrays.fill(oneValue_sorted, 8);
		
		uniform_unordered = fillUniform(1, size);
		uniform_unordered_sorted = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		Arrays.sort(uniform_unordered_sorted);
		
		uniform_ordered = fillUniform(2, size);
		uniform_ordered_sorted = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		Arrays.sort(uniform_ordered_sorted);
		
		uniform_backwards = fillUniform(3, size);
		uniform_backwards_sorted = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		Arrays.sort(uniform_backwards_sorted);
		
		random_unordered = fillRandom(1, size);
		random_unordered_sorted = Arrays.copyOf(random_unordered, random_unordered.length);
		Arrays.sort(random_unordered_sorted);
		
		random_ordered = fillRandom(2, size);
		random_ordered_sorted = Arrays.copyOf(random_ordered, random_ordered.length);
		Arrays.sort(random_ordered_sorted);
		
		random_backwards = fillRandom(3, size);
		random_backwards_sorted = Arrays.copyOf(random_backwards, random_backwards.length);
		Arrays.sort(random_backwards_sorted);
	}
	
	public int[] fillUniform(int ordering, int size){
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
	
	public int[] fillRandom(int ordering, int size){
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
	
	public void reverseArray(int[] array) {
	    int tmp;    

	    for (int i = 0; i < array.length / 2; i++) {
	        tmp = array[i];
	        array[i] = array[array.length - 1 - i];
	        array[array.length - 1 - i] = tmp;
	    }
	}
	
	public void shuffleArray(int[] array)
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
	
	@Test
	public void testQuicksort() {
		int[] quick_empty = Arrays.copyOf(empty, empty.length);
		int[] quick_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] quick_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] quick_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] quick_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] quick_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] quick_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] quick_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] quick_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		QuickSort qs = new QuickSort();
		qs.quickSort(quick_empty, 0, quick_empty.length - 1);
		qs.quickSort(quick_oneElement, 0, quick_oneElement.length - 1);
		qs.quickSort(quick_oneValue, 0, quick_oneValue.length - 1);
		qs.quickSort(quick_uniform_unordered, 0, quick_uniform_unordered.length - 1);
		qs.quickSort(quick_uniform_ordered, 0, quick_uniform_ordered.length - 1);
		qs.quickSort(quick_uniform_backwards, 0, quick_uniform_backwards.length - 1);
		qs.quickSort(quick_random_unordered, 0, quick_random_unordered.length - 1);
		qs.quickSort(quick_random_ordered, 0, quick_random_ordered.length - 1);
		qs.quickSort(quick_random_backwards, 0, quick_random_backwards.length - 1);
		
		assertArrayEquals(quick_empty, empty_sorted);
		assertArrayEquals(quick_oneElement, oneElement_sorted);
		assertArrayEquals(quick_oneValue, oneValue_sorted);
		assertArrayEquals(quick_uniform_unordered, uniform_unordered_sorted);
		assertArrayEquals(quick_uniform_ordered, uniform_ordered_sorted);
		assertArrayEquals(quick_uniform_backwards, uniform_backwards_sorted);
		assertArrayEquals(quick_random_unordered, random_unordered_sorted);
		assertArrayEquals(quick_random_ordered, random_ordered_sorted);
		assertArrayEquals(quick_random_backwards, random_backwards_sorted);
	}

	@Test
	public void testMergesort() {
		int[] merge_empty = Arrays.copyOf(empty, empty.length);
		int[] merge_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] merge_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] merge_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] merge_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] merge_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] merge_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] merge_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] merge_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		MergeSort ms = new MergeSort();
		ms.mergeSort(merge_empty, 0, merge_empty.length - 1);
		ms.mergeSort(merge_oneElement, 0, merge_oneElement.length - 1);
		ms.mergeSort(merge_oneValue, 0, merge_oneValue.length - 1);
		ms.mergeSort(merge_uniform_unordered, 0, merge_uniform_unordered.length - 1);
		ms.mergeSort(merge_uniform_ordered, 0, merge_uniform_ordered.length - 1);
		ms.mergeSort(merge_uniform_backwards, 0, merge_uniform_backwards.length - 1);
		ms.mergeSort(merge_random_unordered, 0, merge_random_unordered.length - 1);
		ms.mergeSort(merge_random_ordered, 0, merge_random_ordered.length - 1);
		ms.mergeSort(merge_random_backwards, 0, merge_random_backwards.length - 1);
		
		assertArrayEquals(merge_empty, empty_sorted);
		assertArrayEquals(merge_oneElement, oneElement_sorted);
		assertArrayEquals(merge_oneValue, oneValue_sorted);
		assertArrayEquals(merge_uniform_unordered, uniform_unordered_sorted);
		assertArrayEquals(merge_uniform_ordered, uniform_ordered_sorted);
		assertArrayEquals(merge_uniform_backwards, uniform_backwards_sorted);
		assertArrayEquals(merge_random_unordered, random_unordered_sorted);
		assertArrayEquals(merge_random_ordered, random_ordered_sorted);
		assertArrayEquals(merge_random_backwards, random_backwards_sorted);
	}
	
	@Test
	public void testSelectionSort() {
		int[] selection_empty = Arrays.copyOf(empty, empty.length);
		int[] selection_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] selection_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] selection_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] selection_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] selection_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] selection_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] selection_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] selection_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		SelectionSort sel = new SelectionSort();
		sel.selectionSort(selection_empty);
		sel.selectionSort(selection_oneElement);
		sel.selectionSort(selection_oneValue);
		sel.selectionSort(selection_uniform_unordered);
		sel.selectionSort(selection_uniform_ordered);
		sel.selectionSort(selection_uniform_backwards);
		sel.selectionSort(selection_random_unordered);
		sel.selectionSort(selection_random_ordered);
		sel.selectionSort(selection_random_backwards);
		
		assertArrayEquals(selection_empty, empty_sorted);
		assertArrayEquals(selection_oneElement, oneElement_sorted);
		assertArrayEquals(selection_oneValue, oneValue_sorted);
		assertArrayEquals(selection_uniform_unordered, uniform_unordered_sorted);
		assertArrayEquals(selection_uniform_ordered, uniform_ordered_sorted);
		assertArrayEquals(selection_uniform_backwards, uniform_backwards_sorted);
		assertArrayEquals(selection_random_unordered, random_unordered_sorted);
		assertArrayEquals(selection_random_ordered, random_ordered_sorted);
		assertArrayEquals(selection_random_backwards, random_backwards_sorted);
	}
	
	@Test
	public void testInsertionSort() {
		int[] insertion_empty = Arrays.copyOf(empty, empty.length);
		int[] insertion_oneElement = Arrays.copyOf(oneElement, oneElement.length);
		int[] insertion_oneValue = Arrays.copyOf(oneValue, oneValue.length);
		int[] insertion_uniform_unordered = Arrays.copyOf(uniform_unordered, uniform_unordered.length);
		int[] insertion_uniform_ordered = Arrays.copyOf(uniform_ordered, uniform_ordered.length);
		int[] insertion_uniform_backwards = Arrays.copyOf(uniform_backwards, uniform_backwards.length);
		int[] insertion_random_unordered = Arrays.copyOf(random_unordered, random_unordered.length);
		int[] insertion_random_ordered = Arrays.copyOf(random_ordered, random_ordered.length);
		int[] insertion_random_backwards = Arrays.copyOf(random_backwards, random_backwards.length);
		
		InsertionSort ins = new InsertionSort();
		ins.insertionSort(insertion_empty);
		ins.insertionSort(insertion_oneElement);
		ins.insertionSort(insertion_oneValue);
		ins.insertionSort(insertion_uniform_unordered);
		ins.insertionSort(insertion_uniform_ordered);
		ins.insertionSort(insertion_uniform_backwards);
		ins.insertionSort(insertion_random_unordered);
		ins.insertionSort(insertion_random_ordered);
		ins.insertionSort(insertion_random_backwards);
		
		assertArrayEquals(insertion_empty, empty_sorted);
		assertArrayEquals(insertion_oneElement, oneElement_sorted);
		assertArrayEquals(insertion_oneValue, oneValue_sorted);
		assertArrayEquals(insertion_uniform_unordered, uniform_unordered_sorted);
		assertArrayEquals(insertion_uniform_ordered, uniform_ordered_sorted);
		assertArrayEquals(insertion_uniform_backwards, uniform_backwards_sorted);
		assertArrayEquals(insertion_random_unordered, random_unordered_sorted);
		assertArrayEquals(insertion_random_ordered, random_ordered_sorted);
		assertArrayEquals(insertion_random_backwards, random_backwards_sorted);
	}
}
