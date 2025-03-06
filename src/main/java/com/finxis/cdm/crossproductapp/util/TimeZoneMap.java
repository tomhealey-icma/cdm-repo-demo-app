package com.finxis.cdm.crossproductapp.util;


import java.util.List;
import java.util.Map;
public class TimeZoneMap {

    private List<Map.Entry<String, String>> list;
    public Map buildEnumMap(Map<String, String> map) {

        map.put("GMT", "+00:00");
        map.put("BST", "+01:00");
        map.put("EST", "-05:00");
        map.put("EDT", "-04:00");
        map.put("JST", "+09:00");

        return map;
    }
}
