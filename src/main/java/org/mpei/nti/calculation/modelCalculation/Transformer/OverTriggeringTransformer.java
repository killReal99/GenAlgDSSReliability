package org.mpei.nti.calculation.modelCalculation.Transformer;

import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.GeneralCoefficients.*;

public class OverTriggeringTransformer {

    public static float overTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int iedIndex) {

        int iedCount = substationMeasuresPerYear.getIedList().size();

        float A1 = attackedIED / (10f * iedCount);
        float A3 = attackedIED / (10f);
        float A5 = attackedIED / (10f * iedCount);
        float A7 = attackedIED / (10f * iedCount);
        float A9 = attackedIED / (10f * iedCount);
        float A11 = attackedIED / (10f * iedCount);
        float A13 = attackedIED / (10f * iedCount);
        float A15 = attackedIED / (10f * iedCount);
        float A17 = attackedIED / (10f * iedCount);
        float A19 = attackedIED / (10f * iedCount);

        float DD1 = (1 - D1 * substationMeasuresPerYear.getOrganizationalMeasures().getD1()) / iedCount;
        float DD2 = (1 - D2 * substationMeasuresPerYear.getIedList().get(iedIndex).getD2());
        float DD3 = (1 - D3 * substationMeasuresPerYear.getImprosedMeasures().getD3()) / iedCount;
        float DD4 = (1 - D4 * substationMeasuresPerYear.getIedList().get(iedIndex).getD4());
        float DD5 = (1 - D5 * substationMeasuresPerYear.getIedList().get(iedIndex).getD5());
        float DD6 = (1 - D6 * substationMeasuresPerYear.getOrganizationalMeasures().getD6()) / iedCount;
        float DD7 = (1 - D7 * substationMeasuresPerYear.getImprosedMeasures().getD7()) / iedCount;
        float DD8 = (1 - D8 * substationMeasuresPerYear.getIedList().get(iedIndex).getD8());
        float DD9 = (1 - D9 * substationMeasuresPerYear.getIedList().get(iedIndex).getD9());
        float DD10 = (1 - D10 * substationMeasuresPerYear.getOrganizationalMeasures().getD10()) / iedCount;
        float DD11 = (1 - D11 * substationMeasuresPerYear.getImprosedMeasures().getD11()) / iedCount;
        float DD12 = (1 - D12 * substationMeasuresPerYear.getOrganizationalMeasures().getD12()) / iedCount;
        float DD13 = (1 - D13 * substationMeasuresPerYear.getIedList().get(iedIndex).getD13());
        float DD14 = (1 - D14 * substationMeasuresPerYear.getIedList().get(iedIndex).getD14());
        float DD15 = (1 - D15 * substationMeasuresPerYear.getIedList().get(iedIndex).getD15());

        if (substationMeasuresPerYear.getArchitectureType() == 2) {
            A1 = 0f;
            A3 = 0f;
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            A1 = 0f;
            A3 = 0f;
        }

        float Psv = A1 * A2 * DD1 * DD2 + A3 * A4 * DD3;
        float Pust = A5 * A6 * DD4 * DD5 + A7 * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 + DD5 * DD6 * (1 - DD7)) +
            A9 * A10 * DD7 * DD8 * DD9 + A11 * A12 * (DD5 * DD10 * DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 *
            (1 - DD11));
        float Psoft = A13 * A14 * DD9 * DD12 * DD13 * DD14 + A15 * A16 * DD9 * DD12 * DD13 * DD14 + A17 * A18 * DD9 *
            DD12 * DD13 * DD15 + A19 * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        float Pfull = (Psv + Pust + Psoft) / yearsToAttack;

        return Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa;
    }

}
