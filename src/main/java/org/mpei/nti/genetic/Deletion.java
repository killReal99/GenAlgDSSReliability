package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

import static org.mpei.nti.Main.populationSize;

public class Deletion {

    public static void deletePartOfPopulation (List<SubstationMeasures> population){
        int countDeleting = population.size() - populationSize;
        for (int j = 0; j < countDeleting; j++) {
            population.remove(population.size() - 1);
        }
    }
}
