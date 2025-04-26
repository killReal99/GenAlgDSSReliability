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

    public static void quickSort(List<SubstationMeasures> population, int low, int high, boolean multicriterial) {
        if (low < high) {
            int pi = partition(population, low, high, multicriterial);
            quickSort(population, low, pi - 1, multicriterial);
            quickSort(population, pi + 1, high, multicriterial);
        }
    }


    private static int partition(List<SubstationMeasures> population, int low, int high, boolean multicriterial) {
        int middle = low + (high - low) / 2;
        float pivot;
        float opex = population.get(middle).getOpexPrice();
        if (multicriterial) {
            pivot = population.get(middle).getTotalPrice() - population.get(middle).getCapexPrice() -
                    population.get(middle).getOpexPrice();
        } else {
            pivot = population.get(middle).getTotalPrice();
        }
        SubstationMeasures temp = population.get(middle);
        population.set(middle, population.get(high));
        population.set(high, temp);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (multicriterial) {
                float economicDamage = population.get(j).getTotalPrice() - population.get(j).getCapexPrice() -
                        population.get(j).getOpexPrice();
                if ((economicDamage < pivot) || ((economicDamage == pivot) && (population.get(j).getOpexPrice() < opex))) {
                    i++;
                    temp = population.get(i);
                    population.set(i, population.get(j));
                    population.set(j, temp);
                }
            } else {
                if (population.get(j).getTotalPrice() < pivot) {
                    i++;
                    temp = population.get(i);
                    population.set(i, population.get(j));
                    population.set(j, temp);
                }
            }
        }

        temp = population.get(i + 1);
        population.set((i + 1), population.get(high));
        population.set(high, temp);
        return i + 1;
    }

}
