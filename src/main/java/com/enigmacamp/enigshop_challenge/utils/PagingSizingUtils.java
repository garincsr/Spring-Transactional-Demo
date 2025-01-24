package com.enigmacamp.enigshop_challenge.utils;

public class PagingSizingUtils {
    public static Integer validatePage(String page){
        if (isDigit(page) || Integer.parseInt(page) <= 0) {
            return 1;
        }

        return Integer.parseInt(page);
    }

    public static Integer validateSize(String size){
        if (isDigit(size) || Integer.parseInt(size) <= 0) {
            return 10;
        }

        return Integer.parseInt(size);
    }

    private static boolean isDigit(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
}
