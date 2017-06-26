package search;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
	private T value;
	private List<GraphNode<T>> neighbors;
	private boolean marked;
	
	public GraphNode(){
		this.neighbors = new ArrayList<GraphNode<T>>();
		this.marked = false;
	}
	
	public GraphNode(T value){
		this.value = value;
		this.neighbors = new ArrayList<GraphNode<T>>();
		this.marked = false;
	}
	
	public GraphNode(T value, List<GraphNode<T>> neighbors){
		this.value = value;
		this.neighbors = new ArrayList<GraphNode<T>>(neighbors);
		this.marked = false;
	}

	public List<GraphNode<T>> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<GraphNode<T>> neighbors) {
		this.neighbors = neighbors;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	public void addNeighbor(GraphNode<T> neighbor){
		neighbors.add(neighbor);
	}
}
