package org.example;


import java.util.ArrayList;
import java.util.List;


public class Parsing {
    public static String[] splitPath(String path) {
        return path.split("/");
    }

    public static boolean hasFilter(String string) {
        return string.contains("?");
    }

    public static String[] splitFilter(String filter) {
        return filter.split("&");
    }

    public static String[] splitFilterCondition(String condition) {
        return condition.split("=");
    }

    public static List<String> extractFilterConditions(String filter) {
        List<String> conditions = new ArrayList<>();
        String[] filterParts = splitFilter(filter);

        for (String part : filterParts) {
            String[] condition = splitFilterCondition(part);
            if (condition.length == 2) {
                conditions.add(condition[0] + "=" + condition[1]);
            }
        }
        return conditions;
    }
}
