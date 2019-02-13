package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.model.*;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import com.lkprogramator.tool_images.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by @author LKprogramator.
 */
public class ImageProcesor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcesor.class);

    /**
     * Load image files into model as Picture
     *
     * @param imageFileList  Image files from folder
     * @return Processed image
     */
    public ArrayList<Picture> prepareImageFiles(File[] imageFileList) {

        ArrayList<Picture> imagesToProcess = new ArrayList<>();

        for (File imageFile : imageFileList) {
            imagesToProcess.add(loadPicture(imageFile));
        }

        return imagesToProcess;
    }

    /**
     * Load image file into model as Picture
     *
     * @param imageFile  Image file
     * @return Processed image
     */
    public Picture loadPicture(File imageFile) {

        Picture loadedPicture = null;

        try {
            BufferedImage bfi = ImageUtils.readImage(imageFile);

            loadedPicture = new Picture(imageFile.getName(), imageFile.getAbsolutePath(), bfi);
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error loading of image file failure.").addValue("Source path", imageFile.getAbsolutePath()).addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        return loadedPicture;
    }

    /**
     * Apply filters to Images
     *
     * @param imagesAfterFilters  Image files to process
     * @param options  Settings
     * @return Processed images
     */
    public ArrayList<Picture> applyFiltersToImages(ArrayList<Picture> imagesAfterFilters, Options options) {

        if (  options.getRotate() != 0   ) {
            imagesAfterFilters = applyFilter(imagesAfterFilters, FilterType.ROTATE, options);
        }

        if ( !options.getWatermarkText().isEmpty() ) {
            imagesAfterFilters = applyFilter(imagesAfterFilters, FilterType.WATERMARKTEXT, options);
        }

        for (String s : options.getImageMods()) {

            imagesAfterFilters = applyFilter(imagesAfterFilters, FilterType.valueOf(s.toUpperCase()), options);
        }

        return imagesAfterFilters;
    }

    /**
     * Apply filters to Images
     *
     * @param imagesAfterFilters  Image files to process
     * @param filter  Filter tobe apply to the image
     * @param options  Settings
     * @return Processed images
     */
    private ArrayList<Picture> applyFilter(ArrayList<Picture> imagesAfterFilters, FilterType filter, Options options) {

        ImageConvertor imageConvertor = new ImageConvertor();
        ImageMovers imageMovers = new ImageMovers();

        for (Picture imageFile : imagesAfterFilters) {

            switch (filter) {
                case BLACKANDWHITE:
                    imageFile.setImageData(imageConvertor.blackAndWhiteImage(imageFile.getImageData()));
                    break;
                case GRAY:
                    imageFile.setImageData(imageConvertor.grayScaleImage(imageFile.getImageData()));
                    break;
                case MIRROR:
                    imageFile.setImageData(imageMovers.flipImageHorizontal(imageFile.getImageData()));
                    break;
                case ROTATE:
                    imageFile.setImageData(imageMovers.rotateImage(imageFile.getImageData(), (double) options.getRotate()));
                    break;
                case WATERMARKTEXT:
                    imageFile.setImageData(imageConvertor.filterWaterMarkWithText(imageFile.getImageData(),options.getWatermarkText()));
                    break;
            }

        }
        return imagesAfterFilters;
    }


}
