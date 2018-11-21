package grouping.initialCentroidSelection;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import grouping.distanceCalculator.IDistanceCalculator;
import optimization.solution.ISolution;
import utilities.random.RNGProvider;

public class RandomCentroidSelection<T extends ISolution<T>> implements IInitialCentroidSelection<T>{

	@Override
	public void getInitialCentroids(@Nonnull T[] centroids,@Nonnull  T[] elements, @Nonnegative int groupCount,
			@Nonnull IDistanceCalculator<T> distanceCalculator) {
		
		Random random = RNGProvider.getRandom();
		Set<T> selected = new HashSet<>();
		for(int i=0; i<centroids.length;i++){
			int id = random.nextInt(elements.length);
			if(!selected.contains(elements[id])){
				centroids[i] = elements[id].clone();
			}
		}
		
	}

}
