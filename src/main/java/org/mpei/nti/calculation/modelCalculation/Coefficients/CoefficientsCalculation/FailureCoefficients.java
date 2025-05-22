package org.mpei.nti.calculation.modelCalculation.Coefficients.CoefficientsCalculation;

import org.mpei.nti.calculation.modelCalculation.Coefficients.Failure;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.calculation.modelCalculation.Coefficients.GeneralCoefficients.*;

public class FailureCoefficients {

    public static Failure failureCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {

        int iedCount = substationMeasuresPerYear.getIedList().size();
        int connectionsCount = 26;
        Failure failure = new Failure();

        failure.setA1((float) Math.pow(attackedIED / 20d, (1d / connectionsCount)));
        failure.setA3(attackedIED / 20f);
        failure.setA5((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA7((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA9((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA11((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA13((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA15((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA17((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA19((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA23(attackedIED / 20f);
        failure.setA29((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA31(attackedIED / 20f);
        failure.setA33(attackedIED / 20f);
        failure.setA35(attackedIED / 20f);
        failure.setA37((float) Math.pow(attackedIED / 20d, (1d / iedCount)));
        failure.setA39((float) Math.pow(attackedIED / 20d, (1d / connectionsCount)));
        failure.setA41(attackedIED / 20f);
        failure.setA43(attackedIED / 20f);
        failure.setA45(attackedIED / 20f);

        return failure;
    }

}
