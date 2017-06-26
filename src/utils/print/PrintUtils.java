package utils.print;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import utils.timing.DataStructureTimingResult;
import utils.timing.SortTimingResult;

public class PrintUtils {
	public static void printDataStructureTime(String implementation, String method, String notes, int size_after, long time){
		System.out.println(String.format("%-25s", implementation.substring(6)) 
							+ String.format("%-15s", method)
							+ String.format("%-10d", size_after)
							+ String.format("%-12s", NumberFormat.getNumberInstance(Locale.US).format(time))
							+ notes);
	}
	
	public static void printDataStructureTime(String implementation, String method, String notes, int size_before, int size_after, long time){
		String[] splitImplementation = implementation.split("\\.");
		if(splitImplementation.length > 0){
			implementation = splitImplementation[splitImplementation.length - 1];
		}
		System.out.println(String.format("%-14s", implementation) 
							+ String.format("%-17s", method)
							+ String.format("%-10d", size_before)
							+ String.format("%-10d", size_after)
							+ String.format("%-12s", NumberFormat.getNumberInstance(Locale.US).format(time))
							+ notes);
	}
	
	public static void printDataStructureHeader(){
		System.out.println(String.format("%-14s", "Class") 
							+ String.format("%-17s", "Method")
							+ String.format("%-10s", "# Before")
							+ String.format("%-10s", "# After")
							+ String.format("%-12s", "Time")
							+ "Notes");
	}
	
	public static void printLine(){
		System.out.println("");
	}
	
	public static void printDivider(){
		System.out.println(String.join("", Collections.nCopies(80, "-")));
	}

	public static void printDataStructureResults(List<DataStructureTimingResult> timingResults, 
			DataStructureTimingResult.DSTRComp comparator, int breakOnSection) {
		Collections.sort(timingResults, DataStructureTimingResult.getComparator(comparator));
		if(timingResults.size() > 0) { printDataStructureHeader(); }
		
		DataStructureTimingResult prevResult = null;
		for(DataStructureTimingResult timingResult: timingResults){
			if(prevResult != null){
				if(timingResult.
						getComparatorBreakLevel(comparator, prevResult) <= breakOnSection)
				{
					printLine();
					printDataStructureHeader();
				}
			}
			timingResult.print();
			prevResult = timingResult;
		}
	}
	
	public static void printSortTime(String sort, int size, long time, String details){
		System.out.println(String.format("%-15s", sort) 
							+ String.format("%-10d", size)
							+ String.format("%-12s", NumberFormat.getNumberInstance(Locale.US).format(time))
							+ details);
	}
	
	public static void printSortHeader(){
		System.out.println(String.format("%-15s", "Sort")
							+ String.format("%-10s", "Size")
							+ String.format("%-12s", "Time")
							+ "Notes");
	}
	
	public static void printSortResults(List<SortTimingResult> timingResults, 
			SortTimingResult.SortTRComp comparator, int breakOnSection) {
		Collections.sort(timingResults, SortTimingResult.getComparator(comparator));
		if(timingResults.size() > 0) { printSortHeader(); }
		
		SortTimingResult prevResult = null;
		for(SortTimingResult timingResult: timingResults){
			if(prevResult != null){
				if(timingResult.
						getComparatorBreakLevel(comparator, prevResult) <= breakOnSection)
				{
					printLine();
					printSortHeader();
				}
			}
			timingResult.print();
			prevResult = timingResult;
		}
	}
}
