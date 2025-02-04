package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

import static org.mpei.nti.Main.maxArch;
import static org.mpei.nti.Main.minArch;

public class Mutating {

    public static void mutatePopulation(List<SubstationMeasures> population) {
        float mutationProbability = 0.95f;
        for (SubstationMeasures substationMeasures : population) {
            if (Math.random() < mutationProbability) {
                int randomYear = (int) (Math.random() * 24 + 0.05);
                int countOfChromosomes = 14 + 11 * substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList().size();
                double randomChromosome = Math.random();
                if (randomChromosome <= 1.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).
                            setArchitectureType((int) (Math.random() * (maxArch - minArch) + minArch + 0.01));
                } else if (randomChromosome > 1.0 / countOfChromosomes && randomChromosome <= 2.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                            setD3(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD3()));
                } else if (randomChromosome > 2.0 / countOfChromosomes && randomChromosome <= 3.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                            setD7(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD7()));
                } else if (randomChromosome > 3.0 / countOfChromosomes && randomChromosome <= 4.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                            setD11(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD11()));
                } else if (randomChromosome > 4.0 / countOfChromosomes && randomChromosome <= 5.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                            setD19(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD19()));
                } else if (randomChromosome > 5.0 / countOfChromosomes && randomChromosome <= 6.0 / countOfChromosomes) {
                    substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().
                            setD20(mutate(substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures().getD20()));
                } else if (randomChromosome > 6.0 / countOfChromosomes && randomChromosome <= 7.0 / countOfChromosomes) {
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
                    int numberOfEmbeddedMeauseresSet = 0;
                    for (IED ied : substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList()) {
                        if (randomChromosome <= (14.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD2(mutate(ied.getD2()));
                        } else if (randomChromosome > (14.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (15.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD4(mutate(ied.getD4()));
                        } else if (randomChromosome > (15.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (16.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD5(mutate(ied.getD5()));
                        } else if (randomChromosome > (16.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (17.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD8(mutate(ied.getD8()));
                        } else if (randomChromosome > (17.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (18.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD9(mutate(ied.getD9()));
                        } else if (randomChromosome > (18.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (19.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD13(mutate(ied.getD13()));
                        } else if (randomChromosome > (19.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (20.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD14(mutate(ied.getD14()));
                        } else if (randomChromosome > (20.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (21.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD15(mutate(ied.getD15()));
                        } else if (randomChromosome > (21.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (22.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD17(mutate(ied.getD17()));
                        } else if (randomChromosome > (22.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (23.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD18(mutate(ied.getD18()));
                        } else if (randomChromosome > (23.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                                randomChromosome <= (24.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                            ied.setD23(mutate(ied.getD23()));
                        }
                        numberOfEmbeddedMeauseresSet++;
                    }
                    SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType(), randomYear,
                            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures(),
                            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures(),
                            substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getIedList());
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
