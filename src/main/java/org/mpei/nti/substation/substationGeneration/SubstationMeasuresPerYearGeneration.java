package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.modelCalculation.CostsCalculation;
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
        if (yearNumber == 1) {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.buildingCAPEX(substationMeasuresPerYear) +
                    CostsCalculation.exploitationCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.buildingOPEX(substationMeasuresPerYear) +
                    CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
        } else {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.exploitationCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
        }
        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
        return substationMeasuresPerYear;
    }

}
