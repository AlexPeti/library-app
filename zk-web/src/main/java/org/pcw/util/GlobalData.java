package org.pcw.util;

public class GlobalData {
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalData.username = username;
    }
}
