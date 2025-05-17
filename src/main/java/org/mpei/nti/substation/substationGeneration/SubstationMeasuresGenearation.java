package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.util.List;
import java.util.UUID;

public class SubstationMeasuresGenearation {

    public static SubstationMeasures substationMeasuresGeneration(List<SubstationMeasuresPerYear> substationMeasuresPerYear,
                                                                  boolean lanRosseti, boolean iedRosseti, int fstec
    ) {
        SubstationMeasures substationMeasures = new SubstationMeasures();
        substationMeasures.setId(UUID.randomUUID());
        substationMeasures.setTotalPrice(0f);
        substationMeasures.setDamage(0f);
        substationMeasures.setOpexPrice(0f);
        substationMeasures.setCapexPrice(0f);
        substationMeasures.setLanRosseti(lanRosseti);
        substationMeasures.setIedRosseti(iedRosseti);
        substationMeasures.setFstec(fstec);
        substationMeasures.setSubstationMeasuresPerYear(substationMeasuresPerYear);
        return substationMeasures;
    }

}
