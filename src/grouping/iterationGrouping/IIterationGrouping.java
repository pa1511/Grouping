package grouping.iterationGrouping;

import grouping.distanceCalculator.IDistanceCalculator;

public interface IIterationGrouping<T> {

	void group(T[] centroids, IDistanceCalculator<T> distanceCalculator, T[] elements, int[] grouping,
			double[] distances);

}