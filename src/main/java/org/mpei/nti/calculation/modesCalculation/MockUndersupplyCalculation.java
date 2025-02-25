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
import org.mpei.nti.utils.Probability;

import java.util.*;

import static org.mpei.nti.calculation.modelCalculation.GeneralCoefficients.*;

public class MockUndersupplyCalculation {

    public static float undersupplyCalculation(SubstationMeasuresPerYear substationMeasuresPerYear, HashMap<Breaker,
            Probability> breakersMap, List<IEDImpact> iedImpactList, List<SchemaStatus> schemaStatusList) {
        int iedIndex = 0;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            if (ied.getEquipmentTypeName() == EquipmentType.LINE) {
                ied.setFailureTriggering(FailureTriggeringLine.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.BUS) {
                ied.setFailureTriggering(FailureTriggeringBus.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                ied.setFailureTriggering(FailureTriggeringTransformer.failureTriggeringCalculation(substationMeasuresPerYear));
                for (Protection protection : ied.getProtectionList()) {
                    protection.setOverTriggering(OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            iedIndex++;
        }

        probabilityCalculation(breakersMap, iedImpactList, substationMeasuresPerYear.getIedList());

        float undersupplyOverTrigger = 0f;
        float undersupplyFalsePositive = 0f;
        float undersupplyFailureTrigger = 0f;
        for (SchemaStatus schemaStatus : schemaStatusList) {
            float overTriggeredBreaker = 1f;
            float falsePositivedBreaker = 1f;
            float failureTriggeredBreaker = 1f;
            for (Breaker breaker : schemaStatus.getBreakers()) {
                if (breaker.getPosition() == 1) {
                    for (Map.Entry<Breaker, Probability> breakerProbabilityEntry : breakersMap.entrySet()) {
                        if (breakerProbabilityEntry.getKey().getBreakerName().equals(breaker.getBreakerName())) {
                            overTriggeredBreaker *= breakerProbabilityEntry.getValue().getOverTriggerProbability();
                            falsePositivedBreaker *= breakerProbabilityEntry.getValue().getFalsePositiveProbability();
                            failureTriggeredBreaker *= breakerProbabilityEntry.getValue().getFailureTriggerProbablility();
                        }
                    }
                }
            }
            undersupplyOverTrigger += (float) -Math.log(1 - overTriggeredBreaker) * schemaStatus.getUndersupply() * qapv * 99 * 1000;
            undersupplyFalsePositive += (float) -Math.log(1 - falsePositivedBreaker) * schemaStatus.getUndersupply() * 99 * 1000;
            undersupplyFailureTrigger += (float) -Math.log(1 - failureTriggeredBreaker) * schemaStatus.getUndersupply() * 99 * 1000;
        }
        return undersupplyOverTrigger + undersupplyFalsePositive + undersupplyFailureTrigger;
    }

    public static void probabilityCalculation
            (HashMap<Breaker, Probability> breakersMap, List<IEDImpact> iedImpactList, List<IED> iedList) {
        for (Map.Entry<Breaker, Probability> breakerProbabilityEntry : breakersMap.entrySet()) {
            IED rpaCurrentMain = new IED();
            IED rpaCurrentReserve = new IED();
            IED rpaNeighborsMain = new IED();
            IED rpaNeighborsReserve = new IED();
            int iedQuantity = 0;
            for (IEDImpact iedImpact : iedImpactList) {
                if (breakerProbabilityEntry.getKey().equals(iedImpact.getBreaker())) { // Допущение о том, что если 2 ИЭУ в мапе, то оба относятся к текущему оборудованию (не влияет на число в ответе)
                    for (IED ied : iedList) {
                        if (iedImpact.getIedList().get(0).equals(ied.getNameOfIED())) {
                            rpaCurrentMain = ied;
                            iedQuantity++;
                        }
                        if (iedImpact.getIedList().size() >= 2 && iedImpact.getIedList().get(1).equals(ied.getNameOfIED())) {
                            rpaCurrentReserve = ied;
                            iedQuantity++;
                        }
                        if (iedImpact.getIedList().size() >= 3 && iedImpact.getIedList().get(2).equals(ied.getNameOfIED())) {
                            rpaNeighborsMain = ied;
                            iedQuantity++;
                        }
                        if (iedImpact.getIedList().size() == 4 && iedImpact.getIedList().get(3).equals(ied.getNameOfIED())) {
                            rpaNeighborsReserve = ied;
                            iedQuantity++;
                        }
                    }
                }
            }
            failureProbabilityCalculation(breakerProbabilityEntry, rpaCurrentMain, rpaCurrentReserve, rpaNeighborsMain,
                    rpaNeighborsReserve, iedQuantity);
            overTriggerAndFalsePositiveProbabilityCalculation(breakerProbabilityEntry, rpaCurrentMain, rpaCurrentReserve, rpaNeighborsMain,
                    rpaNeighborsReserve);
        }
    }

    public static void failureProbabilityCalculation(Map.Entry<Breaker, Probability> breakersMapEntry,
                                                     IED rpaCurrentMain, IED rpaCurrentReserve, IED rpaNeighborsMain,
                                                     IED rpaNeighborsReserve, int iedQuantity) {
        if (iedQuantity == 1) {
            breakersMapEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering());
        } else if (iedQuantity == 2) {
            breakersMapEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                    rpaCurrentReserve.getFailureTriggering());
        } else if (iedQuantity == 3) {
            breakersMapEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                    rpaCurrentReserve.getFailureTriggering() * rpaNeighborsMain.getFailureTriggering());
        } else {
            breakersMapEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                    rpaCurrentReserve.getFailureTriggering() * rpaNeighborsMain.getFailureTriggering() *
                    rpaNeighborsReserve.getFailureTriggering());
        }
    }

