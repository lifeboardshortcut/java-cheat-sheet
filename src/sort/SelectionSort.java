package sort;

import java.util.List;

public class SelectionSort {
	public SelectionSort(){
		
	}
	
	public void selectionSort(List<Integer> data){
		if(data.size() < 2) return;
		for(int i = 0; i < data.size() - 1; i++){
			int min = data.get(i);
			int minIndex = i;
			for(int seeker = i + 1; seeker < data.size(); seeker++){
				if(data.get(seeker) < min){
					minIndex = seeker;
					min = data.get(seeker);
				}
			}
			if(minIndex != i){
				int temp = data.get(i);
				data.set(i, min);
				data.set(minIndex, temp);
			}
		}
	}
	
	public void selectionSort(int[] data){
		if(data.length < 2) return;
		for(int i = 0; i < data.length - 1; i++){
			int min = data[i];
			int minIndex = i;
			for(int seeker = i + 1; seeker < data.length; seeker++){
				if(data[seeker] < min){
					minIndex = seeker;
					min = data[seeker];
				}
			}
			if(minIndex != i){
				int temp = data[i];
				data[i] = min;
				data[minIndex] = temp;
			}
		}
	}
}
