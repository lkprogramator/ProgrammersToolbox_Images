package com.lkprogramator.tool_images.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * File filter for Image files
 *
 * Created by LKprogramator.
 */
public class ImageFileFilter  implements FileFilter {

    private final String[] imageFileExtensions = new String[] { "jpg", "jpeg", "png" };

    public boolean accept(File file) {
        for (String extension : imageFileExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
