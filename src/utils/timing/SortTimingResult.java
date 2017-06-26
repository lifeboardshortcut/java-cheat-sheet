package utils.timing;

import java.util.Comparator;

import utils.print.PrintUtils;

public class SortTimingResult extends TimingResult{
	public String sort;
	public Integer size;
	
	public SortTimingResult(){
		
	}
	
	public SortTimingResult(String sort, long time, int size, int order, String details)
	{
		this.sort = sort;
		this.size = size;
		this.order = order;
		this.details = details;
		this.time = time;
	}

	public void print() {
		PrintUtils.printSortTime(sort, size, time, details);
	}
	
	public enum SortTRComp{
		SORT_SIZE_INPUT_TIME
		, INPUT_SIZE_SORT_TIME
		, INPUT_SORT_SIZE_TIME
	}
	
	public static Comparator<SortTimingResult> getComparator(SortTRComp comp) {
		if(comp == SortTRComp.SORT_SIZE_INPUT_TIME){
	        return new Comparator<SortTimingResult>() {
	    		public int compare(SortTimingResult result1, SortTimingResult result2) {
	    			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
	    			if(sortCompare != 0) { return sortCompare; }
	    				    			
	    			int sizeCompare = Integer.compare(result1.size, result2.size);
	    			if(sizeCompare != 0) { return sizeCompare; }
	    			
	    			int inputCompare = Integer.compare(result1.order, result2.order);
	    			if(inputCompare != 0) { return inputCompare; }

	    			return Long.compare(result1.time, result2.time);
	    		}
	        };
		} else if(comp == SortTRComp.INPUT_SIZE_SORT_TIME){
	        return new Comparator<SortTimingResult>() {
	    		public int compare(SortTimingResult result1, SortTimingResult result2) {
	    			int inputCompare = Integer.compare(result1.order, result2.order);
	    			if(inputCompare != 0) { return inputCompare; }
	    				    			
	    			int sizeCompare = Integer.compare(result1.size, result2.size);
	    			if(sizeCompare != 0) { return sizeCompare; }
	    			
	    			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
	    			if(sortCompare != 0) { return sortCompare; }
	    			
	    			return Long.compare(result1.time, result2.time);
	    		}
	        };
		} else if(comp == SortTRComp.INPUT_SORT_SIZE_TIME){
	        return new Comparator<SortTimingResult>() {
	    		public int compare(SortTimingResult result1, SortTimingResult result2) {
	    			int inputCompare = Integer.compare(result1.order, result2.order);
	    			if(inputCompare != 0) { return inputCompare; }
	    			
	    			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
	    			if(sortCompare != 0) { return sortCompare; }
	    			
	    			int sizeCompare = Integer.compare(result1.size, result2.size);
	    			if(sizeCompare != 0) { return sizeCompare; }
	    			
	    			return Long.compare(result1.time, result2.time);
	    		}
	        };
		}
		
		return null;
    }
	
	public Integer getComparatorBreakLevel(SortTRComp comp, SortTimingResult compare) {
		SortTimingResult result1 = this;
		SortTimingResult result2 = compare;
		
		if(comp == SortTRComp.SORT_SIZE_INPUT_TIME){
			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
			if(sortCompare != 0) { return 1; }
				    			
			int sizeCompare = Integer.compare(result1.size, result2.size);
			if(sizeCompare != 0) { return 2; }
			
			int inputCompare = Integer.compare(result1.order, result2.order);
			if(inputCompare != 0) { return 3; }

			return 4;
		} else if(comp == SortTRComp.INPUT_SIZE_SORT_TIME){
			int inputCompare = Integer.compare(result1.order, result2.order);
			if(inputCompare != 0) { return 1; }
				    			
			int sizeCompare = Integer.compare(result1.size, result2.size);
			if(sizeCompare != 0) { return 2; }
			
			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
			if(sortCompare != 0) { return 3; }
			
			return 4;
		} else if(comp == SortTRComp.INPUT_SORT_SIZE_TIME){
			int inputCompare = Integer.compare(result1.order, result2.order);
			if(inputCompare != 0) { return 1; }
			
			int sortCompare = result1.sort.compareToIgnoreCase(result2.sort);
			if(sortCompare != 0) { return 2; }
			
			int sizeCompare = Integer.compare(result1.size, result2.size);
			if(sizeCompare != 0) { return 3; }
			
			return 4;
		}
		
		return null;
	}
}
