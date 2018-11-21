package grouping.decorator;

import java.util.Optional;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import grouping.IGrouping;

public class TimedGrouping<T> implements IGrouping<T>{

	private final @Nonnull IGrouping<T> groupingStrategy;
	private @Nonnegative long executionTime;
	
	public TimedGrouping(IGrouping<T> grouping) {
		this.groupingStrategy = grouping;
	}
	
	@Override
	public void group(T[] elements, int[] grouping, int groupCount) {
		long start = System.nanoTime();
		this.groupingStrategy.group(elements, grouping, groupCount);
		long end = System.nanoTime();
		executionTime = end-start;
	}
	
	@Override
	public int[] group(T[] elements, int groupCount) {
		
		long start = System.nanoTime();
		int[] grouping = groupingStrategy.group(elements, groupCount);
		long end = System.nanoTime();
		executionTime = end-start;
		return grouping;
	}
	
	@Override
	public Optional<T[]> getLastGroupingCentroids() {
		return groupingStrategy.getLastGroupingCentroids();
	}
	
	/**
	 * Returns the execution time of the last algorithm execution in ns. <br>
	 * If it was never executed the method will return 0. <br>
	 */
	public @Nonnegative long getExecutionTime() {
		return executionTime;
	}

}
