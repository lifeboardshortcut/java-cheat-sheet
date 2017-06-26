package utils.timing;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import utils.print.PrintUtils;

public class DataStructureTimingResult extends TimingResult{
	public String javaClass;
	public String method;
	public Integer size_before;
	public Integer size_after;
	
	public DataStructureTimingResult(){
		
	}
	
	public DataStructureTimingResult(Collection<?> collection, String method,
			Integer order, String details, Integer size_before, 
			Integer size_after, Long time)
	{
		this.javaClass = collection.getClass().toString();
		this.method = method;
		this.size_before = size_before;
		this.size_after = size_after;
		this.order = order;
		this.details = details;
		this.time = time;
	}
	
	public DataStructureTimingResult(Map<?, ?> map, String method,
			Integer order, String details, Integer size_before, 
			Integer size_after, Long time)
	{
		this.javaClass = map.getClass().toString();
		this.method = method;
		this.size_before = size_before;
		this.size_after = size_after;
		this.order = order;
		this.details = details;
		this.time = time;

	}

	public void print() {
		PrintUtils.printDataStructureTime(javaClass, method, details, size_before, size_after, time);
	}
	
	public enum DSTRComp{
		CLASS_METHOD_TEST_SIZE_TIME
		, CLASS_METHOD_SIZE_TEST_TIME
		, METHOD_TEST_SIZE_CLASS_TIME
		, METHOD_TEST_CLASS_SIZE_TIME
	}
	
	public static Comparator<DataStructureTimingResult> getComparator(DSTRComp comp) {
		if(comp == DSTRComp.CLASS_METHOD_TEST_SIZE_TIME){
	        return new Comparator<DataStructureTimingResult>() {
	    		public int compare(DataStructureTimingResult result1, DataStructureTimingResult result2) {
	    			int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
	    			if(classCompare != 0) { return classCompare; }
	    			
	    			int methodCompare = result1.method.compareToIgnoreCase(result2.method);
	    			if(methodCompare != 0) { return methodCompare; }
	    			
	    			int testCompare = Integer.compare(result1.order, result2.order);
	    			if(testCompare != 0) { return testCompare; }
	    			
	    			int sizeCompare = Integer.compare(
	    								Math.max(result1.size_before, result1.size_after),
	    								Math.max(result2.size_before, result2.size_after));
	    			if(sizeCompare != 0) { return sizeCompare; }
	    			
	    			return Long.compare(result1.time, result2.time);
	    		}
	        };
		}
		else if(comp == DSTRComp.CLASS_METHOD_SIZE_TEST_TIME){
			return new Comparator<DataStructureTimingResult>() {
				public int compare(DataStructureTimingResult result1, DataStructureTimingResult result2) {
					int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
					if(classCompare != 0) { return classCompare; }
					
					int methodCompare = result1.method.compareToIgnoreCase(result2.method);
					if(methodCompare != 0) { return methodCompare; }
					
					int sizeCompare = Integer.compare(
										Math.max(result1.size_before, result1.size_after),
										Math.max(result2.size_before, result2.size_after));
					if(sizeCompare != 0) { return sizeCompare; }
					
					int testCompare = Integer.compare(result1.order, result2.order);
					if(testCompare != 0) { return testCompare; }
					
					return Long.compare(result1.time, result2.time);
				}
			};
		}
		else if(comp == DSTRComp.METHOD_TEST_SIZE_CLASS_TIME){
			return new Comparator<DataStructureTimingResult>() {
				public int compare(DataStructureTimingResult result1, DataStructureTimingResult result2) {
					int methodCompare = result1.method.compareToIgnoreCase(result2.method);
					if(methodCompare != 0) { return methodCompare; }

					int testCompare = Integer.compare(result1.order, result2.order);
					if(testCompare != 0) { return testCompare; }
					
					int sizeCompare = Integer.compare(
										Math.max(result1.size_before, result1.size_after),
										Math.max(result2.size_before, result2.size_after));
					if(sizeCompare != 0) { return sizeCompare; }					
					
					int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
					if(classCompare != 0) { return classCompare; }
					
					return Long.compare(result1.time, result2.time);
				}
			};
		}
		else if(comp == DSTRComp.METHOD_TEST_CLASS_SIZE_TIME){
			return new Comparator<DataStructureTimingResult>() {
				public int compare(DataStructureTimingResult result1, DataStructureTimingResult result2) {
					int methodCompare = result1.method.compareToIgnoreCase(result2.method);
					if(methodCompare != 0) { return methodCompare; }

					int testCompare = Integer.compare(result1.order, result2.order);
					if(testCompare != 0) { return testCompare; }

					int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
					if(classCompare != 0) { return classCompare; }
					
					int sizeCompare = Integer.compare(
										Math.max(result1.size_before, result1.size_after),
										Math.max(result2.size_before, result2.size_after));
					if(sizeCompare != 0) { return sizeCompare; }					
					
					return Long.compare(result1.time, result2.time);
				}
			};
		}
		
		return null;
    }
	
