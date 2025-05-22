package org.mpei.nti.calculation.economicCalculation;

import org.mpei.nti.economic.CAPEXEquipment;
import org.mpei.nti.economic.CAPEXSalary;
import org.mpei.nti.economic.OPEX;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

public class CostsCalculation {

    public static Float capexEquipment(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float buildingCAPEX;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            buildingCAPEX = CAPEXEquipment.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            buildingCAPEX = CAPEXEquipment.withoutISSecondArch;
        } else {
            buildingCAPEX = CAPEXEquipment.withoutISThirdArch;
        }

        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * CAPEXEquipment.D2 + ied.getD4() * CAPEXEquipment.D4 +
                    ied.getD5() * CAPEXEquipment.D5 + ied.getD8() * CAPEXEquipment.D8 + ied.getD9() * CAPEXEquipment.D9 +
                    ied.getD13() * CAPEXEquipment.D13 + ied.getD14() * CAPEXEquipment.D14 +
                    ied.getD15() * CAPEXEquipment.D15 + ied.getD17() * CAPEXEquipment.D17 +
                    ied.getD18() * CAPEXEquipment.D18 + ied.getD23() * CAPEXEquipment.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * CAPEXEquipment.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * CAPEXEquipment.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * CAPEXEquipment.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * CAPEXEquipment.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * CAPEXEquipment.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * CAPEXEquipment.D24;
        return buildingCAPEX + embeddedMeasuresPrice + improsedMeasuresPrice;
    }

    public static Float capexSalary(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float capexSalary;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            capexSalary = CAPEXSalary.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            capexSalary = CAPEXSalary.withoutISSecondArch;
        } else {
            capexSalary = CAPEXSalary.withoutISThirdArch;
        }
        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * CAPEXSalary.D2 + ied.getD4() * CAPEXSalary.D4 +
                    ied.getD5() * CAPEXSalary.D5 + ied.getD8() * CAPEXSalary.D8 + ied.getD9() * CAPEXSalary.D9 +
                    ied.getD13() * CAPEXSalary.D13 + ied.getD14() * CAPEXSalary.D14 + ied.getD15() * CAPEXSalary.D15 +
                    ied.getD17() * CAPEXSalary.D17 + ied.getD18() * CAPEXSalary.D18 + ied.getD23() * CAPEXSalary.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * CAPEXSalary.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * CAPEXSalary.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD11() * CAPEXSalary.D11 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * CAPEXSalary.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * CAPEXSalary.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * CAPEXSalary.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * CAPEXSalary.D24;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD6() * CAPEXSalary.D6 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD10() * CAPEXSalary.D10 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD12() * CAPEXSalary.D12 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD16() * CAPEXSalary.D16 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD22() * CAPEXSalary.D22;
        return capexSalary + embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }

    public static Float opex(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float opexBased = 0f;
        if (substationMeasuresPerYear.getArchitectureType() == 1) {
            opexBased = OPEX.withoutISFirstArch;
        } else if (substationMeasuresPerYear.getArchitectureType() == 2) {
            opexBased = OPEX.withoutISSecondArch;
        } else {
            opexBased = OPEX.withoutISThirdArch;
        }

        float embeddedMeasuresPrice = 0f;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            embeddedMeasuresPrice += (ied.getD2() * OPEX.D2 + ied.getD4() * OPEX.D4 + ied.getD5() * OPEX.D5 +
                    ied.getD8() * OPEX.D8 + ied.getD9() * OPEX.D9 + ied.getD13() * OPEX.D13 + ied.getD14() * OPEX.D14 +
                    ied.getD15() * OPEX.D15 + ied.getD17() * OPEX.D17 + ied.getD18() * OPEX.D18 + ied.getD23() * OPEX.D23);
        }
        float improsedMeasuresPrice = substationMeasuresPerYear.getImprosedMeasures().getD3() * OPEX.D3 +
                substationMeasuresPerYear.getImprosedMeasures().getD7() * OPEX.D7 +
                substationMeasuresPerYear.getImprosedMeasures().getD11() * OPEX.D11 +
                substationMeasuresPerYear.getImprosedMeasures().getD19() * OPEX.D19 +
                substationMeasuresPerYear.getImprosedMeasures().getD20() * OPEX.D20 +
                substationMeasuresPerYear.getImprosedMeasures().getD21() * OPEX.D21 +
                substationMeasuresPerYear.getImprosedMeasures().getD24() * OPEX.D24;
        float organizationalMeasuresPrice = substationMeasuresPerYear.getOrganizationalMeasures().getD1() * OPEX.D1 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD6() * OPEX.D6 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD10() * OPEX.D10 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD12() * OPEX.D12 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD16() * OPEX.D16 +
                substationMeasuresPerYear.getOrganizationalMeasures().getD22() * OPEX.D22;
        return opexBased + embeddedMeasuresPrice + improsedMeasuresPrice + organizationalMeasuresPrice;
    }
}
