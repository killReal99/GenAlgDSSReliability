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
        for (int i = 1; i < 17; i++) {
            if (i < 5) {
                for (int j = 1; j < 3; j++) {
                    List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                    IED ied = new IED("W" + i + "_" + j, EquipmentType.LINE, protectionList, (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), 0.0f);
                    iedList.add(ied);
                }
            } else { //Lines 35 kV and 10 kV
                List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                IED ied = new IED("W" + i + "_1", EquipmentType.LINE, protectionList, (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), 0.0f);
                iedList.add(ied);
            }
        }

        for (int i = 1; i < 7; i++) {
            if (i < 3) {
                for (int j = 1; j < 3; j++) {
                    List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                    IED ied = new IED("B" + i + "_" + j, EquipmentType.BUS, protectionList, (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                            (int) Math.round(Math.random()), 0.0f);
                    iedList.add(ied);
                }
            } else { //Buss 35 kV and 10 kV
                List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                IED ied = new IED("B" + i + "_1", EquipmentType.BUS, protectionList, (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                        (int) Math.round(Math.random()), 0.0f);
                iedList.add(ied);
            }
        }

        for (int i = 1; i < 3; i++) {
            List<Protection> protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
            IED ied = new IED("T" + i + "_1", EquipmentType.TRANSFORMER, protectionList, (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), 0.0f);
            iedList.add(ied);
        }
        return iedList;
    }
}