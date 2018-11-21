package grouping.distanceCalculator;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface IDistanceCalculator<T> {

	public @Nonnegative double getDistance(@Nonnull T X,@Nonnull T Y);
	
}
