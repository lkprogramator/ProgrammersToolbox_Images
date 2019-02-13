package com.lkprogramator.tool_images.utils;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * Created by @author LKprogramator.
 */
public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    public static BufferedImage readImage(String path) throws IOException {
        return readImage(new File(path));
    }

    public static BufferedImage readImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    public static boolean writeImage(BufferedImage image, String path) throws IOException {
        return writeImage(image, new File(path));
    }

    public static boolean writeImage(BufferedImage image, File file) throws IOException {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ImageIO.write(image, extension, file);
    }

    public static boolean writeImage(BufferedImage image, File file, String fileExtension) throws IOException {
        return ImageIO.write(image, fileExtension, file);
    }

    public static BufferedImage resizePercent(BufferedImage image, float percent) {

        int width = (int) (image.getWidth() * percent);
        int height = (int) (image.getHeight() * percent);

        return resize(image, width, height);
    }

    public static BufferedImage resizeMaxHeight(BufferedImage image, int height) {
        int width = (int) (image.getWidth() * (height * 100 / image.getHeight()) / 100);
        return resize(image, width, height);
    }

    public static BufferedImage resizeMaxWidth(BufferedImage image, int width) {
        int height = (int) (image.getHeight() * (width * 100 / image.getWidth()) / 100);
        return resize(image, width, height);
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }



}
