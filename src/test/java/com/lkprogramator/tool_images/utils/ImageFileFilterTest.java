package com.lkprogramator.tool_images.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ImageFileFilterTest {
    @Test
    @DisplayName("Filter Accepts Image file extension")
    void accept() {

        File testImageFile = new File("/test_images/test_image_01.jpg");
        ImageFileFilter imageFileFilter = new ImageFileFilter();
       boolean result = imageFileFilter.accept(testImageFile);

        assertTrue(result, "The file filter found image.");

    }

}
