package com.lkprogramator.tool_images.model;

import java.awt.image.BufferedImage;

/**
 * Created by LKprogramator.
 */
public class Picture {

    private String name;
    private String extension;
    private String filePath;
    private BufferedImage imageData;

    public Picture( String name, String filePath,BufferedImage imageData ) {
       this.name = name;
        this.filePath = filePath;
        this.imageData = imageData;
       this.extension = getFileExtension(name);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BufferedImage getImageData() {
        return imageData;
    }

    public void setImageData(BufferedImage imageData) {
        this.imageData = imageData;
    }


    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = "";
        if (index > 0) {
          extension = fileName.substring(index + 1);
        }
        return extension;
    }


}
