package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int maxArch, int IEDCount, int protectionsCount) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    ((int) (Math.random() * (maxArch - minArch) + minArch + 0.001)), year,
                    organizationalMeasuresGenerator(), improsedMeasuresGenerator(),
                    IEDGenerator(IEDCount, protectionsCount)));
        }
        return SubstationMeasuresGenearation.substationMeasuresGeneration(substationMeasuresPerYearList);
    }

    public static OrganizationalMeasures organizationalMeasuresGenerator() {
        return OrganizationalMeasuresGeneration.organizationalMeasuresGeneration((int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()));
    }

    public static ImprosedMeasures improsedMeasuresGenerator() {
        return ImprosedMeasuresGeneration.improsedMeasuresGeneration((int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()));
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