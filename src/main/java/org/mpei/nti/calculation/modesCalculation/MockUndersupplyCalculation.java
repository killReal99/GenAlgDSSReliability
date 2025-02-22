package org.mpei.nti.calculation.modesCalculation;

import org.mpei.nti.calculation.modelCalculation.Bus.FailureTriggeringBus;
import org.mpei.nti.calculation.modelCalculation.Bus.FalsePositiveBus;
import org.mpei.nti.calculation.modelCalculation.Bus.OverTriggeringBus;
import org.mpei.nti.calculation.modelCalculation.Line.FailureTriggeringLine;
import org.mpei.nti.calculation.modelCalculation.Line.FalsePositiveLine;
import org.mpei.nti.calculation.modelCalculation.Line.OverTriggeringLine;
import org.mpei.nti.calculation.modelCalculation.Transformer.FailureTriggeringTransformer;
import org.mpei.nti.calculation.modelCalculation.Transformer.FalsePositiveTransformer;
import org.mpei.nti.calculation.modelCalculation.Transformer.OverTriggeringTransformer;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.utils.GenerateSchem;
import org.mpei.nti.utils.Probability;

import java.util.*;

import static org.mpei.nti.calculation.modelCalculation.GeneralCoefficients.*;

public class MockUndersupplyCalculation {

