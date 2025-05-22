package org.mpei.nti.calculation.modelCalculation.Line;

import org.mpei.nti.calculation.modelCalculation.Coefficients.Failure;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.Coefficients.GeneralCoefficients.*;

public class FailureTriggeringLine {

    public static float failureTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int iedIndex,
                                                     Failure failure) {

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
            failure.setA1(0f);
            failure.setA3(0f);
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            failure.setA1(0f);
            failure.setA3(0f);
            failure.setA23(0f);
            failure.setA31(0f);
            failure.setA41(0f);
        }

        float Psv = failure.getA1() * A2 * DD1 * DD2 + failure.getA3() * A4 * DD3;
        float PotkIED = failure.getA31() * A32 * DD16 * DD23 + failure.getA33() * A34 * DD16 * DD23 + failure.getA35() *
                A36 * DD10 * DD19 * DD23 + failure.getA37() * A38 * DD16 * DD23 + failure.getA29() * A30 * (DD10 *
                DD19 * DD20 * DD18 * DD21 * DD22 + DD10 * DD19 * (1 - DD20) * DD18 * DD21 * DD22 + DD10 * DD19 * DD20 *
                (1 - DD18) * DD21 * DD22 + DD10 * DD19 * DD20 * DD18 * (1 - DD21 * DD22) + DD10 * DD19 * (1 - DD20) *
                (1 - DD18) * DD21 * DD22 + DD10 * DD19 * (1 - DD20) * DD18 * (1 - DD21 * DD22) + DD10 * DD19 * DD20 *
                (1 - DD18) * (1 - DD21 * DD22));
        float PotkPds = failure.getA39() * A40 * DD16;
        float Potkkom = failure.getA41() * A42 * DD16 * DD24 + failure.getA43() * A44 * DD16 * DD24 + failure.getA45() *
                A46 * (DD10 * DD19 * DD24 * DD21 * DD22 + DD10 * DD19 * (1 - DD24) * DD21 * DD22 + DD10 * DD19 * DD24 *
                (1 - DD21 * DD22)) + failure.getA23() * A24 * DD16 * DD17;
        float Pust = failure.getA5() * A6 * DD4 * DD5 + failure.getA7() * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 +
                DD5 * DD6 * (1 - DD7)) + failure.getA9() * A10 * DD7 * DD8 * DD9 + failure.getA11() * A12 * (DD5 * DD10 *
                DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 * (1 - DD11));
        float Psoft = failure.getA13() * A14 * DD9 * DD12 * DD13 * DD14 + failure.getA15() * A16 * DD9 * DD12 * DD13 *
                DD14 + failure.getA17() * A18 * DD9 * DD12 * DD13 * DD15 + failure.getA19() * A20 * DD9 * DD12 * DD13 *
                DD14 * DD15;

        float Pfull = (Psv + PotkIED + PotkPds + Pust + Potkkom + Psoft) / yearsToAttack;

        return (Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa);
    }

}
