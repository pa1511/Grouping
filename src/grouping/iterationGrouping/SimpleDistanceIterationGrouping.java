package grouping.iterationGrouping;

import grouping.distanceCalculator.IDistanceCalculator;

public class SimpleDistanceIterationGrouping<T> implements IIterationGrouping<T>{

	@Override
	public void group(T[] centroids, IDistanceCalculator<T> distanceCalculator,T[] elements, int[] grouping, double[] distances) {
		for(int i=0; i<elements.length;i++){
			distances[i] = Double.MAX_VALUE;
			for(int j=0; j<centroids.length; j++){
				double distance = distanceCalculator.getDistance(elements[i], centroids[j]);
				if(distance<distances[i]){
					distances[i] = distance;
					grouping[i] = j;
				}
			}
		}
	}
	
}
