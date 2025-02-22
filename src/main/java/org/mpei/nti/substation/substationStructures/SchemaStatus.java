package org.mpei.nti.substation.substationStructures;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchemaStatus {
    List<Breaker> breakers;
    float undersupply;
}
