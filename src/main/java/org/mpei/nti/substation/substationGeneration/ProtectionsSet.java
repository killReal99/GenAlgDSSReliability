package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protection;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProtectionsSet {

    public static List<Protection> lineProtectionsGeneration(int number) {
        List<Protection> protectionList = new ArrayList<>();
        if (number < 5) {
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZL, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.TZNP, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MFTO, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.AMTZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.TZP, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZNR, EquipmentType.LINE, 0.0f, 0.0f));
        } else {
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZOZZ, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZMN, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZNR, EquipmentType.LINE, 0.0f, 0.0f));
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZDZ, EquipmentType.LINE, 0.0f, 0.0f));
        }
        return protectionList;
    }

    public static List<Protection> busProtectionsGeneration(int number) {
        List<Protection> protectionList = new ArrayList<>();
        if (number < 5) {
            protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZSH, EquipmentType.BUS, 0.0f, 0.0f));
        }
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.LZSH, EquipmentType.BUS, 0.0f, 0.0f));
        return protectionList;
    }

    public static List<Protection> transformerProtectionsGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZT, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DZO, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.DTZNP, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.GZ, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.TZNP, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.MTZ, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.LZSH, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(UUID.randomUUID(), ProtectionType.ZP, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        return protectionList;
    }

}
