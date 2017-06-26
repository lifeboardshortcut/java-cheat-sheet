package binarySearchTree;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestBinarySearchTree {
	BST<Integer> bst = new BST<Integer>();
	
	/*
	 *            8
	 *      4           8
	 *    2   5      10   12
	 *  1                    15
	 */
	
	@Test
	public void testBST(){
		// Test insert
		bst.insert(8);
		bst.insert(4);
		bst.insert(5);
		bst.insert(8);
		bst.insert(12);
		bst.insert(15);
		bst.insert(10);
		bst.insert(2);
		bst.insert(1);
		
		// Test size
		//assertTrue(bst.size() == 9);
		assertTrue(bst.height() == 3);
		
		// Test Access
		
		// Test Search
		
		// Test Delete
		
		// Test Traverse
		List<Integer> inOrder = bst.traverseInOrder();
		assertEquals(inOrder, Arrays.asList(1, 2, 4, 5, 8, 8, 10, 12, 15));
	}
}
