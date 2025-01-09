package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.modelCalculation.GeneralCoefficients.*;

public class OverTriggering {

    static float A1, A3, A5, A7, A9, A11, A13, A15, A17, A19 = 0.1f;

    public static float overTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {

        float DD1 = (1 - D1 * substationMeasuresPerYear.getOrganizationalMeasures().getD1());
        float DD2 = (1 - D2 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD2());
        float DD3 = (1 - D3 * substationMeasuresPerYear.getImprosedMeasures().getD3());
        float DD4 = (1 - D4 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD4());
        float DD5 = (1 - D5 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD5());
        float DD6 = (1 - D6 * substationMeasuresPerYear.getOrganizationalMeasures().getD6());
        float DD7 = (1 - D7 * substationMeasuresPerYear.getImprosedMeasures().getD7());
        float DD8 = (1 - D8 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD8());
        float DD9 = (1 - D9 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD9());
        float DD10 = (1 - D10 * substationMeasuresPerYear.getOrganizationalMeasures().getD10());
        float DD11 = (1 - D11 * substationMeasuresPerYear.getImprosedMeasures().getD11());
        float DD12 = (1 - D12 * substationMeasuresPerYear.getOrganizationalMeasures().getD12());
        float DD13 = (1 - D13 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD13());
        float DD14 = (1 - D14 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD14());
        float DD15 = (1 - D15 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD15());

        if (substationMeasuresPerYear.getArchitectureType() == 2) {
            A1 = 0.0f;
            A2 = 0.0f;
            A3 = 0.0f;
            A4 = 0.0f;
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            A1 = 0.0f;
            A2 = 0.0f;
            A3 = 0.0f;
            A4 = 0.0f;
        }

        float Psv = A1 * A2 * DD1 * DD2 + A3 * A4 * DD3;
        float Pust = A5 * A6 * DD4 * DD5 + A7 * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 + DD5 * DD6 * (1 - DD7)) +
                A9 * A10 * DD7 * DD8 * DD9 + A11 * A12 * (DD5 * DD10 * DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 *
                (1 - DD11));
        float Psoft = A13 * A14 * DD9 * DD12 * DD13 * DD14 + A15 * A16 * DD9 * DD12 * DD13 * DD14 + A17 * A18 * DD9 *
                DD12 * DD13 * DD15 + A19 * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        float Pfull = Psv + Pust + Psoft;

        float P = Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa;

        float q = ((float) -Math.log(1.0f - P) / 1.0f);
        float W = Pper * Tvosst;
        return W * q * qapv;
    }

}