    public static void overTriggerAndFalsePositiveProbabilityCalculation(Map.Entry<Breaker, Probability> breakersMapEntry,
                                                                         IED rpaCurrentMain, IED rpaCurrentReserve,
                                                                         IED rpaNeighborsMain, IED rpaNeighborsReserve) {
        List<Protection> protectionList = new ArrayList<>();
        if (rpaCurrentMain.getProtectionList() != null) {
            protectionList.addAll(rpaCurrentMain.getProtectionList());
        }
        if (rpaCurrentReserve.getProtectionList() != null) {
            protectionList.addAll(rpaCurrentReserve.getProtectionList());
        }
        if (rpaNeighborsMain.getProtectionList() != null) {
            protectionList.addAll(rpaNeighborsMain.getProtectionList());
        }
        if (rpaNeighborsReserve.getProtectionList() != null) {
            protectionList.addAll(rpaNeighborsReserve.getProtectionList());
        }

        if (protectionList.size() == 1) {
            breakersMapEntry.getValue().setFalsePositiveProbability(protectionList.get(0).getFalsePositive());
            breakersMapEntry.getValue().setOverTriggerProbability(protectionList.get(0).getOverTriggering());
        } else if (protectionList.size() == 2) {
            breakersMapEntry.getValue().setFalsePositiveProbability(twoParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(twoParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering()));
        } else if (protectionList.size() == 3) {
            breakersMapEntry.getValue().setFalsePositiveProbability(threeParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(threeParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering()));
        } else if (protectionList.size() == 4) {
            breakersMapEntry.getValue().setFalsePositiveProbability(fourParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(fourParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getFalsePositive()));
        } else if (protectionList.size() == 5) {
            breakersMapEntry.getValue().setFalsePositiveProbability(fiveParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(fiveParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering()));
        } else if (protectionList.size() == 6) {
            breakersMapEntry.getValue().setFalsePositiveProbability(sixParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(sixParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering()));
        } else if (protectionList.size() == 7) {
            breakersMapEntry.getValue().setFalsePositiveProbability(sevenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(sevenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering()));
        } else if (protectionList.size() == 8) {
            breakersMapEntry.getValue().setFalsePositiveProbability(eightParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(eightParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering()));
        } else if (protectionList.size() == 9) {
            breakersMapEntry.getValue().setFalsePositiveProbability(nineParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(nineParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive()));
        } else if (protectionList.size() == 10) {
            breakersMapEntry.getValue().setFalsePositiveProbability(tenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(tenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive()));
        } else if (protectionList.size() == 11) {
            breakersMapEntry.getValue().setFalsePositiveProbability(elevenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(elevenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive()));
        } else if (protectionList.size() == 12) {
            breakersMapEntry.getValue().setFalsePositiveProbability(twelveParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(twelveParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive()));
        } else if (protectionList.size() == 13) {
            breakersMapEntry.getValue().setFalsePositiveProbability(thirteenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(thirteenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive()));
        } else if (protectionList.size() == 14) {
            breakersMapEntry.getValue().setFalsePositiveProbability(fourteenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                    protectionList.get(13).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(fourteenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                    protectionList.get(13).getFalsePositive()));
        } else if (protectionList.size() == 15) {
            breakersMapEntry.getValue().setFalsePositiveProbability(fifteenParamsCalculate(protectionList.get(0).getFalsePositive(),
                    protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                    protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                    protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                    protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                    protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive()));
            breakersMapEntry.getValue().setOverTriggerProbability(fifteenParamsCalculate(protectionList.get(0).getOverTriggering(),
                    protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                    protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                    protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                    protectionList.get(7).getOverTriggering(), protectionList.get(8).getFalsePositive(),
                    protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                    protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                    protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive()));
        }
    }

