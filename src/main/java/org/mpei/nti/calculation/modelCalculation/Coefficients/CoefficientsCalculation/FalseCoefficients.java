package org.mpei.nti.calculation.modelCalculation.Coefficients.CoefficientsCalculation;

import org.mpei.nti.calculation.modelCalculation.Coefficients.FalseCoeff;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.Coefficients.GeneralCoefficients.attackedIED;

public class FalseCoefficients {

    public static FalseCoeff falseCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {

        int iedCount = substationMeasuresPerYear.getIedList().size();
        int connectionsCount = 26;
        FalseCoeff falseCoeff = new FalseCoeff();

        falseCoeff.setA1(attackedIED / (15f * connectionsCount));
        falseCoeff.setA3(attackedIED / 15f);
        falseCoeff.setA5( attackedIED / (15f * iedCount));
        falseCoeff.setA7(attackedIED / (15f * iedCount));
        falseCoeff.setA9(attackedIED / (15f * iedCount));
        falseCoeff.setA11(attackedIED / (15f * iedCount));
        falseCoeff.setA13(attackedIED / (15f * iedCount));
        falseCoeff.setA15(attackedIED / (15f * iedCount));
        falseCoeff.setA17(attackedIED / (15f * iedCount));
        falseCoeff.setA19(attackedIED / (15f * iedCount));
        falseCoeff.setA21(attackedIED / (15f * connectionsCount));
        falseCoeff.setA23(attackedIED / 15f);
        falseCoeff.setA25(attackedIED / (15f * connectionsCount));
        falseCoeff.setA27(attackedIED / (15f * iedCount));
        falseCoeff.setA29(attackedIED / (15f * iedCount));

        return falseCoeff;
    }
}
