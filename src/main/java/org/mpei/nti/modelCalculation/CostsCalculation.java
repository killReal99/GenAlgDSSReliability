package org.mpei.nti.modelCalculation;

import org.mpei.nti.economic.BuildingCAPEX;
import org.mpei.nti.economic.BuildingOPEX;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

public class CostsCalculation {

    public static Float buildingCAPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float buildingCAPEX = 0.0f;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            buildingCAPEX = BuildingCAPEX.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            buildingCAPEX = BuildingCAPEX.withoutISSecondArch;
        } else {
            buildingCAPEX = BuildingCAPEX.withoutISThirdArch;
        }

        float embeddedMeasuresPrice = 0.0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * 1083.33f + ied.getD4() * 1083.33f + ied.getD5() * 1083.33f +
                    ied.getD8() * 1083.33f + ied.getD9() * 1083.33f + ied.getD13() * 1083.33f +
                    ied.getD14() * 1083.33f + ied.getD15() * 1083.33f + ied.getD17() * 1083.33f +
                    ied.getD18() * 1083.33f + ied.getD23() * 1083.33f);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * 20000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * 1083.33f +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * 750000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * 3250000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * 150000.0f;
        return buildingCAPEX + embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float exploitationCAPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0.0f;
        float improsedMeasuresPrice = 0.0f;
        return embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float buildingOPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float buildingOPEX = 0.0f;
        if (substationMeasuresPerYear.getYearNumber() == 1) {
            if (substationMeasuresPerYear.getArchitectureType() == 1) {
                buildingOPEX = BuildingOPEX.withoutISFirstArch;
            } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
                buildingOPEX = BuildingOPEX.withoutISSecondArch;
            } else {
                buildingOPEX = BuildingOPEX.withoutISThirdArch;
            }
        }
        float embeddedMeasuresPrice = 0.0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * 15000.0f + ied.getD4() * 15000.0f + ied.getD5() * 15000.0f +
                    ied.getD8() * 15000.0f + ied.getD9() * 15000.0f + ied.getD13() * 15000.0f +
                    ied.getD14() * 15000.0f + ied.getD15() * 15000.0f + ied.getD17() * 15000.0f +
                    ied.getD18() * 10000.0f + ied.getD23() * 15000.0f);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * 1500.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * 15000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD11() * 75000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * 1500.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * 150000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * 300000.0f +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * 1500.0f;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD1() * 75000.0f +
                substationMeasuresPerYear.getOrganizationalMeasures().getD6() * 75000.0f +
                substationMeasuresPerYear.getOrganizationalMeasures().getD10() * 75000.0f +
                substationMeasuresPerYear.getOrganizationalMeasures().getD12() * 75000.0f +
                substationMeasuresPerYear.getOrganizationalMeasures().getD16() * 75000.0f +
                substationMeasuresPerYear.getOrganizationalMeasures().getD22() * 75000.0f;
        return buildingOPEX + embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

    public static Float exploitationOPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0.0f;
        float improsedMeasuresPrice = 0.0f;
        float organizationalMeasuresPrice = 0.0f;
        return embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

}
