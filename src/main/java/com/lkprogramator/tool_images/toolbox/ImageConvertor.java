package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;

/**
 * Created by @author LKprogramator.
 */
 class ImageConvertor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageConvertor.class);

    /**
     * Image with WaterMark text
     *
     * @param imageForFilter  Image to be converted
     * @param waterMark  waterMark text in center of image
     * @return Prosed image
     */
    public BufferedImage filterWaterMarkWithText(BufferedImage imageForFilter, String waterMark ) {

        BufferedImage imageAfterFilter = new BufferedImage(imageForFilter.getWidth(), imageForFilter.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = imageAfterFilter.getGraphics();
        graphics.drawImage(imageForFilter, 0, 0, null);

        graphics.setFont(new Font("Arial", Font.PLAIN, 80));
        graphics.setColor(new Color(255, 0, 0, 40));

        //String watermark = "WaterMark generated";
        graphics.drawString(waterMark, imageForFilter.getWidth()/5, imageForFilter.getHeight()/3);
        graphics.dispose();

            return imageAfterFilter;
    }

    /**
     * Image with WaterMark image
     *
     * @param imageForFilter  Image to be converted
     * @param watermarkFile  WaterMark image
     * @param watermarkPosition  WaterMark position
     * @param alpha  Transparency of WaterMark image
     * @return Processed image
     */
// TODO find image for watermark
    public BufferedImage filterWaterMarkWithImage (BufferedImage imageForFilter, File watermarkFile,
                                      WatermarkPosition watermarkPosition, int alpha) {

        int srcWidth = imageForFilter.getWidth();
        int srcHeight = imageForFilter.getHeight();
        BufferedImage destBufferedImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);

        try {
            Graphics2D graphics2D = destBufferedImage.createGraphics();
            graphics2D.setBackground(Color.WHITE);
            graphics2D.clearRect(0, 0, srcWidth, srcHeight);
            graphics2D.drawImage(imageForFilter.getScaledInstance(srcWidth, srcHeight, Image.SCALE_SMOOTH), 0, 0, null);

            if (watermarkFile != null && watermarkFile.exists() && watermarkPosition != null
                    && watermarkPosition != WatermarkPosition.no) {
                BufferedImage watermarkBufferedImage = ImageIO.read(watermarkFile);
                int watermarkImageWidth = watermarkBufferedImage.getWidth();
                int watermarkImageHeight = watermarkBufferedImage.getHeight();
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha / 100.0F));
                int x = 0;
                int y = 0;
                if (watermarkPosition == WatermarkPosition.topLeft) {
                    x = 0;
                    y = 0;
                } else if (watermarkPosition == WatermarkPosition.topRight) {
                    x = srcWidth - watermarkImageWidth;
                    y = 0;
                } else if (watermarkPosition == WatermarkPosition.center) {
                    x = (srcWidth - watermarkImageWidth) / 2;
                    y = (srcHeight - watermarkImageHeight) / 2;
                } else if (watermarkPosition == WatermarkPosition.bottomLeft) {
                    x = 0;
                    y = srcHeight - watermarkImageHeight;
                } else if (watermarkPosition == WatermarkPosition.bottomRight) {
                    x = srcWidth - watermarkImageWidth;
                    y = srcHeight - watermarkImageHeight;
                }
                graphics2D.drawImage(watermarkBufferedImage, x, y, watermarkImageWidth, watermarkImageHeight, null);
            }
            graphics2D.dispose();
          // ImageIO.write(destBufferedImage, "JPEG", destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destBufferedImage;

    }

    public enum WatermarkPosition {
        no, topLeft, topRight, center, bottomLeft, bottomRight
    }

    /**
     * Image to gray scale
     *
     * @param image  Image to be converted
     * @return Processed image
     */
    public BufferedImage grayScaleImage(BufferedImage image) {

            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            image = op.filter(image, null);
            return image;

    }

    /**
     * Image to black and white
     *
     * @param orginalImage  Image to be converted
     * @return Processed image
     */
 public BufferedImage blackAndWhiteImage(BufferedImage orginalImage) {

      BufferedImage blackAndWhiteImg = new BufferedImage(
             orginalImage.getWidth(), orginalImage.getHeight(),
             BufferedImage.TYPE_BYTE_BINARY);

     Graphics2D graphics = blackAndWhiteImg.createGraphics();
     graphics.drawImage(orginalImage, 0, 0, null);

     return blackAndWhiteImg;
 }



}







