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
            ImprosedMeasures improsedMeasures = new ImprosedMeasures((int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));
            OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures((int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()));

            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                ((int) (Math.random() * (maxArch - minArch) + minArch)), year, organizationalMeasures,
                improsedMeasures, IEDGenerator()));
        }
        return SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList);
    }

    public static List<IED> IEDGenerator() {
        List<IED> iedList = new ArrayList<>();
        //Lines 110 kV
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 3; j++) {
                List<Protections> protectionsList = ProtectionsSet.lineProtectionsSetGeneration();
                IED ied = new IED("W" + i + "_" + j, EquipmentType.LINE, protectionsList, (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()));
                iedList.add(ied);
            }
        }
        //Lines 35 kV and 10 kV
        for (int i = 5; i < 17; i++) {
            List<Protections> protectionsList = ProtectionsSet.lineProtectionsSetGeneration();
            IED ied = new IED("W" + i + "_" + 1, EquipmentType.LINE, protectionsList, (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()));
            iedList.add(ied);
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                List<Protections> protectionsList = ProtectionsSet.busProtectionsSetGeneration();
                IED ied = new IED("K" + i + "_" + j, EquipmentType.BUS, protectionsList, (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()));
                iedList.add(ied);
            }
        }
        for (int i = 1; i < 3; i++) {
            List<Protections> protectionsList = ProtectionsSet.transformerProtectionsSetGeneration();
            IED ied = new IED("T" + i + "_1", EquipmentType.TRANSFORMER, protectionsList, (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()));
            iedList.add(ied);
        }
        return iedList;
    }
}