package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protection;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProtectionsSet {

    public static List<Protection> lineProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZL, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZ, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.TZNP, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MFTO, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.AMTZ, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.TZP, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZNR, EquipmentType.LINE, 0.0f, 0.0f));
        return protectionList;
    }

    public static List<Protection> busProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZSH, EquipmentType.BUS, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.BUS, 0.0f, 0.0f));
        return protectionList;
    }

    public static List<Protection> transformerProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZT, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        return protectionList;
    }

}
