package org.mpei.nti.genetic;

import org.mpei.nti.modelCalculation.CostsCalculation;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.RoulettePart;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

    public static List<SubstationMeasures> populationCrossing(List<SubstationMeasures> population) {
        List<SubstationMeasures> newSubstationMeasuresList = new ArrayList<>();
        List<RoulettePart> rouletteWeights = rouletteWeightCalculation(population);

        for (int i = 0; i < population.size(); i++) {
            SubstationMeasures firstParent = population.get(rouletteIndexChecker(rouletteWeights, Math.random()));
            SubstationMeasures secondParent = population.get(rouletteIndexChecker(rouletteWeights, Math.random()));
            if (Math.random() >= 0.5) {
                yearSwap(newSubstationMeasuresList, firstParent, secondParent);
            } else {
                insideYearSwap(newSubstationMeasuresList, firstParent, secondParent);
            }
        }
        return newSubstationMeasuresList;
    }

    public static void yearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                SubstationMeasures secondParent) {
        SubstationMeasures substationMeasures = new SubstationMeasures();
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);

        for (int i = 0; i <= 24; i++) {
            if (i <= swapYearNumber) {
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        firstParent.getSubstationMeasuresPerYear().get(i).getArchitectureType(), (i + 1),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList()));
            } else {
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        secondParent.getSubstationMeasuresPerYear().get(i).getArchitectureType(), (i + 1),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getIedList()));
            }
        }
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(substationMeasures);
    }

    public static void insideYearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                      SubstationMeasures secondParent) {
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);
        SubstationMeasuresPerYear substationMeasuresPerYear = new SubstationMeasuresPerYear(0.0f, 0.0f, 0.0f,
                firstParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getArchitectureType(),
                firstParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getYearNumber(),
                firstParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures(),
                firstParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures(),
                firstParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList());
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            if (i == (swapYearNumber - 1)) {
                substationMeasuresPerYearList.add(substationMeasuresPerYear);
            } else {
                substationMeasuresPerYearList.add(firstParent.getSubstationMeasuresPerYear().get(i));
            }
        }
        SubstationMeasures newSubstationMeasures = new SubstationMeasures(0.0f, 0.0f, 0.0f,
                substationMeasuresPerYearList);

        double randomNumber = Math.random();
        if (randomNumber < 0.25) {
            substationMeasuresPerYear.setArchitectureType(secondParent.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getArchitectureType());
        } else if (randomNumber < 0.5) {
            substationMeasuresPerYear.setOrganizationalMeasures(secondParent.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getOrganizationalMeasures());
        } else if (randomNumber < 0.75) {
            substationMeasuresPerYear.setImprosedMeasures(secondParent.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getImprosedMeasures());
        } else {
            substationMeasuresPerYear.setIedList(secondParent.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getIedList());
        }

        if (substationMeasuresPerYear.getYearNumber() == 1) {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.buildingCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.buildingOPEX(substationMeasuresPerYear));
        } else {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.exploitationCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
        }
        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() +
                substationMeasuresPerYear.getOpexPrice());

        substationMeasuresPerYearList.set(swapYearNumber - 1, substationMeasuresPerYear);
        newSubstationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(newSubstationMeasures);
    }


    public static Integer rouletteIndexChecker(List<RoulettePart> rouletteWeights, double pointer) {
        int index = 0;
        for (RoulettePart roulettePart : rouletteWeights) {
            if (pointer >= roulettePart.getLeftPart() && pointer < roulettePart.getRightPart()) {
                index = roulettePart.getIndex();
            }
        }
        return index;
    }

    public static List<RoulettePart> rouletteWeightCalculation(List<SubstationMeasures> population) {
        List<RoulettePart> rouletteWeightList = new ArrayList<>();
        float fullPopulationWeight = 0.0f;
        float partOfPopulationWeight = 0.0f;
        for (SubstationMeasures individual : population) {
            fullPopulationWeight += individual.getTotalPrice();
        }
        int individualIndex = 0;
        for (SubstationMeasures individual : population) {
            RoulettePart roulettePart = new RoulettePart();
            roulettePart.setLeftPart(partOfPopulationWeight);
            partOfPopulationWeight += individual.getTotalPrice() / fullPopulationWeight;
            roulettePart.setRightPart(partOfPopulationWeight);
            roulettePart.setIndex(individualIndex);
            rouletteWeightList.add(roulettePart);
            individualIndex++;
        }
        return rouletteWeightList;
    }

}