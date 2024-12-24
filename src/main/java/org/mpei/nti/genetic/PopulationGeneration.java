package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationGeneration.EmbeddedMeasuresGeneration;
import org.mpei.nti.substation.substationGeneration.ImprosedMeasuresGeneration;
import org.mpei.nti.substation.substationGeneration.OrganizationalMeasuresGeneration;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresGenearation;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static SubstationMeasures generatePopulation(int minArch, int protectionsCount) {
        List<SubstationMeasuresPerYear> substationMeasuresPerYearList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            SubstationMeasuresPerYear substationMeasuresPerYear = new SubstationMeasuresPerYear();
            substationMeasuresPerYear.setArchitectureType((int) (Math.random() * (3 - minArch) + minArch + 0.001));
            substationMeasuresPerYear.setYearNumber(year);
            substationMeasuresPerYear.setOrganizationalMeasures(organizationalMeasuresGenerator());
            substationMeasuresPerYear.setImprosedMeasures(improsedMeasuresGenerator());
            substationMeasuresPerYear.setEmbeddedMeasuresList(embeddedMeasuresGenerator(protectionsCount));
            substationMeasuresPerYearList.add(substationMeasuresPerYear);
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