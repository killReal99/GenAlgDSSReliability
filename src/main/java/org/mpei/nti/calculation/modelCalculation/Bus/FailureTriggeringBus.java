package org.mpei.nti.calculation.modelCalculation.Bus;

import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.GeneralCoefficients.*;

public class FailureTriggeringBus {

    public static float failureTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int iedIndex) {

        float A1 = 0.05f;
        float A3 = 0.05f;
        float A5 = 0.05f;
        float A7 = 0.05f;
        float A9 = 0.05f;
        float A11 = 0.05f;
        float A13 = 0.05f;
        float A15 = 0.05f;
        float A17 = 0.05f;
        float A19 = 0.05f;
        float A23 = 0.05f;
        float A29 = 0.05f;
        float A31 = 0.05f;
        float A33 = 0.05f;
        float A35 = 0.05f;
        float A37 = 0.05f;
        float A39 = 0.05f;
        float A41 = 0.05f;
        float A43 = 0.05f;
        float A45 = 0.05f;

        float DD1 = (1 - D1 * substationMeasuresPerYear.getOrganizationalMeasures().getD1());
        float DD2 = (1 - D2 * substationMeasuresPerYear.getIedList().get(iedIndex).getD2());
        float DD3 = (1 - D3 * substationMeasuresPerYear.getImprosedMeasures().getD3());
        float DD4 = (1 - D4 * substationMeasuresPerYear.getIedList().get(iedIndex).getD4());
        float DD5 = (1 - D5 * substationMeasuresPerYear.getIedList().get(iedIndex).getD5());
        float DD6 = (1 - D6 * substationMeasuresPerYear.getOrganizationalMeasures().getD6());
        float DD7 = (1 - D7 * substationMeasuresPerYear.getImprosedMeasures().getD7());
        float DD8 = (1 - D8 * substationMeasuresPerYear.getIedList().get(iedIndex).getD8());
        float DD9 = (1 - D9 * substationMeasuresPerYear.getIedList().get(iedIndex).getD9());
        float DD10 = (1 - D10 * substationMeasuresPerYear.getOrganizationalMeasures().getD10());
        float DD11 = (1 - D11 * substationMeasuresPerYear.getImprosedMeasures().getD11());
        float DD12 = (1 - D12 * substationMeasuresPerYear.getOrganizationalMeasures().getD12());
        float DD13 = (1 - D13 * substationMeasuresPerYear.getIedList().get(iedIndex).getD13());
        float DD14 = (1 - D14 * substationMeasuresPerYear.getIedList().get(iedIndex).getD14());
        float DD15 = (1 - D15 * substationMeasuresPerYear.getIedList().get(iedIndex).getD15());
        float DD16 = (1 - D16 * substationMeasuresPerYear.getOrganizationalMeasures().getD16());
        float DD17 = (1 - D17 * substationMeasuresPerYear.getIedList().get(iedIndex).getD17());
        float DD18 = (1 - D18 * substationMeasuresPerYear.getIedList().get(iedIndex).getD18());
        float DD19 = (1 - D19 * substationMeasuresPerYear.getImprosedMeasures().getD19());
        float DD20 = (1 - D20 * substationMeasuresPerYear.getImprosedMeasures().getD20());
        float DD21 = (1 - D21 * substationMeasuresPerYear.getImprosedMeasures().getD21());
        float DD22 = (1 - D22 * substationMeasuresPerYear.getOrganizationalMeasures().getD22());
        float DD23 = (1 - D23 * substationMeasuresPerYear.getIedList().get(iedIndex).getD23());
        float DD24 = (1 - D24 * substationMeasuresPerYear.getImprosedMeasures().getD24());

        if (substationMeasuresPerYear.getArchitectureType() == 2) {
            A1 = 0f;
            A3 = 0f;
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            A1 = 0f;
            A3 = 0f;
            A23 = 0f;
            A31 = 0f;
            A41 = 0f;
        }

        float Psv = A1 * A2 * DD1 * DD2 + A3 * A4 * DD3;
        float PotkIED = A31 * A32 * DD16 * DD23 + A33 * A34 * DD16 * DD23 + A35 * A36 * DD10 * DD19 * DD23 + A37 *
                A38 * DD16 * DD23 + A29 * A30 * (DD10 * DD19 * DD20 * DD18 * DD21 * DD22 + DD10 * DD19 * (1 - DD20) *
                DD18 * DD21 * DD22 + DD10 * DD19 * DD20 * (1 - DD18) * DD21 * DD22 + DD10 * DD19 * DD20 * DD18 *
                (1 - DD21 * DD22) + DD10 * DD19 * (1 - DD20) * (1 - DD18) * DD21 * DD22 + DD10 * DD19 * (1 - DD20) *
                DD18 * (1 - DD21 * DD22) + DD10 * DD19 * DD20 * (1 - DD18) * (1 - DD21 * DD22));
        float PotkPds = A39 * A40 * DD16;
        float Potkkom = A41 * A42 * DD16 * DD24 + A43 * A44 * DD16 * DD24 + A45 * A46 * (DD10 * DD19 * DD24 * DD21 *
                DD22 + DD10 * DD19 * (1 - DD24) * DD21 * DD22 + DD10 * DD19 * DD24 * (1 - DD21 * DD22)) + A23 * A24 *
                DD16 * DD17;
        float Pust = A5 * A6 * DD4 * DD5 + A7 * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 + DD5 * DD6 * (1 - DD7))
                + A9 * A10 * DD7 * DD8 * DD9 + A11 * A12 * (DD5 * DD10 * DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 *
                (1 - DD11));
        float Psoft = A13 * A14 * DD9 * DD12 * DD13 * DD14 + A15 * A16 * DD9 * DD12 * DD13 * DD14 + A17 * A18 * DD9 *
                DD12 * DD13 * DD15 + A19 * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        float Pfull = Psv + PotkIED + PotkPds + Pust + Potkkom + Psoft;

        return (Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa);
    }

}
