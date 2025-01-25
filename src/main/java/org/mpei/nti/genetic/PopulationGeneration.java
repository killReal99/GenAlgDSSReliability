package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int maxArch) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            ImprosedMeasures improsedMeasures = new ImprosedMeasures(1, 1, 1, 1, 1, 1, 1);
            OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures(1, 1, 1, 1, 1, 1);

//            ImprosedMeasures improsedMeasures = new ImprosedMeasures((int) Math.round(Math.random()),
//                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
//                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));
//            OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures((int) Math.round(Math.random()),
//                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
//                    (int) Math.round(Math.random()), (int) Math.round(Math.random()));

            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                ((int) (Math.random() * (maxArch - minArch) + minArch)), year, organizationalMeasures,
                improsedMeasures, IEDGenerator()));
        }
        return SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList);
    }

    public static List<IED> IEDGenerator() {
        List<IED> iedList = new ArrayList<>();
        // LINE
        for (int i = 0; i < 2; i++) {
            List<Protections> protectionsList = ProtectionsSet.lineProtectionsSetGeneration();
            IED ied = new IED("W1_" + i, EquipmentType.LINE, protectionsList, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
            iedList.add(ied);
        }
        return iedList;
    }
}