package grouping.centroidUpdater;

import javax.annotation.Nonnull;

public interface ICentroidUpdater<T> {

	/**
	 * Returns true if a major update to the centroids happened. <br>
	 * If this method returns false it means the grouping is stagnating. <br> 
	 */
	public boolean updateCentroids(
			@Nonnull T[] oldCentroids, @Nonnull T[] newCentroids
			,@Nonnull T[] elements,@Nonnull int[] grouping,@Nonnull double[] distances);
	
}
