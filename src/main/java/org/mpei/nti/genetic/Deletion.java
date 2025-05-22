package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

public class Deletion {

    public static void deletePartOfPopulation(List<SubstationMeasures> population, int populationSize) {
        if (population.size() > populationSize) {
            int countDeleting = population.size() - populationSize;
            for (int j = 0; j < countDeleting; j++) {
                population.remove(population.size() - 1);
            }
        }
    }
}
