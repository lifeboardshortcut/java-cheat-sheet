package collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import utils.print.PrintUtils;
import utils.timing.DataStructureTimingResult;

/*
 * This class times access, insert, search, and delete
 * operations on java queues. Tests are performed on a variety
 * of cases and on a definable number of arrays of different
 * size in order to see the effect of size on performance
 * 
 * TimingResult class is used for the recording timing of 
 * tests and then printing everything in the desired order
 * afterwards
 */
public class TimeQueues {
	private static int[] priorityQueueSizes = new int[]{10000, 20000, 30000};
	private static int[] linkedListSizes = new int[]{10000, 20000, 30000};
	private static List<DataStructureTimingResult> timingResults = new ArrayList<DataStructureTimingResult>();
	private final static int rangeLow = 0;
	private final static int rangeHigh = 999;
	
	public static void main(String[] args){
		// Array Lists
		List<Queue<Integer>> priorityQueues = new ArrayList<Queue<Integer>>();
		for(int i = 0; i < priorityQueueSizes.length; i++){
			priorityQueues.add(new PriorityQueue<Integer>());
		}
		timingResults.addAll(timeQueues(priorityQueues, priorityQueueSizes));
		
		// Linked Lists
		List<Queue<Integer>> linkedLists = new ArrayList<Queue<Integer>>();
		for(int i = 0; i < linkedListSizes.length; i++){
			linkedLists.add(new LinkedList<Integer>());
		}
		timingResults.addAll(timeQueues(linkedLists, linkedListSizes));
		
		// Print Results
		int breakOnSortIndex = 2;
		DataStructureTimingResult.DSTRComp comparator = 
				DataStructureTimingResult.DSTRComp.METHOD_TEST_CLASS_SIZE_TIME;
		PrintUtils.printDataStructureResults(timingResults, comparator, breakOnSortIndex);
	}
	
	private static List<DataStructureTimingResult> timeQueues(List<Queue<Integer>> lists, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		
		// Offer
		results.addAll(timeQueueOffer(lists, sizes));
		
		// Peek
		results.addAll(timeQueuePeek(lists, sizes));
		
		// Poll
		results.addAll(timeQueuePoll(lists, sizes));
		
		return results;
	}
	
	/*
	 * ********************
	 *    queue.offer()
	 * ********************
	 */
	private static List<DataStructureTimingResult> timeQueueOffer(List<Queue<Integer>> queues, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".offer()";
		for(int i = 0; i < queues.size(); i++){
			Queue<Integer> queue = queues.get(i);
			results.add(timeOfferQueue(queue, method, 1, "Add to Empty Queue", rangeLow - 1));
			results.addAll(timeOfferToQueueInLoop(queue, method, 2, sizes[i]));
			results.add(timeOfferQueue(queue, method, 5, "Add to Full Queue", rangeHigh + 1));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timeOfferQueue(Queue<Integer> queue, String method, 
			int order, String details, int value){
		int size_before = queue.size();
		final long offer_0 = System.nanoTime();
		queue.offer(value);
		final long offer_1 = System.nanoTime();
		int size_after = queue.size();
		
		return new DataStructureTimingResult(queue, method, order, details, 
				size_before, size_after, offer_1 - offer_0);
	}
	
	private static List<DataStructureTimingResult> timeOfferToQueueInLoop(Queue<Integer> offer, String method, int order, int size){
		int value;
		int size_before = offer.size();
		long offerLoop_0, offerLoop_1, offerLoop_time, offerLoop_total = 0, 
				offerLoop_max = Long.MIN_VALUE, offerLoop_min = Long.MAX_VALUE;
		for(int i = 0; i < size; i++){
			value = ThreadLocalRandom.current().nextInt(rangeLow, rangeHigh);
			if(i == size / 2){
				value = rangeHigh + 2;
			}
			offerLoop_0 = System.nanoTime();
			offer.offer(value);
			offerLoop_1 = System.nanoTime();
			
			offerLoop_time = offerLoop_1 - offerLoop_0;
			offerLoop_total += offerLoop_time;
			if(offerLoop_time > offerLoop_max) { offerLoop_max = offerLoop_time; }
			if(offerLoop_time < offerLoop_min) { offerLoop_min = offerLoop_time; }
		}
		int size_after = offer.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(offer, method, order, "Offer Loop: Min Time", size_before, size_after, offerLoop_min));
		results.add(new DataStructureTimingResult(offer, method, order + 1, "Offer Loop: Max Time", size_before, size_after, offerLoop_max));
		results.add(new DataStructureTimingResult(offer, method, order + 2, "Offer Loop: Avg Time", size_before, size_after, offerLoop_total / size));
		return results;
	}
	
	/*
	 * ********************
	 *    queue.peek()
	 * ********************
	 */
	
	private static List<DataStructureTimingResult> timeQueuePeek(List<Queue<Integer>> queues, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".peek()";
		for(int i = 0; i < queues.size(); i++){
			Queue<Integer> queue = queues.get(i);
			results.add(timePeekAtQueue(queue, method, 1, ""));
		}
		
		return results;
	}
	
	private static DataStructureTimingResult timePeekAtQueue(Queue<Integer> queue, String method, int order, String details){
		int size_before = queue.size();
		final long peek_0 = System.nanoTime();
		queue.peek();
		final long peek_1 = System.nanoTime();
		int size_after = queue.size();
		
		return new DataStructureTimingResult(queue, method, order, details, 
				size_before, size_after, peek_1 - peek_0);
	}
	
	/*
	 * ********************
	 *     queue.poll()
	 * ********************
	 */
	
	private static List<DataStructureTimingResult> timeQueuePoll(List<Queue<Integer>> queues, int[] sizes){
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		String method = ".poll()";
		for(int i = 0; i < queues.size(); i++){
			Queue<Integer> queue = queues.get(i);
			results.addAll(timePollQueueInLoop(queue, method, 2, sizes[i]));
		}
		
		return results;
	}
	
	private static List<DataStructureTimingResult> timePollQueueInLoop(Queue<Integer> queue, 
			String method, int order, int size){
		int size_before = queue.size();
		long queueLoop_0, queueLoop_1, queueLoop_time, queueLoop_total = 0, 
				queueLoop_max = Long.MIN_VALUE, queueLoop_min = Long.MAX_VALUE;
		while(queue.peek() != null){
			queueLoop_0 = System.nanoTime();
			queue.poll();
			queueLoop_1 = System.nanoTime();
			
			queueLoop_time = queueLoop_1 - queueLoop_0;
			queueLoop_total += queueLoop_time;
			if(queueLoop_time > queueLoop_max) { queueLoop_max = queueLoop_time; }
			if(queueLoop_time < queueLoop_min) { queueLoop_min = queueLoop_time; }
		}
		int size_after = queue.size();
		
		List<DataStructureTimingResult> results = new ArrayList<DataStructureTimingResult>();
		results.add(new DataStructureTimingResult(queue, method, order, "Poll Loop: Min Time", size_before, size_after, queueLoop_min));
		results.add(new DataStructureTimingResult(queue, method, order + 1, "Poll Loop: Max Time", size_before, size_after, queueLoop_max));
		results.add(new DataStructureTimingResult(queue, method, order + 2, "Poll Loop: Avg Time", size_before, size_after, queueLoop_total / size));
		return results;
	}	
}
