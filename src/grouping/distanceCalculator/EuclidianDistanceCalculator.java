package grouping.distanceCalculator;

import javax.annotation.Nonnull;

import optimization.solution.DoubleArraySolution;

public class EuclidianDistanceCalculator implements IDistanceCalculator<DoubleArraySolution>{

	@Override
	public double getDistance(@Nonnull DoubleArraySolution X,@Nonnull DoubleArraySolution Y) {
		
		double distance = 0;
		
		for(int i=0; i<X.values.length;i++){
			double d = X.values[i]-Y.values[i];
			//TODO
			distance+=d*d;//Math.abs(d);
		}
		
		return distance;
	}

}
