package grouping;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import grouping.centroidUpdater.ICentroidUpdater;
import grouping.distanceCalculator.IDistanceCalculator;
import grouping.initialCentroidSelection.IInitialCentroidSelection;
import grouping.iterationGrouping.IIterationGrouping;
import optimization.solution.ISolution;

public class KMeansGrouping<T extends ISolution<T>> implements IGrouping<T>{

	private final @Nonnull IInitialCentroidSelection<T> centroidSelection;
	private final @Nonnull IIterationGrouping<T> iterationGrouping;
	private final @Nonnull IDistanceCalculator<T> distanceCalculator;
	private final @Nonnull ICentroidUpdater<T> centroidUpdater;
	private @CheckForNull T[] centroids;

	public KMeansGrouping(@Nonnull IInitialCentroidSelection<T> centroidSelection,
			@Nonnull IIterationGrouping<T> iterationGrouping,
			@Nonnull ICentroidUpdater<T> centroidUpdater,
			@Nonnull IDistanceCalculator<T> distanceCalculator) {
		this.centroidSelection = centroidSelection;
		this.iterationGrouping = iterationGrouping;
		this.centroidUpdater = centroidUpdater;
		this.distanceCalculator = distanceCalculator;
	}
	
	
	@Override
	public void group(@Nonnull T[] elements,@Nonnull int[] grouping,@Nonnegative int groupCount) {
		T[] centroids1 = centroidSelection.getInitialCentroids(elements, groupCount, distanceCalculator);
		T[] centroids2 = Arrays.copyOf(centroids1, centroids1.length);
		for(int i=0; i<centroids2.length; i++){
			centroids2[i] = centroids2[i].clone();
		}
		
		double[] distances = new double[elements.length];
		boolean isMajorChange;
		
		do{

			iterationGrouping.group(centroids1, distanceCalculator, elements, grouping, distances);

			isMajorChange = centroidUpdater.updateCentroids(centroids1, centroids2, elements, grouping, distances);
			
			T[] switchHelp = centroids1;
			centroids1 = centroids2;
			centroids2 = switchHelp;
			
			centroids = centroids1;

		}while(isMajorChange);
	}
	
	@Override
	public @Nonnull Optional<T[]> getLastGroupingCentroids() {
		return Optional.ofNullable(centroids);
	}

}
