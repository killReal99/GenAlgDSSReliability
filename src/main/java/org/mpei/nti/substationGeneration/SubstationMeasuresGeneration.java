package org.mpei.nti.substationGeneration;

import org.mpei.nti.substationStructure.EmbeddedMeasures;
import org.mpei.nti.substationStructure.ImprosedMeasures;
import org.mpei.nti.substationStructure.OrganizationalMeasures;
import org.mpei.nti.substationStructure.SubstationMeasures;

import java.util.List;

public class SubstationMeasuresGeneration {

    public static SubstationMeasures substationMeasures(int architectureType, int yearNumber,
                                                        OrganizationalMeasures organizationalMeasures,
                                                        ImprosedMeasures improsedMeasures,
                                                        List<EmbeddedMeasures> embeddedMeasuresList) {

        SubstationMeasures substationMeasures = new SubstationMeasures();
        float embeddedMeasuresPrice = 0.0f;
        for (EmbeddedMeasures embeddedMeasures : embeddedMeasuresList){
            embeddedMeasuresPrice += embeddedMeasures.getPrice();
        }
        substationMeasures.setTotalPrice(organizationalMeasures.getPrice() + improsedMeasures.getPrice()
                + embeddedMeasuresPrice);

        substationMeasures.setArchitectureType(architectureType);
        substationMeasures.setYearNumber(yearNumber);
        substationMeasures.setOrganizationalMeasures(organizationalMeasures);
        substationMeasures.setImprosedMeasures(improsedMeasures);
        substationMeasures.setEmbeddedMeasuresList(embeddedMeasuresList);
        return substationMeasures;
    }

}
