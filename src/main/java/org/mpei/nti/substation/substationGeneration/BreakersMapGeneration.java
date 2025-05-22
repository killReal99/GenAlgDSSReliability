package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Breaker;
import org.mpei.nti.utils.Probability;

import java.util.HashMap;

public class BreakersMapGeneration {

    public static HashMap<Breaker, Probability> generate() {
        HashMap<Breaker, Probability> breakersMap = new HashMap<>();
        for (int i = 1; i < 25; i++) {
            Breaker breaker = new Breaker();
            breaker.setBreakerName("Q" + i);
            breaker.setPosition(1);
            breakersMap.put(breaker,  new Probability(0f, 0f, 0f));
        }
        return breakersMap;
    }

}