	public Integer getComparatorBreakLevel(DSTRComp comp, DataStructureTimingResult compare) {
		DataStructureTimingResult result1 = this;
		DataStructureTimingResult result2 = compare;
		
		if(comp == DSTRComp.CLASS_METHOD_TEST_SIZE_TIME){
			int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
			if(classCompare != 0) { return 1; }
			
			int methodCompare = result1.method.compareToIgnoreCase(result2.method);
			if(methodCompare != 0) { return 2; }
			
			int testCompare = result1.details.compareToIgnoreCase(result2.details);
			if(testCompare != 0) { return 3; }
			
			int sizeCompare = Integer.compare(
								Math.max(result1.size_before, result1.size_after),
								Math.max(result2.size_before, result2.size_after));
			if(sizeCompare != 0) { return 4; }
			
			return 5;
		}
		else if(comp == DSTRComp.CLASS_METHOD_SIZE_TEST_TIME){
			int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
			if(classCompare != 0) { return 1; }
			
			int methodCompare = result1.method.compareToIgnoreCase(result2.method);
			if(methodCompare != 0) { return 2; }
			
			int sizeCompare = Integer.compare(
								Math.max(result1.size_before, result1.size_after),
								Math.max(result2.size_before, result2.size_after));
			if(sizeCompare != 0) { return 3; }
			
			int testCompare = result1.details.compareToIgnoreCase(result2.details);
			if(testCompare != 0) { return 4; }
			
			return 5;
		}
		else if(comp == DSTRComp.METHOD_TEST_SIZE_CLASS_TIME){
			int methodCompare = result1.method.compareToIgnoreCase(result2.method);
			if(methodCompare != 0) { return 1; }
	
			int testCompare = result1.details.compareToIgnoreCase(result2.details);
			if(testCompare != 0) { return 2; }
			
			int sizeCompare = Integer.compare(
								Math.max(result1.size_before, result1.size_after),
								Math.max(result2.size_before, result2.size_after));
			if(sizeCompare != 0) { return 3; }					
			
			int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
			if(classCompare != 0) { return 4; }
			
			return 5;
		}
		else if(comp == DSTRComp.METHOD_TEST_CLASS_SIZE_TIME){
			int methodCompare = result1.method.compareToIgnoreCase(result2.method);
			if(methodCompare != 0) { return 1; }

			int testCompare = Integer.compare(result1.order, result2.order);
			if(testCompare != 0) { return 2; }

			int classCompare = result1.javaClass.compareToIgnoreCase(result2.javaClass);
			if(classCompare != 0) { return 3; }
			
			int sizeCompare = Integer.compare(
								Math.max(result1.size_before, result1.size_after),
								Math.max(result2.size_before, result2.size_after));
			if(sizeCompare != 0) { return 4; }					
			
			return 5;
		}
		
		return null;
    }
}
