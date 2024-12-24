package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.EmbeddedMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class ReliabilityCalculation {

    public static List<Double> electricalEnergyUndersupply(List<Double> individ, Double capex, Double opex) {
        double Mizl = OverTriggering.mathNedoutp(individ.get(0), individ.get(2), individ.get(3), individ.get(4),
                individ.get(5), individ.get(6), individ.get(7), individ.get(8), individ.get(9), individ.get(10),
                individ.get(11), individ.get(12), individ.get(13), individ.get(14), individ.get(15), individ.get(16));
        double Mlozh = lozh.mathNedoutp(individ.get(0), individ.get(2), individ.get(3), individ.get(4), individ.get(5),
                individ.get(6), individ.get(7), individ.get(8), individ.get(9), individ.get(10), individ.get(11),
                individ.get(12), individ.get(13), individ.get(14), individ.get(15), individ.get(16), individ.get(17),
                individ.get(18), individ.get(19), individ.get(20), individ.get(21), individ.get(22), individ.get(23));
        double Motk = otk.mathNedoutp(individ.get(0), individ.get(2), individ.get(3), individ.get(4), individ.get(5),
                individ.get(6), individ.get(7), individ.get(8), individ.get(9), individ.get(10), individ.get(11),
                individ.get(12), individ.get(13), individ.get(14), individ.get(15), individ.get(16), individ.get(17),
                individ.get(18), individ.get(19), individ.get(20), individ.get(21), individ.get(22), individ.get(23),
                individ.get(24), individ.get(25));

        double M = (Mizl + Mlozh + Motk) * 99 * 1000 + capex + opex;
        individ.set(1, M);
        return individ;
    }


    public static void economicDamageCalculation(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            substationMeasure.setTotalPrice(1.0f);
        }
    }


    public static Double capexCalculation(List<Double> individ) {
        return individ.get(3) * 1083.33 + individ.get(4) * 20000 + individ.get(5) * 1083.33 +
                individ.get(6) * 1083.33 + individ.get(8) * 1083.33 + individ.get(9) * 1083.33 +
                individ.get(10) * 1083.33 + individ.get(14) * 1083.33 + individ.get(15) * 1083.33 +
                individ.get(16) * 1083.33 + individ.get(18) * 1083.33 + individ.get(19) * 1083.33 +
                individ.get(20) * 1500000 + individ.get(21) * 750000 + individ.get(22) * 3250000 +
                individ.get(24) * 1083.33 + individ.get(25) * 150000;
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

    public static Double opexCalculation(List<Double> individ) {
        return individ.get(2) * 75000 + individ.get(3) * 15000 + individ.get(4) * 1500 +
                individ.get(5) * 15000 + individ.get(6) * 15000 + individ.get(7) * 75000 + individ.get(7) * 15000 +
                individ.get(9) * 15000 + individ.get(10) * 15000 + individ.get(11) * 75000 + individ.get(12) * 75000 +
                individ.get(13) * 75000 + individ.get(14) * 15000 + individ.get(15) * 15000 + individ.get(16) * 15000 +
                individ.get(17) * 75000 + individ.get(18) * 15000 + individ.get(19) * 10000 + individ.get(20) * 1500 +
                individ.get(21) * 150000 + individ.get(22) * 300000 + individ.get(23) * 75000 +
                individ.get(24) * 15000 + individ.get(25) * 1500;
    }

    public static Double opexMeasuresCalculation(List<SubstationMeasuresPerYear> substationMeasurePerYears) {
        return substationMeasurePerYears.get(0).getOrganizationalMeasures().getD1() * 75000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD2() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD2() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD2()) * 15000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD3() * 1500.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD4() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD4() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD4()) * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD5() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD5() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD5()) * 15000.0 +
                substationMeasurePerYears.get(0).getOrganizationalMeasures().getD6() * 75000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD7() * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD8() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD8() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD8()) * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD9() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD9() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD9()) * 15000.0 +
                substationMeasurePerYears.get(0).getOrganizationalMeasures().getD10() * 75000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD11() * 75000.0 +
                substationMeasurePerYears.get(0).getOrganizationalMeasures().getD12() * 75000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD13() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD13() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD13()) * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD14() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD14() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD14()) * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD15() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD15() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD15()) * 15000.0 +
                substationMeasurePerYears.get(0).getOrganizationalMeasures().getD16() * 75000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD17() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD17() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD17()) * 15000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD18() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD18() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD18()) * 10000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD19() * 1500.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD20() * 150000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD21() * 300000.0 +
                substationMeasurePerYears.get(0).getOrganizationalMeasures().getD22() * 75000.0 +
                (substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(0).getD23() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(1).getD23() +
                        substationMeasurePerYears.get(0).getEmbeddedMeasuresList().get(2).getD23()) * 15000.0 +
                substationMeasurePerYears.get(0).getImprosedMeasures().getD24() * 1500.0;
    }

}
