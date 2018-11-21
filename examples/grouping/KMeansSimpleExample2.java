package grouping;

import java.awt.Color;
import java.util.Optional;
import java.util.Random;

import grouping.centroidUpdater.GroupingBasedCentroidUpdater;
import grouping.centroidUpdater.ICentroidUpdater;
import grouping.decorator.TimedGrouping;
import grouping.distanceCalculator.EuclidianDistanceCalculator;
import grouping.distanceCalculator.IDistanceCalculator;
import grouping.initialCentroidSelection.IInitialCentroidSelection;
import grouping.initialCentroidSelection.SpreadOutInitialCentroidSelection;
import grouping.iterationGrouping.SimpleDistanceIterationGrouping;
import optimization.solution.DoubleArraySolution;
import optimization.utility.AlgorithmsPresentationUtility;
import ui.graph.SimpleGraph;
import utilities.random.RNGProvider;

public class KMeansSimpleExample2 {
	
	private KMeansSimpleExample2() {}

	public static void main(String[] args) {
		Random random = RNGProvider.getRandom();
		
		int groupCount = 5;
		Color[] groupPresentationColor = new Color[groupCount];
		int r = 255;
		int g = 255;
		int b = 255;
		for(int i=0; i<groupCount;i++){
			groupPresentationColor[i] = new Color(random.nextInt(r), random.nextInt(g), random.nextInt(b));
		}
		
		//Element definition
		int groupPointCount = 10000;
		double[][] rawElements = new double[groupCount*groupPointCount][2];
		
		double max = 3;
		double min = -3;
		double interval = max-min;
		double deltaX = 0.1;
		double deltaY = 0.1;
		
		for(int i=0; i<groupCount; i++){
			
			double centerX = random.nextDouble()*interval+min;
			double centerY = random.nextDouble()*interval+min;
			
			System.out.println("Center X: " + centerX + " Center Y: " + centerY);
			
			for(int j=groupPointCount*i,limit = groupPointCount*(i+1); j<limit; j++){
				rawElements[j][0] = centerX + random.nextGaussian()*deltaX;
				rawElements[j][1] = centerY + random.nextGaussian()*deltaY;
			}
			
		}
		System.out.println();
		
		
		DoubleArraySolution[] elements = new DoubleArraySolution[rawElements.length];
		for(int i=0; i<elements.length;i++){
			elements[i] = new DoubleArraySolution(rawElements[i]);
		}
		
		//Initial Centroid Selection
		IInitialCentroidSelection<DoubleArraySolution> centroidSelection = new SpreadOutInitialCentroidSelection<>();

		//Iteration grouping
		SimpleDistanceIterationGrouping<DoubleArraySolution> iterationGrouping = new SimpleDistanceIterationGrouping<>();
		
		//Centroid updater
		double centroidChangeLimit = 1e-3;
		ICentroidUpdater<DoubleArraySolution> centroidUpdater = new GroupingBasedCentroidUpdater(centroidChangeLimit);
		
		//Distance calculator
		IDistanceCalculator<DoubleArraySolution> distanceCalculator = new EuclidianDistanceCalculator();

		//Grouping algorithm
		TimedGrouping<DoubleArraySolution> grouping = new TimedGrouping<DoubleArraySolution>(
				new KMeansGrouping<DoubleArraySolution>(centroidSelection, 
						iterationGrouping,
						centroidUpdater, distanceCalculator));
		
		//run algorithm
		int[] groups = grouping.group(elements, groupCount);
		
		Optional<DoubleArraySolution[]> optionalCentroids = grouping.getLastGroupingCentroids();
		optionalCentroids.ifPresent(centroids->{
			for(DoubleArraySolution centroid:centroids){
				System.out.println("Centroid: " + centroid);
			}
			
			System.out.println();
		});
		
		//Plot points
		SimpleGraph graph = new SimpleGraph(12, 12);
		for(int i=0; i<elements.length;i++){
//			System.out.println(elements[i] + " grouped to: " + groups[i]);
			graph.addPoint(rawElements[i][0], rawElements[i][1],groupPresentationColor[groups[i]]);
			
		}
		graph.display();
		
		AlgorithmsPresentationUtility.printExecutionTime(grouping.getExecutionTime());

	}
	
}
