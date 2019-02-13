package com.lkprogramator.tool_images.utils;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class CommonUtilsTest {

    @Test
    @DisplayName("String has only number value")
    void isNumeric() {

        assertTrue(CommonUtils.isNumeric("123"), "String has only number value.");
    }

    @Test
    @DisplayName("String has no number value")
    void isNumeric2() {

        assertFalse(CommonUtils.isNumeric("abcds"), "String has no number value.");
    }

    @Test
    @DisplayName("String has same number value")
    void isNumeric3() {

        assertFalse(CommonUtils.isNumeric("as456e"), "String has mix values.");
    }

    @Test
    @DisplayName("Test if String is blank")
    void isBlankString() {

        assertTrue(CommonUtils.isBlankString(""), "String is blank.");
    }

    @Test
    @DisplayName("Test if String is not blank")
    void isBlankString2() {

        assertFalse(CommonUtils.isBlankString("xxx"), "String is not blank.");
    }

    @Test
    @DisplayName("Test if file name is image ")
    void isImageFile() {

        assertTrue(CommonUtils.isImageFile("myImageFile.jpg"), "Its image file.");
    }


    @Test
    @DisplayName("Test if file name is image ")
    void isImageFile2() {

        assertFalse(CommonUtils.isImageFile("myImageFile"), "Its not image file.");
    }

    @Test
    @DisplayName("Test if number is in interval ")
    void isBetweenNumbers() {

        assertTrue(CommonUtils.isBetweenNumbers(5, 2, 10), "Number is in interval.");
    }

    @Test
    @DisplayName("Test if number is not in interval ")
    void isBetweenNumbers2() {

        assertFalse(CommonUtils.isBetweenNumbers(15, 3, 5), "Number is not in interval .");
    }
}
