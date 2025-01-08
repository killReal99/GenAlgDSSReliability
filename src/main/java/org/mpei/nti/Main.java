package org.mpei.nti;

import org.mpei.nti.genetic.*;
import org.mpei.nti.modelCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.ResultsMapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int minArch = 1;
    public static int protectionsCount = 3;
    public static int populationSize = 100;
    public static int numberOfIterations = 1000;

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
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population);
                Mutating.mutatePopulation(newPopulation);
                population.addAll(newPopulation);
                ReliabilityCalculation.economicDamageCalculation(population);
                Sorting.bubbleSort(population);
                Deleting.deletePartOfPopulation(population);

                System.out.println("Iteration N = " + i);
                System.out.println("The best individual " + population.get(0).hashCode() + " with total price " +
                    String.format("%f", population.get(0).getTotalPrice()) + " rubles");
                System.out.println("With CAPEX price " +String.format("%f", population.get(0).getCapexPrice()));
                System.out.println("With OPEX price " +String.format("%f", population.get(0).getOpexPrice()));
            } else {
                break;
            }

            if (Math.round(previousValueOfTotalPrice) == Math.round(population.get(0).getCapexPrice())){
                priceIterator++;
            } else {
                priceIterator = 0;
            }
            previousValueOfTotalPrice = population.get(0).getCapexPrice();
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