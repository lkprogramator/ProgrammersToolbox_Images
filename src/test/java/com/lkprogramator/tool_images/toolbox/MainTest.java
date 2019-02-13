package com.lkprogramator.tool_images.toolbox;

import com.lkprogramator.tool_images.model.Options;
import com.lkprogramator.tool_images.model.Picture;
import com.lkprogramator.tool_images.testBase.baseTestClass;
import com.lkprogramator.tool_images.utils.messages.LoggerMessage;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class MainTest extends baseTestClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    @AfterAll
    private static void cleanUp() {

        // clean generated images and folders
        try {
            FileUtils.cleanDirectory(new File(pathToGenFolder));
        } catch (IOException e) {
            LoggerMessage lm = new LoggerMessage("Error when clearing folder after tests.").addValue("Error message", e.getMessage());
            LOGGER.error(lm.toString());
        }

    }

    @Test
    @DisplayName("Test Main App resize ")
    void main() {

        String[] args = { "source="+ pathToBaseImageFolder + baseImageName ,"output="+ "/home/zax/IntelliJProjects/ProgrammersToolbox_Images/src/test/resources/test_images_gen/", "resizeByPercent=0.5"};

         Main.main(args);

        BufferedImage  baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
        BufferedImage  baseImageAfter = loadTestImage(pathToGenFolder+ "result_mod_images/" + baseImageName);

        assertAll("Main App resize",
                () -> assertEquals(Math.round( baseImage.getHeight() * 0.5), baseImageAfter.getHeight() ,"Image Height is correct"  ),
                () -> assertEquals(  Math.round(baseImage.getWidth() * 0.5) , baseImageAfter.getWidth() ,"Image Width is correct" )
        );

    }

    @Test
    @DisplayName("Extract Arguments from input")
    void extractArguments() {

        Map expected = new HashMap();
        expected.put("source", "/home/zax/IntelliJProjects/ProgrammersToolbox_Images/src/test/resources/test_images_base/tree_and_rock.jpg");
        expected.put("output", "/home/zax/IntelliJProjects/ProgrammersToolbox_Images/src/test/resources/test_images_gen");
        expected.put("resizeByPercent", "0.5");

        String[] args = { "source="+expected.get("source") ,"output="+expected.get("output"), "resizeByPercent="+expected.get("resizeByPercent")};

        Map actual = Main.extractArguments( args);

        assertTrue(expected.equals( actual ),"Arguments were extracted.");

    }

    @Test
    @DisplayName("Validate input Arguments")
    void validateArguments() {

        Map expected = new HashMap();
        expected.put("source", "/home/zax/IntelliJProjects/ProgrammersToolbox_Images/src/test/resources/test_images_base/tree_and_rock.jpg");
        expected.put("output", "/home/zax/IntelliJProjects/ProgrammersToolbox_Images/src/test/resources/test_images_gen");
        expected.put("resizeByPercent", "0.5");
        expected.put("testFakeKey", "emptyValue");
        expected.put("resizeToHeight", "emptyValue");

        Map actual = Main.validateArguments(expected);

        expected.remove("testFakeKey");
        expected.remove("resizeToHeight");


        assertTrue( expected.equals(  actual ),"Arguments were validated correctly.");

    }


    @Test
    @Ignore
    @DisplayName("Filter black and white ")
    void incorrectArgumentValueMessage() {
        // test LOG message
    }


    @Test
    @DisplayName("Building of Options model")
    void buildModel() {

        Map mapOfArguments = new HashMap();
        mapOfArguments.put("source",  pathToBaseImageFolder + baseImageName);
        mapOfArguments.put("output", pathToGenFolder);
        mapOfArguments.put("resizeByPercent", "0.5");


        Options expectedOptions = new Options();
        expectedOptions.setSourcePath(pathToBaseImageFolder + baseImageName);
        expectedOptions.setResizeByPercent(0.5f);
        expectedOptions.setOutputFolderPath(pathToGenFolder);

        Options actualOptions = Main.buildModel(mapOfArguments);

        assertEquals( expectedOptions, actualOptions ,"Options model was created");
    }


    @Test
    @DisplayName("Validate Filters")
    void validateFilters() {

        String inputFilters = "blackAndWhite;mirror;fakefilter";
        String expectedFilters = "blackAndWhite;mirror";

        String actualFilters = Main.validateFilters(inputFilters);

        assertEquals(  actualFilters, expectedFilters ,"List of filters is correct");

    }


    @Test
    @DisplayName("Load folder of Images ")
    void loadImageFiles() {

        ArrayList<Picture> imagesToProcess = Main.loadImageFiles(pathToTestImageFolder );

        assertAll("Images loaded",
                () -> assertNotNull(imagesToProcess ," list is not null" ),
                () -> assertEquals( 4, imagesToProcess.size(), "List size is correct")
        );

    }


    @Test
    @DisplayName("Process Images by resize Options")
    void processImagesByOptionsResize() {

        BufferedImage  baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);

        ArrayList<Picture> imagesToProcess = new ArrayList<>();

        Picture p1 = new Picture(baseImageName,pathToBaseImageFolder + baseImageName, baseImage );

        imagesToProcess.add(p1);

        Options options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);
        options.setResizeByPercent(0.5f);
        options.setOutputFolderPath(pathToGenFolder);

        ArrayList<Picture> resultImages =  Main.processImagesByOptions(imagesToProcess, options);

        Picture resultPicture = resultImages.get(0);

        assertAll("Images By Options",
                () -> assertEquals(  Math.round( baseImage.getHeight() * 0.5), resultPicture.getImageData().getHeight(),"Image Height is correct"  ),
                () -> assertEquals( Math.round(baseImage.getWidth() * 0.5) , resultPicture.getImageData().getWidth()  ,"Image Width is correct" )
        );

    }


    @Test
    @DisplayName("Process Images by filter Options")
    void processImagesByOptionsFilters() {

        BufferedImage  baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);
        BufferedImage  baseImageBW = loadTestImage(pathToFiltersFolder + baseImageFilterBaWName);

        ArrayList<Picture> imagesToProcess = new ArrayList<>();

        Picture p1 = new Picture(baseImageName,pathToBaseImageFolder + baseImageName, baseImage );

        imagesToProcess.add(p1);

        String[] filters = { "blackAndWhite" };


        Options options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);
        options.setImageMods( filters );
        options.setOutputFolderPath(pathToGenFolder);

        ArrayList<Picture> resultImages =  Main.processImagesByOptions(imagesToProcess, options);

        Picture resultPicture = resultImages.get(0);

        double percentageDiff = getDifferencePercent(baseImageBW, resultPicture.getImageData());

        assertTrue( percentageDiff < maxPercentageDiff,"ImagesByOptions Filters" );

    }

    @Test
    @DisplayName("Create output files")
    void createOutputFiles() {

        BufferedImage  baseImage = loadTestImage(pathToBaseImageFolder + baseImageName);

        ArrayList<Picture> imagesToProcess = new ArrayList<>();

        Picture p1 = new Picture(baseImageName,pathToBaseImageFolder + baseImageName, baseImage );

        imagesToProcess.add(p1);

        Options options = new Options();
        options.setSourcePath(pathToBaseImageFolder + baseImageName);
        options.setResizeByPercent(0.5f);
        options.setOutputFolderPath(pathToGenFolder);

            Main.createOutputFiles(imagesToProcess ,options );

        Path resultPath = Paths.get(pathToGenFolder + "result_mod_images/"+  baseImageName );

        assertTrue(Files.exists( resultPath ), "Output images successfully created.");

    }


}
