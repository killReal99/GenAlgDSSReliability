package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.ProtectionsSet;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresGenearation;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoundaryIndividualsAdding {

    public static void addBoundaryAdding(List<SubstationMeasures> population, int minArch, int maxArch,
                                         boolean lanRosseti, boolean iedRosseti, int fstec) {
        int archQuantity = maxArch - minArch + 1;
        for (int i = 0; i < archQuantity; i++) {
            for (int j = 0; j < 2; j++) {
                List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
                for (int year = 1; year <= 25; year++) {
                    int D19;
                    int D20;
                    int D21;
                    if (lanRosseti) {
                        D19 = 1;
                        D20 = 1;
                        D21 = 1;
                    } else {
                        D19 = j;
                        D20 = j;
                        D21 = j;
                    }
                    ImprosedMeasures improsedMeasures = new ImprosedMeasures(UUID.randomUUID(), j, j, j, D19, D20, D21, j);
                    OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                            j, j, j, j, j, j);

                    substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                            (i + 1), year, organizationalMeasures, improsedMeasures, IEDGenerator(j, (i + 1), iedRosseti)));
                }
                population.add(SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList,
                        lanRosseti, iedRosseti, fstec));
            }
        }
    }

    public static List<IED> IEDGenerator(int boundaryValue, int architecture, boolean iedRosseti) {
        List<IED> iedList = new ArrayList<>();
        int D2 = 0;
        int D17 = 0;
        int D4 = 0;
        int D5 = 0;
        int D13 = 0;
        int D14 = 0;
        int D15 = 0;
        if (architecture == 3) {
            D2 = boundaryValue;
            D17 = boundaryValue;
        } else if (architecture == 2) {
            D17 = boundaryValue;
        }
        if (iedRosseti) {
            D4 = 1;
            D5 = 1;
            D13 = 1;
            D14 = 1;
            D15 = 1;
        }
        for (int i = 1; i < 17; i++) { //Lines 110 kV
            if (i < 5) {
                for (int j = 1; j < 3; j++) {
                    if (!iedRosseti) {
                        D4 = (int) Math.round(Math.random());
                        D5 = (int) Math.round(Math.random());
                        D13 = (int) Math.round(Math.random());
                        D14 = (int) Math.round(Math.random());
                        D15 = (int) Math.round(Math.random());
                    }
                    List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                    IED ied = new IED(UUID.randomUUID(), "W" + i + "_" + j, EquipmentType.LINE, protectionList,
                            D2, D4, D5, boundaryValue, boundaryValue, D13, D14, D15, D17, boundaryValue, boundaryValue,
                            0.0f);
                    iedList.add(ied);
                }
            } else { //Lines 35 kV and 10 kV
                if (!iedRosseti) {
                    D4 = (int) Math.round(Math.random());
                    D5 = (int) Math.round(Math.random());
                    D13 = (int) Math.round(Math.random());
                    D14 = (int) Math.round(Math.random());
                    D15 = (int) Math.round(Math.random());
                }
                List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                IED ied = new IED(UUID.randomUUID(), "W" + i + "_1", EquipmentType.LINE, protectionList,
                        D2, D4, D5, boundaryValue, boundaryValue, D13, D14, D15, D17, boundaryValue, boundaryValue,
                        0.0f);
                iedList.add(ied);
            }
        }
        for (int i = 1; i < 7; i++) {
            if (i < 3) {
                for (int j = 1; j < 3; j++) {
                    if (!iedRosseti) {
                        D4 = (int) Math.round(Math.random());
                        D5 = (int) Math.round(Math.random());
                        D13 = (int) Math.round(Math.random());
                        D14 = (int) Math.round(Math.random());
                        D15 = (int) Math.round(Math.random());
                    }
                    List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                    IED ied = new IED(UUID.randomUUID(), "B" + i + "_" + j, EquipmentType.BUS, protectionList,
                            D2, D4, D5, boundaryValue, boundaryValue, D13, D14, D15, D17, boundaryValue, boundaryValue,
                            0.0f);
                    iedList.add(ied);
                }
            } else { //Buss 35 kV and 10 kV
                if (!iedRosseti) {
                    D4 = (int) Math.round(Math.random());
                    D5 = (int) Math.round(Math.random());
                    D13 = (int) Math.round(Math.random());
                    D14 = (int) Math.round(Math.random());
                    D15 = (int) Math.round(Math.random());
                }
                List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                IED ied = new IED(UUID.randomUUID(), "B" + i + "_1", EquipmentType.BUS, protectionList,
                        D2, D4, D5, boundaryValue, boundaryValue, D13, D14, D15, D17, boundaryValue, boundaryValue,
                        0.0f);
                iedList.add(ied);
            }
        }
        for (int i = 1; i < 3; i++) {
            if (!iedRosseti) {
                D4 = (int) Math.round(Math.random());
                D5 = (int) Math.round(Math.random());
                D13 = (int) Math.round(Math.random());
                D14 = (int) Math.round(Math.random());
                D15 = (int) Math.round(Math.random());
            }
            List<Protection> protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
            IED ied = new IED(UUID.randomUUID(), "T" + i + "_1", EquipmentType.TRANSFORMER, protectionList,
                    D2, D4, D5, boundaryValue, boundaryValue, D13, D14, D15, D17, boundaryValue, boundaryValue,
                    0.0f);
            iedList.add(ied);
        }
        return iedList;
    }
}
