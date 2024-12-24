package org.mpei.nti.genetic;

import org.mpei.nti.substationGeneration.EmbeddedMeasuresGeneration;
import org.mpei.nti.substationGeneration.ImprosedMeasuresGeneration;
import org.mpei.nti.substationGeneration.OrganizationalMeasuresGeneration;
import org.mpei.nti.substationStructure.EmbeddedMeasures;
import org.mpei.nti.substationStructure.ImprosedMeasures;
import org.mpei.nti.substationStructure.OrganizationalMeasures;
import org.mpei.nti.substationStructure.SubstationMeasures;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {

    public static List<SubstationMeasures> generatePopulation(int minArch, int protectionsCount) {
        List<SubstationMeasures> substationMeasuresList = new ArrayList<>();
        for (int year = 1; year <= 25; year++) {
            SubstationMeasures substationMeasures = new SubstationMeasures();
            substationMeasures.setArchitectureType((int) (Math.random() * (3 - minArch) + minArch + 0.001));
            substationMeasures.setYearNumber(year);
            substationMeasures.setOrganizationalMeasures(organizationalMeasuresGenerator());
            substationMeasures.setImprosedMeasures(improsedMeasuresGenerator());
            substationMeasures.setEmbeddedMeasuresList(embeddedMeasuresGenerator(protectionsCount));
            substationMeasuresList.add(substationMeasures);
        }
        return substationMeasuresList;
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