package com.finxis.cdm.crossproductapp;

import java.util.HashMap;
import java.util.Map;

public class CollateralDescription {
    static private final Map<String, CollateralDescription> known = new HashMap<>();
    static public final CollateralDescription Gilt0522jul2022 = new CollateralDescription("Gilt 0.5% 22jul2022");

    static private final CollateralDescription[] array = {};

    private final String name;

    public CollateralDescription(String name) {
        this.name = name;
        synchronized (CollateralDescription.class) {
            known.put(name, this);
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    static public Object[] toArray() {
        return array;
    }

    public static CollateralDescription parse(String type) throws IllegalArgumentException {
        CollateralDescription result = known.get(type);
        if (result == null) {
            throw new IllegalArgumentException
            ("CollateralDescription: " + type + " is unknown.");
        }
        return result;
    }
}
