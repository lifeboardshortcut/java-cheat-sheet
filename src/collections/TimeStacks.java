package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import utils.print.PrintUtils;
import utils.timing.DataStructureTimingResult;

/*
 * This class times access, insert, search, and delete
 * operations on java stacks. Tests are performed on a variety
 * of cases and on a definable number of arrays of different
 * size in order to see the effect of size on performance
 * 
 * TimingResult class is used for the recording timing of 
 * tests and then printing everything in the desired order
 * afterwards
 */
public class TimeStacks {
	private static int[] stackSizes = new int[]{10000, 20000, 30000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	private final static int rangeLow = 0;
	private final static int rangeHigh = 999;
	
	public static void main(String[] args){
		// Array Lists
		List<Stack<Integer>> stacks = new ArrayList<Stack<Integer>>();
		for(int i = 0; i < stackSizes.length; i++){
			stacks.add(new Stack<Integer>());
		}
		timingResults.addAll(timeStacks(stacks, stackSizes));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	private static List<DataStructureTimingResult> timeStacks(List<Stack<Integer>> lists, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		
		// Push
		results.addAll(timeStackPush(lists, sizes));
		
		// Peek
		results.addAll(timeStackPeek(lists, sizes));
		
		// Search
		results.addAll(timeStackSearch(lists, sizes));
		
		// Pop
		results.addAll(timeStackPop(lists, sizes));
		
		return results;
	}
	
	/*
	 * ********************
	 *    stack.push()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeStackPush(List<Stack<Integer>> stacks, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".push()";
		for(int i = 0; i < stacks.size(); i++){
			Stack<Integer> stack = stacks.get(i);
			results.add(timePushToStack(stack, method, 1, "Add to Empty Stack", rangeLow - 1));
			results.addAll(timePushToStackInLoop(stack, method, 2, sizes[i]));
			results.add(timePushToStack(stack, method, 5, "Add to Full Stack", rangeHigh + 1));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timePushToStack(Stack<Integer> stack, String method, 
			int order, String details, int value){
		int size_before = stack.size();
		final long push_0 = System.nanoTime();
		stack.push(value);
		final long push_1 = System.nanoTime();
		int size_after = stack.size();
		
		return new DataStructureTimingResult(stack, method, order, details, 
				size_before, size_after, push_1 - push_0);
	}
	
	private static List<DataStructureTimingResult> timePushToStackInLoop(Stack<Integer> stack, String method, int order, int size){
		int value;
		int size_before = stack.size();
		long pushLoop_0, pushLoop_1, pushLoop_time, pushLoop_total = 0, 
				pushLoop_max = Long.MIN_VALUE, pushLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			value = ThreadLocalRandom.current().nextInt(rangeLow, rangeHigh);
			if(i == size / 2){
				value = rangeHigh + 2;
			}
			pushLoop_0 = System.nanoTime();
			stack.push(value);
			pushLoop_1 = System.nanoTime();
			
			pushLoop_time = pushLoop_1 - pushLoop_0;
			pushLoop_total += pushLoop_time;
			if(pushLoop_time > pushLoop_max) { pushLoop_max = pushLoop_time; }
			if(pushLoop_time < pushLoop_min) { pushLoop_min = pushLoop_time; }
		}
		int size_after = stack.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(stack, method, order, "Push Loop: Min Time", size_before, size_after, pushLoop_min));
		results.add(new DataStructureTimingResult(stack, method, order + 1, "Push Loop: Max Time", size_before, size_after, pushLoop_max));
		results.add(new DataStructureTimingResult(stack, method, order + 2, "Push Loop: Avg Time", size_before, size_after, pushLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *    stack.peek()
	 * ********************
	 */
	
	private static List<DataStructureTimingResult> timeStackPeek(List<Stack<Integer>> stacks, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".peek()";
		for(int i = 0; i < stacks.size(); i++){
			Stack<Integer> stack = stacks.get(i);
			results.add(timePeekAtStack(stack, method, 1, ""));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timePeekAtStack(Stack<Integer> stack, String method, int order, String details){
		int size_before = stack.size();
		final long peek_0 = System.nanoTime();
		stack.peek();
		final long peek_1 = System.nanoTime();
		int size_after = stack.size();
		
		return new DataStructureTimingResult(stack, method, order, details, 
				size_before, size_after, peek_1 - peek_0);
	}
	
	
	/*
	 * ********************
	 *    stack.search()
	 * ********************
	 */
	
	private static List<DataStructureTimingResult> timeStackSearch(List<Stack<Integer>> stacks, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".search()";
		for(int i = 0; i < stacks.size(); i++){
			Stack<Integer> stack = stacks.get(i);
			results.add(timeStackSearch(stack, method, 1, "Search: Top", rangeHigh + 1));
			results.add(timeStackSearch(stack, method, 2, "Search: Middle", rangeHigh + 2));
			results.add(timePushToStack(stack, method, 3, "Search: Bottom", rangeLow - 1));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeStackSearch(Stack<Integer> stack, String method, 
			int order, String details, int value){
		int size_before = stack.size();
		final long search_0 = System.nanoTime();
		stack.search(value);
		final long search_1 = System.nanoTime();
		int size_after = stack.size();
		
		return new DataStructureTimingResult(stack, method, order, details, 
				size_before, size_after, search_1 - search_0);
	}
	

	/*
	 * ********************
	 *     stack.pop()
	 * ********************
	 */
	
	private static List<DataStructureTimingResult> timeStackPop(List<Stack<Integer>> stacks, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".pop()";
		for(int i = 0; i < stacks.size(); i++){
			Stack<Integer> stack = stacks.get(i);
			results.addAll(timePopFromStackInLoop(stack, method, 2, sizes[i]));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timePopFromStackInLoop(Stack<Integer> stack, 
			String method, int order, int size){
		int size_before = stack.size();
		long popLoop_0, popLoop_1, popLoop_time, popLoop_total = 0, 
				popLoop_max = Long.MIN_VALUE, popLoop_min = Long.MAX_VALUE;
		while(!stack.isEmpty()){
			popLoop_0 = System.nanoTime();
			stack.pop();
			popLoop_1 = System.nanoTime();
			
			popLoop_time = popLoop_1 - popLoop_0;
			popLoop_total += popLoop_time;
			if(popLoop_time > popLoop_max) { popLoop_max = popLoop_time; }
			if(popLoop_time < popLoop_min) { popLoop_min = popLoop_time; }
		}
		int size_after = stack.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(stack, method, order, "Pop Loop: Min Time", size_before, size_after, popLoop_min));
		results.add(new DataStructureTimingResult(stack, method, order + 1, "Pop Loop: Max Time", size_before, size_after, popLoop_max));
		results.add(new DataStructureTimingResult(stack, method, order + 2, "Pop Loop: Avg Time", size_before, size_after, popLoop_total / size));
		return results;
	}	
}
