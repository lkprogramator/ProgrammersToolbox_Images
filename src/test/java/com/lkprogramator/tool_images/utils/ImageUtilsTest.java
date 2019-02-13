package com.lkprogramator.tool_images.utils;

import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * Created by @author LKprogramator.
 */
class ImageUtilsTest extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtilsTest.class);

    private static BufferedImage baseImage;

    @BeforeAll
    private static void setUpClass() {
            baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
    }

    @AfterAll
    private static void cleanUp() {

        // clean generated images from folder
        try {
            FileUtils.cleanDirectory(new File(pathToWriteFolder));
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when clearing folder for tests.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

    }

    @Test
    @DisplayName("Read Image from file by path")
    void readImage() {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageUtils.readImage(pathToBaseImageFolder + baseImageName);


        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when reading image in test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        assertTrue(bufferedImagesEqual(baseImage, bufferedImage), "Reading of image is correct.");

    }

    @Test
    @DisplayName("Read Image from file by file instants")
    void readImage1() {

        File testImageFile = new File(pathToBaseImageFolder + baseImageName);
        BufferedImage bufferedImage = null;
        try {

            bufferedImage = ImageUtils.readImage(testImageFile);

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when reading image in test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }
        assertTrue(  bufferedImagesEqual(baseImage, bufferedImage),"Reading of image is correct.");

    }

    @Test
    @DisplayName("Write Image to file by file path")
    void writeImage() {

        String imageName = "write_image.jpg";
        BufferedImage bufferedImage = null;

        try {
            ImageUtils.writeImage(baseImage, pathToWriteFolder + imageName);

            bufferedImage = ImageIO.read(new File(pathToWriteFolder + imageName));

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when reading / writing image in test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        double percentageDiff = getDifferencePercent(baseImage, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff,"Write image to file with file path is correct");

    }

    @Test
    @DisplayName("Write Image to file by file instants")
    void writeImage1() {

        String imageName = "write_image1.jpg";

        BufferedImage bufferedImage = null;
        File testImageFile = new File(pathToWriteFolder + imageName);
        try {
            ImageUtils.writeImage(baseImage, testImageFile);

            bufferedImage = ImageIO.read(new File(pathToWriteFolder + imageName));

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when reading / writing image in test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        double percentageDiff = getDifferencePercent(baseImage, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff,"Write image to file with file instant is correct");
    }

    @Test
    @DisplayName("Write Image to file by file instants with fileExtension")
    void writeImage2() {

        String imageName = "write_image2.jpg";
        String fileExtension = "jpg";

        BufferedImage bufferedImage = null;
        File testImageFile = new File(pathToWriteFolder + imageName);
        try {
            ImageUtils.writeImage(baseImage, testImageFile, fileExtension);

            bufferedImage = ImageIO.read(new File(pathToWriteFolder + imageName));

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when reading / writing image in test.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        double percentageDiff = getDifferencePercent(baseImage, bufferedImage);

        assertTrue(percentageDiff < maxPercentageDiff,"Write image to file with extension is correct");
    }

    @Test
    @DisplayName("Resize Image by percent")
    void resizePercent() {

        float percent = 0.5f;
        double ratio = 0.5;
        int resizeToHeight = (int) Math.round(baseImage.getHeight() * ratio);
        int newWidth = (int) Math.round(baseImage.getWidth() * ratio);

        BufferedImage resizedBufferedImage = ImageUtils.resizePercent(baseImage, percent);

        assertTrue((resizedBufferedImage.getHeight() == resizeToHeight && resizedBufferedImage.getWidth() == newWidth),"Resize of image by percent is correct");
    }

    @Test
    @DisplayName("Resize Image max height")
    void resizeMaxHeight() {

        double ratio = 0.5;
        int resizeToHeight = (int) Math.round(baseImage.getHeight() * ratio);
        int newWidth = (int) Math.round(baseImage.getWidth() * ratio);

        BufferedImage resizedBufferedImage = ImageUtils.resizeMaxHeight(baseImage, resizeToHeight);

        assertTrue((resizedBufferedImage.getHeight() == resizeToHeight && resizedBufferedImage.getWidth() == newWidth),"Resize of image by max height is correct.");
    }

    @Test
    @DisplayName("Resize Image to max width")
    void resizeMaxWidth() {

        double ratio = 0.5;
        int resizeTowidth = (int) Math.round(baseImage.getWidth() * ratio);
        int newHeight = (int) Math.round(baseImage.getHeight() * ratio);

        BufferedImage resizedBufferedImage = ImageUtils.resizeMaxWidth(baseImage, resizeTowidth);

        assertTrue( (resizedBufferedImage.getWidth() == resizeTowidth && resizedBufferedImage.getHeight() == newHeight),"Resize of image by max width is correct.");
    }

    @Test
    @DisplayName("Resize Image to exact size")
    void resize() {
        int resizeToHeight = 660;
        int resizeTowidth = 809;

        BufferedImage resizedBufferedImage = ImageUtils.resize(baseImage, resizeTowidth, resizeToHeight);

        assertTrue( (resizedBufferedImage.getWidth() == resizeTowidth && resizedBufferedImage.getHeight() == resizeToHeight),"Resize of image to specific size  is correct.");
    }


    boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {

        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }


}
