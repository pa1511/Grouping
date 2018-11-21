package grouping;

import java.util.Optional;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface IGrouping<T> {

	public default @Nonnull int[] group(@Nonnull T[] elements, @Nonnegative int groupCount){
		int[] grouping = new int[elements.length];
		group(elements, grouping, groupCount);
		return grouping;
	}
	
	public void group(@Nonnull T[] elements, @Nonnull int[] grouping, @Nonnegative int groupCount);
	
	/**
	 * If the return value is null it means that no grouping has yet been done
	 * @return
	 */
	public Optional<T[]> getLastGroupingCentroids();
}
