package org.mpei.nti.genetic;

import org.mpei.nti.substation.substationGeneration.SubstationMeasuresPerYearGeneration;
import org.mpei.nti.substation.substationStructures.EmbeddedMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.util.List;

import static org.mpei.nti.Main.minArch;

public class Mutating {

    public static void mutatePopulation(List<SubstationMeasures> population) {
        for (SubstationMeasures substationMeasures : population){
            int randomYear = (int) (Math.random() * 24);
            int countOfChromosomes = 14 + 11 * substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getEmbeddedMeasuresList().size();
            double randomChromosome = Math.random();
            if (randomChromosome <= 1.0 / countOfChromosomes) {
                substationMeasures.getSubstationMeasuresPerYear().get(randomYear).
                        setArchitectureType((int) (Math.random() * (3.0 - minArch) + minArch + 0.1));
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
                for (EmbeddedMeasures embeddedMeasures : substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getEmbeddedMeasuresList()){
                    if (randomChromosome <= (14.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD2(mutate(embeddedMeasures.getD2()));
                    } else if (randomChromosome > (14.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (15.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD4(mutate(embeddedMeasures.getD4()));
                    } else if (randomChromosome > (15.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (16.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD5(mutate(embeddedMeasures.getD5()));
                    } else if (randomChromosome > (16.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (17.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD8(mutate(embeddedMeasures.getD8()));
                    } else if (randomChromosome > (17.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (18.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD9(mutate(embeddedMeasures.getD9()));
                    } else if (randomChromosome > (18.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (19.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD13(mutate(embeddedMeasures.getD13()));
                    } else if (randomChromosome > (19.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (20.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD14(mutate(embeddedMeasures.getD14()));
                    } else if (randomChromosome > (20.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (21.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD15(mutate(embeddedMeasures.getD15()));
                    } else if (randomChromosome > (21.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (22.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD17(mutate(embeddedMeasures.getD17()));
                    } else if (randomChromosome > (22.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (23.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD18(mutate(embeddedMeasures.getD18()));
                    } else if (randomChromosome > (23.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes &&
                            randomChromosome <= (24.0 + 11.0 * numberOfEmbeddedMeauseresSet) / countOfChromosomes) {
                        embeddedMeasures.setD23(mutate(embeddedMeasures.getD23()));
                    }
                    numberOfEmbeddedMeauseresSet++;
                }
                SubstationMeasuresPerYearGeneration.substationMeasuresGeneration(
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getArchitectureType(), randomYear,
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getOrganizationalMeasures(),
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getImprosedMeasures(),
                        substationMeasures.getSubstationMeasuresPerYear().get(randomYear).getEmbeddedMeasuresList());
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
