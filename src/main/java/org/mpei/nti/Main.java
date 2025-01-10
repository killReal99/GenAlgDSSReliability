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
    public static int protectionsCount = 3;
    public static int populationSize = 5000;
    public static int numberOfIterations = 100;

    public static void main(String[] args) throws IOException {
        final long startTime = System.currentTimeMillis();
        List<SubstationMeasures> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(PopulationGeneration.generatePopulation(minArch, protectionsCount));
        }

        float previousValueOfTotalPrice = 0.0f;
        int priceIterator = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            if (priceIterator != numberOfIterations / 2) {
                Selection.selectionOfSuitableIndividuals(population);
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population);
                Mutating.mutatePopulation(newPopulation);
                population.addAll(newPopulation);
                ReliabilityCalculation.economicDamageCalculation(population);
                Sorting.bubbleSort(population);
                Deletion.deletePartOfPopulation(population);

                System.out.println("Iteration number " + i);
                System.out.println("Population size " + population.size());
                System.out.println("The best individual " + population.get(0).hashCode() + " with total price " +
                    String.format("%f", population.get(0).getTotalPrice()) + " rubles");

            } else break;

            if (Math.round(previousValueOfTotalPrice) == Math.round(population.get(0).getTotalPrice())) {
                priceIterator++;
            } else priceIterator = 0;

            previousValueOfTotalPrice = population.get(0).getTotalPrice();
        }

        ResultsMapping.resultsMapping(population);

//            for (int j = 0; j < theNextGenerationPopulation; j++) {
//                double capex = ReliabilityCalculation.capexCalculation(population.get(j));
//                double opex = ReliabilityCalculation.opexCalculation(population.get(j));
//                if (capex >= 7000000.0) {
//                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), 100000000.0, opex));
//                } else if (opex >= 1500000.0) {
//                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, 100000000.0));
//                } else {
//                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, opex));
//                }
//            }

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }
}