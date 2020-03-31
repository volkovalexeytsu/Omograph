package ling;

import java.util.HashMap;

public class Omograph {
    HashMap<String, ContextInfo> contextLeft = new HashMap();
    HashMap<String, ContextInfo> contextRight = new HashMap();
    void view()
    {
        System.out.println("LEFT:");
        for (String key: contextLeft.keySet()) {
            System.out.println("        " + key);
            for (String ke : contextLeft.get(key).next.keySet()) {
                System.out.println("    " +ke);
                for (String k : contextLeft.get(key).next.get(ke).next.keySet())
                    System.out.println(k);
            }
            System.out.println("");
        }
        System.out.println("RIGHT:");
        for (String key: contextRight.keySet()) {
            System.out.println(key);
            for (String ke: contextRight.get(key).next.keySet()) {
                System.out.println("    " + ke);
                for (String k : contextRight.get(key).next.get(ke).next.keySet())
                    System.out.println("        " + k);
            }
            System.out.println("");
        }
    }
}
