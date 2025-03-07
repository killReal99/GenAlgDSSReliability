package org.mpei.nti.calculation.economicCalculation;

import org.mpei.nti.economic.BuildingCAPEX;
import org.mpei.nti.economic.BuildingOPEX;
import org.mpei.nti.economic.ExploitationOPEX;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

public class CostsCalculation {

    public static Float buildingCAPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float reBuildingCAPEX = 0f;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            reBuildingCAPEX = BuildingCAPEX.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            reBuildingCAPEX = BuildingCAPEX.withoutISSecondArch;
        } else {
            reBuildingCAPEX = BuildingCAPEX.withoutISThirdArch;
        }

        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * BuildingCAPEX.D2 + ied.getD4() * BuildingCAPEX.D4 +
                    ied.getD5() * BuildingCAPEX.D5 + ied.getD8() * BuildingCAPEX.D8 + ied.getD9() * BuildingCAPEX.D9 +
                    ied.getD13() * BuildingCAPEX.D13 + ied.getD14() * BuildingCAPEX.D14 +
                    ied.getD15() * BuildingCAPEX.D15 + ied.getD17() * BuildingCAPEX.D17 +
                    ied.getD18() * BuildingCAPEX.D18 + ied.getD23() * BuildingCAPEX.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * BuildingCAPEX.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * BuildingCAPEX.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * BuildingCAPEX.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * BuildingCAPEX.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * BuildingCAPEX.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * BuildingCAPEX.D24;
        return reBuildingCAPEX + embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float buildingOPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float buildingOPEX;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            buildingOPEX = BuildingOPEX.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            buildingOPEX = BuildingOPEX.withoutISSecondArch;
        } else {
            buildingOPEX = BuildingOPEX.withoutISThirdArch;
        }
        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * BuildingOPEX.D2 + ied.getD4() * BuildingOPEX.D4 +
                    ied.getD5() * BuildingOPEX.D5 + ied.getD8() * BuildingOPEX.D8 + ied.getD9() * BuildingOPEX.D9 +
                    ied.getD13() * BuildingOPEX.D13 + ied.getD14() * BuildingOPEX.D14 + ied.getD15() * BuildingOPEX.D15 +
                    ied.getD17() * BuildingOPEX.D17 + ied.getD18() * BuildingOPEX.D18 + ied.getD23() * BuildingOPEX.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * BuildingOPEX.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * BuildingOPEX.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD11() * BuildingOPEX.D11 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * BuildingOPEX.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * BuildingOPEX.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * BuildingOPEX.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * BuildingOPEX.D24;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD6() * BuildingOPEX.D6 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD10() * BuildingOPEX.D10 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD12() * BuildingOPEX.D12 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD16() * BuildingOPEX.D16 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD22() * BuildingOPEX.D22;
        return buildingOPEX + embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

    public static Float exploitationOPEX(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * ExploitationOPEX.D2 + ied.getD4() * ExploitationOPEX.D4 +
                    ied.getD5() * ExploitationOPEX.D5 + ied.getD8() * ExploitationOPEX.D8 +
                    ied.getD9() * ExploitationOPEX.D9 + ied.getD13() * ExploitationOPEX.D13 +
                    ied.getD14() * ExploitationOPEX.D14 + ied.getD15() * ExploitationOPEX.D15 +
                    ied.getD17() * ExploitationOPEX.D17 + ied.getD18() * ExploitationOPEX.D18 +
                    ied.getD23() * ExploitationOPEX.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * ExploitationOPEX.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * ExploitationOPEX.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD11() * ExploitationOPEX.D11 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * ExploitationOPEX.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * ExploitationOPEX.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * ExploitationOPEX.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * ExploitationOPEX.D24;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD1() * ExploitationOPEX.D1 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD6() * ExploitationOPEX.D6 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD10() * ExploitationOPEX.D10 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD12() * ExploitationOPEX.D12 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD16() * ExploitationOPEX.D16 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD22() * ExploitationOPEX.D22;
        return embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

}
