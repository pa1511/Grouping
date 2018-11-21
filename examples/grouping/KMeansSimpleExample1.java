package grouping;

import java.awt.Color;
import java.util.Optional;

import grouping.centroidUpdater.GroupingBasedCentroidUpdater;
import grouping.centroidUpdater.ICentroidUpdater;
import grouping.distanceCalculator.EuclidianDistanceCalculator;
import grouping.distanceCalculator.IDistanceCalculator;
import grouping.initialCentroidSelection.IInitialCentroidSelection;
import grouping.initialCentroidSelection.SpreadOutInitialCentroidSelection;
import grouping.iterationGrouping.SimpleDistanceIterationGrouping;
import optimization.solution.DoubleArraySolution;
import ui.graph.SimpleGraph;

public class KMeansSimpleExample1 {
	
	private KMeansSimpleExample1() {}

	public static void main(String[] args) {
		
		int groupCount = 3;

		//Element definition
		double[][] rawElements = new double[][]{
			{0,0},
			{0.1,0},
			{0,0.1},
			{0.1,0.1},
			//
			{5,0},
			{5.1,0},
			{4.9,0.1},
			{5.1,-0.1},
			//
			{0.1,5},
			{-0.1,4.9},
			{0,5.1},
			{0,5}

		};
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
		KMeansGrouping<DoubleArraySolution> grouping = new KMeansGrouping<>(centroidSelection, 
				iterationGrouping,centroidUpdater, distanceCalculator);
		
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
		SimpleGraph graph = new SimpleGraph(8, 8);
		Color[] groupPresentationColor = new Color[]{Color.BLUE,Color.GREEN,Color.YELLOW};
		for(int i=0; i<elements.length;i++){
			System.out.println(elements[i] + " grouped to: " + groups[i]);
			graph.addPoint(rawElements[i][0], rawElements[i][1],groupPresentationColor[groups[i]]);
			
		}
		graph.display();
	}
	
}
