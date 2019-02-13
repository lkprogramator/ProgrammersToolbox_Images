package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageMoversTest extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMoversTest.class);

    private static BufferedImage baseImage;
    private static BufferedImage baseImageFilterFlip;
    private static BufferedImage baseImageFilterRotate;

    private static ImageMovers imageMovers = new ImageMovers();


    @BeforeAll
    private static void setUpClass() {

            baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
            baseImageFilterFlip = loadTestImage(pathToFiltersFolder + baseImageFilterFlipName);
            baseImageFilterRotate = loadTestImage(pathToFiltersFolder + baseImageFilterRotateName);
    }

    @Test
    @DisplayName("Test Flip Image Horizontal ")
    void flipImageHorizontal() {

        BufferedImage bufferedImage =  imageMovers.flipImageHorizontal( baseImage );

        double percentageDiff = getDifferencePercent(baseImageFilterFlip, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff, "Mirror image created successfully" );

    }

    @Test
    @DisplayName("Test Rotate Image")
    void rotateImage() {

        double angle = 90 ;

        BufferedImage bufferedImage =  imageMovers.rotateImage( baseImage , angle);

        double percentageDiff = getDifferencePercent(baseImageFilterRotate, bufferedImage);

        assertTrue( percentageDiff < maxPercentageDiff,"Rotate image");

    }




}
