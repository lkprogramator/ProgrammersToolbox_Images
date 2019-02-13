package com.lkprogramator.tool_images.toolbox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.lkprogramator.tool_images.model.*;
import com.lkprogramator.tool_images.utils.ImageUtils;
import org.apache.commons.lang3.EnumUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import com.lkprogramator.tool_images.utils.DirectoryUtils;
import com.lkprogramator.tool_images.utils.CommonUtils;

/**
 * Created by LKprogramator.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    //private static final String ARGUMENT_PREFIX ="-";
    private static final String ARGUMENT_SEPARATOR = "=";
    private static final String ARGUMENT_ITEM_SEPARATOR = ";";

    public static void main(String[] args) {

        if (args.length == 0) {
            LoggerMessage lm = new LoggerMessage("Error No console parameters, App has been terminated.");
            LOGGER.error(lm.toString());
            exitApp("Because there are No console parameters");
        }

        LoggerMessage lmAr = new LoggerMessage("Arguments:").addValue("args", args.toString());
        LOGGER.info("App has started. ", lmAr);

        Map<String, String> data = extractArguments(args);
        data = validateArguments(data);

        if ( data.isEmpty() ) {
            exitApp("No valid data");
        }

        Options options = buildModel(data);

        ArrayList<Picture> imagesToProcess = loadImageFiles(options.getSourcePath());

        if (!imagesToProcess.isEmpty() && (options.getResizeVariant() != ResizeVariants.NO_RESIZE || options.isFilterPresent())) {
            imagesToProcess = processImagesByOptions(imagesToProcess, options);
            createOutputFiles(imagesToProcess, options);
        }

    }

    /**
     * Extract Arguments from input
     * @param args  Array of input arguments and values
     * @return Map argument : value
     */
    public static Map<String, String> extractArguments(String[] args) {

        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {

            String[] keyVal = arg.split(ARGUMENT_SEPARATOR);

            if (keyVal.length == 2) {
                if (!CommonUtils.isBlankString(keyVal[0]) && !CommonUtils.isBlankString(keyVal[1])) {
                    arguments.put(keyVal[0].trim(), keyVal[1].trim());
                }
            }

        }

        return arguments;
    }

    /**
     * Validate Arguments, remove items with incorrect key or value
     * @param argsMap  Map to validate
     * @return Map of valid ones
     */
    public static Map<String, String> validateArguments(Map<String, String> argsMap) {

        Map<String, String> arguments = new HashMap<>();

        boolean isSourcePressent = false;
        boolean isResizePressent = false;
        boolean isFilterPressent = false;

        for (Map.Entry<String, String> entry : argsMap.entrySet()) {

            if (EnumUtils.isValidEnum(EntryArguments.class, entry.getKey().toUpperCase())) {

                EntryArguments entArg = EntryArguments.valueOf(entry.getKey().toUpperCase());

                switch (entArg) {
                    case SOURCE:

                        Path sourcePath = Paths.get(entry.getValue());

                        if (Files.notExists(sourcePath)) {
                            LoggerMessage lm = new LoggerMessage("Error Image source not exist.").addValue("Source path", entry.getValue());
                            LOGGER.error(lm.toString());
                            exitApp("Source File/Folder does not exist");
                        } else {

                            if (Files.isDirectory(sourcePath)) {

                                if (!DirectoryUtils.isAnyImageFileInFolder(entry.getValue())) {

                                    incorrectArgumentValueMessage("Source Folder", entry.getValue());
                                    exitApp("Source Folder does not contain any Image");

                                } else {
                                    arguments.put(entry.getKey(), entry.getValue());
                                    isSourcePressent = true;
                                }

                            } else {

                                if (CommonUtils.isImageFile(entry.getValue())) {
                                    arguments.put(entry.getKey(), entry.getValue());
                                    isSourcePressent = true;
                                } else {
                                    incorrectArgumentValueMessage("Source file", entry.getValue());
                                    exitApp("Source file is not Image");
                                }

                            }

                        }
                        break;
                    case RESIZEBYPERCENT:
                        if (!CommonUtils.isNumeric(entry.getValue())) {
                            incorrectArgumentValueMessage("Resize by Percent ", entry.getValue());
                        } else {
                            if (CommonUtils.isBetweenNumbers(Float.parseFloat(entry.getValue()), 0.1f, 100.0f)) {
                                arguments.put(entry.getKey(), entry.getValue());
                                isResizePressent = true;
                            }
                        }

                        break;
                    case RESIZETOHEIGHT:
                    case RESIZETOWIDTH:

                        if (CommonUtils.isNumeric(entry.getValue())) {

                            int res_size = Integer.parseInt(entry.getValue());
                            if (res_size >= 1) {

                                arguments.put(entry.getKey(), entry.getValue());
                                isResizePressent = true;
                            }
                        } else {

                            incorrectArgumentValueMessage("Resize " + entry.getKey(), entry.getValue());

                        }
                        break;

                    case IMAGEMODS:

                        String validImageMods = validateFilters(entry.getValue());

                        if (!validImageMods.isEmpty()) {

                            arguments.put(entry.getKey(), entry.getValue());
                            isFilterPressent = true;
                        }

                        break;
                    case WATERMARKTEXT:

                        if (!entry.getValue().trim().isEmpty()) {

                            arguments.put(entry.getKey(), entry.getValue());
                            isFilterPressent = true;
                        }

                        break;
                    case ROTATE:

                        if (CommonUtils.isNumeric(entry.getValue())) {
                            if (CommonUtils.isBetweenNumbers(Integer.parseInt(entry.getValue()), -359, 359)) {
                                arguments.put(entry.getKey(), entry.getValue());
                                isFilterPressent = true;
                            }
                        } else {
                            incorrectArgumentValueMessage("Rotate", entry.getValue());

                        }
                        break;

                    case OUTPUT:

                        Path pathToResultFolder = Paths.get(entry.getValue());

                        if (Files.notExists(pathToResultFolder)) {
                            LoggerMessage lm = new LoggerMessage("The result folder not exist.").addValue("Result folder path", entry.getValue());
                            LOGGER.info(lm.toString());

                        } else {
                            if (Files.isDirectory(pathToResultFolder)) {
                                arguments.put(entry.getKey(), entry.getValue());
                            }
                        }

                        break;
                }

            }

        }

        // check validation patterns
        if (isSourcePressent && (isResizePressent || isFilterPressent)) {
            return arguments;
        } else {
            LoggerMessage lm = new LoggerMessage("App finished, with insufficient data.").addValue("isSourcePressent", "" + isSourcePressent).addValue("isResizePressent", "" + isResizePressent).addValue("isFilterPressent", "" + isFilterPressent);
            LOGGER.info(lm.toString());

            arguments.clear();
            return arguments;
        }

    }

    public static void incorrectArgumentValueMessage(String arg, String value) {
        LoggerMessage lm = new LoggerMessage("Argument " + arg + " has incorrect value.").addValue("Value", value);
        LOGGER.info(lm.toString());
    }


    public static void exitApp(String reason) {
        LoggerMessage lm = new LoggerMessage("The application was terminated").addValue("Reason", reason);
        LOGGER.info(lm.toString());
        System.exit(0);
    }

    /**
     * Turn input arguments into Options
     *
     * @param OptionMap  Map of input arguments
     * @return Options
     */
    public static Options buildModel(Map<String, String> OptionMap) {

        Options options = new Options();

        OptionMap.forEach((key, val) -> {
            EntryArguments entArg = EntryArguments.valueOf(key.toUpperCase());
            switch (entArg) {
                case SOURCE:
                    options.setSourcePath(val);
                    break;
                case RESIZEBYPERCENT:
                    options.setResizeByPercent(Float.parseFloat(val));
                    break;
                case RESIZETOHEIGHT:
                    options.setResizeToHeight(Integer.parseInt(val));
                    break;
                case RESIZETOWIDTH:
                    options.setResizeToWidth(Integer.parseInt(val));
                    break;
                case IMAGEMODS:
                    options.setImageMods(val.split(ARGUMENT_ITEM_SEPARATOR));
                    break;
                case ROTATE:
                    options.setRotate(Integer.parseInt(val));
                    break;
                case WATERMARKTEXT:
                    options.setWatermarkText(val);
                    break;
                case OUTPUT:
                    options.setOutputFolderPath(val);
                    break;
            }

        });

        return options;
    }


    /**
     * Check if filters in string are valid FilterType and remove those that not
     *
     * @param str  String of filters separated by ARGUMENT_ITEM_SEPARATOR
     * @return String of valid filters
     */
    public static String validateFilters(String str) {

        String[] imageMods = str.split(ARGUMENT_ITEM_SEPARATOR);

        ArrayList<String> returnedValidImageMods = new ArrayList<>();

        for (String imageMod : imageMods) {

            if (EnumUtils.isValidEnum(FilterType.class, imageMod.toUpperCase())) {
                returnedValidImageMods.add(imageMod);
            }
        }

        return String.join(ARGUMENT_ITEM_SEPARATOR, returnedValidImageMods);
    }

    /**
     * Load Images from source as Pictures
     *
     * @param imageSource  Source file/folder
     * @return Processed images
     */
    public static ArrayList<Picture> loadImageFiles(String imageSource) {

        Path sourcePath = Paths.get(imageSource);
        ImageProcesor imageProcesor = new ImageProcesor();
        ArrayList<Picture> images = new ArrayList<>();

        if (Files.isDirectory(sourcePath)) {
            File[] imageFiles = DirectoryUtils.listImageFilesInFolder(imageSource);
            images = imageProcesor.prepareImageFiles(imageFiles);
        } else {
            File imagefile = new File(imageSource);
            images.add(imageProcesor.loadPicture(imagefile));
        }

        return images;

    }

    /**
     * Apply Options to Images
     *
     * @param imagesToProcess  Image files to process
     * @param options  Settings
     * @return Processed images
     */
    public static ArrayList<Picture> processImagesByOptions(ArrayList<Picture> imagesToProcess, Options options) {

        if (options.getResizeVariant() != ResizeVariants.NO_RESIZE) {
            ImageResizer imageResizer = new ImageResizer();
            imagesToProcess = imageResizer.resizeImages(imagesToProcess, options);
        }

        if (options.isFilterPresent()) {

            ImageProcesor imageProcesor = new ImageProcesor();
            imagesToProcess = imageProcesor.applyFiltersToImages(imagesToProcess, options);

        }
        return imagesToProcess;
    }

    /**
     * Generate output files for images
     *
     * @param imagesToProcess  Images to process
     * @param options  Settings
     */
    public static void createOutputFiles(ArrayList<Picture> imagesToProcess, Options options) {

        String outputFileFolderName = "result_mod_images";

        String outputFolder = options.getOutputFolderPath() + outputFileFolderName;

        Path outputPath = Paths.get(outputFolder);

        try {

            if (Files.exists(outputPath)) {
                DirectoryUtils.clearDirectory(outputPath);
            } else {
                DirectoryUtils.createDirectory(outputPath);
            }

        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error create result folder failure.").addValue("folder Path", options.getOutputFolderPath()).addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

        try {
            for (Picture imagesToPrint : imagesToProcess) {
                ImageUtils.writeImage(imagesToPrint.getImageData(), new File(outputPath.toString() + "/" + imagesToPrint.getName()), imagesToPrint.getExtension());
            }
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error creating result files failure.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

    }

// end
}




