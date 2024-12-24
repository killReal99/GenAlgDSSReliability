package org.mpei.nti.substation.substationStructures;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubstationMeasures {
    private float totalPrice;
    private float opexPrice;
    private float capexPrice;
    private List<SubstationMeasuresPerYear> substationMeasuresPerYear;
}
