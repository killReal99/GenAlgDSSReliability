package org.mpei.nti.calculation.modesCalculation;

import org.mpei.nti.calculation.modelCalculation.FailureTriggeringLine;
import org.mpei.nti.calculation.modelCalculation.FalsePositiveLine;
import org.mpei.nti.calculation.modelCalculation.OverTriggeringLine;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.Protections;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.utils.Probability;

import static org.mpei.nti.calculation.modelCalculation.GeneralCoefficients.*;

public class MockUndersupplyCalculation {

    public static float undersupplyCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        Probability w1Probability = new Probability(0.0f, 0.0f, 1.0f);
        int iedIndex = 0;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            if (ied.getEquipmentTypeName() == EquipmentType.LINE) {
                String[] parse = ied.getNameOfIED().split("_");
                if (parse[0].equals("W1")) {
                    lineProbabilityCalculation(substationMeasuresPerYear, iedIndex, w1Probability);
                }
                iedIndex++;
            }
        }

        float lineUnderSupplyW1 = (undersupplyCalculation(w1Probability.getOverTriggerProbability(), 1) +
            undersupplyCalculation(w1Probability.getFalsePositiveProbability(), 2) +
            undersupplyCalculation(w1Probability.getFailureTriggerProbablility(), 2)) * 99 * 1000;
        return lineUnderSupplyW1;
    }


    public static void lineProbabilityCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int index, Probability elementProbability) {
        float dzlOverTriggerProbability = OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, index);
        float mtzOverTriggerProbability = OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, index);
        float tznpOverTriggerProbability = OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, index);
        float dzOverTriggerProbability = OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, index);
        float oldOverTrigger = elementProbability.getOverTriggerProbability();
        float newOverTrigger = mtzOverTriggerProbability * dzlOverTriggerProbability * dzOverTriggerProbability * tznpOverTriggerProbability +
            (1 - mtzOverTriggerProbability) * dzlOverTriggerProbability * dzOverTriggerProbability * tznpOverTriggerProbability +
            mtzOverTriggerProbability * (1 - dzlOverTriggerProbability) * dzOverTriggerProbability * tznpOverTriggerProbability +
            mtzOverTriggerProbability * dzlOverTriggerProbability * (1 - dzOverTriggerProbability) * tznpOverTriggerProbability +
            mtzOverTriggerProbability * dzlOverTriggerProbability * dzOverTriggerProbability * (1 - tznpOverTriggerProbability) +
            (1 - mtzOverTriggerProbability) * (1 - dzlOverTriggerProbability) * dzOverTriggerProbability * tznpOverTriggerProbability +
            (1 - mtzOverTriggerProbability) * dzlOverTriggerProbability * (1 - dzOverTriggerProbability) * tznpOverTriggerProbability +
            (1 - mtzOverTriggerProbability) * dzlOverTriggerProbability * dzOverTriggerProbability * (1 - tznpOverTriggerProbability) +
            mtzOverTriggerProbability * (1 - dzlOverTriggerProbability) * (1 - dzOverTriggerProbability) * tznpOverTriggerProbability +
            mtzOverTriggerProbability * (1 - dzlOverTriggerProbability) * dzOverTriggerProbability * (1 - tznpOverTriggerProbability) +
            mtzOverTriggerProbability * dzlOverTriggerProbability * (1 - dzOverTriggerProbability) * (1 - tznpOverTriggerProbability) +
            (1 - mtzOverTriggerProbability) * (1 - dzlOverTriggerProbability) * (1 - dzOverTriggerProbability) * tznpOverTriggerProbability +
            (1 - mtzOverTriggerProbability) * (1 - dzlOverTriggerProbability) * dzOverTriggerProbability * (1 - tznpOverTriggerProbability) +
            mtzOverTriggerProbability * (1 - dzlOverTriggerProbability) * (1 - dzOverTriggerProbability) * (1 - tznpOverTriggerProbability);
        elementProbability.setOverTriggerProbability(oldOverTrigger * newOverTrigger + (1 - oldOverTrigger) * newOverTrigger +
            oldOverTrigger * (1 - newOverTrigger));

        float dzlFalsePositiveProbability = FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, index);
        float mtzFalsePositiveProbability = FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, index);
        float tznpFalsePositiveProbability = FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, index);
        float dzFalsePositiveProbability = FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, index);
        float oldFalsePositive = elementProbability.getFalsePositiveProbability();
        float newFalsePositive = mtzFalsePositiveProbability * dzlFalsePositiveProbability * dzFalsePositiveProbability * tznpFalsePositiveProbability +
            (1 - mtzFalsePositiveProbability) * dzlFalsePositiveProbability * dzFalsePositiveProbability * tznpFalsePositiveProbability +
            mtzFalsePositiveProbability * (1 - dzlFalsePositiveProbability) * dzFalsePositiveProbability * tznpFalsePositiveProbability +
            mtzFalsePositiveProbability * dzlFalsePositiveProbability * (1 - dzFalsePositiveProbability) * tznpFalsePositiveProbability +
            mtzFalsePositiveProbability * dzlFalsePositiveProbability * dzFalsePositiveProbability * (1 - tznpFalsePositiveProbability) +
            (1 - mtzFalsePositiveProbability) * (1 - dzlFalsePositiveProbability) * dzFalsePositiveProbability * tznpFalsePositiveProbability +
            (1 - mtzFalsePositiveProbability) * dzlFalsePositiveProbability * (1 - dzFalsePositiveProbability) * tznpFalsePositiveProbability +
            (1 - mtzFalsePositiveProbability) * dzlFalsePositiveProbability * dzFalsePositiveProbability * (1 - tznpFalsePositiveProbability) +
            mtzFalsePositiveProbability * (1 - dzlFalsePositiveProbability) * (1 - dzFalsePositiveProbability) * tznpFalsePositiveProbability +
            mtzFalsePositiveProbability * (1 - dzlFalsePositiveProbability) * dzFalsePositiveProbability * (1 - tznpFalsePositiveProbability) +
            mtzFalsePositiveProbability * dzlFalsePositiveProbability * (1 - dzFalsePositiveProbability) * (1 - tznpFalsePositiveProbability) +
            (1 - mtzFalsePositiveProbability) * (1 - dzlFalsePositiveProbability) * (1 - dzFalsePositiveProbability) * tznpFalsePositiveProbability +
            (1 - mtzFalsePositiveProbability) * (1 - dzlFalsePositiveProbability) * dzFalsePositiveProbability * (1 - tznpFalsePositiveProbability) +
            mtzFalsePositiveProbability * (1 - dzlFalsePositiveProbability) * (1 - dzFalsePositiveProbability) * (1 - tznpFalsePositiveProbability);
        elementProbability.setFalsePositiveProbability(oldFalsePositive * newFalsePositive +
            oldFalsePositive * (1 - newFalsePositive) + (1 - oldFalsePositive) * newFalsePositive);

        float oldFailureTrigger = elementProbability.getFalsePositiveProbability();
        float newFailureTrigger = FailureTriggeringLine.failureTriggeringCalculation(substationMeasuresPerYear, index);
        elementProbability.setFailureTriggerProbablility(oldFailureTrigger * newFailureTrigger);
    }

    public static float undersupplyCalculation(float probablility, int calculationType) {
        float q = ((float) -Math.log(1 - probablility) / 1.0f);
        float W = Pper * Tvosst;
        float undersupply = 0.0f;
        if (calculationType == 1) {
            undersupply = W * q * qapv;
        } else {
            undersupply = W * q;
        }
        return undersupply;
    }

}
