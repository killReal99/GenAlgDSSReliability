package org.mpei.nti.calculation.economicCalculation;

import org.mpei.nti.calculation.modesCalculation.MockUndersupplyCalculation;
import org.mpei.nti.economic.BuildingCAPEX;
import org.mpei.nti.economic.BuildingOPEX;
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
            if (substationMeasure.getTotalPrice() == 0.0f) {
                idsCheck(substationMeasure);
                firewallCheck(substationMeasure);
                antivirusCheck(substationMeasure);
                archCheck(substationMeasure);
                iedCheck(substationMeasure);
                for (SubstationMeasuresPerYear substationMeasuresPerYear : substationMeasure.getSubstationMeasuresPerYear()) {
                    substationMeasure.setCapexPrice(substationMeasure.getCapexPrice() + substationMeasuresPerYear.getCapexPrice());
                    substationMeasure.setOpexPrice(substationMeasure.getOpexPrice() + substationMeasuresPerYear.getOpexPrice());

                    substationMeasure.setTotalPrice(substationMeasure.getTotalPrice() +
                            MockUndersupplyCalculation.undersupplyCalculation(substationMeasuresPerYear, breakersMap,
                                    iedImpactList, schemaStatusList) + substationMeasuresPerYear.getCapexPrice() +
                            substationMeasuresPerYear.getOpexPrice());
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
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D21);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                }
                if (idsCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D21);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D21);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
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
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D20);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D20);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                }
                if (firewallCheck == 10) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D20);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D20);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
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
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D19);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D19);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                }
                if (antivirusCheck == 5) {
                    substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D19);
                    substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D19);
                    substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                            substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
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
                                BuildingCAPEX.fromFirstToSecondRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromFirstToSecondRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromFirstToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                } else if (arch == 2) {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromSecondToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromSecondToThirdRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                } else {
                    if (substationMeasuresPerYear.getArchitectureType() == 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromThirdToFirstRebuild);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    } else {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() +
                                BuildingCAPEX.fromThirdToSecondRebuild);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() +
                                BuildingOPEX.fromThirdToSecondRebuild);
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
        for (int i = 0; i < substationMeasures.getSubstationMeasuresPerYear().get(0).getIedList().size(); i++){
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
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D2);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D2);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d2CheckList.set(i, true);
                } else {
                    if (d2CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D2);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d2CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD4() == 1) {
                    if (!d4CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D4);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D4);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d4CheckList.set(i, true);
                } else {
                    if (d4CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D4);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d4CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD5() == 1) {
                    if (!d5CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D5);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D5);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d5CheckList.set(i, true);
                } else {
                    if (d5CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D5);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d5CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD8() == 1) {
                    if (!d8CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D8);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D8);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d8CheckList.set(i, true);
                } else {
                    if (d8CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D8);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d8CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD9() == 1) {
                    if (!d9CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D9);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D9);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d9CheckList.set(i, true);
                } else {
                    if (d9CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D9);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d9CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD13() == 1) {
                    if (!d13CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D13);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D13);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d13CheckList.set(i, true);
                } else {
                    if (d13CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D13);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d13CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD14() == 1) {
                    if (!d14CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D14);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D14);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d14CheckList.set(i, true);
                } else {
                    if (d14CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D14);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d14CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD15() == 1) {
                    if (!d15CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D15);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D15);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d15CheckList.set(i, true);
                } else {
                    if (d15CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D15);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d15CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD17() == 1) {
                    if (!d17CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D17);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D17);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d17CheckList.set(i, true);
                } else {
                    if (d17CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D17);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d17CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD18() == 1) {
                    if (!d18CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D18);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D18);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d18CheckList.set(i, true);
                } else {
                    if (d18CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D18);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d18CheckList.set(i, false);
                }

                if (substationMeasuresPerYear.getIedList().get(i).getD23() == 1) {
                    if (!d23CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setCapexPrice(substationMeasuresPerYear.getCapexPrice() + BuildingCAPEX.D23);
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D23);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getCapexPrice() + substationMeasuresPerYear.getOpexPrice());
                    }
                    d23CheckList.set(i, true);
                } else {
                    if (d23CheckList.get(i) && substationMeasuresPerYear.getYearNumber() != 1) {
                        substationMeasuresPerYear.setOpexPrice(substationMeasuresPerYear.getOpexPrice() + BuildingOPEX.D23);
                        substationMeasuresPerYear.setTotalPrice(substationMeasuresPerYear.getTotalPrice() +
                                substationMeasuresPerYear.getOpexPrice());
                    }
                    d23CheckList.set(i, false);
                }
            }
        }
    }
}
