package com.lkprogramator.tool_images.testBase;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(baseTestClass.class);

    protected static String pathToFiltersFolder = "src/test/resources/test_images_filters/";
    protected static String pathToGenFolder = "src/test/resources/test_images_gen/";
    protected static String pathToWriteFolder = "src/test/resources/test_images_write/";
    protected static String pathToResizeFolder = "src/test/resources/test_images_resize/";
    protected static String pathToBaseImageFolder = "src/test/resources/test_images_base/";
    protected static String pathToTestImageFolder = "src/test/resources/test_images/";
    protected static String pathToResourceFolder = "src/test/resources/";

    protected static String baseImageName = "tree_and_rock_good.jpg";
    protected static String baseImageFilterFlipName = "tree_and_rock_filter_flip.jpg";
    protected static String baseImageFilterRotateName = "tree_and_rock_filter_rotate.jpg";
    protected static String baseImageFilterGrayName = "tree_and_rock_filter_gray.jpg";
    protected static String baseImageFilterBaWName = "tree_and_rock_filter_bw.jpg";
    protected static String baseImageFilterWaterMarkTextName = "tree_and_rock_filter_watermark_text.jpg";


    // To compare images, set allowed difference between two images, default is 1.0 = 1% usually 0.71
    protected static double maxPercentageDiff = 1.0;

    protected static BufferedImage loadTestImage(String pathToResource) {
        BufferedImage testImage = null;
        try {
            testImage = ImageIO.read(new File(pathToResource));
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when Loading images for tests.")
                    .addValue("Resouce path ", pathToResource)
                    .addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        return testImage;
    }


    protected static double getDifferencePercent(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();
        if (width != width2 || height != height2) {
            throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", width, height, width2, height2));
        }

        long diff = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * width * height;

        return 100.0 * diff / maxDiff;
    }

    private static int pixelDiff(int rgb1, int rgb2) {
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }







}
