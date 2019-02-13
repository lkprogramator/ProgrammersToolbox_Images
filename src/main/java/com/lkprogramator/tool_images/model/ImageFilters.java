package com.lkprogramator.tool_images.model;

import java.util.*;

/**
 * Created by LKprogramator.
 */
public class ImageFilters {

    // private String imageMods; //  blackAndWhite;grayScale;FitForWeb
    private String blackAndWhite;
    private String FitForWeb;
    private String mirror;
    private String rotate; // number


    public ImageFilters() {
    }

    public ImageFilters(Map<String, String> mapOfFilters) {

        mapOfFilters.forEach((k, v) -> {

            switch (k) {
                case "blackAndWhite":
                    this.setBlackAndWhite("blackAndWhite");
                    break;

                case "mirror":
                    if (isProperDirection(v)) {
                        this.setMirror(v);
                    }
                    break;

                case "rotate":
                    if (isNumeric(v)) {
                        this.setRotate(v);
                    }
                    break;

            }

        });

    }


    public String getBlackAndWhite() {
        return blackAndWhite;
    }

    public void setBlackAndWhite(String blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public String getFitForWeb() {
        return FitForWeb;
    }

    public void setFitForWeb(String fitForWeb) {
        FitForWeb = fitForWeb;
    }

    public String getMirror() {
        return mirror;
    }

    public void setMirror(String mirror) {
        this.mirror = mirror;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        return "{\"blackAndWhite\":\"" + blackAndWhite + '\"' +
                ",\"FitForWeb:\"" + FitForWeb + '\"' +
                ",\"mirror:\"" + mirror + '\"' +
                ",\"rotate:\"" + rotate + '\"' +
                '}';
    }

    public boolean isEmpty() {
        return (this.blackAndWhite == null && this.mirror == null && this.rotate == null);
    }

    private boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isProperDirection(String strDirection) {

        Set<String> directions = new HashSet<>(Arrays.asList("LR", "RL", "UD", "DU"));

        return directions.contains(strDirection);
    }


    public ArrayList<FilterType> getAvailableFilters() {

        ArrayList<FilterType> filters = new ArrayList<>();

        if (!"".equals(blackAndWhite) && blackAndWhite != null) {
            filters.add(FilterType.BLACKANDWHITE);
        }
        if (!"".equals(mirror) && mirror != null) {
            filters.add(FilterType.MIRROR);
        }
        if (!"".equals(rotate) && rotate != null) {
            filters.add(FilterType.ROTATE);
        }

        return filters;
    }


}
