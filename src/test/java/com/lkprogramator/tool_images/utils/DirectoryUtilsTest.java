package com.lkprogramator.tool_images.utils;

import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryUtilsTest  extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryUtilsTest.class);

    private static String createDirectoryName = "test_create_directory";
    private static String deleteDirectoryName = "test_del_directory";

    private static String directoryWithoutImagesName = "test_no_image_in_folder";

    private static Path createDirectoryPath = Paths.get(pathToResourceFolder + createDirectoryName);
    private static Path deleteDirectoryPath = Paths.get(pathToResourceFolder + deleteDirectoryName);

    @AfterAll
    private static void cleanUpAfterTests() {

        try {
            Files.deleteIfExists(createDirectoryPath);
            Files.deleteIfExists(deleteDirectoryPath);
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when Setup for Creating / Deleting directory by test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

    }

    @Test
    @DisplayName("List all images in empty directory")
    void listImageFilesInEmptyFolder() {
        File[] fList = DirectoryUtils.listImageFilesInFolder(pathToResourceFolder + "test_empty_folder");

        assertFalse( fList.length > 0,"Listing images in empty directory is working." );
    }

    @Test
    @DisplayName("List all images in directory without images")
    void listImageFilesInFolderwithoutimages() {
        File[] fList = DirectoryUtils.listImageFilesInFolder(pathToResourceFolder + directoryWithoutImagesName);

        assertFalse( fList.length > 0,"Listing images in directory without images is working.");
    }

    @Test
    @DisplayName("List all images in directory")
    void listImageFilesInFolder() {
        File[] fList = DirectoryUtils.listImageFilesInFolder(pathToTestImageFolder);

        assertTrue(fList.length > 0 ,"Listing images in directory is working.");
    }

    @Test
    @DisplayName("Test if directory contains images")
    void isAnyImageFileInFolder() {

        assertAll("Folder scenarios ",
                () -> assertTrue(DirectoryUtils.isAnyImageFileInFolder(pathToTestImageFolder), "Folder contain images." ),
                () -> assertFalse(DirectoryUtils.isAnyImageFileInFolder(pathToResourceFolder + "test_empty_folder"),"Folder contain no images."  )
        );
    }

    @Test
    @DisplayName("Test create directory")
    void createDirectory() {

        Path directoryPath = Paths.get(pathToResourceFolder + createDirectoryName);

        try {
            DirectoryUtils.createDirectory(directoryPath);
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when Creating directory by test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        assertTrue(Files.exists(directoryPath) ,"Create directory is working.");
    }

    @Test
    @DisplayName("Test delete directory")
    void deleteDirectory() {

        Path directoryPath = Paths.get(pathToResourceFolder + deleteDirectoryName);

        try {
            Files.createDirectory(directoryPath);

            DirectoryUtils.deleteDirectory(directoryPath);

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when deleting directory by test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        assertTrue(Files.notExists(directoryPath),"Delete directory is working." );
    }
}
