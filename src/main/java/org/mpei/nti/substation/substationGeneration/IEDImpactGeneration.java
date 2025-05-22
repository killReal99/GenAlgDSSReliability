package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Breaker;
import org.mpei.nti.substation.substationStructures.IEDImpact;
import org.mpei.nti.utils.Probability;

import java.util.*;

public class IEDImpactGeneration {

    public static List<IEDImpact> generate(HashMap<Breaker, Probability> breakersMap) {
        List<IEDImpact> iedImpactList = new ArrayList<>();
        Iterator<HashMap.Entry<Breaker, Probability>> iterator = breakersMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Breaker, Probability> breakerIterator = iterator.next();
            IEDImpact iedImpact = new IEDImpact();
            List<String> iedList = new ArrayList<>();
            if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q1")) {
                iedList.add("W1_1");
                iedList.add("W1_2");
                iedList.add("B1_1");
                iedList.add("B1_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q2")) {
                iedList.add("W2_1");
                iedList.add("W2_2");
                iedList.add("B2_1");
                iedList.add("B2_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q3")) {
                iedList.add("W3_1");
                iedList.add("W3_2");
                iedList.add("B1_1");
                iedList.add("B1_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q4")) {
                iedList.add("W4_1");
                iedList.add("W4_2");
                iedList.add("B2_1");
                iedList.add("B2_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
           else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q5")) {
                iedList.add("T1_1");
                iedList.add("B1_1");
                iedList.add("B1_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q6")) {
                iedList.add("T2_1");
                iedList.add("B2_1");
                iedList.add("B2_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q7")) {
                iedList.add("B1_1");
                iedList.add("B1_2");
                iedList.add("B2_1");
                iedList.add("B2_2");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q8")) {
                iedList.add("T1_1");
                iedList.add("B4_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q9")) {
                iedList.add("T1_1");
                iedList.add("B4_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q10")) {
                iedList.add("W5_1");
                iedList.add("B4_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q11")) {
                iedList.add("W6_1");
                iedList.add("B4_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q12")) {
                iedList.add("B3_1");
                iedList.add("B4_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q13")) {
                iedList.add("W7_1");
                iedList.add("B3_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q14")) {
                iedList.add("W8_1");
                iedList.add("B3_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q15")) {
                iedList.add("T1_1");
                iedList.add("B5_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q16")) {
                iedList.add("T2_1");
                iedList.add("B6_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q17")) {
                iedList.add("W9_1");
                iedList.add("B6_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q18")) {
                iedList.add("W10_1");
                iedList.add("B6_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q19")) {
                iedList.add("W11_1");
                iedList.add("B6_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q20")) {
                iedList.add("W12_1");
                iedList.add("B6_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q21")) {
                iedList.add("W13_1");
                iedList.add("B5_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q22")) {
                iedList.add("W14_1");
                iedList.add("B5_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q23")) {
                iedList.add("W15_1");
                iedList.add("B5_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            else if (Objects.equals(breakerIterator.getKey().getBreakerName(), "Q24")) {
                iedList.add("W16_1");
                iedList.add("B5_1");
                iedImpact.setIedList(iedList);
                iedImpact.setBreaker(breakerIterator.getKey());
            }
            iedImpactList.add(iedImpact);
        }
        return iedImpactList;
    }

}
