package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.calculation.economicCalculation.CostsCalculation;
import org.mpei.nti.substation.substationStructures.*;

import java.util.List;
import java.util.UUID;

public class SubstationMeasuresPerYearGeneration {

    public static SubstationMeasuresPerYear substationMeasuresGeneration(int architectureType, int yearNumber,
                                                                         OrganizationalMeasures organizationalMeasures,
                                                                         ImprosedMeasures improsedMeasures,
                                                                         List<IED> iedList) {
        SubstationMeasuresPerYear substationMeasuresPerYear = new SubstationMeasuresPerYear();
        substationMeasuresPerYear.setId(UUID.randomUUID());
        substationMeasuresPerYear.setTotalPrice(0f);
        substationMeasuresPerYear.setCapexPrice(0f);
        substationMeasuresPerYear.setOpexPrice(0f);
        substationMeasuresPerYear.setArchitectureType(architectureType);
        substationMeasuresPerYear.setYearNumber(yearNumber);
        substationMeasuresPerYear.setOrganizationalMeasures(organizationalMeasures);
        substationMeasuresPerYear.setImprosedMeasures(improsedMeasures);
        substationMeasuresPerYear.setIedList(iedList);
        economicPerYearCalculation(substationMeasuresPerYear);
        return substationMeasuresPerYear;
    }

    public static void economicPerYearCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        if (substationMeasuresPerYear.getYearNumber() == 1) {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.buildingCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.buildingOPEX(substationMeasuresPerYear) +
                    CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
        } else {
            substationMeasuresPerYear.setCapexPrice(CostsCalculation.buildingCAPEX(substationMeasuresPerYear));
            substationMeasuresPerYear.setOpexPrice(CostsCalculation.exploitationOPEX(substationMeasuresPerYear));
        }
        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
    }

}
