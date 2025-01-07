package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.*;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

    public static float crossingProbability = 0.5f;

    public static void individsCrossing(List<SubstationMeasures> substationMeasuresList) {
        List<SubstationMeasures> newSubstationMeasuresList = new ArrayList<>();
        for (SubstationMeasures substationMeasures : substationMeasuresList) {
            if (Math.random() >= crossingProbability) {
                if (Math.random() >= 0.5) {
                    yearSwap(newSubstationMeasuresList, substationMeasures,
                            substationMeasuresList.get((int) (Math.random() * (substationMeasuresList.size() - 1))));
                } else {
                    insideYearSwap(newSubstationMeasuresList, substationMeasures,
                            substationMeasuresList.get((int) (Math.random() * (substationMeasuresList.size() - 1))));
                }
            }
        }
        substationMeasuresList.addAll(newSubstationMeasuresList);
    }

    public static void yearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstSubstationMeasures,
                                SubstationMeasures secondSubstationMeasures) {
        SubstationMeasures substationMeasures = new SubstationMeasures();
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);

        for (int i = 0; i <= 24; i++) {
            if (i <= swapYearNumber) {
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        firstSubstationMeasures.getSubstationMeasuresPerYear().get(i).getArchitectureType(),
                        (i + 1), firstSubstationMeasures.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures(),
                        firstSubstationMeasures.getSubstationMeasuresPerYear().get(i).getImprosedMeasures(),
                        firstSubstationMeasures.getSubstationMeasuresPerYear().get(i).getEmbeddedMeasuresList()));
            } else {
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getArchitectureType(),
                        (i + 1), secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures(),
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getImprosedMeasures(),
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getEmbeddedMeasuresList()));
            }
        }
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(substationMeasures);
    }

    public static void insideYearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstSubstationMeasures,
                                      SubstationMeasures secondSubstationMeasures) {
        SubstationMeasures substationMeasures = new SubstationMeasures();

        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);

        SubstationMeasuresPerYear swapedSubstationMeasuresPerYear = firstSubstationMeasures.getSubstationMeasuresPerYear().
                get(swapYearNumber - 1);

        double randomNumber = Math.random();
        if (randomNumber < 0.25) {
            swapedSubstationMeasuresPerYear.setArchitectureType(secondSubstationMeasures.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getArchitectureType());
        } else if (randomNumber < 0.5) {
            swapedSubstationMeasuresPerYear.setOrganizationalMeasures(secondSubstationMeasures.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getOrganizationalMeasures());
        } else if (randomNumber < 0.75) {
            swapedSubstationMeasuresPerYear.setImprosedMeasures(secondSubstationMeasures.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getImprosedMeasures());
        } else {
            swapedSubstationMeasuresPerYear.setEmbeddedMeasuresList(secondSubstationMeasures.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getEmbeddedMeasuresList());
        }

        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = firstSubstationMeasures.getSubstationMeasuresPerYear();
        substationMeasuresPerYearList.set((swapYearNumber - 1), SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                swapedSubstationMeasuresPerYear.getArchitectureType(), swapYearNumber,
                swapedSubstationMeasuresPerYear.getOrganizationalMeasures(), swapedSubstationMeasuresPerYear.getImprosedMeasures(),
                swapedSubstationMeasuresPerYear.getEmbeddedMeasuresList()));
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(substationMeasures);
    }

}