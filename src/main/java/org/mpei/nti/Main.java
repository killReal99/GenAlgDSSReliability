package org.mpei.nti;

import org.mpei.nti.genetic.*;
import org.mpei.nti.calculation.economicCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationGeneration.IEDImpactGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.*;
import org.mpei.nti.substation.substationGeneration.BreakersMapGeneration;
import org.mpei.nti.utils.enums.SingleCriteria;
import org.mpei.nti.utils.enums.WeightScenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        final long startTime = System.currentTimeMillis();
        int minArch = 3;
        int maxArch = 3;
        SingleCriteria singleCriteria = SingleCriteria.CONVOLUTION_METHOD;
        WeightScenario weightScenario = WeightScenario.Direct;
        boolean lanRosseti = true;
        boolean iedRosseti = true;
        int fstec = 0;
        int populationSize = 100;
        int numberOfIterations = 5000;
        List<SubstationMeasures> bestIndividuals = new ArrayList<>();
        WeightCoeff weightCoeff = new WeightCoeff();

        List<SchemaStatus> schemaStatusList = ReadSchemStatus.readSchem();
        HashMap<Breaker, Probability> breakersMap = BreakersMapGeneration.generate();
        List<IEDImpact> iedImpactList = IEDImpactGeneration.generate(breakersMap);

        List<SubstationMeasures> population = new ArrayList<>();
        BoundaryIndividualsAdding.addBoundaryAdding(population, minArch, maxArch, lanRosseti, iedRosseti, fstec);
        WeightCoeffCalculation.calculateWeights(population, breakersMap, iedImpactList, schemaStatusList, weightCoeff);
        for (int i = 0; i < populationSize; i++) {
            population.add(PopulationGeneration.generatePopulation(minArch, maxArch, lanRosseti, iedRosseti, fstec));
        }
//        Accelerator.quickStart(population, lanRosseti, iedRosseti, fstec);
        OptimizeGenotype.genotypeOptimization(population);
        ReliabilityCalculation.goalFunctionCalculation(population, breakersMap, iedImpactList, schemaStatusList,
                weightScenario, weightCoeff);
        Selection.selectionOfSuitableIndividuals(population);

        float previousValueOfTotalPrice = 0f;
        int priceIterator = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            if (priceIterator != numberOfIterations * 0.1) {
                List<SubstationMeasures> newPopulation = Crossing.populationCrossing(population, lanRosseti, iedRosseti,
                        fstec);
                float mutationProbability = 0.5f + priceIterator / (numberOfIterations * 0.1f);
                Mutating.mutatePopulation(newPopulation, mutationProbability, minArch, maxArch, lanRosseti, iedRosseti, fstec);

                if (priceIterator >= numberOfIterations * 0.05) {
                    Deletion.deletePartOfPopulation(population, (populationSize - 1));
                    for (int d = 0; d < (int) (numberOfIterations * 0.05); d++) {
                        newPopulation.add(PopulationGeneration.generatePopulation(minArch, maxArch, lanRosseti, iedRosseti, fstec));
                    }
                    System.out.println("Повышение разнообразия популяции");
                }

                OptimizeGenotype.genotypeOptimization(newPopulation);
                ReliabilityCalculation.goalFunctionCalculation(newPopulation, breakersMap, iedImpactList,
                        schemaStatusList, weightScenario, weightCoeff);
                population.addAll(newPopulation);
                Selection.selectionOfSuitableIndividuals(population);
                Sorting.quickSort(population, 0, population.size() - 1, singleCriteria);
                Deletion.deletePartOfPopulation(population, populationSize);

                bestIndividuals.add(population.get(0));
//                for (int a = 0; a < 10; a++) {
//                    if (a < population.size()) {
//                        bestIndividuals.add(population.get(a));
//                    }
//                }
                System.out.println("Номер итерации " + (i + 1));
                System.out.println("Лучший индивид " + population.get(0).getId() + " с целевой функцией " +
                        String.format("%f", population.get(0).getTotalPrice()) + " руб");
            } else break;
            if (previousValueOfTotalPrice == population.get(0).getTotalPrice()) {
                priceIterator++;
            } else priceIterator = 0;
            previousValueOfTotalPrice = population.get(0).getTotalPrice();
        }

        ResultsMapping.resultsMapping(population, bestIndividuals);

        final long endTime = System.currentTimeMillis();
        System.out.println("Общее время выполнения: " + (endTime - startTime) / 1000 + " сек");
    }
}