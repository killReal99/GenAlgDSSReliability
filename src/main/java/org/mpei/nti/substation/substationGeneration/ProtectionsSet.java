package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protection;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.ArrayList;
import java.util.List;

public class ProtectionsSet {

    public static List<Protection> lineProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(ProtectionType.DZL, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(ProtectionType.MTZ, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(ProtectionType.TZNP, EquipmentType.LINE, 0.0f, 0.0f));
        protectionList.add(new Protection(ProtectionType.DZ, EquipmentType.LINE, 0.0f, 0.0f));
        return protectionList;
    }

    public static List<Protection> busProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(ProtectionType.DZSH, EquipmentType.BUS, 0.0f, 0.0f));
        protectionList.add(new Protection(ProtectionType.MTZ, EquipmentType.BUS, 0.0f, 0.0f));
        return protectionList;
    }

    public static List<Protection> transformerProtectionsSetGeneration() {
        List<Protection> protectionList = new ArrayList<>();
        protectionList.add(new Protection(ProtectionType.DZT, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        protectionList.add(new Protection(ProtectionType.MTZ, EquipmentType.TRANSFORMER, 0.0f, 0.0f));
        return protectionList;
    }

}
