package grouping.initialCentroidSelection;

import java.util.Random;

import grouping.distanceCalculator.IDistanceCalculator;
import optimization.solution.ISolution;
import utilities.random.RNGProvider;

public class SpreadOutInitialCentroidSelection<T extends ISolution<T>> implements IInitialCentroidSelection<T>{

	@Override
	public void getInitialCentroids(T[] centroids, T[] elements, int groupCount,
			IDistanceCalculator<T> distanceCalculator) {
		
		Random random = RNGProvider.getRandom();
		centroids[0] = elements[random.nextInt(elements.length)].clone();
		
		for(int i=1;i<centroids.length;i++){
			
			double maxDistance = Double.MIN_VALUE;
			int maxDistanceId = -1;

			for(int k=0; k<elements.length;k++){
				double distance = Double.MAX_VALUE;
				for(int j=0; j<i;j++){
					if(!centroids[j].equals(elements[k])){
						distance = Math.min(distanceCalculator.getDistance(centroids[j], elements[k]),distance);
					}
					else{
						distance = 0;
						break;
					}
				}
				if(maxDistance<distance){
					maxDistance = distance;
					maxDistanceId = k;
				}
			}

			centroids[i] = elements[maxDistanceId].clone();
			
		}
		
		
		
	}

}
