package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.RoulettePart;

import java.util.ArrayList;
import java.util.List;

public class Crossing {

    public static List<SubstationMeasures> populationCrossing(List<SubstationMeasures> population) {
        List<SubstationMeasures> newSubstationMeasuresList = new ArrayList<>();
        List<RoulettePart> rouletteWeights = rouletteWeightCalculation(population);

        for (int i = 0; i < population.size(); i++){
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
                        firstSubstationMeasures.getSubstationMeasuresPerYear().get(i).getIedList()));
            } else {
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getArchitectureType(),
                        (i + 1), secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures(),
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getImprosedMeasures(),
                        secondSubstationMeasures.getSubstationMeasuresPerYear().get(i).getIedList()));
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
            swapedSubstationMeasuresPerYear.setIedList(secondSubstationMeasures.getSubstationMeasuresPerYear().
                    get(swapYearNumber - 1).getIedList());
        }

        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = firstSubstationMeasures.getSubstationMeasuresPerYear();
        substationMeasuresPerYearList.set((swapYearNumber - 1), SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                swapedSubstationMeasuresPerYear.getArchitectureType(), swapYearNumber,
                swapedSubstationMeasuresPerYear.getOrganizationalMeasures(), swapedSubstationMeasuresPerYear.getImprosedMeasures(),
                swapedSubstationMeasuresPerYear.getIedList()));
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(substationMeasures);
    }



    public static Integer rouletteIndexChecker(List<RoulettePart> rouletteWeights, double pointer){
        int index = 0;
        for (RoulettePart roulettePart : rouletteWeights){
                if (pointer >= roulettePart.getLeftPart() && pointer < roulettePart.getRightPart()){
                    index = roulettePart.getIndex();
                }
            }
        return index;
    }

        public static List<RoulettePart> rouletteWeightCalculation(List<SubstationMeasures> population){
            List<RoulettePart> rouletteWeightList = new ArrayList<>();
            float fullPopulationWeight = 0.0f;
            float partOfPopulationWeight = 0.0f;
            for (SubstationMeasures individual : population){
                fullPopulationWeight += individual.getTotalPrice();
            }
            int individualIndex = 0;
            for (SubstationMeasures individual : population){
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