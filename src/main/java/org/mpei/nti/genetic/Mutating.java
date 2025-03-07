package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

public class Mutating {

    public static void mutatePopulation(List<SubstationMeasures> population, int minArch, int maxArch,
                                        boolean lanRosseti, boolean iedRosseti, int fstec) {
        float mutationProbability = 0.80f;
        for (SubstationMeasures substationMeasures : population) {
            if (Math.random() < mutationProbability) {
                int randomYear = (int) (Math.random() * 24 + 0.055);
                double mutationAlgorithm = Math.random();
                if (mutationAlgorithm < 0.9) {
                    onePointMutation(substationMeasures, randomYear, minArch, maxArch, lanRosseti, iedRosseti, fstec);
                } else {
                    blockMutation(substationMeasures, randomYear, lanRosseti, iedRosseti, fstec);
                }
            }
        }
    }

    public static void onePointMutation(SubstationMeasures substationMeasures, int randomYear, int minArch, int maxArch,
                                        boolean lanRosseti, boolean iedRosseti, int fstec) {
        int countOfChromosomes = 14 + 11 * substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().size();
        double randomChromosome = Math.random();
        if (randomChromosome <= 1.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).
                    setArchitectureType((int) (Math.random() * (maxArch - minArch) + minArch + 0.01));
            if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() == 1) {
                for (IED ied : substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList()) {
                    ied.setD2(0);
                    ied.setD17(0);
                }
            } else if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() == 2) {
                for (IED ied : substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList()) {
                    ied.setD17(0);
                }
            }
        } else if (randomChromosome > 1.0 / countOfChromosomes && randomChromosome <= 2.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD3(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD3()));
        } else if (randomChromosome > 2.0 / countOfChromosomes && randomChromosome <= 3.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD7(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD7()));
        } else if (randomChromosome > 3.0 / countOfChromosomes && randomChromosome <= 4.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD11(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD11()));
        } else if (randomChromosome > 4.0 / countOfChromosomes && randomChromosome <= 5.0 / countOfChromosomes && !lanRosseti) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD19(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD19()));
        } else if (randomChromosome > 5.0 / countOfChromosomes && randomChromosome <= 6.0 / countOfChromosomes && !lanRosseti) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD20(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD20()));
        } else if (randomChromosome > 6.0 / countOfChromosomes && randomChromosome <= 7.0 / countOfChromosomes && !lanRosseti) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD21(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD21()));
        } else if (randomChromosome > 7.0 / countOfChromosomes && randomChromosome <= 8.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                    setD24(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD24()));
        } else if (randomChromosome > 8.0 / countOfChromosomes && randomChromosome <= 9.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD1(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD1()));
        } else if (randomChromosome > 9.0 / countOfChromosomes && randomChromosome <= 1.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD6(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD6()));
        } else if (randomChromosome > 10.0 / countOfChromosomes && randomChromosome <= 11.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD10(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD10()));
        } else if (randomChromosome > 11.0 / countOfChromosomes && randomChromosome <= 12.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD12(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD12()));
        } else if (randomChromosome > 12.0 / countOfChromosomes && randomChromosome <= 13.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD16(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD16()));
        } else if (randomChromosome > 13.0 / countOfChromosomes && randomChromosome <= 14.0 / countOfChromosomes) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().
                    setD22(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD22()));
        } else {
            int numberOfEmbeddedMeauseres = 0;
            for (IED ied : substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList()) {
                if (randomChromosome > (14.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (15.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() != 3) {
                        ied.setD2(0);
                    } else {
                        ied.setD2(mutate(ied.getD2()));
                    }
                    break;
                } else if (randomChromosome > (15.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (16.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD4(mutate(ied.getD4()));
                    break;
                } else if (randomChromosome > (16.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (17.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD5(mutate(ied.getD5()));
                    break;
                } else if (randomChromosome > (17.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (18.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD8(mutate(ied.getD8()));
                    break;
                } else if (randomChromosome > (18.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (19.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD9(mutate(ied.getD9()));
                    break;
                } else if (randomChromosome > (19.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (20.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD13(mutate(ied.getD13()));
                    break;
                } else if (randomChromosome > (20.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (21.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD14(mutate(ied.getD14()));
                    break;
                } else if (randomChromosome > (21.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (22.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD15(mutate(ied.getD15()));
                    break;
                } else if (randomChromosome > (22.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (23.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() == 1) {
                        ied.setD17(0);
                    } else {
                        ied.setD17(mutate(ied.getD17()));
                    }
                    break;
                } else if (randomChromosome > (23.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (24.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD18(mutate(ied.getD18()));
                    break;
                } else if (randomChromosome > (24.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes &&
                        randomChromosome <= (25.0 + 11.0 * numberOfEmbeddedMeauseres) / countOfChromosomes) {
                    ied.setD23(mutate(ied.getD23()));
                    break;
                }
                numberOfEmbeddedMeauseres++;
            }

            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).setCapexPrice(0.0f);
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).setOpexPrice(0.0f);
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).setTotalPrice(0.0f);
            substationMeasures.getSubstationMeasuresPerYear().set(randomYear, SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType(), (randomYear + 1),
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures(),
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures(),
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList()));
        }
    }

    public static void blockMutation(SubstationMeasures substationMeasures, int randomYear, boolean lanRosseti, boolean iedRosseti, int fstec) {
        int countOfBlocks = 2 + substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().size();
        double randomBlock = Math.random();
        if (randomBlock <= 1.0 / countOfBlocks) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD3(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD3()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD7(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD7()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD11(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD11()));
            if (!lanRosseti) {
                substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD19(
                        mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD19()));
                substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD20(
                        mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD20()));
                substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD21(
                        mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD21()));
            }
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().setD24(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD24()));
        } else if (randomBlock > 1.0 / countOfBlocks && randomBlock <= 2.0 / countOfBlocks) {
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD1(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD1()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD6(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD6()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD10(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD10()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD12(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD12()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD16(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD16()));
            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().setD22(
                    mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures().getD22()));
        } else {
            for (int i = 0; i < substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().size(); i++) {
                if (randomBlock > 2.0 / countOfBlocks && randomBlock <= (3.0 + i) / countOfBlocks) {
                    if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() == 1) {
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD2(0);
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD17(0);
                    } else if (substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType() == 2) {
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD2(0);
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD17(
                                mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD17()));
                    } else {
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD2(
                                mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD2()));
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD17(
                                mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD17()));
                    }
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD4(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD4()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD5(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD5()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD8(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD8()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD9(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD9()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD13(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD13()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD14(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD14()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD15(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD15()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD18(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD18()));
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).setD23(
                            mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().get(i).getD23()));
                }
            }
        }

    }

    public static int mutate(int chromosome) {
        if (chromosome == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
