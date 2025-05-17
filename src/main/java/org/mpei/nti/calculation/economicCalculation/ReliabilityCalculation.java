package org.mpei.nti.calculation.economicCalculation;

import org.mpei.nti.calculation.modesCalculation.MockUndersupplyCalculation;
import org.mpei.nti.economic.CAPEXEquipment;
import org.mpei.nti.economic.CAPEXSalary;
import org.mpei.nti.substation.substationStructures.*;
import org.mpei.nti.utils.Probability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReliabilityCalculation {

    public static void goalFunctionCalculation(List<SubstationMeasures> substationMeasuresList,
                                               HashMap<Breaker, Probability> breakersMap, List<IEDImpact> iedImpactList,
                                               List<SchemaStatus> schemaStatusList) {
        for (SubstationMeasures substationMeasure : substationMeasuresList) {
            if (substationMeasure.getTotalPrice() == 0f) {
                idsCheck(substationMeasure);
                firewallCheck(substationMeasure);
                antivirusCheck(substationMeasure);
                archCheck(substationMeasure);
                iedCheck(substationMeasure);
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());
                    float damage = MockUndersupplyCalculation.undersupplyCalculation(substationMeasuresPerYear,
                            breakersMap, iedImpactList, schemaStatusList);
                    substationMeasure.setDamage(damage);
                    substationMeasure.setTotalPrice(substationMeasure.getTotalPrice() + damage +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                }
            }
        }
    }

    public static void idsCheck(SubstationMeasures substationMeasures) {
        int idsCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                idsCheck++;
                if (substationMeasuresPerYear.getYearNumber() != 1 && idsCheck == 1) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                            CAPEXEquipment.D21 + CAPEXSalary.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                }
                if (idsCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                            CAPEXEquipment.D21 + CAPEXSalary.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                    idsCheck = 1;
                }
            } else {
                idsCheck = 0;
            }
        }
    }

    public static void firewallCheck(SubstationMeasures substationMeasures) {
        int firewallCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                firewallCheck++;
                if (substationMeasuresPerYear.getYearNumber() != 1 && firewallCheck == 1) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                            + CAPEXEquipment.D20 + CAPEXSalary.D20);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                }
                if (firewallCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                            + CAPEXEquipment.D20 + CAPEXSalary.D20);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                    firewallCheck = 1;
                }
            } else {
                firewallCheck = 0;
            }
        }
    }

    public static void antivirusCheck(SubstationMeasures substationMeasures) {
        int antivirusCheck = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                antivirusCheck++;
                if (substationMeasuresPerYear.getYearNumber() != 1 && antivirusCheck == 1) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                            + CAPEXEquipment.D19 + CAPEXSalary.D19);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                }
                if (antivirusCheck == 5) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                            + CAPEXEquipment.D19 + CAPEXSalary.D19);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice());
                    antivirusCheck = 1;
                }
            } else {
                antivirusCheck = 0;
            }
        }
    }

    public static void archCheck(SubstationMeasures substationMeasures) {
        int arch = 0;
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            if (substationMeasuresPerYear.getYearNumber() != 1 && substationMeasuresPerYear.getArchitectureType() != arch) {
                if (arch == 1) {
                    if (substationMeasuresPerYear.getArchitectureType() == 2) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromFirstToSecondRebuild + CAPEXSalary.fromFirstToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromFirstToThirdRebuild + CAPEXSalary.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                } else if (arch == 2) {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromSecondToFirstRebuild + CAPEXSalary.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromSecondToThirdRebuild + CAPEXSalary.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                } else {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromThirdToFirstRebuild + CAPEXSalary.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.fromThirdToSecondRebuild + CAPEXSalary.fromThirdToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                }
            }
            arch = substationMeasuresPerYear.getArchitectureType();
        }
    }

    public static void iedCheck(SubstationMeasures substationMeasures) {
        List<Boolean> d2CheckList = new ArrayList<>();
        List<Boolean> d4CheckList = new ArrayList<>();
        List<Boolean> d5CheckList = new ArrayList<>();
        List<Boolean> d8CheckList = new ArrayList<>();
        List<Boolean> d9CheckList = new ArrayList<>();
        List<Boolean> d13CheckList = new ArrayList<>();
        List<Boolean> d14CheckList = new ArrayList<>();
        List<Boolean> d15CheckList = new ArrayList<>();
        List<Boolean> d17CheckList = new ArrayList<>();
        List<Boolean> d18CheckList = new ArrayList<>();
        List<Boolean> d23CheckList = new ArrayList<>();
        for (int i = 0; i < substationMeasures.getSubstationMeasuresPerYear().get(0).getIedList().size(); i++) {
            d2CheckList.add(false);
            d4CheckList.add(false);
            d5CheckList.add(false);
            d8CheckList.add(false);
            d9CheckList.add(false);
            d13CheckList.add(false);
            d14CheckList.add(false);
            d15CheckList.add(false);
            d17CheckList.add(false);
            d18CheckList.add(false);
            d23CheckList.add(false);
        }
        for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasures.getSubstationMeasuresPerYear()) {
            for (int i = 0; i < substationMeasuresPerYear.getIedList().size(); i++) {
                if (substationMeasuresPerYear.getIedList().get(i).getD2() == 1) {
                    if (!d2CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                                + CAPEXEquipment.D2 + CAPEXSalary.D2);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d2CheckList.set(i, true);
                } else {
                    d2CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD4() == 1) {
                    if (!d4CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D4 + CAPEXSalary.D4);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d4CheckList.set(i, true);
                } else {
                    d4CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD5() == 1) {
                    if (!d5CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice()
                                + CAPEXEquipment.D5 + CAPEXSalary.D5);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d5CheckList.set(i, true);
                } else {
                    d5CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD8() == 1) {
                    if (!d8CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D8 + CAPEXSalary.D8);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d8CheckList.set(i, true);
                } else {
                    d8CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD9() == 1) {
                    if (!d9CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D9 + CAPEXSalary.D9);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d9CheckList.set(i, true);
                } else {
                    d9CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD13() == 1) {
                    if (!d13CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D13 + CAPEXSalary.D13);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d13CheckList.set(i, true);
                } else {
                    d13CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD14() == 1) {
                    if (!d14CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D14 + CAPEXSalary.D14);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d14CheckList.set(i, true);
                } else {
                    d14CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD15() == 1) {
                    if (!d15CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D15 + CAPEXSalary.D15);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d15CheckList.set(i, true);
                } else {
                    d15CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD17() == 1) {
                    if (!d17CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D17 + CAPEXSalary.D17);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d17CheckList.set(i, true);
                } else {
                    d17CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD18() == 1) {
                    if (!d18CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D18 + CAPEXSalary.D18);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d18CheckList.set(i, true);
                } else {
                    d18CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD23() == 1) {
                    if (!d23CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                CAPEXEquipment.D23 + CAPEXSalary.D23);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice());
                    }
                    d23CheckList.set(i, true);
                } else {
                    d23CheckList.set(i, false);
                }
            }
        }
    }
}
