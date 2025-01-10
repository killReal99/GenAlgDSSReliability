package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static void economicDamageCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0.0f) {
                int firewallCheck = 0;
                int idsCheck = 0;
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());

                    if (substationMeasuresPerYear.getImprosedMeasures().getD20() == 1){
                        firewallCheck++;
                    } else {
                        firewallCheck = 0;
                    }
                    if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1){
                        idsCheck++;
                    } else {
                        idsCheck = 0;
                    }

                    if (idsCheck == 10){
                        substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + 750000.0f);
                        idsCheck = 0;
                    }
                    if (firewallCheck == 10){
                        substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + 3250000.0f);
                        firewallCheck = 0;
                    }

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
        float improsedMeasuresPrice = 0.0f;
        if (substationMeasuresPerYear.getYearNumber() == 1) {
            for (IED ied : substationMeasuresPerYear.getIedList()) {
                embeddedMeasuresPrice += (ied.getD2() * 1083.33f + ied.getD4() * 1083.33f + ied.getD5() * 1083.33f +
                        ied.getD8() * 1083.33f + ied.getD9() * 1083.33f + ied.getD13() * 1083.33f +
                        ied.getD14() * 1083.33f + ied.getD15() * 1083.33f + ied.getD17() * 1083.33f +
                        ied.getD18() * 1083.33f + ied.getD23() * 1083.33f);
            }
            improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * 20000.0f +
                    substationMeasuresPerYear.getImprosedMeasures().getD7() * 1083.33f +
                    substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500000.0f +
                    substationMeasuresPerYear.getImprosedMeasures().getD20() * 750000.0f +
                    substationMeasuresPerYear.getImprosedMeasures().getD21() * 3250000.0f +
                    substationMeasuresPerYear.getImprosedMeasures().getD24() * 150000.0f;
        } else{
            improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500000.0f;
        }
        return embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float opexMeasuresCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0.0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * 15000.0f + ied.getD4() * 15000.0f + ied.getD5() * 15000.0f +
                    ied.getD8() * 15000.0f + ied.getD9() * 15000.0f + ied.getD13() * 15000.0f +
                    ied.getD14() * 15000.0f + ied.getD15() * 15000.0f + ied.getD17() * 15000.0f +
                    ied.getD18() * 10000.0f + ied.getD23() * 15000.0f);
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
