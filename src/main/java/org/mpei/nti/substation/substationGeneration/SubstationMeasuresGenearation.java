package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;

public class SubstationMeasuresGenearation {

    public static SubstationMeasures substationMeasuresGeneration(List<SubstationMeasuresPerYear> substationMeasuresPerYear) {
        SubstationMeasures substationMeasures = new SubstationMeasures();
        substationMeasures.setTotalPrice(0.0f);
        substationMeasures.setOpexPrice(0.0f);
        substationMeasures.setCapexPrice(0.0f);
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYear);
        return substationMeasures;
    }

}
