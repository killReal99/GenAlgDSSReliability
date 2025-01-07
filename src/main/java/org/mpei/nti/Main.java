package org.mpei.nti;

import org.mpei.nti.genetic.Crossing;
import org.mpei.nti.genetic.Deleting;
import org.mpei.nti.genetic.Sorting;
import org.mpei.nti.modelCalculation.ReliabilityCalculation;
import org.mpei.nti.genetic.PopulationGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.MappingSzi;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int minArch = 1;
    public static int protectionsCount = 3;
    public static int populationSize = 10;
    public static int numberOfIterations = 1000;

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        List<SubstationMeasures> substationMeasuresList = new ArrayList<>();

        // Generate population
        for (int i = 0; i < populationSize; i++) {
            substationMeasuresList.add(PopulationGeneration.generatePopulation(minArch, protectionsCount));
        }

        for (int i = 0; i < numberOfIterations; i++) {
            Crossing.individsCrossing(substationMeasuresList);
            ReliabilityCalculation.economicDamageCalculation(substationMeasuresList);
            Sorting.bubbleSort(substationMeasuresList);
            Deleting.deletePartOfPopulation(substationMeasuresList);
        }

        List<List<Double>> population = new ArrayList<>();
        for (int j = 0; j < populationSize; j++) {
            List<Double> d = new ArrayList<>();
            int arch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
            d.add((double) arch);
            d.add(0.0);
            for (int i = 0; i < 25; i++) {
                if (Math.round(Math.random()) >= 0.5) {
                    d.add(1.0);
                } else {
                    d.add(0.0);
                }
            }
            population.add(d);
        }

        for (int j = 0; j < populationSize; j++) {
            population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j),
                    ReliabilityCalculation.capexCalculation(population.get(j)),
                    ReliabilityCalculation.opexCalculation(population.get(j))));
        }

        int withoutChanges = 0;
        double theLastValue = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            int theNextGenerationPopulation = populationSize;
            for (int k = 0; k < populationSize; k++) {
                // Swap
                if (Math.random() >= 0.5) {
                    int sizeOfSwap = (int) Math.round(Math.random() * (population.get(0).size() - 3));
                    int secondFather = (int) Math.round(Math.random() * (populationSize - 1));
                    List<Double> sonOfSwap = new ArrayList<>();
                    int newArch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
                    sonOfSwap.add((double) newArch);
                    sonOfSwap.add(0.0);
                    for (int l = 0; l < sizeOfSwap; l++) {
                        sonOfSwap.add(population.get(k).get(l + 2));
                    }
                    for (int l = 0; l < (population.get(0).size() - 2 - sizeOfSwap); l++) {
                        sonOfSwap.add(population.get(secondFather).get(l + sizeOfSwap + 2));
                    }
                    // Mutation
                    if (Math.random() >= 0.5) {
                        if (Math.random() >= 0.5) {
                            int mutateArch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
                            sonOfSwap.set(0, (double) mutateArch);
                        } else {
                            int mutateGen = (int) Math.round(Math.random() * (population.get(0).size() - 3));
                            if (sonOfSwap.get(mutateGen) == 0) {
                                sonOfSwap.set(mutateGen, 1.0);
                            } else {
                                sonOfSwap.set(mutateGen, 0.0);
                            }
                        }
                    }
                    population.add(sonOfSwap);
                    theNextGenerationPopulation++;
                }
            }

            System.out.println("Iteration N = " + i);

            for (int j = 0; j < theNextGenerationPopulation; j++) {
                double capex = ReliabilityCalculation.capexCalculation(population.get(j));
                double opex = ReliabilityCalculation.opexCalculation(population.get(j));
                if (capex >= 7000000.0) {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), 100000000.0, opex));
                } else if (opex >= 1500000.0) {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, 100000000.0));
                } else {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, opex));
                }
            }

            bubbleSort(population, theNextGenerationPopulation);

            int countDeleting = theNextGenerationPopulation - populationSize;
            for (int j = 0; j < countDeleting; j++) {
                population.remove(population.size() - 1);
            }
            System.out.println("The best individ with M = " + population.get(0).get(1));

            if (Math.round(theLastValue * 100) == Math.round(population.get(0).get(1) * 100)) {
                withoutChanges++;
            } else {
                withoutChanges = 0;
            }
            theLastValue = population.get(0).get(1);
            if (withoutChanges == numberOfIterations / 2) {
                break;
            }

        }
        MappingSzi.mapSzi(population);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    static void bubbleSort
            (List<List<Double>> population,
             int populationSize) {
        boolean swapped;
        List<Double> temp;
        for (int i = 0; i < populationSize - 1; i++) {
            swapped = false;
            for (int j = 0; j < populationSize - i - 1; j++) {
                if (population.get(j).get(1) > population.get(j + 1).get(1)) {
                    temp = population.get(j);
                    population.set(j, population.get(j + 1));
                    population.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}