package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph<T> {
	private List<GraphNode<T>> nodes;
	
	public Graph(){
		this.nodes = new ArrayList<GraphNode<T>>();
	}
	
	public Graph(List<GraphNode<T>> nodes){
		this.nodes = new ArrayList<GraphNode<T>>(nodes);
	}

	public List<GraphNode<T>> getNodes() {
		return nodes;
	}

	public void setNodes(List<GraphNode<T>> nodes) {
		this.nodes = nodes;
	}
	
	public void setAllUnmarked(){
		for(GraphNode<?> node : nodes){
			node.setMarked(false);
		}
	}
	
	public void breadthFirstTraversal(GraphNode<?> root){
		Queue<GraphNode<?>> queue = new LinkedList<GraphNode<?>>();
		root.setMarked(true);
		queue.offer(root);
		
		while(!queue.isEmpty()){
			GraphNode<?> next = queue.poll();
			System.out.println(next.getValue()); // Visit
			for(GraphNode<?> neighbor : next.getNeighbors()){
				if(!neighbor.isMarked()){
					neighbor.setMarked(true);
					queue.offer(neighbor);
				}
			}
		}
	}
	
	public void breadthFirstTraversal(GraphNode<?> root, List<T> list){
		Queue<GraphNode<?>> queue = new LinkedList<GraphNode<?>>();
		root.setMarked(true);
		queue.offer(root);
		
		while(!queue.isEmpty()){
			GraphNode<?> next = queue.poll();
			list.add((T) next.getValue());
			for(GraphNode<?> neighbor : next.getNeighbors()){
				if(!neighbor.isMarked()){
					neighbor.setMarked(true);
					queue.offer(neighbor);
				}
			}
		}
	}
	
	public void depthFirstTraversal(GraphNode<?> root){
		if(root == null) return;
		System.out.println(root.getValue()); // Visit
		root.setMarked(true);
		for(GraphNode<?> neighbor : root.getNeighbors()){
			if(!neighbor.isMarked()){
				depthFirstTraversal(neighbor);
			}
		}
	}
	
	public void depthFirstTraversal(GraphNode<?> root, List<T> list){
		if(root == null) return;
		list.add((T) root.getValue());
		root.setMarked(true);
		for(GraphNode<?> neighbor : root.getNeighbors()){
			if(!neighbor.isMarked()){
				depthFirstTraversal(neighbor, list);
			}
		}
	}
	
	public GraphNode<?> breadthFirstSearch(GraphNode<?> root, T key){
		Queue<GraphNode<?>> queue = new LinkedList<GraphNode<?>>();
		root.setMarked(true);
		queue.offer(root);
		
		while(!queue.isEmpty()){
			GraphNode<?> next = queue.poll();
			if(key.equals(next.getValue())){
				return next;
			}
			for(GraphNode<?> neighbor : next.getNeighbors()){
				if(!neighbor.isMarked()){
					neighbor.setMarked(true);
					queue.offer(neighbor);
				}
			}
		}
		
		return null;
	}
	
	public GraphNode<?> depthFirstSearch(GraphNode<?> root, T key){
		if(root == null) return null;
		if(key.equals(root.getValue())){
			return root;
		}
		root.setMarked(true);
		GraphNode<?> result = null;
		for(GraphNode<?> neighbor : root.getNeighbors()){
			if(!neighbor.isMarked()){
				result = depthFirstSearch(neighbor, key);
				if(result != null){
					return result; 
				}
			}
		}
		return null;
	}
}
