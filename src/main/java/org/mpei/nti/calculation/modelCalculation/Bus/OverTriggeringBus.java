package org.mpei.nti.calculation.modelCalculation.Bus;

import org.mpei.nti.calculation.modelCalculation.Coefficients.Over;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.Coefficients.GeneralCoefficients.*;

public class OverTriggeringBus {

    public static float overTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int iedIndex,
                                                  Over over) {

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

        if (substationMeasuresPerYear.getArchitectureType() == 2) {
            over.setA1(0f);
            over.setA3(0f);
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            over.setA1(0f);
            over.setA3(0f);
        }

        float Psv = over.getA1() * A2 * DD1 * DD2 + over.getA3() * A4 * DD3;
        float Pust = over.getA5() * A6 * DD4 * DD5 + over.getA7() * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 +
                DD5 * DD6 * (1 - DD7)) + over.getA9() * A10 * DD7 * DD8 * DD9 + over.getA11() * A12 * (DD5 * DD10 *
                DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 * (1 - DD11));
        float Psoft = over.getA13() * A14 * DD9 * DD12 * DD13 * DD14 + over.getA15() * A16 * DD9 * DD12 * DD13 * DD14 +
                over.getA17() * A18 * DD9 * DD12 * DD13 * DD15 + over.getA19() * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        float Pfull = (Psv + Pust + Psoft) / yearsToAttack;

        return Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa;
    }

}
