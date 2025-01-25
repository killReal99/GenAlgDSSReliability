package org.mpei.nti.calculation.modesCalculation;

import org.mpei.nti.calculation.modelCalculation.FalsePositive;
import org.mpei.nti.calculation.modelCalculation.OverTriggering;
import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.Protections;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

public class MockUndersupplyCalculation {

    public static float undersupplyCalculation(SubstationMeasuresPerYear substationMeasuresPerYear) {
        float overTriggerUndersupply = 0.0f;
        float falsePositiveUndersupply = 0.0f;
        int iedIndex = 0;
        for (IED ied : substationMeasuresPerYear.getIedList()) {
            if (ied.getEquipmentTypeName() == EquipmentType.LINE) {
                for (Protections protection : ied.getProtectionsList()) {
                    overTriggerUndersupply += OverTriggering.overTriggeringCalculation(substationMeasuresPerYear, iedIndex);
                    falsePositiveUndersupply += FalsePositive.falsePositiveCalculation(substationMeasuresPerYear, iedIndex);
                }
                iedIndex++;
            }
        }
        return overTriggerUndersupply + falsePositiveUndersupply;
    }
}
