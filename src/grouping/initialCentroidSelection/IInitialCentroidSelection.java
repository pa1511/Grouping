package grouping.initialCentroidSelection;

import java.lang.reflect.Array;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import grouping.distanceCalculator.IDistanceCalculator;

public interface IInitialCentroidSelection<T> {
	
	@SuppressWarnings("unchecked")
	public default @Nonnull T[] getInitialCentroids(@Nonnull T[] elements ,@Nonnegative int groupCount, @Nonnull IDistanceCalculator<T> distanceCalculator){
		//TODO: this can lead to problems if T[] has different implementations inside of it
		T[] centroids = (T[]) Array.newInstance(elements[0].getClass(), groupCount);
		getInitialCentroids(centroids, elements, groupCount, distanceCalculator);
		return centroids;
	}

	public void getInitialCentroids(@Nonnull T[] centroids,@Nonnull T[] elements ,@Nonnegative int groupCount, @Nonnull IDistanceCalculator<T> distanceCalculator);

	
}
