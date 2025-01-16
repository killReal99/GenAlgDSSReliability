package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

public class Sorting {

    public static void bubbleSort(List<SubstationMeasures> population) {
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

    public static void quickSort(List<SubstationMeasures> population, int low, int high) {
        if (low < high) {
            int pi = partition(population, low, high);
            quickSort(population, low, pi - 1);
            quickSort(population, pi + 1, high);
        }
    }


    private static int partition(List<SubstationMeasures> population, int low, int high) {
        int middle = low + (high - low) / 2;
        float pivot = population.get(middle).getTotalPrice();
        SubstationMeasures temp = population.get(middle);
        population.set(middle, population.get(high));
        population.set(high, temp);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (population.get(j).getTotalPrice() < pivot) {
                i++;
                temp = population.get(i);
                population.set(i, population.get(j));
                population.set(j, temp);
            }
        }

        temp = population.get(i + 1);
        population.set((i + 1), population.get(high));
        population.set(high, temp);
        return i + 1;
    }

}
