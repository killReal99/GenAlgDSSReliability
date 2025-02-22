package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Breaker;

import java.util.HashMap;

public class BreakersMapGeneration {

    public static HashMap<Breaker, Float> generate() {
        HashMap<Breaker, Float> breakersMap = new HashMap<>();
        for (int i = 1; i < 25; i++) {
            Breaker breaker = new Breaker();
            breaker.setBreakerName("Q" + i);
            breaker.setPosition(0);
            breakersMap.put(breaker, 0f);
        }
        return breakersMap;
    }

}
