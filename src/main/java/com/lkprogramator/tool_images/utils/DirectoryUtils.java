package com.lkprogramator.tool_images.utils;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
/**
 * Created by LKprogramator.
 */
public class DirectoryUtils {

    /**
     * List all the image files from a directory
     *
     * @param directoryName to be listed
     * @return array of image files, or empty array if non found
     */
    public static File[] listImageFilesInFolder(String directoryName) {

        File directory = new File(directoryName);

        File[] fList = directory.listFiles(new ImageFileFilter());

        return fList;
    }

    /**
     * Find out if there is et least one image file from a directory
     *
     * @param directoryName to searched in
     * @return true if found
     */
    public static boolean isAnyImageFileInFolder(String directoryName) {

        File[] fList = listImageFilesInFolder(directoryName);

        return fList.length > 0;
    }

    /**
     * Create directory
     *
     * @param directoryPath directory to create
     * @return true if created
     */
    public static boolean createDirectory(Path directoryPath) throws IOException {

        Files.createDirectory(directoryPath);

        return Files.exists(directoryPath);
    }

    /**
     * Delete directory
     *
     * @param directoryPath directory to delete
     * @return true if deleted
     */
    public static boolean deleteDirectory(Path directoryPath) throws IOException {
        return Files.deleteIfExists(directoryPath);
    }

 /**
     * Clean directory of all files
     *
     * @param directoryPath directory to delete
     *
     */
    public static void clearDirectory(Path directoryPath) throws IOException {
       FileUtils.cleanDirectory(new File(directoryPath.toString()) );
    }

}
