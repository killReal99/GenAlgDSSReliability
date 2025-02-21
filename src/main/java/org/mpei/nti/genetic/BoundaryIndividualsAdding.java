package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.ProtectionsSet;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresGenearation;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.ArrayList;
import java.util.List;

public class BoundaryIndividualsAdding {

    public static void addBoundaryAdding(List<SubstationMeasures> population, int minArch, int maxArch) {
        int archQuantity = maxArch - minArch + 1;
        for (int i = 0; i < archQuantity; i++) {
            for (int j = 0; j < 2; j++) {
                List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
                for (int year = 1; year <= 25; year++) {
                    ImprosedMeasures improsedMeasures = new ImprosedMeasures(j, j, j, j, j, j, j);
                    OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures(j, j, j, j, j, j);

                    substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                            (i + 1), year, organizationalMeasures, improsedMeasures, IEDGenerator(j)));
                }
                population.add(SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList));
            }
        }
    }

    public static List<IED> IEDGenerator(int boundaryValue) {
        List<IED> iedList = new ArrayList<>();
        //Lines 110 kV
        for (int i = 1; i < 17; i++) {
            if (i < 5) {
                for (int j = 1; j < 3; j++) {
                    List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                    IED ied = new IED("W" + i + "_" + j, EquipmentType.LINE, protectionList, boundaryValue,
                            boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue,
                            boundaryValue, boundaryValue, boundaryValue, boundaryValue, 0.0f);
                    iedList.add(ied);
                }
            } else { //Lines 35 kV and 10 kV
                List<Protection> protectionList = ProtectionsSet.lineProtectionsSetGeneration();
                IED ied = new IED("W" + i + "_1", EquipmentType.LINE, protectionList, boundaryValue,
                        boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue,
                        boundaryValue, boundaryValue, boundaryValue, boundaryValue, 0.0f);
                iedList.add(ied);
            }
        }
        for (int i = 1; i < 7; i++) {
            if (i < 3) {
                for (int j = 1; j < 3; j++) {
                    List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                    IED ied = new IED("B" + i + "_" + j, EquipmentType.BUS, protectionList, boundaryValue,
                            boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue,
                            boundaryValue, boundaryValue, boundaryValue, boundaryValue, 0.0f);
                    iedList.add(ied);
                }
            } else { //Buss 35 kV and 10 kV
                List<Protection> protectionList = ProtectionsSet.busProtectionsSetGeneration();
                IED ied = new IED("B" + i + "_1", EquipmentType.BUS, protectionList, boundaryValue,
                        boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue,
                        boundaryValue, boundaryValue, boundaryValue, boundaryValue, 0.0f);
                iedList.add(ied);
            }
        }
        for (int i = 1; i < 3; i++) {
            List<Protection> protectionList = ProtectionsSet.transformerProtectionsSetGeneration();
            IED ied = new IED("T" + i + "_1", EquipmentType.TRANSFORMER, protectionList, boundaryValue,
                    boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue, boundaryValue,
                    boundaryValue, boundaryValue, boundaryValue, boundaryValue, 0.0f);
            iedList.add(ied);
        }
        return iedList;
    }

}