    public static float twoParamsCalculate(float a, float b) {
        return a + b - a * b;
    }

    public static float threeParamsCalculate(float a, float b, float c) {
        return a + b + c - (a * b + a * c + b * c) + a * b * c;
    }

    public static float fourParamsCalculate(float a, float b, float c, float d) {
        return a + b + c + d - (a * b + a * c + a * d + b * c + b * d + c * d) + (a * b * c + a * b * d + a * c * d +
                b * c * d) - a * b * c * d;
    }

    public static float fiveParamsCalculate(float a, float b, float c, float d, float e) {
        return a + b + c + d + e - (a * b + a * c + a * d + a * e + b * c + b * d + b * e + c * d + c * e + d * e) +
                (a * b * c + a * b * d + a * b * e + a * c * d + a * c * e + a * d * e + b * c * d + b * c * e +
                        b * d * e + c * d * e) - a * b * c * d * e - (a * b * c * d + a * b * c * e + a * b * d * e +
                a * c * d * e + b * c * d * e) + a * b * c * d * e;
    }

    public static float sixParamsCalculate(float a, float b, float c, float d, float e, float f) {
        return a + b + c + d + e + f - (a * b + a * c + a * d + a * e + a * f + b * c + b * d + b * e + b * f + c * d +
                c * e + c * f + d * e + d * f + e * f) + (a * b * c + a * b * d + a * b * e + a * b * f + a * c * d +
                a * c * e + a * c * f + a * d * e + a * e * f + b * c * d + b * c * e + b * c * f + b * d * e +
                b * e * f + c * d * f + c * e * f + d * e * f) - (a * b * c * d + a * b * c * e + a * b * c * f +
                a * b * d * e + a * b * e * f + a * c * d * f + a * c * e * f + a * d * e * f + b * c * d * f +
                b * c * e * f + b * d * e * f + c * d * e * f) + (a * b * c * d * e + a * b * c * d * f +
                a * b * c * e * f + a * b * d * e * f + a * c * d * e * f + b * c * d * e * f) - a * b * c * d * e * f;
    }

