package org.mpei.nti.modelCalculation;

import org.mpei.nti.economic.BuildingCAPEX;
import org.mpei.nti.economic.BuildingOPEX;
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
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D21);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                }
                if (idsCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D21);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
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
            if (substationMeasuresPerYear.getYearNumber() != 1 && substationMeasuresPerYear.getArchitectureType() != arch) {
                if (arch == 1) {
                    if (substationMeasuresPerYear.getArchitectureType() == 2) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromFirstToSecondRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromFirstToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                } else if (arch == 2) {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                } else {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToSecondRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromThirdToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                }
            }
            arch = substationMeasuresPerYear.getArchitectureType();
        }
    }
}
