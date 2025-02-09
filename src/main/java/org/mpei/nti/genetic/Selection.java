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
            for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
                if (substationMeasuresPerYear.getYearNumber() == 1) {
                    if (substationMeasuresPerYear.getCapexPrice() >= 10000000f || substationMeasuresPerYear.getOpexPrice() >= 4000000f) {
                        substationMeasureForDeleting = substationMeasures;
                        deletingChecker = true;
                    }
                } else {
                    if (substationMeasuresPerYear.getCapexPrice() >= 10000000f || substationMeasuresPerYear.getOpexPrice() >= 5000000f) {
                        substationMeasureForDeleting = substationMeasures;
                        deletingChecker = true;
                    }
                }
            }
            if (deletingChecker) {
                populationForDeleting.add(substationMeasureForDeleting);
            }
        }
        for (SubstationMeasures deletingIndividual : populationForDeleting) {
            population.remove(deletingIndividual);
        }
    }

}
