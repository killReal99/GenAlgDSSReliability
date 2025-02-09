package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class OptimizeGenotype {

    public static void genotypeOptimization(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasures : substationMeasuresList) {
            idsOptimization(substationMeasures);
            firewallOptimization(substationMeasures);
            antivirusOptimization(substationMeasures);
            architectureOptimization(substationMeasures);
        }
    }

    public static void idsOptimization(SubstationMeasures substationMeasures) {
        int idsCheck = 0;
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
    }

    public static void firewallOptimization(SubstationMeasures substationMeasures) {
        int firewallCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (firewallCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD20() == 1) {
                firewallCheck++;
            }
            if (firewallCheck > 0 && firewallCheck <= 10) {
                substationMeasuresPerYear.getImprosedMeasures().setD20(1);
                firewallCheck++;
            }
            if (firewallCheck == 10) {
                firewallCheck = 0;
            }
        }
    }

    public static void antivirusOptimization(SubstationMeasures substationMeasures) {
        int antivirusCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (antivirusCheck == 0 && substationMeasuresPerYear.getImprosedMeasures().getD19() == 1) {
                antivirusCheck++;
            }
            if (antivirusCheck > 0 && antivirusCheck <= 5) {
                substationMeasuresPerYear.getImprosedMeasures().setD19(1);
                antivirusCheck++;
            }
            if (antivirusCheck == 5) {
                antivirusCheck = 0;
            }
        }
    }

    public static void architectureOptimization(SubstationMeasures substationMeasures) {
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getArchitectureType() == 2) {
                substationMeasuresPerYear.getOrganizationalMeasures().setD1(0);
                for (IED ied : substationMeasuresPerYear.getIedList()) {
                    ied.setD2(0);
                }
            }
            if (substationMeasuresPerYear.getArchitectureType() == 1) {
                substationMeasuresPerYear.getOrganizationalMeasures().setD1(0);
                for (IED ied : substationMeasuresPerYear.getIedList()) {
                    ied.setD2(0);
                    ied.setD17(0);
                }
            }
        }
    }

}
