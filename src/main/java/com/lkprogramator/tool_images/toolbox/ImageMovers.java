package com.lkprogramator.tool_images.toolbox;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by @author LKprogramator.
 */
public class ImageMovers {

    /**
     * Flip Image horizontally
     *
     * @param image  Image for flip
     * @return Processed image
     */
    public BufferedImage flipImageHorizontal(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage imageAfterFilter = new BufferedImage(width, height, type);

        for (int y = 0; y < height; y++) {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                int p = image.getRGB(lx, y);
                imageAfterFilter.setRGB(rx, y, p);
            }
        }


        return imageAfterFilter;
    }

/*

     * Flip Image vertically
     *
     * @param image  Image for flip
     * @return Processed image
     *
    private BufferedImage flipImageVertical(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage imageAfterFilter = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++)
        {
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--)
            {
                int p = image.getRGB(lx, y);
                imageAfterFilter.setRGB(rx, y, p);
            }
        }

        return imageAfterFilter;
    }
*/

    /**
     * Rotate Image by given angle
     *
     * @param image  Image to be rotated
     * @param angle  angle, positive number clockwise negative number counterclockwise
     * @return Processed image
     */
    public BufferedImage rotateImage(BufferedImage image, double angle) {

        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));

        int w = image.getWidth(null), h = image.getHeight(null);

        int neww = (int) Math.floor(w * cos + h * sin),
                newh = (int) Math.floor(h * cos + w * sin);

        BufferedImage imageAfterFilter = new BufferedImage(neww, newh, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = imageAfterFilter.createGraphics();

        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();

        return imageAfterFilter;
    }


}
