package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

public class Sorting {

    public static void bubbleSort (List<SubstationMeasures> population) {
        boolean swapped;
        SubstationMeasures temp;
        for (int i = 0; i < population.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < population.size() - i - 1; j++) {
                if (population.get(j).getTotalPrice() > population.get(j + 1).getTotalPrice()) {
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
