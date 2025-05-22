package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class OptimizeGenotype {

    public static void genotypeOptimization(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasures : substationMeasuresList) {
            boolean idsReCalculate = idsOptimization(substationMeasures);
            boolean firewallReCalculate = firewallOptimization(substationMeasures);
            boolean antivirusReCalculate = antivirusOptimization(substationMeasures);
            if (idsReCalculate || firewallReCalculate || antivirusReCalculate) {
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()){
                    substationMeasuresPerYear.setTotalPrice(0f);
                    substationMeasuresPerYear.setCapexPrice(0f);
                    substationMeasuresPerYear.setOpexPrice(0f);
                    SubstationMeasuresPerYearGeneration.economicPerYearCalculation(substationMeasuresPerYear);
                }
            }
        }
    }

    public static boolean idsOptimization(SubstationMeasures substationMeasures) {
        int idsCheck = 0;
        boolean reCalculate = false;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (idsCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                idsCheck++;
            }
            if (idsCheck > 0 && idsCheck <= 10) {
                substationMeasuresPerYear.getImprosedMeasures().setD21(1);
                idsCheck++;
            }
            if (idsCheck == 10) {
                idsCheck = 0;
            }
        }
        return reCalculate;
    }

    public static boolean firewallOptimization(SubstationMeasures substationMeasures) {
        int firewallCheck = 0;
        boolean reCalculate = false;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (firewallCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD20() == 1) {
                firewallCheck++;
            }
            if (firewallCheck > 0 && firewallCheck <= 10) {
                substationMeasuresPerYear.getImprosedMeasures().setD20(1);
                reCalculate = true;
                firewallCheck++;
            }
            if (firewallCheck == 10) {
                firewallCheck = 0;
            }
        }
        return reCalculate;
    }

    public static boolean antivirusOptimization(SubstationMeasures substationMeasures) {
        int antivirusCheck = 0;
        boolean reCalculate = false;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (antivirusCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD19() == 1) {
                antivirusCheck++;
            }
            if (antivirusCheck > 0 && antivirusCheck <= 5) {
                substationMeasuresPerYear.getImprosedMeasures().setD19(1);
                reCalculate = true;
                antivirusCheck++;
            }
            if (antivirusCheck == 5) {
                antivirusCheck = 0;
            }
        }
        return reCalculate;
    }

}
