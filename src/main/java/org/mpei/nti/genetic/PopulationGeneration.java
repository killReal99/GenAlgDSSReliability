package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.*;
import org.mpei.nti.substation.substationStructures.*;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int protectionsCount) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            substationMeasuresPerYearList.add(SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    ((int) (Math.random() * (3 - minArch) + minArch + 0.001)), year,
                    organizationalMeasuresGenerator(), improsedMeasuresGenerator(),
                    embeddedMeasuresGenerator(protectionsCount)));
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

    public static List<EmbeddedMeasures> embeddedMeasuresGenerator(int protectionCount) {
        List<EmbeddedMeasures> embeddedMeasuresList = new ArrayList<>();
        for (int i = 0; i < protectionCount; i++) {
            EmbeddedMeasures embeddedMeasures = EmbeddedMeasuresGeneration.embeddedMeasuresGeneration(
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()), (int) Math.round(Math.random()),
                    (int) Math.round(Math.random()), (int) Math.round(Math.random()));
            embeddedMeasuresList.add(embeddedMeasures);
        }
        return embeddedMeasuresList;
    }

}