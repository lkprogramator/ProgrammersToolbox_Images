package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.testBase.baseTestClass;

import com.lkprogramator.tool_images.model.Picture;
import com.lkprogramator.tool_images.model.Options;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

class ImageResizerTest extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizerTest.class);

    private static BufferedImage baseImage;

    private static ArrayList<Picture> imageToResize;
    private static Options options;


    @BeforeAll
    private static void setup() {

        baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
        imageToResize = new ArrayList<>();

        Picture p1 = new Picture(baseImageName, pathToBaseImageFolder + baseImageName, baseImage);

        imageToResize.add(p1);

        options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);
        options.setResizeByPercent(0.5f);
        options.setOutputFolderPath(pathToGenFolder);

    }

    @Test
    @DisplayName("test resize list of images")
    void resizeImages() {

        double ratio = 0.5;
        int resizeToHeight = (int) Math.round(baseImage.getHeight() * ratio);
        int newWidth = (int) Math.round(baseImage.getWidth() * ratio);

        ImageResizer imageResizer = new ImageResizer();
        ArrayList<Picture> processedImages = imageResizer.resizeImages(imageToResize, options);

        BufferedImage resizedBufferedImage = processedImages.get(0).getImageData();

        assertTrue(resizedBufferedImage.getHeight() == resizeToHeight && resizedBufferedImage.getWidth() == newWidth);

    }
}
