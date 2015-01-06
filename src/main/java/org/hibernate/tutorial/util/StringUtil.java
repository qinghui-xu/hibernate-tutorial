package org.hibernate.tutorial.util;

/**
 * Type «StringUtil».
 */
public final class StringUtil {

    private StringUtil() {

    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
