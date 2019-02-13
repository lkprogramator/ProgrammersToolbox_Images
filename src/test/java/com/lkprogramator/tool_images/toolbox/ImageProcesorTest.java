package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.model.Options;
import com.lkprogramator.tool_images.model.Picture;
import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ImageProcesorTest extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcesorTest.class);

    private ImageProcesor imageProcesor = new ImageProcesor();

    @Test
    @DisplayName("Load multiple images as pictures")
    void prepareImageFiles() {
        BufferedImage baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);

        File folder = new File(pathToBaseImageFolder);
        File[] imageFileList = folder.listFiles();

        ArrayList<Picture> imagesToProcess = imageProcesor.prepareImageFiles(imageFileList);

        double percentageDiff = getDifferencePercent(baseImage, imagesToProcess.get(0).getImageData());

        assertTrue(percentageDiff < maxPercentageDiff,"Load multiple images as pictures");

    }

    @Test
    @DisplayName("Load single image as picture")
    void loadPicture() {

        BufferedImage baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
        Picture resultImage = imageProcesor.loadPicture(new File(pathToBaseImageFolder + baseImageName));

        double percentageDiff = getDifferencePercent(baseImage, resultImage.getImageData());

        assertTrue(percentageDiff < maxPercentageDiff,"Load single image as picture");

    }

    @Test
    @DisplayName("Apply Filters to Images ")

    void applyFiltersToImages() {

        BufferedImage baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
        BufferedImage  baseImageGray = loadTestImage(pathToFiltersFolder + baseImageFilterGrayName);

        ArrayList<Picture> imagesToProcess = new ArrayList<>();

        Picture p1 = new Picture(baseImageName,pathToBaseImageFolder + baseImageName, baseImage );

        imagesToProcess.add(p1);

        String[] filters = {"gray"};

        Options options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);
        options.setImageMods( filters );
        options.setOutputFolderPath(pathToGenFolder);

        ArrayList<Picture> resultImages = imageProcesor.applyFiltersToImages( imagesToProcess, options);

        Picture resultPicture = resultImages.get(0);

        double percentageDiff = getDifferencePercent(baseImageGray, resultPicture.getImageData());

        assertTrue(percentageDiff < (maxPercentageDiff*2),"Apply Filters to Images done");

    }
}
