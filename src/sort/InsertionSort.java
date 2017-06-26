package sort;

import java.util.List;

public class InsertionSort {
	public InsertionSort(){
		
	}

	public void insertionSort(int[] data){
		if(data.length < 2) return;
		for(int i = 1; i < data.length; i++){
			int val = data[i];
			int lookLeft = i - 1;
			while(lookLeft >= 0 && data[lookLeft] > val){
				int temp = data[lookLeft];
				data[lookLeft] = val;
				data[lookLeft + 1] = temp;
				lookLeft--;
			}
		}
	}
	
	public void insertionSort(List<Integer> data){
		if(data.size() < 2) return;
		for(int i = 1; i < data.size(); i++){
			int val = data.get(i);
			int lookLeft = i - 1;
			while(lookLeft >= 0 && data.get(lookLeft) > val){
				int temp = data.get(lookLeft);
				data.set(lookLeft, val);
				data.set(lookLeft + 1, temp);
				lookLeft--;
			}
		}
	}
}