    public static float sevenParamsCalculate(float a, float b, float c, float d, float e, float f, float g) {
        return a + b + c + d + e + f + g - (a * b + a * c + a * d + a * e + a * f + a * g + b * c + b * d + b * e +
                b * f + b * g + c * d + c * e + c * f + c * g + d * e + d * f + d * g + e * f + e * g + f * g) +
                (a * b * c + a * b * d + a * b * e + a * b * f + a * b * g + a * c * d + a * c * e + a * c * f +
                        a * c * g + a * d * e + a * d * f + a * d * g + a * e * f + a * e * g + a * f * g + b * c * d +
                        b * c * e + b * c * f + b * c * g + b * d * e + b * d * f + b * d * g + b * e * f + b * e * g +
                        b * f * g + c * d * e + c * d * f + c * d * g + c * e * f + c * e * g + c * f * g + d * e * f +
                        d * e * g + d * f * g + e * f * g) - (a * b * c * d + a * b * c * e + a * b * c * f +
                a * b * c * g + a * b * d * e + a * b * d * f + a * b * d * g + a * b * e * f + a * b * e * g +
                a * b * f * g + a * c * d * e + a * c * d * f + a * c * d * g + a * c * e * f + a * c * e * g +
                a * c * f * g + a * d * e * f + a * d * e * g + a * d * f * g + a * e * f * g + b * c * d * e +
                b * c * d * f + b * c * d * g + b * c * e * f + b * c * e * g + b * c * f * g + b * d * e * f +
                b * d * e * g + b * d * f * g + b * e * f * g + c * d * e * f + c * d * e * g + c * d * f * g +
                c * e * f * g + d * e * f * g) + (a * b * c * d * e + a * b * c * d * f + a * b * c * d * e +
                a * b * c * e * f + a * b * c * f * g + a * b * d * e * f + a * b * d * e * g + a * b * d * f * g +
                a * b * e * f * g + a * c * d * e * f + a * c * d * e * g + a * c * d * f * g + a * c * e * f * g +
                a * d * e * f * g + b * c * d * e * f + b * c * d * e * g + b * c * d * f * g + b * c * e * f * g +
                c * d * e * f * g) - a * b * c * d * e * f * g;
    }

