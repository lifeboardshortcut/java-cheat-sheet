package collections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;


public class TestCollectionOperations {
	Collection<Integer> list;
	Collection<Integer> zeros;
	
	@Before
	public void setUp() {
		list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		zeros = new ArrayList<Integer>(Collections.nCopies(10, 0));
	}
	
	@Test
	public void testCollectionSort(){
		List<Integer> sortList = new ArrayList<Integer>();
		sortList.add(5);
		sortList.add(3);
		sortList.add(2);
		sortList.add(4);
		sortList.add(1);
		
		Collections.sort(sortList);
		
		assertEquals(Arrays.asList(1, 2, 3, 4, 5), sortList);
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(3.1));
		students.add(new Student(3.9));
		students.add(new Student(1.5));
		students.add(new Student(2.7));
		students.add(new Student(3.2));
		
		Collections.sort(students, Student.GPAAscending);
		
		List<Double> gpas = students.stream().map(s -> s.GPA).collect(Collectors.toList());
		assertEquals(gpas, Arrays.asList(1.5, 2.7, 3.1, 3.2, 3.9));
	}

	@Test
	public void testListToSet(){
		Set<Integer> zerosSet = new HashSet<Integer>(zeros);
		Set<Integer> set = new HashSet<Integer>(list);
		
		assertEquals(zerosSet.size(), 1);
		assertEquals(set.size(), list.size());
	}
	
	@Test
	public void testSetToList(){
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(-5);
		set.add(10);
		set.add(15);
		set.add(20);
		set.add(-20);
		
		// Order of insertion not preserved
		List<Integer> list = new ArrayList<Integer>(set);
		assertEquals(list.size(), set.size());
	}
	
	@Test
	public void testCollectionToArray(){
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.add(4);
		collection.add(5);
		
		int size = collection.size();
		Iterator<Integer> iterator = collection.iterator();
		Object[] array = collection.toArray();
		assertArrayEquals(array, new Integer[]{1, 2, 3, 4, 5});
		collection.clear();
		boolean isEmpty = collection.isEmpty();
		assertTrue(isEmpty);
	}
	
	@Test
	public void testShuffle(){
		List<Integer> shuffled = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		Collections.shuffle(shuffled); // In place
		
		assertFalse(shuffled.equals(list)); // Actually a 1 in 3 million chance
		 									// chance that we get the same sorted
											// list and this fails
	}
}
