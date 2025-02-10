package org.mpei.nti;

import org.mpei.nti.genetic.*;
import org.mpei.nti.calculation.economicCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.ResultsMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int minArch = 1;
    public static int maxArch = 3;
    public static int populationSize = 5000;
    public static int numberOfIterations = 10000;

    public static void main(String[] args) throws IOException {
        final long startTime = System.currentTimeMillis();
        List<SubstationMeasures> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(PopulationGeneration.generatePopulation(minArch, maxArch));
        }
        BoundaryIndividualsAdding.addBoundaryAdding(population, minArch, maxArch);
        OptimizeGenotype.genotypeOptimization(population);
        ReliabilityCalculation.goalFunctionCalculation(population);
        Selection.selectionOfSuitableIndividuals(population);

        float previousValueOfTotalPrice = 0.0f;
        int priceIterator = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            if (priceIterator != numberOfIterations / 2) {
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population);
                Mutating.mutatePopulation(newPopulation);
                for (SubstationMeasures substationMeasures : newPopulation) {
                    OptimizeGenotype.architectureOptimization(substationMeasures);
                }
                population.addAll(newPopulation);
                ReliabilityCalculation.goalFunctionCalculation(population);
                Selection.selectionOfSuitableIndividuals(population);
                Sorting.quickSort(population, 0, population.size() - 1);
                Deletion.deletePartOfPopulation(population);

                System.out.println("Номер итерации " + (i + 1));
                System.out.println("Лучший индивид " + population.get(0).hashCode() + " с весовой функцией " +
                        String.format("%f", population.get(0).getTotalPrice()) + " руб");
            } else break;

            if (previousValueOfTotalPrice == population.get(0).getTotalPrice()) {
                priceIterator++;
            } else priceIterator = 0;

            previousValueOfTotalPrice = population.get(0).getTotalPrice();
        }

        ResultsMapping.resultsMapping(population);

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) / 1000 + " sec");
    }
}