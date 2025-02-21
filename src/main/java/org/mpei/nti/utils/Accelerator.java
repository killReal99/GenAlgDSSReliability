package org.mpei.nti.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Accelerator {

    public static void quickStart(List<SubstationMeasures> population) throws IOException {
        File file = new File("src/main/resources/optimize.json");
        ObjectMapper objectMapper = new ObjectMapper();
        SubstationMeasures substationMeasures = objectMapper.readValue(file, SubstationMeasures.class);
        substationMeasures.setTotalPrice(0.0f);
        substationMeasures.setCapexPrice(0.0f);
        substationMeasures.setOpexPrice(0.0f);
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            substationMeasuresPerYear.setTotalPrice(0.0f);
            substationMeasuresPerYear.setCapexPrice(0.0f);
            substationMeasuresPerYear.setOpexPrice(0.0f);
            SubstationMeasuresPerYearGeneration.economicPerYearCalculation(substationMeasuresPerYear,
                    substationMeasuresPerYear.getYearNumber());
        }
        population.add(substationMeasures);
    }

}
