package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.modelCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationStructures.*;

import java.util.List;

public class SubstationMeasuresPerYearGeneration {

    public static SubstationMeasuresPerYear substationMeasuresGeneration(int architectureType, int yearNumber,
                                                                         OrganizationalMeasures organizationalMeasures,
                                                                         ImprosedMeasures improsedMeasures,
                                                                         List<IED> iedList) {
        SubstationMeasuresPerYear substationMeasuresPerYear = new SubstationMeasuresPerYear();
        substationMeasuresPerYear.setArchitectureType(architectureType);
        substationMeasuresPerYear.setYearNumber(yearNumber);
        substationMeasuresPerYear.setOrganizationalMeasures(organizationalMeasures);
        substationMeasuresPerYear.setImprosedMeasures(improsedMeasures);
        substationMeasuresPerYear.setIedList(iedList);
        substationMeasuresPerYear.setOpexPrice(ReliabilityCalculation.opexMeasuresCalculation(substationMeasuresPerYear));
        substationMeasuresPerYear.setCapexPrice(ReliabilityCalculation.capexMeasuresCalculation(substationMeasuresPerYear));
        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
        return substationMeasuresPerYear;
    }

}
