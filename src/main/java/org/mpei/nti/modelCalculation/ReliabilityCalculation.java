package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static void goalFunctionCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0.0f) {
                idsCheck(substationMeasure);

                int firewallCheck = 0;

                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());

                    if (substationMeasuresPerYear.getImprosedMeasures().getD20() == 1) {
                        firewallCheck++;
                    } else {
                        firewallCheck = 0;
                    }
                    if (firewallCheck == 10) {
                        substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + 3250000.0f);
                        firewallCheck = 0;
                    }
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

    public static void idsCheck(SubstationMeasures substationMeasure) {
        int idsCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getYearNumber() != 1) {
                if (idsCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + 750000.0f);
                }
                if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                    idsCheck++;
                } else {
                    idsCheck = 0;
                }
                if (idsCheck == 10) {
                    substationMeasure.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + 750000.0f);
                    idsCheck = 0;
                }
            }
        }
    }

}
