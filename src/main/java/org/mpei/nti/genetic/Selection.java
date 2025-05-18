package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static void selectionOfSuitableIndividuals(List<SubstationMeasures> population) {
        List<SubstationMeasures> populationForDeleting = new ArrayList<>();
        for (SubstationMeasures substationMeasures : population) {
            SubstationMeasures substationMeasureForDeleting = new SubstationMeasures();
            boolean deletingChecker = false;
            if (substationMeasures.getCapexPrice() >= 2250000000f) {
                substationMeasureForDeleting = substationMeasures;
                deletingChecker = true;
            }
            if (substationMeasures.getOpexPrice() >= 25500000f) {
                substationMeasureForDeleting = substationMeasures;
                deletingChecker = true;
            }
//            for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
//                if (substationMeasuresPerYear.getYearNumber() == 1) {
//                    if (substationMeasuresPerYear.getCapexPrice() >= 200000000f || substationMeasuresPerYear.getOpexPrice() >= 1200000f) {
//                        substationMeasureForDeleting = substationMeasures;
//                        deletingChecker = true;
//                    }
//                } else {
//                    if (substationMeasuresPerYear.getCapexPrice() >= 15000000f || substationMeasuresPerYear.getOpexPrice() >= 1200000f) {
//                        substationMeasureForDeleting = substationMeasures;
//                        deletingChecker = true;
//                    }
//                }
//            }
            if (deletingChecker) {
                populationForDeleting.add(substationMeasureForDeleting);
            }
        }
        for (SubstationMeasures deletingIndividual : populationForDeleting) {
            population.remove(deletingIndividual);
        }
    }

}
