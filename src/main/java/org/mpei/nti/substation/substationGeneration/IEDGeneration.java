package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.IED;

public class IEDGeneration {

    public static IED iedGeneration(int D2, int D4, int D5, int D8, int D9, int D13, int D14, int D15, int D17,
                                    int D18, int D23) {
        IED ied = new IED();
        ied.setD2(D2);
        ied.setD4(D4);
        ied.setD5(D5);
        ied.setD8(D8);
        ied.setD9(D9);
        ied.setD13(D13);
        ied.setD14(D14);
        ied.setD15(D15);
        ied.setD17(D17);
        ied.setD18(D18);
        ied.setD23(D23);
        return ied;
    }

}
