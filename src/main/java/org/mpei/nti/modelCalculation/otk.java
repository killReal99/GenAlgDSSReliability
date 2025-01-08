package org.mpei.nti.modelCalculation;

import static org.mpei.nti.modelCalculation.GeneralCoefficients.*;

public class otk {

    static double A1 = (double) 1 / 19;
    static double A3 = (double) 1 / 19;
    static double A5 = (double) 1 / 19;
    static double A7 = (double) 1 / 19;
    static double A9 = (double) 1 / 19;
    static double A11 = (double) 1 / 19;
    static double A13 = (double) 1 / 19;
    static double A15 = (double) 1 / 19;
    static double A17 = (double) 1 / 19;
    static double A19 = (double) 1 / 19;
    static double A21 = (double) 1 / 19;
    static double A23 = (double) 1 / 19;
    static double A25 = (double) 1 / 19;
    static double A27 = (double) 1 / 19;
    static double A29 = (double) 1 / 19;
    static double A31 = (double) 1 / 19;
    static double A33 = (double) 1 / 19;
    static double A35 = (double) 1 / 19;
    static double A37 = (double) 1 / 19;
    static double A39 = (double) 1 / 19;
    static double A41 = (double) 1 / 19;
    static double A43 = (double) 1 / 19;
    static double A45 = (double) 1 / 19;

    public static double mathNedoutp(double arch, double d1, double d2, double d3, double d4, double d5, double d6, double d7,
                                     double d8, double d9, double d10, double d11, double d12, double d13, double d14,
                                     double d15, double d16, double d17, double d18, double d19, double d20, double d21,
                                     double d22, double d23, double d24) {

        double DD1 = (1 - D1 * d1);
        double DD2 = (1 - D2 * d2);
        double DD3 = (1 - D3 * d3);
        double DD4 = (1 - D4 * d4);
        double DD5 = (1 - D5 * d5);
        double DD6 = (1 - D6 * d6);
        double DD7 = (1 - D7 * d7);
        double DD8 = (1 - D8 * d8);
        double DD9 = (1 - D9 * d9);
        double DD10 = (1 - D10 * d10);
        double DD11 = (1 - D11 * d11);
        double DD12 = (1 - D12 * d12);
        double DD13 = (1 - D13 * d13);
        double DD14 = (1 - D14 * d14);
        double DD15 = (1 - D15 * d15);
        double DD16 = (1 - D16 * d16);
        double DD17 = (1 - D17 * d17);
        double DD18 = (1 - D18 * d18);
        double DD19 = (1 - D19 * d19);
        double DD20 = (1 - D20 * d20);
        double DD21 = (1 - D21 * d21);
        double DD22 = (1 - D22 * d22);
        double DD23 = (1 - D23 * d23);
        double DD24 = (1 - D24 * d24);

        if (arch == 2.0) {
            A1 = 0.0;
            A3 = 0.0;
        }
        if (arch == 1.0) {
            A1 = 0.0;
            A2 = 0.0;
            A3 = 0.0;
            A4 = 0.0;
            A23 = 0.0;
            A24 = 0.0;
            A31 = 0.0;
            A32 = 0.0;
            A41 = 0.0;
            A42 = 0.0;
        }

        double Psv = A1 * A2 * DD1 * DD2 + A3 * A4 * DD3;
        double Pust = A5 * A6 * DD4 * DD5 + A7 * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 + DD5 * DD6 * (1 - DD7))
                + A9 * A10 * DD7 * DD8 * DD9 + A11 * A12 * (DD5 * DD10 * DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 *
                (1 - DD11));
        double PotkIED = A31 * A32 * DD16 * DD23 + A33 * A34 * DD16 * DD23 + A35 * A16 * DD10 * DD19 * DD23 + A37 *
                A38 * DD16 * DD23 + A29 * A30 * (DD10 * DD19 * DD20 * DD18 * DD21 * DD22 + DD10 * DD19 * (1 - DD20) *
                DD18 * DD21 * DD22 + DD10 * DD19 * DD20 * (1 - DD18) * DD21 * DD22 + DD10 * DD19 * DD20 * DD18 *
                (1 - DD21 * DD22) + DD10 * DD19 * (1 - DD20) * (1 - DD18) * DD21 * DD22 + DD10 * DD19 * (1 - DD20) *
                DD18 * (1 - DD21 * DD22) + DD10 * DD19 * DD20 * (1 - DD18) * (1 - DD21 * DD22));
        double PotkPds = A39 * A40 * DD16;
        double Potkkom = A41 * A42 * DD16 * DD24 + A43 * A44 * DD16 * DD24 + A45 * A46 * (DD10 * DD19 * DD24 * DD21 *
                DD22 + DD10 * DD19 * (1 - DD24) * DD21 * DD22 + DD10 * DD19 * DD24 * (1 - DD21) * DD22) + A23 * A24 *
                DD16 * DD17;
        double Psoft = A13 * A14 * DD9 * DD12 * DD13 * DD14 + A15 * A16 * DD9 * DD12 * DD13 * DD14 + A17 * A18 * DD9 *
                DD12 * DD13 * DD15 + A19 * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        double Pfull = Psv + PotkIED + PotkPds + Pust + Potkkom + Psoft;

        double P = Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa;

        double q = (-Math.log(1 - P) / 1);
        double W = Pper * Tvosst;
        return W * q;
    }
}
