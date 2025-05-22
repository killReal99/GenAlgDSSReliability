package org.mpei.nti.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Accelerator {

    public static void quickStart(List<SubstationMeasures> population, boolean lanRosseti, boolean iedRosseti,
                                  int fstec) throws IOException {
        String lan = "";
        String ied = "";
        if (lanRosseti) {
            lan = "LAN_";
        }
        if (iedRosseti){
            ied = "IED_";
        }

        String path = "src/main/resources/optimize_" + lan + ied + fstec + ".json";
        File file = new File(path);
        ObjectMapper objectMapper = new ObjectMapper();
        SubstationMeasures substationMeasures = objectMapper.readValue(file, SubstationMeasures.class);
        substationMeasures.setTotalPrice(0f);
        substationMeasures.setDamage(0f);
        substationMeasures.setCapexPrice(0f);
        substationMeasures.setOpexPrice(0f);
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            substationMeasuresPerYear.setTotalPrice(0f);
            substationMeasuresPerYear.setCapexPrice(0f);
            substationMeasuresPerYear.setOpexPrice(0f);
            SubstationMeasuresPerYearGeneration.economicPerYearCalculation(substationMeasuresPerYear);
        }
        population.add(substationMeasures);
    }

}
