package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int maxArch, int IEDCount, int protectionsCount) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            ImprosedMeasures improsedMeasures = new ImprosedMeasures((int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));
            OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures((int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()));

            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    ((int) (Math.random() * (maxArch - minArch) + minArch + 0.001)), year, organizationalMeasures,
                    improsedMeasures, IEDGenerator(IEDCount, protectionsCount)));
        }
        return SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList);
    }

    public static List<IED> IEDGenerator(int IEDCount, int protectionCount) {
        List<IED> iedList = new ArrayList<>();
        for (int i = 0; i < IEDCount; i++) {
            IED ied = IEDGeneration.iedGeneration((int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));
            ied.setProtectionsList(protectionsGenerator(protectionCount));
            iedList.add(ied);
        }
        return iedList;
    }

    public static List<Protections> protectionsGenerator(int protectionCount) {
        List<Protections> protectionsList = new ArrayList<>();
        for (int i = 0; i < protectionCount; i++) {
            Protections protections = new Protections();
            protectionsList.add(protections);
        }
        return protectionsList;
    }

}