package org.mpei.nti.substation.substationStructures;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IEDImpact {
    private Breaker breaker;
    private List<String> iedList;
}
