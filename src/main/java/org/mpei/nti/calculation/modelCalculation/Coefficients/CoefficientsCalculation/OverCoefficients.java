package org.mpei.nti.calculation.modelCalculation.Coefficients.CoefficientsCalculation;

import org.mpei.nti.calculation.modelCalculation.Coefficients.Over;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.Coefficients.GeneralCoefficients.attackedIED;

public class OverCoefficients {

    public static Over overCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {

        int iedCount = substationMeasuresPerYear.getIedList().size();
                Over over = new Over();

        over.setA1(attackedIED / (10f * iedCount));
        over.setA3(attackedIED / 10f);
        over.setA5(attackedIED / (10f * iedCount));
        over.setA7(attackedIED / (10f * iedCount));
        over.setA9(attackedIED / (10f * iedCount));
        over.setA11(attackedIED / (10f * iedCount));
        over.setA13(attackedIED / (10f * iedCount));
        over.setA15(attackedIED / (10f * iedCount));
        over.setA17(attackedIED / (10f * iedCount));
        over.setA19(attackedIED / (10f * iedCount));

        return over;
    }

}
