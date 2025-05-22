package org.mpei.nti.utils;

import org.mpei.nti.calculation.modesCalculation.MockUndersupplyCalculation;
import org.mpei.nti.substation.substationStructures.*;

import java.util.HashMap;
import java.util.List;

public class WeightCoeffCalculation {

    public static void calculateWeights(List<SubstationMeasures> population, HashMap<Breaker, Probability> breakersMap,
                                        List<IEDImpact> iedImpactList, List<SchemaStatus> schemaStatusList,
                                        WeightCoeff weightCoeff) {

        float damageMax = 0f;
        float damageMin = 0f;
        float capexMax = 0f;
        float capexMin = 0f;
        float opexMax = 0f;
        float opexMin = 0f;

        for (SubstationMeasuresPerYear substationMeasuresPerYear : population.get(0).getSubstationMeasuresPerYear()) {
            damageMax += MockUndersupplyCalculation.undersupplyCalculation(substationMeasuresPerYear, breakersMap,
                    iedImpactList, schemaStatusList);
            capexMin += substationMeasuresPerYear.getCapexPrice();
            opexMin += substationMeasuresPerYear.getOpexPrice();
        }
        for (SubstationMeasuresPerYear substationMeasuresPerYear : population.get(population.size() - 1).getSubstationMeasuresPerYear()) {
            damageMin += MockUndersupplyCalculation.undersupplyCalculation(substationMeasuresPerYear, breakersMap,
                    iedImpactList, schemaStatusList);
            capexMax += substationMeasuresPerYear.getCapexPrice();
            opexMax += substationMeasuresPerYear.getOpexPrice();
        }

        weightCoeff.setMaxDamage(damageMax);
        weightCoeff.setMinOPEX(damageMin);
        weightCoeff.setMaxCAPEX(capexMax);
        weightCoeff.setMinOPEX(capexMin);
        weightCoeff.setMaxOPEX(opexMax);
        weightCoeff.setMinOPEX(opexMin);
    }

}
