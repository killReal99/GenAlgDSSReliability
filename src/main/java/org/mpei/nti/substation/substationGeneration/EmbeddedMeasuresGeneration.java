package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.EmbeddedMeasures;

public class EmbeddedMeasuresGeneration {

    public static EmbeddedMeasures embeddedMeasuresGeneration(int D2, int D4, int D5, int D8, int D9, int D13, int D14,
                                                              int D15, int D17, int D18, int D23) {
        EmbeddedMeasures embeddedMeasures = new EmbeddedMeasures();
        embeddedMeasures.setD2(D2);
        embeddedMeasures.setD4(D4);
        embeddedMeasures.setD5(D5);
        embeddedMeasures.setD8(D8);
        embeddedMeasures.setD9(D9);
        embeddedMeasures.setD13(D13);
        embeddedMeasures.setD14(D14);
        embeddedMeasures.setD15(D15);
        embeddedMeasures.setD17(D17);
        embeddedMeasures.setD18(D18);
        embeddedMeasures.setD23(D23);
        return embeddedMeasures;
    }

}
