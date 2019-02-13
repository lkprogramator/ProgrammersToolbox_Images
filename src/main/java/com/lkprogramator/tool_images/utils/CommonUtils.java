package com.lkprogramator.tool_images.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);


    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isBlankString(String str) {
        return str.trim().isEmpty();
    }

    public static boolean isImageFile(String str) {
        return str.matches("([^\\s]+(\\.(?i)(jpg|png))$)");
    }

    public static boolean isBetweenNumbers(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

    public static boolean isBetweenNumbers(float i, float minValueInclusive, float maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

}
