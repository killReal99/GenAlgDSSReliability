package org.mpei.nti;

import org.mpei.nti.calculation.ReliabilityCalculation;
import org.mpei.nti.substationGeneration.EmbeddedMeasuresGeneration;
import org.mpei.nti.substationGeneration.ImprosedMeasuresGeneration;
import org.mpei.nti.substationGeneration.OrganizationalMeasuresGeneration;
import org.mpei.nti.substationGeneration.SubstationMeasuresGeneration;
import org.mpei.nti.substationStructure.EmbeddedMeasures;
import org.mpei.nti.substationStructure.ImprosedMeasures;
import org.mpei.nti.substationStructure.OrganizationalMeasures;
import org.mpei.nti.substationStructure.SubstationMeasures;
import org.mpei.nti.utils.Debuging;
import org.mpei.nti.utils.MappingSzi;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();

        List<List<SubstationMeasures>> substationMeasuresList = new ArrayList<>();

        for (int year = 1; year <= 25; year++){

        }





        Debuging debuging = new Debuging();

        for (int arch = 1; arch <= 3; arch++) {
            organizationalMeasuresGenerator(arch, substationMeasuresList, debuging);
        }

        List<List<Double>> population = new ArrayList<>();
        int populationSize = 5000;
        double minArch = 1.0;
        for (int j = 0; j < populationSize; j++) {
            List<Double> d = new ArrayList<>();
            int arch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
            d.add((double) arch);
            d.add(0.0);
            for (int i = 0; i < 25; i++) {
                if (Math.round(Math.random()) >= 0.5) {
                    d.add(1.0);
                } else {
                    d.add(0.0);
                }
            }
            population.add(d);
        }

        for (int j = 0; j < populationSize; j++) {
            population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j),
                    ReliabilityCalculation.capexCalculation(population.get(j)),
                    ReliabilityCalculation.opexCalculation(population.get(j))));
        }

        int numberOfIterations = 1000;

        int withoutChanges = 0;
        double theLastValue = 0;

        for (int i = 0; i < numberOfIterations; i++) {
            int theNextGenerationPopulation = populationSize;
            for (int k = 0; k < populationSize; k++) {
                // Swap
                if (Math.random() >= 0.5) {
                    int sizeOfSwap = (int) Math.round(Math.random() * (population.get(0).size() - 3));
                    int secondFather = (int) Math.round(Math.random() * (populationSize - 1));
                    List<Double> sonOfSwap = new ArrayList<>();
                    int newArch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
                    sonOfSwap.add((double) newArch);
                    sonOfSwap.add(0.0);
                    for (int l = 0; l < sizeOfSwap; l++) {
                        sonOfSwap.add(population.get(k).get(l + 2));
                    }
                    for (int l = 0; l < (population.get(0).size() - 2 - sizeOfSwap); l++) {
                        sonOfSwap.add(population.get(secondFather).get(l + sizeOfSwap + 2));
                    }
                    // Mutation
                    if (Math.random() >= 0.5) {
                        if (Math.random() >= 0.5) {
                            int mutateArch = (int) (Math.random() * (3.0 - minArch) + minArch + 0.1);
                            sonOfSwap.set(0, (double) mutateArch);
                        } else {
                            int mutateGen = (int) Math.round(Math.random() * (population.get(0).size() - 3));
                            if (sonOfSwap.get(mutateGen) == 0) {
                                sonOfSwap.set(mutateGen, 1.0);
                            } else {
                                sonOfSwap.set(mutateGen, 0.0);
                            }
                        }
                    }
                    population.add(sonOfSwap);
                    theNextGenerationPopulation++;
                }
            }

            System.out.println("Iteration N = " + i);

            for (int j = 0; j < theNextGenerationPopulation; j++) {
                double capex = ReliabilityCalculation.capexCalculation(population.get(j));
                double opex = ReliabilityCalculation.opexCalculation(population.get(j));
                if (capex >= 7000000.0) {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), 100000000.0, opex));
                } else if (opex >= 1500000.0) {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, 100000000.0));
                } else {
                    population.set(j, ReliabilityCalculation.electricalEnergyUndersupply(population.get(j), capex, opex));
                }
            }

            bubbleSort(population, theNextGenerationPopulation);

            int countDeleting = theNextGenerationPopulation - populationSize;
            for (int j = 0; j < countDeleting; j++) {
                population.remove(population.size() - 1);
            }
            System.out.println("The best individ with M = " + population.get(0).get(1));

            if (Math.round(theLastValue * 100) == Math.round(population.get(0).get(1) * 100)) {
                withoutChanges++;
            } else {
                withoutChanges = 0;
            }
            theLastValue = population.get(0).get(1);
            if (withoutChanges == numberOfIterations / 2) {
                break;
            }

        }
        MappingSzi.mapSzi(population);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    static void organizationalMeasuresGenerator(int arch, List<List<SubstationMeasures>> substationMeasuresList, Debuging debuging) {
        for (int d1 = 0; d1 <= 1; d1++) {
            for (int d6 = 0; d6 <= 1; d6++) {
                for (int d10 = 0; d10 <= 1; d10++) {
                    for (int d12 = 0; d12 <= 1; d12++) {
                        for (int d16 = 0; d16 <= 1; d16++) {
                            for (int d22 = 0; d22 <= 1; d22++) {
                                OrganizationalMeasures organizationalMeasures = OrganizationalMeasuresGeneration
                                        .organizationalMeasuresGeneration(d1, d6, d10, d12, d16, d22);
                                improsedMeasuresGenerator(arch, organizationalMeasures, substationMeasuresList, debuging);
                            }
                        }
                    }
                }
            }
        }
    }

    static void improsedMeasuresGenerator(int arch, OrganizationalMeasures organizationalMeasures,
                                          List<List<SubstationMeasures>> substationMeasuresList, Debuging debuging) {
        for (int d3 = 0; d3 <= 1; d3++) {
            for (int d7 = 0; d7 <= 1; d7++) {
                for (int d11 = 0; d11 <= 1; d11++) {
                    for (int d19 = 0; d19 <= 1; d19++) {
                        for (int d20 = 0; d20 <= 1; d20++) {
                            for (int d21 = 0; d21 <= 1; d21++) {
                                for (int d24 = 0; d24 <= 1; d24++) {
                                    ImprosedMeasures improsedMeasures = ImprosedMeasuresGeneration.
                                            improsedMeasuresGeneration(d3, d7, d11, d19, d20, d21, d24);
                                    embeddedMeasuresGenerator(arch, organizationalMeasures, improsedMeasures,
                                            substationMeasuresList, debuging);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    static void embeddedMeasuresGenerator(int arch, OrganizationalMeasures organizationalMeasures,
                                          ImprosedMeasures improsedMeasures,
                                          List<List<SubstationMeasures>> substationMeasuresList, Debuging debuging) {
        for (int D2_1 = 0; D2_1 <= 1; D2_1++) {
            for (int D4_1 = 0; D4_1 <= 1; D4_1++) {
                for (int D5_1 = 0; D5_1 <= 1; D5_1++) {
                    for (int D8_1 = 0; D8_1 <= 1; D8_1++) {
                        for (int D9_1 = 0; D9_1 <= 1; D9_1++) {
                            for (int D13_1 = 0; D13_1 <= 1; D13_1++) {
                                for (int D14_1 = 0; D14_1 <= 1; D14_1++) {
                                    for (int D15_1 = 0; D15_1 <= 1; D15_1++) {
                                        for (int D17_1 = 0; D17_1 <= 1; D17_1++) {
                                            for (int D18_1 = 0; D18_1 <= 1; D18_1++) {
                                                for (int D23_1 = 0; D23_1 <= 1; D23_1++) {
                                                    for (int D2_2 = 0; D2_2 <= 1; D2_2++) {
                                                        for (int D4_2 = 0; D4_2 <= 1; D4_2++) {
                                                            for (int D5_2 = 0; D5_2 <= 1; D5_2++) {
                                                                for (int D8_2 = 0; D8_2 <= 1; D8_2++) {
                                                                    for (int D9_2 = 0; D9_2 <= 1; D9_2++) {
                                                                        for (int D13_2 = 0; D13_2 <= 1; D13_2++) {
                                                                            for (int D14_2 = 0; D14_2 <= 1; D14_2++) {
                                                                                for (int D15_2 = 0; D15_2 <= 1; D15_2++) {
                                                                                    for (int D17_2 = 0; D17_2 <= 1; D17_2++) {
                                                                                        for (int D18_2 = 0; D18_2 <= 1; D18_2++) {
                                                                                            for (int D23_2 = 0; D23_2 <= 1; D23_2++) {
                                                                                                for (int D2_3 = 0; D2_3 <= 1; D2_3++) {
                                                                                                    for (int D4_3 = 0; D4_3 <= 1; D4_3++) {
                                                                                                        for (int D5_3 = 0; D5_3 <= 1; D5_3++) {
                                                                                                            for (int D8_3 = 0; D8_3 <= 1; D8_3++) {
                                                                                                                for (int D9_3 = 0; D9_3 <= 1; D9_3++) {
                                                                                                                    for (int D13_3 = 0; D13_3 <= 1; D13_3++) {
                                                                                                                        for (int D14_3 = 0; D14_3 <= 1; D14_3++) {
                                                                                                                            for (int D15_3 = 0; D15_3 <= 1; D15_3++) {
                                                                                                                                for (int D17_3 = 0; D17_3 <= 1; D17_3++) {
                                                                                                                                    for (int D18_3 = 0; D18_3 <= 1; D18_3++) {
                                                                                                                                        for (int D23_3 = 0; D23_3 <= 1; D23_3++) {
                                                                                                                                            List<EmbeddedMeasures> embeddedMeasuresList = new ArrayList<>();
                                                                                                                                            embeddedMeasuresList.add(EmbeddedMeasuresGeneration.embeddedMeasuresGeneration(D2_1, D4_1, D5_1, D8_1, D9_1, D13_1, D14_1, D15_1, D17_1, D18_1, D23_1));
                                                                                                                                            embeddedMeasuresList.add(EmbeddedMeasuresGeneration.embeddedMeasuresGeneration(D2_2, D4_2, D5_2, D8_2, D9_2, D13_2, D14_2, D15_2, D17_2, D18_2, D23_2));
                                                                                                                                            embeddedMeasuresList.add(EmbeddedMeasuresGeneration.embeddedMeasuresGeneration(D2_3, D4_3, D5_3, D8_3, D9_3, D13_3, D14_3, D15_3, D17_3, D18_3, D23_3));

                                                                                                                                            List<SubstationMeasures> substationMeasures = new ArrayList<>();
                                                                                                                                            substationMeasures.add(SubstationMeasuresGeneration.substationMeasures(arch,
                                                                                                                                                    1, organizationalMeasures, improsedMeasures, embeddedMeasuresList));

                                                                                                                                            debuging.setTree(debuging.getTree() + 1);

                                                                                                                                            double percent = 100.0 * debuging.getTree() / Math.pow(2.0, 39.0);

                                                                                                                                            System.out.println("Percent " + percent + " %");


                                                                                                                                            if ((ReliabilityCalculation.opexMeasuresCalculation(substationMeasures) < 75000.0) && (ReliabilityCalculation.capexMeasuresCalculation(substationMeasures) < 500000.0)) {
                                                                                                                                                substationMeasuresList.add(substationMeasures);
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    static void bubbleSort
            (List<List<Double>> population,
             int populationSize) {
        boolean swapped;
        List<Double> temp;
        for (int i = 0; i < populationSize - 1; i++) {
            swapped = false;
            for (int j = 0; j < populationSize - i - 1; j++) {
                if (population.get(j).get(1) > population.get(j + 1).get(1)) {
                    temp = population.get(j);
                    population.set(j, population.get(j + 1));
                    population.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }
}