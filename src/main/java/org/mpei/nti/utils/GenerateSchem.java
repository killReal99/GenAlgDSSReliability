package org.mpei.nti.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mpei.nti.substation.substationStructures.Breaker;
import org.mpei.nti.substation.substationStructures.SchemaStatus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GenerateSchem {

    public static void generateStartSchem() throws IOException {
        List<SchemaStatus> schemaStatusList = new ArrayList<>();
        for (int q1 = 0; q1 <= 1; q1++) {
            for (int q2 = 0; q2 <= 1; q2++) {
                for (int q3 = 0; q3 <= 1; q3++) {
                    for (int q4 = 0; q4 <= 1; q4++) {
                        for (int q5 = 0; q5 <= 1; q5++) {
                            for (int q6 = 0; q6 <= 1; q6++) {
                                for (int q7 = 0; q7 <= 1; q7++) {
                                    for (int q8 = 0; q8 <= 1; q8++) {
                                        for (int q9 = 0; q9 <= 1; q9++) {
                                            for (int q10 = 0; q10 <= 1; q10++) {
                                                for (int q11 = 0; q11 <= 1; q11++) {
                                                    for (int q12 = 0; q12 <= 1; q12++) {
                                                        for (int q13 = 0; q13 <= 1; q13++) {
                                                            for (int q14 = 0; q14 <= 1; q14++) {
                                                                for (int q15 = 0; q15 <= 1; q15++) {
                                                                    for (int q16 = 0; q16 <= 1; q16++) {
                                                                        for (int q17 = 0; q17 <= 1; q17++) {
                                                                            for (int q18 = 0; q18 <= 1; q18++) {
                                                                                for (int q19 = 0; q19 <= 1; q19++) {
                                                                                    for (int q20 = 0; q20 <= 1; q20++) {
                                                                                        for (int q21 = 0; q21 <= 1; q21++) {
                                                                                            for (int q22 = 0; q22 <= 1; q22++) {
                                                                                                for (int q23 = 0; q23 <= 1; q23++) {
                                                                                                    for (int q24 = 0; q24 <= 1; q24++) {
                                                                                                        SchemaStatus schemaStatus = new SchemaStatus();
                                                                                                        List<Breaker> breakers = new ArrayList<>();
                                                                                                        Breaker Q1 = new Breaker();
                                                                                                        Q1.setBreakerName("Q1");
                                                                                                        Q1.setPosition(q1);
                                                                                                        breakers.add(Q1);
                                                                                                        Breaker Q2 = new Breaker();
                                                                                                        Q2.setBreakerName("Q2");
                                                                                                        Q2.setPosition(q2);
                                                                                                        breakers.add(Q2);
                                                                                                        Breaker Q3 = new Breaker();
                                                                                                        Q3.setBreakerName("Q3");
                                                                                                        Q3.setPosition(q3);
                                                                                                        breakers.add(Q3);
                                                                                                        Breaker Q4 = new Breaker();
                                                                                                        Q4.setBreakerName("Q4");
                                                                                                        Q4.setPosition(q4);
                                                                                                        breakers.add(Q4);
                                                                                                        Breaker Q5 = new Breaker();
                                                                                                        Q5.setBreakerName("Q5");
                                                                                                        Q5.setPosition(q5);
                                                                                                        breakers.add(Q5);
                                                                                                        Breaker Q6 = new Breaker();
                                                                                                        Q6.setBreakerName("Q6");
                                                                                                        Q6.setPosition(q6);
                                                                                                        breakers.add(Q6);
                                                                                                        Breaker Q7 = new Breaker();
                                                                                                        Q7.setBreakerName("Q7");
                                                                                                        Q7.setPosition(q7);
                                                                                                        breakers.add(Q7);
                                                                                                        Breaker Q8 = new Breaker();
                                                                                                        Q8.setBreakerName("Q8");
                                                                                                        Q8.setPosition(q8);
                                                                                                        breakers.add(Q8);
                                                                                                        Breaker Q9 = new Breaker();
                                                                                                        Q9.setBreakerName("Q1");
                                                                                                        Q9.setPosition(q9);
                                                                                                        breakers.add(Q9);
                                                                                                        Breaker Q10 = new Breaker();
                                                                                                        Q10.setBreakerName("Q10");
                                                                                                        Q10.setPosition(q10);
                                                                                                        breakers.add(Q10);
                                                                                                        Breaker Q11 = new Breaker();
                                                                                                        Q11.setBreakerName("Q11");
                                                                                                        Q11.setPosition(q11);
                                                                                                        breakers.add(Q11);
                                                                                                        Breaker Q12 = new Breaker();
                                                                                                        Q12.setBreakerName("Q12");
                                                                                                        Q12.setPosition(q12);
                                                                                                        breakers.add(Q12);
                                                                                                        Breaker Q13 = new Breaker();
                                                                                                        Q13.setBreakerName("Q13");
                                                                                                        Q13.setPosition(q13);
                                                                                                        breakers.add(Q13);
                                                                                                        Breaker Q14 = new Breaker();
                                                                                                        Q14.setBreakerName("Q14");
                                                                                                        Q14.setPosition(q14);
                                                                                                        breakers.add(Q14);
                                                                                                        Breaker Q15 = new Breaker();
                                                                                                        Q15.setBreakerName("Q15");
                                                                                                        Q15.setPosition(q15);
                                                                                                        breakers.add(Q15);
                                                                                                        Breaker Q16 = new Breaker();
                                                                                                        Q16.setBreakerName("Q16");
                                                                                                        Q16.setPosition(q16);
                                                                                                        breakers.add(Q16);
                                                                                                        Breaker Q17 = new Breaker();
                                                                                                        Q17.setBreakerName("Q17");
                                                                                                        Q17.setPosition(q17);
                                                                                                        breakers.add(Q17);
                                                                                                        Breaker Q18 = new Breaker();
                                                                                                        Q18.setBreakerName("Q18");
                                                                                                        Q18.setPosition(q18);
                                                                                                        breakers.add(Q18);
                                                                                                        Breaker Q19 = new Breaker();
                                                                                                        Q19.setBreakerName("Q19");
                                                                                                        Q19.setPosition(q19);
                                                                                                        breakers.add(Q19);
                                                                                                        Breaker Q20 = new Breaker();
                                                                                                        Q20.setBreakerName("Q20");
                                                                                                        Q1.setPosition(q20);
                                                                                                        breakers.add(Q20);
                                                                                                        Breaker Q21 = new Breaker();
                                                                                                        Q21.setBreakerName("Q21");
                                                                                                        Q21.setPosition(q21);
                                                                                                        breakers.add(Q21);
                                                                                                        Breaker Q22 = new Breaker();
                                                                                                        Q22.setBreakerName("Q22");
                                                                                                        Q22.setPosition(q22);
                                                                                                        breakers.add(Q22);
                                                                                                        Breaker Q23 = new Breaker();
                                                                                                        Q23.setBreakerName("Q23");
                                                                                                        Q23.setPosition(q23);
                                                                                                        breakers.add(Q23);
                                                                                                        Breaker Q24 = new Breaker();
                                                                                                        Q24.setBreakerName("Q24");
                                                                                                        Q24.setPosition(q24);
                                                                                                        breakers.add(Q24);
                                                                                                        schemaStatus.setBreakers(breakers);
                                                                                                        float undersupply = (float) (120 * Math.random());
                                                                                                        if (Math.random() > 0.999) {
                                                                                                            schemaStatus.setUndersupply(undersupply);
                                                                                                            schemaStatusList.add(schemaStatus);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String schema = new ObjectMapper().writeValueAsString(schemaStatusList);
        PrintWriter jsonObj = new PrintWriter("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "schemaStatus.json", StandardCharsets.UTF_8);
        jsonObj.println(schema);
        jsonObj.close();
    }

}
