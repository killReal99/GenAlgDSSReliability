package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.utils.enums.SingleCriteria;

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

    public static void quickSort(List<SubstationMeasures> population, int low, int high, SingleCriteria singleCriteria) {
        if (low < high) {
            int pi = partition(population, low, high, singleCriteria);
            quickSort(population, low, pi - 1, singleCriteria);
            quickSort(population, pi + 1, high, singleCriteria);
        }
    }


    private static int partition(List<SubstationMeasures> population, int low, int high, SingleCriteria singleCriteria) {
        int middle = low + (high - low) / 2;
        float pivot;
        float economic = population.get(middle).getOpexPrice() + population.get(middle).getCapexPrice();
        if (singleCriteria == SingleCriteria.MAIN_CRITERIA || singleCriteria == SingleCriteria.TARGET_PROGRAMMING) {
            pivot = population.get(middle).getDamage();
        } else {
            pivot = population.get(middle).getTotalPrice();
        }
        SubstationMeasures temp = population.get(middle);
        population.set(middle, population.get(high));
        population.set(high, temp);

        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (singleCriteria == SingleCriteria.MAIN_CRITERIA) {
                float economicDamage = population.get(j).getDamage();
                if (economicDamage < pivot) {
                    i++;
                    temp = population.get(i);
                    population.set(i, population.get(j));
                    population.set(j, temp);
                }
            } else if (singleCriteria == SingleCriteria.TARGET_PROGRAMMING) {
                float economicDamage = population.get(j).getDamage();
                if ((economicDamage < pivot) || ((economicDamage == pivot) && ((population.get(j).getOpexPrice() +
                        population.get(j).getOpexPrice()) < economic))) {
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
