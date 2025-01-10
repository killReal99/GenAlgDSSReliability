package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubstationMeasures {
    private float totalPrice;
    private float opexPrice;
    private float capexPrice;
    private List<SubstationMeasuresPerYear> substationMeasuresPerYear;
}
