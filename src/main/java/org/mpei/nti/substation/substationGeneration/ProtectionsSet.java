package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protections;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.ArrayList;
import java.util.List;

public class ProtectionsSet {

    public static List<Protections> lineProtectionsSetGeneration() {
        List<Protections> protectionsList = new ArrayList<>();
        protectionsList.add(new Protections(ProtectionType.DZL, EquipmentType.LINE));
        protectionsList.add(new Protections(ProtectionType.MTZ, EquipmentType.LINE));
        protectionsList.add(new Protections(ProtectionType.TZNP, EquipmentType.LINE));
        protectionsList.add(new Protections(ProtectionType.DZ, EquipmentType.LINE));
        return protectionsList;
    }

}
