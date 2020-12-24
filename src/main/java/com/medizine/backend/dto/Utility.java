package com.medizine.backend.dto;

import com.google.common.base.CaseFormat;

import java.util.IllegalFormatException;

public class Utility {

    public static String enumConverter(String value) {
        if (value != null) {
            CaseFormat converterCase = getCaseFormatName(value);
            if (converterCase != null) {
                return converterCase.to(CaseFormat.UPPER_UNDERSCORE, value);
            } else {
                return value.toUpperCase();
            }
        }
        return null;
    }

    private static CaseFormat getCaseFormatName(String s) throws IllegalFormatException {
        if (s.contains("_")) {
            if (s.toUpperCase().equals(s))
                return CaseFormat.UPPER_UNDERSCORE;

            if (s.toLowerCase().equals(s))
                return CaseFormat.LOWER_UNDERSCORE;

        } else if (s.contains("-")) {
            if (s.toLowerCase().equals(s))
                return CaseFormat.LOWER_HYPHEN;

        } else {
            if (Character.isLowerCase(s.charAt(0))) {
                if (s.matches("([a-z]+[A-Z]+\\w+)+"))
                    return CaseFormat.LOWER_CAMEL;
            } else {
                if (s.matches("([A-Z]+[a-z]+\\w+)+"))
                    return CaseFormat.UPPER_CAMEL;
            }
        }
        return null;
    }
}
