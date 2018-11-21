package grouping.centroidUpdater;

import javax.annotation.Nonnull;

import optimization.solution.DoubleArraySolution;
import util.VectorUtils;

public class GroupingBasedCentroidUpdater implements ICentroidUpdater<DoubleArraySolution>{

	private final double centroidChangeLimit;

	/**
	 * When the centroid change is below the given limit the updater will signal there was not major change. <br>
	 * @param centroidChangeLimit
	 */
	public GroupingBasedCentroidUpdater(double centroidChangeLimit) {
		this.centroidChangeLimit = centroidChangeLimit;
	}
	
	
	@Override
	public boolean updateCentroids(@Nonnull DoubleArraySolution[] oldCentroids, @Nonnull DoubleArraySolution[] newCentroids,
			@Nonnull DoubleArraySolution[] elements, @Nonnull int[] grouping,
			@Nonnull double[] distances) {
		
		double totalChange = 0;
		
		for(int i=0; i<oldCentroids.length; i++){
			
			double[] oldCentroidValues = oldCentroids[i].values;
			double[] newCentroidValues = newCentroids[i].values;
			int groupSize = 0;
			for(int j=0; j<elements.length;j++){
				if(grouping[j]==i){
					VectorUtils.add(newCentroidValues, elements[j].values, newCentroidValues, false);
					groupSize++;
				}
			}
			VectorUtils.multiplyByScalar(newCentroidValues, 1.0/groupSize, newCentroidValues, false);

			for(int j=0; j<newCentroidValues.length;j++){
				double change = oldCentroidValues[j]-newCentroidValues[j];
				totalChange += change*change;
			}
			
		}
		
		return totalChange>centroidChangeLimit;
	}

}
