package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

class ImageConvertorTest extends baseTestClass {


    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMoversTest.class);

    private static BufferedImage baseImage;
    private static BufferedImage baseImageFilterGray;
    private static BufferedImage baseImageFilterBaW;
    private static BufferedImage baseImageFilterWaterMarkText;

    private static ImageConvertor imageConvertor = new ImageConvertor();


    @BeforeAll
    private static void setUpClass() {

            baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
            baseImageFilterGray = loadTestImage(pathToFiltersFolder + baseImageFilterGrayName);
            baseImageFilterBaW = loadTestImage(pathToFiltersFolder + baseImageFilterBaWName);
            baseImageFilterWaterMarkText = loadTestImage(pathToFiltersFolder + baseImageFilterWaterMarkTextName);

    }

    @Test
    @DisplayName("Filter Watermark text ")
    void filterWaterMarkWithText() {

        BufferedImage bufferedImage =  imageConvertor.filterWaterMarkWithText( baseImage, "Test of Watermark" );

        double percentageDiff = getDifferencePercent(baseImageFilterWaterMarkText, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff ,"Filter Image with text watermark.");

    }

    @Test
    @Ignore
    @DisplayName("Filter Watermark image")
    void filterWaterMarkWithImage() {
        // TODO find Image to use as watermark
    }

    @Test
    @DisplayName("Filter image gray")
    void grayScaleImage() {

        BufferedImage bufferedImage =  imageConvertor.grayScaleImage( baseImage );

        double percentageDiff = getDifferencePercent(baseImageFilterGray, bufferedImage);

        assertTrue(percentageDiff < (maxPercentageDiff*2),"Filter Image is in Gray scale.");

    }

    @Test
    @DisplayName("Filter black and white ")
    void blackAndWhiteImage() {

        BufferedImage bufferedImage =  imageConvertor.blackAndWhiteImage( baseImage );

        double percentageDiff = getDifferencePercent(baseImageFilterBaW, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff,"Filter Image is in black & white.");

    }

}
