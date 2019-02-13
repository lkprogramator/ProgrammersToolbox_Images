package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.model.Options;
import com.lkprogramator.tool_images.model.Picture;
import com.lkprogramator.tool_images.utils.ImageUtils;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by @author LKprogramator.
 */
class ImageResizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResizer.class);


    /**
     * Resize all image files
     *
     * @param imagesToResize  Images(pictures) to process
     * @param options  Settings
     * @return Processed image
     */
    public ArrayList<Picture> resizeImages(ArrayList<Picture> imagesToResize, Options options) {

        for (Picture imageFile : imagesToResize) {

            BufferedImage imageData = imageFile.getImageData();

            switch (options.getResizeVariant()) {
                case RESIZE_H:
                    imageFile.setImageData(ImageUtils.resizeMaxHeight(imageData, options.getResizeToHeight()));
                    break;
                case RESIZE_W:
                    imageFile.setImageData(ImageUtils.resizeMaxWidth(imageData, options.getResizeToWidth()));
                    break;
                case RESIZE_HW:
                    imageFile.setImageData(ImageUtils.resize(imageData, options.getResizeToWidth(), options.getResizeToHeight()));
                    break;
                case RESIZE_PRC:
                    imageFile.setImageData(ImageUtils.resizePercent(imageData, options.getResizeByPercent())); /// 100
                    break;
            }

        }

        LoggerMessage lm = new LoggerMessage("Resize of ALL Images is completed.")
                .addValue("Resize Variant ", options.getResizeVariant().toString());
        LOGGER.info(lm.toString());

        return imagesToResize;
    }

}
