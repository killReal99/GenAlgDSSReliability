package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class OptimizeGenotype {

    public static void genotypeOptimization(List<SubstationMeasures> substationMeasuresList) {
        for (SubstationMeasures substationMeasures : substationMeasuresList) {
            idsOptimization(substationMeasures);
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

}
