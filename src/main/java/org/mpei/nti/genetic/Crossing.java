package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.utils.RoulettePart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Crossing {

    public static List<SubstationMeasures> populationCrossing(List<SubstationMeasures> population, boolean lanRosseti,
                                                              boolean iedRosseti, int fstec) {
        List<SubstationMeasures> newSubstationMeasuresList = new ArrayList<>();
        float fullPopulationWeight = 0.0f;
        float crossingProbability = 0.95f;
        for (SubstationMeasures individual : population) {
            fullPopulationWeight += individual.getTotalPrice();
        }
        List<RoulettePart> rouletteWeights = rouletteWeightCalculation(population, fullPopulationWeight);
        for (int i = 0; i < (population.size() * 2); i++) {
            if (Math.random() < crossingProbability) {
                SubstationMeasures firstParent = population.get(rouletteSearch(rouletteWeights,
                        (float) Math.random() * rouletteWeights.get(rouletteWeights.size() - 1).getRightPart(),
                        rouletteWeights.size() / 2));
                SubstationMeasures secondParent = population.get(rouletteSearch(rouletteWeights,
                        (float) Math.random() * rouletteWeights.get(rouletteWeights.size() - 1).getRightPart(),
                        rouletteWeights.size() / 2));
                if (Math.random() >= 0.7) {
                    yearSwap(newSubstationMeasuresList, firstParent, secondParent);
                } else {
                    insideYearSwap(newSubstationMeasuresList, firstParent, secondParent, lanRosseti, iedRosseti, fstec);
                }
            }
        }
        return newSubstationMeasuresList;
    }

    public static void yearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                SubstationMeasures secondParent) {
        SubstationMeasures child = new SubstationMeasures();
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        int swapYearNumber = (int) (Math.random() * (24 - 1) + 1);
        for (int i = 0; i < 25; i++) {
            if (i <= swapYearNumber) {
                int architectureType = firstParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
                OrganizationalMeasures childOrganizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
                ImprosedMeasures childImprosedMeasures = new ImprosedMeasures(UUID.randomUUID(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24()
                );
                List<IED> childIedList = new ArrayList<>();
                for (int j = 0; j < firstParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                    List<Protection> protectionList = new ArrayList<>();
                    if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.LINE) {
                        protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                    } else if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.BUS) {
                        protectionList = ProtectionsSet.busProtectionsSetGeneration();
                    } else if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                        protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
                    }
                    IED ied = new IED(UUID.randomUUID(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getNameOfIED(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName(),
                            protectionList,
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                            firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23(), 0.0f);
                    childIedList.add(ied);
                }
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        architectureType, (i + 1), childOrganizationalMeasures, childImprosedMeasures, childIedList));
            } else {
                int architectureType = secondParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
                OrganizationalMeasures childOrganizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
                ImprosedMeasures childImprosedMeasures = new ImprosedMeasures(UUID.randomUUID(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                        secondParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24());
                List<IED> childIedList = new ArrayList<>();
                for (int j = 0; j < secondParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                    List<Protection> protectionList = new ArrayList<>();
                    if (secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.LINE) {
                        protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                    } else if (secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.BUS) {
                        protectionList = ProtectionsSet.busProtectionsSetGeneration();
                    } else if (secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                        protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
                    }
                    IED ied = new IED(UUID.randomUUID(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getNameOfIED(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName(),
                            protectionList,
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                            secondParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23(), 0.0f);
                    childIedList.add(ied);
                }
                substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        architectureType, (i + 1), childOrganizationalMeasures, childImprosedMeasures, childIedList));
            }
        }
        child.setId(UUID.randomUUID());
        child.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(child);
    }

    public static void insideYearSwap(List<SubstationMeasures> substationMeasuresList, SubstationMeasures firstParent,
                                      SubstationMeasures secondParent, boolean lanRosseti, boolean iedRosseti, int fstec) {
        SubstationMeasures child = new SubstationMeasures();
        int swapYearNumber = (int) (Math.random() * (25 - 1) + 1);
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            int architectureType = firstParent.getSubstationMeasuresPerYear().get(i).getArchitectureType();
            OrganizationalMeasures childOrganizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD1(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD6(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD10(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD12(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD16(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getOrganizationalMeasures().getD22());
            ImprosedMeasures childImprosedMeasures = new ImprosedMeasures(UUID.randomUUID(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD3(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD7(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD11(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD19(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD20(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD21(),
                    firstParent.getSubstationMeasuresPerYear().get(i).getImprosedMeasures().getD24()
            );
            List<IED> childIedList = new ArrayList<>();
            for (int j = 0; j < firstParent.getSubstationMeasuresPerYear().get(i).getIedList().size(); j++) {
                List<Protection> protectionList = new ArrayList<>();
                if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.LINE) {
                    protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                } else if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.BUS) {
                    protectionList = ProtectionsSet.busProtectionsSetGeneration();
                } else if (firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                    protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
                }
                IED ied = new IED(UUID.randomUUID(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getNameOfIED(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getEquipmentTypeName(),
                        protectionList,
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD2(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD4(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD5(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD8(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD9(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD13(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD14(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD15(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD17(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD18(),
                        firstParent.getSubstationMeasuresPerYear().get(i).getIedList().get(j).getD23(), 0.0f);
                childIedList.add(ied);
            }
            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    architectureType, (i + 1), childOrganizationalMeasures, childImprosedMeasures, childIedList));
        }
        child.setSubstationMeasuresPerYear(substationMeasuresPerYearList);

        double randomNumber = Math.random();
        if (randomNumber < 0.05) {
            int architecture = secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getArchitectureType();
            substationMeasuresPerYearList.get(swapYearNumber - 1).setArchitectureType(architecture);
        } else if (randomNumber < 0.25) {
            OrganizationalMeasures childdOrganizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD1(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD6(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD10(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD12(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD16(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getOrganizationalMeasures().getD22());
            substationMeasuresPerYearList.get(swapYearNumber).setOrganizationalMeasures(childdOrganizationalMeasures);
        } else if (randomNumber < 0.45) {
            ImprosedMeasures childImprosedMeasures = new ImprosedMeasures(UUID.randomUUID(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD3(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD7(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD11(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD19(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD20(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD21(),
                    secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getImprosedMeasures().getD24()
            );
            substationMeasuresPerYearList.get(swapYearNumber - 1).setImprosedMeasures(childImprosedMeasures);
        } else {
            List<IED> childIedList = new ArrayList<>();
            for (int j = 0; j < secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().size(); j++) {
                int D2 = 0;
                int D17 = 0;
                List<Protection> protectionList = new ArrayList<>();
                if (secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getEquipmentTypeName() == EquipmentType.LINE) {
                    protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                } else if (secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getEquipmentTypeName() == EquipmentType.BUS) {
                    protectionList = ProtectionsSet.busProtectionsSetGeneration();
                } else if (secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                    protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
                }
                if (secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getArchitectureType() == 3) {
                    D2 = secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD2();
                    D17 = secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD17();
                } else if (secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getArchitectureType() == 2) {
                    D17 = secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD17();
                }
                IED ied = new IED(UUID.randomUUID(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getNameOfIED(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getEquipmentTypeName(),
                        protectionList, D2,
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD4(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD5(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD8(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD9(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD13(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD14(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD15(),
                        D17, secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD18(),
                        secondParent.getSubstationMeasuresPerYear().get(swapYearNumber - 1).getIedList().get(j).getD23(), 0.0f);
                childIedList.add(ied);
            }
            substationMeasuresPerYearList.get(swapYearNumber - 1).setIedList(childIedList);
        }
        substationMeasuresPerYearList.get(swapYearNumber - 1).setCapexPrice(0.0f);
        substationMeasuresPerYearList.get(swapYearNumber - 1).setOpexPrice(0.0f);
        substationMeasuresPerYearList.get(swapYearNumber - 1).setTotalPrice(0.0f);
        substationMeasuresPerYearList.set(swapYearNumber - 1, SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                substationMeasuresPerYearList.get(swapYearNumber - 1).getArchitectureType(), swapYearNumber,
                substationMeasuresPerYearList.get(swapYearNumber - 1).getOrganizationalMeasures(),
                substationMeasuresPerYearList.get(swapYearNumber - 1).getImprosedMeasures(),
                substationMeasuresPerYearList.get(swapYearNumber - 1).getIedList()));
        child.setId(UUID.randomUUID());
        child.setSubstationMeasuresPerYear(substationMeasuresPerYearList);
        substationMeasuresList.add(child);
    }

    public static int rouletteSearch(List<RoulettePart> rouletteWeights, float pointer, int startPoint) {
        int index = 0;
        if (pointer >= rouletteWeights.get(startPoint).getLeftPart() && pointer <= rouletteWeights.get(startPoint).getRightPart()) {
            index = rouletteWeights.get(startPoint).getIndex();
        } else {
            if (pointer <= rouletteWeights.get(startPoint).getLeftPart()) {
                int newStartPoint = startPoint - (int) (startPoint *
                        (rouletteWeights.get(startPoint).getLeftPart() - pointer) /
                        rouletteWeights.get(rouletteWeights.size() - 1).getRightPart());
                if (newStartPoint < 0) {
                    newStartPoint = 0;
                }
                if (startPoint == newStartPoint) {
                    newStartPoint = newStartPoint - 1;
                }
                index = rouletteSearch(rouletteWeights, pointer, newStartPoint);
            }
            if (pointer >= rouletteWeights.get(startPoint).getRightPart()) {
                int newStartPoint = getNewStartPoint(rouletteWeights, pointer, startPoint);
                index = rouletteSearch(rouletteWeights, pointer, newStartPoint);
            }
        }
        return index;
    }

    private static int getNewStartPoint(List<RoulettePart> rouletteWeights, float pointer, int startPoint) {
        int newStartPoint = startPoint + (int) (startPoint *
                (pointer - rouletteWeights.get(startPoint).getLeftPart()) /
                rouletteWeights.get(rouletteWeights.size() - 1).getRightPart());
        if (newStartPoint > (rouletteWeights.size() - 1)) {
            newStartPoint = (rouletteWeights.size() - 1);
        }
        if (startPoint == newStartPoint) {
            newStartPoint = newStartPoint + 1;
        }
        return newStartPoint;
    }

    public static List<RoulettePart> rouletteWeightCalculation(List<SubstationMeasures> population,
                                                               float fullPopulationWeight) {
        List<RoulettePart> rouletteWeightList = new ArrayList<>();
        float partOfPopulationWeight = 0.0f;
        int individualIndex = 0;
        for (SubstationMeasures individual : population) {
            RoulettePart roulettePart = new RoulettePart();
            roulettePart.setLeftPart(partOfPopulationWeight);
            partOfPopulationWeight += fullPopulationWeight / individual.getTotalPrice();
            roulettePart.setRightPart(partOfPopulationWeight);
            roulettePart.setIndex(individualIndex);
            rouletteWeightList.add(roulettePart);
            individualIndex++;
        }
        return rouletteWeightList;
    }

}