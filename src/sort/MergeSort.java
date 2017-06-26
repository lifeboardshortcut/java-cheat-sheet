package sort;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {
	public MergeSort(){
		
	}
	
	public void mergeSort(int[] array, int low, int high){
		if(low < high){
			int mid = low + ((high - low) / 2);
			mergeSort(array, low, mid);
			mergeSort(array, mid + 1, high);
			merge(array, low, mid, high);
		}
	}
	
	public void merge(int[] array, int low, int mid, int high){
		int n1 = mid - low + 1;
		int n2 = high - mid;
		
		int[] left = new int[n1 + 1];
		int[] right = new int[n2 + 1];
		System.arraycopy(array, low, left, 0, n1);
		System.arraycopy(array, mid + 1, right, 0, n2);
		left[left.length - 1] = Integer.MAX_VALUE;
		right[right.length - 1] = Integer.MAX_VALUE;
		
		int i = 0, j = 0;
		for(int k = low; k <= high; k++){
			if(left[i] <= right[j]){
				array[k] = left[i];
				i++;
			}
			else{
				array[k] = right[j];
				j++;
			}
		}
	}
	
	public List<Integer> mergeSort(List<Integer> data){
		if(data.size() < 2) return data;
		int partition = data.size() / 2;
		List<Integer> left = data.subList(0, partition);
		List<Integer> right = data.subList(partition, data.size());
		left = mergeSort(left);
		right = mergeSort(right);
		return merge(left, right);
	}
	
	public List<Integer> merge(
			List<Integer> left, List<Integer> right){
		int l = 0, r = 0;
		List<Integer> merged = new ArrayList<Integer>();
		while(l < left.size() && r < right.size()){
			if(left.get(l) < right.get(r)){
				merged.add(left.get(l));
				l++;
			}
			else{
				merged.add(right.get(r));
				r++;
			}
		}
		if(l < left.size()){
			merged.addAll(left.subList(l, left.size()));
		}
		else if(r < right.size()){
			merged.addAll(right.subList(r, right.size()));
		}
		return merged;
	}
}
