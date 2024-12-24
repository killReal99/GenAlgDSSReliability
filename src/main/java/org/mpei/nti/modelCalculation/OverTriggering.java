package org.mpei.nti.modelCalculation;

public class OverTriggering {

    static double A1 = 0.1;
    static double A2 = 0.559193;
    static double A3 = 0.1;
    static double A4 = 0.614269;
    static double A5 = 0.1;
    static double A6 = 0.338363;
    static double A7 = 0.1;
    static double A8 = 0.157916;
    static double A9 = 0.1;
    static double A10 = 0.8437735;
    static double A11 = 0.1;
    static double A12 = 0.5725;
    static double A13 = 0.1;
    static double A14 = 0.453563;
    static double A15 = 0.1;
    static double A16 = 0.513088;
    static double A17 = 0.1;
    static double A18 = 0.332159;
    static double A19 = 0.1;
    static double A20 = 0.332159;

    static double D1 = 0.753601f;
    static double D2 = 0.917973f;
    static double D3 = 0.697452f;
    static double D4 = 0.579821;
    static double D5 = 0.684628;
    static double D6 = 0.567494;
    static double D7 = 0.54136;
    static double D8 = 0.921765;
    static double D9 = 0.829793;
    static double D10 = 0.490983;
    static double D11 = 0.533953;
    static double D12 = 0.794845;
    static double D13 = 0.943504;
    static double D14 = 0.959038;
    static double D15 = 0.924996;

    static double Pne = 0.099;
    static double Pkz = 0.93279;
    static double PkzKa = 0.0003082;
    static double qapv = 0.05;
    static double Pper = 30;
    static double Tvosst = 9.4;

    public static double mathNedoutp(double arch, double d1, double d2, double d3, double d4, double d5, double d6,
                                     double d7, double d8, double d9, double d10, double d11, double d12, double d13,
                                     double d14, double d15) {

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

        if (arch == 2.0) {
            A1 = 0.0;
            A2 = 0.0;
            A3 = 0.0;
            A4 = 0.0;
        }
        if (arch == 1.0) {
            A1 = 0.0;
            A2 = 0.0;
            A3 = 0.0;
            A4 = 0.0;
        }

        double Psv = A1 * A2 * DD1 * DD2 + A3 * A4 * DD3;
        double Pust = A5 * A6 * DD4 * DD5 + A7 * A8 * (DD5 * DD6 * DD7 + DD5 * (1 - DD6) * DD7 + DD5 * DD6 * (1 - DD7)) +
                A9 * A10 * DD7 * DD8 * DD9 + A11 * A12 * (DD5 * DD10 * DD11 + (1 - DD5) * DD10 * DD11 + DD5 * DD10 *
                (1 - DD11));
        double Psoft = A13 * A14 * DD9 * DD12 * DD13 * DD14 + A15 * A16 * DD9 * DD12 * DD13 * DD14 + A17 * A18 * DD9 *
                DD12 * DD13 * DD15 + A19 * A20 * DD9 * DD12 * DD13 * DD14 * DD15;

        double Pfull = Psv + Pust + Psoft;

        double P = Pne * Pfull * Pkz + Pne * (1 - Pfull) * Pkz + (1 - Pne) * Pfull * PkzKa;

        double q = (-Math.log(1 - P) / 1);
        double W = Pper * Tvosst;
        return W * q * qapv;
    }


}
