package ling;

import java.util.HashMap;

public class ContextInfo {
    String word;
    HashMap<String, ContextInfo> next = new HashMap();
}
