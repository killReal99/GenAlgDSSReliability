package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.modelCalculation.ReliabilityCalculation;
import org.mpei.nti.substation.substationStructures.EmbeddedMeasures;
import org.mpei.nti.substation.substationStructures.ImprosedMeasures;
import org.mpei.nti.substation.substationStructures.OrganizationalMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class SubstationMeasuresPerYearGeneration {

    public static SubstationMeasuresPerYear substationMeasuresGeneration(int architectureType, int yearNumber,
                                                                         OrganizationalMeasures organizationalMeasures,
                                                                         ImprosedMeasures improsedMeasures,
                                                                         List<EmbeddedMeasures> embeddedMeasuresList) {
        SubstationMeasuresPerYear substationMeasuresPerYear = new SubstationMeasuresPerYear();
        substationMeasuresPerYear.setArchitectureType(architectureType);
        substationMeasuresPerYear.setYearNumber(yearNumber);
        substationMeasuresPerYear.setOrganizationalMeasures(organizationalMeasures);
        substationMeasuresPerYear.setImprosedMeasures(improsedMeasures);
        substationMeasuresPerYear.setEmbeddedMeasuresList(embeddedMeasuresList);
        substationMeasuresPerYear.setOpexPrice(0.0f);
        substationMeasuresPerYear.setCapexPrice(ReliabilityCalculation.capexMeasuresCalculation(substationMeasuresPerYear));
        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
        return substationMeasuresPerYear;
    }

}
