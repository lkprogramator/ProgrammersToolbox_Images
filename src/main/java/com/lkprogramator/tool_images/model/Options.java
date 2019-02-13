package com.lkprogramator.tool_images.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * Created by LKprogramator.
 */
public class Options {

    private String sourcePath;
    private String outputFolderPath = "";

    private int resizeToHeight = 0;
    private int resizeToWidth = 0;
    private float resizeByPercent = 0;

    private int rotate = 0;

    private String watermarkText = "";
    private String[] imageMods; //  blackAndWhite;grayScale;FitForWeb;mirror

    public Options() {}


    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getOutputFolderPath() {

        if (outputFolderPath.trim().isEmpty() ) {
            getOutputFolderFromSource();
        }

        return outputFolderPath;
    }

    public void setOutputFolderPath(String outputFolderPath) {
        this.outputFolderPath = outputFolderPath;
    }

    public int getResizeToHeight() {
        return resizeToHeight;
    }

    public void setResizeToHeight(int resizeToHeight) {
        this.resizeToHeight = resizeToHeight;
    }

    public int getResizeToWidth() {
        return resizeToWidth;
    }

    public void setResizeToWidth(int resizeToWidth) {
        this.resizeToWidth = resizeToWidth;
    }

    public float getResizeByPercent() {
        return resizeByPercent;
    }

    public void setResizeByPercent(float resizeByPercent) {
        this.resizeByPercent = resizeByPercent;
    }


    public String[] getImageMods() {
        return imageMods;
    }

    public void setImageMods(String[] imageMods) {
        this.imageMods = imageMods;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }
    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    /**
     * Determine if there is any filter tobe apply
     */
    public boolean isFilterPresent() {

        boolean isFilterPresent = false;

        if (  this.rotate != 0   ) {
            isFilterPresent =  true;
        }

        if ( !this.watermarkText.isEmpty() ) {
            isFilterPresent =  true;
        }

        if (this.imageMods != null) {
            if (this.imageMods.length > 0) {
                isFilterPresent =  true;
            }
        }

        return isFilterPresent;
    }

    /**
     * Resize Variant tobe apply to images
     */
    public ResizeVariants getResizeVariant() {

        ResizeVariants resizeVariant = ResizeVariants.NO_RESIZE;

        if ( this.resizeToHeight > 0 && this.resizeToWidth > 0  ) {
            resizeVariant = ResizeVariants.RESIZE_HW;
        }

        if ( this.resizeToHeight > 0 && this.resizeToWidth == 0  ) {
            resizeVariant = ResizeVariants.RESIZE_H;
        }

        if ( this.resizeToHeight == 0 && this.resizeToWidth > 0  ) {
            resizeVariant = ResizeVariants.RESIZE_W;
        }

        if ( this.resizeByPercent != 0 ) {
            resizeVariant = ResizeVariants.RESIZE_PRC;
        }
        return resizeVariant;
    }

    /**
     * If output folder is not specified, get it from source
     */
    private void getOutputFolderFromSource( ) {

        Path sourcePath = Paths.get(this.sourcePath);

        if ( Files.isDirectory(sourcePath) ) {
            this.outputFolderPath = this.sourcePath;
        }
        else {
            this.outputFolderPath = sourcePath.getParent().toString() + "/" ;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Options options = (Options) o;
        return resizeToHeight == options.resizeToHeight &&
                resizeToWidth == options.resizeToWidth &&
                Float.compare(options.resizeByPercent, resizeByPercent) == 0 &&
                rotate == options.rotate &&
                sourcePath.equals(options.sourcePath) &&
                Objects.equals(outputFolderPath, options.outputFolderPath) &&
                Objects.equals(watermarkText, options.watermarkText) &&
                Arrays.equals(imageMods, options.imageMods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sourcePath, outputFolderPath, resizeToHeight, resizeToWidth, resizeByPercent, rotate, watermarkText);
        result = 31 * result + Arrays.hashCode(imageMods);
        return result;
    }
}

