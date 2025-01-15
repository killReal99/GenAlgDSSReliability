package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static void goalFunctionCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0.0f) {
                idsCheck(substationMeasure);
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());

                    substationMeasure.setTotalPrice(underSupplyCalculation(substationMeasuresPerYear) +
                            substationMeasure.getCapexPrice() + substationMeasure.getOpexPrice());
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
}
