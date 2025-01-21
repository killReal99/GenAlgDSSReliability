package org.mpei.nti;

import org.mpei.nti.genetic.*;
import org.mpei.nti.modelCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.ResultsMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int minArch = 1;
    public static int maxArch = 3;
    public static int IEDCount = 2;
    public static int protectionsCount = 3;
    public static int populationSize = 10000;
    public static int numberOfIterations = 10;

    public static void main(String[] args) throws IOException {
        final long startTime = System.currentTimeMillis();
        List<SubstationMeasures> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(PopulationGeneration.generatePopulation(minArch, maxArch, IEDCount, protectionsCount));
        }
        OptimizeGenotype.genotypeOptimization(population);
        ReliabilityCalculation.goalFunctionCalculation(population);

        float previousValueOfTotalPrice = 0.0f;
        int priceIterator = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            if (priceIterator != numberOfIterations / 2) {
                Selection.selectionOfSuitableIndividuals(population);
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population);
                Mutating.mutatePopulation(newPopulation);
                population.addAll(newPopulation);
                ReliabilityCalculation.goalFunctionCalculation(population);
                Sorting.quickSort(population, 0, population.size() - 1);
                Deletion.deletePartOfPopulation(population);

                System.out.println("Iteration number " + i);
                System.out.println("The best individual " + population.get(0).hashCode() + " with total price " +
                        String.format("%f", population.get(0).getTotalPrice()) + " rubles");

            } else break;

            if (Math.round(previousValueOfTotalPrice) == Math.round(population.get(0).getTotalPrice())) {
                priceIterator++;
            } else priceIterator = 0;

            previousValueOfTotalPrice = population.get(0).getTotalPrice();
        }

        ResultsMapping.resultsMapping(population);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) / 1000 + " sec");
    }
}