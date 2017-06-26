package sort;

import java.util.List;

public class QuickSort {
	public QuickSort(){
		
	}
	
	public void quickSort(int[] array, int low, int high){
		if (low < high) {
			int partition = partition(array, low, high);
			quickSort(array, low, partition - 1); // left
			quickSort(array, partition + 1, high); // right
		}
	}
	
	public int partition(
			int[] array, int low, int high){
		int pivot = array[high];
		int partition = low;
		for(int i = low; i < high; i++){
			if(array[i] <= pivot){
				if(i != partition){
					swap(array, i, partition);
				}
				partition++;
			}
		}
		swap(array, partition, high);
		return partition;
	}
	
	public void swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public void quickSort(
			List<Integer> data, int low, int high){
		if(low < high){
			int partition = partition(data, low, high);
			quickSort(data, low, partition - 1); // left
			quickSort(data, partition + 1, high); // right
		}
	}
	
	public int partition(
			List<Integer> data, int low, int high){
		int pivot = data.get(high);
		int partition = low;
		for(int i = low; i < high; i++){
			if(data.get(i) <= pivot){
				int temp = data.get(i);
				data.set(i, data.get(partition));
				data.set(partition, temp);
				partition++;
			}
		}
		int temp = data.get(high);
		data.set(high, data.get(partition));
		data.set(partition, temp);
		return partition;
	}
}
