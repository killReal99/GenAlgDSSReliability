package org.mpei.nti.substationGeneration;


import org.mpei.nti.substationStructure.ImprosedMeasures;

public class ImprosedMeasuresGeneration {

    public static ImprosedMeasures improsedMeasuresGeneration(int D3, int D7, int D11, int D19, int D20, int D21, int D24) {
        ImprosedMeasures improsedMeasures = new ImprosedMeasures();
        improsedMeasures.setPrice(0.0f);
        improsedMeasures.setD3(D3);
        improsedMeasures.setD7(D7);
        improsedMeasures.setD11(D11);
        improsedMeasures.setD19(D19);
        improsedMeasures.setD20(D20);
        improsedMeasures.setD21(D21);
        improsedMeasures.setD24(D24);
        return improsedMeasures;
    }

}
