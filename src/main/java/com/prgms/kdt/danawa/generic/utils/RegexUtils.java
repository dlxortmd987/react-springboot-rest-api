package com.prgms.kdt.danawa.generic.utils;

import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean checkRegex(String regex, String value) {
        return Pattern.matches(regex, value);
    }
}
