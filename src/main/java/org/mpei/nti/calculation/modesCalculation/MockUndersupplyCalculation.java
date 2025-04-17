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
                ied.setFailureTriggering(FailureTriggeringLine.failureTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                for (Protection protection : ied.getProtections()) {
                    protection.setOverTriggering(OverTriggeringLine.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveLine.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.BUS) {
                ied.setFailureTriggering(FailureTriggeringBus.failureTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                for (Protection protection : ied.getProtections()) {
                    protection.setOverTriggering(OverTriggeringBus.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveBus.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            if (ied.getEquipmentTypeName() == EquipmentType.TRANSFORMER) {
                ied.setFailureTriggering(FailureTriggeringTransformer.failureTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                for (Protection protection : ied.getProtections()) {
                    protection.setOverTriggering(OverTriggeringTransformer.overTriggeringCalculation(substationMeasuresPerYear, iedIndex));
                    protection.setFalsePositive(FalsePositiveTransformer.falsePositiveCalculation(substationMeasuresPerYear, iedIndex));
                }
            }
            iedIndex++;
        }

        HashMap<Breaker, Probability> newBreakersMap = probabilityCalculation(breakersMap, iedImpactList, substationMeasuresPerYear.getIedList());

        float undersupplyOverTrigger = 0f;
        float undersupplyFalsePositive = 0f;
        float undersupplyFailureTrigger = 0f;
        for (SchemaStatus schemaStatus : schemaStatusList) {
            float overTriggeredBreaker = 1f;
            float falsePositivedBreaker = 1f;
            float failureTriggeredBreaker = 1f;
            for (Breaker breaker : schemaStatus.getBreakers()) {
                if (breaker.getPosition() == 0) {
                    for (Map.Entry<Breaker, Probability> breakerProbability : newBreakersMap.entrySet()) {
                        if (breakerProbability.getKey().getBreakerName().equals(breaker.getBreakerName())) {
                            overTriggeredBreaker *= breakerProbability.getValue().getOverTriggerProbability();
                            falsePositivedBreaker *= breakerProbability.getValue().getFalsePositiveProbability();
                            failureTriggeredBreaker *= breakerProbability.getValue().getFailureTriggerProbablility();
                            break;
                        }
                    }
                }
            }
            undersupplyOverTrigger += (float) -Math.log(1 - overTriggeredBreaker) * schemaStatus.getUndersupply() * qapv * Tvosst * 99 * 1000;
            undersupplyFalsePositive += (float) -Math.log(1 - falsePositivedBreaker) * schemaStatus.getUndersupply() * Tvosst * 99 * 1000;
            undersupplyFailureTrigger += (float) -Math.log(1 - failureTriggeredBreaker) * schemaStatus.getUndersupply() * Tvosst * 99 * 1000;
        }
        return undersupplyOverTrigger + undersupplyFalsePositive + undersupplyFailureTrigger;
    }

    public static HashMap<Breaker, Probability> probabilityCalculation(HashMap<Breaker, Probability> breakersMap,
                                                                       List<IEDImpact> iedImpactList,
                                                                       List<IED> iedList) {
        HashMap<Breaker, Probability> newBreakersMap = new HashMap<>();
        for (Map.Entry<Breaker, Probability> breakerProbabilityEntry : breakersMap.entrySet()) {
            IED rpaCurrentMain = new IED();
            IED rpaCurrentReserve = new IED();
            IED rpaNeighborsMain = new IED();
            IED rpaNeighborsReserve = new IED();
            int iedQuantity = 0;
            newBreakersMap.put(new Breaker(breakerProbabilityEntry.getKey().getBreakerName(), breakerProbabilityEntry.getKey().getPosition()),
                    new Probability(breakerProbabilityEntry.getValue().getOverTriggerProbability(), breakerProbabilityEntry.getValue().getFalsePositiveProbability(),
                            breakerProbabilityEntry.getValue().getFailureTriggerProbablility()));
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
            failureProbabilityCalculation(newBreakersMap, breakerProbabilityEntry, rpaCurrentMain, rpaCurrentReserve, rpaNeighborsMain,
                    rpaNeighborsReserve, iedQuantity);
            overTriggerAndFalsePositiveProbabilityCalculation(newBreakersMap, breakerProbabilityEntry, rpaCurrentMain, rpaCurrentReserve, rpaNeighborsMain,
                    rpaNeighborsReserve);
        }
        return newBreakersMap;
    }

    public static void failureProbabilityCalculation(HashMap<Breaker, Probability> newBreakersMap,
                                                     Map.Entry<Breaker, Probability> breakersMapEntry,
                                                     IED rpaCurrentMain, IED rpaCurrentReserve, IED rpaNeighborsMain,
                                                     IED rpaNeighborsReserve, int iedQuantity) {
        for (Map.Entry<Breaker, Probability> breakerProbabilityEntry : newBreakersMap.entrySet()) {
            if (breakerProbabilityEntry.getKey().getBreakerName().equals(breakersMapEntry.getKey().getBreakerName())) {
                if (iedQuantity == 1) {
                    breakerProbabilityEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering());
                } else if (iedQuantity == 2) {
                    breakerProbabilityEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                            rpaCurrentReserve.getFailureTriggering());
                } else if (iedQuantity == 3) {
                    breakerProbabilityEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                            rpaCurrentReserve.getFailureTriggering() * rpaNeighborsMain.getFailureTriggering());
                } else {
                    breakerProbabilityEntry.getValue().setFailureTriggerProbablility(rpaCurrentMain.getFailureTriggering() *
                            rpaCurrentReserve.getFailureTriggering() * rpaNeighborsMain.getFailureTriggering() *
                            rpaNeighborsReserve.getFailureTriggering());
                }
            }
        }
    }

    public static void overTriggerAndFalsePositiveProbabilityCalculation(HashMap<Breaker, Probability> newBreakersMap,
                                                                         Map.Entry<Breaker, Probability> breakersMapEntry,
                                                                         IED rpaCurrentMain, IED rpaCurrentReserve,
                                                                         IED rpaNeighborsMain, IED rpaNeighborsReserve) {
        List<Protection> protectionList = new ArrayList<>();
        if (rpaCurrentMain.getProtections() != null) {
            protectionList.addAll(rpaCurrentMain.getProtections());
        }
        if (rpaCurrentReserve.getProtections() != null) {
            protectionList.addAll(rpaCurrentReserve.getProtections());
        }
        if (rpaNeighborsMain.getProtections() != null) {
            protectionList.addAll(rpaNeighborsMain.getProtections());
        }
        if (rpaNeighborsReserve.getProtections() != null) {
            protectionList.addAll(rpaNeighborsReserve.getProtections());
        }

        for (Map.Entry<Breaker, Probability> breakerProbability : newBreakersMap.entrySet()) {
            if (breakerProbability.getKey().getBreakerName().equals(breakersMapEntry.getKey().getBreakerName())) {
                if (protectionList.size() == 1) {
                    breakerProbability.getValue().setFalsePositiveProbability(protectionList.get(0).getFalsePositive());
                    breakerProbability.getValue().setOverTriggerProbability(protectionList.get(0).getOverTriggering());
                } else if (protectionList.size() == 2) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering()));
                } else if (protectionList.size() == 3) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering()));
                } else if (protectionList.size() == 4) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getFalsePositive()));
                } else if (protectionList.size() == 5) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering()));
                } else if (protectionList.size() == 6) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering()));
                } else if (protectionList.size() == 7) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering()));
                } else if (protectionList.size() == 8) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering()));
                } else if (protectionList.size() == 9) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering()));
                } else if (protectionList.size() == 10) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering()));
                } else if (protectionList.size() == 11) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering(), protectionList.get(10).getOverTriggering()));
                } else if (protectionList.size() == 12) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering(), protectionList.get(10).getOverTriggering(),
                            protectionList.get(11).getOverTriggering()));
                } else if (protectionList.size() == 13) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering(), protectionList.get(10).getOverTriggering(),
                            protectionList.get(11).getOverTriggering(), protectionList.get(12).getOverTriggering()));
                } else if (protectionList.size() == 14) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering(), protectionList.get(10).getOverTriggering(),
                            protectionList.get(11).getOverTriggering(), protectionList.get(12).getOverTriggering(),
                            protectionList.get(13).getOverTriggering()));
                } else if (protectionList.size() == 15) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getOverTriggering(),
                            protectionList.get(1).getOverTriggering(), protectionList.get(2).getOverTriggering(),
                            protectionList.get(3).getOverTriggering(), protectionList.get(4).getOverTriggering(),
                            protectionList.get(5).getOverTriggering(), protectionList.get(6).getOverTriggering(),
                            protectionList.get(7).getOverTriggering(), protectionList.get(8).getOverTriggering(),
                            protectionList.get(9).getOverTriggering(), protectionList.get(10).getOverTriggering(),
                            protectionList.get(11).getOverTriggering(), protectionList.get(12).getOverTriggering(),
                            protectionList.get(13).getOverTriggering(), protectionList.get(14).getOverTriggering()));
                } else if (protectionList.size() == 16) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive()));
                } else if (protectionList.size() == 17) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive()));
                } else if (protectionList.size() == 18) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive()));
                } else if (protectionList.size() == 19) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive(), protectionList.get(18).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive(), protectionList.get(18).getFalsePositive()));
                } else if (protectionList.size() == 20) {
                    breakerProbability.getValue().setFalsePositiveProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive(), protectionList.get(18).getFalsePositive(),
                            protectionList.get(19).getFalsePositive()));
                    breakerProbability.getValue().setOverTriggerProbability(ProbabilityUnion.calculateProbability(protectionList.get(0).getFalsePositive(),
                            protectionList.get(1).getFalsePositive(), protectionList.get(2).getFalsePositive(),
                            protectionList.get(3).getFalsePositive(), protectionList.get(4).getFalsePositive(),
                            protectionList.get(5).getFalsePositive(), protectionList.get(6).getFalsePositive(),
                            protectionList.get(7).getFalsePositive(), protectionList.get(8).getFalsePositive(),
                            protectionList.get(9).getFalsePositive(), protectionList.get(10).getFalsePositive(),
                            protectionList.get(11).getFalsePositive(), protectionList.get(12).getFalsePositive(),
                            protectionList.get(13).getFalsePositive(), protectionList.get(14).getFalsePositive(),
                            protectionList.get(15).getFalsePositive(), protectionList.get(16).getFalsePositive(),
                            protectionList.get(17).getFalsePositive(), protectionList.get(18).getFalsePositive(),
                            protectionList.get(19).getFalsePositive()));
                } else {
                    System.out.println("A lot of protections : " + protectionList.size());
                }
                break;
            }
        }
    }
}
