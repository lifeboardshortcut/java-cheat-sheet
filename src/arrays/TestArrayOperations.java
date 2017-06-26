package arrays;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class TestArrayOperations {

	@Test
	public void testDeclaration() {
		int[] a = {1, 2, 3, 4}, b = new int[]{5, 6, 7};
		int[] zeros = new int[4]; // {0, 0, 0, 0}
		Integer[] nulls = new Integer[4]; // {null, null, null, null}
		int[][] multiDim = new int[][]{a, b, zeros}; // {{1, 2, 3, 4}, {5, 6, 7}, {0, 0, 0, 0}}
		
		assertArrayEquals(a, new int[]{1, 2, 3, 4});
		assertArrayEquals(b, new int[]{5, 6, 7});
		assertArrayEquals(zeros, new int[]{0, 0, 0, 0});
		assertArrayEquals(nulls, new Integer[]{null, null, null, null});
		assertArrayEquals(multiDim, new int[][]{{1, 2, 3, 4}, {5, 6, 7}, {0, 0, 0, 0}});
	}
	
	@Test
	public void testAssignment() {
		int[] a = {1, 2, 3, 4};
		int[] d = a; // Points at same object as 'a'
		// d == a evaluates to true

		assertTrue(a == d);
		assertArrayEquals(a, d);
		
		a[0] = 100;
		// d[0] == 100 evaluates to true
		assertTrue(d[0] == 100);
	}
	
	@Test
	public void testToString() {
		int[] a = {1, 2, 3, 4};
		String toString = a.toString();
		String arrayToString = Arrays.toString(a); // "[1, 2, 3, 4]"
		
		assertNotEquals(toString, "[1, 2, 3, 4]");
		assertEquals(arrayToString, "[1, 2, 3, 4]");
	}
	
	@Test
	public void testLength() {
		// Populated
		int[] a = {1, 2, 3, 4};
		int l = a.length;
		
		assertEquals(l, 4);
		
		// Unpopulated
		int[] b = new int[10];
		l = b.length;
		assertEquals(l, 10);
	}
	
	@Test
	public void testSearch() {
		int[] a = {1, 2, 3, 4};
		boolean contains = IntStream.of(a).anyMatch(x -> x == 2);
		assertTrue(contains);
		
		boolean containsFalse = IntStream.of(a).anyMatch(x -> x == 10);
		assertFalse(containsFalse);
		
		int[] b = new int[10];
		boolean containsZero = IntStream.of(a).anyMatch(x -> x == 0); 
		assertTrue(b[0] == 0);
		assertFalse(containsZero); // Because it's a stream?
	}
	
	@Test
	public void testConcatenate(){
		int[] a = {1, 2, 3, 4};
		int[] b = {5, 6, 7, 8};

		int[] combined = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).toArray();
		assertArrayEquals(combined, new int[]{1, 2, 3, 4, 5, 6, 7, 8});
	}
	
	@Test
	public void testJoin(){
		int[] a = {1, 2, 3, 4};
		String join = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining("-")); // "1-2-3-4"
		
		assertEquals(join, "1-2-3-4");
	}
	
	@Test 
	public void testPrimitiveCopy(){
		int[] src = {1, 2, 3, 4};
		int[] dest = {5, 6, 7, 8};
		
		System.arraycopy(src, 0, dest, 0, src.length); // In place
		int[] copy = Arrays.copyOf(src, src.length); // New array
		
		assertArrayEquals(src, new int[]{1, 2, 3, 4});
		assertArrayEquals(dest, src);
		assertArrayEquals(copy, src);
	}
	
	@Test 
	public void testCopyOfRange(){
		int[] a = {1, 2, 3, 4};
		int inclusive = 1;
		int exclusive = a.length;		
		int[] sub = Arrays.copyOfRange(a, inclusive, exclusive);
		
		assertArrayEquals(sub, new int[]{2, 3, 4});
	}
	
	@Test 
	public void testShallowCopy(){
		Point[] src = {new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4)};
		Point[] dest = {new Point(5, 5), new Point(6, 6), new Point(7, 7), new Point(8, 8)};
		
		System.arraycopy(src, 0, dest, 0, src.length); // In place
		Object[] copy = Arrays.copyOf(src, src.length); // New array
		
		assertArrayEquals(src, dest);
		assertArrayEquals(src, copy);
		
		src[0].x = 100;
		src[0].y = 100;
		
		assertArrayEquals(src, dest);
		assertArrayEquals(src, copy);
	}
	
	
	@Test 
	public void testFill(){
		int[] a = {1, 2, 3, 4};
		
		Arrays.fill(a, 7); // {7, 7, 7, 7}
		
		assertArrayEquals(a, new int[]{7, 7, 7, 7});
	}
	
	@Test 
	public void testToList(){
		Integer[] boxed = {1, 2, 3, 4};
		List<Integer> list = Arrays.asList(boxed); // Not for primitives
		
		assertEquals(list, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
	}
	
	@Test 
	public void testToSet(){
		int[] a = {1, 2, 3, 4};
		Set<Integer> set = Arrays.stream(a).boxed().collect(Collectors.toSet());
		
		assertEquals(set, new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)));
	}
	
	@Test
	public void indexOf(){
		Integer[] a = {1, 2, 3, 4};
		int idx = Arrays.asList(a).indexOf(3); // 2
		int idxBS = Arrays.binarySearch(a, 3); // 2
		
		assertEquals(idx, 2);
		assertEquals(idxBS, 2);
	}
	
	public class Point {
	    public int x = 0;
	    public int y = 0;
	    //constructor
	    public Point(int a, int b) {
	        x = a;
	        y = b;
	    }
	}
}
