package org.hhplus.hhplus_concert_service;

public class Utils {

    public static String checkNull(String str) {

        if (str == null)
            return "";
        else
            return str;
    }

    public static int checkNullByInt(String str) {

        if (str == null || str.equals(""))
            return 0;
        else
            return Integer.parseInt(str);
    }
}
