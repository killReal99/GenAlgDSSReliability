package org.mpei.nti.genetic;

import org.mpei.nti.substationGeneration.OrganizationalMeasuresGeneration;
import org.mpei.nti.substationStructure.OrganizationalMeasures;
import org.mpei.nti.substationStructure.SubstationMeasures;

import java.util.ArrayList;
import java.util.List;

public class PopulationGeneration {


    public static List<List<SubstationMeasures>> generatePopulation() {
        List<List<SubstationMeasures>> substationMeasuresList = new ArrayList<>();

        for (int year = 1; year <= 25; year++) {
            OrganizationalMeasures organizationalMeasures = organizationalMeasuresGenerator();



        }


        return substationMeasuresList;
    }


    public static OrganizationalMeasures organizationalMeasuresGenerator() {
        OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures();
        for (int d1 = 0; d1 <= 1; d1++) {
            for (int d6 = 0; d6 <= 1; d6++) {
                for (int d10 = 0; d10 <= 1; d10++) {
                    for (int d12 = 0; d12 <= 1; d12++) {
                        for (int d16 = 0; d16 <= 1; d16++) {
                            for (int d22 = 0; d22 <= 1; d22++) {
                                organizationalMeasures = OrganizationalMeasuresGeneration
                                        .organizationalMeasuresGeneration(d1, d6, d10, d12, d16, d22);
                            }
                        }
                    }
                }
            }
        }
        return organizationalMeasures;
    }

}