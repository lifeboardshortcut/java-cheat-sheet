package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestGraphSearch {
	GraphNode<Integer> node0, node1, node2, node3,
		node4, node5, node6, node7, node8, node9;
	Graph<Integer> graph;
	
	/*
	 *       3 -> 5 -> 8 
	 *       |    |     
	 *       V    V
	 *       9 -> 0 -> 4 <-> 6
	 *       |    |
	 *       V    V
	 *       2    1 -> 7
	 */
	@Before
	public void setUp(){
		node0 = new GraphNode<Integer>(0);
		node1 = new GraphNode<Integer>(1);
		node2 = new GraphNode<Integer>(2);
		node3 = new GraphNode<Integer>(3);
		node4 = new GraphNode<Integer>(4);
		node5 = new GraphNode<Integer>(5);
		node6 = new GraphNode<Integer>(6);
		node7 = new GraphNode<Integer>(7);
		node8 = new GraphNode<Integer>(8);
		node9 = new GraphNode<Integer>(9);
		
		node3.addNeighbor(node5);
		node3.addNeighbor(node9);
		node5.addNeighbor(node8);
		node5.addNeighbor(node0);
		node9.addNeighbor(node2);
		node9.addNeighbor(node0);
		node0.addNeighbor(node1);
		node0.addNeighbor(node4);
		node1.addNeighbor(node7);
		node4.addNeighbor(node6);
		node6.addNeighbor(node4);
		
		List<GraphNode<Integer>> graphNodes = new ArrayList<GraphNode<Integer>>();
		graphNodes.add(node0);
		graphNodes.add(node1);
		graphNodes.add(node2);
		graphNodes.add(node3);
		graphNodes.add(node4);
		graphNodes.add(node5);
		graphNodes.add(node6);
		graphNodes.add(node7);
		graphNodes.add(node8);
		graphNodes.add(node9);
		
		graph = new Graph<Integer>(graphNodes);
	}
	
	@Test
	public void testDepthFirstSearch(){
		GraphNode<Integer> root = node3;
		GraphNode<Integer> n0 = (GraphNode<Integer>) graph.depthFirstSearch(root, 0);
		graph.setAllUnmarked();
		GraphNode<Integer> n1 = (GraphNode<Integer>) graph.depthFirstSearch(root, 1);
		graph.setAllUnmarked();
		GraphNode<Integer> n2 = (GraphNode<Integer>) graph.depthFirstSearch(root, 2);
		graph.setAllUnmarked();
		GraphNode<Integer> n3 = (GraphNode<Integer>) graph.depthFirstSearch(root, 3);
		graph.setAllUnmarked();
		GraphNode<Integer> n4 = (GraphNode<Integer>) graph.depthFirstSearch(root, 4);
		graph.setAllUnmarked();
		GraphNode<Integer> n5 = (GraphNode<Integer>) graph.depthFirstSearch(root, 5);
		graph.setAllUnmarked();
		GraphNode<Integer> n6 = (GraphNode<Integer>) graph.depthFirstSearch(root, 6);
		graph.setAllUnmarked();
		GraphNode<Integer> n7 = (GraphNode<Integer>) graph.depthFirstSearch(root, 7);
		graph.setAllUnmarked();
		GraphNode<Integer> n8 = (GraphNode<Integer>) graph.depthFirstSearch(root, 8);
		graph.setAllUnmarked();
		GraphNode<Integer> n9 = (GraphNode<Integer>) graph.depthFirstSearch(root, 9);
		graph.setAllUnmarked();
		
		assertTrue(n0.equals(node0));
		assertTrue(n1.equals(node1));
		assertTrue(n2.equals(node2));
		assertTrue(n3.equals(node3));
		assertTrue(n4.equals(node4));
		assertTrue(n5.equals(node5));
		assertTrue(n6.equals(node6));
		assertTrue(n7.equals(node7));
		assertTrue(n8.equals(node8));
		assertTrue(n9.equals(node9));
	}
	
	@Test
	public void testDepthFirstTraversal(){
		GraphNode<Integer> root = node3;
		List<Integer> list = new ArrayList<Integer>();
		graph.depthFirstTraversal(root, list);

		assertEquals(list, Arrays.asList(3, 5, 8, 0, 1, 7, 4, 6, 9, 2));
	}
	
	@Test
	public void testBreadthFirstTraversal(){
		GraphNode<Integer> root = node3;
		List<Integer> list = new ArrayList<Integer>();
		graph.breadthFirstTraversal(root, list);

		assertEquals(list, Arrays.asList(3, 5, 9, 8, 0, 2, 1, 4, 7, 6));
	}
	
	@Test
	public void testBreadthFirstSearch(){
		GraphNode<Integer> root = node3;
		GraphNode<Integer> n0 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 0);
		graph.setAllUnmarked();
		GraphNode<Integer> n1 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 1);
		graph.setAllUnmarked();
		GraphNode<Integer> n2 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 2);
		graph.setAllUnmarked();
		GraphNode<Integer> n3 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 3);
		graph.setAllUnmarked();
		GraphNode<Integer> n4 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 4);
		graph.setAllUnmarked();
		GraphNode<Integer> n5 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 5);
		graph.setAllUnmarked();
		GraphNode<Integer> n6 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 6);
		graph.setAllUnmarked();
		GraphNode<Integer> n7 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 7);
		graph.setAllUnmarked();
		GraphNode<Integer> n8 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 8);
		graph.setAllUnmarked();
		GraphNode<Integer> n9 = (GraphNode<Integer>) graph.breadthFirstSearch(root, 9);
		graph.setAllUnmarked();
		
		assertTrue(n0.equals(node0));
		assertTrue(n1.equals(node1));
		assertTrue(n2.equals(node2));
		assertTrue(n3.equals(node3));
		assertTrue(n4.equals(node4));
		assertTrue(n5.equals(node5));
		assertTrue(n6.equals(node6));
		assertTrue(n7.equals(node7));
		assertTrue(n8.equals(node8));
		assertTrue(n9.equals(node9));
	}

}
