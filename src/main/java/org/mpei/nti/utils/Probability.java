package org.mpei.nti.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Probability {
    private float overTriggerProbability;
    private float falsePositiveProbability;
    private float failureTriggerProbablility;
}
