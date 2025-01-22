package org.mpei.nti.modelCalculation;

import org.mpei.nti.economic.BuildingCAPEX;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static void goalFunctionCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0.0f) {
                idsCheck(substationMeasure);
                archCheck(substationMeasure);
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());

                    substationMeasure.setTotalPrice(substationMeasure.getTotalPrice() +
                            underSupplyCalculation(substationMeasuresPerYear) + substationMeasuresPerYear.getCapexPrice() +
                            substationMeasuresPerYear.getOpexPrice());
                }
            }
        }
    }

    public static Float underSupplyCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float Mizl = OverTriggering.overTriggeringCalculation(substationMeasuresPerYear);
        float Mlozh = FalsePositive.falsePositiveCalculation(substationMeasuresPerYear);
        float Motk = FailureTriggering.failureTriggeringCalculation(substationMeasuresPerYear);
        return (Mizl + Mlozh + Motk) * 99 * 1000;
    }

    public static void idsCheck(SubstationMeasures substationMeasures) {
        int idsCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                idsCheck++;
                if (substationMeasuresPerYear.getYearNumber() != 1 && idsCheck == 1) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + 750000.0f);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + 1.0f);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            750000.0f + 1.0f);
                }
                if (idsCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + 750000.0f);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + 1.0f);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            750000.0f + 1.0f);
                    idsCheck = 1;
                }
            } else {
                idsCheck = 0;
            }
        }
    }

    public static void archCheck(SubstationMeasures substationMeasures) {
        int arch = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getYearNumber() != 1 && substationMeasuresPerYear.getYearNumber() != arch) {
                if (substationMeasuresPerYear.getArchitectureType() == 1) {
                    if (arch == 2) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromFirstToSecondRebuild);
//                        OPEX;
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromFirstToSecondRebuild + 1.0f);
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromFirstToThirdRebuild + 1.0f);
                    }
                } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
                    if (arch == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromSecondToFirstRebuild + 1.0f);
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromSecondToThirdRebuild + 1.0f);
                    }
                } else {
                    if (arch == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromThirdToFirstRebuild + 1.0f);
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                BuildingCAPEX.fromThirdToSecondRebuild + 1.0f);
                    }
                }
            }
            arch = substationMeasuresPerYear.getArchitectureType();
        }
    }
}
