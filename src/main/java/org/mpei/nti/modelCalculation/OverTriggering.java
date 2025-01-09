package org.mpei.nti.modelCalculation;

import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import static org.mpei.nti.modelCalculation.GeneralCoefficients.*;

public class OverTriggering {

    static double A1 = 0.1;
    static double A3 = 0.1;
    static double A5 = 0.1;
    static double A7 = 0.1;
    static double A9 = 0.1;
    static double A11 = 0.1;
    static double A13 = 0.1;
    static double A15 = 0.1;
    static double A17 = 0.1;
    static double A19 = 0.1;

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

    public static double overTriggeringCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {

        double DD1 = (1 - D1 * substationMeasuresPerYear.getOrganizationalMeasures().getD1());
        double DD2 = (1 - D2 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD2());
        double DD3 = (1 - D3 * substationMeasuresPerYear.getImprosedMeasures().getD3());
        double DD4 = (1 - D4 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD4());
        double DD5 = (1 - D5 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD5());
        double DD6 = (1 - D6 * substationMeasuresPerYear.getOrganizationalMeasures().getD6());
        double DD7 = (1 - D7 * substationMeasuresPerYear.getImprosedMeasures().getD7());
        double DD8 = (1 - D8 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD8());
        double DD9 = (1 - D9 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD9());
        double DD10 = (1 - D10 * substationMeasuresPerYear.getOrganizationalMeasures().getD10());
        double DD11 = (1 - D11 * substationMeasuresPerYear.getImprosedMeasures().getD11());
        double DD12 = (1 - D12 * substationMeasuresPerYear.getOrganizationalMeasures().getD12());
        double DD13 = (1 - D13 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD13());
        double DD14 = (1 - D14 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD14());
        double DD15 = (1 - D15 * substationMeasuresPerYear.getEmbeddedMeasuresList().get(0).getD15());

        if (substationMeasuresPerYear.getArchitectureType() == 2.0) {
            A1 = 0.0;
            A2 = 0.0;
            A3 = 0.0;
            A4 = 0.0;
        }
        if (substationMeasuresPerYear.getArchitectureType() == 1.0) {
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