    public static float undersupplyCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, HashMap<Breaker, Float> breakersMap) {
        Probability w1Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability w2Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability w3Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability w4Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability k1Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability k2Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability t1Probability = new Probability(0.0f, 0.0f, 1.0f);
        Probability t2Probability = new Probability(0.0f, 0.0f, 1.0f);
        int iedIndex = 0;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            String[] parse = ied.getNameOfIED().split("_");
            if (ied.getEquipmentTypeName() == EquipmentType.LINE) {
                ied.setFailureTriggering(FailureTriggeringLine.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
                if (parse[0].equals("W1")) {
                    lineProbabilityCalculation(substationMeasuresPerYear, iedIndex, w1Probability);
                } else if (parse[0].equals("W2")) {
                    lineProbabilityCalculation(substationMeasuresPerYear, iedIndex, w2Probability);
                } else if (parse[0].equals("W3")) {
                    lineProbabilityCalculation(substationMeasuresPerYear, iedIndex, w3Probability);
                } else if (parse[0].equals("W4")) {
                    lineProbabilityCalculation(substationMeasuresPerYear, iedIndex, w4Probability);
                } else if (parse[0].equals("W5")) {

                } else if (parse[0].equals("W6")) {

                } else if (parse[0].equals("W7")) {

                } else if (parse[0].equals("W8")) {

                } else if (parse[0].equals("W9")) {

                } else if (parse[0].equals("W10")) {

                } else if (parse[0].equals("W11")) {

                } else if (parse[0].equals("W12")) {

                } else if (parse[0].equals("W13")) {

                } else if (parse[0].equals("W14")) {

                } else if (parse[0].equals("W15")) {

                } else if (parse[0].equals("W16")) {

                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.BUS) {
                ied.setFailureTriggering(FailureTriggeringBus.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
                if (parse[0].equals("B1")) {
                    busProbabilityCalculation(substationMeasuresPerYear, iedIndex, k1Probability);
                } else if (parse[0].equals("B2")) {
                    busProbabilityCalculation(substationMeasuresPerYear, iedIndex, k2Probability);
                } else if (parse[0].equals("B3")) {

                } else if (parse[0].equals("B4")) {

                } else if (parse[0].equals("B5")) {

                } else if (parse[0].equals("B6")) {

                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                ied.setFailureTriggering(FailureTriggeringTransformer.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
                if (parse[0].equals("T1")) {
                    transformerProbabilityCalculation(substationMeasuresPerYear, iedIndex, t1Probability);
                } else if (parse[0].equals("T2")) {
                    transformerProbabilityCalculation(substationMeasuresPerYear, iedIndex, t2Probability);
                }
            }
            iedIndex++;
        }

        float lineUnderSupplyW1 = (undersupplyCalc(w1Probability.getOverTriggerProbability(), 1) +
                undersupplyCalc(w1Probability.getFalsePositiveProbability(), 2) +
                undersupplyCalc(w1Probability.getFailureTriggerProbablility(), 2)) * 99 * 1000;
        float lineUnderSupplyW2 = (undersupplyCalc(w2Probability.getOverTriggerProbability(), 1) +
                undersupplyCalc(w2Probability.getFalsePositiveProbability(), 2) +
                undersupplyCalc(w2Probability.getFailureTriggerProbablility(), 2)) * 99 * 1000;
        float lineUnderSupplyW3 = (undersupplyCalc(w3Probability.getOverTriggerProbability(), 1) +
                undersupplyCalc(w3Probability.getFalsePositiveProbability(), 2) +
                undersupplyCalc(w3Probability.getFailureTriggerProbablility(), 2)) * 99 * 1000;
        float lineUnderSupplyW4 = (undersupplyCalc(w4Probability.getOverTriggerProbability(), 1) +
                undersupplyCalc(w4Probability.getFalsePositiveProbability(), 2) +
                undersupplyCalc(w4Probability.getFailureTriggerProbablility(), 2)) * 99 * 1000;


        qLineProbabilityCalculation(substationMeasuresPerYear.getIedList(), breakersMap);



        List<SchemaStatus> schemaStatusList = new ArrayList<>();
        float undersupply = 0f;
        for (int i = 0; i < schemaStatusList.size(); i++) {

        }


        return lineUnderSupplyW1 + lineUnderSupplyW2 + lineUnderSupplyW3 + lineUnderSupplyW4;
    }

    public static void lineProbabilityCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int index,
                                                  Probability elementProbability) {
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

        float oldFailureTrigger = elementProbability.getFailureTriggerProbablility();
        float newFailureTrigger = FailureTriggeringLine.failureTriggeringCalculation(substationMeasuresPerYear);
        elementProbability.setFailureTriggerProbablility(oldFailureTrigger * newFailureTrigger);
    }

    public static void transformerProbabilityCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int index,
                                                         Probability elementProbability) {
        float dzlOverTriggerProbability = OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, index);
        float mtzOverTriggerProbability = OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, index);
        float tznpOverTriggerProbability = OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, index);
        float dzOverTriggerProbability = OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, index);
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

        float dzlFalsePositiveProbability = FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, index);
        float mtzFalsePositiveProbability = FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, index);
        float tznpFalsePositiveProbability = FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, index);
        float dzFalsePositiveProbability = FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, index);
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

        float oldFailureTrigger = elementProbability.getFailureTriggerProbablility();
        float newFailureTrigger = FailureTriggeringTransformer.failureTriggeringCalculation(substationMeasuresPerYear);
        elementProbability.setFailureTriggerProbablility(oldFailureTrigger * newFailureTrigger);
    }

    public static void busProbabilityCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, int index,
                                                 Probability elementProbability) {
        float dzlOverTriggerProbability = OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, index);
        float mtzOverTriggerProbability = OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, index);
        float tznpOverTriggerProbability = OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, index);
        float dzOverTriggerProbability = OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, index);
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

        float dzlFalsePositiveProbability = FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, index);
        float mtzFalsePositiveProbability = FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, index);
        float tznpFalsePositiveProbability = FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, index);
        float dzFalsePositiveProbability = FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, index);
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

        float oldFailureTrigger = elementProbability.getFailureTriggerProbablility();
        float newFailureTrigger = FailureTriggeringBus.failureTriggeringCalculation(substationMeasuresPerYear);
        elementProbability.setFailureTriggerProbablility(oldFailureTrigger * newFailureTrigger);
    }

    public static float undersupplyCalc(float probablility, int calculationType) {
        float q = (float) -Math.log(1 - probablility);
        float W = Pper * Tvosst;
        float undersupply;
        if (calculationType == 1) {
            undersupply = W * q * qapv;
        } else {
            undersupply = W * q;
        }
        return undersupply;
    }

    public static void qLineProbabilityCalculation(List<IED> iedList, HashMap<Breaker, Float> breakersMap){
        Iterator<HashMap.Entry<Breaker, Float>> iterator = breakersMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Float> entry = iterator.next();
            IED rpaWMain = new IED();
            IED rpaWReserve = new IED();
            IED rpaBmain = new IED();
            IED rpaBReserve = new IED();
            if (Objects.equals(entry.getKey(), "Q1")){
                for (IED ied : iedList){
                    if (Objects.equals(ied.getNameOfIED(), "W1_1")){
                        rpaWMain = ied;
                    }
                    if (Objects.equals(ied.getNameOfIED(), "W1_2")){
                        rpaWReserve = ied;
                    }
                    if (Objects.equals(ied.getNameOfIED(), "B1_1")){
                        rpaWReserve = ied;
                    }
                    if (Objects.equals(ied.getNameOfIED(), "B1_2")){
                        rpaWReserve = ied;
                    }
                }
            }
        }

    }

    public static float twoParamsCalculate(float a, float b) {
        return a + b - a * b;
    }

    public static float threeParamsCalculate(float a, float b, float c) {
        return a + b + c - a * b - a * c - b * c + a * b * c;
    }

    public static float fourParamsCalculate(float a, float b, float c, float d) {
        return a + b + c - a * b - a * c - b * c + a * b * c;
    }

}
