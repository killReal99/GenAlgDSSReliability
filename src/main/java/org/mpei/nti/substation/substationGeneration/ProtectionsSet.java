package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protections;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.ArrayList;
import java.util.List;

public class ProtectionsSet {

    public static List<Protections> lineProtectionsSetGeneration() {
        List<Protections> protectionsList = new ArrayList<>();
        protectionsList.add(new Protections(ProtectionType.DZL, EquipmentType.LINE, 0.0f, 0.0f));
        protectionsList.add(new Protections(ProtectionType.MTZ, EquipmentType.LINE, 0.0f, 0.0f));
        protectionsList.add(new Protections(ProtectionType.TZNP, EquipmentType.LINE, 0.0f, 0.0f));
        protectionsList.add(new Protections(ProtectionType.DZ, EquipmentType.LINE, 0.0f, 0.0f));
        return protectionsList;
    }

    public static List<Protections> busProtectionsSetGeneration() {
        List<Protections> protectionsList = new ArrayList<>();
        protectionsList.add(new Protections(ProtectionType.DZSH, EquipmentType.BUS, 0.0f, 0.0f));
        protectionsList.add(new Protections(ProtectionType.MTZ, EquipmentType.BUS, 0.0f, 0.0f));
        return protectionsList;
    }

    public static List<Protections> transformerProtectionsSetGeneration() {
        List<Protections> protectionsList = new ArrayList<>();
        protectionsList.add(new Protections(ProtectionType.DZT, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionsList.add(new Protections(ProtectionType.MTZ, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        return protectionsList;
    }

}
