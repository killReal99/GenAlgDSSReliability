package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.EmbeddedMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static void economicDamageCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0.0f) {
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());
                    float Mizl = OverTriggering.overTriggeringCalculation(substationMeasuresPerYear);
                    float Mlozh = FalsePositive.falsePositiveCalculation(substationMeasuresPerYear);
                    float Motk = FailureTriggering.failureTriggeringCalculation(substationMeasuresPerYear);
                    substationMeasure.setTotalPrice((Mizl + Mlozh + Motk) * 99 * 1000 +
                        substationMeasure.getCapexPrice() + substationMeasure.getOpexPrice());
                }
            }
        }
    }

    public static Float capexMeasuresCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0.0f;
        for (EmbeddedMeasures embeddedMeasures : substationMeasuresPerYear.getEmbeddedMeasuresList()) {
            embeddedMeasuresPrice += (embeddedMeasures.getD2() * 1083.33f + embeddedMeasures.getD4() * 1083.33f +
                embeddedMeasures.getD5() * 1083.33f + embeddedMeasures.getD8() * 1083.33f +
                embeddedMeasures.getD9() * 1083.33f + embeddedMeasures.getD13() * 1083.33f +
                embeddedMeasures.getD14() * 1083.33f + embeddedMeasures.getD15() * 1083.33f +
                embeddedMeasures.getD17() * 1083.33f + embeddedMeasures.getD18() * 1083.33f +
                embeddedMeasures.getD23() * 1083.33f);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * 20000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD7() * 1083.33f +
            substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD20() * 750000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD21() * 3250000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD24() * 150000.0f;
        return embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float opexMeasuresCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0.0f;
        for (EmbeddedMeasures embeddedMeasures : substationMeasuresPerYear.getEmbeddedMeasuresList()) {
            embeddedMeasuresPrice += (embeddedMeasures.getD2() * 15000.0f + embeddedMeasures.getD4() * 15000.0f +
                embeddedMeasures.getD5() * 15000.0f + embeddedMeasures.getD8() * 15000.0f +
                embeddedMeasures.getD9() * 15000.0f + embeddedMeasures.getD13() * 15000.0f +
                embeddedMeasures.getD14() * 15000.0f + embeddedMeasures.getD15() * 15000.0f +
                embeddedMeasures.getD17() * 15000.0f + embeddedMeasures.getD18() * 10000.0f +
                embeddedMeasures.getD23() * 15000.0f);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * 1500.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD7() * 15000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD11() * 75000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD20() * 150000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD21() * 300000.0f +
            substationMeasuresPerYear.getImprosedMeasures().getD24() * 1500.0f;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD1() * 75000.0f +
            substationMeasuresPerYear.getOrganizationalMeasures().getD6() * 75000.0f +
            substationMeasuresPerYear.getOrganizationalMeasures().getD10() * 75000.0f +
            substationMeasuresPerYear.getOrganizationalMeasures().getD12() * 75000.0f +
            substationMeasuresPerYear.getOrganizationalMeasures().getD16() * 75000.0f +
            substationMeasuresPerYear.getOrganizationalMeasures().getD22() * 75000.0f;
        return embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

}
