package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int maxArch, boolean lanRosseti, boolean iedRosseti,
                                                        int fstec) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            int architecture = (int) (Math.random() * (maxArch - minArch) + minArch);
            int D19;
            int D20;
            int D21;
            if (lanRosseti) {
                D19 = 1;
                D20 = 1;
                D21 = 1;
            } else {
                D19 = (int) Math.round(Math.random());
                D20 = (int) Math.round(Math.random());
                D21 = (int) Math.round(Math.random());
            }
            ImprosedMeasures improsedMeasures = new ImprosedMeasures(UUID.randomUUID(), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), D19, D20, D21,
                    (int) Math.round(Math.random()));
            OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures(UUID.randomUUID(),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));

            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    architecture, year, organizationalMeasures, improsedMeasures, IEDGenerator(architecture, iedRosseti)));
        }
        return SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList, lanRosseti,
                iedRosseti, fstec);
    }

    public static List<IED> IEDGenerator(int architecture, boolean iedRosseti) {
        List<IED> iedList = new ArrayList<>();
        int D2 = 0;
        int D17 = 0;
        int D4 = 0;
        int D5 = 0;
        int D13 = 0;
        int D14 = 0;
        int D15 = 0;
        if (iedRosseti) {
            D4 = 1;
            D5 = 1;
            D13 = 1;
            D14 = 1;
            D15 = 1;
        }
        //Lines 110 kV
        for (int i = 1; i < 17; i++) {
            if (i < 5) {
                for (int j = 1; j < 3; j++) {
                    if (architecture == 3) {
                        D2 = (int) Math.round(Math.random());
                        D17 = (int) Math.round(Math.random());
                    } else if (architecture == 2) {
                        D17 = (int) Math.round(Math.random());
                    }
                    if (!iedRosseti) {
                        D4 = (int) Math.round(Math.random());
                        D5 = (int) Math.round(Math.random());
                        D13 = (int) Math.round(Math.random());
                        D14 = (int) Math.round(Math.random());
                        D15 = (int) Math.round(Math.random());
                    }
                    List<Protection> protectionList = ProtectionsSet.lineProtectionsGeneration(i);
                    IED ied = new IED(UUID.randomUUID(), "W" + i + "_" + j, EquipmentType.LINE, protectionList,
                            D2, D4, D5, (int) Math.round(Math.random()), (int) Math.round(Math.random()), D13, D14, D15,
                            D17, (int) Math.round(Math.random()), (int) Math.round(Math.random()), 0f);
                    iedList.add(ied);
                }
            } else { //Lines 35 kV and 10 kV
                if (architecture == 3) {
                    D2 = (int) Math.round(Math.random());
                    D17 = (int) Math.round(Math.random());
                } else if (architecture == 2) {
                    D17 = (int) Math.round(Math.random());
                }
                if (!iedRosseti) {
                    D4 = (int) Math.round(Math.random());
                    D5 = (int) Math.round(Math.random());
                    D13 = (int) Math.round(Math.random());
                    D14 = (int) Math.round(Math.random());
                    D15 = (int) Math.round(Math.random());
                }
                List<Protection> protectionList = ProtectionsSet.lineProtectionsGeneration(i);
                IED ied = new IED(UUID.randomUUID(), "W" + i + "_1", EquipmentType.LINE, protectionList,
                        D2, D4, D5, (int) Math.round(Math.random()), (int) Math.round(Math.random()), D13, D14, D15,
                        D17, (int) Math.round(Math.random()), (int) Math.round(Math.random()), 0f);
                iedList.add(ied);
            }
        }

        for (int i = 1; i < 7; i++) {
            if (i < 3) {
                for (int j = 1; j < 3; j++) {
                    if (architecture == 3) {
                        D2 = (int) Math.round(Math.random());
                        D17 = (int) Math.round(Math.random());
                    } else if (architecture == 2) {
                        D17 = (int) Math.round(Math.random());
                    }
                    if (!iedRosseti) {
                        D4 = (int) Math.round(Math.random());
                        D5 = (int) Math.round(Math.random());
                        D13 = (int) Math.round(Math.random());
                        D14 = (int) Math.round(Math.random());
                        D15 = (int) Math.round(Math.random());
                    }
                    List<Protection> protectionList = ProtectionsSet.busProtectionsGeneration(i);
                    IED ied = new IED(UUID.randomUUID(), "B" + i + "_" + j, EquipmentType.BUS, protectionList,
                            D2, D4, D5, (int) Math.round(Math.random()), (int) Math.round(Math.random()), D13, D14, D15,
                            D17, (int) Math.round(Math.random()), (int) Math.round(Math.random()), 0f);
                    iedList.add(ied);
                }
            } else { //Buss 35 kV and 10 kV
                if (architecture == 3) {
                    D2 = (int) Math.round(Math.random());
                    D17 = (int) Math.round(Math.random());
                } else if (architecture == 2) {
                    D17 = (int) Math.round(Math.random());
                }
                if (!iedRosseti) {
                    D4 = (int) Math.round(Math.random());
                    D5 = (int) Math.round(Math.random());
                    D13 = (int) Math.round(Math.random());
                    D14 = (int) Math.round(Math.random());
                    D15 = (int) Math.round(Math.random());
                }
                List<Protection> protectionList = ProtectionsSet.busProtectionsGeneration(i);
                IED ied = new IED(UUID.randomUUID(), "B" + i + "_1", EquipmentType.BUS, protectionList,
                        D2, D4, D5, (int) Math.round(Math.random()), (int) Math.round(Math.random()), D13, D14, D15,
                        D17, (int) Math.round(Math.random()), (int) Math.round(Math.random()), 0f);
                iedList.add(ied);
            }
        }

        for (int i = 1; i < 3; i++) {
            if (architecture == 3) {
                D2 = (int) Math.round(Math.random());
                D17 = (int) Math.round(Math.random());
            } else if (architecture == 2) {
                D17 = (int) Math.round(Math.random());
            }
            if (!iedRosseti) {
                D4 = (int) Math.round(Math.random());
                D5 = (int) Math.round(Math.random());
                D13 = (int) Math.round(Math.random());
                D14 = (int) Math.round(Math.random());
                D15 = (int) Math.round(Math.random());
            }
            List<Protection> protectionList = ProtectionsSet.transformerProtectionsGeneration();
            IED ied = new IED(UUID.randomUUID(), "T" + i + "_1", EquipmentType.TRANSFORMER, protectionList,
                    D2, D4, D5, (int) Math.round(Math.random()), (int) Math.round(Math.random()), D13, D14, D15,
                    D17, (int) Math.round(Math.random()), (int) Math.round(Math.random()), 0f);
            iedList.add(ied);
        }
        return iedList;
    }
}