    public static float eightParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h) {
        return a + b + c + d + e + f + g + h - (a * b + a * c + a * d + a * e + a * f + a * g + a * h + b * c +
                b * d + b * e + b * f + b * g + b * h + c * d + c * e + c * f + c * g + c * h + d * e + d * f + d * g +
                d * h + e * f + e * g + e * h + f * g + f * h + g * h) + (a * b * c + a * b * d + a * b * e +
                a * b * f + a * b * g + a * b * h + a * c * d + a * c * e + a * c * f + a * c * g + a * c * h +
                a * d * e + a * d * f + a * d * g + a * d * h + a * e * f + a * e * g + a * e * h + a * f * g +
                a * f * h + a * g * h + b * c * d + b * c * e + b * c * f + b * c * g + b * c * h + b * d * e +
                b * d * f + b * d * g + b * d * h + b * e * f + b * e * g + b * e * h + b * f * g + b * f * h +
                b * g * h + c * d * e + c * d * f + c * d * g + c * d * h + c * e * f + c * e * g + c * e * h +
                c * f * g + c * f * h + c * g * h + d * e * f + d * e * g + d * e * h + d * f * g + d * f * h +
                d * g * h + e * f * g + e * f * h + e * g * h + f * g * h) - (a * b * c * d + a * b * c * e +
                a * b * c * f + a * b * c * g + a * b * c * h + a * b * d * e + a * b * d * f + a * b * d * g +
                a * b * d * h + a * b * e * f + a * b * e * g + a * b * e * h + a * b * f * g + a * b * f * h +
                a * b * g * h + a * c * d * e + a * c * d * f + a * c * d * g + a * c * d * h + a * c * e * f +
                a * c * e * g + a * c * e * h + a * c * f * g + a * c * f * h + a * c * g * h + a * d * e * f +
                a * d * e * g + a * d * e * h + a * d * f * g + a * d * f * h + a * d * g * h + a * e * f * g +
                a * e * f * h + a * e * g * h + a * f * g * h + b * c * d * e + b * c * d * f + b * c * d * g +
                b * c * d * h + b * c * e * f + b * c * e * g + b * c * e * h + b * c * f * g + b * c * f * h +
                b * c * g * h + b * d * e * f + b * d * e * g + b * d * e * h + b * d * f * g + b * d * f * h +
                b * d * g * h + b * e * f * g + b * e * f * h + b * e * g * h + b * f * g * h + c * d * e * f +
                c * d * e * g + c * d * e * h + c * d * f * g + c * d * f * h + c * d * g * h + c * e * f * g +
                c * e * f * h + c * e * g * h + c * f * g * h + d * e * f * g + d * e * f * h + d * e * g * h +
                d * f * g * h + e * f * g * h) + (a * b * c * d * e + a * b * c * d * f + a * b * c * d * g +
                a * b * c * d * h + a * b * c * e * f + a * b * c * e * g + a * b * c * e * h + a * b * c * f * g +
                a * b * c * f * h + a * b * c * g * h + a * b * d * e * f + a * b * d * e * g + a * b * d * e * h +
                a * b * d * f * g + a * b * d * f * h + a * b * d * g * h + a * b * e * f * g + a * b * e * f * h +
                a * b * e * g * h + a * b * f * g * h + a * c * d * e * f + a * c * d * e * g + a * c * d * e * h +
                a * c * d * f * g + a * c * d * f * h + a * c * d * g * h + a * c * e * f * g + a * c * e * f * h +
                a * c * e * g * h + a * c * f * g * h + a * d * e * f * g + a * d * e * f * h + a * d * e * g * h +
                a * d * f * g * h + a * e * f * g * h + b * c * d * e * f + b * c * d * e * g + b * c * d * e * h +
                b * c * d * f * g + b * c * d * f * h + b * c * d * g * h + b * c * e * f * g + b * c * e * f * h +
                b * c * e * g * h + b * c * f * g * h + b * d * e * f * g + b * d * e * f * h + b * d * e * g * h +
                b * d * f * g * h + b * e * f * g * h + c * d * e * f * g + c * d * e * f * h + c * d * e * g * h +
                c * d * f * g * h + c * e * f * g * h + d * e * f * g * h) - (a * b * c * d * e * f +
                a * b * c * d * e * g + a * b * c * d * e * h + a * b * c * d * f * g + a * b * c * d * f * h +
                a * b * c * d * g * h + a * b * c * e * f * g + a * b * c * e * f * h + a * b * c * e * g * h +
                a * b * c * f * g * h + a * b * d * e * f * g + a * b * d * e * f * h + a * b * d * e * g * h +
                a * b * d * f * g * h + a * b * e * f * g * h + a * c * d * e * f * g + a * c * d * e * f * h +
                a * c * d * e * g * h + a * c * d * f * g * h + a * c * e * f * g * h + a * d * e * f * g * h +
                b * c * d * e * f * g + b * c * d * e * f * h + b * c * d * e * g * h + b * c * d * f * g * h +
                b * c * e * f * g * h + b * d * e * f * g * h + c * d * e * f * g * h) + (a * b * c * d * e * f * g +
                a * b * c * d * e * f * h + a * b * c * d * e * g * h + a * b * c * d * f * g * h +
                a * b * c * e * f * g * h + a * b * d * e * f * g * h + a * c * d * e * f * g * h +
                b * c * d * e * f * g * h) - a * b * c * d * e * f * g * h;
    }

    public static float nineParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                            float i) {
        return a + b + c + d + e + f + g + h + i - (a * b + a * c + a * d + a * e + a * f + a * g + a * h + a * i +
                b * c + b * d + b * e + b * f + b * g + b * h + b * i + c * d + c * e + c * f + c * g + c * h + c * i +
                d * e + d * f + d * g + d * h + d * i + e * f + e * g + e * h + e * i + f * g + f * h + f * i + g * h +
                g * i + h * i) + (a * b * c + a * b * d + a * b * e + a * b * f + a * b * g + a * b * h + a * b * i +
                a * c * d + a * c * e + a * c * f + a * c * g + a * c * h + a * c * i + a * d * e + a * d * f +
                a * d * g + a * d * h + a * d * i + a * e * f + a * e * g + a * e * h + a * e * i + a * f * g +
                a * f * h + a * f * i + a * g * h + a * g * i + a * h * i + b * c * d + b * c * e + b * c * f +
                b * c * g + b * c * h + b * c * i + b * d * e + b * d * f + b * d * g + b * d * h + b * d * i +
                b * e * f + b * e * g + b * e * h + b * e * i + b * f * g + b * f * h + b * f * i + b * g * h +
                b * g * i + b * h * i + c * d * e + c * d * f + c * d * g + c * d * h + c * d * i + c * e * f +
                c * e * g + c * e * h + c * e * i + c * f * g + c * f * h + c * f * i + c * g * h + c * g * i +
                c * h * i + d * e * f + d * e * g + d * e * h + d * e * i + d * f * g + d * f * h + d * f * i +
                d * g * h + d * g * i + d * h * i + e * f * g + e * f * h + e * f * i + e * g * h + e * g * i +
                e * h * i + f * g * h + f * g * i + f * h * i + g * h * i) - (a * b * c * d + a * b * c * e +
                a * b * c * f + a * b * c * g + a * b * c * h + a * b * c * i + a * b * d * e + a * b * d * f +
                a * b * d * g + a * b * d * h + a * b * d * i + a * b * e * f + a * b * e * g + a * b * e * h +
                a * b * e * i + a * b * f * g + a * b * f * h + a * b * f * i + a * b * g * h + a * b * g * i +
                a * b * h * i + a * c * d * e + a * c * d * f + a * c * d * g + a * c * d * h + a * c * d * i +
                a * c * e * f + a * c * e * g + a * c * e * h + a * c * e * i + a * c * f * g + a * c * f * h +
                a * c * f * i + a * c * g * h + a * c * g * i + a * c * h * i + a * d * e * f + a * d * e * g +
                a * d * e * h + a * d * e * i + a * d * f * g + a * d * f * h + a * d * f * i + a * d * g * h +
                a * d * g * i + a * d * h * i + a * e * f * g + a * e * f * h + a * e * f * i + a * e * g * h +
                a * e * g * i + a * e * h * i + a * f * g * h + a * f * g * i + a * f * h * i + a * g * h * i +
                b * c * d * e + b * c * d * f + b * c * d * g + b * c * d * h + b * c * d * i + b * c * e * f +
                b * c * e * g + b * c * e * h + b * c * e * i + b * c * f * g + b * c * f * h + b * c * f * i +
                b * c * g * h + b * c * g * i + b * c * h * i + b * d * e * f + b * d * e * g + b * d * e * h +
                b * d * e * i + b * d * f * g + b * d * f * h + b * d * f * i + b * d * g * h + b * d * g * i +
                b * d * h * i + b * e * f * g + b * e * f * h + b * e * f * i + b * e * g * h + b * e * g * i +
                b * e * h * i + b * f * g * h + b * f * g * i + b * f * h * i + b * g * h * i + c * d * e * f +
                c * d * e * g + c * d * e * h + c * d * e * i + c * d * f * g + c * d * f * h + c * d * f * i +
                c * d * g * h + c * d * g * i + c * d * h * i + c * e * f * g + c * e * f * h + c * e * f * i +
                c * e * g * h + c * e * g * i + c * e * h * i + c * f * g * h + c * f * g * i + c * f * h * i +
                c * g * h * i + d * e * f * g + d * e * f * h + d * e * f * i + d * e * g * h + d * e * g * i +
                d * e * h * i + d * f * g * h + d * f * g * i + d * f * h * i + d * g * h * i + e * f * g * h +
                e * f * g * i + e * f * h * i + e * g * h * i + f * g * h * i) + (a * b * c * d * e +
                a * b * c * d * f + a * b * c * d * g + a * b * c * d * h + a * b * c * d * i + a * b * c * e * f +
                a * b * c * e * g + a * b * c * e * h + a * b * c * e * i + a * b * c * f * g + a * b * c * f * h +
                a * b * c * f * i + a * b * c * g * h + a * b * c * g * i + a * b * c * h * i + a * b * d * e * f +
                a * b * d * e * g + a * b * d * e * h + a * b * d * e * i + a * b * d * f * g + a * b * d * f * h +
                a * b * d * f * i + a * b * d * g * h + a * b * d * g * i + a * b * d * h * i + a * b * e * f * g +
                a * b * e * f * h + a * b * e * f * i + a * b * e * g * h + a * b * e * g * i + a * b * e * h * i +
                a * b * f * g * h + a * b * f * g * i + a * b * f * h * i + a * b * g * h * i + a * c * d * e * f +
                a * c * d * e * g + a * c * d * e * h + a * c * d * e * i + a * c * d * f * g + a * c * d * f * h +
                a * c * d * f * i + a * c * d * g * h + a * c * d * g * i + a * c * d * h * i + a * c * e * f * g +
                a * c * e * f * h + a * c * e * f * i + a * c * e * g * h + a * c * e * g * i + a * c * e * h * i +
                a * c * f * g * h + a * c * f * g * i + a * c * f * h * i + a * c * g * h * i + a * d * e * f * g +
                a * d * e * f * h + a * d * e * f * i + a * d * e * g * h + a * d * e * g * i + a * d * e * h * i +
                a * d * f * g * h + a * d * f * g * i + a * d * f * h * i + a * d * g * h * i + a * e * f * g * h +
                a * e * f * g * i + a * e * f * h * i + a * e * g * h * i + a * f * g * h * i + b * c * d * e * f +
                b * c * d * e * g + b * c * d * e * h + b * c * d * e * i + b * c * d * f * g + b * c * d * f * h +
                b * c * d * f * i + b * c * d * g * h + b * c * d * g * i + b * c * d * h * i + b * c * e * f * g +
                b * c * e * f * h + b * c * e * f * i + b * c * e * g * h + b * c * e * g * i + b * c * e * h * i +
                b * c * f * g * h + b * c * f * g * i + b * c * f * h * i + b * c * g * h * i + b * d * e * f * g +
                b * d * e * f * h + b * d * e * f * i + b * d * e * g * h + b * d * e * g * i + b * d * e * h * i +
                b * d * f * g * h + b * d * f * g * i + b * d * f * h * i + b * d * g * h * i + b * e * f * g * h +
                b * e * f * g * i + b * e * f * h * i + b * e * g * h * i + b * f * g * h * i + c * d * e * f * g +
                c * d * e * f * h + c * d * e * f * i + c * d * e * g * h + c * d * e * g * i + c * d * e * h * i +
                c * d * f * g * h + c * d * f * g * i + c * d * f * h * i + c * d * g * h * i + c * e * f * g * h +
                c * e * f * g * i + c * e * f * h * i + c * e * g * h * i + c * f * g * h * i + d * e * f * g * h +
                d * e * f * g * i + d * e * f * h * i + d * e * g * h * i + d * f * g * h * i + e * f * g * h * i) -
                (a * b * c * d * e * f + a * b * c * d * e * g + a * b * c * d * e * h + a * b * c * d * e * i +
                        a * b * c * d * f * g + a * b * c * d * f * h + a * b * c * d * f * i + a * b * c * d * g * h +
                        a * b * c * d * g * i + a * b * c * d * h * i + a * b * c * e * f * g + a * b * c * e * f * h +
                        a * b * c * e * f * i + a * b * c * e * g * h + a * b * c * e * g * i + a * b * c * e * h * i +
                        a * b * c * f * g * h + a * b * c * f * g * i + a * b * c * f * h * i + a * b * c * g * h * i +
                        a * b * d * e * f * g + a * b * d * e * f * h + a * b * d * e * f * i + a * b * d * e * g * h +
                        a * b * d * e * g * i + a * b * d * e * h * i + a * b * d * f * g * h + a * b * d * f * g * i +
                        a * b * d * f * h * i + a * b * d * g * h * i + a * b * e * f * g * h + a * b * e * f * g * i +
                        a * b * e * f * h * i + a * b * e * g * h * i + a * b * f * g * h * i + a * c * d * e * f * g +
                        a * c * d * e * f * h + a * c * d * e * f * i + a * c * d * e * g * h + a * c * d * e * g * i +
                        a * c * d * e * h * i + a * c * d * f * g * h + a * c * d * f * g * i + a * c * d * f * h * i +
                        a * c * d * g * h * i + a * c * e * f * g * h + a * c * e * f * g * i + a * c * e * f * h * i +
                        a * c * e * g * h * i + a * c * f * g * h * i + a * d * e * f * g * h + a * d * e * f * g * i +
                        a * d * e * f * h * i + a * d * e * g * h * i + a * d * f * g * h * i + a * e * f * g * h * i +
                        b * c * d * e * f * g + b * c * d * e * f * h + b * c * d * e * f * i + b * c * d * e * g * h +
                        b * c * d * e * g * i + b * c * d * e * h * i + b * c * d * f * g * h + b * c * d * f * g * i +
                        b * c * d * f * h * i + b * c * d * g * h * i + b * c * e * f * g * h + b * c * e * f * g * i +
                        b * c * e * f * h * i + b * c * e * g * h * i + b * c * f * g * h * i + b * d * e * f * g * h +
                        b * d * e * f * g * i + b * d * e * f * h * i + b * d * e * g * h * i + b * d * f * g * h * i +
                        b * e * f * g * h * i + c * d * e * f * g * h + c * d * e * f * g * i + c * d * e * f * h * i +
                        c * d * e * g * h * i + c * d * f * g * h * i + c * e * f * g * h * i + d * e * f * g * h * i) +
                (a * b * c * d * e * f * g + a * b * c * d * e * f * h + a * b * c * d * e * f * i +
                        a * b * c * d * e * g * h + a * b * c * d * e * g * i + a * b * c * d * e * h * i +
                        a * b * c * d * f * g * h + a * b * c * d * f * g * i + a * b * c * d * f * h * i +
                        a * b * c * d * g * h * i + a * b * c * e * f * g * h + a * b * c * e * f * g * i +
                        a * b * c * e * f * h * i + a * b * c * e * g * h * i + a * b * c * f * g * h * i +
                        a * b * d * e * f * g * h + a * b * d * e * f * g * i + a * b * d * e * f * h * i +
                        a * b * d * e * g * h * i + a * b * d * f * g * h * i + a * b * e * f * g * h * i +
                        a * c * d * e * f * g * h + a * c * d * e * f * g * i + a * c * d * e * f * h * i +
                        a * c * d * e * g * h * i + a * c * d * f * g * h * i + a * c * e * f * g * h * i +
                        a * d * e * f * g * h * i + b * c * d * e * f * g * h + b * c * d * e * f * g * i +
                        b * c * d * e * f * h * i + b * c * d * e * g * h * i + b * c * d * f * g * h * i +
                        b * c * e * f * g * h * i + b * d * e * f * g * h * i + c * d * e * f * g * h * i) -
                (a * b * c * d * e * f * g * h + a * b * c * d * e * f * g * i + a * b * c * d * e * f * h * i +
                        a * b * c * d * e * g * h * i + a * b * c * d * f * g * h * i + a * b * c * e * f * g * h * i +
                        a * b * d * e * f * g * h * i + a * c * d * e * f * g * h * i + b * c * d * e * f * g * h * i) +
                a * b * c * d * e * f * g * h * i;
    }

    public static float tenParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                           float i, float j) {
        return (float) Math.pow(0.1, 8.0);
    }

    public static float elevenParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                              float i, float j, float k) {
        return (float) Math.pow(0.1, 6.0);
    }

    public static float twelveParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                              float i, float j, float k, float l) {
        return (float) Math.pow(0.1, 6.0);
    }

    public static float thirteenParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                                float i, float j, float k, float l, float m) {
        return (float) Math.pow(0.1, 6.0);
    }

    public static float fourteenParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                                float i, float j, float k, float l, float m, float n) {
        return (float) Math.pow(0.1, 6.0);
    }

    public static float fifteenParamsCalculate(float a, float b, float c, float d, float e, float f, float g, float h,
                                               float i, float j, float k, float l, float m, float n,
                                               float p) {
        return (float) Math.pow(0.1, 6.0);
    }


}
